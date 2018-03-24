package server.essp.timesheet.rmmaint.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import c2s.essp.timesheet.rmmaint.DtoRmMaint;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.rmmaint.service.IRmMaintService;
import server.framework.common.BusinessException;

public class AcGetUserList extends AbstractESSPAction {

	public void executeAct(HttpServletRequest arg0, HttpServletResponse arg1,
			TransactionData data) throws BusinessException {
		IRmMaintService service = (IRmMaintService) this
										.getBean("rmMaintService");
		List result = service.getUserList();
		data.getReturnInfo().setReturnObj(DtoRmMaint.DTO_RESULTS, result);

	}

}
