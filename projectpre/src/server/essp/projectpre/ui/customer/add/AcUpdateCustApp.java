package server.essp.projectpre.ui.customer.add;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.CustomerApplication;
import server.essp.projectpre.service.customerapplication.ICustomerApplication;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;

public class AcUpdateCustApp extends AbstractESSPAction {

    /**
     * 根据页面上获取的信息修改资料
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {
            
            // 如果有ActionForm传入的话，用此方法获得ActionFrom
            AfCustomerApplication af=(AfCustomerApplication) this.getForm();
            ICustomerApplication logic = (ICustomerApplication) this.getBean("CustomerApplicationLogic");  
            CustomerApplication customerApplication =logic.loadByRid(Long.valueOf(af.getRid()));
            customerApplication.setApplicationStatus(af.getApplicationStatus());                      
            customerApplication.setAttribute(af.getAttribute().trim());
            customerApplication.setRegId(af.getRegId().trim());         
            customerApplication.setCustomerShort(af.getCustomerShort().trim());
            customerApplication.setCustomerNameCn(af.getCustomerNameCn().trim());
            customerApplication.setCustomerNameEn(af.getCustomerNameEn().trim());
            customerApplication.setBelongBd(af.getBelongBd().trim());
            customerApplication.setBelongSite(af.getBelongSite().trim());
            customerApplication.setCustomerClass(af.getCustomerClass().trim());
            customerApplication.setCustomerCountry(af.getCustomerCountry().trim());
            customerApplication.setCustDescription(af.getCustDescription().trim());
            if(customerApplication.getAttribute().equals("Company")&&af.getGroupId()!=null&&!af.getGroupId().equals("")){
               customerApplication.setGroupId(af.getGroupId().substring(0, af.getGroupId().indexOf("---")));
            }else{
               customerApplication.setGroupId("");
            }
            customerApplication.setDescription("");
            // 通过此方法以接口的形式得到一个服务的实例
            logic.update(customerApplication);
      
    }

}
