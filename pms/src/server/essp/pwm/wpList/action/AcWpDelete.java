package server.essp.pwm.wpList.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.pwm.wp.DtoPwWp;
import server.framework.action.AbstractBusinessAction;
import server.framework.common.BusinessException;
import server.essp.pwm.wpList.logic.LgWpList;

public class AcWpDelete extends AbstractBusinessAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws BusinessException {

        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        DtoPwWp dto = (DtoPwWp)inputInfo.getInputObj("dto");


        LgWpList lgWpList = new LgWpList();
        lgWpList.delete(dto);
    }
}
