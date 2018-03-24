package server.essp.timesheet.report.timesheet.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.report.timesheet.service.ITsReportService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
import c2s.essp.timesheet.report.DtoTsDetailReport;

public class AcGetProjectsByDept extends AbstractESSPAction {

	@Override
	public void executeAct(HttpServletRequest arg0, HttpServletResponse arg1,
			TransactionData data) throws BusinessException {
		ITsReportService service = (ITsReportService) this.getBean("tsReportService");
        service.setExcelDto(false);
        String deptId = (String) data.getInputInfo()
                                     .getInputObj(DtoTsDetailReport.DTO_DEPT_ID);
        data.getReturnInfo().setReturnObj(DtoTsDetailReport.DTO_PROJECT_LIST, 
        		                              service.getProjectsForPmo(deptId));
	}

}
