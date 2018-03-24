package server.essp.tc.dmview.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import server.essp.tc.dmview.logic.LgTcInitObsList;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;

public class AcTcInitObsList extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData transData) throws BusinessException {
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        LgTcInitObsList logic = new LgTcInitObsList();
        List orgIdList = logic.listOrgId();
        List orgNameList = logic.listOrgName();
        String orgId = null;
        if( orgIdList.size() > 0 ){
            orgId = (String)orgIdList.get(0);
        }

        returnInfo.setReturnObj(DtoTcKey.ORG, orgId);
        returnInfo.setReturnObj(DtoTcKey.ORG_ID_LIST, orgIdList);
        returnInfo.setReturnObj(DtoTcKey.ORG_NAME_LIST, orgNameList);
    }
}
