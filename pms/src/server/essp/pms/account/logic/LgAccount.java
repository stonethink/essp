package server.essp.pms.account.logic;

import db.essp.pms.Account;
import server.essp.pms.account.baseline.logic.LgAccountBaseline;
import server.framework.common.BusinessException;

public class LgAccount extends LgAccountBase {
    public LgAccount() {
    }

    public LgAccount(Account account) {
        super(account);
    }


    /**
     * ���������account��¼��rid����
     * @param accountRid Long
     */
    public LgAccount(Long accountRid) throws BusinessException {
        super(accountRid);
    }


    /**
     * �����������Ŀ����accountId����
     * @param accountId String
     */
    public LgAccount(String accountId) throws BusinessException {
        super(accountId);
    }

    private LgAccountBaseline
        lgAccountBaseline;
    public LgAccountBaseline getLgAccountBaseline() throws BusinessException {
        this.lgAccountBaseline = new LgAccountBaseline(this.account);
        return lgAccountBaseline;
    }
//
//    private LgAccountLaborRes
//        lgAccountLaborRes;
//    public LgAccountLaborRes getAccountLaborRes() throws BusinessException {
//        this.lgAccountLaborRes = new LgAccountLaborRes(this.account);
//        return lgAccountLaborRes;
//    }
//
}
