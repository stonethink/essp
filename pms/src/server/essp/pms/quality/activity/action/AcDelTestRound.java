package server.essp.pms.quality.activity.action;

import server.essp.framework.action.AbstractESSPAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import c2s.essp.pms.quality.activity.DtoTestRound;
import server.essp.pms.quality.activity.logic.LgTestRound;

public  class AcDelTestRound extends AbstractESSPAction {
    public AcDelTestRound() {
    }

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
        BusinessException {

        InputInfo inputInfo = data.getInputInfo();
        ReturnInfo returnInfo = data.getReturnInfo();
        DtoTestRound dtoTestRound = (DtoTestRound) inputInfo.getInputObj("dtoTestRound");
        LgTestRound lgTestRound = new LgTestRound();
        lgTestRound.deleteTestRound(dtoTestRound);

    }
}
