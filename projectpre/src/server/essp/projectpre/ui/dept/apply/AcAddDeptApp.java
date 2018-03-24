package server.essp.projectpre.ui.dept.apply;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.AcntApp;
import server.essp.projectpre.db.AcntPersonApp;
import server.essp.projectpre.service.account.IAccountService;
import server.essp.projectpre.service.accountapplication.IAccountApplicationService;
import server.essp.projectpre.service.constant.Constant;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
import c2s.essp.common.account.IDtoAccount;
import c2s.essp.common.user.DtoUser;

public class AcAddDeptApp extends AbstractESSPAction{
    /**
     * 部门新增申请,如果填写的部门代号已经存在则会报错,如果新增成功向部门申请表中添加一条记录,
     * 并向人员申请表中添加相关信息
     */
    public void executeAct(HttpServletRequest request, HttpServletResponse response, 
                           TransactionData data) throws BusinessException {        
              AfDeptApp af = (AfDeptApp) this.getForm();
              AcntApp acntApp = new AcntApp();
              IAccountApplicationService logic = (IAccountApplicationService) this.getBean("AccountApplicationLogic");
              IAccountService logicAcnt = (IAccountService) this.getBean("AccountLogic");
              //从Session中得到当前登陆者姓名和LoginId
              DtoUser user = (DtoUser)request.getSession().getAttribute(DtoUser.SESSION_USER);
              String userLoginId = user.getUserLoginId();
              String userName=user.getUserName();
              String userDomain = user.getDomain();
              if(af.getApplicationStatus().trim().equals("Submitted")){
                  if(logicAcnt.loadByAcntId((af.getAcntId().trim()), "0")!=null||logic.loadByAcntIdAndStatus((af.getAcntId().trim()), "0")!=null){
                    throw new BusinessException("error.logic.deptServiceImpl.deptIdduplicate");                  
                 }else{
                     acntApp.setAcntId(af.getAcntId().trim());
                  }
               }else{
                acntApp.setAcntId(af.getAcntId().trim());
               }
              acntApp.setApplicantId(userLoginId);
              acntApp.setApplicantName(userName);
              acntApp.setDomain(userDomain);
              acntApp.setIsAcnt("0");
              acntApp.setApplicationType(Constant.DEPARTMENTADDAPP);       
              acntApp.setApplicationDate(new Date());
              acntApp.setRid(Long.valueOf(af.getRid()));         
              acntApp.setAcntName(af.getAcntName().trim());
              acntApp.setAcntFullName(af.getAcntFullName().trim());
              acntApp.setAchieveBelong(af.getAchieveBelong().trim());
              acntApp.setApplicationStatus(af.getApplicationStatus().trim());
              acntApp.setAcntAttribute(IDtoAccount.ACCOUNT_ATTRIBUTE_DEPT);                       
              acntApp.setRelParentId(af.getParentDept().substring(0, af.getParentDept().indexOf("---")));
              //根据选择的相关人员向Person_App中插入数据
              addPersonApp(logic,af,Long.valueOf(af.getRid()));
              logic.save(acntApp);
              
    }
    /**
     * 将相关人员加入到Person_App中
     * @param logic 
     * @param af 
     * @param rid 
     * @return 含人员姓名的af
     */
    private void addPersonApp(IAccountApplicationService logic,AfDeptApp af,Long rid) {
        AcntPersonApp acntPersonApp = new AcntPersonApp();       
        acntPersonApp.setPersonType("DeptManager");      
        acntPersonApp.setAcntAppRid(rid);
        acntPersonApp.setName(af.getDeptManager());
        acntPersonApp.setLoginId(af.getDMLoginId());
        acntPersonApp.setDomain(af.getDMDomain());
        logic.saveAcntPersonApp(acntPersonApp);
        
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
