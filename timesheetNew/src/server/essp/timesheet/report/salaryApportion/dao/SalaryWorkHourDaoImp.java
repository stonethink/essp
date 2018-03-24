/*
 * Created on 2008-1-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.report.salaryApportion.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import org.springframework.orm.hibernate.HibernateCallback;
import org.springframework.orm.hibernate.support.HibernateDaoSupport;

import c2s.essp.timesheet.weeklyreport.DtoTimeSheet;


public class SalaryWorkHourDaoImp extends HibernateDaoSupport 
implements ISalaryWorkHourDao{
    /**
     * 根据查询条件查询
     * @param site
     * @param beginDate
     * @param endDate
     * @return List
     */
    public List queryByCondition(final String site, final Date beginDate,
            final Date endDate, boolean needApprove) {
        StringBuffer sBuff = new StringBuffer("");
        sBuff.append("select upper(m.loginId), a.accountId,a.accountName,d.codeValueRid,d.isLeaveType,a.achieveBelong,");
        sBuff.append(" sum(day.workHours),sum(day.overtimeHours)");
        sBuff.append(" from TsAccount as a,TsHumanBase h,TsTimesheetMaster as m,");
        sBuff.append(" TsTimesheetDay as day,TsTimesheetDetail as d where a.rid=d.accountRid");
        sBuff.append(" and m.rid=d.tsRid and d.rid=day.tsDetailRid and upper(h.employeeId)=upper(m.loginId)"); 
        if(needApprove) {
            sBuff.append(" and m.status = '");
            sBuff.append(DtoTimeSheet.STATUS_APPROVED);
            sBuff.append("' ");
        } else {
            sBuff.append(" and m.status not in ('");
            sBuff.append(DtoTimeSheet.STATUS_ACTIVE);
            sBuff.append("', '");
            sBuff.append(DtoTimeSheet.STATUS_REJECTED);
            sBuff.append("') ");
        }
        sBuff.append(" and h.site=? and m.beginDate>=? and m.endDate<=?");
        sBuff.append(" group by m.loginId, a.accountId,a.accountName,d.codeValueRid,d.isLeaveType,a.achieveBelong");
        sBuff.append(" order by m.loginId,a.accountId,d.codeValueRid");
        final String sql = sBuff.toString();
        List list = (List)this.getHibernateTemplate().execute(new
           HibernateCallback() {
        public Object doInHibernate(Session session) throws SQLException,
               HibernateException {
           Query query = session.createQuery(sql)
                         .setString(0,site)
                         .setDate(1,beginDate)
                         .setDate(2,endDate);
           return query.list();
           }
       });
         return list;
    }
    
}
