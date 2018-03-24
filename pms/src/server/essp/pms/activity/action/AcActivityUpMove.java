package server.essp.pms.activity.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.essp.pms.wbs.DtoWbsActivity;
import c2s.dto.ITreeNode;
import c2s.essp.pms.wbs.DtoKEY;
import c2s.essp.common.account.IDtoAccount;
import server.essp.pms.activity.logic.LgActivityMove;

public class AcActivityUpMove extends AbstractESSPAction {
    public AcActivityUpMove() {
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
        IDtoAccount accountDto = (IDtoAccount)this.getSession().getAttribute(
            IDtoAccount.SESSION_KEY);
        if (accountDto == null) {
            data.getReturnInfo().setError(true);
            data.getReturnInfo().setReturnMessage(
                "Please select a account at first!!!");
            return;
        }
        if (accountDto.getRid() == null) {
            data.getReturnInfo().setError(true);
            data.getReturnInfo().setReturnMessage("Account'Rid is null!!!");
            return;
        }

        ITreeNode node = (ITreeNode) data.getInputInfo().getInputObj(DtoKEY.DTO);
        DtoWbsActivity dataBean = (DtoWbsActivity) node.getDataBean();
        dataBean.setAcntRid(accountDto.getRid());
        LgActivityMove logic=new LgActivityMove();
        logic.upMove(node);
    }

    public static void main(String[] args) {
        AcActivityUpMove acactivityupmove = new AcActivityUpMove();
    }
}
