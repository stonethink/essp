package server.essp.pms.account.keyperson.logic;

import java.util.List;

import db.essp.pms.Account;
import db.essp.pms.Keypesonal;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import server.essp.framework.logic.AbstractESSPLogic;
import server.framework.common.BusinessException;

public class LgAccountKeyPersonnelBase extends AbstractESSPLogic {
    /**
     * ������Ŀ��Key Personnel���ж�ͬһ��Ŀ�ڲ�������ͬ��LoginId
     * @param person Keypesonal
     */
    public void add(Keypesonal person){
        Account acnt = person.getAccount();
        String loginId = person.getLoginId();
        try {
            checkPersonnelLoginId(acnt,loginId);
            this.getDbAccessor().save(person);
        }catch(BusinessException e){
            throw e;
        }
        catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("ACNT_KP_001","error while saving account Key Personnel:["+loginId+"]",ex);
        }
    }
    /**
     * ���һ����Ŀ�е�KeyPersonnel�Ƿ����ظ�
     * @param acnt Account
     * @param loginId String
     * @throws HibernateException
     * @throws Exception
     */
    private void checkPersonnelLoginId(Account acnt,String loginId) throws HibernateException, Exception {
        Session s = this.getDbAccessor().getSession();
        List l = s.createQuery("from Keypesonal as person " +
                               "where person.loginId=:loginId and person.account=:acnt")
                 .setString("loginId",loginId)
                 .setParameter("acnt",acnt)
                 .list();
        if(l.size() > 0)
            throw new BusinessException("ACNT_KP_002","Key Personnel [" + loginId +"] is in this account!");

    }
    /**
     * ����Key Personnel
     * @param person Keypesonal
     */
    public void update(Keypesonal person){
        try {
            this.getDbAccessor().update(person);
        } catch (Exception ex) {
            throw new BusinessException("ACNT_KP_003","error while updating account Key Personnel:["+person.getLoginId()+"]",ex);
        }
    }
    /**
     * ɾ��Keypesonal
     * @param person Keypesonal
     */
    public void delete(Keypesonal person){
        try {
            this.getDbAccessor().delete(person);
        } catch (Exception ex) {
            throw new BusinessException("ACNT_KP_004","error while deleting account Key Personnel:["+person.getLoginId()+"]",ex);
        }
    }
    /**
     * ������Ŀ��KeyPersonnel
     * @param acntRid Long
     * @return List
     */
    public List listKeyPersonnel(Long acntRid)  {
        try {
            Session s = this.getDbAccessor().getSession();
            List l = s.createQuery("from Keypesonal as person " +
                                   "where person.account.rid=:acntRid")
                     .setParameter("acntRid", acntRid)
                     .list();
            return l;
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("ACNT_KP_005","error while list key personnels of account:[" +acntRid+ "]",ex);
        }
    }
    public static void main(String[] args){
        LgAccountKeyPersonnelBase base = new LgAccountKeyPersonnelBase();
        List list =base.listKeyPersonnel(new Long(6022));
        System.out.println(list.size());
    }
}
