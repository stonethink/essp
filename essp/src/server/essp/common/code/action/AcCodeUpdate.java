package server.essp.common.code.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.common.code.DtoCode;
import c2s.essp.common.code.DtoKey;
import server.essp.common.code.logic.LgCode;
import server.framework.action.AbstractBusinessAction;
import server.framework.common.BusinessException;

public class AcCodeUpdate extends AbstractBusinessAction{

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws BusinessException {

        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        DtoCode dtoCode = (DtoCode)inputInfo.getInputObj(DtoKey.DTO);

        LgCode logic = new LgCode();
        logic.update(dtoCode);

        returnInfo.setReturnObj(DtoKey.DTO, dtoCode);
    }
}
