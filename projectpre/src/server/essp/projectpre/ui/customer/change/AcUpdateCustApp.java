package server.essp.projectpre.ui.customer.change;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.wits.util.comDate;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.CustomerApplication;
import server.essp.projectpre.service.customerapplication.ICustomerApplication;
import server.essp.projectpre.ui.customer.add.AfCustomerApplication;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import c2s.dto.TransactionData;

public class AcUpdateCustApp extends AbstractESSPAction {

    /**
     * 获得页面信息修改记录
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {
            
            // 如果有ActionForm传入的话，用此方法获得ActionFrom
            AfCustomerApplication af=(AfCustomerApplication) this.getForm();
            ICustomerApplication logic = (ICustomerApplication) this.getBean("CustomerApplicationLogic");  
            CustomerApplication customerApplication =logic.loadByRid(Long.valueOf(af.getRid()));
           
            customerApplication.setApplicationStatus(af.getApplicationStatus()); 
            if(customerApplication.getAttribute().equals("Company")&&af.getGroupId()!=null&&!af.getGroupId().equals("")){       
                customerApplication.setGroupId(af.getGroupId().substring(0, af.getGroupId().indexOf("---")));
             }else{
                customerApplication.setGroupId("");
             }
            customerApplication.setRegId(af.getRegId().trim());         
            customerApplication.setCustomerShort(af.getCustomerShort().trim());
            customerApplication.setCustomerNameCn(af.getCustomerNameCn().trim());
            customerApplication.setCustomerNameEn(af.getCustomerNameEn().trim());
            customerApplication.setBelongBd(af.getBelongBd().trim());
            customerApplication.setBelongSite(af.getBelongSite().trim());
            customerApplication.setCustomerClass(af.getCustomerClass().trim());
            customerApplication.setCustomerCountry(af.getCustomerCountry().trim());
            customerApplication.setCustDescription(af.getCustDescription().trim());
            customerApplication.setApplicationDate(java.sql.Date.valueOf(comDate.dateToString(new Date(), "yyyy-MM-dd")));
            customerApplication.setDescription("");
            // 通过此方法以接口的形式得到一个服务的实例
            logic.update(customerApplication);
            request.setAttribute(Constant.VIEW_BEAN_KEY,customerApplication);
    }

}
