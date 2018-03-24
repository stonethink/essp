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
import server.framework.common.BusinessException;
import server.essp.tc.common.LgWeeklyReport;
import server.essp.tc.common.LgWeeklyReportLock;
import server.essp.tc.weeklyreport.logic.LgWeeklyReportSumByWorker;

public class AcWeeklyReportLock extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData transData) throws BusinessException {
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();
        List weeklyReportList = (List) inputInfo.getInputObj(DtoTcKey.WEEKLY_REPORT_LIST);
        Date beginPeriod = (Date) inputInfo.getInputObj(DtoTcKey.BEGIN_PERIOD);
        Date endPeriod = (Date) inputInfo.getInputObj(DtoTcKey.END_PERIOD);
        String userId = (String) inputInfo.getInputObj(DtoTcKey.USER_ID);

        LgWeeklyReport logic = new LgWeeklyReport();
        if( logic.checkConfirmStatus(weeklyReportList) ){
            logic.update(weeklyReportList);
        }

        LgWeeklyReportSumByWorker logicSum = new LgWeeklyReportSumByWorker();
        logicSum.updateConfirmStatusByWorker(weeklyReportList, userId, beginPeriod, endPeriod);

        LgWeeklyReportLock logicLock = new LgWeeklyReportLock();
        logicLock.setLockOn(userId, beginPeriod, endPeriod);

        returnInfo.setReturnObj(DtoTcKey.WEEKLY_REPORT_LIST, weeklyReportList);
        returnInfo.setReturnObj(DtoTcKey.IS_LOCKED, Boolean.TRUE);
        returnInfo.setReturnObj(DtoTcKey.MESSAGE, logic.getMsg());
    }
}
