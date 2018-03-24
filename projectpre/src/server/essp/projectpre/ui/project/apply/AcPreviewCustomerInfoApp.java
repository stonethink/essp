package server.essp.projectpre.ui.project.apply;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Acnt;
import server.essp.projectpre.db.AcntApp;
import server.essp.projectpre.db.AcntCustContactor;
import server.essp.projectpre.db.AcntCustContactorApp;
import server.essp.projectpre.db.AcntPerson;
import server.essp.projectpre.db.AcntPersonApp;
import server.essp.projectpre.db.Customer;
import server.essp.projectpre.service.account.IAccountService;
import server.essp.projectpre.service.accountapplication.IAccountApplicationService;
import server.essp.projectpre.service.customer.ICustomerService;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import c2s.dto.TransactionData;
import c2s.essp.common.account.IDtoAccount;

/**
 * 预览修改申请客户资料卡片的Action
 * @author wenhaizheng
 *
 */
public class AcPreviewCustomerInfoApp extends AbstractESSPAction {
     
    private final static String RID="rid";
    /**
     * 根据不同情况初始化专案新增申请的客户资料卡片页面
     *    1.取得复制专案功能的标志位的值
     *    2.判断是否为变更选择专案代码并显示该专案相关信息的功能
     *    3.取得相应的客户资料
     *    4.将ViewBean设置到前台
     * @param request
     * @param response
     * @param data
     * @throws BusinessException
     */
    public void executeAct(HttpServletRequest request, HttpServletResponse response, 
                  TransactionData data) throws BusinessException {   
        Long rid=null;
        Long changeRid = null;
        //取得复制专案功能的标志位的值
        String selectStatus = null;
        if(request.getParameter("selectAcnt")!=null){
           selectStatus = request.getParameter("selectAcnt");
        }
        if (request.getParameter(RID) != null) {
            rid = Long.valueOf(request.getParameter(RID));
        }
        VbCustomerInfo viewBean = new VbCustomerInfo();
        //判断是否为变更选择专案代码并显示该专案相关信息的功能
        if(request.getParameter("view")!=null){
            String str = request.getParameter("view");
            String acntId = str.substring(0, str.indexOf("---"));
            IAccountService acntLogic = (IAccountService) this.getBean("AccountLogic");
            Acnt acnt = acntLogic.loadByAcntId(acntId, "1");
            changeRid =acnt.getRid();
            ICustomerService custLogic = (ICustomerService)this.getBean("CustomerLogic");
            Customer customer = custLogic.loadByCustomerId(acnt.getCustomerId());
            if(customer!=null&&customer.getShort_()!=null){
                viewBean.setCustomerId(customer.getShort_());
            } else {
                viewBean.setCustomerId("");
            }
            AcntPerson person = acntLogic.loadByRidFromPerson(changeRid, IDtoAccount.USER_TYPE_CUST_SERVICE_MANAGER);
            viewBean.setCustServiceManager(person.getName());
            viewBean.setCustServiceManagerId(person.getLoginId());
            viewBean.setCustServiceManagerDomain(person.getDomain());
            person = acntLogic.loadByRidFromPerson(changeRid, IDtoAccount.USER_TYPE_ENGAGE_MANAGER);
            viewBean.setEngageManager(person.getName());
            viewBean.setEngageManagerId(person.getLoginId());
            viewBean.setEngageManagerDomain(person.getDomain());
            
            
            IAccountService logic = (IAccountService) this.getBean("AccountLogic");
            AcntCustContactor acntCustContactor = logic.loadByRidTypeFromAcntCustContactor(changeRid, IDtoAccount.CUSTOMER_CONTACTOR_CONTRACT);
            if(acntCustContactor!=null) {
            viewBean.setContract(acntCustContactor.getName());
            viewBean.setContractTel(acntCustContactor.getTelephone());
            viewBean.setContractEmail(acntCustContactor.getEmail());
            }
            acntCustContactor =logic.loadByRidTypeFromAcntCustContactor(changeRid, IDtoAccount.CUSTOMER_CONTACTOR_EXE);
            if(acntCustContactor!=null) {
            viewBean.setExecutive(acntCustContactor.getName());
            viewBean.setExecutiveTel(acntCustContactor.getTelephone());
            viewBean.setExecutiveEmail(acntCustContactor.getEmail());
            }
            acntCustContactor =logic.loadByRidTypeFromAcntCustContactor(changeRid, IDtoAccount.CUSTOMER_CONTACTOR_FINANCIAL);
            if(acntCustContactor!=null) {
            viewBean.setFinancial(acntCustContactor.getName());
            viewBean.setFinancialTel(acntCustContactor.getTelephone());
            viewBean.setFinancialEmail(acntCustContactor.getEmail());
            }
        } else if(selectStatus!=null){
            IAccountService acntLogic = (IAccountService) this.getBean("AccountLogic");
            Acnt acnt = acntLogic.loadByRid(rid);
            ICustomerService custLogic = (ICustomerService)this.getBean("CustomerLogic");
            Customer customer = custLogic.loadByCustomerId(acnt.getCustomerId());
            if(customer!=null&&customer.getShort_()!=null){
                viewBean.setCustomerId(customer.getShort_());
            } else {
                viewBean.setCustomerId("");
            }
            AcntPerson person = acntLogic.loadByRidFromPerson(rid, IDtoAccount.USER_TYPE_CUST_SERVICE_MANAGER);
            viewBean.setCustServiceManager(person.getName());
            viewBean.setCustServiceManagerId(person.getLoginId());
            viewBean.setCustServiceManagerDomain(person.getDomain());
            person = acntLogic.loadByRidFromPerson(rid, IDtoAccount.USER_TYPE_ENGAGE_MANAGER);
            viewBean.setEngageManager(person.getName());
            viewBean.setEngageManagerId(person.getLoginId());
            viewBean.setEngageManagerDomain(person.getDomain());
            
        
            IAccountService logic = (IAccountService) this.getBean("AccountLogic");
            AcntCustContactor acntCustContactor = logic.loadByRidTypeFromAcntCustContactor(rid, IDtoAccount.CUSTOMER_CONTACTOR_CONTRACT);
            if(acntCustContactor!=null) {
            viewBean.setContract(acntCustContactor.getName());
            viewBean.setContractTel(acntCustContactor.getTelephone());
            viewBean.setContractEmail(acntCustContactor.getEmail());
            }
            acntCustContactor =logic.loadByRidTypeFromAcntCustContactor(rid, IDtoAccount.CUSTOMER_CONTACTOR_EXE);
            if(acntCustContactor!=null) {
            viewBean.setExecutive(acntCustContactor.getName());
            viewBean.setExecutiveTel(acntCustContactor.getTelephone());
            viewBean.setExecutiveEmail(acntCustContactor.getEmail());
            }
            acntCustContactor =logic.loadByRidTypeFromAcntCustContactor(rid, IDtoAccount.CUSTOMER_CONTACTOR_FINANCIAL);
            if(acntCustContactor!=null) {
            viewBean.setFinancial(acntCustContactor.getName());
            viewBean.setFinancialTel(acntCustContactor.getTelephone());
            viewBean.setFinancialEmail(acntCustContactor.getEmail());
            }
       } else {
           IAccountApplicationService logic = (IAccountApplicationService) this.getBean("AccountApplicationLogic");    
           AcntApp acntApp = logic.loadByRid(rid);
           viewBean.setCustomerId(acntApp.getCustomerId());  
           AcntPersonApp personApp = logic.loadByRidFromPersonApp(rid, IDtoAccount.USER_TYPE_CUST_SERVICE_MANAGER);
           viewBean.setCustServiceManager(personApp.getName());
           viewBean.setCustServiceManagerId(personApp.getLoginId());
           viewBean.setCustServiceManagerDomain(personApp.getDomain());
           personApp = logic.loadByRidFromPersonApp(rid, IDtoAccount.USER_TYPE_ENGAGE_MANAGER);
           viewBean.setEngageManager(personApp.getName());
           viewBean.setEngageManagerId(personApp.getLoginId());
           viewBean.setEngageManagerDomain(personApp.getDomain());
       
          AcntCustContactorApp acntCustContactorApp = logic.loadByRidTypeFromAcntCustContactorApp(rid, IDtoAccount.CUSTOMER_CONTACTOR_CONTRACT); 
          if(acntCustContactorApp!=null) {
              viewBean.setContract(acntCustContactorApp.getName());
              viewBean.setContractTel(acntCustContactorApp.getTelephone());
              viewBean.setContractEmail(acntCustContactorApp.getEmail());
          }
          acntCustContactorApp =logic.loadByRidTypeFromAcntCustContactorApp(rid, IDtoAccount.CUSTOMER_CONTACTOR_EXE);
          if(acntCustContactorApp!=null) {
              viewBean.setExecutive(acntCustContactorApp.getName());
              viewBean.setExecutiveTel(acntCustContactorApp.getTelephone());
              viewBean.setExecutiveEmail(acntCustContactorApp.getEmail());
          }
          acntCustContactorApp =logic.loadByRidTypeFromAcntCustContactorApp(rid, IDtoAccount.CUSTOMER_CONTACTOR_FINANCIAL);
          if(acntCustContactorApp!=null) {
              viewBean.setFinancial(acntCustContactorApp.getName());
              viewBean.setFinancialTel(acntCustContactorApp.getTelephone());
              viewBean.setFinancialEmail(acntCustContactorApp.getEmail());
          }
       }
        
//      将ViewBean设置到前台
        request.setAttribute(Constant.VIEW_BEAN_KEY,viewBean);
    }

}
