package server.essp.pms.quality.goal.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
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
public class AcQualityGoalReleaseList extends AbstractESSPAction {
    public AcQualityGoalReleaseList() {
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
                           HttpServletResponse response,
                           TransactionData transData) throws
        BusinessException {
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        Long goalRid = (Long) inputInfo.getInputObj("goalRid");

        LgQualityGoalRelease lg = new LgQualityGoalRelease();

        List qualityGoalReleaseList = lg.listReleaseRecord(goalRid);

        returnInfo.setReturnObj("qualityGoalReleaseList",
                                qualityGoalReleaseList);

    }
}
