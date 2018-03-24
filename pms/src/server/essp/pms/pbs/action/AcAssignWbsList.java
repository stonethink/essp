package server.essp.pms.pbs.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.ITreeNode;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.pms.pbs.logic.LgAssignWbsList;
import server.framework.common.BusinessException;

public class AcAssignWbsList extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws BusinessException {

        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        String inAcntRid = (String)inputInfo.getInputObj("inAcntRid");
        Long lAcntRid;
        try {
            lAcntRid = new Long(inAcntRid);
        } catch (NumberFormatException ex) {
            throw new BusinessException("E000101","The account rid -- " + inAcntRid + " is invalid number.");
        }

        LgAssignWbsList lgAssignWbsList = new LgAssignWbsList();
        lgAssignWbsList.setDbAccessor(this.getDbAccessor());
        ITreeNode root = lgAssignWbsList.list(lAcntRid);

        returnInfo.setReturnObj("root", root);
    }
}
