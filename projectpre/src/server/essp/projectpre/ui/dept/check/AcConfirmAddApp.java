package server.essp.projectpre.ui.dept.check;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Acnt;
import server.essp.projectpre.db.AcntApp;
import server.essp.projectpre.db.AcntPerson;
import server.essp.projectpre.db.AcntPersonApp;
import server.essp.projectpre.service.account.IAccountService;
import server.essp.projectpre.service.accountapplication.IAccountApplicationService;
import server.essp.projectpre.service.constant.Constant;
import server.essp.projectpre.service.sendMail.ISendMailService;
import server.essp.projectpre.ui.dept.apply.AfDeptApp;
import server.essp.syncproject.SyncService;
import server.framework.common.BusinessException;
import c2s.dto.DtoUtil;
import c2s.dto.TransactionData;
import c2s.essp.common.account.IDtoAccount;
import c2s.essp.common.user.DtoUser;

public class AcConfirmAddApp extends AbstractESSPAction {
    public static final String vmFile11 = "mail/template/proj/DeptCheckMail.html"; 
    public static final String vmFile12 = "mail/template/proj/RejectDeptMail.html";
    /**
     * 如果部门新增申请被确认,则将部门申请表中的该条记录复制到部门正式表中,将与此记录相关的人员信息从人员申请表复制到人员正式表中,
     * 将状态设置为确认,并从部门申请表中删除这条确认的记录,删除人员申请表的相关信息
     * 如果部门新增申请被拒绝,则将状态设置为被拒绝
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {       
    		DtoUser user = (DtoUser)request.getSession().getAttribute(DtoUser.SESSION_USER);
            // 如果有ActionForm传入的话，用此方法获得ActionFrom
            AfDeptApp af=(AfDeptApp) this.getForm();
            IAccountApplicationService logic = (IAccountApplicationService) this.getBean("AccountApplicationLogic");  
            AcntApp acntApp =logic.loadByRid(Long.valueOf(af.getRid()));     
            Acnt acnt=new Acnt();
            boolean isConfirm = false;
            AcntPersonApp personApp = logic.loadByRidFromPersonApp(acntApp.getRid(), "DeptManager");
            af.setDMDomain(personApp.getDomain());
            IAccountService logicAcnt = (IAccountService) this.getBean("AccountLogic");  
            if(!acntApp.getApplicationStatus().equals(af.getApplicationStatus())){
               isConfirm = true;          
               acnt.setIsAcnt("0");  
               acnt.setIsEnable("1");
               acnt.setAcntName(acntApp.getAcntName());            
               acnt.setAcntName(acntApp.getAcntName()); 
               acnt.setAcntFullName(acntApp.getAcntFullName());
               acnt.setAcntAttribute(acntApp.getAcntAttribute());
               acnt.setAchieveBelong(acntApp.getAchieveBelong());  
               acnt.setAcntId(acntApp.getAcntId());
               acnt.setAcntStatus(server.essp.projectpre.service.constant.Constant.CONFIRMED);   
               acnt.setRelParentId(acntApp.getRelParentId());
               logicAcnt.save(acnt);
               //根据选择的相关人员向Person中插入数据
               addPerson(af,acnt.getRid());
               logic.delete(acntApp.getRid());
               logic.deleteAcntPersonApp(acntApp.getRid());
               
               //数据同步（TimeSheet，财务系统）
               SyncService syncLogic = (SyncService) this.getBean("SyncService");
               IAccountService  acntLogic = (IAccountService) this.getBean("AccountLogic");
               Map map = new HashMap();
               map.put("projId", acnt.getAcntId());
               map.put("achieveBelong", acnt.getAchieveBelong());
               map.put("projName", acnt.getAcntFullName());
               map.put("nickName", acnt.getAcntName());
               map.put("deptFlag", "1");
               map.put("AccountTypeName", "");
               AcntPerson person = acntLogic.loadByRidFromPerson(acnt.getRid(), "DeptManager");
               map.put("projectManager", person.getLoginId());
               map.put("ManagerName", person.getName());
               person = acntLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_BD_MANAGER);
               map.put("divisionManager", person.getLoginId());
               person = acntLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_TC_SIGNER);
               map.put("tcSigner", person.getLoginId());
               map.put("projectProperty", acnt.getAcntAttribute());
               map.put("closeMark", "N");
               map.put("masterProjects", acnt.getRelParentId());
               map.put("AchieveBelong", acnt.getAchieveBelong());
               syncLogic.invokeAdd(map);
               
          }else{
               acntApp.setApplicationStatus(Constant.REJECTED);
               acntApp.setRemark(af.getRemark());
               logic.update(acntApp);     
          }
            
            AfDeptApp contend = new AfDeptApp();
            DtoUtil.copyBeanToBean(contend,af);//拷贝内容到新的对象保证邮件内容不发生错误
            ISendMailService sendLogic = (ISendMailService)this.getBean("SendMailLogic");
            //通过时给部门主管和申请人发送邮件
            //拒绝时给申请人和执行者发送邮件
            if(isConfirm){
            	sendLogic.sendMail(af.getDMLoginId(),af.getDMDomain(), vmFile11, "Dept Add Check", contend);
                sendLogic.sendMail(acntApp.getApplicantId(),acntApp.getDomain(), vmFile11, "Dept Add Check", contend);
            } else {
            	sendLogic.sendMail(acntApp.getApplicantId(),acntApp.getDomain(), vmFile12, "Dept Add Rejected", contend);
            	//给执行者发邮件
            	sendLogic.sendMail(user.getUserLoginId(), user.getDomain(), vmFile12, "Dept Add Rejected", contend);
            }
            
            
  }
    /**
     * 将人员申请表涉及到的记录复制到人员正式表中
     */
     private void addPerson(AfDeptApp af,Long rid) {
        IAccountService logic = (IAccountService)this.getBean("AccountLogic");
        
        AcntPerson acntPersonDM = new AcntPerson();       
        acntPersonDM.setPersonType("DeptManager");      
        acntPersonDM.setAcntRid(rid);
        acntPersonDM.setName(af.getDeptManager());
        acntPersonDM.setLoginId(af.getDMLoginId());
        acntPersonDM.setDomain(af.getDMDomain());
        logic.saveAcntPerson(acntPersonDM);
        
        AcntPerson acntPersonTC = new AcntPerson();   
        acntPersonTC.setPersonType(IDtoAccount.USER_TYPE_TC_SIGNER);        
        acntPersonTC.setAcntRid(rid);
        acntPersonTC.setName(af.getTCSName());
        acntPersonTC.setLoginId(af.getTCSLoginId());
        logic.saveAcntPerson(acntPersonTC);
        
        AcntPerson acntPersonBD = new AcntPerson();   
        acntPersonBD.setPersonType(IDtoAccount.USER_TYPE_BD_MANAGER);
        acntPersonBD.setAcntRid(rid);
        acntPersonBD.setName(af.getBDMName());
        acntPersonBD.setLoginId(af.getBDLoginId());
        logic.saveAcntPerson(acntPersonBD);
        
        AcntPerson acntPerson = new AcntPerson();   
        acntPerson.setPersonType(IDtoAccount.USER_TYPE_APPLICANT);     
        acntPerson.setAcntRid(rid);
        acntPerson.setName(af.getApplicantName());
        acntPerson.setLoginId(af.getApplicantId());
        logic.saveAcntPerson(acntPerson);
    }
   
}
