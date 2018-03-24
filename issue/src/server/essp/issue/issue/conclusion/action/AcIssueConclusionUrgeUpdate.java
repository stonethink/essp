package server.essp.issue.issue.conclusion.action;

import server.framework.action.AbstractBusinessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import server.essp.issue.common.action.AbstractISSUEAction;
import server.essp.issue.issue.conclusion.form.AfIssueConclusionUrge;
import server.essp.issue.issue.conclusion.logic.LgIssueConclusionUrge;
import c2s.dto.ReturnInfo;

public class AcIssueConclusionUrgeUpdate extends AbstractISSUEAction {
    /**
     * 根据传入AfIssueConclusionUrge修改IssueConclusionUg对象
     * Call: LgIssueConclusionUrge.update(AfIssueConclusionUrge)
     * ForwardID: success
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     */

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
        BusinessException {
       // System.out.println("qwert");
        AfIssueConclusionUrge form = (AfIssueConclusionUrge)this.getForm();
        log.info("update urge,rid:" + form.getRid() +
                 "\n issueRid:" + form.getIssueRid());

        String isMail = (String) request.getParameter("IsMail");
        LgIssueConclusionUrge logic = new LgIssueConclusionUrge();
        logic.setDbAccessor(this.getDbAccessor());
        if(isMail==null || isMail.length()==0){
           logic.update(form,"");
        }else{
           logic.update(form,isMail);
        }

        request.setAttribute("refresh", "opener.refreshSelf()");
        ReturnInfo returnInfo = data.getReturnInfo();
        returnInfo.setForwardID("success");

    }

    /** @link dependency */
    /*# server.essp.issue.issue.conclusion.logic.LgIssueConclusionUrge lnkLgIssueConclusionUrge; */
}
