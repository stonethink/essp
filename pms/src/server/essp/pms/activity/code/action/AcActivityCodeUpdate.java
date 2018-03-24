package server.essp.pms.activity.code.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.common.code.DtoKey;
import server.framework.action.AbstractBusinessAction;
import server.framework.common.BusinessException;
import server.essp.pms.activity.code.logic.LgActivityCode;

public class AcActivityCodeUpdate extends AbstractBusinessAction{

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws BusinessException {

        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        Long acntRid = (Long)inputInfo.getInputObj(DtoKey.ACNT_RID);
        Long activityRid = (Long)inputInfo.getInputObj(DtoKey.ACTIVITY_RID);
        List codeList = (List)inputInfo.getInputObj(DtoKey.DTO_LIST);

        LgActivityCode logic = new LgActivityCode(acntRid,activityRid);
        logic.update(codeList);

        returnInfo.setReturnObj(DtoKey.DTO_LIST, codeList);
    }
}
