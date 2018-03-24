package server.essp.hrbase.synchronization.syncdata;

import server.essp.hrbase.dept.dao.IDeptDao;
import server.essp.hrbase.dept.viewbean.VbSyncUnitBase;
import server.essp.hrbase.humanbase.dao.IHumanBaseDao;
import server.essp.hrbase.humanbase.viewbean.VbHumanBaseLog;
import server.essp.hrbase.synchronization.dao.INextKeyDao;
import server.essp.hrbase.synchronization.dao.ISyncTimesheetDao;
import server.essp.hrbase.synchronization.syncexception.viewbean.VbSyncException;
import server.framework.common.BusinessException;
import c2s.dto.DtoUtil;
import db.essp.hrbase.*;

public class SyncTimesheetImp implements ISyncService {
	
	private ISyncTimesheetDao syncTimesheetDao;
	private IHumanBaseDao humanBaseDao;
	private INextKeyDao nextKeyDao;
	private IDeptDao deptDao;
	private static final String TS_NEXTKEY_NAME = "TS_ACCOUNT";
	private static final String TSHRB_NEXTKEY_NAME = "TS_HUMAN_BASE";
	private static final String TS_LABOR_NEXTKEY_NAME = "TS_LABOR_RESOURCE";


	public void setSyncTimesheetDao(ISyncTimesheetDao syncTimesheetDao) {
		this.syncTimesheetDao = syncTimesheetDao;
	}
	/**
	 * ��ȡʵ��������
	 */
	public String getFunctionName() {
		return this.getClass().getName();
	}
	
	/**
	 * ��timesheetϵͳ�������Ӧ��������rid
	 * @param nextKeyName
	 * @return
	 */
	private Long getNextKey(String nextKeyName){
		return nextKeyDao.getNextKey(nextKeyName);
	}
	/**
	 * ͬ������������Ϣ��timesheetϵͳ
	 */
	public void addUnit(HrbUnitBase unitBase) throws BusinessException {
		VbSyncUnitBase dtoUnit = new VbSyncUnitBase();
	    DtoUtil.copyBeanToBean(dtoUnit, unitBase);
//		List<HrbHumanBase> list = humanBaseDao.queryHumanBaseUnderUnit(dtoUnit.getUnitCode());
		dtoUnit.setRid(getNextKey(TS_NEXTKEY_NAME));
//		if(list != null && list.size() > 0) {
//			Long accountRid = dtoUnit.getRid();
//			VbHumanBase dtoHuman = null;
//			for(HrbHumanBase hrbHumanBase : list) {
//				dtoHuman = new VbHumanBase();
//				DtoUtil.copyBeanToBean(dtoHuman, hrbHumanBase);
//				dtoHuman.setRid(getNextKey(TS_LABOR_NEXTKEY_NAME));
//				syncTimesheetDao.insertLabor(dtoHuman, accountRid);
//			}
//		}
		syncTimesheetDao.addUnit(dtoUnit);
	}
	/**
	 * ͬ�����²�����Ϣ��timesheetϵͳ
	 */
	public void updateUnit(HrbUnitBase unitBase) throws BusinessException {
		VbSyncUnitBase dtoUnit = new VbSyncUnitBase();
		DtoUtil.copyBeanToBean(dtoUnit, unitBase);
		syncTimesheetDao.updateUnit(dtoUnit);
	}

	public void setHumanBaseDao(IHumanBaseDao humanBaseDao) {
		this.humanBaseDao = humanBaseDao;
	}

	public void setNextKeyDao(INextKeyDao nextKeyDao) {
		this.nextKeyDao = nextKeyDao;
	}
	
	/**
	 * �����ת�쳣�����½�ת
	 */
	public void carryForward(HrbExceptionTemp exception) {
		if(VbSyncException.RES_TYPE_HUMAN.equals(exception.getResType())) {
			HrbHumanBaseLog humanBaseLog = humanBaseDao.loadHumanBaseLog(exception.getResDataRid());
			if(humanBaseLog == null) {
				return;
			}
			this.syncHuman(humanBaseLog);
		} else if(VbSyncException.RES_TYPE_UNIT.equals(exception.getResType())) {
			HrbUnitBase unitBase = deptDao.getUnitBaseByRid(exception.getResDataRid());
			if(unitBase == null) {
				return;
			}
			if(VbSyncUnitBase.OPERATION_INSERT.equals(exception.getOperation())) {
				this.addUnit(unitBase);
			} else if(VbSyncUnitBase.OPERATION_UPDATE.equals(exception.getOperation())) {
				this.updateUnit(unitBase);
			} else if(VbSyncUnitBase.OPERATION_DELETE.equals(exception.getOperation())) {
				this.deleteUnit(unitBase.getUnitCode());
			}
		}
	}

	public void setDeptDao(IDeptDao deptDao) {
		this.deptDao = deptDao;
	}
	
	/**
	 * ͬ����Ա�������ϵ�timesheetϵͳ����Ա�������ϵ���
	 */
	public void syncHuman(HrbHumanBaseLog humanBaseLog) {
		VbHumanBaseLog dtoHumanBase = null;
		dtoHumanBase = new VbHumanBaseLog();
	    DtoUtil.copyBeanToBean(dtoHumanBase, humanBaseLog);
		if (HrbHumanBaseLog.OPERATION_INSERT.equals(dtoHumanBase.getOperation())) {
			dtoHumanBase.setRid(getNextKey(TSHRB_NEXTKEY_NAME));
			syncTimesheetDao.add(dtoHumanBase);
			dtoHumanBase.setRid(getNextKey(TS_LABOR_NEXTKEY_NAME));
			syncTimesheetDao.addHuman2Labor(dtoHumanBase);
		} else if (HrbHumanBaseLog.OPERATION_UPDATE.equals(dtoHumanBase.getOperation())) {
			if(!syncTimesheetDao.updateHumanInLabor(dtoHumanBase)) {
				dtoHumanBase.setRid(getNextKey(TS_LABOR_NEXTKEY_NAME));
				syncTimesheetDao.addHuman2Labor(dtoHumanBase);
			}
			syncTimesheetDao.update(dtoHumanBase);
		} else if (HrbHumanBaseLog.OPERATION_DELETE.equals(dtoHumanBase.getOperation())) {
			syncTimesheetDao.delete(dtoHumanBase.getEmployeeId());
			syncTimesheetDao.deleteHumanInLabor(dtoHumanBase.getEmployeeId(), dtoHumanBase.getUnitCode());
		}
		
	}
	/**
	 * ɾ��timesheet�Ĳ�����Ϣ��ʵ���޸�״̬��
	 */
	public void deleteUnit(String unitCode) {
		syncTimesheetDao.deleteUnit(unitCode);
	}

}
