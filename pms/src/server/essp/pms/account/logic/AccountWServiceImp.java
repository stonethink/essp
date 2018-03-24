package server.essp.pms.account.logic;

import c2s.essp.common.account.IDtoAccount;
import itf.webservices.IAccountWService;
import c2s.essp.pms.account.DtoPmsAcnt;
import c2s.dto.DtoUtil;
import db.essp.pms.Account;
import server.framework.logic.AbstractBusinessLogic;
import java.util.Map;

public class AccountWServiceImp extends AbstractBusinessLogic implements IAccountWService {
    private LgAccountBase lg = new LgAccount();
    /**
     * addAccount
     *
     * @param account IDtoAccount
     * @todo Implement this itf.webservices.IAccountWService method
     */
    public void addAccount(Map acntAttribute) {
        log.info("add account from webservice");
        log.info("account id:"+acntAttribute.get("id"));
//        try {
//            this.getDbAccessor().followTx();
//            DtoPmsAcnt dto = new DtoPmsAcnt();
//            DtoUtil.copyMapToBean(dto,acntAttribute);
//            DtoUtil.copyBeanToBean(dto,account);
//            dto.setRid(null);
//            lg.add(dto);
//            this.getDbAccessor().commit();
//        } catch (Exception ex) {
//            try {
//                System.out.println("error while adding account from web services!");
//                ex.printStackTrace();
//                this.getDbAccessor().rollback();
//            } catch (Exception ex1) {
//                ex1.printStackTrace();
//            }
//        }
    }

    /**
     * closeAccount
     *
     * @param acntId String
     * @todo Implement this itf.webservices.IAccountWService method
     */
    public void closeAccount(String acntId) {
        log.info("close account:"+acntId+" from webservice");
        try {
            this.getDbAccessor().followTx();
            Account account = lg.load(acntId);
            account.setStatus(IDtoAccount.ACCOUNT_STATUS_CLOSED);
            log.info("set account status closed!");
            lg.getDbAccessor().update(account);
            this.getDbAccessor().commit();
        } catch (Exception ex) {
            try {
                System.out.println("error while adding account from web services!");
                ex.printStackTrace();
                this.getDbAccessor().rollback();
            } catch (Exception ex1) {
                ex1.printStackTrace();
            }
        }
    }

    /**
     * updateAccount
     *
     * @param account IDtoAccount
     * @todo Implement this itf.webservices.IAccountWService method
     */
    public void updateAccount(Map acntAttribute) {
//        try {
//            this.getDbAccessor().followTx();
//            Account account = lg.load(dto.getId());
//            DtoUtil.copyBeanToBean(account,dto,new String[]{"rid"});
//            lg.getDbAccessor().update(account);
//            this.getDbAccessor().commit();
//        } catch (Exception ex) {
//            try {
//                System.out.println("error while adding account from web services!");
//                ex.printStackTrace();
//                this.getDbAccessor().rollback();
//            } catch (Exception ex1) {
//                ex1.printStackTrace();
//            }
//        }
    }
}
