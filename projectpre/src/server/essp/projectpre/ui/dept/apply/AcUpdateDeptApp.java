package server.essp.projectpre.ui.dept.apply;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.AcntApp;
import server.essp.projectpre.db.AcntPersonApp;
import server.essp.projectpre.service.account.IAccountService;
import server.essp.projectpre.service.accountapplication.IAccountApplicationService;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import c2s.dto.TransactionData;
import c2s.essp.common.account.IDtoAccount;
public class AcUpdateDeptApp extends AbstractESSPAction{
    /**
     * 修改部门新增申请
     */     
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {
        AfDeptApp af = (AfDeptApp) this.getForm();
        IAccountApplicationService logic = (IAccountApplicationService) this.getBean("AccountApplicationLogic");
        IAccountService logicAcnt = (IAccountService) this.getBean("AccountLogic");
        AcntApp acntApp = logic.loadByRid(Long.valueOf(af.getRid()));
        if(af.getApplicationStatus().trim().equals("Submitted")){
           if(logicAcnt.loadByAcntId((af.getAcntId().trim()), "0")!=null||logic.loadByAcntIdAndStatus((af.getAcntId().trim()), "0")!=null){
               throw new BusinessException("error.logic.deptServiceImpl.deptIdduplicate");                  
          }else{
              acntApp.setAcntId(af.getAcntId().trim());
           }
        }else{
         acntApp.setAcntId(af.getAcntId().trim());
        }
        acntApp.setApplicationStatus(af.getApplicationStatus().trim());
        acntApp.setAcntName(af.getAcntName().trim());
        acntApp.setAcntFullName(af.getAcntFullName().trim());
        acntApp.setAchieveBelong(af.getAchieveBelong().trim());
        acntApp.setRelParentId(af.getParentDept().substring(0, af.getParentDept().indexOf("---")));
       //更新person表中的记录
        updatePersonApp(af,logic,Long.valueOf(af.getRid()));
        logic.update(acntApp);
        request.setAttribute(Constant.VIEW_BEAN_KEY,acntApp);
        request.getSession().setAttribute("IsRollBack","false");
        
    }
    /**
     * 更新人员申请表中涉及到的人员
     * @param af
     * @param logic
     * @param rid
     */
    private void  updatePersonApp(AfDeptApp af,IAccountApplicationService logic,Long rid){
        AcntPersonApp acntPersonAppDM = new AcntPersonApp();
        acntPersonAppDM = logic.loadByRidFromPersonApp(Long.valueOf(af.getRid()), "DeptManager");       
        acntPersonAppDM.setName(af.getDeptManager()); 
        acntPersonAppDM.setLoginId(af.getDMLoginId());
        acntPersonAppDM.setDomain(af.getDMDomain());
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