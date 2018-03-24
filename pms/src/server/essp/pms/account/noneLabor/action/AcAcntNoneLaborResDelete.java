package server.essp.pms.account.noneLabor.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.pms.account.DtoAcntKEY;
import c2s.essp.pms.account.DtoNoneLaborRes;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.pms.account.noneLabor.logic.LgAcntNoneLaborRes;
import server.framework.common.BusinessException;

public class AcAcntNoneLaborResDelete extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws BusinessException {
         InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        DtoNoneLaborRes dtoNoneLaborRes = (DtoNoneLaborRes) inputInfo.getInputObj(DtoAcntKEY.DTO);

        LgAcntNoneLaborRes logic = new LgAcntNoneLaborRes();
        logic.delete(dtoNoneLaborRes);
    }
}
