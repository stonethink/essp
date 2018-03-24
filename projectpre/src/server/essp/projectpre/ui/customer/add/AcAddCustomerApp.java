package server.essp.projectpre.ui.customer.add;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.CustomerApplication;
import server.essp.projectpre.service.customerapplication.ICustomerApplication;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
import c2s.essp.common.user.DtoUser;

public class AcAddCustomerApp  extends AbstractESSPAction {

    /**
     * 新增客户资料申请，在页面上获取信息，生成申请状态为保存或提交的数据
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {

        // 如果有ActionForm传入的话，用此方法获得ActionFrom
            AfCustomerApplication af = (AfCustomerApplication) this.getForm();
            CustomerApplication customerApplication = new CustomerApplication();
            ICustomerApplication logic = (ICustomerApplication) this.getBean("CustomerApplicationLogic");  
            customerApplication.setApplicationType("CustomerAddApp");
            customerApplication.setAttribute(af.getAttribute().trim());
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
            customerApplication.setApplicationDate(new Date()); 
            customerApplication.setApplicationStatus(af.getApplicationStatus());
            customerApplication.setDescription("");
            //如果选择公司码且所属集团码不为空，则获取集团码代号（去除集团码名称） 
            if(customerApplication.getAttribute().equals("Company")&&af.getGroupId()!=null&&!af.getGroupId().equals("")){
                customerApplication.setGroupId(af.getGroupId().substring(0, af.getGroupId().indexOf("---")));
             }else{
                customerApplication.setGroupId("");
             }
            //从Session中获得当前登陆者的Name和LoginId    
            DtoUser user = (DtoUser)request.getSession().getAttribute(DtoUser.SESSION_USER);
            String userLoginId = user.getUserLoginId();
            String userName=user.getUserName();
            customerApplication.setApplicantId(userLoginId);
            customerApplication.setApplicantName(userName);
            logic.save(customerApplication);
                  
    }

}
