package server.essp.pms.wbs.process.checklist.action;

import c2s.dto.ReturnInfo;
import server.essp.pms.wbs.process.checklist.logic.LgQaCheckAction;
import c2s.dto.InputInfo;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import c2s.essp.pms.qa.DtoQaCheckAction;
import server.framework.common.BusinessException;
import javax.servlet.http.HttpServletResponse;
import c2s.essp.pms.qa.DtoQaCheckPoint;

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
public class AcGetQaCheckActionCode extends AbstractESSPAction {
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
        ReturnInfo returnInfo = transData.getReturnInfo();
        DtoQaCheckPoint cp = (DtoQaCheckPoint) inputInfo.getInputObj(
            DtoQaCheckPoint.DTO_PMS_CHECK_POINT);
        LgQaCheckAction lg = new LgQaCheckAction();
        Long newRid = lg.getQaCheckActionRid(cp.getAcntRid());
        returnInfo.setReturnObj(DtoQaCheckAction.DTO_PMS_CHECK_ACTION_RID,
                                newRid);
    }

}
