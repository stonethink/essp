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
     * ����ͻ��������ȷ��,�򽫿ͻ����������е���Ϣ���µ��ͻ������ʽ����,�ҽ��˼�¼�ӱ���������ɾ��
     * ����ͻ�������뱻�ܾ�,������״̬��Ϊ�ܾ�
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {       
            // �����ActionForm����Ļ����ô˷������ActionFrom
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

