package server.essp.pms.wbs.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import server.essp.pms.wbs.logic.LgWbs;
import c2s.essp.pms.wbs.DtoKEY;
import java.util.List;
import com.wits.util.ThreadVariant;
import c2s.essp.common.user.DtoUser;
import c2s.essp.common.account.IDtoAccount;

public class AcWbsGetManagers extends AbstractESSPAction {
    public AcWbsGetManagers() {
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
        //UserTest.test();
//        IDtoAccount accountDto = (IDtoAccount)this.getSession().getAttribute(
//            IDtoAccount.SESSION_KEY);
//        if (accountDto == null) {
//            data.getReturnInfo().setError(true);
//            data.getReturnInfo().setReturnMessage(
//                "Please select a account at first!!!");
//            return;
//        }
//        if (accountDto.getRid() == null) {
//            data.getReturnInfo().setError(true);
//            data.getReturnInfo().setReturnMessage("Account'Rid is null!!!");
//            return;
//        }

        LgWbs logic = new LgWbs();
        Long accountId = (Long) data.getInputInfo().getInputObj(DtoKEY.MANAGERS);
        Object managers = logic.getManagers(accountId);
        data.getReturnInfo().setReturnObj(DtoKEY.MANAGERS, managers);
    }
}
