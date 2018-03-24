package server.essp.timesheet.rmmaint.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import c2s.essp.timesheet.rmmaint.DtoRmMaint;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.rmmaint.service.IRmMaintService;
import server.framework.common.BusinessException;

public class AcSaveRm extends AbstractESSPAction {

	public void executeAct(HttpServletRequest arg0, HttpServletResponse arg1,
			TransactionData data) throws BusinessException {
		String loginId = (String) data.getInputInfo()
		                             .getInputObj(DtoRmMaint.DTO_LOGINID);
		String rmId = (String) data.getInputInfo()
		                             .getInputObj(DtoRmMaint.DTO_RMID);
		IRmMaintService service = (IRmMaintService) 
		                              this.getBean("rmMaintService");
		service.saveOrUpdateRm(loginId, rmId);
	}

}
