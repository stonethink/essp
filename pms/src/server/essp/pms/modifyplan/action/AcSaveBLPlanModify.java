package server.essp.pms.modifyplan.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.dto.ITreeNode;
import server.essp.pms.modifyplan.logic.LgBLPlanModify;

public class AcSaveBLPlanModify extends AbstractESSPAction {
    public AcSaveBLPlanModify() {
    }

    /**
     * executeAct
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     * @todo Implement this server.framework.action.AbstractBusinessAction
     *   method
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
        BusinessException {
        ITreeNode root=(ITreeNode)data.getInputInfo().getInputObj("RootNode");
        LgBLPlanModify logic=new LgBLPlanModify();
        logic.saveBLPlan(root);
        data.getReturnInfo().setReturnObj("RootNode",root);
    }
}
