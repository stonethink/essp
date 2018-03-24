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
     * ��������������뱻ȷ��,�򽫲���������еĸ�����¼���Ƶ�������ʽ����,����˼�¼��ص���Ա��Ϣ����Ա������Ƶ���Ա��ʽ����,
     * ��״̬����Ϊȷ��,���Ӳ����������ɾ������ȷ�ϵļ�¼,ɾ����Ա�����������Ϣ
     * ��������������뱻�ܾ�,��״̬����Ϊ���ܾ�
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {       
    		DtoUser user = (DtoUser)request.getSession().getAttribute(DtoUser.SESSION_USER);
            // �����ActionForm����Ļ����ô˷������ActionFrom
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
               //����ѡ��������Ա��Person�в�������
               addPerson(af,acnt.getRid());
               logic.delete(acntApp.getRid());
               logic.deleteAcntPersonApp(acntApp.getRid());
               
               //����ͬ����TimeSheet������ϵͳ��
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
            DtoUtil.copyBeanToBean(contend,af);//�������ݵ��µĶ���֤�ʼ����ݲ���������
            ISendMailService sendLogic = (ISendMailService)this.getBean("SendMailLogic");
            //ͨ��ʱ���������ܺ������˷����ʼ�
            //�ܾ�ʱ�������˺�ִ���߷����ʼ�
            if(isConfirm){
            	sendLogic.sendMail(af.getDMLoginId(),af.getDMDomain(), vmFile11, "Dept Add Check", contend);
                sendLogic.sendMail(acntApp.getApplicantId(),acntApp.getDomain(), vmFile11, "Dept Add Check", contend);
            } else {
            	sendLogic.sendMail(acntApp.getApplicantId(),acntApp.getDomain(), vmFile12, "Dept Add Rejected", contend);
            	//��ִ���߷��ʼ�
            	sendLogic.sendMail(user.getUserLoginId(), user.getDomain(), vmFile12, "Dept Add Rejected", contend);
            }
            
            
  }
    /**
     * ����Ա������漰���ļ�¼���Ƶ���Ա��ʽ����
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
