package server.essp.common.issue.action;

import server.essp.framework.action.AbstractESSPAction;
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
import c2s.essp.common.user.DtoUser;
import c2s.dto.DtoComboItem;

public class AcIssueAddPre extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData data) throws
            BusinessException {
      try {
          DtoUser user = this.getUser();
          Long acntRid = (Long) data.getInputInfo().getInputObj("acntRid");
          String issueType = (String)data.getInputInfo().getInputObj("issueType");
          IIssueTypeUtil issueTypeLogic = IssueFactory.createIssueType();
          Vector issueTypeItems = issueTypeLogic.getIssueTypeComboItems();
          //ȡ��Ĭ��ѡ��� Issue Type
          if(issueType == null) {
              DtoComboItem issueTypeItem = (DtoComboItem) issueTypeItems.get(0);
              issueType = (String) issueTypeItem.getItemValue();
          }
          //��ȡpriorityѡ��
          Vector issuePriority = issueTypeLogic.getPriorityComboItems(
                  issueType);
          //��ȡscopeѡ��
          Vector issueScope = issueTypeLogic.getScopeComboItems(issueType,
                  user.getUserType());
          //��ȡstatusѡ��
          Vector issueStatus = issueTypeLogic.getStatusComboItem(issueType);
          //��ȡIssue ID
          IAccountUtil accountUtil = AccountFactory.create();
          IDtoAccount account = accountUtil.getAccountByRID(acntRid);
          IIssueUtil issueUtilLogic = IssueFactory.createIssue();
          String issueIdValue = issueUtilLogic.generateIssueId(issueType,
                  acntRid, account);

          data.getReturnInfo().setReturnObj("issueTypeItems", issueTypeItems);
          data.getReturnInfo().setReturnObj("issuePriority", issuePriority);
          data.getReturnInfo().setReturnObj("issueScope", issueScope);
          data.getReturnInfo().setReturnObj("issueStatus", issueStatus);
          data.getReturnInfo().setReturnObj("issueIdValue", issueIdValue);
          data.getReturnInfo().setReturnObj("userName", user.getUserName());
          data.getReturnInfo().setReturnObj("userLoginId", user.getUserLoginId());
          data.getReturnInfo().setReturnObj("accountId", account.getId());
          data.getReturnInfo().setReturnObj("manager", account.getManager());
      } catch(Exception e){
         throw new BusinessException(e);
      }

    }
}
