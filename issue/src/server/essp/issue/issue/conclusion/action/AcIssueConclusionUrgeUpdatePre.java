package server.essp.issue.issue.conclusion.action;

import server.framework.action.AbstractBusinessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import server.essp.issue.common.action.AbstractISSUEAction;
import server.essp.issue.issue.conclusion.logic.LgIssueConclusionUrge;
import server.essp.issue.issue.conclusion.form.AfIssueConclusionUrge;
import db.essp.issue.IssueConclusionUg;
import server.essp.issue.issue.conclusion.viewbean.VbIssueConclusionUrge;
import com.wits.util.comDate;
import c2s.dto.ReturnInfo;
import server.framework.common.Constant;
import server.essp.issue.issue.conclusion.form.AfIssueConclusion;

public class AcIssueConclusionUrgeUpdatePre extends AbstractISSUEAction {
    /**
     * 根据主键查找IssueConclusionUg对象
     * Call: LgIssueConclusionUrge.load(rid)
     * ForwardID: success
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     */

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
        BusinessException {


        AfIssueConclusionUrge form=(AfIssueConclusionUrge)this.getForm();
       String sRid=form.getRid();
       Long rid = Long.valueOf(sRid);

        //Long issueRid = new Long((String) request.getParameter("issueRid"));
       // log.info("update urge,rid:" + rid +
       //          "\n issueRid:" + issueRid);
        LgIssueConclusionUrge logic = new LgIssueConclusionUrge();
        //logic.setDbAccessor(this.getDbAccessor());
        VbIssueConclusionUrge viewbean = logic.updatePre(rid);

        request.setAttribute("webVo", viewbean);

        ReturnInfo returnInfo = data.getReturnInfo();
        returnInfo.setForwardID("success");

    }

    /** @link dependency */
    /*# server.essp.issue.issue.conclusion.logic.LgIssueConclusionUrge lnkLgIssueConclusionUrge; */
}
