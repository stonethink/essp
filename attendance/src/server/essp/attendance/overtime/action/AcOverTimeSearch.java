package server.essp.attendance.overtime.action;

import server.essp.framework.action.AbstractESSPAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import server.essp.attendance.overtime.form.AfOverTimeSearch;
import server.essp.attendance.overtime.logic.LgOverTimeSearch;
import java.util.List;

public class AcOverTimeSearch extends AbstractESSPAction {
    public AcOverTimeSearch() {
    }

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData data) throws
            BusinessException {
        AfOverTimeSearch af = (AfOverTimeSearch) this.getForm();
        LgOverTimeSearch lgSearch = new LgOverTimeSearch();
        List list = lgSearch.queryByCondition(af);
        request.setAttribute("webVo",list);
    }

}
