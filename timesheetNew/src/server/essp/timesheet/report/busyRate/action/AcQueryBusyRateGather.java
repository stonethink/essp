/*
 * Created on 2008-6-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.report.busyRate.action;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.report.busyRate.service.IBusyRateGatherService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
import c2s.essp.timesheet.report.DtoBusyRateQuery;

public class AcQueryBusyRateGather extends AbstractESSPAction {
        public void executeAct(HttpServletRequest request,
                HttpServletResponse response,
                TransactionData data) throws
            BusinessException {
            IBusyRateGatherService lg = (IBusyRateGatherService) this.getBean("busyRateGatherService");
            DtoBusyRateQuery dtoQuery = (DtoBusyRateQuery) data.getInputInfo().
                             getInputObj(DtoBusyRateQuery.DTO_UNTIL_RATE_QUERY);
            Map deptList = lg.getBusyRateInfoByYear(dtoQuery);
            data.getReturnInfo().setReturnObj(DtoBusyRateQuery.DTO_DEPT_LIST,deptList);
            }
}
