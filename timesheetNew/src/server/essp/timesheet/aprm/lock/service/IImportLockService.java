package server.essp.timesheet.aprm.lock.service;

import java.util.Date;
import java.util.List;

import server.essp.timesheet.aprm.lock.form.AfImportLock;

import db.essp.timesheet.TsImportLock;

public interface IImportLockService {
	/**
	 * 列出规定时间内所有导入锁定信息
	 * @param begin
	 * @param end
	 * @return List<TsImportLock>
	 */
	public List listImportLock(Date begin, Date end);
	
	/**
	 * 列出最近两个月内所有导入锁定信息
	 * @return List<TsImportLock>
	 */
	public List listImportLock();
	
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
	public void saveImportLock(AfImportLock form);
	
	/**
	 * 根据Rid删除TsImportLock
	 * @param rid
	 */
	public void deleteImportLock(Long rid);

}
