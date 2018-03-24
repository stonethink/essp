package server.essp.ebs.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.IDto;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.ebs.DtoPmsAcnt;
import server.essp.ebs.logic.LgAcnt;
import server.framework.action.AbstractBusinessAction;
import server.framework.common.BusinessException;

public class AcAcntUpdate extends AbstractBusinessAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws BusinessException {

        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        DtoPmsAcnt dto = (DtoPmsAcnt) inputInfo.getInputObj("dto");
        LgAcnt logic = new LgAcnt();
        logic.setDbAccessor(this.getDbAccessor());
        logic.update(dto);

        returnInfo.setReturnObj("dto", dto);
    }
}
