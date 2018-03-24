package server.essp.ebs.action;

import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import server.essp.ebs.logic.LgAcntList;
import server.framework.action.AbstractBusinessAction;
import server.framework.common.BusinessException;

public class AcAcntList extends AbstractBusinessAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws BusinessException {

        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        LgAcntList acntListLogic = new LgAcntList();
        acntListLogic.setDbAccessor(this.getDbAccessor());
        List accountList = acntListLogic.list();

        returnInfo.setReturnObj("accountList", new Vector(accountList));
    }
}
