package server.essp.timesheet.aprm.lock.dao;

import java.util.Date;
import java.util.List;

import net.sf.hibernate.type.DateType;
import net.sf.hibernate.type.Type;

import org.springframework.orm.hibernate.support.HibernateDaoSupport;

import db.essp.timesheet.TsImportLock;

public class ImportLockImp extends HibernateDaoSupport implements IImportLockDao {
	private final static DateType dateType = new DateType();
	
	/**
	 * �г��涨ʱ�������е���������Ϣ
	 * @param begin
	 * @param end
	 * @return List<TsImportLock>
	 */
	public List listImportLock(Date begin, Date end) {
		String hql = "from TsImportLock where end_Date >= ? and begin_Date <= ?";
		return this.getHibernateTemplate().find(hql,
							new Object[] {begin, end},
							new Type[] {dateType, dateType});
	}

	/**
	 * ����Rid��ȡTsImportLock
	 * @param rid
	 * @return TsImportLock
	 */
	public TsImportLock getImportLock(Long rid) {
		return (TsImportLock) this.getHibernateTemplate()
										.load(TsImportLock.class, rid);
	}

	/**
	 * ����TsImportLock
	 * @param importLock
	 */
	public void saveImportLock(TsImportLock importLock) {
		this.getHibernateTemplate().saveOrUpdateCopy(importLock);
	}
	
	/**
	 * �жϸ���ʱ����Ƿ�����
	 * @param begin
	 * @param end
	 * @return boolean
	 */
	public boolean isPeriodLocked(Date begin, Date end) {
		String hql = "from TsImportLock where end_Date >= ? and begin_Date <= ? and status='true'";
		List list = this.getHibernateTemplate().find(hql,
							new Object[] {begin, end},
							new Type[] {dateType, dateType});
		if(list != null && list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * ����Ridɾ��TsImportLock
	 * @param rid
	 */
	public void deleteImportLock(Long rid) {
		this.getHibernateTemplate().delete("from TsImportLock where rid = " + rid);
	}

}
