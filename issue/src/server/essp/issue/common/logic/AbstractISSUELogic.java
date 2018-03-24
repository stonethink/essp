package server.essp.issue.common.logic;

import server.essp.framework.logic.AbstractESSPLogic;
import c2s.essp.common.account.IDtoAccount;
import com.wits.util.ThreadVariant;
import java.util.List;
import c2s.essp.common.user.DtoUser;
import server.framework.common.BusinessException;
import server.essp.pms.account.logic.LgAccountUtilImpl;

public class AbstractISSUELogic extends AbstractESSPLogic {
    public AbstractISSUELogic() {
    }

    public void setAccount(IDtoAccount account) {
        ThreadVariant thread = ThreadVariant.getInstance();
        if (account != null) {
            thread.set(IDtoAccount.SESSION_KEY, account);
        }
    }

    public IDtoAccount getAccount(String accountRid) {
        if (accountRid == null || accountRid.equals("")) {
            log.info("accountRid is empty");
            return null;
        }
        LgAccountUtilImpl accountUtil = new LgAccountUtilImpl();
        IDtoAccount account = accountUtil.getAccountByRID(new Long(accountRid));
        return account;
    }

    public List getAccounts() throws
        BusinessException {
        DtoUser user = this.getUser();
        String userType = user.getUserType();
        String userId = user.getUserLoginId();
        LgAccount accountLogic=new LgAccount();
        return accountLogic.getAccounts(userType,userId);
    }

    public List getAccountOptions() throws
        BusinessException {
        DtoUser user = this.getUser();
        String userType = user.getUserType();
        String userId = user.getUserLoginId();
        LgAccount accountLogic = new LgAccount();
        return accountLogic.getAccountOptions(userType,
                    userId);
    }
}
