package server.essp.issue.issue.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import server.essp.issue.issue.viewbean.VbIssueReload;
import server.essp.issue.issue.logic.LgIssueList;
import server.essp.issue.common.action.AbstractISSUEAction;
import server.essp.issue.issue.form.AfIssueSearch;

public class AcIssueReloadPre extends AbstractISSUEAction {
    /**
     * 提供Issue Reload页面显示所需内容
     * Call: LgIssueList.searchPrepare()
     * ForwardId:reload
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     */
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
       issueLoad.setPrincipalScope(issueSearch.getPrincipalScope());
       issueLoad.setPriority(issueSearch.getPriority());
       issueLoad.setScope(issueSearch.getScope());
       issueLoad.setStatus(issueSearch.getStatus());
       request.setAttribute("webVo", issueLoad);
    }

    /** @link dependency */
    /*# server.essp.issue.issue.logic.LgIssueList lnkLgIssueList; */

    /** @link dependency */
    /*# VbIssueReload lnkVbIssueReload; */
}
