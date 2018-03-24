/*
 * Created on 2008-5-26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.step.monitoring.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.step.monitoring.service.IMonitoringService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
import c2s.essp.timesheet.step.monitoring.DtoMonitoringQuery;

public class AcLoadProject extends AbstractESSPAction {

    @Override
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {

        IMonitoringService service = (IMonitoringService) this
                .getBean("monitoringService");
        data.getReturnInfo().setReturnObj(DtoMonitoringQuery.DTO_PROJECT_LIST,
                service.getProjectList(this.getUser().getUserLoginId()));
    }

}