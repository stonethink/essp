package server.essp.timesheet.period.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheetPeriod;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.period.service.ITsDatesService;
import server.framework.common.BusinessException;

public class AcGetLastTsDate extends AbstractESSPAction {

	public void executeAct(HttpServletRequest arg0, HttpServletResponse arg1,
			TransactionData data) throws BusinessException {
		ITsDatesService service = (ITsDatesService) this.getBean("tsDatesService");
		DtoTimeSheetPeriod dtoPeriod = service.getLastTsDate();
		data.getReturnInfo().setReturnObj(DtoTimeSheetPeriod.DTO_PERIOD, dtoPeriod);
	}

}
