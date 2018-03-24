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
import server.essp.tc.hrmanage.logic.LgWeeklyReportSumByHr;
import server.framework.common.BusinessException;
import server.essp.tc.hrmanage.logic.LgTimeCardFreeze;


public class AcWeeklyReportSumOnWeekList extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData transData) throws BusinessException {
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();
        Long acntRid = (Long) inputInfo.getInputObj(DtoTcKey.ACNT_RID);
        Date beginPeriod = (Date) inputInfo.getInputObj(DtoTcKey.BEGIN_PERIOD);
        Date endPeriod = (Date) inputInfo.getInputObj(DtoTcKey.END_PERIOD);

        LgWeeklyReportSumByHr logic = new LgWeeklyReportSumByHr();
        List tcList = logic.listByUserInHrOnWeek(acntRid, beginPeriod, endPeriod);

        LgTimeCardFreeze lgFreeze = new LgTimeCardFreeze();
        boolean isFrozen = lgFreeze.isFrozen(acntRid,beginPeriod,endPeriod);

        returnInfo.setReturnObj(DtoTcKey.WEEKLY_REPORT_LIST, tcList);
        returnInfo.setReturnObj(DtoTcKey.FROZEN, new Boolean(isFrozen));
    }

}
