package server.essp.issue.issue.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.issue.common.action.AbstractISSUEAction;
import server.framework.common.BusinessException;
import server.essp.issue.issue.form.AfIssueDuplation;
import server.essp.issue.issue.logic.LgIssueDuplation;
import server.essp.issue.common.logic.LgIssueType;
import server.essp.issue.common.constant.Status;
import java.util.List;
import db.essp.issue.Issue;
import server.framework.taglib.util.SelectOptionImpl;
import net.sf.hibernate.Query;
import net.sf.hibernate.*;
import java.util.ArrayList;


public class AcIssueDuplation extends AbstractISSUEAction {
    public AcIssueDuplation() {
    }

    /**
     * executeAct
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     * @todo Implement this server.framework.action.AbstractBusinessAction
     *   method
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
        BusinessException {
        AfIssueDuplation duplation = (AfIssueDuplation)this.getForm();
        LgIssueType logic = new LgIssueType();

        String accountRid = duplation.getAccountRid();
        String rid = duplation.getRid();
        String typeName = duplation.getTypeName();
        String statusName = duplation.getStatusName();
        String belongTo = logic.getStatusBelongTo(typeName, statusName);
        String IssueDuplation = "";
        if (belongTo.equals(Status.DUPLATION)) {
            IssueDuplation = "ENABLED";
        } else {
            IssueDuplation = "DISABLED";
        }
        this.getRequest().setAttribute("issueDuplation", IssueDuplation);

        List duplationIssueList=new ArrayList();
        if (accountRid!= null && !accountRid.equals("")) {

            try {
                Query q = null;
                if(rid==null || rid.equals("")) {
                    q = this.getDbAccessor().getSession().createQuery(
                        "from Issue s where s.accountId=" +
                        accountRid + " order by s.rid ");
                } else {
                    q = this.getDbAccessor().getSession().createQuery(
                        "from Issue s where s.accountId=" +
                        accountRid + " and s.rid<>"+rid+" order by s.rid ");
                }
                List dbDuplationList = q.list();
                duplationIssueList.add(new SelectOptionImpl("  ----  Please Select  ----  ",""));
                if (dbDuplationList != null) {
                    for (int i = 0; i < dbDuplationList.size(); i++) {
                        Issue dbIssue = (Issue) dbDuplationList.get(i);
                        SelectOptionImpl duplationOption = new SelectOptionImpl(
                            dbIssue.getIssueId(),
                            dbIssue.getRid().toString());
                        duplationIssueList.add(duplationOption);
                    }
                }
            } catch (Exception ex) {
                throw new BusinessException(ex);
            }
        }

        this.getRequest().setAttribute("duplationIssueList", duplationIssueList);

    }
}
