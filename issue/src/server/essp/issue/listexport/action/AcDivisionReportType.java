package server.essp.issue.listexport.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.issue.common.action.AbstractISSUEAction;
import server.framework.common.BusinessException;
import server.essp.issue.issue.form.AfIssueSearch;

public class AcDivisionReportType extends AbstractISSUEAction {
    public AcDivisionReportType() {
    }

    /**
     * 区分过来的请求是要详细讨论情况还是不带讨论情况的报表
     *
     * @param httpServletRequest HttpServletRequest
     * @param httpServletResponse HttpServletResponse
     * @param transactionData TransactionData
     * @throws BusinessException
     * @todo Implement this server.framework.action.AbstractBusinessAction
     *   method
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData data) throws
        BusinessException {
        AfIssueSearch searchform =(AfIssueSearch)this.getForm();
        request.getSession().setAttribute("RoprtConditionForm",searchform);
        String reportType="";
        if(request.getParameter("ReportType")!=null){
            reportType=(String)request.getParameter("ReportType");
        }

        if(reportType.equals("withoutDetail")){
            data.getReturnInfo().setForwardID("withoutDetail");
        }
        if(reportType.equals("withDetail")){
            data.getReturnInfo().setForwardID("withDetail");
        }


    }
}
