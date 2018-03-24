package server.essp.common.issue.action;

import server.essp.framework.action.AbstractESSPAction;
import c2s.essp.common.user.DtoUser;
import itf.issue.IIssueUtil;
import itf.account.IAccountUtil;
import itf.account.AccountFactory;
import c2s.essp.common.issue.IDtoIssue;
import javax.servlet.http.HttpServletResponse;
import itf.issue.IssueFactory;
import c2s.essp.common.account.IDtoAccount;
import c2s.dto.InputInfo;
import itf.issue.IIssueTypeUtil;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import java.util.Vector;

public class AcIssueUpdatePre extends AbstractESSPAction{
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData data) throws
            BusinessException {
      try{
        DtoUser user = this.getUser();
        InputInfo inputInfo = data.getInputInfo();
        Long acntRid = (Long)inputInfo.getInputObj("acntRid");
        String issueId = (String)inputInfo.getInputObj("issueId");
        IIssueUtil issueUtil = IssueFactory.createIssueUtil();
        IDtoIssue dtoIssue = issueUtil.getIssue(issueId);

        IIssueTypeUtil issueTypeLogic = IssueFactory.createIssueType();
         Vector issueTypeItems = issueTypeLogic.getIssueTypeComboItems();
         //获取priority选项
         Vector issuePriority = issueTypeLogic.getPriorityComboItems(
                 dtoIssue.getIssueType());
         //获取scope选项
         Vector issueScope = issueTypeLogic.getScopeComboItems(dtoIssue.getIssueType(),
                 user.getUserType());
         //获取status选项
         Vector issueStatus = issueTypeLogic.getStatusComboItem(dtoIssue.getIssueType());
         //获取accountId
         IAccountUtil accountUtil = AccountFactory.create();
         IDtoAccount account = accountUtil.getAccountByRID(acntRid);
         String accountId = account.getId();

         data.getReturnInfo().setReturnObj("issueTypeItems", issueTypeItems);
         data.getReturnInfo().setReturnObj("issuePriority", issuePriority);
         data.getReturnInfo().setReturnObj("issueScope", issueScope);
         data.getReturnInfo().setReturnObj("issueStatus", issueStatus);
         data.getReturnInfo().setReturnObj("accountId", accountId);
         data.getReturnInfo().setReturnObj("dtoIssue", dtoIssue);
      }catch(Exception e){
         throw new BusinessException(e);
      }
    }

}
