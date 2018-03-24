package server.essp.pms.pbs.pbsAndFiles.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import server.essp.pms.pbs.pbsAndFiles.logic.LgPbsList;
import server.framework.action.AbstractBusinessAction;
import server.framework.common.BusinessException;

public class AcPbsList extends AbstractBusinessAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws BusinessException {

        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        Long joinType = (Long)inputInfo.getInputObj("joinType");
        Long joinRid = (Long)inputInfo.getInputObj("joinRid");
        Long acntRid = (Long)inputInfo.getInputObj("acntRid");

        LgPbsList lgPbsList = new LgPbsList();
        lgPbsList.setDbAccessor(this.getDbAccessor());
        List pbsList = lgPbsList.list(acntRid, joinType, joinRid);

        returnInfo.setReturnObj("pbsList", pbsList);
    }
}
