package server.essp.issue.report.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import server.essp.issue.issue.logic.LgIssueList;
import server.essp.issue.issue.viewbean.VbIssueReload;
import server.essp.issue.report.logic.LgIssueReport;
import server.essp.issue.report.viewbean.VbIssueReportList;
import server.essp.issue.report.viewbean.VbIssueReport;
import server.essp.issue.common.action.AbstractISSUEAction;
import server.essp.issue.report.form.AfReportForm;
import java.util.*;


public class AcIssueReportPre extends AbstractISSUEAction {
    /**
     * 依据userId, userType或accountId获得进入Report查询页面所需内容
     * Action: IssueReportPre.do
     * ForwardId:report
     * Call:LgIssueReport.reportPrepare()
     * Exec:{
     * 1. getFromRequest： accountId { if(accountId != null) setToSeesion: accountId }
     * 2. getFromSeesion： userId, userType, accountId
     * 3. Call:LgIssueReport.reportPrepare()
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
        AfReportForm form = (AfReportForm)this.getForm();

        VbIssueReport reportPre = new VbIssueReport();
        LgIssueReport logic = new LgIssueReport();
        logic.setDbAccessor(this.getDbAccessor());
        reportPre = logic.reportPrepare();
        String submitFlag = request.getParameter("submit");
        if (submitFlag != null &&
            form != null) {
            List typeList = (List) request.getSession().getAttribute("typeList");
            int size = typeList.size();
            List typeParam = new ArrayList();
            for (int i = 0; i < size; i++) {
                String temp = (String) typeList.get(i);
                if (request.getParameter(temp).equalsIgnoreCase("1")) {
                    typeParam.add(temp);
                }
            }
            logic.processPre(form, typeParam);
            reportPre.setSubmitFlag(submitFlag);
            form = logic.paramPre(form ,typeParam);
            setParamInSession(form);
        }
        request.getSession().setAttribute("typeList", reportPre.getTypeList());
        request.setAttribute("webVo", reportPre);
    }

    private void setParamInSession(AfReportForm form) {

        String accountId = form.getAccountId();
        String dateBegin = form.getDateBegin();
        String dateEnd = form.getDateEnd();
        String dateBy = form.getDateBy();
        if (!"".equals(accountId) && !"".equals(dateBegin) &&
            !"".equals(dateEnd) && !"".equals(dateBy)) {
            this.getRequest().getSession().setAttribute("ReportForm", form);
        }

    }
    /** @link dependency */
    /*# server.essp.issue.report.logic.LgIssueReport lnkLgIssueReport; */

    /** @link dependency */
    /*# server.essp.issue.report.viewbean.VbIssueReport lnkVbIssueReport; */
}
