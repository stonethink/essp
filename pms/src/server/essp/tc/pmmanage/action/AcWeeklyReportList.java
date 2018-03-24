package server.essp.tc.pmmanage.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.tc.common.LgWeeklyReport;
import server.essp.tc.common.LgWeeklyReportLock;
import server.essp.tc.pmmanage.logic.LgWeeklyReportSumByPm;
import server.framework.common.BusinessException;

public class AcWeeklyReportList extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData transData) throws BusinessException {
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();
        String userId = (String) inputInfo.getInputObj(DtoTcKey.USER_ID);
        Long acntRid = (Long) inputInfo.getInputObj(DtoTcKey.ACNT_RID);
        Date beginPeriod = (Date) inputInfo.getInputObj(DtoTcKey.BEGIN_PERIOD);
        Date endPeriod = (Date) inputInfo.getInputObj(DtoTcKey.END_PERIOD);

        LgWeeklyReport logic = new LgWeeklyReport();
        List weeklyReport = logic.listByUserAcnt(userId, acntRid, beginPeriod, endPeriod);

        LgWeeklyReportSumByPm logicSum = new LgWeeklyReportSumByPm();
        List weeklyReportSum = logicSum.listByUserExcludeAcnt(userId, acntRid, beginPeriod, endPeriod);
        weeklyReport.addAll(weeklyReportSum);

        LgWeeklyReportLock logicLock = new LgWeeklyReportLock();
        Boolean isLocked = logicLock.getLock(userId, beginPeriod,endPeriod);

        returnInfo.setReturnObj(DtoTcKey.WEEKLY_REPORT_LIST, weeklyReport);
        returnInfo.setReturnObj(DtoTcKey.IS_LOCKED, isLocked);
    }
}
