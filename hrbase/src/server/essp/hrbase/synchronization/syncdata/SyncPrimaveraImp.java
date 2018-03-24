package server.essp.hrbase.synchronization.syncdata;

import db.essp.hrbase.HrbExceptionTemp;
import db.essp.hrbase.HrbHumanBaseLog;
import db.essp.hrbase.HrbUnitBase;
import server.essp.common.primavera.PrimaveraApiBase;
import server.essp.hrbase.humanbase.dao.IHumanBaseDao;
import server.essp.hrbase.synchronization.dao.ISyncPrimaveraDao;
import server.essp.hrbase.synchronization.syncexception.viewbean.VbSyncException;
import server.framework.common.BusinessException;

public class SyncPrimaveraImp extends PrimaveraApiBase implements ISyncService {
	private ISyncPrimaveraDao syncPrimaveraDao;
	private IHumanBaseDao humanBaseDao;

	public void syncHuman(HrbHumanBaseLog humanBaseLog) {
		String op = humanBaseLog.getOperation();
		if(HrbHumanBaseLog.OPERATION_INSERT.equals(op)) {
			syncPrimaveraDao.addResource(humanBaseLog);
		} else if(HrbHumanBaseLog.OPERATION_UPDATE.equals(op)) {
			syncPrimaveraDao.updateResource(humanBaseLog);
		} else if(HrbHumanBaseLog.OPERATION_DELETE.equals(op)) {
			syncPrimaveraDao.deleteResource(humanBaseLog);
		}
	}

	public void addUnit(HrbUnitBase unitBase) throws BusinessException {

	}

	public void updateUnit(HrbUnitBase unitBase) throws BusinessException {

	}

	public void deleteUnit(String unitCode) {

	}

	public String getFunctionName() {
		return this.getClass().getName();
	}

	public void carryForward(HrbExceptionTemp exception) {
		if(VbSyncException.RES_TYPE_HUMAN.equals(exception.getResType())) {
			HrbHumanBaseLog humanBaseLog = humanBaseDao.loadHumanBaseLog(exception.getResDataRid());
			if(humanBaseLog == null) {
				return;
			}
			this.syncHuman(humanBaseLog);
		}
	}

	public void setSyncPrimaveraDao(ISyncPrimaveraDao syncPrimaveraDao) {
		this.syncPrimaveraDao = syncPrimaveraDao;
	}
	
	public void setHumanBaseDao(IHumanBaseDao humanBaseDao) {
		this.humanBaseDao = humanBaseDao;
	}

}
