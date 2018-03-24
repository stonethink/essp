package server.essp.common.issue.action;

import server.essp.framework.action.AbstractESSPAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import c2s.essp.common.issue.IDtoIssue;
import c2s.essp.common.user.DtoUser;
import itf.issue.IssueFactory;
import itf.issue.IIssueUtil;


public class AcIssueUpdate extends AbstractESSPAction{
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData data) throws
            BusinessException {
        try{
            DtoUser user = this.getUser();
            IDtoIssue dtoIssue = (IDtoIssue)data.getInputInfo().getInputObj("dtoIssue");
            Boolean isMail = (Boolean) data.getInputInfo().getInputObj("isMail");
            if (isMail == null) {
                isMail = true;
            }
            dtoIssue.setConfirmBy(user.getUserLoginId());
            IIssueUtil logic = IssueFactory.createIssueUtil();
            String issueId = logic.updateIssue(dtoIssue, isMail);
            data.getReturnInfo().setReturnObj("issueId", issueId);
        } catch(Exception e){
            e.printStackTrace();
           throw new BusinessException(e);
        }
    }

}
