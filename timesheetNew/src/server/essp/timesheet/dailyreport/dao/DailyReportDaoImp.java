package server.essp.timesheet.dailyreport.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import net.sf.hibernate.*;
import net.sf.hibernate.type.*;

import org.springframework.orm.hibernate.HibernateCallback;
import org.springframework.orm.hibernate.support.HibernateDaoSupport;

import c2s.essp.timesheet.dailyreport.DtoDrActivity;

import com.wits.util.comDate;

import db.essp.timesheet.TsDailyReport;

public class DailyReportDaoImp extends HibernateDaoSupport implements
		IDailyReportDao {

	public List listDailyReport(Long activityId) {
		String sql = "from TsDailyReport t where t.taskId=? and t.reportDate=?";
		return this.getHibernateTemplate().find(sql, new Object[]{activityId, comDate.toDate(comDate.dateToString(new Date(), "yyyy-MM-dd"))});
	}

	public TsDailyReport loadDailtReportByRid(Long rid) {
		return (TsDailyReport) this.getHibernateTemplate().load(TsDailyReport.class, rid);
	}

	public void addDailyReport(TsDailyReport tsDailyReport) {
		this.getHibernateTemplate().save(tsDailyReport);
	}

	public void updateDailyReport(TsDailyReport tsDailyReport) {
		this.getHibernateTemplate().update(tsDailyReport);
	}

	public List listTodayDailyReportByLoginId(String loginId, Date day) {
		String sql = "from TsDailyReport t where lower(t.resourceId)=lower(?) and t.reportDate=? order by t.taskId";
		return this.getHibernateTemplate().find(sql, new Object[]{loginId, comDate.toDate(comDate.dateToString(day, "yyyy-MM-dd"))});
	}

	public void delete(TsDailyReport tsDailyReport) {
		this.getHibernateTemplate().delete(tsDailyReport);
	}

	public void delDailyReport(Date day, DtoDrActivity dto, String employeeId) {
		String sql = "";
		Object[] args = null;
		if(dto.isActivity()) {
			sql = "from TsDailyReport t where t.taskId=? and t.resourceId=? " 
				+" and t.reportDate=?";
			args = new Object[]{dto.getActivityId(), employeeId, day};
		} else {
			sql = "from TsDailyReport t where t.codeValueRid=? and t.resourceId=? " 
				+" and t.reportDate=?";
			args = new Object[]{dto.getCodeValueRid(), employeeId, day};
		}
		this.getHibernateTemplate().delete(sql, args, new Type[]{new LongType(), new StringType(), new DateType()});
	}
	public List listDailyReportByDate(final String loginId, final Date begin, final Date end) {
		final String sql = "select t.taskId,t.taskName,t.accountRid,t.projName,t.codeValueRid,t.codeValueName,t.reportDate,sum(t.workTime) " 
			       + " from TsDailyReport t where t.resourceId=? and t.reportDate>=? and t.reportDate<=? " 
			       + " group by t.taskId,t.taskName,t.accountRid,t.projName,t.codeValueRid,t.codeValueName,t.reportDate"
			       + " order by t.reportDate";
		List list = (List) this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query q = session.createQuery(sql)
				                 .setString(0, loginId)
				                 .setDate(1, begin)
				                 .setDate(2, end);
	            return q.list();
			}
			
		});
		return list;
	}
	public List listHoursByDate(final String loginId, final Date begin, final Date end) {
		final String sql = "select t.reportDate, sum(t.workTime) from TsDailyReport t " 
			             + " where t.resourceId=? and t.reportDate>=? and t.reportDate<=? "
			             + " group by t.reportDate order by t.reportDate";
		List list = (List) this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query q = session.createQuery(sql)
				                 .setString(0, loginId)
				                 .setDate(1, begin)
				                 .setDate(2, end);
	            return q.list();
			}
			
		});
		return list;
	}

	public TsDailyReport loadLastReport(Long stepRid) {
		String sql = "from TsDailyReport t where t.stepRid=? order by t.reportDate desc";
		List list = this.getHibernateTemplate().find(sql, stepRid);
		TsDailyReport tsDailyReport = null;
		if(list != null && list.size() > 0) {
			tsDailyReport = (TsDailyReport) list.get(0);
		}
		return tsDailyReport;
	}
}
