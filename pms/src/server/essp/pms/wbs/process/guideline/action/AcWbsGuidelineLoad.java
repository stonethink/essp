package server.essp.pms.wbs.process.guideline.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import c2s.essp.pms.wbs.DtoWbsGuideLine;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.pms.wbs.process.guideline.logic.LgWbsGuideLine;
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
public class AcWbsGuidelineLoad  extends AbstractESSPAction {
    public void executeAct(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           TransactionData transactionData) throws
        BusinessException {

        Long acntRid = (Long) transactionData.getInputInfo().getInputObj("accountRid");
        Long wbsRid = (Long) transactionData.getInputInfo().getInputObj("wbsRid");
        DtoWbsGuideLine dto = null;
        if(acntRid != null &&  wbsRid != null) {
            LgWbsGuideLine lg = new LgWbsGuideLine();
            dto = lg.getDtoWbsGuideline(acntRid, wbsRid);
        }
        transactionData.getReturnInfo().setReturnObj("dto", dto);

    }

}
