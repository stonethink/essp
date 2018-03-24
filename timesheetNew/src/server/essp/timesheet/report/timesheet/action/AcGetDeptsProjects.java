package server.essp.timesheet.report.timesheet.action;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.report.timesheet.service.ITsReportService;
import server.framework.common.BusinessException;
import c2s.dto.DtoComboItem;
import c2s.dto.TransactionData;
import c2s.essp.common.user.DtoRole;
import c2s.essp.timesheet.report.DtoTsDetailReport;

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
public class AcGetDeptsProjects extends AbstractESSPAction {
    /**
     * executeAct
     *
     * @param httpServletRequest HttpServletRequest
     * @param httpServletResponse HttpServletResponse
     * @param transactionData TransactionData
     * @throws BusinessException
     * @todo Implement this server.framework.action.AbstractBusinessAction
     *   method
     */
    public void executeAct(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           TransactionData data) throws
            BusinessException {
        List roleList = (List)this.getUser().getRoles();
        boolean isPMO = false;
        String roleIdTmp = "";
        String roleId = "";
           for(int i=0;i<roleList.size();i++){
               DtoRole role = (DtoRole)roleList.get(i);
               if(role!=null){
            	   roleIdTmp = role.getRoleId();
            	   if(roleIdTmp.equals(DtoRole.ROLE_UAP)) {
            		   isPMO = true;
            		   roleId = roleIdTmp;
            	   }
                   if(roleIdTmp.equals(DtoRole.ROLE_SYSUSER)
                    ||roleIdTmp.equals(DtoRole.ROLE_HAP)){
                	   isPMO = true;
                	   roleId = roleIdTmp;
                       break;
                   }
               }
           }
        ITsReportService service = (ITsReportService) this.getBean("tsReportService");
        service.setExcelDto(false);
        data.getReturnInfo().setReturnObj(DtoTsDetailReport.DTO_IS_PMO, isPMO);
        if(!isPMO) {
        	Map result = service.getDeptsProjects(this.getUser().getUserLoginId());
        	data.getReturnInfo().setReturnObj(DtoTsDetailReport.DTO_DEPT_LIST,
                                          result.get(DtoTsDetailReport.DTO_DEPT_LIST));
        	data.getReturnInfo().setReturnObj(DtoTsDetailReport.DTO_PROJECT_LIST,
                                          result.get(DtoTsDetailReport.DTO_PROJECT_LIST));
        	data.getReturnInfo().setReturnObj(DtoTsDetailReport.DTO_RELATION_MAP,
                                          result.get(DtoTsDetailReport.DTO_RELATION_MAP));
        } else if(isPMO) {
        	Vector depts = service.getDeptsForPmo(this.getUser().getUserLoginId(), roleId);
        	data.getReturnInfo().setReturnObj(DtoTsDetailReport.DTO_DEPT_LIST,
        									  depts);
        	Vector projects = new Vector();
        	DtoComboItem item = null;
        	if(depts != null && depts.size() > 0) {
        		item = (DtoComboItem) depts.get(0);
        		projects = service.getProjectsForPmo((String)item.getItemValue());
        	}
        	data.getReturnInfo().setReturnObj(DtoTsDetailReport.DTO_PROJECT_LIST, projects);
        }
    }
}
