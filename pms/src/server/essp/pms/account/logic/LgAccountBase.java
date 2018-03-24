package server.essp.pms.account.logic;

import java.util.Iterator;
import java.util.Set;

import c2s.essp.pms.account.DtoPmsAcnt;
import db.essp.pms.Account;
import net.sf.hibernate.Session;
import server.essp.framework.logic.AbstractESSPLogic;
import server.framework.common.BusinessException;
import javax.sql.RowSet;
import server.framework.common.Constant;
import c2s.essp.common.account.IDtoAccount;


/**
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: WITS-WH</p>
 * @version 1.0
 * @author stone*/
public class LgAccountBase extends AbstractESSPLogic {
    /**
     * default constructor*/
    public LgAccountBase() {
    }

    /**
     *
     * @param account Account*/
    public LgAccountBase(Account account) {
        this.account = account;
    }


    /**
     * 依据输入的account记录的rid构造
     * @param accountRid Long*/
    public LgAccountBase(Long accountRid) throws BusinessException {
        this.account = load(accountRid);
    }


    /**
     * 依据输入的项目代号accountId构造
     * @param accountId String*/
    public LgAccountBase(String accountId) throws BusinessException {
        this.account = load(accountId);
    }

    /**
     *
     * @param rid Long
     * @return Account*/
    public Account load(Long rid) throws BusinessException {
        try {
            Session s = this.getDbAccessor().getSession();
            return (Account)s.load(Account.class, rid);
        } catch (Exception ex) {
            throw new BusinessException("LgAccountBase","error while load account: rid=[" +rid+ "]",ex);
        }
    }

    /**
     *
     * @param accountId String
     * @return Account*/
    public Account load(String accountId) throws BusinessException {
        Account result=null;
        try {
            RowSet rs=this.getDbAccessor().executeQuery("select * from PMS_ACNT where rst='"+Constant.RST_NORMAL+"' and ACNT_ID='"+accountId+"'");
            String rid=null;
            if(rs.next()) {
                rid=rs.getString("rid");
            }
            rs.close();
            result=this.load(Long.valueOf(rid));
            return result;
        } catch (Exception ex) {
            throw new BusinessException("LgAccountBase","error while load account: code=[" +accountId+ "]",ex);
        }
    }

   /**
     * 新增Account
     * 需判断accountId不能重复
     * @param dtoAccount DtoAccount
     * @throws BusinessException*/
    public void add(DtoPmsAcnt dataBean) throws BusinessException {
        try{
            Account dbBean = new Account();
            dbBean.setId(dataBean.getId());
            dbBean.setName(dataBean.getName());
            dbBean.setType(dataBean.getType());
            dbBean.setBrief(dataBean.getBrief());
            dbBean.setIsTemplate(new Boolean(true));
            dbBean.setAcntTailor(dataBean.getAcntTailor());
            dbBean.setStatus(IDtoAccount.ACCOUNT_STATUS_INIT);
            getDbAccessor().save(dbBean);
            //返回生成的Account RID
            dataBean.setRid(dbBean.getRid());

        }catch(Exception ex){
            log.error("", ex);
            ex.printStackTrace();
        }
    }

    /**
     * 修改Account
     * 需判断accountId不能重复
     * @param dtoAccount DtoAccount
     * @throws BusinessException*/
    public void update(DtoPmsAcnt dataBean) throws BusinessException {
        try {
            Account dbBean = (Account)this.getDbAccessor().load(Account.class, dataBean.getRid());

            dbBean.setActualFinish(dataBean.getActualFinish());
            dbBean.setActualStart(dataBean.getActualStart());
            dbBean.setAnticipatedFinish(dataBean.getAnticipatedFinish());
            dbBean.setAnticipatedStart((dataBean.getAnticipatedStart()));
            dbBean.setBrief(dataBean.getBrief());
            dbBean.setCurrency(dataBean.getCurrency());
            dbBean.setId(dataBean.getId());
            dbBean.setInner(dataBean.getInner());
            dbBean.setManager(dataBean.getManager());
            dbBean.setName(dataBean.getName());
            dbBean.setOrganization(dataBean.getOrganization());
            dbBean.setPlannedFinish(dataBean.getPlannedFinish());
            dbBean.setPlannedStart(dataBean.getPlannedStart());
            dbBean.setRid(dataBean.getRid());
            dbBean.setStatus(dataBean.getStatus());
            dbBean.setType(dataBean.getType());

            getDbAccessor().update(dbBean);
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("", ex);
            throw new BusinessException("E000000", "update pms account error.");
        }
    }

    Account account;
    public static void main(String[] args){
        try{
            LgAccountBase lg = new LgAccountBase();
            Account account = lg.load(new Long("6022"));
            System.out.println(account.getName());
            System.out.println(account.getId());
            System.out.println(account.getType());
            System.out.println(account.getManager());
            System.out.println(account.getAnticipatedStart());
            System.out.println(account.getAnticipatedStart());
            System.out.println(account.getBrief());
        }catch(Exception ex){
            log.error(ex);
        }
    }
}
