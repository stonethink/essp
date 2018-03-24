package server.essp.issue.issue.resolution.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import server.essp.issue.common.action.AbstractISSUEAction;
import server.essp.issue.issue.resolution.viewbean.VbIssueResolution;
import server.essp.issue.issue.resolution.logic.LgIssueResolution;

public class AcIssueResolutionPre extends AbstractISSUEAction {

    /**
     * 获得Issue Resoluion页面显示所需内容
     * Call:LgIssueResolution.resolutionPrepare()
     * ForwareId:resolution
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
        BusinessException {
        String isMail = request.getParameter("isMail");
        if(isMail == null)
            isMail = (String) request.getAttribute("isMail");

        String rid=request.getParameter("rid");
        if(rid==null) {
            rid=(String)request.getAttribute("rid");
        }
        LgIssueResolution logic=new LgIssueResolution();
        VbIssueResolution webVo=logic.resolutionPrepare(rid);
        webVo.setIsMail(isMail);
        request.setAttribute("webVo",webVo);
    }

    /** @link dependency */
    /*# server.essp.issue.issue.resolution.logic.LgIssueResolution lnkLgIssueResolution; */
}
