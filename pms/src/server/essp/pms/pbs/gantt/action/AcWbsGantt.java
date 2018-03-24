package server.essp.pms.pbs.gantt.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.ITreeNode;
import c2s.dto.TransactionData;
import c2s.essp.common.account.IDtoAccount;
import c2s.essp.pms.wbs.DtoKEY;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.pms.pbs.gantt.logic.LgWbsGantt;
import server.essp.pms.wbs.logic.LgWbs;
import server.framework.common.BusinessException;

public class AcWbsGantt extends AbstractESSPAction {
    public AcWbsGantt() {
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
        LgWbs wbsLogic = new LgWbsGantt();
        Long acntRid;
        String accountId = (String) data.getInputInfo().getInputObj(DtoKEY.ACCOUNT_ID); //null;
        if( accountId != null ){
            try {
                acntRid = new Long(accountId);
            } catch (NumberFormatException ex) {
                throw new BusinessException("E000", "The account id[" + accountId + "] is error.", ex );
            }
        }else{

            IDtoAccount accountDto = (IDtoAccount)this.getSession().getAttribute(IDtoAccount.SESSION_KEY);
            if (accountDto == null) {
                data.getReturnInfo().setError(true);
                data.getReturnInfo().setReturnMessage("Please select a account at first!!!");
                return;
            }
            if (accountDto.getRid() == null) {
                data.getReturnInfo().setError(true);
                data.getReturnInfo().setReturnMessage("Account'Rid is null!!!");
                return;
            }

            acntRid = accountDto.getRid();
        }

        ITreeNode treeNode = wbsLogic.getWbsTree(acntRid);
        data.getReturnInfo().setReturnObj(DtoKEY.WBSTREE, treeNode);
    }

    public static void main(String[] args) {
        AcWbsGantt acactivitylist = new AcWbsGantt();
    }
}
