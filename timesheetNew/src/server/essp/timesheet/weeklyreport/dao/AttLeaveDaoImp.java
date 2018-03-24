/*
 * Created on 2008-1-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.weeklyreport.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.type.DateType;
import net.sf.hibernate.type.StringType;
import net.sf.hibernate.type.Type;

import org.springframework.orm.hibernate.HibernateCallback;
import org.springframework.orm.hibernate.support.HibernateDaoSupport;

public class AttLeaveDaoImp extends HibernateDaoSupport implements IAttLeaveDao{

        public List loadByCondition(String empId, Date leaveDate) {
               String sql = "from AttLeave where lower(employee_Id)=lower(?) and leave_Date=?";
               List list =this.getHibernateTemplate()
               .find(sql, new Object[]{empId, leaveDate},
                new Type[]{new StringType(), new DateType()});
                return list;
        }
        
        public List loadByCondition(String empId, Date begin, Date end) {
            String sql = "from AttLeave where lower(employee_Id)=lower(?) and leave_Date>=? and leave_Date<=?";
            List list =this.getHibernateTemplate()
            .find(sql, new Object[]{empId, begin, end},
             new Type[]{new StringType(), new DateType(), new DateType()});
             return list;
        }
        
        /**
         * 在指定时间段内得到员工的总的请假工时
         * @param begin
         * @param end
         * @return List
         */
        public List sumHoursByDates(final Date begin,final Date end){
               final  String sql = "select a.employeeId,sum(a.actualHours) from AttLeave as a"
                               +" where a.leaveDate>=? and a.leaveDate<=?"
                               +" group by a.employeeId"
                               +" order by a.employeeId";
               List list = (List)this.getHibernateTemplate().execute(new
               HibernateCallback() {
               public Object doInHibernate(Session session) throws SQLException,
               HibernateException {
               Query query = session.createQuery(sql)
                      .setDate(0,begin)
                      .setDate(1,end);
               return query.list();
              }
           });
        return list;
        }
}
