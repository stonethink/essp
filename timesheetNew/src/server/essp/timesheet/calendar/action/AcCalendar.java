package server.essp.timesheet.calendar.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import server.essp.timesheet.calendar.service.ICalendarService;
import com.primavera.integration.client.bo.object.Calendar;
/**
 *
 * <p>Title: AcCalendar</p>
 *
 * <p>Description: 用于获取某年工作日和非工作日设置的Action</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author wenhaizheng
 * @version 1.0
 */
public class AcCalendar extends AbstractESSPAction {
    /**
     * 用于获取某年工作日和非工作日设置
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData data) throws
            BusinessException {
        Integer tmpYear = (Integer) data.getInputInfo().getInputObj("WorkYear");
        ICalendarService cdService = (ICalendarService)this.getBean("calendarService");
        Calendar ca = cdService.getCalendar();
        String workDayString = cdService.getWorkDayString(tmpYear, ca);
        data.getReturnInfo().setReturnObj("WorkDayString", workDayString);
    }
}
