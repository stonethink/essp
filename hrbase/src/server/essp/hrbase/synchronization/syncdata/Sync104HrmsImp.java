package server.essp.hrbase.synchronization.syncdata;

import server.essp.hrbase.dept.dao.IDeptDao;
import server.essp.hrbase.dept.viewbean.VbSyncUnitBase;
import server.essp.hrbase.synchronization.dao.ISync104HrmsDao;
import server.essp.hrbase.synchronization.syncexception.viewbean.VbSyncException;
import server.framework.common.BusinessException;
import db.essp.hrbase.*;

public class Sync104HrmsImp implements ISyncService {
	
	private ISync104HrmsDao sync104HrmsDao;
	private IDeptDao deptDao;

	public void setDeptDao(IDeptDao deptDao) {
		this.deptDao = deptDao;
	}

	public void setSync104HrmsDao(ISync104HrmsDao sync104HrmsDao) {
		this.sync104HrmsDao = sync104HrmsDao;
	}

	public void addUnit(HrbUnitBase unitBase) throws BusinessException {
		sync104HrmsDao.add(unitBase);
	}

	public void carryForward(HrbExceptionTemp exception) {
		if(VbSyncException.RES_TYPE_UNIT.equals(exception.getResType())) {
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

	public void deleteUnit(String unitCode) {
		HrbUnitBase unitBase = deptDao.loadByDeptId(unitCode);
		if(unitBase == null) {
			return;
		}
		sync104HrmsDao.delete(unitBase);
	}

	public String getFunctionName() {
		return this.getClass().getName();
	}

	public void syncHuman(HrbHumanBaseLog humanBaseLog) {

	}

	public void updateUnit(HrbUnitBase unitBase) throws BusinessException {
		sync104HrmsDao.update(unitBase);
	}

}
