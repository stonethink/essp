package server.essp.tc.hrmanage.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.tc.hrmanage.logic.LgStandardTimecard;
import server.framework.common.BusinessException;


public class AcStandardTimecardUpdate extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData transData) throws BusinessException {
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();
        List tcList = (List) inputInfo.getInputObj(DtoTcKey.WEEKLY_REPORT_LIST);

        LgStandardTimecard logic = new LgStandardTimecard();
        logic.update(tcList);

        returnInfo.setReturnObj(DtoTcKey.WEEKLY_REPORT_LIST, tcList);
    }
}
