package server.essp.pms.account.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.essp.pms.account.DtoPmsAcnt;
import c2s.dto.ReturnInfo;
import c2s.essp.pms.account.DtoAcntKEY;
import c2s.dto.InputInfo;
import server.essp.pms.account.logic.LgOsp;
import c2s.dto.IDto;

public class AcOspSaveAs extends AbstractESSPAction {
    public AcOspSaveAs() {
    }

    /**
     * executeAct
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     * @todo Implement this server.framework.action.AbstractBusinessAction
     *   method
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws
        BusinessException {
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

       DtoPmsAcnt dto = (DtoPmsAcnt) inputInfo.getInputObj(DtoAcntKEY.DTO);
       LgOsp lgOsp = new LgOsp();
       lgOsp.ospSaveAs(dto);

       dto.setOp(IDto.OP_NOCHANGE);
       returnInfo.setReturnObj(DtoAcntKEY.DTO, dto);
    }
}
