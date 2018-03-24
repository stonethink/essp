package server.essp.timesheet.code.codevalue.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import c2s.essp.timesheet.code.DtoCodeValue;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.code.codevalue.service.ICodeValueService;
import server.framework.common.BusinessException;

public class AcMoveLeftCodeValue extends AbstractESSPAction {

	public void executeAct(HttpServletRequest request, HttpServletResponse response,
			TransactionData data) throws BusinessException {
		 DtoCodeValue dto = (DtoCodeValue) data.getInputInfo().getInputObj(DtoCodeValue.DTO);
		 ICodeValueService service = (ICodeValueService) this.getBean("codeValueService");
		 service.moveLeftCodeValue(dto);
	}

}
