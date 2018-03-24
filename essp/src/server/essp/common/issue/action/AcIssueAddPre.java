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
          //取得默认选择的 Issue Type
          if(issueType == null) {
              DtoComboItem issueTypeItem = (DtoComboItem) issueTypeItems.get(0);
              issueType = (String) issueTypeItem.getItemValue();
          }
          //获取priority选项
          Vector issuePriority = issueTypeLogic.getPriorityComboItems(
                  issueType);
          //获取scope选项
          Vector issueScope = issueTypeLogic.getScopeComboItems(issueType,
                  user.getUserType());
          //获取status选项
          Vector issueStatus = issueTypeLogic.getStatusComboItem(issueType);
          //获取Issue ID
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
