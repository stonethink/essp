package server.essp.pms.wbs.process.guideline.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.pms.wbs.process.guideline.logic.LgWbsGuideLine;
import server.framework.common.BusinessException;

/**
 * <p>Title: </p>
 *
 * <p>Description: 获得对活动的描述</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author wuyi
 * @version 1.0
 */
public class AcGuideLineDescription extends AbstractESSPAction {
    public AcGuideLineDescription() {
    }


    public void executeAct(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           TransactionData transactionData) throws
        BusinessException {

        Long acntRid = (Long) transactionData.getInputInfo().getInputObj("accountRid");
        Long wbsRid = (Long) transactionData.getInputInfo().getInputObj("wbsRid");
        String description = null;
        if(acntRid != null &&  wbsRid != null) {
            LgWbsGuideLine lg = new LgWbsGuideLine();
            description = lg.getWbsDescription(acntRid, wbsRid);
        }
        transactionData.getReturnInfo().setReturnObj("description", description);

    }
}
