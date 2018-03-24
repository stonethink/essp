package server.essp.common.issue.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import java.util.Vector;
import itf.issue.IIssueTypeUtil;
import itf.issue.IssueFactory;
import itf.issue.IIssueUtil;
import itf.account.IAccountUtil;
import itf.account.AccountFactory;
import c2s.essp.common.account.IDtoAccount;
import server.essp.framework.action.AbstractESSPAction;
import c2s.essp.common.user.DtoUser;

public class AcIssueSelectType extends AbstractESSPAction{
    public void executeAct(HttpServletRequest reuqest,
                           HttpServletResponse response,
                           TransactionData data) throws
            BusinessException {
        try{
            DtoUser user = this.getUser();
            String issueType = (String)data.getInputInfo().getInputObj("issueType");
            Long acntRid = (Long)data.getInputInfo().getInputObj("acntRid");
            IIssueTypeUtil issueTypeLogic =  IssueFactory.createIssueType();
            Vector issuePriority  = issueTypeLogic.getPriorityComboItems(issueType);

            Vector issueScope = issueTypeLogic.getScopeComboItems(issueType, user.getUserType());
            Vector issueStatus = issueTypeLogic.getStatusComboItem(issueType);
            IAccountUtil accountUtil = AccountFactory.create();
            IDtoAccount account = accountUtil.getAccountByRID(acntRid);
            IIssueUtil issueUtilLogic = IssueFactory.createIssue();
            String issueIdValue = issueUtilLogic.generateIssueId(issueType, acntRid, account);

            data.getReturnInfo().setReturnObj("issuePriority", issuePriority);
            data.getReturnInfo().setReturnObj("issueScope", issueScope);
            data.getReturnInfo().setReturnObj("issueStatus", issueStatus);
            data.getReturnInfo().setReturnObj("issueIdValue", issueIdValue);


        } catch(Exception e){
          throw new BusinessException(e);
        }
    }

}
