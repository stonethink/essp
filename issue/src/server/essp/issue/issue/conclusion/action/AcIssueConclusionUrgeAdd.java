package server.essp.issue.issue.conclusion.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import server.essp.issue.common.action.AbstractISSUEAction;
import server.essp.issue.issue.conclusion.logic.LgIssueConclusionUrge;
import server.essp.issue.issue.conclusion.form.AfIssueConclusionUrge;
import c2s.dto.ReturnInfo;

public class AcIssueConclusionUrgeAdd extends AbstractISSUEAction {
    /**
    * 依据传入的AfIssueConclusionUrge新增Issue Conclusion的Urge信息
    * Call: LgIssueConclusionUrge.add(AfIssueConclusionUrge)
    * ForwardID: add_success
    * @param request HttpServletRequest
    * @param response HttpServletResponse
    * @param data TransactionData
    * @throws BusinessException
    */

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
            BusinessException {
            AfIssueConclusionUrge form = (AfIssueConclusionUrge)this.getForm();
       log.info("add Urged by:" + form.getUrgedBy() +
                "\n IssueRid:" + form.getIssueRid() +
                "\n Urge to:" + form.getUrgedBy() +
                "\n Urg Date:" + form.getUrgDate() +
                "\n Description:" + form.getDescription() +
                "\n Attachment:" + form.getAttachment());

       String isMail = (String) request.getParameter("IsMail");
       LgIssueConclusionUrge logic = new LgIssueConclusionUrge();
       logic.setDbAccessor( this.getDbAccessor() );
       if(isMail==null || isMail.length()==0){
           logic.add(form,"");
       }else{
           logic.add(form,isMail);
       }
       request.setAttribute("refresh", "opener.refreshSelf()");
       ReturnInfo returnInfo = data.getReturnInfo();
       returnInfo.setForwardID("success");


    }

    /** @link dependency */
    /*# server.essp.issue.issue.conclusion.logic.LgIssueConclusionUrge lnkLgIssueConclusionUrge; */
}
