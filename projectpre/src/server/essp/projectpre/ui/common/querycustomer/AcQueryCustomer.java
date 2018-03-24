package server.essp.projectpre.ui.common.querycustomer;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.service.customer.ICustomerService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;

public class AcQueryCustomer extends AbstractESSPAction {

    /**
     * ����Action Form �д����������ѯ�ͻ�����
     * ����customer�б�
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {
    	AfQueryCustomer af = (AfQueryCustomer) this.getForm();
    	ICustomerService logicCustomer = (ICustomerService) this.getBean("CustomerLogic");
    	List customerList = logicCustomer.queryCustomer(af.getCustomerId(),af.getShortName(),af.getParamKeys());
		request.setAttribute(server.framework.common.Constant.VIEW_BEAN_KEY, customerList);
    }

}