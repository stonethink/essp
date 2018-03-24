package server.essp.projectpre.ui.customer.change;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Customer;
import server.essp.projectpre.db.CustomerApplication;
import server.essp.projectpre.service.customer.ICustomerService;
import server.essp.projectpre.service.customerapplication.ICustomerApplication;
import server.essp.projectpre.ui.customer.add.AfCustomerApplication;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
import c2s.essp.common.user.DtoUser;

import com.wits.util.comDate;

public class AcAddCustApp extends AbstractESSPAction {

    /**
     * 在页面上获取资料新增客户变更申请
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {
          // 如果有ActionForm传入的话，用此方法获得ActionFrom
           AfCustomerApplication af = (AfCustomerApplication) this.getForm();
           ICustomerService logic0 = (ICustomerService) this.getBean("CustomerLogic");       
           Customer customer = new Customer();
           customer = logic0.loadByCustomerId(af.getCustomerId());
           CustomerApplication customerApplication = new CustomerApplication();               
           ICustomerApplication logic = (ICustomerApplication) this.getBean("CustomerApplicationLogic");  
           //从Session获得当前登陆者的Name和LoginId
           DtoUser user = (DtoUser)request.getSession().getAttribute(DtoUser.SESSION_USER);       
           String userLoginId = user.getUserLoginId();
           String userName=user.getUserName();
          
            customerApplication.setApplicantId(userLoginId);
            customerApplication.setApplicantName(userName);         
            customerApplication.setCustomerRid(String.valueOf(customer.getRid()));
            customerApplication.setAttribute(customer.getAttribute());
            customerApplication.setApplicationType("CustomerChangeApp");        
            customerApplication.setRid(Long.valueOf(af.getRid()));
            customerApplication.setRegId(af.getRegId().trim());
            customerApplication.setCustomerShort(af.getCustomerShort().trim());
            customerApplication.setCustomerNameCn(af.getCustomerNameCn().trim());    
            customerApplication.setCustomerNameEn(af.getCustomerNameEn().trim());
            customerApplication.setBelongBd(af.getBelongBd().trim());
            customerApplication.setBelongSite(af.getBelongSite().trim()); 
            customerApplication.setCustomerClass(af.getCustomerClass().trim());   
            customerApplication.setCustomerCountry(af.getCustomerCountry().trim());
            customerApplication.setCustDescription(af.getCustDescription().trim());
            //如果是客户码且集团编号不为空，则获得集团码
            if(customerApplication.getAttribute().equals("Company")){
               if(customerApplication.getAttribute().equals("Company")&&af.getGroupId()!=null&&!af.getGroupId().equals("")){
                customerApplication.setGroupId(af.getGroupId().substring(0, af.getGroupId().indexOf("---")));
                }
             }else{
                customerApplication.setGroupId("");
             }
            customerApplication.setApplicationDate(java.sql.Date.valueOf(comDate.dateToString(new Date(), "yyyy-MM-dd")));
            customerApplication.setCustomerId(af.getCustomerId().trim());       
            customerApplication.setApplicationStatus(af.getApplicationStatus());
            customerApplication.setDescription("");
            logic.save(customerApplication);              
    }
}
