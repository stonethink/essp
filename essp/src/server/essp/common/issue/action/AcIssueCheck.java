package server.essp.common.issue.action;

import server.framework.action.AbstractBusinessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import itf.issue.IIssueUtil;
import c2s.essp.common.issue.IDtoIssue;
import itf.issue.IssueFactory;
import c2s.dto.InputInfo;

public class AcIssueCheck extends AbstractBusinessAction{


    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData data) throws
            BusinessException {
      try{
        InputInfo inputInfo = data.getInputInfo();
        String issueId = (String)inputInfo.getInputObj("issueId");
        IIssueUtil issueLogic = IssueFactory.createIssueUtil();
        IDtoIssue dtoIssue = issueLogic.getIssue(issueId);
        data.getReturnInfo().setReturnObj("dtoIssue", dtoIssue);
      }catch (Exception e){
          throw new BusinessException(e);
      }
    }
}
