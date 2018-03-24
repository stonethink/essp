package server.essp.timesheet.aprm.lock.dao;

import java.util.Date;
import java.util.List;

import db.essp.timesheet.TsImportLock;

public interface IImportLockDao {
	
	/**
	 * 列出规定时间内所有导入锁定信息
	 * @param begin
	 * @param end
	 * @return List<TsImportLock>
	 */
	public List listImportLock(Date begin, Date end);
	
	/**
	 * 根据Rid获取TsImportLock
	 * @param rid
	 * @return TsImportLock
	 */
	public TsImportLock getImportLock(Long rid);
	
	/**
	 * 保存TsImportLock
	 * @param importLock
	 */
	public void saveImportLock(TsImportLock importLock);
	
	/**
	 * 判断给定时间段是否被锁定
	 * @param begin
	 * @param end
	 * @return boolean
	 */
	public boolean isPeriodLocked(Date begin, Date end);
	
	/**
	 * 根据Rid删除TsImportLock
	 * @param rid
	 */
	public void deleteImportLock(Long rid);

}
