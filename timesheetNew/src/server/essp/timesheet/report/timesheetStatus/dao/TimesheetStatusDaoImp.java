/*
 * Created on 2007-11-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.report.timesheetStatus.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import org.springframework.orm.hibernate.HibernateCallback;
import org.springframework.orm.hibernate.support.HibernateDaoSupport;
import c2s.essp.timesheet.report.DtoTsStatusQuery;

/**
 * <p>Title:TimesheetStatusDaoImp</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author tubaohui
 * @version 1.0
 */
public class TimesheetStatusDaoImp extends HibernateDaoSupport 
           implements ITimesheetStatusDao{

    /**
     * 根據時間區間和狀態得到符合條件的集合
     * @param begin
     * @param end
     * @param status
     * @return List
     */
    public List queryByPeriod(DtoTsStatusQuery dtoQuery,
            final String status)throws Exception {
            final Date begin = dtoQuery.getBeginDate();
            final Date end = dtoQuery.getEndDate();
            final String site  = dtoQuery.getSite();
            String deptId = dtoQuery.getDeptId();
            String deptIdStr = dtoQuery.getDeptIdStr();
            String sqlT = "select distinct a.accountName,m.loginId,a.manager,"
                          +"h.resManagerId,m.beginDate,m.endDate,d.status,h.site"
                          +" from TsTimesheetMaster as m,TsTimesheetDetail as d,"
                          +" TsAccount as a,TsHumanBase as h"
                          +" where m.beginDate>=? and m.endDate<=? and m.status=?"
                          +" and lower(m.loginId)=lower(h.employeeId)"
                          +" and m.rid=d.tsRid and d.accountRid=a.rid";
            if(!dtoQuery.getIsDeptQuery() && site != null) {
                sqlT += " and lower(h.site)=lower('" + site + "') ";
            }
            if(deptId != null && dtoQuery.getIsSub() != null && dtoQuery.getIsSub()){
                sqlT +=" and h.unitCode in"+deptIdStr+"";
            }else if(deptId != null && dtoQuery.getIsSub() != null && !dtoQuery.getIsSub()){
                sqlT +=" and h.unitCode ='"+deptId+"'";
            }
            sqlT +=  " order by m.loginId,m.beginDate";
            final String sql = sqlT;
            List list = (List)this.getHibernateTemplate().execute(new
              HibernateCallback() {
            public Object doInHibernate(Session session) throws SQLException,
                  HibernateException {
              Query query = session.createQuery(sql)
                            .setDate(0,begin)
                            .setDate(1,end)
                            .setString(2,status);
              return query.list();
              }
        });
       return list;
    }
    
    /**
     * 根據時間區域得到未填寫工時單的數據
     * @param begin
     * @param end
     * @param status
     * @return List
     */
    public List queryUnfilledByPeriod(DtoTsStatusQuery dtoQuery)
            throws Exception {
            final Date begin = dtoQuery.getBeginDate();
            final Date end = dtoQuery.getEndDate();
            final String site = dtoQuery.getSite();
            String deptId = dtoQuery.getDeptId();
            String deptIdStr = dtoQuery.getDeptIdStr();
            String sqlT = "from TsHumanBase as h"
                         +" where not exists (select m.loginId"
                         +" from TsTimesheetMaster as m,TsTimesheetDetail as d"
                         +" where m.rid=d.tsRid and lower(m.loginId) = lower(h.employeeId)"
                         +" and m.beginDate>=? and m.endDate<=? )";
            if(deptId != null && dtoQuery.getIsSub() != null && dtoQuery.getIsSub()){
                sqlT +=" and h.unitCode in"+deptIdStr+"";
            }else if(deptId != null && dtoQuery.getIsSub() != null && !dtoQuery.getIsSub()){
                sqlT +=" and h.unitCode ='"+deptId+"'";
            }
            if(!dtoQuery.getIsDeptQuery() && site != null) {
                sqlT += " and lower(h.site)=lower('" + site + "') ";
            }
            sqlT += " and h.inDate <= ? and (h.outDate is null or h.outDate > ?)";
            sqlT += " order by h.employeeId";
            final String sql = sqlT;
            List list = (List)this.getHibernateTemplate().execute(new
              HibernateCallback() {
            public Object doInHibernate(Session session) throws SQLException,
                  HibernateException {
              Query query = session.createQuery(sql)
                            .setDate(0,begin)
                            .setDate(1,end)
                            .setDate(2,end)
                            .setDate(3,begin);
              return query.list();
              }
        });
        return list;
    }
    
    /**
     * 得到指定部門下需要填寫工時單人員集合
     * @param dtoQuery
     * @param site
     * @param viewAll
     * @return List
     * @throws Exception
     */
    public List getEmpIdList(DtoTsStatusQuery dtoQuery){
            final Date begin = dtoQuery.getBeginDate();
            final Date end = dtoQuery.getEndDate();
            final String deptIds = dtoQuery.getDeptIdStr();
            String sql = "select h.employeeId, h.unitCode from TsHumanBase as h where "
                        +" h.inDate <= ? and (h.outDate is null or h.outDate >?) ";
            if(dtoQuery.getIsDeptQuery() && dtoQuery.getIsSub()){
              sql +=" and h.unitCode in "+deptIds+"";
            }else{
                sql +=" and h.unitCode = '"+deptIds+"'";  
            }
            sql += " group by h.employeeId, h.unitCode";
            sql += " order by h.employeeId";
            final String sqlT = sql;
            List list = (List)this.getHibernateTemplate().execute(new
             HibernateCallback() {
              public Object doInHibernate(Session session) throws SQLException,
                 HibernateException {
              Query query = session.createQuery(sqlT)
                           .setDate(0,end)
                           .setDate(1,begin);
              return query.list();
             }
          });
           return list;
        }
    
    /**
     * 得到需填寫工時單人數集合
     * @param dtoQuery
     * @param flag
     * @return List
     * @throws Exception
     */
     public List getFillTSEmpIdList(DtoTsStatusQuery dtoQuery,boolean flag
                 ) throws Exception {
                final Date begin = dtoQuery.getBeginDate();
                final Date end = dtoQuery.getEndDate();
                final String site = dtoQuery.getSite();
                String deptId = dtoQuery.getDeptId();
                String deptIdStr = dtoQuery.getDeptIdStr();
                String sqlT = "select upper(h.employeeId),h.unitCode,upper(h.site) from TsHumanBase as h where 1=1";
                if(flag && deptId != null && dtoQuery.getIsSub() != null && dtoQuery.getIsSub()){
                    sqlT +=" and h.unitCode in"+deptIdStr+"";
                }else if(flag && deptId != null && dtoQuery.getIsSub() != null && !dtoQuery.getIsSub()){
                    sqlT +=" and h.unitCode ='"+deptId+"'";
                }
                if(!dtoQuery.getIsDeptQuery() && site != null) {
                    sqlT += " and lower(h.site)=lower('" + site + "') ";
                }
                sqlT += " and h.inDate <= ? and (h.outDate is null or h.outDate > ?)";
                sqlT += " group upper(h.employeeId),h.unitCode,upper(h.site)";
                sqlT += " order upper(h.employeeId),h.unitCode,upper(h.site)";
                final String sql = sqlT;
                List list = (List)this.getHibernateTemplate().execute(new
                  HibernateCallback() {
                public Object doInHibernate(Session session) throws SQLException,
                      HibernateException {
                  Query query = session.createQuery(sql)
                                .setDate(0,end)
                                .setDate(1,begin);
                  return query.list();
                  }
            });
            return list;
        }
    
}
