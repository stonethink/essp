package server.essp.ebs.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import server.essp.ebs.logic.LgEbs9Acnt;
import server.framework.action.AbstractBusinessAction;
import server.framework.common.BusinessException;

public class AcEbs9AcntUpdate extends AbstractBusinessAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws BusinessException {

        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        Long acntRid = (Long) inputInfo.getInputObj("acntRid");
        Long oldEbsRid = (Long) inputInfo.getInputObj("oldEbsRid");
        Long newEbsRid = (Long) inputInfo.getInputObj("newEbsRid");

        LgEbs9Acnt logic = new LgEbs9Acnt();
        logic.setDbAccessor(this.getDbAccessor());
        logic.update(acntRid, newEbsRid, oldEbsRid);
    }
}
