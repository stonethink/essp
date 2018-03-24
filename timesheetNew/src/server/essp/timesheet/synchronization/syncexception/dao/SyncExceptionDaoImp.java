package server.essp.timesheet.synchronization.syncexception.dao;

import java.util.List;

import org.springframework.orm.hibernate.support.HibernateDaoSupport;

import db.essp.timesheet.TsException;

public class SyncExceptionDaoImp extends HibernateDaoSupport implements
		ISyncExceptionDao {

	public void insert(TsException te) {
		this.getHibernateTemplate().save(te);

	}

	public List<TsException> listException() {
		String sql = "from TsException where lower(status)=lower(?)";
		return this.getHibernateTemplate().find(sql, "Pending");
	}

	public TsException loadExceptionByRid(Long rid) {
		return (TsException) this.getHibernateTemplate().load(
				TsException.class, rid);
	}

	public void update(TsException te) {
		this.getHibernateTemplate().update(te);

	}

}
