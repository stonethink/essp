package server.essp.timesheet.report.humanreport.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.report.humanreport.service.IHuamnReportService;
import server.essp.timesheet.rmmaint.service.IRmMaintService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
import c2s.essp.common.user.DtoRole;
import c2s.essp.timesheet.report.DtoHumanReport;

public class AcQueryHumanReport extends AbstractESSPAction {

	public void executeAct(HttpServletRequest arg0, HttpServletResponse arg1,
			TransactionData data) throws BusinessException {
		boolean isPMO = false;
		List roleList = (List)this.getUser().getRoles();
        String roleIdTmp = "";
        String roleId = "";
           for(int i=0;i<roleList.size();i++){
               DtoRole role = (DtoRole)roleList.get(i);
               if(role!=null){
            	   roleIdTmp = role.getRoleId();
                   if(roleIdTmp.equals(DtoRole.ROLE_SYSUSER)
                    ||roleIdTmp.equals(DtoRole.ROLE_HAP)){
                	   isPMO = true;
                	   roleId = roleIdTmp;
                       break;
                   }
               }
           }
       Date begin = (Date) data.getInputInfo().getInputObj(DtoHumanReport.DTO_BEGIN);
       Date end = (Date) data.getInputInfo().getInputObj(DtoHumanReport.DTO_END);
      
       IHuamnReportService service = (IHuamnReportService) this.getBean("humanReportService");
       service.setExcelDto(false);
       service.clearMap();
       String site = "";
       if(isPMO) {
    	   site = (String) data.getInputInfo().getInputObj(DtoHumanReport.DTO_SITE);
       } else {
    	   IRmMaintService rmMaintService = (IRmMaintService) this.getBean("rmMaintService");
    	   site = rmMaintService.getSite(this.getUser().getUserLoginId());
       }
       data.getReturnInfo().setReturnObj(DtoHumanReport.DTO_QUERY_RESULT, 
    		                        service.queryHumanReport(begin, end, site));
	}

}
