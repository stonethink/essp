package server.essp.timesheet.report.utilizationRate.gather.dao;

import java.util.List;
import java.util.Date;
import c2s.essp.timesheet.account.DtoAccount;
import c2s.essp.timesheet.approval.DtoTsApproval;

import org.springframework.orm.hibernate.support.HibernateDaoSupport;
import org.springframework.orm.hibernate.HibernateCallback;

import db.essp.timesheet.TsAccount;
import net.sf.hibernate.Query;
import net.sf.hibernate.HibernateException;
import java.sql.SQLException;
import net.sf.hibernate.Session;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author tbh
 * @version 1.0
 */
public  class UtilRateGatherDaoImp extends HibernateDaoSupport
        implements IUtilRateGatherDao{
        /**
         * ��������Ȩ�޵õ�����Ͻ�Ĳ��ż���
         * @param loginId String
         * @return List
         */
        public List listDeptInfo(String loginId) {
            String sql = "from TsAccount where manager=? and account_status='" +
                         DtoAccount.STATUS_NORMAL + "'"
                         + " and dept_flag='1' order by Account_Id";
            return (List)this.getHibernateTemplate()
                    .find(sql, new Object[] {loginId});
    
        }
        /**
         * PMO��ѯ��ʱ���г����в���
         * @return
         */
        public List listDept() {
        	String sql = "from TsAccount where account_status='"
    				+ DtoAccount.STATUS_NORMAL + "'"
    				+ " and dept_flag='1' order by Account_Id";
    		return (List) this.getHibernateTemplate().find(sql);
        }

        /**
         * ���ݲ���RID�õ������Ϣ
         * @param acntRid Long
         * @return List
         */
        public TsAccount getDeptInfoByMonths(final Long acntRid) {
                final String sql = " from TsAccount as a where a.rid=?";
                TsAccount tsAccount = (TsAccount)this.getHibernateTemplate().execute(new
                       HibernateCallback() {
                    public Object doInHibernate(Session session) throws SQLException,
                           HibernateException {
                       List list = session.createQuery(sql)
                                     .setLong(0,acntRid)
                                     .list();
                       if(list != null && list.size() > 0){
                           TsAccount tsAccount = (TsAccount)list.get(0);
                           return tsAccount;
                       }
                       return null;
                      }
               });
            return tsAccount;
        }
    
        /**
         * ���ݲ�ѯ�����õ��в�ֵ��ʱ��һ�β�ѯ��ָ�����ŵ��в�ֵ��ʱ��
         * @param beginDate Date
         * @param endDate Date
         * @param acntIdStr String
         * @return Double
         */
        public List getValidHours(final Date beginDate,
                                    final Date endDate,final String acntIds) {
                String status = DtoTsApproval.STATUS_APPROVED;
                String accountStatus = DtoAccount.STATUS_ENABLE;            
                final String sql = "select h.unitCode, sum(y.workHours)"
                    +" from TsAccount as a,TsTimesheetMaster as m,TsTimesheetDetail as d,"
                    +" TsTimesheetDay as y,TsHumanBase as h"
                    +" where h.unitCode in "+acntIds+" and y.workDate>=? and y.workDate<=?"
                    +" and lower(m.loginId) = lower(h.employeeId) and m.rid = d.tsRid "
                    +" and d.rid=y.tsDetailRid and d.accountRid=a.rid and a.billable='1'"
					+" and m.status='"+status+"' and a.accountStatus ='"+accountStatus+"'"
					+" group by h.unitCode"
                    +" order by h.unitCode";
                List actHourList = (List)this.getHibernateTemplate().execute(new
                       HibernateCallback() {
                   public Object doInHibernate(Session session) throws SQLException,
                           HibernateException {
                       Query query = session.createQuery(sql)
                                     .setDate(0,beginDate)
                                     .setDate(1,endDate);
                        return query.list();
                       }
               });
             return actHourList;
        }
    
        /**
         * ������ԃ�l���õ����H���r��
         * @param beginDate Date
         * @param endDate Date
         * @param acntIdStr String
         * @return Double
         */
        public List getActualHours(final Date beginDate,
                                      final Date endDate,final String acntIds) {
                String status = DtoTsApproval.STATUS_APPROVED;
                String accountStatus = DtoAccount.STATUS_ENABLE;
                final String sql = "select h.unitCode, sum(y.workHours)"
                    +" from TsAccount as a,TsTimesheetMaster as m,TsTimesheetDetail as d,"
                    +" TsTimesheetDay as y,TsHumanBase as h"
                    +" where h.unitCode in "+acntIds+" and y.workDate>=? and y.workDate<=?"
                    +" and lower(m.loginId) = lower(h.employeeId) and m.rid = d.tsRid "
                    +" and d.rid=y.tsDetailRid and d.accountRid=a.rid "
					+" and m.status='"+status+"' and a.accountStatus ='"+accountStatus+"'"
					+" group by h.unitCode"
                    +" order by h.unitCode";
                
               List actualHoursList = (List)this.getHibernateTemplate().execute(new
                      HibernateCallback() {
                  public Object doInHibernate(Session session) throws SQLException,
                          HibernateException {
                      Query query = session.createQuery(sql)
                                    .setDate(0,beginDate)
                                    .setDate(1,endDate);
                       return query.list();
                      }
              });
            return actualHoursList;
        }
     
        /**
         * ������Ŀ���ŵõ���ǰ��Ŀ�µ�Ա��
         * @param acntId
         * @return List
         */
        public List getLoginIdsByCondition(final String acntId){
                final String sql = "select h.employeeId from TsHumanBase as h"
                                 +" where h.unitCode=? "
                                 +" group by h.employeeId"
                                 +" order by h.employeeId";
                List list = (List)this.getHibernateTemplate().execute(new
                     HibernateCallback() {
                  public Object doInHibernate(Session session) throws SQLException,
                         HibernateException {
                     Query query = session.createQuery(sql)
                                   .setString(0,acntId);
                      return query.list();
                      }
             });
              return list;
        }
}
