package server.essp.issue.listexport.action;

import server.essp.issue.issue.logic.LgIssueList;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import server.essp.issue.issue.form.AfIssueSearch;
import server.framework.common.BusinessException;
import server.essp.issue.issue.viewbean.VbIssueReload;
import javax.servlet.http.HttpServletResponse;
import server.essp.issue.common.action.AbstractISSUEAction;

public class AcIssueExportPre extends AbstractISSUEAction {
    public AcIssueExportPre() {
    }

    public void executeAct(HttpServletRequest request,
                       HttpServletResponse response, TransactionData data) throws
    BusinessException {
    VbIssueReload issueLoad = new VbIssueReload();
    LgIssueList logic = new LgIssueList();
    logic.setDbAccessor(this.getDbAccessor());
    issueLoad = logic.searchPrepare();
   AfIssueSearch issueSearch= (AfIssueSearch)this.getSession().getAttribute("searchForm");
   issueLoad.setAccountId(issueSearch.getAccountId());
   issueLoad.setDueDateBegin(issueSearch.getDueDateBegin());
   issueLoad.setDueDateEnd(issueSearch.getDueDateEnd());
   issueLoad.setFillBy(issueSearch.getFillBy());
   issueLoad.setResolveBy(issueSearch.getResolveBy());
   issueLoad.setFillDateBegin(issueSearch.getFillDateBegin());
   issueLoad.setFillDateEnd(issueSearch.getFillDateEnd());
   issueLoad.setIssueId(issueSearch.getIssueId());
   issueLoad.setIssueName(issueSearch.getIssueName());
   issueLoad.setIssueType(issueSearch.getIssueType());
   issueLoad.setPrincipal(issueSearch.getPrincipal());
   issueLoad.setPriority(issueSearch.getPriority());
   issueLoad.setScope(issueSearch.getScope());
   issueLoad.setStatus(issueSearch.getStatus());
   request.setAttribute("webVo", issueLoad);
}

}
