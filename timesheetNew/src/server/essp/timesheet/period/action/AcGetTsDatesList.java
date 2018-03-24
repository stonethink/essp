package server.essp.timesheet.period.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.period.service.ITsDatesService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheetPeriod;

public class AcGetTsDatesList extends AbstractESSPAction {

	public void executeAct(HttpServletRequest arg0, HttpServletResponse arg1,
			TransactionData data) throws BusinessException {
		
		ITsDatesService service = (ITsDatesService) 
												this.getBean("tsDatesService");
		List list = service.getTsDatesList();
		data.getReturnInfo().setReturnObj(DtoTimeSheetPeriod.DTO_TsDastesList,
										  list);
	}

}
