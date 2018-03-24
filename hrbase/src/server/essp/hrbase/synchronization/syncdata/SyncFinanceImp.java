package server.essp.hrbase.synchronization.syncdata;

import itf.webservices.FinAccountWServiceImpl;

import java.util.HashMap;
import java.util.Map;

import com.wits.util.comDate;

import server.essp.hrbase.dept.dao.IDeptDao;
import server.essp.hrbase.dept.viewbean.VbSyncUnitBase;
import server.essp.hrbase.humanbase.dao.IHumanBaseDao;
import server.essp.hrbase.synchronization.syncexception.viewbean.VbSyncException;
import server.framework.common.BusinessException;
import db.essp.hrbase.*;

public class SyncFinanceImp implements ISyncService {
	
	private IDeptDao deptDao;
	private IHumanBaseDao humanBaseDao;

	public void setHumanBaseDao(IHumanBaseDao humanBaseDao) {
		this.humanBaseDao = humanBaseDao;
	}

	public void setDeptDao(IDeptDao deptDao) {
		this.deptDao = deptDao;
	}

	public void addUnit(HrbUnitBase unitBase) throws BusinessException {
		Map map = new HashMap();
        map.put("projId", unitBase.getUnitCode());
        map.put("achieveBelong", unitBase.getBelongBd());
        map.put("projName", unitBase.getUnitFullName());
        map.put("nickName", unitBase.getUnitName());
        map.put("leader", unitBase.getDmId());
        map.put("costDept", unitBase.getParentUnitCode());
        map.put("manager", unitBase.getBelongBd());
        String custShort = "";
        if("TP".equals(unitBase.getBelongSite())) {
        	custShort = "WITS";
        } else {
        	custShort = "WI" + unitBase.getBelongSite();
        }
        map.put("custShort", custShort);
        map.put("planFrom", comDate.dateToString(unitBase.getEffectiveBegin(), "yyyy-MM-dd"));
        map.put("planTo", comDate.dateToString(unitBase.getEffectiveEnd(), "yyyy-MM-dd"));
        map.put("projectManager", unitBase.getDmId());
        map.put("divisionManager", unitBase.getBdId());
        map.put("closeMark", "N");
        FinAccountWServiceImpl financeService = new FinAccountWServiceImpl();
        financeService.addAccount(map);

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
		} else if(VbSyncException.RES_TYPE_HUMAN.equals(exception.getResType())) {
			HrbHumanBaseLog humanBaseLog = humanBaseDao.loadHumanBaseLog(exception.getResDataRid());
			if(humanBaseLog == null) {
				return;
			}
			this.syncHuman(humanBaseLog);
		}
	}

	public void deleteUnit(String unitCode) {
//		System.out.println("No operation to Finance!");
//		return;
	}

	public String getFunctionName() {
		return this.getClass().getName();
	}

	public void syncHuman(HrbHumanBaseLog humanBaseLog) {
//		System.out.println("Finance does not need to synchronise Human Base");
//		return;
		Map map = new HashMap();
		map.put("empId", humanBaseLog.getEmployeeId());
		map.put("empName", humanBaseLog.getChineseName());
		map.put("deptId", humanBaseLog.getUnitCode());
		map.put("inJobDate", comDate.dateToString(humanBaseLog.getInDate(), "yyyy-MM-dd"));
		if(humanBaseLog.getOutDate() != null) {
			map.put("quitDate", comDate.dateToString(humanBaseLog.getOutDate(), "yyyy-MM-dd"));
		} else {
			map.put("quitDate", null);
		}
		FinAccountWServiceImpl financeService = new FinAccountWServiceImpl();
		financeService.saveOrUpdateEmp(map);
	}

	public void updateUnit(HrbUnitBase unitBase) throws BusinessException {
		Map map = new HashMap();
        map.put("projId", unitBase.getUnitCode());
        map.put("achieveBelong", unitBase.getBelongBd());
        map.put("projName", unitBase.getUnitFullName());
        map.put("nickName", unitBase.getUnitName());
        map.put("leader", unitBase.getDmId());
        map.put("costDept", unitBase.getParentUnitCode());
        map.put("manager", unitBase.getBelongBd());
        map.put("projectManager", unitBase.getDmId());
        map.put("divisionManager", unitBase.getBdId());
        String custShort = "";
        if("TP".equals(unitBase.getBelongSite())) {
        	custShort = "WITS";
        } else {
        	custShort = "WI" + unitBase.getBelongSite();
        }
        map.put("custShort", custShort);
        map.put("planFrom", comDate.dateToString(unitBase.getEffectiveBegin(), "yyyy-MM-dd"));
        map.put("planTo", comDate.dateToString(unitBase.getEffectiveEnd(), "yyyy-MM-dd"));
        FinAccountWServiceImpl financeService = new FinAccountWServiceImpl();
        financeService.updateAccount(map);
	}

}
