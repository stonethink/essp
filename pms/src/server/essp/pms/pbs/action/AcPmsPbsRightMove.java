package server.essp.pms.pbs.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.IDto;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.pms.pbs.DtoPmsPbs;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.pms.pbs.logic.LgPmsPbsMove;
import server.framework.common.BusinessException;

public class AcPmsPbsRightMove extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws BusinessException {
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        DtoPmsPbs dto = (DtoPmsPbs) inputInfo.getInputObj("dto");
        LgPmsPbsMove logic = new LgPmsPbsMove();
        logic.rightMove(dto);

        dto.setOp(IDto.OP_NOCHANGE);
        returnInfo.setReturnObj("dto", dto);

    }
}
