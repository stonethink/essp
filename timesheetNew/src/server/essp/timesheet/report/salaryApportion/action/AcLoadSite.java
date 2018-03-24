/*
 * Created on 2008-5-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.report.salaryApportion.action;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.report.salaryApportion.service.ISalaryWorkHourService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
import c2s.essp.timesheet.report.DtoSalaryWkHrQuery;

public class AcLoadSite extends AbstractESSPAction {
        public void executeAct(HttpServletRequest request,
                HttpServletResponse response,TransactionData data)
                throws BusinessException {
        ISalaryWorkHourService lg = (ISalaryWorkHourService) this.getBean("salaryWkHrService");
         String loginId = this.getUser().getUserLoginId();
        List rolesList = this.getUser().getRoles();
        Map siteMap = lg.getSiteList(rolesList,loginId);
        Vector siteVec = (Vector)siteMap.get(DtoSalaryWkHrQuery.DTO_SITE_LIST);
        Boolean isPMO = (Boolean)siteMap.get(DtoSalaryWkHrQuery.DTO_IS_PMO);
        data.getReturnInfo().setReturnObj(DtoSalaryWkHrQuery.DTO_SITE_LIST,siteVec);
        data.getReturnInfo().setReturnObj(DtoSalaryWkHrQuery.DTO_IS_PMO,isPMO);
    }
}
