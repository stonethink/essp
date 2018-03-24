package server.essp.projectpre.ui.dept.change;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.AcntApp;
import server.essp.projectpre.db.AcntPersonApp;
import server.essp.projectpre.service.accountapplication.IAccountApplicationService;
import server.essp.projectpre.ui.dept.apply.AfDeptApp;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import c2s.dto.TransactionData;
import c2s.essp.common.account.IDtoAccount;

public class AcUpdateDeptApp extends AbstractESSPAction{
    /**
     * 修改部门变更申请
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {
        AfDeptApp af = (AfDeptApp) this.getForm();
        IAccountApplicationService logic = (IAccountApplicationService) this.getBean("AccountApplicationLogic");
        AcntApp acntApp = logic.loadByRid(Long.valueOf(af.getRid().trim()));
        acntApp.setApplicationStatus(af.getApplicationStatus().trim());
        acntApp.setAcntName(af.getAcntName().trim());    
        acntApp.setAcntFullName(af.getAcntFullName().trim());
        acntApp.setAchieveBelong(af.getAchieveBelong().trim());
        acntApp.setRelParentId(af.getParentDept().substring(0, af.getParentDept().indexOf("---")));
        if(af.getStatus()!=null&&af.getStatus().equals("1")) {
            acntApp.setIsEnable("1");
        }else {
            acntApp.setIsEnable("0");
        }
//        更新person表中的记录
        updatePersonApp(af,logic,Long.valueOf(af.getRid()));   
        logic.update(acntApp);
        request.setAttribute(Constant.VIEW_BEAN_KEY,acntApp);
        
    }
   /**
    * 更新人员表中与修改记录相关的人员信息
    * @param af
    * @param logic
    * @param rid
    */
    private void  updatePersonApp(AfDeptApp af,IAccountApplicationService logic,Long rid){
        AcntPersonApp acntPersonAppDM = new AcntPersonApp();
        acntPersonAppDM = logic.loadByRidFromPersonApp(Long.valueOf(af.getRid()), "DeptManager");       
        acntPersonAppDM.setName(af.getDeptManager()); 
        acntPersonAppDM.setLoginId(af.getDMLoginId());
        logic.updateAcntPersonApp(acntPersonAppDM);
        
        AcntPersonApp acntPersonAppTC = new AcntPersonApp();
        acntPersonAppTC = logic.loadByRidFromPersonApp(Long.valueOf(af.getRid()), IDtoAccount.USER_TYPE_TC_SIGNER);      
        acntPersonAppTC.setName(af.getTCSName());
        acntPersonAppTC.setLoginId(af.getTCSLoginId());
        logic.updateAcntPersonApp(acntPersonAppTC);
      
        AcntPersonApp acntPersonAppBD = new AcntPersonApp();
        acntPersonAppBD = logic.loadByRidFromPersonApp(Long.valueOf(af.getRid()), IDtoAccount.USER_TYPE_BD_MANAGER);      
        acntPersonAppBD.setName(af.getBDMName());
        acntPersonAppBD.setLoginId(af.getBDLoginId());
        logic.updateAcntPersonApp(acntPersonAppBD);
        
    }
}