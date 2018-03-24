/*
 * Created on 2007-11-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.report.timesheetStatus.action;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.report.timesheetStatus.service.ITimesheetStausService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
import c2s.essp.timesheet.report.DtoTsStatusQuery;
/**
 * <p>Title: AcQueryByPeriod</p>
 *
 * <p>Description: ≤È‘É</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author tbh
 * @version 1.0
 */
public class AcQueryByPeriod  extends AbstractESSPAction {
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response,
            TransactionData data) throws
    BusinessException {
    ITimesheetStausService lg = (ITimesheetStausService)this.
                                 getBean("tsStatusService");
    DtoTsStatusQuery dtoQuery = (DtoTsStatusQuery) data.getInputInfo().
                     getInputObj(DtoTsStatusQuery.DTO_TS_QUERY);
    String loginId = this.getUser().getUserLoginId();
    lg.setExcelDto(false);
    Map resultMap;
    try {
        resultMap = lg.queryByPeriod(dtoQuery, loginId,false);
        data.getReturnInfo().setReturnObj(DtoTsStatusQuery.DTO_RESULT_LIST,resultMap);
    } catch (Exception e) {
        e.printStackTrace();
    }
    }
  }
