package server.essp.pms.activity.process.guideline.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import c2s.essp.common.code.DtoKey;
import c2s.essp.pms.wbs.DtoWbsGuideLine;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.pms.activity.process.guideline.logic.LgActivityGuideLine;
import server.framework.common.BusinessException;

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
public class AcActivityGLLoad extends AbstractESSPAction {
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
        Long acntRid = (Long) data.getInputInfo().getInputObj("accountRid");
        Long activityRid = (Long) data.getInputInfo().getInputObj("activityRid");
        DtoWbsGuideLine dto = null;
        if(acntRid != null && activityRid != null) {
            LgActivityGuideLine lg = new LgActivityGuideLine();
            dto = lg.getActivityGuideLineDto(acntRid, activityRid);
        }
        data.getReturnInfo().setReturnObj(DtoKey.DTO, dto);
    }
}
