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
import server.essp.tc.weeklyreport.logic.LgWeeklyReportByWorker;
import server.framework.common.BusinessException;


public class AcWeeklyReportInitFromDailyreport extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData transData) throws BusinessException {
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();
        Date beginPeriod = (Date) inputInfo.getInputObj(DtoTcKey.BEGIN_PERIOD);
        Date endPeriod = (Date) inputInfo.getInputObj(DtoTcKey.END_PERIOD);
        List weeklyReportList = (List) inputInfo.getInputObj(DtoTcKey.WEEKLY_REPORT_LIST);

        LgWeeklyReportByWorker logic = new LgWeeklyReportByWorker();
        List weeklyReport = logic.initFromDailyreport(beginPeriod, endPeriod, weeklyReportList);

        returnInfo.setReturnObj(DtoTcKey.WEEKLY_REPORT_INIT_FROM_DAILYREPORT_LIST, weeklyReport);
    }
}
