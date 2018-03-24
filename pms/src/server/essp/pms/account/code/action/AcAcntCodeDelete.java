package server.essp.pms.account.code.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.common.code.DtoCodeValueChoose;
import c2s.essp.common.code.DtoKey;
import server.essp.pms.account.code.logic.LgAcntCode;
import server.framework.action.AbstractBusinessAction;
import server.framework.common.BusinessException;

public class AcAcntCodeDelete extends AbstractBusinessAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws BusinessException {

        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        Long acntRid = (Long) inputInfo.getInputObj(DtoKey.ACNT_RID);
        DtoCodeValueChoose dto = (DtoCodeValueChoose) inputInfo.getInputObj(DtoKey.DTO);

        LgAcntCode logic = new LgAcntCode(acntRid);
        logic.delete(dto);
    }
}
