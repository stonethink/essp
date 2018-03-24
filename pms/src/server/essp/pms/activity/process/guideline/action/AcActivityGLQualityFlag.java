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
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author wuyi
 * @version 1.0
 */
public class AcActivityGLQualityFlag extends AbstractESSPAction{
    public AcActivityGLQualityFlag() {
    }

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
        BusinessException {
        Long acntRid=(Long) data.getInputInfo().getInputObj("acntRid");
        Long activityRid=(Long) data.getInputInfo().getInputObj("activityRid");
        LgActivityGuideLine lg = new LgActivityGuideLine();
        String flag = lg.getQualityFlag(acntRid,activityRid);

        data.getReturnInfo().setReturnObj("flag",flag);

    }
}
