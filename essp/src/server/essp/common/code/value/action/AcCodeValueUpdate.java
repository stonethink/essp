package server.essp.common.code.value.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.ITreeNode;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.common.code.DtoKey;
import server.framework.action.AbstractBusinessAction;
import server.framework.common.BusinessException;
import server.essp.common.code.value.logic.LgCodeValue;
import c2s.essp.common.code.DtoCodeValue;

public class AcCodeValueUpdate extends AbstractBusinessAction{

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws BusinessException {

        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        DtoCodeValue dto = (DtoCodeValue)inputInfo.getInputObj(DtoKey.DTO);

        LgCodeValue logic = new LgCodeValue();
        logic.update(dto);

        returnInfo.setReturnObj(DtoKey.DTO, dto);
    }
}
