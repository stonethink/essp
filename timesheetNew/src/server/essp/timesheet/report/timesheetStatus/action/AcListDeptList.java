/*
 * Created on 2007-11-29
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.report.timesheetStatus.action;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.report.timesheet.service.ITsReportService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
import c2s.essp.common.user.DtoRole;
import c2s.essp.timesheet.report.DtoTsDetailReport;
import c2s.essp.timesheet.report.DtoTsStatusQuery;
/**
 * <p>Title: AcListDeptList</p>
 *
 * <p>Description: 得到部門集合</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author tbh
 * @version 1.0
 */
  public class AcListDeptList  extends AbstractESSPAction {
        public void executeAct(HttpServletRequest request,
                HttpServletResponse response,TransactionData data)
                throws BusinessException {
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
             Map result = service.getDeptsPersons(this.getUser().getUserLoginId());
             data.getReturnInfo().setReturnObj(DtoTsStatusQuery.DTO_DEPTLIST,
                                               result.get(DtoTsDetailReport.DTO_DEPT_LIST));
         } else if(isPMO) {
             Vector depts = service.getDeptsForPmo(this.getUser().getUserLoginId(), roleId);
             data.getReturnInfo().setReturnObj(DtoTsStatusQuery.DTO_DEPTLIST, depts);
         }
     }
 }
