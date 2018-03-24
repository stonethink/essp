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
	 * �г�״̬ΪPending���쳣��¼
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
	 * ����rid��ȡ�쳣��Ϣ��¼��viewbean
	 */
	public VbSyncException loadExceptionByRid(Long rid) {
		HrbExceptionTemp exceptionTemp = syncExceptionDao.loadExceptionBy(rid);
		VbSyncException dtoSyncException = new VbSyncException();
		DtoUtil.copyBeanToBean(dtoSyncException, exceptionTemp);
		return dtoSyncException;
	}
	
	/**
	 * �����쳣��¼
	 */
	public void updateException(HrbExceptionTemp exception) {
		syncExceptionDao.updateException(exception);
	}

	/**
	 * �����쳣��¼
	 */
	public void addException(HrbExceptionTemp exceptionTemp) {
		syncExceptionDao.addException(exceptionTemp);
	}
	
	/**
	 * ͨ��RID��ȡ�쳣��¼
	 */
	public HrbExceptionTemp getExceptionByRid(Long rid) {
		return syncExceptionDao.loadExceptionBy(rid);
	}

}
