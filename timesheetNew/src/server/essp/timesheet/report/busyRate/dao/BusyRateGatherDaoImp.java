/*
 * Created on 2008-6-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.report.busyRate.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;

import org.springframework.orm.hibernate.HibernateCallback;
import org.springframework.orm.hibernate.support.HibernateDaoSupport;

import c2s.essp.timesheet.account.DtoAccount;
import c2s.essp.timesheet.approval.DtoTsApproval;
import db.essp.timesheet.TsAccount;

public class BusyRateGatherDaoImp extends HibernateDaoSupport
         implements IBusyRateGatherDao{

        /**
         * 根据部门RID得到相关信息
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
         * 根据查询条件得到有产值工时（一次查询出指定部门的有产值工时）
         * @param beginDate Date
         * @param endDate Date
         * @param acntIdStr String
         * @return Double
         */
        public List getValidHours(final Date beginDate, final Date endDate,
                final String deptIds) {
                String status = DtoTsApproval.STATUS_APPROVED;
                String accountStatus = DtoAccount.STATUS_ENABLE;            
                final String sql = "select h.unitCode,h.isDirect, sum(y.workHours)"
                    +" from TsAccount as a,TsTimesheetMaster as m,TsTimesheetDetail as d,"
                    +" TsTimesheetDay as y,TsHumanBase as h"
                    +" where h.unitCode in "+deptIds+" and y.workDate>=? and y.workDate<=? "
                    +" and m.rid = d.tsRid and lower(m.loginId) = lower(h.employeeId)"
                    +" and d.rid=y.tsDetailRid and d.accountRid=a.rid and a.billable='1'"
					+" and m.status='"+status+"' and a.accountStatus ='"+accountStatus+"'"
					+" group by h.unitCode,h.isDirect"
                    +" order by h.unitCode,h.isDirect";
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
         * 根据查询条件得到无产值工时（一次查询出指定部门的无产值工时）
         * @param beginDate Date
         * @param endDate Date
         * @param acntIdStr String
         * @return Double
         */
        public List getActualHours(final Date beginDate, final Date endDate,
                final String deptIds) {
                String status = DtoTsApproval.STATUS_APPROVED;
                String accountStatus = DtoAccount.STATUS_ENABLE;
                final String sql = "select h.unitCode, h.isDirect,sum(y.workHours)"
                    +" from TsAccount as a,TsTimesheetMaster as m,TsTimesheetDetail as d,"
                    +" TsTimesheetDay as y,TsHumanBase as h"
                    +" where h.unitCode in "+deptIds+" and y.workDate>=? and y.workDate<=?"
                    +" and lower(m.loginId) = lower(h.employeeId)"
                    +" and d.rid=y.tsDetailRid and d.accountRid=a.rid and m.rid = d.tsRid"
                    +" and m.status='"+status+"' and a.accountStatus ='"+accountStatus+"'"
                    +" group by h.unitCode,h.isDirect"
                    +" order by h.unitCode,h.isDirect";
    
               List invalidHoursList = (List)this.getHibernateTemplate().execute(new
                      HibernateCallback() {
                  public Object doInHibernate(Session session) throws SQLException,
                          HibernateException {
                      Query query = session.createQuery(sql)
                                    .setDate(0,beginDate)
                                    .setDate(1,endDate);
                       return query.list();
                      }
              });
            return invalidHoursList;
        }
     
        /**
         * 按部門代號得到對應部門下的所有員工集合
         * @param acntId
         * @return List
         */
        public List getLoginIdsByDeptIds(final String acntIds){
                final String sql = "select h.employeeId,h.unitCode from TsHumanBase as h"
                                 +" where h.unitCode in "+acntIds+" and h.isDirect='D'"
                                 +" group by h.employeeId,h.unitCode"
                                 +" order by h.employeeId,h.unitCode";
                List list = (List)this.getHibernateTemplate().execute(new
                     HibernateCallback() {
                  public Object doInHibernate(Session session) throws SQLException,
                         HibernateException {
                     Query query = session.createQuery(sql);
                      return query.list();
                      }
             });
              return list;
        }

}
