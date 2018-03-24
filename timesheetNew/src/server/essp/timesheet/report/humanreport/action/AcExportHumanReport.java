package server.essp.timesheet.report.humanreport.action;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.common.excelUtil.AbstractExcelAction;
import server.essp.timesheet.report.humanreport.exporter.HumanReportExporter;
import server.essp.timesheet.report.humanreport.service.IHuamnReportService;
import server.essp.timesheet.rmmaint.service.IRmMaintService;
import server.framework.common.BusinessException;
import c2s.essp.common.user.DtoRole;
import c2s.essp.common.user.DtoUser;
import c2s.essp.timesheet.report.DtoHumanReport;
import c2s.essp.timesheet.report.DtoHumanTimes;

import com.wits.util.*;

public class AcExportHumanReport extends AbstractExcelAction {

	public void execute(HttpServletRequest request,
			HttpServletResponse response, OutputStream os, Parameter param)
			throws Exception {
		ThreadVariant thread = ThreadVariant.getInstance();
        DtoUser user = (DtoUser)request.getSession().getAttribute(DtoUser.SESSION_USER);
        thread.set(DtoUser.SESSION_USER, user);
        List roleList = (List)user.getRoles();
        boolean isPMO = false;
           for(int i=0;i<roleList.size();i++){
               DtoRole role = (DtoRole)roleList.get(i);
               if(role!=null){
                   String roleId = role.getRoleId();
                   if(roleId.equals(DtoRole.ROLE_SYSUSER)
                    ||roleId.equals(DtoRole.ROLE_HAP)){
                	   isPMO = true;
                       break;
                   }
               }
        }
        String begin = (String) param.get(DtoHumanReport.DTO_BEGIN);
        String end = (String) param.get(DtoHumanReport.DTO_END);
        String site = "";
        
        IHuamnReportService service = (IHuamnReportService) this.getBean("humanReportService");
        service.setExcelDto(true);
        service.clearMap();
        if(isPMO) {
        	site = (String) param.get(DtoHumanReport.DTO_SITE);
        } else {
            IRmMaintService rmMaintService = (IRmMaintService) this.getBean("rmMaintService");
      	    site = rmMaintService.getSite(user.getUserLoginId());
        }
        Map resultMap = service.queryHumanReport(comDate.toDate(begin), comDate.toDate(end), site);
        HumanReportExporter exporter = new HumanReportExporter();
        Parameter inputData = new Parameter();
        inputData.put(DtoHumanReport.DTO_QUERY_RESULT, (List)resultMap.get(DtoHumanReport.DTO_QUERY_RESULT));
        inputData.put(DtoHumanTimes.DTO_QUERY_LIST, (List)resultMap.get(DtoHumanTimes.DTO_QUERY_LIST));
        inputData.put(DtoHumanReport.DTO_BEGIN, begin);
        inputData.put(DtoHumanReport.DTO_END, end);
        try{
            exporter.webExport(response, os, inputData);
        } catch(Exception e){
                throw new BusinessException("", "Export Error!", e);
        }
	}



}
