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
import server.essp.tc.common.LgOvertimeLeave;
import server.framework.common.BusinessException;


public class AcOvertimeLeave extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData transData) throws BusinessException {
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();
        Date beginPeriod = (Date) inputInfo.getInputObj(DtoTcKey.BEGIN_PERIOD);
        Date endPeriod = (Date) inputInfo.getInputObj(DtoTcKey.END_PERIOD);
        String userId = (String) inputInfo.getInputObj(DtoTcKey.USER_ID);

        LgOvertimeLeave logic = new LgOvertimeLeave();
        List overtimeLeave = logic.list(userId, beginPeriod, endPeriod);

        returnInfo.setReturnObj(DtoTcKey.OVERTIME_LEAVE_LIST, overtimeLeave);

        returnInfo.setReturnObj(DtoTcKey.LEAVE_ON_WEEK, logic.getLeaveOnWeek());
        returnInfo.setReturnObj(DtoTcKey.LEAVE_ON_WEEK_CONFIRMED, logic.getLeaveOnWeekConfirmed());
        returnInfo.setReturnObj(DtoTcKey.OVERTIME_ON_WEEK, logic.getOvertimeOnWeek());
        returnInfo.setReturnObj(DtoTcKey.OVERTIME_ON_WEEK_CONFIRMED, logic.getOvertimeOnWeekConfirmed());
    }
}
