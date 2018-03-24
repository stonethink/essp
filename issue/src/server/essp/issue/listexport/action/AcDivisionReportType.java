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
     * ���ֹ�����������Ҫ��ϸ����������ǲ�����������ı���
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
