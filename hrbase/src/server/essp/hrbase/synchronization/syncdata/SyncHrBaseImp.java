package server.essp.hrbase.synchronization.syncdata;

import server.essp.hrbase.humanbase.dao.IHumanBaseDao;
import server.essp.hrbase.synchronization.syncexception.viewbean.VbSyncException;
import server.framework.common.BusinessException;
import c2s.dto.DtoUtil;
import c2s.dto.IDto;
import db.essp.hrbase.*;

public class SyncHrBaseImp implements ISyncService {
	
	private IHumanBaseDao humanBaseDao;

	public void setHumanBaseDao(IHumanBaseDao humanBaseDao) {
		this.humanBaseDao = humanBaseDao;
	}

	public void addUnit(HrbUnitBase unitBase) throws BusinessException {
		System.out.println("need not to add unit to HrBase");
		return;
	}
	/**
	 * 获取实现类的名称
	 */
	public String getFunctionName() {
		return this.getClass().getName();
	}
	
	/**
	 * 同步更新部门信息（hrbase系统不需要操作）
	 */
	public void updateUnit(HrbUnitBase unitBase)
			throws BusinessException {
		System.out.println("need not to update unit in HrBase");
		return;

	}
	/**
	 * 处理结转异常重新结转资料
	 */
	public void carryForward(HrbExceptionTemp exception) {
		if(VbSyncException.RES_TYPE_HUMAN.equals(exception.getResType())) {
			HrbHumanBaseLog humanBaseLog = humanBaseDao.loadHumanBaseLog(exception.getResDataRid());
			if(humanBaseLog == null) {
				return;
			}
			this.syncHuman(humanBaseLog);
		}
	}
	/**
	 * 同步人员资料基本信息到人员主挡
	 */
	public void syncHuman(final HrbHumanBaseLog hbLog) {
		HrbHumanBase humanBase = null;
//		HrbHumanBaseLog hbLog = humanBaseDao.loadHumanBaseLog(humanBaseLog.getRid());
		
		//reset attribute
		Object[] atts = humanBaseDao.getAttributeInfoByGroupRid(hbLog.getAttributeGroupRid());
		if(atts != null && atts.length >= 3) {
			hbLog.setAttribute((String)atts[2]);
		}
		
		if (HrbHumanBaseLog.OPERATION_INSERT.equals(hbLog.getOperation())) {
			humanBase = humanBaseDao.findHumanBase(hbLog.getEmployeeId());
			//根据人员工号查询人员基本资料正式档
			//如果不存在做新增操作，存在则做更新操作并将LOG中的操作类型改为更新操作
			if(humanBase == null) {
				humanBase = new HrbHumanBase();
				DtoUtil.copyBeanToBean(humanBase, hbLog);
				humanBase.setOperation(HrbHumanBaseLog.OPERATION_NONE);
				humanBaseDao.saveHumanBase(humanBase);
			} else {
				if(humanBase.getInDate() != null &&
						hbLog.getInDate() != null &&
						humanBase.getInDate().equals(hbLog.getInDate()) == false) {
					hbLog.setRemark("In date changed.");
				}
				DtoUtil.copyBeanToBean(humanBase, hbLog, new String[] {"rid"});
				humanBase.setOperation(HrbHumanBaseLog.OPERATION_NONE);
				humanBaseDao.updateHumanBase(humanBase);
				hbLog.setOperation(HrbHumanBaseLog.OPERATION_UPDATE);
			}
		} else if (HrbHumanBaseLog.OPERATION_UPDATE.equals(hbLog.getOperation())) {
			Long baseRid = hbLog.getBaseRid();
			if(baseRid != null) {
				humanBase = humanBaseDao.loadHumanBase(baseRid);
			} else {
				humanBase = humanBaseDao.findHumanBase(hbLog.getEmployeeId());
			}
			if(humanBase == null){
				hbLog.setStatus(HrbHumanBaseLog.STATUS_COMPLETE);
				humanBaseDao.updateHumanBaseLog(hbLog);
				return;
			}
			if(humanBase.getInDate() != null &&
					hbLog.getInDate() != null &&
					humanBase.getInDate().equals(hbLog.getInDate()) == false) {
				hbLog.setRemark("In date changed.");
			}
			DtoUtil.copyBeanToBean(humanBase, hbLog, new String[] {"rid"});
			humanBase.setOperation(HrbHumanBaseLog.OPERATION_NONE);
			humanBaseDao.updateHumanBase(humanBase);
		} else if (HrbHumanBaseLog.OPERATION_DELETE.equals(hbLog.getOperation())) {
			Long baseRid = hbLog.getBaseRid();
			if(baseRid != null) {
				humanBase = humanBaseDao.loadHumanBase(baseRid);
			} else {
				humanBase = humanBaseDao.findHumanBase(hbLog.getEmployeeId());
			}
			humanBase.setRst(IDto.OP_DELETE);
			humanBase.setOperation(HrbHumanBaseLog.OPERATION_NONE);
			humanBaseDao.updateHumanBase(humanBase);
		}
		hbLog.setBaseRid(humanBase.getRid());
		hbLog.setStatus(HrbHumanBaseLog.STATUS_COMPLETE);
		humanBaseDao.updateHumanBaseLog(hbLog);
		
	}

	public void deleteUnit(String unitCode) {
		System.out.println("need not to delete unit in HrBase");
		return;
	}

}
