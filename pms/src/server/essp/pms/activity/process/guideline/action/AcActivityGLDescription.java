package server.essp.pms.activity.process.guideline.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.pms.activity.process.guideline.logic.LgActivityGuideLine;
import server.framework.common.BusinessException;

/**
 * <p>Title: </p>
 *
 * <p>Description: 得到活动的description</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author wuyi
 * @version 1.0
 */
public class AcActivityGLDescription extends AbstractESSPAction {
    public AcActivityGLDescription() {
    }


    public void executeAct(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           TransactionData transactionData) throws
        BusinessException {
        Long acntRid = (Long) transactionData.getInputInfo().getInputObj("accountRid");
        Long activityRid = (Long) transactionData.getInputInfo().getInputObj("activityRid");
        String description = null;
        if(acntRid != null && activityRid != null) {
            LgActivityGuideLine lg = new LgActivityGuideLine();
            description = lg.getActivityDecription(acntRid, activityRid);
        }
        transactionData.getReturnInfo().setReturnObj("description", description);

    }
}
