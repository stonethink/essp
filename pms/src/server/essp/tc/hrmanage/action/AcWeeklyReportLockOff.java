package server.essp.tc.hrmanage.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.tc.common.LgWeeklyReportLock;
import server.essp.tc.hrmanage.logic.LgWeeklyReportSumByHr;
import server.framework.common.BusinessException;
import c2s.essp.common.user.DtoUser;


public class AcWeeklyReportLockOff extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData transData) throws BusinessException {
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();
        String userId = (String) inputInfo.getInputObj(DtoTcKey.USER_ID);
        Date beginPeriod = (Date) inputInfo.getInputObj(DtoTcKey.BEGIN_PERIOD);
        Date endPeriod = (Date) inputInfo.getInputObj(DtoTcKey.END_PERIOD);

        //设置lock标志
        LgWeeklyReportLock logicLock = new LgWeeklyReportLock();
        logicLock.setLockOff(userId, beginPeriod, endPeriod);

        //设置所有周报为“unLocked”状态
        LgWeeklyReportSumByHr logicSum = new LgWeeklyReportSumByHr();
        logicSum.updateConfirmStatusByHr(userId, beginPeriod, endPeriod);

        DtoUser dto =(DtoUser)request.getSession().getAttribute(DtoUser.SESSION_USER);
        logicSum.send(userId, beginPeriod, endPeriod,dto.getUserName());
    }
}
