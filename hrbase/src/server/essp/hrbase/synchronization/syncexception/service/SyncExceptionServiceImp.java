package server.essp.hrbase.synchronization.syncexception.service;

import java.util.ArrayList;
import java.util.List;

import server.essp.hrbase.synchronization.syncexception.dao.ISyncExceptionDao;
import server.essp.hrbase.synchronization.syncexception.viewbean.VbSyncException;
import c2s.dto.DtoUtil;
import db.essp.hrbase.*;

public class SyncExceptionServiceImp implements ISyncExceptionService {
	
	private ISyncExceptionDao syncExceptionDao;

	public void setSyncExceptionDao(ISyncExceptionDao syncExceptionDao) {
		this.syncExceptionDao = syncExceptionDao;
	}
	/**
	 * 列出状态为Pending的异常记录
	 */
	public List listException() {
		List<HrbExceptionTemp> list = syncExceptionDao.listException();
		List resultList = new ArrayList();
		VbSyncException dtoSyncException = null;
		if(list != null && list.size() > 0) {
			for(HrbExceptionTemp exceptionTemp : list) {
				dtoSyncException = new VbSyncException();
				DtoUtil.copyBeanToBean(dtoSyncException, exceptionTemp);
				resultList.add(dtoSyncException);
			}
		}
		return resultList;
	}
	
	/**
	 * 根据rid获取异常信息记录的viewbean
	 */
	public VbSyncException loadExceptionByRid(Long rid) {
		HrbExceptionTemp exceptionTemp = syncExceptionDao.loadExceptionBy(rid);
		VbSyncException dtoSyncException = new VbSyncException();
		DtoUtil.copyBeanToBean(dtoSyncException, exceptionTemp);
		return dtoSyncException;
	}
	
	/**
	 * 更新异常记录
	 */
	public void updateException(HrbExceptionTemp exception) {
		syncExceptionDao.updateException(exception);
	}

	/**
	 * 新增异常记录
	 */
	public void addException(HrbExceptionTemp exceptionTemp) {
		syncExceptionDao.addException(exceptionTemp);
	}
	
	/**
	 * 通过RID获取异常记录
	 */
	public HrbExceptionTemp getExceptionByRid(Long rid) {
		return syncExceptionDao.loadExceptionBy(rid);
	}

}
