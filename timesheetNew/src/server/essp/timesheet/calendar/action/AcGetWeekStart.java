package server.essp.timesheet.calendar.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import server.essp.timesheet.calendar.service.ICalendarService;
import c2s.dto.ReturnInfo;
/**
 *
 * <p>Title: </p>
 *
 * <p>Description: 用于获取开始星期数的Action</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author wenhaizheng
 * @version 1.0
 */
public class AcGetWeekStart extends AbstractESSPAction {
    /**
     * 用于获取开始星期数
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData data) throws
            BusinessException {
        ICalendarService cdService = (ICalendarService)this.getBean("calendarService");
        int weekStart = cdService.getWeekStrat();
        data.getReturnInfo().setReturnObj("weekStart", Integer.valueOf(weekStart));
    }
}
