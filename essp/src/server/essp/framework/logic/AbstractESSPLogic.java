package server.essp.framework.logic;

import server.framework.hibernate.*;
import server.framework.logic.*;
import c2s.essp.common.user.DtoUser;
import com.wits.util.ThreadVariant;
import c2s.essp.common.account.IDtoAccount;

public abstract class AbstractESSPLogic extends AbstractBusinessLogic {
    public AbstractESSPLogic() {
        super();
    }

    public AbstractESSPLogic(HBComAccess hbAccess) {
        super(hbAccess);
    }

    public DtoUser getUser() {
        ThreadVariant thread=ThreadVariant.getInstance();
        return (DtoUser)thread.get(DtoUser.SESSION_USER);
    }

    public IDtoAccount getAccount() {
        ThreadVariant thread=ThreadVariant.getInstance();
        return (IDtoAccount)thread.get(IDtoAccount.SESSION_KEY);
    }
}
