package server.essp.projectpre.ui.customer.change;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.service.customerapplication.ICustomerApplication;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;

public class AcDeleteCustApp extends AbstractESSPAction {

    /**
     * 根据CODE删除选中的那一行
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {
       Long code= null;
        if (request.getParameter("CODE") != null
                && !request.getParameter("CODE").equals("")) {
            code = Long.valueOf(request.getParameter("CODE"));
        }
        // 通过此方法以接口的形式得到一个服务的实例
        ICustomerApplication logic = (ICustomerApplication) this.getBean("CustomerApplicationLogic");       
        logic.delete(code);

    }

}

