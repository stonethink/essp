package server.essp.timesheet.period.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheetPeriod;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.period.service.ITsDatesService;
import server.framework.common.BusinessException;

public class AcAddTsDates extends AbstractESSPAction {

	public void executeAct(HttpServletRequest arg0, HttpServletResponse arg1,
			TransactionData data) throws BusinessException {
		DtoTimeSheetPeriod dtoPeriod = 
			(DtoTimeSheetPeriod) data.getInputInfo()
			                           .getInputObj(DtoTimeSheetPeriod.DTO_PERIOD);
		ITsDatesService service = (ITsDatesService) this.getBean("tsDatesService");
		service.addPeriod(dtoPeriod);
	}

}
