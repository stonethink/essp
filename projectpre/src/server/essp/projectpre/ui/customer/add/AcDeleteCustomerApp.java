package server.essp.projectpre.ui.customer.add;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.service.customerapplication.ICustomerApplication;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;

public class AcDeleteCustomerApp  extends AbstractESSPAction {

    /**
     * 根据CODE将选中的记录删除
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
