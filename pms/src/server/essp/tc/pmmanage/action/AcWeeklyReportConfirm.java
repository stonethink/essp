package server.essp.tc.pmmanage.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import c2s.essp.tc.weeklyreport.DtoWeeklyReportSumByPm;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.tc.pmmanage.logic.LgWeeklyReportSumByPm;
import server.framework.common.BusinessException;

public class AcWeeklyReportConfirm extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData transData) throws BusinessException {
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();
        DtoWeeklyReportSumByPm dto = (DtoWeeklyReportSumByPm) inputInfo.getInputObj(DtoTcKey.DTO);

        LgWeeklyReportSumByPm logicSum = new LgWeeklyReportSumByPm();
        String msg = logicSum.confirm(dto);

        returnInfo.setReturnObj(DtoTcKey.MESSAGE, msg);
    }
}
