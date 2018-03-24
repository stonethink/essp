package server.essp.hrbase.synchronization.syncexception.dao;

import java.util.List;

import org.springframework.orm.hibernate.support.HibernateDaoSupport;

import server.essp.hrbase.synchronization.syncexception.viewbean.VbSyncException;
import db.essp.hrbase.HrbExceptionTemp;

public class SyncExceptionDaoImp extends HibernateDaoSupport implements
		ISyncExceptionDao {
	
	/**
	 * ������ת�쳣��¼
	 */
	public void addException(HrbExceptionTemp exceptionTemp) {
		this.getHibernateTemplate().save(exceptionTemp);
	}
	
	/**
	 * �г�״̬ΪPending�Ľ�ת�쳣��Ϣ��¼
	 */
	public List listException() {
		String sql = "from HrbExceptionTemp where status='"+VbSyncException.STATUS_PENDING
		            +"' order by rct";
		return this.getHibernateTemplate().find(sql);
	}
	
	/**
	 * ����RID��ȡ��ת�쳣��¼
	 */
	public HrbExceptionTemp loadExceptionBy(Long rid) {
		return (HrbExceptionTemp) this.getHibernateTemplate().load(HrbExceptionTemp.class, rid);
	}

	/**
	 * ���½�ת�쳣��¼
	 */
	public void updateException(HrbExceptionTemp exception) {
		this.getHibernateTemplate().update(exception);
		
	}

}
