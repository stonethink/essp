package server.essp.issue.issue.conclusion.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import server.essp.issue.common.action.AbstractISSUEAction;
import server.essp.issue.issue.conclusion.form.AfIssueConclusion;
import server.essp.issue.issue.conclusion.logic.LgIssueConclusionUrgeList;
import server.essp.issue.issue.conclusion.viewbean.VbIssueConclusion;
import c2s.dto.ReturnInfo;
import java.util.List;
import server.framework.common.Constant;


public class AcIssueConclusionUrgeList extends AbstractISSUEAction {
    /**
     * 根据IssueConclusion查找对应的ConclusionUg
     * Call: LgIssueConclusionUrgeList.list(issueRid)
     * ForwardID: list
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     */

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
        BusinessException {
        VbIssueConclusion listViewBean = new VbIssueConclusion();
       // String selectedRow = (String) request.getParameter("selectedRow");
        AfIssueConclusion issueConclusionForm = (AfIssueConclusion)this.getForm();

        Long issueRid = new Long((String) issueConclusionForm.getRid());
        String fromDelete=(String)request.getAttribute("fromDelete");
        if("true".equalsIgnoreCase(fromDelete)){
            String strRid=(String)request.getAttribute("issueRid");
            issueRid=new Long(strRid);
        }
        request.setAttribute("fromDelete","false");
        System.out.println(issueRid);
        log.info("list urge of issue rid:" + issueRid);
        LgIssueConclusionUrgeList logic = new LgIssueConclusionUrgeList();
        logic.setDbAccessor(this.getDbAccessor());
        List result = logic.list(issueRid);
       listViewBean.setRid(String.valueOf(issueRid));
        listViewBean.setUrgeList(result);
       /* if (selectedRow == null || selectedRow.equals("") ||
            selectedRow.equalsIgnoreCase("null")) {
            listViewBean.setSelectedRow("tr0");
        } else {
            if ((new Integer(selectedRow.substring(2))).intValue() >=
                result.size()) {
                listViewBean.setSelectedRow("tr0");
            } else {
                listViewBean.setSelectedRow(selectedRow);
            }
        }*/
        request.setAttribute(Constant.VIEW_BEAN_KEY, listViewBean);
        request.setAttribute("issueRid",issueRid.toString());

        String isPrincipal=logic.getIsPrincipal(issueRid);
        request.setAttribute("isPrincipal",isPrincipal);

        String isexecutor=logic.getIsAssignto(issueRid);
        request.setAttribute("isexecutor",isexecutor);

        ReturnInfo returnInfo = data.getReturnInfo();
        returnInfo.setForwardID("list");

    }

    /** @link dependency */
    /*# server.essp.issue.issue.conclusion.logic.LgIssueConclusionUrgeList lnkLgIssueConclusionUrgeList; */
}
