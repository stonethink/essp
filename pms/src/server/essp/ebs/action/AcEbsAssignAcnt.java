package server.essp.ebs.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import server.essp.ebs.logic.LgEbsList;
import server.framework.action.AbstractBusinessAction;
import server.framework.common.BusinessException;

public class AcEbsAssignAcnt extends AbstractBusinessAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws BusinessException {

        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        List newAccountList = (List) inputInfo.getInputObj("newAccountList");
        Long ebsRid = (Long) inputInfo.getInputObj("ebsRid");

//        Long lParentId;
//        try {
//            lParentId = new Long(ebsRid);
//        } catch (NumberFormatException ex) {
//            throw new BusinessException("E010101", "ParentId is not a number.");
//        }

        LgEbsList logic = new LgEbsList();
        logic.setDbAccessor(this.getDbAccessor());
        List accountlist = logic.addExistAcnt(newAccountList, ebsRid);

        returnInfo.setReturnObj("accountList", accountlist);
    }
}
