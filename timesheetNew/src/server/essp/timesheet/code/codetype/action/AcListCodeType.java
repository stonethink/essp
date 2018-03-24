package server.essp.timesheet.code.codetype.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import server.essp.timesheet.code.codetype.service.ICodeTypeService;
import java.util.List;
import c2s.essp.timesheet.code.DtoCodeType;

public class AcListCodeType extends AbstractESSPAction {

    /**
     * 列出所有Code type
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData data) throws BusinessException {
    	boolean isLeaveType = (Boolean) data.getInputInfo().getInputObj(DtoCodeType.DTO_IS_LEAVE_TYPE);
        ICodeTypeService service = (ICodeTypeService) this.getBean("codeTypeService");
        List list = service.listCodeType(isLeaveType);
        data.getReturnInfo().setReturnObj(DtoCodeType.DTO_LIST, list);
    }
}
