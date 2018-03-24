/*
 * Created on 2008-6-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.report.attendance.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import org.springframework.orm.hibernate.HibernateCallback;
import org.springframework.orm.hibernate.support.HibernateDaoSupport;

import c2s.essp.timesheet.approval.DtoTsApproval;
import c2s.essp.timesheet.report.DtoAttendanceQuery;

public class AttendanceDaoImp extends HibernateDaoSupport 
          implements IAttendanceDao{

        public List query(DtoAttendanceQuery dtoQry) {
              String status = DtoTsApproval.STATUS_APPROVED;
              final Date begin = dtoQry.getBegin();
              final Date end = dtoQry.getEnd();
              final String site = dtoQry.getSite();
              final String sql = " select m.loginId,a.accountId,a.deptFlag,sum(y.workHours)"
                    +" from TsTimesheetMaster as m,TsTimesheetDetail as d,"
                    +" TsTimesheetDay as y,TsHumanBase as h,TsAccount as a "
                    +" where y.workDate>=? and y.workDate<=? and lower(h.site)=lower(?)"
                    +" and m.status='"+status+"' and m.rid=d.tsRid and d.rid=y.tsDetailRid"
                    +" and d.accountRid = a.rid and lower(h.employeeId)=lower(m.loginId)"
                    +" group by  m.loginId,a.accountId,a.deptFlag"
              		+" order by  m.loginId,a.accountId";
 
              List list = (List)this.getHibernateTemplate().execute(new
                    HibernateCallback() {
                 public Object doInHibernate(Session session) throws SQLException,
                        HibernateException {
                    List list = session.createQuery(sql)
                                  .setDate(0,begin)
                                  .setDate(1,end)
                                  .setString(2,site)
                                  .list();
                    return list;
                   }
            });
            return list;
        }    
}
