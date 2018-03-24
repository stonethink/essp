package server.essp.tc.pmmanage.action;

import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import javax.servlet.http.HttpServletResponse;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import c2s.essp.tc.weeklyreport.DtoWeeklyReportSumByPm;
import c2s.essp.common.user.DtoUser;
import server.essp.tc.common.LgWeeklyReport;
import server.essp.tc.pmmanage.logic.LgWeeklyReportSumByPm;
import server.essp.framework.action.AbstractESSPAction;

public class AcWeeklyReportSend extends AbstractESSPAction{
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData transData) throws BusinessException {
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();
        DtoWeeklyReportSumByPm dto = (DtoWeeklyReportSumByPm)inputInfo.getInputObj(DtoTcKey.DTO);
        DtoUser dtouser = (DtoUser)request.getSession().getAttribute(DtoUser.SESSION_USER);
        LgWeeklyReportSumByPm logic = new LgWeeklyReportSumByPm();
        logic.send(dto,dtouser.getUserName());
    }
}
