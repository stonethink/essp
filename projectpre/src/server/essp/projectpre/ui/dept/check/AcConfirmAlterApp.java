package server.essp.projectpre.ui.dept.check;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wits.util.comDate;

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
import c2s.dto.TransactionData;
import c2s.essp.common.account.IDtoAccount;
import c2s.essp.common.user.DtoUser;

public class AcConfirmAlterApp  extends AbstractESSPAction {
	public static final String vmFile11 = "mail/template/proj/CheckDeptChangeMail.html"; 
	public static final String vmFile12 = "mail/template/proj/RejectDeptChangeMail.html";
    /**
     *  如果部门变更申请被确认,则将部门申请表中的该条记录信息更新到部门正式表中,将与此记录相关的人员信息更新到人员正式表中,
     * 将状态设置为确认,并从部门申请表中删除这条确认的记录,删除人员申请表的相关信息
     * 如果部门新增申请被拒绝,则将状态设置为被拒绝
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {       
    		DtoUser user = (DtoUser)request.getSession().getAttribute(DtoUser.SESSION_USER);
            // 如果有ActionForm传入的话，用此方法获得ActionFrom
            AfDeptApp af = (AfDeptApp) this.getForm();
            IAccountApplicationService logic = (IAccountApplicationService) this.getBean("AccountApplicationLogic");  
            AcntApp acntApp =logic.loadByRid(Long.valueOf(af.getRid()));     
            Acnt acnt=new Acnt();
            IAccountService logicAcnt = (IAccountService) this.getBean("AccountLogic"); 
            acnt = logicAcnt.loadByRid(Long.valueOf(af.getRid())); 
            //准备邮件内容
            VbDeptChange vb = new VbDeptChange();
            prepareMail(vb, acntApp, acnt, Long.valueOf(af.getRid()));
            AcntPersonApp acntPersonApp = new AcntPersonApp();       
            boolean isConfirm = false;
            if(!acntApp.getApplicationStatus().equals(af.getApplicationStatus())){
             isConfirm = true;
             acnt.setAcntName(acntApp.getAcntName());
             acnt.setAcntFullName(acntApp.getAcntFullName());
             acnt.setAchieveBelong(acntApp.getAchieveBelong());
             acnt.setApplicationDate(acntApp.getApplicationDate());
             acnt.setIsEnable(acntApp.getIsEnable());
             acnt.setRelParentId(acntApp.getRelParentId());
             //更新acntPerson表
             updatePerson(acntPersonApp,af,logicAcnt); 
             logic.deleteAcntPersonApp(acntApp.getRid());
             logicAcnt.update(acnt);     
             logic.delete(Long.valueOf(af.getRid()));
             
             //数据同步（TimeSheet，暂时没有同步到财务系统，确认后再做修改）
             SyncService syncLogic = (SyncService) this.getBean("SyncService");
             Map map = new HashMap();
             map.put("projId", acnt.getAcntId());
             map.put("achieveBelong", acnt.getAchieveBelong());
             map.put("projName", acnt.getAcntFullName());
             map.put("nickName", acnt.getAcntName());
             map.put("masterProjects", acnt.getRelParentId());
             map.put("deptFlag", "1");
             map.put("AccountTypeName", "");
             AcntPerson person = logicAcnt.loadByRidFromPerson(acnt.getRid(), "DeptManager");
             map.put("projectManager", person.getLoginId());
             map.put("ManagerName", person.getName());
             person = logicAcnt.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_BD_MANAGER);
             map.put("divisionManager", person.getLoginId());
             person = logicAcnt.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_TC_SIGNER);
             map.put("tcSigner", person.getLoginId());
             map.put("AchieveBelong", acnt.getAchieveBelong());
             if("1".equals(acnt.getIsEnable())){
                 map.put("status", "N");
             } else {
                 map.put("status", "Y");
             }
             syncLogic.invokeUpdate(map);
             
            }else{
               acntApp.setApplicationStatus(Constant.REJECTED);
               acntApp.setRemark(af.getRemark());
               logic.update(acntApp);  
            }  
            //发送邮件   
            ISendMailService sendLogic = (ISendMailService)this.getBean("SendMailLogic");
            //通过时给部门主管和申请人发送邮件
            //拒绝时给申请人和执行者发送邮件
            if(isConfirm){
            	AcntPerson person = logicAcnt.loadByRidFromPerson(acnt.getRid(), "DeptManager");
            	sendLogic.sendMail(person.getLoginId(),person.getDomain(), vmFile11, "Dept Change Check", vb);
                sendLogic.sendMail(acntApp.getApplicantId(),acntApp.getDomain(), vmFile11, "Dept Change Check", vb);
            } else {
            	vb.setRemark(af.getRemark());
            	sendLogic.sendMail(acntApp.getApplicantId(),acntApp.getDomain(), vmFile12, "Dept Change Rejected", vb);
            	//给执行者发邮件
            	sendLogic.sendMail(user.getUserLoginId(), user.getDomain(), vmFile12, "Dept Change Rejected", vb);
            }
            
    }
    /**
     * 准备邮件内容
     * @param vb
     * @param acntApp
     * @param acnt
     * @param code
     */
    private void prepareMail(VbDeptChange vb, AcntApp acntApp, Acnt acnt, Long code){
    	IAccountApplicationService logic = (IAccountApplicationService) this.getBean("AccountApplicationLogic");
    	IAccountService logicAcnt = (IAccountService) this.getBean("AccountLogic"); 
    	vb.setAcntIdBefore(acnt.getAcntId());
    	vb.setAcntId(acntApp.getAcntId());
    	
    	vb.setAcntNameBefore(acnt.getAcntName());
    	vb.setAcntName(acntApp.getAcntName());
    	
    	vb.setAcntFullNameBefore(acnt.getAcntFullName());
    	vb.setAcntFullName(acntApp.getAcntFullName());
    	
    	vb.setAchieveBelongBefore(acnt.getAchieveBelong());
    	vb.setAchieveBelong(acntApp.getAchieveBelong());
    	
    	Acnt acntNest = null;
    	String parentBefore = "";
    	String parentAfter = "";
    	if(acnt.getRelParentId() != null && !"".equals(acnt.getRelParentId())) {
        	acntNest = logicAcnt.loadByAcntId(acnt.getRelParentId(), "0");
        	parentBefore = acnt.getRelParentId()+"---"+acntNest.getAcntName();
        }
        if(acntApp.getRelParentId() != null && !"".equals(acntApp.getRelParentId())) {
        	acntNest = logicAcnt.loadByAcntId(acntApp.getRelParentId(), "0");
        	parentAfter = acntApp.getRelParentId()+"---"+acntNest.getAcntName();
        }
    	vb.setParentDeptBefore(parentBefore);
    	vb.setParentDept(parentAfter);
    	
    	AcntPerson acntPerson = logicAcnt.loadByRidFromPerson(code, "DeptManager");
        AcntPersonApp acntPersonApp = logic.loadByRidFromPersonApp(code, "DeptManager");
        vb.setDeptManagerBefore(acntPerson.getName());
        vb.setDeptManager(acntPersonApp.getName());
        
        acntPerson = logicAcnt.loadByRidFromPerson(code, IDtoAccount.USER_TYPE_BD_MANAGER);
        acntPersonApp = logic.loadByRidFromPersonApp(code, IDtoAccount.USER_TYPE_BD_MANAGER);
        vb.setBdNameBefore(acntPerson.getName());
        vb.setBdName(acntPersonApp.getName());
        
        acntPerson = logicAcnt.loadByRidFromPerson(code, IDtoAccount.USER_TYPE_TC_SIGNER);
        acntPersonApp = logic.loadByRidFromPersonApp(code, IDtoAccount.USER_TYPE_TC_SIGNER);
        vb.setTcsNameBefore(acntPerson.getName());
        vb.setTcsName(acntPersonApp.getName());
        
        vb.setApplicantName(acntApp.getApplicantName());
        vb.setApplicationDate(comDate.dateToString(acntApp.getApplicationDate(),"yyyy-MM-dd"));
        
        vb.setIsEnableBefore(acnt.getIsEnable());
        vb.setIsEnable(acntApp.getIsEnable());
    }
    /**
     * 更新acntPerson表
     * @param acntPersonApp
     * @param af
     * @param logicAcnt
     */
     private void updatePerson(AcntPersonApp acntPersonApp,AfDeptApp af,IAccountService logicAcnt) {
         IAccountApplicationService logic = (IAccountApplicationService) this.getBean("AccountApplicationLogic"); 
         AcntPerson acntPerson = new AcntPerson();
       
         acntPersonApp = logic.loadByRidFromPersonApp(Long.valueOf(af.getRid()), "DeptManager");
         acntPerson = logicAcnt.loadByRidFromPerson(Long.valueOf(af.getRid()), "DeptManager");
         acntPerson.setName(acntPersonApp.getName()); 
         acntPerson.setLoginId(acntPersonApp.getLoginId());
         logicAcnt.updateAcntPerson(acntPerson);
     
         acntPersonApp = logic.loadByRidFromPersonApp(Long.valueOf(af.getRid()), IDtoAccount.USER_TYPE_TC_SIGNER);
         acntPerson = logicAcnt.loadByRidFromPerson(Long.valueOf(af.getRid()), IDtoAccount.USER_TYPE_TC_SIGNER);
         acntPerson.setName(acntPersonApp.getName());
         acntPerson.setLoginId(acntPersonApp.getLoginId());
         logicAcnt.updateAcntPerson(acntPerson);
         
         acntPersonApp = logic.loadByRidFromPersonApp(Long.valueOf(af.getRid()), IDtoAccount.USER_TYPE_BD_MANAGER);
         acntPerson = logicAcnt.loadByRidFromPerson(Long.valueOf(af.getRid()), IDtoAccount.USER_TYPE_BD_MANAGER);
         acntPerson.setName(acntPersonApp.getName());
         acntPerson.setLoginId(acntPersonApp.getLoginId());
         logicAcnt.updateAcntPerson(acntPerson);
                           
     }
}

