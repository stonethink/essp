package server.essp.timesheet.aprm.lock.service;

import java.util.Date;
import java.util.List;

import server.essp.timesheet.aprm.lock.form.AfImportLock;

import db.essp.timesheet.TsImportLock;

public interface IImportLockService {
	/**
	 * �г��涨ʱ�������е���������Ϣ
	 * @param begin
	 * @param end
	 * @return List<TsImportLock>
	 */
	public List listImportLock(Date begin, Date end);
	
	/**
	 * �г���������������е���������Ϣ
	 * @return List<TsImportLock>
	 */
	public List listImportLock();
	
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
	public void saveImportLock(AfImportLock form);
	
	/**
	 * ����Ridɾ��TsImportLock
	 * @param rid
	 */
	public void deleteImportLock(Long rid);

}
