package server.essp.projectpre.ui.customer.check;

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
public class AcConfirmAlterApp extends AbstractESSPAction {

    /**
     * 如果客户变更申请确认,则将客户变更申请表中的信息更新到客户变更正式表中,且将此记录从变更申请表中删除
     * 如果客户变更申请被拒绝,则将申请状态改为拒绝
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {       
            // 如果有ActionForm传入的话，用此方法获得ActionFrom
            AfCustomerApplication af = (AfCustomerApplication) this.getForm();
            ICustomerApplication logicApp = (ICustomerApplication) this.getBean("CustomerApplicationLogic");  
            CustomerApplication custApp =logicApp.loadByRid(Long.valueOf(af.getRid()));     
            Customer cust=new Customer();
            ICustomerService logic = (ICustomerService) this.getBean("CustomerLogic"); 
            cust = logic.loadByRid(Long.valueOf(custApp.getCustomerRid()));         
            if(!custApp.getApplicationStatus().equals(af.getApplicationStatus())){
              cust.setGroupId(custApp.getGroupId());
              cust.setShort_(custApp.getCustomerShort());
              cust.setNameCn(custApp.getCustomerNameCn());
              cust.setNameEn(custApp.getCustomerNameEn());
              cust.setBelongBd(custApp.getBelongBd());
              cust.setBelongSite(custApp.getBelongSite());          
              cust.setClass_(custApp.getCustomerClass());
              cust.setCountry(custApp.getCustomerCountry());
              cust.setCustDescription(custApp.getCustDescription());
              cust.setAlterDate(custApp.getApplicationDate());
              logic.update(cust);     
              logicApp.delete(Long.valueOf(af.getRid()));
            }else{
                custApp.setApplicationStatus("Rejected");
                custApp.setDescription(af.getDescription());
                logicApp.update(custApp);  
            }  
    }
}

