package server.essp.framework.action;

import server.framework.action.*;
import server.framework.common.*;
import c2s.essp.common.user.DtoUser;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import com.wits.util.ThreadVariant;
import c2s.essp.common.account.IDtoAccount;
import server.essp.framework.authorize.ESSPAuthorizeImp;

public abstract class AbstractESSPAction extends AbstractBusinessAction {
    private DtoUser user;
    private IDtoAccount account;

    /**
     * Action之前置处理
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     */
    public void preProcess(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData data) {
        ThreadVariant thread = ThreadVariant.getInstance();

        if (this.getSession() == null) {
            throw new BusinessException("E_ESSPCORE001", "session is null");
        }
        user = (DtoUser)this.getSession().getAttribute(DtoUser.SESSION_USER);
        thread.set(DtoUser.SESSION_USER, user);

        //Added by stone 20060801
        DtoUser loginUser = (DtoUser)this.getSession().getAttribute(DtoUser.
                SESSION_LOGIN_USER);
         if (loginUser != null) {
            thread.set("LOGIN_ID", loginUser.getUserLoginId());
            thread.set(DtoUser.SESSION_LOGIN_USER, loginUser);
        }

        account = (IDtoAccount)this.getSession().getAttribute(IDtoAccount.
                SESSION_KEY);
        thread.set(IDtoAccount.SESSION_KEY, account);
    }

    protected DtoUser getUser() {
//        log.info("befor getUser():" + user.getUseLoginName());
        return user;
    }

    protected IDtoAccount getAccount() {
        return account;
    }

    protected Class getAuthorizeClass() {
        return ESSPAuthorizeImp.class;
    }
}
