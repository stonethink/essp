package server.essp.pms.quality.goal.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.pms.quality.goal.DtoReleaseRecord;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.pms.quality.goal.logic.LgQualityGoalRelease;
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
public class AcQualityGoalReleaseDel extends AbstractESSPAction {
    public AcQualityGoalReleaseDel() {
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
        InputInfo inputInfo = data.getInputInfo();
        ReturnInfo returnInfo = data.getReturnInfo();

        DtoReleaseRecord dtoReleaseRecord = (DtoReleaseRecord) inputInfo.
                                            getInputObj("dtoReleaseRecord");

        LgQualityGoalRelease logic = new LgQualityGoalRelease();
        logic.deleteReleaseRecord(dtoReleaseRecord);

    }
}
