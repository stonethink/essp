package server.essp.timesheet.tsmodify.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.tsmodify.service.ITsModifyService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
import c2s.essp.timesheet.tsmodify.DtoTsModify;

public class AcQuery extends AbstractESSPAction {

	public void executeAct(HttpServletRequest request, HttpServletResponse response,
			TransactionData data) throws BusinessException {
		DtoTsModify dto = (DtoTsModify) data.getInputInfo().getInputObj(DtoTsModify.DTO_CONDITION);
		ITsModifyService service = (ITsModifyService) this.getBean("tsModify");
		List list = service.queryByCondition(dto);
		data.getReturnInfo().setReturnObj(DtoTsModify.DTO_QUERY_RESULT, list);
	}

}
