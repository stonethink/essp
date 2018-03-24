package server.essp.timesheet.rmmaint.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import c2s.essp.timesheet.rmmaint.DtoRmMaint;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.rmmaint.service.IRmMaintService;
import server.framework.common.BusinessException;

public class AcGetRmByLoginId extends AbstractESSPAction {


	public void executeAct(HttpServletRequest arg0, HttpServletResponse arg1,
			TransactionData data) throws BusinessException {
			String loginId = (String) data.getInputInfo()
										.getInputObj(DtoRmMaint.DTO_LOGINID);
			IRmMaintService service = (IRmMaintService) 
			                            this.getBean("rmMaintService");
			String rmId = service.getRmByLoginId(loginId);
			data.getReturnInfo().setReturnObj(DtoRmMaint.DTO_RMID, rmId);
	}

}
