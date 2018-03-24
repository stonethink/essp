package server.essp.tc.pmmanage.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.tc.pmmanage.logic.LgWeeklyReportSumByPm;
import server.framework.common.BusinessException;

public class AcWeeklyReportConfirmAll extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData transData) throws BusinessException {
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();
        Date beginPeriod = (Date) inputInfo.getInputObj(DtoTcKey.BEGIN_PERIOD);
        Date endPeriod = (Date) inputInfo.getInputObj(DtoTcKey.END_PERIOD);
        Long acntRid = (Long) inputInfo.getInputObj(DtoTcKey.ACNT_RID);
        String status = (String) inputInfo.getInputObj(DtoTcKey.CONFIRM_ALL_STATUS);

        LgWeeklyReportSumByPm logicSum = new LgWeeklyReportSumByPm();
        String msg = logicSum.confirmAllByPm(acntRid, beginPeriod, endPeriod, status);

        returnInfo.setReturnObj(DtoTcKey.MESSAGE, msg);
    }
}
