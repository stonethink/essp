package server.essp.tc.dmview.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import c2s.essp.tc.weeklyreport.DtoWeeklyReportSumByPm;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.tc.dmview.logic.LgWeeklyReportSumByDm;
import server.framework.common.BusinessException;

public class AcWeeklyReportSumGetForUser extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData transData) throws BusinessException {
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();
        Date beginPeriod = (Date) inputInfo.getInputObj(DtoTcKey.BEGIN_PERIOD);
        Date endPeriod = (Date) inputInfo.getInputObj(DtoTcKey.END_PERIOD);
        String userId = (String) inputInfo.getInputObj(DtoTcKey.USER_ID);

        if (userId == null || beginPeriod == null || endPeriod == null) {
            throw new BusinessException("E0000", "UserId/AcntRid/beginPeriod/endPeriod is null.");
        }

        LgWeeklyReportSumByDm logic = new LgWeeklyReportSumByDm();
        DtoWeeklyReportSumByPm weeklyReport = logic.listByTheUserOnWeek(userId, beginPeriod, endPeriod);

        returnInfo.setReturnObj(DtoTcKey.DTO, weeklyReport);
    }
}
