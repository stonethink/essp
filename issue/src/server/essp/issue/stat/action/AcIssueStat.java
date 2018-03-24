package server.essp.issue.stat.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import c2s.essp.common.user.DtoUser;
import server.essp.issue.common.action.AbstractISSUEAction;
import server.essp.issue.stat.logic.LgIssueStat;
import server.framework.common.BusinessException;

public class AcIssueStat extends AbstractISSUEAction {
    /**
     * 依据userId, userType或accountId获取Issue统计信息
     * Action: IssueStat.do
     * ForwardId: stat
     * Exec:{
     * 1. getFromRequest： accountId { if(accountId != null) setToSeesion: accountId }
     * 2. getFromSeesion： userId, userType, accountId
     * 3. Call: LgIssueStat.statistic()
     * }
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
        BusinessException {
        DtoUser user = this.getUser();
        if(user == null)
            throw new BusinessException("ISSUE_STAT_00000","please login first!");
        LgIssueStat logic = new LgIssueStat();
        List webVo = logic.statistic(user.getUserLoginId(),user.getUserType());
        request.setAttribute("webVo", webVo);
    }


    /** @link dependency */
    /*# server.essp.issue.stat.logic.LgIssueStat lnkLgIssueStat; */

    /** @link dependency */
    /*# server.essp.issue.stat.viewbean.VbIssueTypeStatList lnkVbIssueTypeStatList; */
}
