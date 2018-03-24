package server.essp.issue.issue.resolution.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import server.essp.issue.issue.resolution.logic.LgIssueResolution;
import server.essp.issue.issue.resolution.form.AfIssueGeneralResolution;
import server.essp.issue.common.action.AbstractISSUEAction;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class AcGeneralResolution extends AbstractISSUEAction{
    /**
     * 根据传入AfIssueResolution分析Issue
     * Call：LgIssueResolution.resolution()
     * ForwardId:success
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
        BusinessException {
          String rid = request.getParameter("id");
          LgIssueResolution logic=new LgIssueResolution();
          AfIssueGeneralResolution webVo = logic.GeneralResolution(rid);
          request.setAttribute("webVo",webVo);
    }

    /** @link dependency */
    /*# server.essp.issue.issue.resolution.logic.LgIssueResolution lnkLgIssueResolution; */

}
