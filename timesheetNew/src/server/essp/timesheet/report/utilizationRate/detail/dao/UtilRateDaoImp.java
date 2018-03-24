package server.essp.timesheet.report.utilizationRate.detail.dao;

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
import c2s.essp.timesheet.report.DtoUtilRateQuery;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author TuBaohui
 * @version 1.0
 */
public class UtilRateDaoImp extends HibernateDaoSupport implements IUtilRateDao{
        /**
         * ��ĳʱ�������ڣ����ָ������ָ��Ա���ĸ�����ʱ��(������)
         * @param dtoQuery
         * @return List
         */
          public List getDataByDate(DtoUtilRateQuery dtoQuery) {
                String status = DtoTsApproval.STATUS_APPROVED;
                String accountStatus = DtoAccount.STATUS_ENABLE;
                final Date beginDate = dtoQuery.getBegin();
                final Date endDate = dtoQuery.getEnd();
                final String loginIds = dtoQuery.getLoginIds();
                String acntId = dtoQuery.getAcntId();
                Boolean isSub = dtoQuery.getIsSub();
                String deptIds = dtoQuery.getDeptIds();
                String sql = "select h.employeeId, h.unitCode,h.englishName,h.chineseName,"
                             +"y.workDate,nvl(sum(y.workHours),0)"
                             +" from TsAccount as a,TsHumanBase as h,"
                             +" TsTimesheetMaster as m,TsTimesheetDetail as d,TsTimesheetDay as y"
                             +" where upper(m.loginId)=upper(h.employeeId)";
                if(isSub){
                    sql +=" and  h.unitCode in "+deptIds+"";
                }else{
                    sql +=" and  h.unitCode ='"+acntId+"' and h.employeeId in "+loginIds+"";
                }
                sql +=" and y.workDate>=? and y.workDate<=?";
                sql += " and d.tsRid=m.rid and d.rid=y.tsDetailRid and a.rid = d.accountRid"
                       +" and m.status='"+status+"' and a.accountStatus ='"+accountStatus+"'";
                sql +=" group by h.employeeId,h.unitCode,h.englishName,h.chineseName,y.workDate";
                sql +=" order by h.employeeId,y.workDate";
                final String sqlResult = sql;
                List list = (List)this.getHibernateTemplate().execute(new
                       HibernateCallback() {
                    public Object doInHibernate(Session session) throws SQLException,
                           HibernateException {
                       Query query = session.createQuery(sqlResult)
                                     .setDate(0,beginDate)
                                     .setDate(1,endDate);
                        return query.list();
                       }
               });
                return list;
            }
          
          /**
           * �������ڲ�ԃ���Юa�Ŀ�Ĺ��r
           * @param dtoQuery
           * @return List
           */
            public List getValidHoursByDate(DtoUtilRateQuery dtoQuery) {
                 String status = DtoTsApproval.STATUS_APPROVED;
                 String accountStatus = DtoAccount.STATUS_ENABLE;
                 final Date beginDate = dtoQuery.getBegin();
                 final Date endDate = dtoQuery.getEnd();
                 final String loginIds = dtoQuery.getLoginIds();
                 String acntId = dtoQuery.getAcntId();
                 Boolean isSub = dtoQuery.getIsSub();
                 String deptIds = dtoQuery.getDeptIds();
                 String sql = "select h.employeeId, h.unitCode,y.workDate, nvl(sum(y.workHours),0)"
                              +" from TsAccount as a,TsHumanBase as h,"
                              +" TsTimesheetMaster as m,TsTimesheetDetail as d,TsTimesheetDay as y"
                              +" where upper(m.loginId)=upper(h.employeeId) and a.rid = d.accountRid";
                 if(isSub){
                     sql +=" and h.unitCode in "+deptIds+"";
                 }else{
                     sql +=" and h.unitCode ='"+acntId+"' and h.employeeId in "+loginIds+"";
                 }
                 sql +=" and y.workDate>=? and y.workDate<=?";
                 sql += " and d.tsRid=m.rid and a.rid = d.accountRid and d.rid=y.tsDetailRid "
                       +" and a.billable='1' and m.status='"+status+"'"
                       +" and a.accountStatus ='"+accountStatus+"'";
                 sql +=" group by h.employeeId, h.unitCode,y.workDate";
                 final String sqlT = sql;
                 List validHourList = (List)this.getHibernateTemplate().execute(new
                        HibernateCallback() {
                     public Object doInHibernate(Session session) throws SQLException,
                            HibernateException {
                        Query query = session.createQuery(sqlT)
                                      .setDate(0,beginDate)
                                      .setDate(1,endDate);
                         return query.list();
                        }
                });
                 return validHourList;
         }

        /**
         * ��ĳ�·������ڣ����ָ������ָ��Ա���ĸ�����ʱ��(���·�)
         * @param dtoQuery
         * @return List
         */ 
        public List getDataBeanByMonths(DtoUtilRateQuery dtoQuery) {
                String status = DtoTsApproval.STATUS_APPROVED;
                final Date beginDate = dtoQuery.getBegin();
                final Date endDate = dtoQuery.getEnd();
                String loginIds = dtoQuery.getLoginIds();
                String acntId = dtoQuery.getAcntId();
                Boolean isSub = dtoQuery.getIsSub();
                String deptIds = dtoQuery.getDeptIds();
                String sql = "select h.employeeId,h.englishName,h.chineseName,h.unitCode"
                             +" from TsHumanBase as h,"
                             +" TsTimesheetMaster as m,TsTimesheetDetail as d,TsTimesheetDay as y"
                             +" where upper(m.loginId)=upper(h.employeeId)";
                if(isSub){
                    sql +=" and h.unitCode in "+deptIds+"";
                }else{
                    sql +=" and h.unitCode ='"+acntId+"' and h.employeeId in "+loginIds+"";
                }
                sql +=" and y.workDate>=? and y.workDate<=? ";
                sql +=" and d.tsRid=m.rid and d.rid=y.tsDetailRid and m.status='"+status+"'";
                sql +=" group by h.employeeId,h.englishName,h.chineseName,h.unitCode";
                sql +=" order by h.employeeId";
                final String sqlResult = sql;
                List list = (List)this.getHibernateTemplate().execute(new
                       HibernateCallback() {
                    public Object doInHibernate(Session session) throws SQLException,
                           HibernateException {
                       Query query = session.createQuery(sqlResult)
                                     .setDate(0,beginDate)
                                     .setDate(1,endDate);
                        return query.list();
                       }
               });
                return list;
        }

       /**
        *  ��ĳʱ�������ڣ����ָ������ָ��Ա���ĸ�����ʱ��(����ʱ������)
        * @param dtoQuery
        * @return List
        */
        public List getDataByCycle(DtoUtilRateQuery dtoQuery) {
               String acntId = dtoQuery.getAcntId();
               String deptIds = dtoQuery.getDeptIds();
               Boolean isSub = dtoQuery.getIsSub();
               String loginIds = dtoQuery.getLoginIds();
               final Date beginDate = dtoQuery.getBegin();
               final Date endDate = dtoQuery.getEnd();
               String status = DtoTsApproval.STATUS_APPROVED;
               String accountStatus = DtoAccount.STATUS_ENABLE;
               String sql = "select h.employeeId,h.unitCode,h.englishName,h.chineseName," 
                            +" m.beginDate,m.endDate,nvl(sum(y.workHours),0)"
                            +" from TsAccount as a,TsHumanBase as h,"
                            +" TsTimesheetMaster as m,TsTimesheetDetail as d,TsTimesheetDay as y"
                            +" where upper(m.loginId)=upper(h.employeeId)";
               if(isSub){
                   sql +=" and h.unitCode in"+deptIds+"";
               }else{
                   sql +=" and h.unitCode ='"+acntId+"' and h.employeeId in "+loginIds+"";
               }
               sql += " and m.beginDate>=? and m.endDate<=?";
               sql += " and d.tsRid=m.rid and d.rid=y.tsDetailRid and a.rid = d.accountRid";
               sql += " and m.status='"+status+"' and a.accountStatus ='"+accountStatus+"'";
               sql += " group by h.employeeId,h.unitCode,h.englishName,h.chineseName,m.beginDate,m.endDate";
               sql += " order by h.employeeId";
               final String sqlResult = sql;
               List list = (List)this.getHibernateTemplate().execute(new
                      HibernateCallback() {
                  public Object doInHibernate(Session session) throws SQLException,
                          HibernateException {
                      Query query = session.createQuery(sqlResult)
                                    .setDate(0,beginDate)
                                    .setDate(1,endDate);
                       return query.list();
                      }
              });
               return list;
        }

        /**
         * �����·ݲ�ԃ�����H���r
         * @param dtoQuery
         * @return List
         */
       public  List getAcutalHourByMonths(DtoUtilRateQuery dtoQuery){
            String status = DtoTsApproval.STATUS_APPROVED;
            String accountStatus = DtoAccount.STATUS_ENABLE;
            final Date beginDate = dtoQuery.getBegin();
            final Date endDate = dtoQuery.getEnd();
            String loginIds = dtoQuery.getLoginIds();
            String acntId = dtoQuery.getAcntId();
            Boolean isSub = dtoQuery.getIsSub();
            String deptIds = dtoQuery.getDeptIds();
            String sql = "select h.employeeId,h.unitCode,nvl(sum(y.workHours),0)"
                            +" from TsAccount as a,TsHumanBase as h,"
                            +" TsTimesheetMaster as m,TsTimesheetDetail as d,TsTimesheetDay as y"
                            +" where upper(m.loginId)=upper(h.employeeId)";
            if(isSub){
                sql +=" and h.unitCode in "+deptIds+"";
            }else{
                sql +=" and h.unitCode ='"+acntId+"' and h.employeeId in "+loginIds+"";
            }
            sql +=" and y.workDate>=? and y.workDate<=? ";
            sql +=" and d.tsRid=m.rid and d.rid=y.tsDetailRid and a.rid = d.accountRid";
            sql +=" and m.status='"+status+"' and a.accountStatus ='"+accountStatus+"'";
            sql +=" group by h.employeeId,h.unitCode";
            final String sqlT = sql;
            List actHourList = (List)this.getHibernateTemplate().execute(new
                     HibernateCallback() {
                 public Object doInHibernate(Session session) throws SQLException,
                         HibernateException {
                     Query query = session.createQuery(sqlT)
                                   .setDate(0,beginDate)
                                   .setDate(1,endDate);
                      return query.list();
                     }
             });
           return actHourList;
    }
    
       /**
        * �������ڲ�ԃ����Ч���r
        * @param dtoQuery
        * @return List
        */
        public List getValidHourByCycle(DtoUtilRateQuery dtoQuery){
                String status = DtoTsApproval.STATUS_APPROVED;
                String accountStatus = DtoAccount.STATUS_ENABLE;
                final Date beginDate = dtoQuery.getBegin();
                final Date endDate = dtoQuery.getEnd();
                String loginIds = dtoQuery.getLoginIds();
                String acntId = dtoQuery.getAcntId();
                Boolean isSub = dtoQuery.getIsSub();
                String deptIds = dtoQuery.getDeptIds();
                String sql = "select h.employeeId,h.unitCode,m.beginDate,m.endDate, nvl(sum(y.workHours),0)"
                                +" from TsAccount as a,TsHumanBase as h,"
                                +" TsTimesheetMaster as m,TsTimesheetDetail as d,TsTimesheetDay as y"
                                +" where upper(m.loginId)=upper(h.employeeId)"
                                +" and d.tsRid=m.rid and a.rid = d.accountRid ";
                if(isSub){
                    sql +=" and h.unitCode in "+deptIds+"";
                }else{
                    sql +=" and h.unitCode ='"+acntId+"' and h.employeeId in "+loginIds+"";
                }
                sql +=" and y.workDate>=? and y.workDate<=? ";
                sql +=" and d.rid=y.tsDetailRid and  m.status='"+status+"' and a.billable='1'";
                sql +=" and a.accountStatus ='"+accountStatus+"'";
                sql +=" group by h.employeeId,h.unitCode,m.beginDate,m.endDate";
                final String sqlT = sql;
                List validHourList = (List)this.getHibernateTemplate().execute(new
                         HibernateCallback() {
                     public Object doInHibernate(Session session) throws SQLException,
                             HibernateException {
                         Query query = session.createQuery(sqlT)
                                       .setDate(0,beginDate)
                                       .setDate(1,endDate);
                          return query.list();
                         }
                 });
               return validHourList;
        }

        /**
         * ����loginId�õ�TS_ACCOUNT�ж�Ӧ��acntRid,�ٸ���acntRid��TS_LABOR_RESOURCE�еõ���Ӧ��Ա�����ż���
         * @param loginId String
         * @return List
         */
        public List listUsers(String acntId) {
            String sql = "from TsHumanBase where unit_code=?";
            return (List)this.getHibernateTemplate()
                    .find(sql, new Object[] {acntId});
        }

        /**
         * ����loginId�õ�TS_ACCOUNT�ж�Ӧ�Ĳ��Ŵ��źͲ�������
         * @param loginId String
         * @return List
         */
        public List listDeptInfo(String loginId) {
            String sql = "from TsAccount where upper(manager)=upper(?) and account_status='" +
                         DtoAccount.STATUS_ENABLE + "'"
                         + " and dept_flag='1' order by Account_Id";
            return (List)this.getHibernateTemplate()
                    .find(sql, new Object[] {loginId});
        }
        
        public List listDepts() {
            String sql = "from TsAccount where account_status='"
                    + DtoAccount.STATUS_ENABLE + "'"
                    + " and dept_flag='1' order by Account_Id";
            return (List) this.getHibernateTemplate().find(sql);
        }

        /**
         * �����·ݲ�ԃ���Юa�Ŀ�µ��Юa���r
         * @param dtoQuery
         * @return List
         */
        public List getValidHourByMonths(DtoUtilRateQuery dtoQuery) {
                String status = DtoTsApproval.STATUS_APPROVED;
                String accountStatus = DtoAccount.STATUS_ENABLE;  
                final Date beginDate = dtoQuery.getBegin();
                final Date endDate = dtoQuery.getEnd();
                String loginIds = dtoQuery.getLoginIds();
                String acntId = dtoQuery.getAcntId();
                Boolean isSub = dtoQuery.getIsSub();
                String deptIds = dtoQuery.getDeptIds();
                String sql = "select h.employeeId,h.unitCode, nvl(sum(y.workHours),0)"
                            +" from TsAccount as a,TsHumanBase as h,"
                            +" TsTimesheetMaster as m,TsTimesheetDetail as d,TsTimesheetDay as y"
                            +" where upper(m.loginId)=upper(h.employeeId) ";
                if(isSub){
                    sql +=" and h.unitCode in"+deptIds+"";
                }else{
                    sql +=" and h.unitCode ='"+acntId+"' and h.employeeId in "+loginIds+"";
                }
                sql +=" and y.workDate>=? and y.workDate<=? ";
                sql +=" and d.tsRid=m.rid and a.rid = d.accountRid and d.rid=y.tsDetailRid and a.billable='1'";
                sql +=" and m.status='"+status+"' and a.accountStatus ='"+accountStatus+"'";
                sql +=" group by h.employeeId,h.unitCode";                        
                
                final String sqlT = sql;
                List validHourList = (List)this.getHibernateTemplate().execute(new
                         HibernateCallback() {
                     public Object doInHibernate(Session session) throws SQLException,
                             HibernateException {
                         Query query = session.createQuery(sqlT)
                                       .setDate(0,beginDate)
                                       .setDate(1,endDate);
                          return query.list();
                         }
                 });
               return validHourList;
            }
}
