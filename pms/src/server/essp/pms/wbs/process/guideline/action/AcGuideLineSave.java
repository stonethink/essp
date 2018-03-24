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
 * @author wuyi
 * @version 1.0
 */
public class AcGuideLineSave extends AbstractESSPAction {
    public AcGuideLineSave() {
    }


    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
        BusinessException {

        LgWbsGuideLine lg = new LgWbsGuideLine();
        DtoWbsGuideLine dto=(DtoWbsGuideLine) data.getInputInfo().getInputObj("guideLineInfo");
        lg.saveWbsGuideLineInfo(dto);

    }
}
