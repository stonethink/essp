package server.essp.issue.stat.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.issue.common.action.AbstractISSUEAction;
import server.framework.common.BusinessException;
import c2s.essp.common.user.DtoUser;
import server.essp.issue.stat.logic.LgIssueStat;
import java.util.List;

public class AcIssueStatByAccount extends AbstractISSUEAction {
    /**
     * executeAct
     *
     * @param httpServletRequest HttpServletRequest
     * @param httpServletResponse HttpServletResponse
     * @param transactionData TransactionData
     * @throws BusinessException
     * @todo Implement this server.framework.action.AbstractBusinessAction
     *   method
     */
    public void executeAct(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           TransactionData transactionData) throws
        BusinessException {
        String accountRid = httpServletRequest.getParameter("accountRid");
        this.getRequest().setAttribute("accountRid",accountRid);
        DtoUser user = this.getUser();
        if(user == null)
            throw new BusinessException("ISSUE_STAT_00000","please login first!");
        LgIssueStat logic = new LgIssueStat();
        List webVo = logic.statisticAccountByType(new Long(accountRid),user.getUserType());
        httpServletRequest.setAttribute("webVo", webVo);
    }
}
