package server.essp.timesheet.code.codetype.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import server.essp.timesheet.code.codetype.service.ICodeTypeService;
import c2s.essp.timesheet.code.DtoCodeType;

public class AcDeleteCodeType extends AbstractESSPAction {

    /**
     * É¾³ýCode Type
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData data) throws BusinessException {

        DtoCodeType dto = (DtoCodeType) data.getInputInfo().getInputObj(DtoCodeType.DTO);
        ICodeTypeService service = (ICodeTypeService) this.getBean("codeTypeService");
        service.deleteCodeType(dto);
    }
}
