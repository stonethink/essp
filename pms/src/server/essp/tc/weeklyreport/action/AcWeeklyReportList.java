package server.essp.tc.weeklyreport.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.tc.common.LgWeeklyReportLock;
import server.essp.tc.weeklyreport.logic.LgWeeklyReportByWorker;
import server.framework.common.BusinessException;
import c2s.essp.common.calendar.WorkCalendar;

public class AcWeeklyReportList extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData transData) throws BusinessException {
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();
        Date beginPeriod = (Date) inputInfo.getInputObj(DtoTcKey.BEGIN_PERIOD);
        Date endPeriod = (Date) inputInfo.getInputObj(DtoTcKey.END_PERIOD);
        String userId = (String) inputInfo.getInputObj(DtoTcKey.USER_ID);

        LgWeeklyReportByWorker logic = new LgWeeklyReportByWorker();
        List weeklyReport = logic.listByUserId(userId, beginPeriod, endPeriod);
        boolean workDayBitmap[] = logic.getWorkDayBitmap(beginPeriod, endPeriod);

        LgWeeklyReportLock logicLock = new LgWeeklyReportLock();
        Boolean isLocked = logicLock.getLock(userId, beginPeriod,endPeriod);

        returnInfo.setReturnObj(DtoTcKey.WEEKLY_REPORT_LIST, weeklyReport);
        returnInfo.setReturnObj(DtoTcKey.IS_LOCKED, isLocked);
        returnInfo.setReturnObj(DtoTcKey.WORK_DAY_BITMAP, workDayBitmap);
    }
}
