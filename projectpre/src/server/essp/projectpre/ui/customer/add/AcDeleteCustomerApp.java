package server.essp.projectpre.ui.customer.add;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.service.customerapplication.ICustomerApplication;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;

public class AcDeleteCustomerApp  extends AbstractESSPAction {

    /**
     * ����CODE��ѡ�еļ�¼ɾ��
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {
       Long code= null;
        if (request.getParameter("CODE") != null
                && !request.getParameter("CODE").equals("")) {
            code = Long.valueOf(request.getParameter("CODE"));
        }
        // ͨ���˷����Խӿڵ���ʽ�õ�һ�������ʵ��
        ICustomerApplication logic = (ICustomerApplication) this.getBean("CustomerApplicationLogic");       
        logic.delete(code);
    }

}
