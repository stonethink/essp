package server.essp.issue.issue.conclusion.action;

import server.framework.action.AbstractBusinessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import server.essp.issue.common.action.AbstractISSUEAction;
import server.essp.issue.issue.conclusion.viewbean.VbIssueConclusionUrge;
import server.framework.common.Constant;
import c2s.dto.ReturnInfo;
import server.essp.issue.issue.conclusion.logic.LgIssueConclusionUrge;
import server.essp.issue.issue.conclusion.form.AfIssueConclusion;

public class AcIssueConclusionUrgeAddPre extends AbstractISSUEAction {
    /**
         *依据传入的issueRid,新建VbIssueConclusionUrge对象
         * Call:LgIssueConclusionUrge.addPre(issueRid)
         * ForwardID: add
         * @param request HttpServletRequest
         * @param response HttpServletResponse
         * @param data TransactionData
         * @throws BusinessException
         */



    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
            BusinessException {
            AfIssueConclusion form=(AfIssueConclusion)this.getForm();
           String sRid=form.getRid();
           Long rid = Long.valueOf(sRid);

            VbIssueConclusionUrge vBean = new VbIssueConclusionUrge();
            LgIssueConclusionUrge logic = new LgIssueConclusionUrge();

           vBean=logic.addPre(rid);

          request.setAttribute("webVo", vBean);
         //request.setAttribute(Constant.VIEW_BEAN_KEY, vBean);
         ReturnInfo returnInfo = data.getReturnInfo();
         returnInfo.setForwardID("add");

    }

    /** @link dependency */
    /*# server.essp.issue.issue.conclusion.logic.LgIssueConclusionUrge lnkLgIssueConclusionUrge; */
}
