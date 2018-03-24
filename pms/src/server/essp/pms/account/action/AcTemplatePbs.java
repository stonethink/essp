package server.essp.pms.account.action;

import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.ITreeNode;
import c2s.essp.common.account.IDtoAccount;
import server.essp.pms.pbs.logic.LgPmsPbsList;

public class AcTemplatePbs extends AbstractESSPAction{
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData transData) throws BusinessException{
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();
        Long acntrid = (Long)inputInfo.getInputObj("acntrid");
        LgPmsPbsList lgPmsPbsList = new LgPmsPbsList();
        ITreeNode root= lgPmsPbsList.listPBSByAcntRid(acntrid);
        returnInfo.setReturnObj("root", root);
    }
}
