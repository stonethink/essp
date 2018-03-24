package server.essp.timesheet.rmmaint.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.rmmaint.service.IRmMaintService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
import c2s.essp.timesheet.rmmaint.DtoRmMaint;

public class AcGetUserInfo extends AbstractESSPAction {

	public void executeAct(HttpServletRequest arg0, HttpServletResponse arg1,
			TransactionData data) throws BusinessException {
		String loginIds = (String) data.getInputInfo()
		                           .getInputObj(DtoRmMaint.DTO_LOGINIDS);
		IRmMaintService service = (IRmMaintService) 
		                                   this.getBean("rmMaintService");
		List result = service.getUserInfo(loginIds);
		data.getReturnInfo().setReturnObj(DtoRmMaint.DTO_RESULTS, result);
	}

}
