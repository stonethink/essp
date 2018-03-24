package server.essp.timesheet.report.humanreport.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import net.sf.hibernate.*;

import org.springframework.orm.hibernate.HibernateCallback;
import org.springframework.orm.hibernate.support.HibernateDaoSupport;

import c2s.essp.timesheet.weeklyreport.DtoTimeSheet;

public class HumanReportDaoImp extends HibernateDaoSupport implements
		IHumanReportDao {

	public List queryHumanReportByDate(final Date begin, final Date end, String site) {
		final String sql = "select a.accountId, a.accountName,a.deptFlag,u.accountId,u.accountName,h.employeeId,h.chineseName, "
				+ " h.isDirect,h.attribute,sum(d.workHours),sum(d.overtimeHours) "
				+ " from TsTimesheetMaster m, TsTimesheetDetail de, " 
				+ " TsTimesheetDay d, TsAccount a, TsAccount u, TsHumanBase h "
				+ " where m.beginDate>=? "
				+ " and m.endDate<=? "
				+ " and m.status='"+DtoTimeSheet.STATUS_APPROVED+"' "
				+ " and m.rid=de.tsRid "
				+ " and de.rid=d.tsDetailRid "
				+ " and de.accountRid=a.rid "
				+ " and a.orgCode=u.accountId "
				+ " and lower(m.loginId)=lower(h.employeeId) "
				+ " and lower(h.site)=lower('"+site+"') "
				+ " group by a.accountId,a.accountName,a.deptFlag,u.accountId,u.accountName,h.employeeId,h.chineseName, "
				+ " h.isDirect,h.attribute order by u.accountId,a.accountId";
		List list = (List)this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query q = session.createQuery(sql)
				          .setDate(0, begin)
				          .setDate(1, end);
				 return q.list();
			}
		});
		return list;
	}


	public List getTotalHoursList(final Date begin, final Date end, String site) {
		final String sql = "select upper(m.loginId), sum(d.workHours), sum(d.overtimeHours) "
				+ " from TsTimesheetMaster m, TsTimesheetDetail de, "
				+ " TsTimesheetDay d, TsAccount a, TsAccount u, TsHumanBase h "
				+ " where m.beginDate>=? "
				+ " and m.endDate<=? "
				+ " and m.status='"+DtoTimeSheet.STATUS_APPROVED+"' " 
				+ " and m.rid=de.tsRid "
				+ " and de.rid=d.tsDetailRid "
				+ " and de.accountRid=a.rid "
				+ " and a.orgCode=u.accountId "
				+ " and upper(m.loginId)=upper(h.employeeId) "
				+ " and lower(h.site)=lower('"+site+"') "
				+ " group by upper(m.loginId)"
				+ " order by upper(m.loginId)";
		List list = (List)this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query q = session.createQuery(sql)
				          .setDate(0, begin)
				          .setDate(1, end);
				 return q.list();
			}
		});
		return list;
	}

	public List getProjectInfo(String accountId) {
		final String sql = "select t.accountId,t.accountName,u.accountId,u.accountName, " 
			    + " h.employeeId,h.chineseName,t.billable "
				+ " from TsAccount t, TsAccount u, TsHumanBase h "
				+ " where t.accountId='"+accountId+"' "
				+ " and t.orgCode=u.accountId "
				+ " and lower(t.manager)=lower(h.employeeId) "
				+ " group by t.accountId,t.accountName,u.accountId,u.accountName, " 
				+ " h.employeeId,h.chineseName,t.billable"
				+ " order by t.accountId, h.employeeId";
		List list = (List)this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query q = session.createQuery(sql);
				 return q.list();
			}
		});
		return list;
	}

	public List queryNameAndProject(final Date begin, final Date end, String site) {
		final String sql = "select h.employeeId,a.accountId "
			+ " from TsTimesheetMaster m, TsTimesheetDetail de, " 
			+ " TsTimesheetDay d, TsAccount a, TsAccount u, TsHumanBase h "
			+ " where m.beginDate>=? "
			+ " and m.endDate<=? "
			+ " and m.status='"+DtoTimeSheet.STATUS_APPROVED+"' "
			+ " and m.rid=de.tsRid "
			+ " and de.rid=d.tsDetailRid "
			+ " and de.accountRid=a.rid "
			+ " and a.orgCode=u.accountId "
			+ " and lower(m.loginId)=lower(h.employeeId) "
			+ " and lower(h.site)=lower('"+site+"') "
			+ " group by a.accountId,a.accountName,a.deptFlag,u.accountId,u.accountName,h.employeeId,h.chineseName, "
			+ " h.isDirect,h.attribute order by u.accountId,a.accountId";
	    List list = (List)this.getHibernateTemplate().execute(new HibernateCallback(){
		public Object doInHibernate(Session session)
				throws HibernateException, SQLException {
			Query q = session.createQuery(sql)
			          .setDate(0, begin)
			          .setDate(1, end);
			 return q.list();
		}
	    });
	    return list;
	}
	public List queryBeginEnd(String accountId, String loginId, final Date begin, final Date end) {
		final String sql = "select m.loginId,m.beginDate,m.endDate from TsTimesheetMaster m, " 
				+ " TsTimesheetDetail de,TsAccount a"
				+ " where a.accountId ='"+accountId+"'"
				+ " and m.beginDate>=? "
				+ " and m.endDate<=? "
				+ " and a.rid = de.accountRid"
				+ " and m.rid=de.tsRid"
				+ " and lower(m.loginId)=lower('"+loginId+"')" 
				+ " order by m.beginDate";
		List list = (List)this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query q = session.createQuery(sql)
								.setDate(0, begin)
								.setDate(1, end);
				 return q.list();
			}
		    });
		    return list;
	}
	
	public List queryOvertimeHours(final Date begin, final Date end, String site, String queryWay){
		String sql = "select o.projectCode, upper(o.employeeId), sum(o.actualHours) from AttOvertime o "
			+ " where o.overtimeDate>=? "
			+ " and o.overtimeDate<=? "
			+ " and lower(o.companyId)=lower('"+site+"') "
			+ " and o.actualHours>0 "
			+ " group by o.projectCode,upper(o.employeeId) "
			+ " order by o.projectCode,upper(o.employeeId)";
	    if("D".equals(queryWay)){
			sql = "select o.unitCode, upper(o.employeeId), sum(o.actualHours) from AttOvertime o "
				+ " where o.overtimeDate>=? "
				+ " and o.overtimeDate<=? "
				+ " and lower(o.companyId)=lower('"+site+"') "
				+ " and o.actualHours>0 "
				+ " group by o.unitCode,upper(o.employeeId) "
				+ " order by o.unitCode,upper(o.employeeId)";
		}
	    final String hql = sql;
	    List list = (List)this.getHibernateTemplate().execute(new HibernateCallback(){
		public Object doInHibernate(Session session)
				throws HibernateException, SQLException {
			Query q = session.createQuery(hql)
			          .setDate(0, begin)
			          .setDate(1, end);
			 return q.list();
		}
	    });
	    return list;
	}
	public List queryLeaveHours(final Date begin, final Date end, String site) {
		final String sql = "select l.unitCode, upper(l.employeeId), sum(l.actualHours) from AttLeave l "
			+ " where l.leaveDate>=? "
			+ " and l.leaveDate<=? "
			+ " and lower(l.companyId)=lower('"+site+"') "
			+ " and l.actualHours>0 "
			+ " group by l.unitCode,upper(l.employeeId) "
			+ " order by l.unitCode,upper(l.employeeId)";
		List list = (List) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query q = session.createQuery(sql)
						                 .setDate(0, begin)
								         .setDate(1, end);
						return q.list();
					}
				});
		return list;
	}
	public List queryHumanTimes(final Date begin, final Date end, String site) {
			final String sql = "select a.accountId,a.accountName,h.employeeId, " 
				+ " h.chineseName,h.attribute,sum(d.workHours) "
				+ " from TsTimesheetMaster m, TsTimesheetDetail de, " 
				+ "TsTimesheetDay d,TsHumanBase h, TsAccount a "
				+ "where m.beginDate>=? "
				+ "and m.endDate<=? "
				+ "and m.status='"+DtoTimeSheet.STATUS_APPROVED+"' "
				+ "and m.rid=de.tsRid "
				+ "and de.rid=d.tsDetailRid "
				+ "and lower(m.loginId)=lower(h.employeeId) "
				+ "and h.unitCode=a.accountId "
				+ "and lower(h.site)=lower('"+site+"') "
				+ "group by a.accountId,a.accountName,h.employeeId,h.chineseName,h.attribute "
				+ "order by a.accountId";
			List list = (List) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query q = session.createQuery(sql)
						                 .setDate(0, begin)
								         .setDate(1, end);
						return q.list();
					}
				});
			return list;
	}

}
