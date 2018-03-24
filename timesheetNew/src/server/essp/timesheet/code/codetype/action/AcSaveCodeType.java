package server.essp.timesheet.code.codetype.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.essp.timesheet.code.DtoCodeType;
import server.essp.timesheet.code.codetype.service.ICodeTypeService;

public class AcSaveCodeType extends AbstractESSPAction {

    /**
     * ±£¥ÊCode Type
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
        service.saveCodeType(dto);
        data.getReturnInfo().setReturnObj(DtoCodeType.DTO, dto);
    }

}
