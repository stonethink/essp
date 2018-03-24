package server.essp.issue.common.action;

import server.essp.framework.action.AbstractESSPAction;
import c2s.essp.common.account.IDtoAccount;
import com.wits.util.ThreadVariant;
import server.framework.common.BusinessException;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractISSUEAction extends AbstractESSPAction {
    public AbstractISSUEAction() {
    }

    public void setAccount(IDtoAccount account) {
        if (account != null) {
            if (this.getSession() == null) {
                throw new BusinessException("E_ISSUECORE001", "session is null");
            }
            this.getSession().setAttribute(IDtoAccount.SESSION_KEY, account);
            ThreadVariant thread = ThreadVariant.getInstance();
            thread.set(IDtoAccount.SESSION_KEY, account);
        }
    }

    /**
     * ∫Û÷√¥¶¿Ì
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     */
    public void postProcess(HttpServletRequest request,
                            HttpServletResponse response,
                            TransactionData data) {
        //if (this.getSession().getAttribute(IDtoAccount.SESSION_KEY) == null) {
        ThreadVariant thread = ThreadVariant.getInstance();
        if (thread.get(IDtoAccount.SESSION_KEY) != null) {
            this.getSession().setAttribute(IDtoAccount.SESSION_KEY,
                                           thread.
                                           get(IDtoAccount.SESSION_KEY));
        }
        //}
    }

}
