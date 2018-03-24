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
import net.sf.hibernate.type.AbstractType;
import net.sf.hibernate.type.DateType;
import net.sf.hibernate.type.StringType;
import org.springframework.orm.hibernate.HibernateCallback;
import org.springframework.orm.hibernate.support.HibernateDaoSupport;
import db.essp.timesheet.AttOvertime;

public class AttOvertimeDaoImp extends HibernateDaoSupport implements
		IAttOvertimeDao {

	public List loadByCondition(String empId, Date otDate) {
		String sql = "from AttOvertime where lower(employee_Id)=lower(?) and overtime_Date=?";
		List<AttOvertime> list = this.getHibernateTemplate().find(sql,
				new Object[] { empId, otDate },
				new AbstractType[] { new StringType(), new DateType() });
		return list;
	}

}
