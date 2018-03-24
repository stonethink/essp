package server.essp.projectpre.ui.customer.add;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.wits.util.comDate;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Customer;
import server.essp.projectpre.db.CustomerApplication;
import server.essp.projectpre.service.customer.ICustomerService;
import server.essp.projectpre.service.customerapplication.ICustomerApplication;
import server.essp.projectpre.ui.customer.add.VbCustAppList;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import c2s.dto.TransactionData;
import c2s.essp.common.user.DtoUser;

public class AcListAllCustAppInfo extends AbstractESSPAction {

    /**
     * �����û�ID��״̬Ϊ��ȷ�ϣ���������Ϊ�ͻ����������ѯ�����������������Ŀͻ���¼
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {

        // ͨ���˷����Խӿڵ���ʽ�õ�һ�������ʵ��
        String applicationType = server.essp.projectpre.service.constant.Constant.CUSTOMERADDAPP;
        String applicationStatus = server.essp.projectpre.service.constant.Constant.CONFIRMED;
        DtoUser user = (DtoUser)request.getSession().getAttribute(DtoUser.SESSION_USER);
        String userLoginId = user.getUserLoginId();
        ICustomerApplication   logic = (ICustomerApplication) this.getBean("CustomerApplicationLogic");
        List codeList = logic.listAll(userLoginId,applicationType, applicationStatus);
        List viewBean = new ArrayList();
        String IDFORMATER = "00000000";
        String newRid = null;
        DecimalFormat df = new DecimalFormat(IDFORMATER);
        for(int i=0;i<codeList.size();i++){
            CustomerApplication customerApplication = (CustomerApplication) codeList.get(i);               
            VbCustAppList vbCustAppList = new VbCustAppList();        
            newRid = df.format(customerApplication.getRid(), new StringBuffer(),
                    new FieldPosition(0)).toString();
            vbCustAppList.setRid(newRid);
            vbCustAppList.setCustomerShort(customerApplication.getCustomerShort());
            vbCustAppList.setRegId(customerApplication.getRegId());
            vbCustAppList.setApplicationDate(comDate.dateToString(customerApplication.getApplicationDate(),"yyyy-MM-dd"));
            vbCustAppList.setApplicationStatus(customerApplication.getApplicationStatus());
            
            ICustomerService logicCust = (ICustomerService) this.getBean("CustomerLogic");
            Customer customer=new Customer();
            //���ݿͻ���Ų�ѯ���ͻ����ƣ����ͻ���źͿͻ�����ƴ����һ����ʾ��ҳ����
            if(customerApplication.getGroupId()!=null){
             customer=logicCust.loadByCustomerId(customerApplication.getGroupId());
             vbCustAppList.setGroupId(customer.getCustomerId()+"---"+customer.getShort_());
            }
            viewBean.add(vbCustAppList);
        }
        request.setAttribute(Constant.VIEW_BEAN_KEY, viewBean);
    }

}

        
        
        
        
        
        
        
        



