package server.essp.timesheet.account.labor.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.rmmaint.service.IRmMaintService;
import server.framework.common.BusinessException;
import server.essp.common.ldap.LDAPUtil;
import c2s.essp.common.user.DtoUser;

public class AcSearchManager extends AbstractESSPAction {
    /**
     * executeAct
     *
     * @param httpServletRequest HttpServletRequest
     * @param httpServletResponse HttpServletResponse
     * @param transactionData TransactionData
     * @throws BusinessException
     * @todo Implement this server.framework.action.AbstractBusinessAction
     *   method
     */
    public void executeAct(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           TransactionData data) throws
            BusinessException {
        String loginId = (String) data.getInputInfo().getInputObj("LoginId");
        IRmMaintService service = (IRmMaintService) this.getBean("rmMaintService");
        String rmId = service.getRmByLoginId(loginId);
        data.getReturnInfo().setReturnObj("RMLoginId", rmId);
    }
}
