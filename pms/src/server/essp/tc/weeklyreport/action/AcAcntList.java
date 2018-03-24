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
import server.framework.common.BusinessException;
import server.essp.tc.weeklyreport.logic.LgWeeklyReportSumByWorker;
import c2s.essp.common.user.DtoUser;

public class AcAcntList extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData transData) throws BusinessException {
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();
        Date beginPeriod = (Date) inputInfo.getInputObj(DtoTcKey.BEGIN_PERIOD);
        Date endPeriod = (Date) inputInfo.getInputObj(DtoTcKey.END_PERIOD);
        String userId = (String) inputInfo.getInputObj(DtoTcKey.USER_ID);

        //若客户端没有传过来UserId,从Session中获得
        //modify by xr 2006/03/27
        if(userId == null){
            DtoUser user = getUser();
            if(user == null)
                throw new BusinessException("E0000","Please login first.");
            userId = user.getUserLoginId();
        }

        LgWeeklyReportSumByWorker logic = new LgWeeklyReportSumByWorker();
        List tcList = logic.listByLoginIdOnWeek(userId,beginPeriod, endPeriod);

        returnInfo.setReturnObj(DtoTcKey.WEEKLY_REPORT_LIST, tcList);
    }
}
