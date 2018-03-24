package server.essp.common.issue.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import c2s.essp.common.issue.IDtoIssue;
import itf.issue.IssueFactory;
import itf.issue.IIssueUtil;
import server.essp.framework.action.AbstractESSPAction;
import c2s.essp.common.user.DtoUser;
import c2s.essp.common.issue.DtoIssue;

public class AcIssueAdd extends AbstractESSPAction{
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData data) throws
            BusinessException {
        IDtoIssue dtoIssue =  (DtoIssue)data.getInputInfo().getInputObj("dtoIssue");
        Boolean isMail = (Boolean) data.getInputInfo().getInputObj("isMail");
        if(isMail == null) {
            isMail = true;
        }
        DtoUser user = this.getUser();
        try {
            dtoIssue.setConfirmBy(user.getUserLoginId());
            IIssueUtil issueLogic = IssueFactory.createIssueUtil();
            String issueId = issueLogic.addIssue(dtoIssue, isMail);
            data.getReturnInfo().setReturnObj("issueId", issueId);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
  }
}
