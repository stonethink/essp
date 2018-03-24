package server.essp.issue.common.logic;

import server.framework.logic.AbstractBusinessLogic;
import server.framework.common.BusinessException;
import java.util.List;
import server.framework.taglib.util.SelectOptionImpl;
import java.util.ArrayList;
import server.essp.pms.account.logic.LgAccountList;
import c2s.essp.pms.account.DtoPmsAcnt;
import c2s.essp.common.user.DtoUser;
import itf.account.AccountFactory;
import itf.account.IAccountUtil;
import c2s.essp.common.account.IDtoAccount;
import net.sf.hibernate.Session;
import net.sf.hibernate.Query;
import db.essp.issue.Issue;
import c2s.essp.pms.account.DtoAcntKeyPersonnel;
import java.util.Iterator;
import db.essp.pms.Account;
import db.essp.pms.Keypesonal;
import c2s.dto.DtoUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
import java.util.Collection;

public class LgAccount extends AbstractBusinessLogic {
    private IAccountUtil accountUtil = AccountFactory.create();
    //将上次查询的Account暂时缓存起来,Key ---- Rid;Value ----- IDtoAccount
    private Map accountCache = Collections.synchronizedMap(new HashMap());
    /**
     * 根据Rid查找Account
     * @param accountRid String
     * @return IDtoAccount
     * @throws BusinessException
     */
    public IDtoAccount getAccount(String accountRid) throws BusinessException {
        return getAccountByRid(accountRid);
    }
    public IDtoAccount getAccountByRid(String accountRid) {
        if (accountRid == null || accountRid.trim().equals("")) {
            return null;
        }
        Long rid = new Long(accountRid);
        IDtoAccount account = (IDtoAccount) accountCache.get(rid);
        if(account != null)
            return account;
        try {
            account = accountUtil.getAccountByRID(rid);
            accountCache.put(account.getRid(),account);
            return account;
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }
    /**
     * 根据Code查找Account
     * @param accountCode String
     * @return IDtoAccount
     * @throws BusinessException
     */
    public IDtoAccount getAccountByCode(String accountCode) throws
        BusinessException {
        IDtoAccount account = null;
        Collection values = accountCache.values();
        Iterator it = values.iterator();
        while(it.hasNext()){
            account = (IDtoAccount) it.next();
            if(account != null && account.getId().equals(accountCode))
                return account;
        }
        try {
            account = accountUtil.getAccountByCode(accountCode);
            accountCache.put(account.getRid(),account);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return account;

    }

    public List getAccountOptions(String userType, String userId) throws
        BusinessException {
        List projectList = new ArrayList();

        List dtoList = getAccounts(userType, userId);
        for (int i = 0; i < dtoList.size(); i++) {
            IDtoAccount account = (IDtoAccount) dtoList.get(i);
            SelectOptionImpl option = new SelectOptionImpl(account.getId() +
                "---" +
                account.getName(),
                account.getRid().toString(),account.getId() +
                "---" +
                account.getName());
            projectList.add(option);
        }

        if (projectList.size() == 0) {
            SelectOptionImpl option = new SelectOptionImpl(
                "  ----  Please Select  ----  ", "");
            projectList.add(option);

        }
        return projectList;
    }

    public List getAccounts(String userType, String userId) throws
        BusinessException {
        List projectList = new ArrayList();

        if (DtoUser.USER_TYPE_CUST.equals(userType)) {
            try {
                Session s = this.getDbAccessor().getSession();
                Query q = s.createQuery("from Keypesonal kp " +
                                        "where kp.loginId=:loginId " +
                                        "and kp.typeName=:cusType " +
                                        "and kp.enable=:enable");
                q.setParameter("loginId", userId);
                q.setParameter("cusType",
                               DtoAcntKeyPersonnel.KEY_PERSON_TYPES[0]);
                q.setParameter("enable", DtoAcntKeyPersonnel.ENABLE);
                List l = q.list();
                Iterator i = l.iterator();
                while (i.hasNext()) {
                    Keypesonal kp = (Keypesonal) i.next();
                    Account acnt = kp.getAccount();
                    DtoPmsAcnt acc = new DtoPmsAcnt();
                    DtoUtil.copyProperties(acc, acnt);
                    projectList.add(acc);
                }
            } catch (Exception ex) {
                log.error(ex);
                throw new BusinessException("ACNT_KP_011",
                                            "error while listing customer available accounts!",
                                            ex);
            }
        }
        if (DtoUser.USER_TYPE_EMPLOYEE.equals(userType)) {
            if (userId != null && !userId.equals("")) {
                LgAccountList accountListLogic = new LgAccountList();
                List accountList = accountListLogic.listAccountsByLoginID(
                    userId);
                if (accountList != null) {
                    for (int i = 0; i < accountList.size(); i++) {
                        DtoPmsAcnt acc = (DtoPmsAcnt) accountList.get(i);
                        projectList.add(acc);
                    }
                }
            }
        }
        return projectList;
    }

    public String getAccountName(String accountRid) {
        return getAccountByRid(accountRid).getName();
    }

    public String getAccountId(String accountRid) {
        return getAccountByRid(accountRid).getId();
    }

    public String getAccountManager(String accountRid) {
        return getAccountByRid(accountRid).getManager();
    }



    public IDtoAccount getAccountByIssueRid(String issueRid) {
        if (issueRid == null || issueRid.trim().equals("")) {
            return null;
        }
        try {
            Session session = this.getDbAccessor().getSession();
            Query q = session.createQuery(
                "from Issue s where s.rid=" + issueRid);
            List issues = q.list();
            if (issues != null) {
                Issue issue = (Issue) issues.get(0);
                Long accountRid = issue.getAccountId();
                if (accountRid != null) {
                    return getAccountByRid(accountRid.toString());
                }
            }
        } catch (Exception e) {
            throw new BusinessException(e);
        }
        return null;
    }

//    public List getKeypersons(String accountRid) {
//        List customers=new ArrayList();
//        List results = null;
//        LgAccountKeyPersonnel logic = new LgAccountKeyPersonnel();
//        results = logic.listKeyPersonnelDto(Long.valueOf(accountRid));
//        if(results!=null && results.size()>0) {
//            for(int i=0;i<results.size();i++) {
//                DtoAcntKeyPersonnel dto=(DtoAcntKeyPersonnel)results.get(i);
//                customers.add(dto);
//            }
//        }
//        return customers;
//    }
//
//    public List getKeypersonOptions(String accountRid) {
//        List results = new ArrayList();
//        SelectOptionImpl option = new SelectOptionImpl(
//            "  ----  Please Select  ----  ", "");
//        results.add(option);
//
//        List dtoList = getKeypersons(accountRid);
//        if (dtoList != null && dtoList.size() > 0) {
//            for (int i = 0; i < dtoList.size(); i++) {
//                DtoAcntKeyPersonnel dto = (DtoAcntKeyPersonnel) dtoList.get(i);
//                option = new SelectOptionImpl(dto.getLoginId(), dto.getLoginId());
//                results.add(option);
//            }
//        }
//        return results;
//    }

    public static void main(String[] args) throws Exception {
//        List allKeyPerson=null;
//        LgAccount logicAccount=new LgAccount();
//        allKeyPerson=logicAccount.getKeypersons("4");
//        if(allKeyPerson!=null) {
//            for (int i = 0; i < allKeyPerson.size(); i++) {
//                System.out.println(allKeyPerson.get(i));
//            }
//        }
    }
}
