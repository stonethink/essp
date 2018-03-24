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
     * �������Issueҳ�����������
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
         * ����logic
         * ����logic�е�addpre����������������viewbean��
         * ��viewbean�е����ݴ���jsp��
         */
        AfIssue issueForm = (AfIssue)this.getForm();
        boolean isForEssp = true;
        if (request.getParameter("NotForEssp") != null &&
            !request.getParameter("NotForEssp").equals("") &&
            request.getParameter("NotForEssp").equals("yes")) {
            isForEssp = false; //����רΪESSP��ISSUE
            //������Ǹ�ESSP�����⣬�Ͱ�����������Ϣ����Ϊ ""
            if (issueForm.getAccountCode() != null) {
                issueForm.setAccountCode("");
            }
            if (issueForm.getIssueStatus() != null) {
                issueForm.setIssueStatus("");
            }
            if (issueForm.getPriority() != null) {
                issueForm.setPriority("");
            }
            //��Session�е���Ϣ�������Issue�ı���
            if (this.getSession().getAttribute("searchForm") != null) {
                //��ֻ�轫issueType����
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
