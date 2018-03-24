package server.essp.ebs.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.ebs.DtoEbsEbs;
import server.essp.ebs.logic.LgEbs;
import server.framework.action.AbstractBusinessAction;
import server.framework.common.BusinessException;

public class AcEbsDelete extends AbstractBusinessAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws BusinessException {

        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        DtoEbsEbs dto = (DtoEbsEbs) inputInfo.getInputObj("dto");
        LgEbs logic = new LgEbs();
        logic.setDbAccessor(this.getDbAccessor());
        logic.delete(dto);
    }
}
