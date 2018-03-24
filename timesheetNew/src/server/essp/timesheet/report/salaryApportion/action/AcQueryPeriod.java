/*
 * Created on 2008-1-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.report.salaryApportion.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.report.salaryApportion.service.ISalaryWorkHourService;
import c2s.dto.TransactionData;
import c2s.essp.timesheet.report.DtoSalaryWkHrQuery;


public class AcQueryPeriod extends AbstractESSPAction {
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response,
            TransactionData data)  {
         ISalaryWorkHourService lg = (ISalaryWorkHourService) this.getBean("salaryWkHrService");
         String loginId = this.getUser().getUserLoginId();
         DtoSalaryWkHrQuery dtoQuery = (DtoSalaryWkHrQuery) data.getInputInfo().
                         getInputObj(DtoSalaryWkHrQuery.DTO_SALARY_QUERY);
        
         List tsList = lg.queryByCondition(loginId,dtoQuery);
         data.getReturnInfo().setReturnObj(DtoSalaryWkHrQuery.DTO_QUERY_LIST,tsList);
    }
}
