package server.essp.pms.pbs.pbsAndFiles.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import server.essp.pms.pbs.pbsAndFiles.logic.LgPbsList;
import server.framework.action.AbstractBusinessAction;
import server.framework.common.BusinessException;
import c2s.essp.pms.pbs.pbsAndFiles.DtoPbsAssign;
import c2s.dto.IDto;

public class AcPbsAdd extends AbstractBusinessAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws BusinessException {
         InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        DtoPbsAssign dto = (DtoPbsAssign) inputInfo.getInputObj("dto");

        LgPbsList logic = new LgPbsList();
        logic.setDbAccessor(this.getDbAccessor());
        logic.add(dto);

        dto.setOp(IDto.OP_NOCHANGE);
        returnInfo.setReturnObj("dto", dto);
    }
}
