package server.essp.pms.modifyplan.action;

import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
public class AcExportPlanPre extends AbstractESSPAction {
    public AcExportPlanPre() {
    }

    /**
     * executeAct
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     * @todo Implement this server.framework.action.AbstractBusinessAction
     *   method
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
        BusinessException {
        HttpSession session = request.getSession();
        session.setAttribute("rootNode",
                             data.getInputInfo().getInputObj("rootNode"));
        session.setAttribute("acntRid",
                             data.getInputInfo().getInputObj("acntRid"));
        session.setAttribute("imageHeight",
                             data.getInputInfo().getInputObj("imageHeight"));
        session.setAttribute("imageWidth",
                             data.getInputInfo().getInputObj("imageWidth"));
        session.setAttribute("filter",
                             data.getInputInfo().getInputObj("filter"));
        session.setAttribute("image",
                             data.getInputInfo().getInputObj("image"));
    }
}
