package server.essp.timesheet.report.humanreport.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.report.humanreport.service.IHuamnReportService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
import c2s.essp.timesheet.report.DtoHumanTimes;

public class AcSendMail extends AbstractESSPAction {

	@Override
	public void executeAct(HttpServletRequest arg0, HttpServletResponse arg1,
			TransactionData data) throws BusinessException {
		List sendList = (List) data.getInputInfo().getInputObj(DtoHumanTimes.DTO_SEND_LIST);
		IHuamnReportService service = (IHuamnReportService) this.getBean("humanReportService");
		service.sendMails(sendList);

	}

}
