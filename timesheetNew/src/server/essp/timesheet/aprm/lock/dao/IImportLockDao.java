package server.essp.timesheet.aprm.lock.dao;

import java.util.Date;
import java.util.List;

import db.essp.timesheet.TsImportLock;

public interface IImportLockDao {
	
	/**
	 * �г��涨ʱ�������е���������Ϣ
	 * @param begin
	 * @param end
	 * @return List<TsImportLock>
	 */
	public List listImportLock(Date begin, Date end);
	
	/**
	 * ����Rid��ȡTsImportLock
	 * @param rid
	 * @return TsImportLock
	 */
	public TsImportLock getImportLock(Long rid);
	
	/**
	 * ����TsImportLock
	 * @param importLock
	 */
	public void saveImportLock(TsImportLock importLock);
	
	/**
	 * �жϸ���ʱ����Ƿ�����
	 * @param begin
	 * @param end
	 * @return boolean
	 */
	public boolean isPeriodLocked(Date begin, Date end);
	
	/**
	 * ����Ridɾ��TsImportLock
	 * @param rid
	 */
	public void deleteImportLock(Long rid);

}
