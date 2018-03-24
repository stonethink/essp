package server.essp.tc.hrmanage.action;

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
import server.essp.tc.hrmanage.logic.LgWeeklyReportByHr;
import server.essp.tc.hrmanage.logic.LgWeeklyReportSumByHr;
import server.framework.common.BusinessException;

public class AcWeeklyReportUpdate extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData transData) throws BusinessException {
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();
        List weeklyReportList = (List) inputInfo.getInputObj(DtoTcKey.WEEKLY_REPORT_LIST);
        String userId = (String) inputInfo.getInputObj(DtoTcKey.USER_ID);
        Date beginPeriod = (Date) inputInfo.getInputObj(DtoTcKey.BEGIN_PERIOD);
        Date endPeriod = (Date) inputInfo.getInputObj(DtoTcKey.END_PERIOD);

        LgWeeklyReportByHr logic = new LgWeeklyReportByHr();

        //update
        logic.update(weeklyReportList);

        //set confirm status
        LgWeeklyReportSumByHr logicSum = new LgWeeklyReportSumByHr();
        logicSum.setConfirmStatusForUser(userId, beginPeriod, endPeriod);

        //locked it
        LgWeeklyReportLock lockLogic = new LgWeeklyReportLock();
        lockLogic.setLockOn(userId, beginPeriod, endPeriod);

        returnInfo.setReturnObj(DtoTcKey.WEEKLY_REPORT_LIST, weeklyReportList);
    }
}
