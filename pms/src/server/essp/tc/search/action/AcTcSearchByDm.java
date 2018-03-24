package server.essp.tc.search.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import c2s.essp.tc.weeklyreport.DtoTcSearchCondition;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.tc.search.logic.LgTcSearchByOrg;
import server.framework.common.BusinessException;


public class AcTcSearchByDm extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData transData) throws BusinessException {
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();
        DtoTcSearchCondition condition = (DtoTcSearchCondition) inputInfo.getInputObj(DtoTcKey.SEARCH_CONDITION);

        LgTcSearchByOrg logic = new LgTcSearchByOrg();
        List tcList = logic.search(condition);

        returnInfo.setReturnObj(DtoTcKey.SEARCH_RESULT, tcList);
    }
}
