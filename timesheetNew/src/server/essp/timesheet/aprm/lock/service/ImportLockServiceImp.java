package server.essp.timesheet.aprm.lock.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import c2s.dto.DtoUtil;
import c2s.essp.common.calendar.WorkCalendar;

import server.essp.timesheet.aprm.lock.dao.IImportLockDao;
import server.essp.timesheet.aprm.lock.form.AfImportLock;

import net.sf.hibernate.type.Type;

import db.essp.timesheet.TsImportLock;

public class ImportLockServiceImp implements IImportLockService {
	
	private IImportLockDao importLockDao;

	/**
	 * �г��涨ʱ�������е���������Ϣ
	 * @param begin
	 * @param end
	 * @return List<TsImportLock>
	 */
	public List listImportLock(Date begin, Date end) {
		return importLockDao.listImportLock(begin, end);
	}
	
	/**
	 * �г���������������е���������Ϣ
	 * @return List<TsImportLock>
	 */
	public List listImportLock() {
		Calendar c = Calendar.getInstance();
		Calendar bC = WorkCalendar.getNextDay(c, -31);
		Calendar eC = WorkCalendar.getNextDay(c, 31);
		return listImportLock(bC.getTime(), eC.getTime());
	}

	/**
	 * ����Rid��ȡTsImportLock
	 * @param rid
	 * @return TsImportLock
	 */
	public TsImportLock getImportLock(Long rid) {
		return importLockDao.getImportLock(rid);
	}
	
	/**
	 * ����Ridɾ��TsImportLock
	 * @param rid
	 */
	public void deleteImportLock(Long rid) {
		importLockDao.deleteImportLock(rid);
		
	}

	/**
	 * ����TsImportLock
	 * @param importLock
	 */
	public void saveImportLock(AfImportLock form) {
		TsImportLock importLock = new TsImportLock();
		DtoUtil.copyBeanToBean(importLock, form);
		String status = form.getStatus();
		if(status == null || "".equals(status)) {
			importLock.setStatus("false");
		}
		importLockDao.saveImportLock(importLock);
	}

	public void setImportLockDao(IImportLockDao importLockDao) {
		this.importLockDao = importLockDao;
	}
}
