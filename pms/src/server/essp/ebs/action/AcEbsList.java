package server.essp.ebs.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.ITreeNode;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import server.essp.ebs.logic.LgEbsList;
import server.framework.action.AbstractBusinessAction;
import server.framework.common.BusinessException;

public class AcEbsList extends AbstractBusinessAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws BusinessException {

        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        LgEbsList ebsListLogic = new LgEbsList();
        ebsListLogic.setDbAccessor(this.getDbAccessor());
        ITreeNode root = ebsListLogic.list();
        Map acntMap = ebsListLogic.getAcntMap();

        returnInfo.setReturnObj("root", root);
        returnInfo.setReturnObj("acntMap", acntMap);
    }
}
