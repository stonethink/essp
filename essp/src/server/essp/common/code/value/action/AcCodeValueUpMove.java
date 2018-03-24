package server.essp.common.code.value.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.common.code.DtoCodeValue;
import c2s.essp.common.code.DtoKey;
import server.essp.common.code.value.logic.LgCodeValueMove;
import server.framework.action.AbstractBusinessAction;
import server.framework.common.BusinessException;

public class AcCodeValueUpMove extends AbstractBusinessAction{

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws BusinessException {

        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        DtoCodeValue dtoCodeValue = (DtoCodeValue)inputInfo.getInputObj(DtoKey.DTO);

        LgCodeValueMove logic = new LgCodeValueMove();
        logic.upMove(dtoCodeValue);
    }
}
