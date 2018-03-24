package server.essp.tc.weeklyreport.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import c2s.essp.tc.weeklyreport.DtoWeeklyReport;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import server.essp.tc.common.LgWeeklyReport;

//no use
public class AcWeeklyReportDelete extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData transData) throws BusinessException {
//        InputInfo inputInfo = transData.getInputInfo();
//        ReturnInfo returnInfo = transData.getReturnInfo();
//        DtoWeeklyReport dto = (DtoWeeklyReport) inputInfo.getInputObj(DtoTcKey.DTO);
//
//        LgWeeklyReport logic = new LgWeeklyReport();
//        logic.delete(dto);
    }
}
