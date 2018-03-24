package server.essp.pms.account.labor.logic;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.wits.util.comDate;
import db.essp.pms.Account;
import db.essp.pms.LaborResource;
import db.essp.pms.LaborResourceDetail;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import server.framework.common.*;
import server.framework.logic.AbstractBusinessLogic;
import java.util.Iterator;
import java.util.ArrayList;
import c2s.essp.common.calendar.WorkCalendar;
import c2s.essp.common.calendar.WrokCalendarFactory;
import server.essp.framework.logic.AbstractESSPLogic;


/**
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: WITS-WH</p>
 * @version 1.0
 * @author stone*/
public class LgAccountLaborResBase extends AbstractESSPLogic {
//    public static final int DAY_WORK_HOUR = 8;

    Account account;
    List listLaborResource;
    LaborResource laborResource;

    public LgAccountLaborResBase() {
    }

    public LgAccountLaborResBase(Account account) {
        this.account = account;
    }

    public List listLaborRes() throws BusinessException {
        if (account == null) {
            return null;
        }
        List list = new ArrayList();
        for (Iterator it = account.getLaborResources().iterator(); it.hasNext(); ) {
            LaborResource laborResource = (LaborResource) it.next();
            if (Constant.RST_NORMAL.equals(laborResource.getRst())) {
                list.add(laborResource);
            }
        }
        return list;
    }


    /**
     * ��ȡȫ����Labor Resources
     * @return List {DtoLaborRes}*/
    public List listLaborRes(Long acntRid) throws BusinessException {
        try {
            Session s = this.getDbAccessor().getSession();
            List l = s.createQuery("from LaborResource as res " +
                                   "where res.account.rid=:acntRid "
                                   )
                     .setParameter("acntRid", acntRid)
                     .list();
            this.listLaborResource = l;
            return l;
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("ACNT_LABOR_001",
                "error while list all resource of account [" + acntRid +
                "]",ex);
        }
    }

    /**
     * ���㹤��ʱ��
     * @param begin Calendar
     * @param end Calendar
     * @param percent Long
     * @return float
     */
    public static Double caculateWorkHours(Calendar begin, Calendar end,
                                          Long percent) {
        if (begin == null || end == null) {
            return new Double(0);
        }
        WorkCalendar wc = WrokCalendarFactory.serverCreate();
        double iWH = wc.getWorkHours(begin,end);
        Double lWH = new Double( iWH * percent.longValue() /100);
        return lWH;
    }

    public static Double caculateWorkHours(Date begin, Date end, Long percent) {
        if (begin == null || end == null) {
            return new Double(0);
        }

        Calendar b = new GregorianCalendar();
        b.setTime(begin);
        Calendar e = new GregorianCalendar();
        e.setTime(end);
        return caculateWorkHours(b, e, percent);
    }


    /**
     * ����Labor Resources
     * 1. Check:
     * ����ͬһ��account�ڵ�loginId�����ظ����Ҳ�ΪNull
     * 2. ����PMS_LABOR_RESOURCES
     * 3. ����PMS_LABOR_RESOURCE_DETAIL��
     * @param res LaborResource
     */
    public void add(LaborResource res) {
        try {
            Account acnt = res.getAccount();
            String loginId = res.getLoginId();
            checkResourceLoginId(acnt, loginId);
            this.getDbAccessor().save(res);
        } catch(BusinessException e){
            throw e;
        }
        catch (Exception ex) {
            log.error(ex);
            //throw new BusinessException("ACNT_LABOR_009","error while adding labor resource!");
            /**
             * ÿ���������Ǳ���ģ���������֪������ĸ�Դ   modified by yery on 2005/10/18
             */
            throw new BusinessException("ACNT_LABOR_009","error while adding labor resource!",ex);
        }
    }


    /**
     * �޸�Labor Resource
     * @param res LaborResource
     * @return LaborResource
     */
    public void update(LaborResource res) {
        try {
            this.getDbAccessor().update(res);
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("ACNT_LABOR_002","error while update resource [" +
                                        res.getEmpName() + "]",ex);
        }
    }


    /**
     *���LaborResource��Account��loginId
     */
    private void checkResourceLoginId(Account acnt, String loginId) throws
        HibernateException, Exception {
        LaborResource result = findResource(acnt, loginId);
        if (result != null) {
            throw new BusinessException("ACNT_LABOR_003","labor resource [" + loginId +
                                        "] is in this account!");
        }
    }


    /**
     * ������Ŀ�ڵ���Ա
     * @param acnt Account
     * @param loginId String
     * @return List
     * @throws Exception
     */
    public LaborResource findResource(Account acnt, String loginId) {
        try {
            Session s = this.getDbAccessor().getSession();
            LaborResource result = (LaborResource) s.createQuery(
                "from LaborResource as res " +
                "where res.loginId=:loginId and res.account=:acnt")
                                   .setString("loginId", loginId)
                                   .setParameter("acnt", acnt)
                                   .uniqueResult()
                                   ;
            return result;
        } catch (Exception ex) {
            throw new BusinessException("ACNT_LABOR_004",
                "error while finding resource ,loginid:[" + loginId +
                "] ,acount Rid:[" + acnt.getRid() + "]",ex);
        }
    }


    /**
     * ɾ��LaborResource res
     * Hibernate�����ü���ɾ�����й��� LaborResourceDetail
     * @param res LaborResource
     */
    public void delete(LaborResource res) {
        try {
            Session s = this.getDbAccessor().getSession();
            s.load(res, res.getRid());
            res.getLaborResourceDetails().clear();
            this.getDbAccessor().delete(res);
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("ACNT_LABOR_005","error while delete resource [" +
                                        res.getLoginId() + "]",ex);
        }
    }
}
