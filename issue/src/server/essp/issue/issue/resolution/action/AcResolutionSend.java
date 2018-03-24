package server.essp.issue.issue.resolution.action;


import server.framework.common.BusinessException;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import javax.servlet.http.HttpServletResponse;
import server.essp.issue.issue.resolution.form.AfIssueGeneralResolution;
import server.essp.issue.common.action.AbstractISSUEAction;
import server.essp.issue.issue.resolution.logic.LgIssueResolution;

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
public class AcResolutionSend extends AbstractISSUEAction {
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
         AfIssueGeneralResolution form = (AfIssueGeneralResolution)this.getForm();
         LgIssueResolution logic = new LgIssueResolution();
         logic.ResolutionSend(form);

     }

     /** @link dependency */
    /*# server.essp.issue.issue.resolution.logic.LgIssueResolution lnkLgIssueResolution; */
}
