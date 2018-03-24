package server.essp.tc.dmview.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import server.essp.tc.dmview.logic.LgWeeklyReportSumByDmAcnt;

public class AcWeeklyReportSumByAcntList extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData transData) throws BusinessException {
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();
        String orgId = (String) inputInfo.getInputObj(DtoTcKey.ORG);
        Date beginPeriod = (Date) inputInfo.getInputObj(DtoTcKey.BEGIN_PERIOD);
        Date endPeriod = (Date) inputInfo.getInputObj(DtoTcKey.END_PERIOD);

        if (orgId == null || beginPeriod == null || endPeriod == null) {
            throw new BusinessException("E0000", "OrgId/beginPeriod/endPeriod is null.");
        }

        LgWeeklyReportSumByDmAcnt logic = new LgWeeklyReportSumByDmAcnt();
        List weeklyReport = logic.listByAcntInOrg(orgId, beginPeriod, endPeriod);

        returnInfo.setReturnObj(DtoTcKey.WEEKLY_REPORT_SUM_LIST, weeklyReport);
    }
}
