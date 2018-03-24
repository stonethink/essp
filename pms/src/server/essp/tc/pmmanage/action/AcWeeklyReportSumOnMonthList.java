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
import server.essp.tc.pmmanage.logic.LgWeeklyReportSumByPm;
import server.framework.common.BusinessException;

public class AcWeeklyReportSumOnMonthList extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData transData) throws BusinessException {
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();
        Long acntRid = (Long) inputInfo.getInputObj(DtoTcKey.ACNT_RID);
        Date beginPeriod = (Date) inputInfo.getInputObj(DtoTcKey.BEGIN_PERIOD);
        Date endPeriod = (Date) inputInfo.getInputObj(DtoTcKey.END_PERIOD);

        if (acntRid == null || beginPeriod == null || endPeriod == null) {
            throw new BusinessException("E0000", "AcntRid/beginPeriod/endPeriod is null.");
        }

        LgWeeklyReportSumByPm logic = new LgWeeklyReportSumByPm();
        List weeklyReport = logic.listByAcntOnMonth(acntRid, beginPeriod, endPeriod);

        returnInfo.setReturnObj(DtoTcKey.WEEKLY_REPORT_SUM_LIST, weeklyReport);
    }
}
