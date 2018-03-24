package server.essp.timesheet.report.timesheet.action;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.essp.common.user.DtoRole;
import c2s.essp.common.user.DtoUser;
import c2s.essp.timesheet.report.DtoQueryCondition;
import c2s.essp.timesheet.report.DtoTsDetailReport;
import com.wits.util.Parameter;
import com.wits.util.ThreadVariant;
import com.wits.util.comDate;
import server.essp.common.excelUtil.AbstractExcelAction;
import server.essp.timesheet.report.timesheet.exporter.TsReportExporter;
import server.essp.timesheet.report.timesheet.service.ITsReportService;
import server.framework.common.BusinessException;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author wenhaizheng
 * @version 1.0
 */
public class AcExportReport extends AbstractExcelAction {
    /**
     * execute
     * @param httpServletRequest HttpServletRequest
     * @param httpServletResponse HttpServletResponse
     * @param outputStream OutputStream
     * @param parameter Parameter
     * @throws Exception
     */
    public void execute(HttpServletRequest request,
                        HttpServletResponse response,
                        OutputStream os, Parameter param) throws
            Exception {
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
                    ||roleId.equals(DtoRole.ROLE_HAP)
                    ||roleId.equals(DtoRole.ROLE_UAP)){
                	   isPMO = true;
                       break;
                   }
               }
        }
        DtoQueryCondition dto = new DtoQueryCondition();
        dto.setDeptId((String)param.get("deptId"));
        dto.setProjectId((String)param.get("projectId"));
        dto.setLoginId((String)param.get("loginId"));
        dto.setQueryWay((String)param.get("queryWay"));
        dto.setBegin(comDate.toDate((String)param.get("begin")));
        dto.setEnd(comDate.toDate((String)param.get("end")));
        String includeSub = (String) param.get("includeSub");
        if("true".equals(includeSub)) {
        	dto.setIncludeSub(true);
        } else if("false".equals(includeSub)){
        	dto.setIncludeSub(false);
        }
        dto.setPmo(isPMO);
        ITsReportService service = (ITsReportService)this.getBean("tsReportService");
        service.setExcelDto(true);
        List list = service.queryForExport(dto, user.getUserLoginId(), isPMO);
        TsReportExporter exporter = new TsReportExporter();
        Parameter inputData = new Parameter();
        inputData.put(DtoTsDetailReport.DTO_QUERY_RESULT, list);
        inputData.put(DtoQueryCondition.DTO_BEGIN, (String)param.get("begin"));
        inputData.put(DtoQueryCondition.DTO_END, (String)param.get("end"));
        try{
            exporter.webExport(response, os, inputData);
        } catch(Exception e){
                throw new BusinessException("", "Export Error!", e);
        }
    }
}
