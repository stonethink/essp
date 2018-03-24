package server.essp.attendance.overtime.action;

import server.framework.common.BusinessException;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import java.util.List;
import server.essp.attendance.overtime.logic.LgOverTimeClean;

public class AcOverTimeClean extends AbstractESSPAction {
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData data) throws BusinessException {
        List list = (List) request.getSession().getAttribute("overTimeCleanList");
        LgOverTimeClean lg = new LgOverTimeClean();
        List result = lg.executeClean(list);
        request.setAttribute("overTimeCleanList", result);
        request.getSession().removeAttribute("overTimeCleanList");
    }
}
