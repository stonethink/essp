package server.essp.pms.wbs.process.checklist.action;

import c2s.dto.InputInfo;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import javax.servlet.http.HttpServletResponse;
import c2s.essp.pms.qa.DtoQaCheckPoint;
import c2s.essp.pms.qa.DtoQaCheckAction;
import server.essp.pms.wbs.process.checklist.logic.LgQaCheckAction;

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
public class AcQaCheckActionDelete extends AbstractESSPAction {
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
                           HttpServletResponse response,
                           TransactionData transData) throws
        BusinessException {
        InputInfo inputInfo = transData.getInputInfo();

        DtoQaCheckAction dto = (DtoQaCheckAction) inputInfo.getInputObj(
            DtoQaCheckAction.DTO_PMS_CHECK_ACTION);
        LgQaCheckAction lg = new LgQaCheckAction();

        lg.deleteCheckAction(dto);

    }
}
