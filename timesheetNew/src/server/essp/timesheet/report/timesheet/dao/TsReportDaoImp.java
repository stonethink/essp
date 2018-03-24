package server.essp.timesheet.report.timesheet.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import net.sf.hibernate.*;

import org.springframework.orm.hibernate.HibernateCallback;
import org.springframework.orm.hibernate.support.HibernateDaoSupport;

import c2s.essp.timesheet.report.DtoQueryCondition;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheet;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author wenhaizheng
 * @version 1.0
 */
public class TsReportDaoImp extends HibernateDaoSupport implements ITsReportDao{
    public List queryReport(final DtoQueryCondition dto, final String loginIds, String orderBy) {
       final String sql = "select d.workDate,a.accountId,a.accountName,m.loginId,"
               + " c.name,c.description,c.isLeaveType,"
               + " de.jobDescription,"
               + " sum(d.workHours),sum(d.overtimeHours)"
               + " from TsTimesheetDay as d,TsAccount as a, "
               + " TsTimesheetDetail as de,TsTimesheetMaster as m,TsCodeValue as c"
               + " where lower(m.loginId) in "+loginIds
               + " and a.rid=de.accountRid"
               + " and de.tsRid=m.rid"
               + " and d.tsDetailRid=de.rid"
               + " and c.rid=de.codeValueRid"
               + " and d.workDate>=?"
               + " and d.workDate<=?"
               + " and m.status='"+DtoTimeSheet.STATUS_APPROVED+"'"
               + " group by d.workDate,a.accountId,a.accountName,m.loginId,"
               + " c.name,c.description,c.isLeaveType,"
               + " de.jobDescription "
//               + " d.workHours,d.overtimeHours "
               + orderBy;
       List list = this.getHibernateTemplate().executeFind(new HibernateCallback(){
            public Object doInHibernate(Session session) throws SQLException,
                    HibernateException {
                Query q = session.createQuery(sql)
                          .setDate(0, dto.getBegin())
                          .setDate(1, dto.getEnd());
                return q.list();
            }
        });
        return list;
    }
    public List queryReportByDept(final DtoQueryCondition dto, final String deptIds) {
    	final String sql = "select d.workDate,a.accountId,a.accountName,m.loginId,"
            + " c.name,c.description,c.isLeaveType,"
            + " de.jobDescription,"
            + " sum(d.workHours),sum(d.overtimeHours)"
            + " from TsTimesheetDay as d,TsAccount as a, "
            + " TsTimesheetDetail as de,TsTimesheetMaster as m,TsCodeValue as c,"
            + " TsAccount u, TsHumanBase h"
            + " where lower(m.loginId)=lower(h.employeeId) "
            + " and h.unitCode=u.accountId "
            + " and u.accountId in "+deptIds
            + " and a.rid=de.accountRid "
            + " and de.tsRid=m.rid"
            + " and d.tsDetailRid=de.rid"
            + " and c.rid=de.codeValueRid"
            + " and d.workDate>=?"
            + " and d.workDate<=?"
            + " and m.status='"+DtoTimeSheet.STATUS_APPROVED+"'"
            + " group by d.workDate,a.accountId,a.accountName,m.loginId,"
            + " c.name,c.description,c.isLeaveType,"
            + " de.jobDescription"
            + " order by m.loginId";
    List list = this.getHibernateTemplate().executeFind(new HibernateCallback(){
         public Object doInHibernate(Session session) throws SQLException,
                 HibernateException {
             Query q = session.createQuery(sql)
                       .setDate(0, dto.getBegin())
                       .setDate(1, dto.getEnd());
             return q.list();
         }
     });
     return list;
    }
    
    public List queryReportForPm(final DtoQueryCondition dto, final String loginIds, 
    		                     String orderBy, String acntRids) {
        String sql = "select d.workDate,a.accountId,a.accountName,m.loginId,"
                + " c.name,c.description,c.isLeaveType,"
                + " de.jobDescription,"
                + " sum(d.workHours),sum(d.overtimeHours)"
                + " from TsTimesheetDay as d,TsAccount as a, "
                + " TsTimesheetDetail as de,TsTimesheetMaster as m,TsCodeValue as c"
                + " where lower(m.loginId) in "+loginIds
                + " and a.rid=de.accountRid"
                + " and de.tsRid=m.rid"
                + " and d.tsDetailRid=de.rid"
                + " and c.rid=de.codeValueRid"
                + " and d.workDate>=?"
                + " and d.workDate<=?"
                + " and m.status='"+DtoTimeSheet.STATUS_APPROVED+"'"
                + " group by d.workDate,a.accountId,a.accountName,m.loginId,"
                + " c.name,c.description,c.isLeaveType,"
                + " de.jobDescription "
//                + " d.workHours,d.overtimeHours "
                + orderBy;
        String sqlAcnt = "select d.workDate,a.accountId,a.accountName,m.loginId,"
            + " c.name,c.description,c.isLeaveType,"
            + " de.jobDescription,"
            + " sum(d.workHours),sum(d.overtimeHours)"
            + " from TsTimesheetDay as d,TsAccount as a, "
            + " TsTimesheetDetail as de,TsTimesheetMaster as m,TsCodeValue as c"
            + " where lower(m.loginId) in "+loginIds
            + " and a.rid=de.accountRid"
            + " and de.accountRid in "+acntRids
            + " and de.tsRid=m.rid"
            + " and d.tsDetailRid=de.rid"
            + " and c.rid=de.codeValueRid"
            + " and d.workDate>=?"
            + " and d.workDate<=?"
            + " and m.status='"+DtoTimeSheet.STATUS_APPROVED+"'"
            + " group by d.workDate,a.accountId,a.accountName,m.loginId,"
            + " c.name,c.description,c.isLeaveType,"
            + " de.jobDescription "
//            + " d.workHours,d.overtimeHours "
            + orderBy;
        final String hql;
        if(acntRids != null) {
        	hql = sqlAcnt;
        } else {
        	hql = sql;
        }
        List list = this.getHibernateTemplate().executeFind(new HibernateCallback(){
             public Object doInHibernate(Session session) throws SQLException,
                     HibernateException {
                 Query q = session.createQuery(hql)
                           .setDate(0, dto.getBegin())
                           .setDate(1, dto.getEnd());
                 return q.list();
             }
         });
         return list;
     }
    public List queryReportForPmDept(final DtoQueryCondition dto,
			final String deptIds, String loginId) {
    	String sql = "select d.workDate,a.accountId,a.accountName,m.loginId,"
				+ " c.name,c.description,c.isLeaveType,"
				+ " de.jobDescription,"
				+ " sum(d.workHours),sum(d.overtimeHours)"
				+ " from TsTimesheetDay as d,TsAccount as a, "
				+ " TsTimesheetDetail as de,TsTimesheetMaster as m,TsCodeValue as c,"
				+ " TsAccount as u "
				+ " where u.accountId in "+ deptIds
				+ " and a.rid=de.accountRid "
				+ " and a.orgCode=u.accountId "
				+ " and (lower(u.manager)=lower('"+loginId+"') or lower(a.manager)=lower('"+loginId+"')) "
				+ " and de.tsRid=m.rid"
				+ " and d.tsDetailRid=de.rid"
				+ " and c.rid=de.codeValueRid"
				+ " and d.workDate>=?"
				+ " and d.workDate<=?"
				+ " and m.status='"+ DtoTimeSheet.STATUS_APPROVED+ "'"
				+ " group by d.workDate,a.accountId,a.accountName,m.loginId,"
				+ " c.name,c.description,c.isLeaveType,"
				+ " de.jobDescription"
//				+ " d.workHours,d.overtimeHours "
				+ " order by a.accountId";
    	String sqlPMO = "select d.workDate,a.accountId,a.accountName,m.loginId,"
			+ " c.name,c.description,c.isLeaveType,"
			+ " de.jobDescription,"
			+ " sum(d.workHours),sum(d.overtimeHours)"
			+ " from TsTimesheetDay as d,TsAccount as a, "
			+ " TsTimesheetDetail as de,TsTimesheetMaster as m,TsCodeValue as c,"
			+ " TsAccount as u "
			+ " where u.accountId in "+ deptIds
			+ " and a.rid=de.accountRid "
			+ " and a.orgCode=u.accountId "
			+ " and de.tsRid=m.rid"
			+ " and d.tsDetailRid=de.rid"
			+ " and c.rid=de.codeValueRid"
			+ " and d.workDate>=?"
			+ " and d.workDate<=?"
			+ " and m.status='"+ DtoTimeSheet.STATUS_APPROVED+ "'"
			+ " group by d.workDate,a.accountId,a.accountName,m.loginId,"
			+ " c.name,c.description,c.isLeaveType,"
			+ " de.jobDescription"
//			+ " d.workHours,d.overtimeHours "
			+ " order by a.accountId";
    	final String hql;
    	if(dto.isPmo()) {
    		hql = sqlPMO;
    	} else {
    		hql = sql;
    	}
		List list = this.getHibernateTemplate().executeFind(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws SQLException, HibernateException {
						Query q = session.createQuery(hql)
						                 .setDate(0, dto.getBegin())
						                 .setDate(1, dto.getEnd());
						return q.list();
					}
				});
		return list;
	}
    public List queryGatherReport(final DtoQueryCondition dto, String projectIds) {
        final String sql = "select distinct "
                           + " a.orgCode,a.accountId,a.accountName,a.estWorkHours"
                           + " ,c.isLeaveType,a.deptFlag"
                           + " from TsAccount as a,TsTimesheetDetail as de,TsTimesheetDay as d,TsCodeValue as c"
                           + " where de.accountRid=a.rid"
                           + " and d.tsDetailRid=de.rid"
                           + " and c.rid=de.codeValueRid"
                           + " and a.accountId in "+projectIds
                           + " and d.workDate>=?"
                           + " and d.workDate<=?"
                           + " and de.status='"+DtoTimeSheet.STATUS_APPROVED+"'"
                           + " order by a.orgCode";
        List list = this.getHibernateTemplate().executeFind(new HibernateCallback(){
            public Object doInHibernate(Session session) throws SQLException,
                    HibernateException {
                Query q = session.createQuery(sql)
                          .setDate(0, dto.getBegin())
                          .setDate(1, dto.getEnd());
                return q.list();
            }
        });
        return list;
    }
    public List queryGatherReportDept(final DtoQueryCondition dto,String deptIds, String loginId) {
    	String sql = "select distinct "
				+ " a.orgCode,a.accountId,a.accountName,a.estWorkHours"
				+ " ,c.isLeaveType,a.deptFlag"
				+ " from TsAccount as a,TsTimesheetDetail as de,TsTimesheetDay as d,TsCodeValue as c,"
				+ " TsAccount as u"
				+ " where u.accountId in "+deptIds
				+ " and de.accountRid=a.rid" 
				+ " and a.orgCode=u.accountId"
				+ " and (lower(u.manager)=lower('"+loginId+"') or lower(a.manager)=lower('"+loginId+"'))"
				+ " and d.tsDetailRid=de.rid"
				+ " and c.rid=de.codeValueRid" 
				+ " and d.workDate>=?" 
				+ " and d.workDate<=?"
				+ " and de.status='" + DtoTimeSheet.STATUS_APPROVED + "'"
				+ " order by a.orgCode";
    	String sqlPMO = "select distinct "
			+ " a.orgCode,a.accountId,a.accountName,a.estWorkHours"
			+ " ,c.isLeaveType,a.deptFlag"
			+ " from TsAccount as a,TsTimesheetDetail as de,TsTimesheetDay as d,TsCodeValue as c,"
			+ " TsAccount as u"
			+ " where u.accountId in "+deptIds
			+ " and de.accountRid=a.rid" 
			+ " and a.orgCode=u.accountId"
			+ " and d.tsDetailRid=de.rid"
			+ " and c.rid=de.codeValueRid" 
			+ " and d.workDate>=?" 
			+ " and d.workDate<=?"
			+ " and de.status='" + DtoTimeSheet.STATUS_APPROVED + "'"
			+ " order by a.orgCode";
    	final String hql;
    	if(dto.isPmo()) {
    		hql = sqlPMO;
    	} else {
    		hql = sql;
    	}
		List list = this.getHibernateTemplate().executeFind(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws SQLException, HibernateException {
						Query q = session.createQuery(hql)
						                 .setDate(0, dto.getBegin())
						                 .setDate(1, dto.getEnd());
						return q.list();
					}
				});
		return list;
    }

    public List getSum(final String projectId, final Date begin, final Date end, String isLeaveType) {
        final String sql = "select sum(d.workHours), sum(d.overtimeHours)"
                           + " from TsTimesheetDay as d, TsTimesheetDetail as de, TsAccount as a"
                           + " where d.tsDetailRid=de.rid"
                           + " and de.accountRid=a.rid"
                           + " and a.accountId=?"
                           + " and d.workDate>=?"
                           + " and d.workDate<=?"
                           + " and de.isLeaveType='"+isLeaveType+"'"
                           + " and de.status='"+DtoTimeSheet.STATUS_APPROVED+"'";
        List list = this.getHibernateTemplate().executeFind(new HibernateCallback(){
            public Object doInHibernate(Session session) throws SQLException,
                    HibernateException {
                Query q = session.createQuery(sql)
                          .setString(0, projectId)
                          .setDate(1, begin)
                          .setDate(2, end);
                return q.list();
            }
        });
        return list;
    }
    
    /**
     * 查询实际工时
     * @param loginId
     * @param begin
     * @param end
     * @return List
     */
    public List getActualHoursInfo(final String loginId,final Date begin,final Date end) {
        final String sql = " select a.accountId,a.accountName,c.name,dy.workDate,"
                          +" d.jobDescription,dy.workHours,d.rid"
                          +" from TsTimesheetMaster as m ,TsTimesheetDetail as d,"
                          +" TsTimesheetDay as dy,TsAccount as a,TsCodeValue as c,TsHumanBase as h "
                          +" where m.rid=d.tsRid and d.rid=dy.tsDetailRid and d.accountRid=a.rid "
                          +" and d.codeValueRid=c.rid and lower(m.loginId)=lower(h.employeeId)"
                          +" and lower(m.loginId)=lower(?) and dy.workDate>=? and dy.workDate<=?"
                          +" and m.status='"+DtoTimeSheet.STATUS_APPROVED+"' and (d.isLeaveType='0' or d.isLeaveType is null)"
                          +" order by a.accountId,d.rid";
        List list = this.getHibernateTemplate().executeFind(new HibernateCallback(){
            public Object doInHibernate(Session session) throws SQLException,
                    HibernateException {
                Query q = session.createQuery(sql)
                          .setString(0, loginId)
                          .setDate(1, begin)
                          .setDate(2, end);
                return q.list();
            }
        });
        return list;
    }
    
    /**
     * 查询請假工时
     * @param loginId
     * @param begin
     * @param end
     * @return List
     */
    public List getLeaveHoursInfo(final String loginId,final Date begin,final Date end) {
        final String sql = " select a.accountId,a.accountName,c.name,dy.workDate,"
                          +"d.jobDescription,dy.workHours,d.rid"
                          +" from TsTimesheetMaster as m ,TsTimesheetDetail as d,"
                          +" TsTimesheetDay as dy,TsAccount as a,TsCodeValue as c,TsHumanBase as h "
                          +" where m.rid=d.tsRid and d.rid=dy.tsDetailRid and d.accountRid=a.rid "
                          +" and d.codeValueRid=c.rid and lower(m.loginId)=lower(h.employeeId)"
                          +" and lower(m.loginId)=lower(?) and dy.workDate>=? and dy.workDate<=?"
                          +" and m.status='"+DtoTimeSheet.STATUS_APPROVED+"' and d.isLeaveType='1'"
                          +" order by a.accountId,d.rid";
        List list = this.getHibernateTemplate().executeFind(new HibernateCallback(){
            public Object doInHibernate(Session session) throws SQLException,
                    HibernateException {
                Query q = session.createQuery(sql)
                          .setString(0, loginId)
                          .setDate(1, begin)
                          .setDate(2, end);
                return q.list();
            }
        });
        return list;
    }
    
    /**
     * 查询加班工时
     * @param loginId
     * @param begin
     * @param end
     * @return List
     */
    public List getOvertimeHoursInfo(final String loginId,final Date begin,final Date end) {
            final String sql = " select a.accountId,a.accountName,c.name,dy.workDate,"
                +"d.jobDescription,dy.overtimeHours,d.rid"
                +" from TsTimesheetMaster as m ,TsTimesheetDetail as d,"
                +" TsTimesheetDay as dy,TsAccount as a,TsCodeValue as c,TsHumanBase as h "
                +" where m.rid=d.tsRid and d.rid=dy.tsDetailRid and d.accountRid=a.rid "
                +" and d.codeValueRid=c.rid and lower(m.loginId)=lower(h.employeeId) and dy.overtimeHours>0"
                +" and lower(m.loginId)=lower(?) and dy.workDate>=? and dy.workDate<=?"
                +" and m.status='"+DtoTimeSheet.STATUS_APPROVED+"' and (d.isLeaveType='0' or d.isLeaveType is null)"
                +" order by a.accountId,d.rid";
            List list = this.getHibernateTemplate().executeFind(new HibernateCallback(){
            public Object doInHibernate(Session session) throws SQLException,
               HibernateException {
               Query q = session.createQuery(sql)
                .setString(0, loginId)
                .setDate(1, begin)
                .setDate(2, end);
              return q.list();
         }
      });
   return list;
    }
    
}
