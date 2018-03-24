package server.essp.timesheet.report.humanreport.action;

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

public class AcGetSites extends AbstractESSPAction {

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
    
        IHuamnReportService service = (IHuamnReportService) this.getBean("humanReportService");
    	data.getReturnInfo().setReturnObj(DtoHumanReport.DTO_SITESLIST, service.getSites());
        
        IRmMaintService rmMaintService = (IRmMaintService) this.getBean("rmMaintService");
 	    String site = rmMaintService.getSite(this.getUser().getUserLoginId());
        data.getReturnInfo().setReturnObj(DtoHumanReport.DTO_IS_PMO, isPMO);
        if(site != null) {
        	data.getReturnInfo().setReturnObj(DtoHumanReport.DTO_SITE, site.toUpperCase());
        } else {
        	data.getReturnInfo().setReturnObj(DtoHumanReport.DTO_SITE, "");
        }
        
	}

}
