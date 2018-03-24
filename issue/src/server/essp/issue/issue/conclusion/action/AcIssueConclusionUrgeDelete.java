package server.essp.issue.issue.conclusion.action;

import server.framework.action.AbstractBusinessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import server.essp.issue.common.action.AbstractISSUEAction;
import server.essp.issue.issue.conclusion.logic.LgIssueConclusionUrge;
import c2s.dto.ReturnInfo;
import server.framework.common.Constant;
import server.essp.issue.issue.conclusion.viewbean.VbIssueConclusion;
import server.essp.issue.issue.conclusion.form.AfIssueConclusion;

public class AcIssueConclusionUrgeDelete extends AbstractISSUEAction  {
    /**
     * 根据主键删除IssueConclusionUg对象
     * Call: LgIssueConclusionUrge.delete(rid)
     * ForwardID: delete_success
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     * @todo Implement this server.framework.action.AbstractBusinessAction method
     */

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
            BusinessException {
            Long rid =new Long(request.getParameter("rid")) ;
           Long issueRid = new Long(request.getParameter("issueRid"));
      //  log.info("delete isssue conclusion urge,rid:" + rid +
             //    "\nissueRid:" + issueRid);

        LgIssueConclusionUrge logic = new LgIssueConclusionUrge();
        logic.setDbAccessor( this.getDbAccessor() );
        logic.delete(rid);
       // VbIssueConclusion viewbean=new VbIssueConclusion();
        //viewbean.setRid(issueRid.toString());
        request.setAttribute("fromDelete","true");
        request.setAttribute("issueRid", issueRid.toString());
        request.setAttribute("refresh", "opener.refreshSelf()");
        ReturnInfo returnInfo = data.getReturnInfo();
        returnInfo.setForwardID("success");

    }

    /** @link dependency */
    /*# server.essp.issue.issue.conclusion.logic.LgIssueConclusionUrge lnkLgIssueConclusionUrge; */
}
