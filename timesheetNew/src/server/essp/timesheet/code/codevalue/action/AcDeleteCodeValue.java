package server.essp.timesheet.code.codevalue.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import server.essp.timesheet.code.codevalue.service.ICodeValueService;
import c2s.essp.timesheet.code.DtoCodeValue;

public class AcDeleteCodeValue extends AbstractESSPAction {

    /**
     * É¾³ýCode Value
     *
     * @param httpServletRequest HttpServletRequest
     * @param httpServletResponse HttpServletResponse
     * @param transactionData TransactionData
     * @throws BusinessException
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData data) throws BusinessException {

        DtoCodeValue dto = (DtoCodeValue) data.getInputInfo().getInputObj(DtoCodeValue.DTO);
        ICodeValueService service = (ICodeValueService) this.getBean("codeValueService");
        service.delete(dto);
    }
}
