package server.essp.issue.issue.conclusion.action;

import server.framework.action.AbstractBusinessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import server.essp.issue.common.action.AbstractISSUEAction;
import server.essp.issue.issue.conclusion.logic.LgIssueConclusion;
import server.essp.issue.issue.conclusion.viewbean.VbIssueConclusion;
import c2s.dto.ReturnInfo;
import server.essp.issue.issue.conclusion.form.AfIssueConclusion;

public class AcIssueConclusionPre extends AbstractISSUEAction {
    /**
     * 获得Issue Conclusion页面显示内容
     * Call:LgIssueConclusion.conclusionPrepare();
     * ForwardId:conclusion
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
        BusinessException {
        /**
         * 调用logic
         * 调用logic中的conclusionPrepare方法，并把它放入viewbean中
         * 把viewbean中的数据传到jsp中
         */

        String useid = this.getUser().getUserLoginId();
        AfIssueConclusion form=(AfIssueConclusion)this.getForm();
        String sRid=form.getRid();
        Long rid = Long.valueOf(sRid);
        LgIssueConclusion logic = new LgIssueConclusion();
        VbIssueConclusion viewbean = logic.conclusionPrepare(rid, useid);
        request.setAttribute("webVo", viewbean);
        ReturnInfo returnInfo = data.getReturnInfo();
        returnInfo.setForwardID("conclusion");

    }

    /** @link dependency */
    /*# server.essp.issue.issue.conclusion.logic.LgIssueConclusion lnkLgIssueConclusion; */
}
