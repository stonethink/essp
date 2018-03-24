package server.essp.timesheet.period.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.period.service.ITsDatesService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheetPeriod;

public class AcDeleteTsDates extends AbstractESSPAction {

	public void executeAct(HttpServletRequest arg0, HttpServletResponse arg1,
			TransactionData data) throws BusinessException {
		List tsDates = (List) data.getInputInfo()
		                            .getInputObj(DtoTimeSheetPeriod.DTO_TsDastesList);
		ITsDatesService service = (ITsDatesService) this.getBean("tsDatesService");
		service.detelePeriod(tsDates);
	}

}
