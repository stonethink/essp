package server.essp.issue.issue.conclusion.action;
import server.essp.issue.common.action.AbstractISSUEAction;
import server.framework.action.AbstractBusinessAction;
import server.essp.issue.issue.conclusion.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import server.essp.issue.issue.conclusion.logic.LgIssueConclusion;
import server.essp.issue.issue.conclusion.form.AfIssueConclusion;
import c2s.dto.ReturnInfo;

public class AcIssueConclusion extends AbstractISSUEAction {
        /**
         * 根据传入AfIssueConclusion对Issue做Conclude
         * Call:LgIssueConclusion.conclude();
         * ForwardId:success
         * @param request HttpServletRequest
         * @param response HttpServletResponse
         * @param data TransactionData
         * @throws BusinessException
         */
        public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
            BusinessException {
            AfIssueConclusion form = (AfIssueConclusion)this.getForm();
      log.info("add conclusion Actual Influence:" + form.getActualInfluence() +
                 "\n Solved Description:" + form.getSolvedDescription() +
                 "\n Finished Date:" + form.getFinishedDate() +
                 "\n Delivered Date:" + form.getDeliveredDate()+
                 "\n Attachment:" + form.getAttachment()+
                 "\n Attachment Desc:" + form.getAttachmentDesc()+
                 "\n Closure Status:" + form.getClosureStatus()+
                 "\n Waiting:" + form.getWaiting()+
                 "\n Conform Date:" + form.getConfirmDate()+
                 "\n Conform By:" + form.getConfirmBy()+
                 "\n Instruction of closure:" + form.getInstructionClosure());
          LgIssueConclusion logic = new LgIssueConclusion();
       // logic.setDbAccessor( this.getDbAccessor() );
        logic.conclude(form);

        request.setAttribute("refresh", "opener.refreshSelf()");
        ReturnInfo returnInfo = data.getReturnInfo();
        returnInfo.setForwardID("success");

    }

    /** @link dependency */
    /*# server.essp.issue.issue.conclusion.logic.LgIssueConclusion lnkLgIssueConclusion; */
}
