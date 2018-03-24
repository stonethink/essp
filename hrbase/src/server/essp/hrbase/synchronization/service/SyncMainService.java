package server.essp.hrbase.synchronization.service;

import java.util.List;

import server.essp.hrbase.dept.viewbean.VbSyncUnitBase;
import server.essp.hrbase.humanbase.dao.IHumanBaseDao;
import server.essp.hrbase.synchronization.syncdata.*;
import server.essp.hrbase.synchronization.syncexception.service.ISyncExceptionService;
import server.essp.hrbase.synchronization.syncexception.viewbean.VbSyncException;
import server.framework.common.BusinessException;
import db.essp.hrbase.*;

public class SyncMainService implements ISyncMainService{
	
	private List syncList;
	private ISyncService syncService;
	private IHumanBaseDao humanBaseDao;
	private ISyncExceptionService syncExceptionService;

	public void setSyncList(List syncList) {
		this.syncList = syncList;
	}
	/**
	 * ͬ����Ա�������ϵ�����ϵͳ
	 */
	public boolean synchronise(List<HrbHumanBaseLog> dataList) {
		boolean isError = false;
		int size = syncList.size();
		for(int i = 0;i<size;i++) { 
			syncService = (ISyncService)syncList.get(i);
			for(HrbHumanBaseLog humanBaseLog : dataList) {
				try{
				syncService.syncHuman(humanBaseLog); 
				}catch(Exception e) {
					syncExceptionService.addException(
							getException(humanBaseLog,
									syncService.getFunctionName(),
									getErrorMessage(e)));
					isError = true;
				}
			}
		}
		return isError;
	}
	/**
	 * ��ȡ������Ϣ
	 * @param e
	 * @return
	 */
	public static String getErrorMessage(Exception e) {
		if(e.getMessage() != null && !"".equals(e.getMessage())){
			return e.getMessage();
		}
		String ex = e.toString() + "\n";
		StackTraceElement[] trace = e.getStackTrace();
        for (int i=0; i < trace.length; i++) {
        	ex += "\tat " + trace[i] + "\n";
        }
        return ex;
	}
	/**
	 * ���ݲ��������쳣��Ϣ��¼
	 * @param humanBaseLog
	 * @param functionName
	 * @param errorMessage
	 * @return
	 */
	private HrbExceptionTemp getException(HrbHumanBaseLog humanBaseLog,
			                 String functionName, String errorMessage) {
		HrbExceptionTemp exceptionTemp = new HrbExceptionTemp();
		exceptionTemp.setEffectiveDate(humanBaseLog.getEffectiveDate());
		exceptionTemp.setErrorMessage(errorMessage);
		exceptionTemp.setOperation(humanBaseLog.getOperation());
		if(SyncHrBaseImp.class.getName().equals(functionName)) {
			exceptionTemp.setModel(VbSyncException.MODEL_HR);
		} else if(SyncTimesheetImp.class.getName().equals(functionName)){
			exceptionTemp.setModel(VbSyncException.MODEL_TS);
		} else if(SyncFinanceImp.class.getName().equals(functionName)) {
			exceptionTemp.setModel(VbSyncException.MODEL_FI);
		} else if(SyncPrimaveraImp.class.getName().equals(functionName)) {
			exceptionTemp.setModel(VbSyncException.MODEL_P6);
		}
		exceptionTemp.setResDataRid(humanBaseLog.getRid());
		exceptionTemp.setStatus(VbSyncException.STATUS_PENDING);
		exceptionTemp.setResType(VbSyncException.RES_TYPE_HUMAN);
		return exceptionTemp;
	}
	/**
	 * ���ݲ��������쳣��Ϣ��¼
	 * @param unitBase
	 * @param functionName
	 * @param errorMessage
	 * @return
	 */
	private HrbExceptionTemp getException(HrbUnitBase unitBase, 
			String functionName, String errorMessage) {
		HrbExceptionTemp exceptionTemp = new HrbExceptionTemp();
		exceptionTemp.setErrorMessage(errorMessage);
		exceptionTemp.setOperation(unitBase.getOperation());
		if(SyncProjectpreImp.class.getName().equals(functionName)) {
			exceptionTemp.setModel(VbSyncException.MODEL_PP);
		} else if(SyncTimesheetImp.class.getName().equals(functionName)){
			exceptionTemp.setModel(VbSyncException.MODEL_TS);
		} else if(SyncFinanceImp.class.getName().equals(functionName)) {
			exceptionTemp.setModel(VbSyncException.MODEL_FI);
		} else if(Sync104HrmsImp.class.getName().equals(functionName)) {
			exceptionTemp.setModel(VbSyncException.MODEL_104HRMS);
		} else if(SyncPrimaveraImp.class.getName().equals(functionName)) {
			exceptionTemp.setModel(VbSyncException.MODEL_P6);
		}
		exceptionTemp.setResDataRid(unitBase.getRid());
		exceptionTemp.setStatus(VbSyncException.STATUS_PENDING);
		exceptionTemp.setResType(VbSyncException.RES_TYPE_UNIT);
		return exceptionTemp;
	}
	/**
	 * ��ѯ��Ҫͬ������Ա��������
	 */
	public List<HrbHumanBaseLog> searchDataForSync() {
		return humanBaseDao.queryHumanBaseLogForSync();
	}
	
	/**
	 * ͬ������������Ϣ�Ĳ���
	 */
	public boolean addUnit(HrbUnitBase unitBase) {
		boolean isError = false;
		int size = syncList.size();
		for(int i = 0;i<size;i++) { 
			syncService = (ISyncService)syncList.get(i);
			try{
				syncService.addUnit(unitBase);
			}catch(Exception e) {
				unitBase.setOperation(VbSyncUnitBase.OPERATION_INSERT);
				String errorMessage = "";
				if(e instanceof BusinessException){
					errorMessage = ((BusinessException)e).getErrorCode();
				} else {
					errorMessage = getErrorMessage(e);
				}
				syncExceptionService.addException(getException(unitBase, 
						syncService.getFunctionName(), errorMessage));
				isError = true;
			}
		}
		return isError;
	}
	/**
	 * ͬ�����²�����Ϣ�Ĳ���
	 */
	public boolean updateUnit(HrbUnitBase unitBase) {
		boolean isError = false;
		int size = syncList.size();
		for(int i = 0;i<size;i++) { 
			syncService = (ISyncService)syncList.get(i);
			try{
				syncService.updateUnit(unitBase);
			}catch(Exception e) {
				unitBase.setOperation(VbSyncUnitBase.OPERATION_UPDATE);
				String errorMessage = "";
				if(e instanceof BusinessException){
					errorMessage = ((BusinessException)e).getErrorCode();
				} else {
					errorMessage = getErrorMessage(e);
				}
				syncExceptionService.addException(getException(unitBase, 
						syncService.getFunctionName(), errorMessage));
				isError = true;
			}
		}
		return isError;
	}
	public void setSyncExceptionService(ISyncExceptionService syncExceptionService) {
		this.syncExceptionService = syncExceptionService;
	}
	public void setHumanBaseDao(IHumanBaseDao humanBaseDao) {
		this.humanBaseDao = humanBaseDao;
	}
	public boolean deleteUnit(HrbUnitBase unitBase) {
		boolean isError = false;
		int size = syncList.size();
		for(int i = 0;i<size;i++) { 
			syncService = (ISyncService)syncList.get(i);
			try{
				syncService.deleteUnit(unitBase.getUnitCode());
			}catch(Exception e) {
				unitBase.setOperation(VbSyncUnitBase.OPERATION_DELETE);
				syncExceptionService.addException(getException(unitBase, 
						syncService.getFunctionName(), getErrorMessage(e)));
				isError = true;
			}
		}
		return isError;
	}
}
