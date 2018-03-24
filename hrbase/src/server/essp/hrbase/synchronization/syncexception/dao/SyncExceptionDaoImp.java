package server.essp.hrbase.synchronization.syncexception.dao;

import java.util.List;

import org.springframework.orm.hibernate.support.HibernateDaoSupport;

import server.essp.hrbase.synchronization.syncexception.viewbean.VbSyncException;
import db.essp.hrbase.HrbExceptionTemp;

public class SyncExceptionDaoImp extends HibernateDaoSupport implements
		ISyncExceptionDao {
	
	/**
	 * 新增结转异常记录
	 */
	public void addException(HrbExceptionTemp exceptionTemp) {
		this.getHibernateTemplate().save(exceptionTemp);
	}
	
	/**
	 * 列出状态为Pending的结转异常信息记录
	 */
	public List listException() {
		String sql = "from HrbExceptionTemp where status='"+VbSyncException.STATUS_PENDING
		            +"' order by rct";
		return this.getHibernateTemplate().find(sql);
	}
	
	/**
	 * 根据RID获取结转异常记录
	 */
	public HrbExceptionTemp loadExceptionBy(Long rid) {
		return (HrbExceptionTemp) this.getHibernateTemplate().load(HrbExceptionTemp.class, rid);
	}

	/**
	 * 更新结转异常记录
	 */
	public void updateException(HrbExceptionTemp exception) {
		this.getHibernateTemplate().update(exception);
		
	}

}
