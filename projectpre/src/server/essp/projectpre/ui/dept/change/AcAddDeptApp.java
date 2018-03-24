package server.essp.projectpre.ui.dept.change;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Acnt;
import server.essp.projectpre.db.AcntApp;
import server.essp.projectpre.db.AcntPersonApp;
import server.essp.projectpre.service.account.IAccountService;
import server.essp.projectpre.service.accountapplication.IAccountApplicationService;
import server.essp.projectpre.service.constant.Constant;
import server.essp.projectpre.ui.dept.apply.AfDeptApp;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
import c2s.essp.common.account.IDtoAccount;
import c2s.essp.common.user.DtoUser;

public class AcAddDeptApp extends AbstractESSPAction{
    /**
     * 新增部门变更申请
     */
    public void executeAct(HttpServletRequest request, HttpServletResponse response, 
                           TransactionData data) throws BusinessException {
              DtoUser user = (DtoUser)request.getSession().getAttribute(DtoUser.SESSION_USER);
              String userLoginId = user.getUserLoginId();
              String userName=user.getUserName();
             
              AfDeptApp af = (AfDeptApp) this.getForm();
              IAccountService logic = (IAccountService)this.getBean("AccountLogic");
              Acnt acnt = new Acnt();
              acnt = logic.loadByAcntId(af.getAcntId(), "0");          
            
              AcntApp acntApp = new AcntApp();            
              acntApp.setApplicantId(userLoginId);
              acntApp.setApplicantName(userName);
              acntApp.setApplicationType(Constant.DEPARTMENTCHANGEAPP);  
              acntApp.setIsAcnt("0");
              acntApp.setRid(acnt.getRid());                 
              acntApp.setApplicationDate(new Date());
              acntApp.setAcntName(af.getAcntName().trim());
              acntApp.setAcntFullName(af.getAcntFullName().trim());
              acntApp.setAchieveBelong(af.getAchieveBelong().trim());
              acntApp.setAcntId(af.getAcntId().trim());
              acntApp.setApplicationStatus(af.getApplicationStatus().trim());
              acntApp.setRelParentId(af.getParentDept().substring(0, af.getParentDept().indexOf("---")));
              if(af.getStatus()!=null&&af.getStatus().equals("1")) {
                  acntApp.setIsEnable("1");
              }else {
                  acntApp.setIsEnable("0");
              }
              
              acntApp.setAcntAttribute(IDtoAccount.ACCOUNT_ATTRIBUTE_DEPT);          
              //根据选择的相关人员向Person_App中插入数据
              addPersonApp(af,acnt.getRid());
              
              //调用服务插入数据
              IAccountApplicationService logic1 = (IAccountApplicationService) this.getBean("AccountApplicationLogic");
              logic1.save(acntApp);
              
    }
    /**
     * 向Person_App中插入数据
     * @param af
     * @param rid
     */
    private void addPersonApp(AfDeptApp af,Long rid) {
        IAccountApplicationService logic = (IAccountApplicationService)this.getBean("AccountApplicationLogic");
        AcntPersonApp acntPersonAppDM = new AcntPersonApp();       
        acntPersonAppDM.setPersonType("DeptManager");      
        acntPersonAppDM.setAcntAppRid(rid);
        acntPersonAppDM.setName(af.getDeptManager());
        acntPersonAppDM.setLoginId(af.getDMLoginId());
        logic.saveAcntPersonApp(acntPersonAppDM);
        
        AcntPersonApp acntPersonAppTC = new AcntPersonApp();   
        acntPersonAppTC.setPersonType(IDtoAccount.USER_TYPE_TC_SIGNER);        
        acntPersonAppTC.setAcntAppRid(rid);
        acntPersonAppTC.setName(af.getTCSName());
        acntPersonAppTC.setLoginId(af.getTCSLoginId());
        logic.saveAcntPersonApp(acntPersonAppTC);
        
        AcntPersonApp acntPersonAppBD = new AcntPersonApp();   
        acntPersonAppBD.setPersonType(IDtoAccount.USER_TYPE_BD_MANAGER);
        acntPersonAppBD.setAcntAppRid(rid);
        acntPersonAppBD.setName(af.getBDMName());
        acntPersonAppBD.setLoginId(af.getBDLoginId());
        logic.saveAcntPersonApp(acntPersonAppBD);
   
    }
   
}
