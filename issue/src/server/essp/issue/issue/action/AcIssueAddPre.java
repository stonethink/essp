package server.essp.issue.issue.action;

import server.framework.action.AbstractBusinessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import server.essp.issue.common.action.AbstractISSUEAction;
import server.essp.issue.issue.logic.LgIssue;
import server.essp.issue.issue.viewbean.VbIssue;
import server.essp.issue.issue.form.AfIssue;
import server.essp.issue.issue.form.AfIssueSearch;
import c2s.essp.common.user.DtoUser;


public class AcIssueAddPre extends AbstractISSUEAction {
    /**
     * 获得新增Issue页面所需的内容
     * Call:LgIssue.addPrepare()
     * ForwardId:add
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
         * 调用logic中的addpre方法，并把它放入viewbean中
         * 把viewbean中的数据传到jsp中
         */
        AfIssue issueForm = (AfIssue)this.getForm();
        boolean isForEssp = true;
        if (request.getParameter("NotForEssp") != null &&
            !request.getParameter("NotForEssp").equals("") &&
            request.getParameter("NotForEssp").equals("yes")) {
            isForEssp = false; //不是专为ESSP加ISSUE
            //如果不是给ESSP提问题，就把下面三个信息都置为 ""
            if (issueForm.getAccountCode() != null) {
                issueForm.setAccountCode("");
            }
            if (issueForm.getIssueStatus() != null) {
                issueForm.setIssueStatus("");
            }
            if (issueForm.getPriority() != null) {
                issueForm.setPriority("");
            }
            //将Session中的信息带入添加Issue的表单中
            if (this.getSession().getAttribute("searchForm") != null) {
                //现只需将issueType带入
                AfIssueSearch issueSearchForm = (AfIssueSearch)this.getSession().
                                                getAttribute("searchForm");

                String issueType = issueSearchForm.getIssueType();
                if (issueType != null && !issueType.equals("")) {
                    if (issueSearchForm.getAccountId() == null ||
                        issueSearchForm.getAccountId().trim().equals("")) {
                        issueForm = new AfIssue();
                    } else {
                        issueForm.setAccountId(issueSearchForm.getAccountId());
                    }

                    issueForm.setIssueType(issueType);
                } else {
                    issueForm = new AfIssue();
                    if (issueSearchForm.getAccountId() != null &&
                        !issueSearchForm.getAccountId().trim().equals("")) {
                        issueForm.setAccountId(issueSearchForm.getAccountId());
                    }

                }

            }
        }
        DtoUser user = (DtoUser) request.getSession().getAttribute(DtoUser.SESSION_USER);

        if(user.getUserType().equals("CUST")){
            issueForm.setScope("Customer");
          }

        LgIssue logic = new LgIssue();
        VbIssue viewbean = logic.addPre(issueForm, isForEssp);

        request.setAttribute("webVo", viewbean);

    }

}
