package server.essp.timesheet.approval.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import net.sf.hibernate.*;

import org.springframework.orm.hibernate.HibernateCallback;
import org.springframework.orm.hibernate.support.HibernateDaoSupport;

import server.essp.common.ldap.LDAPUtil;

public class ApprovalDaoImp extends HibernateDaoSupport implements IApprovalDao {
    /**
     * �г���ǩ�����µ�������Ա
     * @param managerId String
     * @return List
     */
    public List listPersonByManager(String managerId) {
        LDAPUtil ldap = new LDAPUtil();
        return ldap.searchUserBasesByManager(LDAPUtil.DOMAIN_ALL, managerId);
    }
    /**
     * ������ѡ��ר������ʼ���ڣ��������ڣ��Լ���ʱ��״̬��ѯ��ʱ��
     * @param accountRids
     * @param begin
     * @param end
     * @param status
     * @return
     */
	public List listPmApproval(String accountRids, final Date begin, 
								final Date end, String status) {
		String sqlNoStatus = "select m.loginId,m.beginDate,m.endDate,m.status,de.accountRid,m.rid,"
			+ " sum(d.workHours),sum(d.overtimeHours),de.isLeaveType,a.accountId,de.status"
			+ " from TsTimesheetMaster m, TsTimesheetDetail de , TsTimesheetDay d, TsAccount a"
			+ " where m.beginDate>=? "
			+ " and m.endDate<=? "
			+ " and de.accountRid in "+accountRids
			+ " and a.rid=de.accountRid "
			+ " and de.tsRid=m.rid"
			+ " and d.tsDetailRid=de.rid"
			+ " group by m.loginId,m.beginDate,m.endDate,m.status,de.accountRid," 
			+ " m.rid,de.isLeaveType,a.accountId,de.status"
			+ " order by m.beginDate desc";
		String sql = "select m.loginId,m.beginDate,m.endDate,m.status,de.accountRid,m.rid,"
				+ " sum(d.workHours),sum(d.overtimeHours),de.isLeaveType,a.accountId,de.status"
				+ " from TsTimesheetMaster m, TsTimesheetDetail de , TsTimesheetDay d, TsAccount a"
				+ " where m.beginDate>=? "
				+ " and m.endDate<=? "
				+ " and de.accountRid in "+accountRids
				+ " and a.rid=de.accountRid "
				+ " and de.status in "+status
				+ " and de.tsRid=m.rid"
				+ " and d.tsDetailRid=de.rid"
				+ " group by m.loginId,m.beginDate,m.endDate,m.status,de.accountRid," 
				+ " m.rid,de.isLeaveType,a.accountId,de.status"
				+ " order by m.beginDate desc";
		final String hql;
		if(status != null) {
			hql = sql;
		} else {
			hql = sqlNoStatus;
		}
		List list = (List)this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query q = session.createQuery(hql)
				          .setDate(0, begin)
				          .setDate(1, end);
				return q.list();
			}
		});
		return list;
	}


}
