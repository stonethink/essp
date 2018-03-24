package server.essp.tc.hrmanage.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.essp.tc.nonattend.DtoNonattend;
import c2s.essp.common.code.DtoKey;
import server.essp.tc.hrmanage.logic.LgNonattend;

public class AcNonattendDelete extends AbstractESSPAction {

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
    public void executeAct(HttpServletRequest request, HttpServletResponse response, TransactionData data) throws BusinessException {
          DtoNonattend dto = (DtoNonattend) data.getInputInfo().getInputObj(DtoKey.DTO);
          LgNonattend lgNonattend=new LgNonattend();
          lgNonattend.delete(dto);

    }
}
