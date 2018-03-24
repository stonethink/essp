package server.essp.tc.hrmanage.action;

import server.essp.framework.action.AbstractESSPAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import java.util.Date;
import c2s.dto.ReturnInfo;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import c2s.dto.InputInfo;
import server.essp.tc.hrmanage.logic.LgTimeCardFreeze;

public class AcFreeze extends AbstractESSPAction {
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws
        BusinessException {
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();
        Long acntRid = (Long) inputInfo.getInputObj(DtoTcKey.ACNT_RID);
        Date beginPeriod = (Date) inputInfo.getInputObj(DtoTcKey.BEGIN_PERIOD);
        Date endPeriod = (Date) inputInfo.getInputObj(DtoTcKey.END_PERIOD);
        Boolean frozen = (Boolean) inputInfo.getInputObj(DtoTcKey.FROZEN);

        LgTimeCardFreeze lgFreeze = new LgTimeCardFreeze();
        //若已冻结,则解冻,否则冻结
        if(frozen != null && frozen.booleanValue()){
            lgFreeze.unFreeze(acntRid,beginPeriod,endPeriod);
            frozen = Boolean.FALSE;
        }//
        else{
            lgFreeze.freeze(acntRid,beginPeriod,endPeriod);
            frozen = Boolean.TRUE;
        }

        returnInfo.setReturnObj(DtoTcKey.FROZEN, frozen);
    }
}
