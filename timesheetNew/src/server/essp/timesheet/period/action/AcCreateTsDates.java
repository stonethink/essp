package server.essp.timesheet.period.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import c2s.essp.timesheet.tsdates.DtoTsDates;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheetPeriod;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.period.service.ITsDatesService;
import server.framework.common.BusinessException;

public class AcCreateTsDates extends AbstractESSPAction {

	public void executeAct(HttpServletRequest arg0, HttpServletResponse arg1,
			TransactionData data) throws BusinessException {
		DtoTsDates dtoTsDates = (DtoTsDates) data.getInputInfo()
		                      .getInputObj(DtoTimeSheetPeriod.CREATE_CONDITION);
		ITsDatesService service = (ITsDatesService) this.getBean("tsDatesService");
		service.createTsDates(dtoTsDates);
		
	}

}
