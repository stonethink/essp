package server.essp.issue.issue.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import server.essp.issue.issue.logic.LgIssueList;
import server.essp.issue.issue.viewbean.VbIssueList;
import server.essp.issue.issue.form.AfIssueSearch;
import server.essp.issue.common.action.AbstractISSUEAction;
import c2s.dto.DtoUtil;
import server.essp.issue.issue.viewbean.VbIssueReload;
import java.util.List;
import server.framework.taglib.util.ISelectOption;
import java.util.ArrayList;

public class AcIssueListSingle extends AbstractISSUEAction {
    /**
     * 此Action专用于在Issue Statics列表中各栏位的查询
     * 根据输入AfIssueSearch查询Issue
     * Call:LgIssueList.search()
     * ForwardId:list
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
        BusinessException {
        VbIssueList listViewBean = new VbIssueList();
        AfIssueSearch issueform = (AfIssueSearch)this.getForm();
        AfIssueSearch issueform2 = new AfIssueSearch();
        try {
            DtoUtil.copyProperties(issueform2, issueform);
        } catch (Exception ex) {
            throw new BusinessException(ex);
        }
        if (request.getAttribute("forwardFromDelete") != null &&
            request.getAttribute("forwardFromDelete").equals("true")) {
            issueform = (AfIssueSearch)this.getSession().getAttribute(
                "searchForm");
            try {
                DtoUtil.copyProperties(issueform2, issueform);
            } catch (Exception ex) {
                throw new BusinessException(ex);
            }
        }
        LgIssueList logic = new LgIssueList();
        listViewBean = logic.search(issueform,new ArrayList());
        request.setAttribute("webVo", listViewBean);

        //获取快速搜索所需要的信息
        VbIssueReload issueLoad = new VbIssueReload();
        issueLoad = logic.searchPrepare();

        List accountList = issueLoad.getAccountList();
        ISelectOption accountFirstOpt = (ISelectOption) accountList.get(0);
        accountFirstOpt.setLabel("--Please Select Account---");

        List issueTypeList=issueLoad.getIssueTypeList();
        ISelectOption issueTypeFirstOpt=(ISelectOption)issueTypeList.get(0);
        issueTypeFirstOpt.setLabel("--Please Select Type---");

        List issueStatusList = issueLoad.getStatusList();
        ISelectOption issueStatusFirstOpt=(ISelectOption)issueStatusList.get(0);
        issueStatusFirstOpt.setLabel("--Please Select Status---");

        issueLoad.setIssueType(issueform2.getIssueType());
        issueLoad.setAccountId(issueform2.getAccountId());
        issueLoad.setStatus(issueform2.getStatus());
        request.setAttribute("webVoQuicklySearch", issueLoad);


        this.getSession().setAttribute("searchForm", issueform2);

    }


}
