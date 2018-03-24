package server.essp.tc.hrmanage.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.essp.tc.nonattend.DtoNonattend;
import server.essp.tc.hrmanage.logic.LgNonattend;
import c2s.essp.common.code.DtoKey;

public class AcNonattendSave extends AbstractESSPAction {
    public AcNonattendSave() {
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
    public void executeAct(HttpServletRequest request, HttpServletResponse response, TransactionData data) throws BusinessException {
        DtoNonattend dto = (DtoNonattend) data.getInputInfo().getInputObj(DtoKey.DTO);
       LgNonattend lgNonattend=new LgNonattend();
       if(dto.getRid()==null){
           lgNonattend.add(dto);
       }
       else{
           lgNonattend.update(dto);
       }

    }
}
