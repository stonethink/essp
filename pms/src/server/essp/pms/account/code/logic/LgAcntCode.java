package server.essp.pms.account.code.logic;

import java.util.Set;

import db.essp.pms.Account;
import server.essp.common.code.choose.logic.LgCodeChoose;
import server.framework.common.BusinessException;

public class LgAcntCode extends LgCodeChoose {
    Account account = null;

    public LgAcntCode(Long acntRid) {

        try {
            account = (Account) getDbAccessor().load(Account.class, acntRid);
        } catch (Exception ex) {
            throw new BusinessException("E0000",
                                        "Error when select account.",
                                        ex);
        }
    }

    public Object getCodeOwner() {
        return account;
    }

    public Set getCodeSet() {
        return account.getAcntCodes();
    }


}
