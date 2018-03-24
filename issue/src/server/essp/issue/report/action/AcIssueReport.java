package server.essp.issue.report.action;

import server.framework.action.AbstractBusinessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;

public class AcIssueReport extends AbstractBusinessAction {
        /**
         * 根据输入projId查询生成报表
         * Call:LgIssueReport.report()
         * ForwardId:result
         * @param request HttpServletRequest
         * @param response HttpServletResponse
         * @param data TransactionData
         * @throws BusinessException
         */
        public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
            BusinessException {
    }

    /** @link dependency */
    /*# server.essp.issue.report.logic.LgIssueReport lnkLgIssueReport; */

    /** @link dependency */
    /*# server.essp.issue.report.viewbean.VbIssueReportList lnkVbIssueReportList; */
}
