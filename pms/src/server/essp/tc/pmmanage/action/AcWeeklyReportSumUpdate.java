package server.essp.tc.pmmanage.action;

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

public class AcWeeklyReportSumUpdate extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData transData) throws BusinessException {
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();
        List weeklyReportList = (List) inputInfo.getInputObj(DtoTcKey.WEEKLY_REPORT_SUM_LIST);

        LgWeeklyReportSumByPm logic = new LgWeeklyReportSumByPm();
        logic.update(weeklyReportList);

    }
}
