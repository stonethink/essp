package server.essp.hrbase.synchronization.syncdata;

import server.essp.hrbase.dept.dao.IDeptDao;
import server.essp.hrbase.dept.viewbean.VbSyncUnitBase;
import server.essp.hrbase.synchronization.dao.INextKeyDao;
import server.essp.hrbase.synchronization.dao.ISyncProjectpreDao;
import server.essp.hrbase.synchronization.syncexception.viewbean.VbSyncException;
import server.framework.common.BusinessException;
import c2s.dto.DtoUtil;
import c2s.essp.common.account.IDtoAccount;
import db.essp.hrbase.*;

public class SyncProjectpreImp implements ISyncService {
	
	private ISyncProjectpreDao syncProjectpreDao;
	private INextKeyDao nextKeyDao;
	private IDeptDao deptDao;
	private static final String PP_ACNT_NEXTKEY_NAME = "PP_ACNT"; 
	private static final String PP_ACNT_PERSON_NEXTKEY_NAME = "PP_ACNT_PERSON"; 

	/**
	 * 获取实现类名称
	 */
	public String getFunctionName() {
		return this.getClass().getName();
	}
	
	/**
	 * 向projectpre系统申请对应表的新rid
	 * @param nextKeyName
	 * @return
	 */
	private Long getNextKey(String nextKeyName) {
		return nextKeyDao.getNextKey(nextKeyName);
	}
	/**
	 * 同步新增部门信息到projectpre系统
	 */
	public void addUnit(HrbUnitBase unitBase) throws BusinessException {
		VbSyncUnitBase dtoUnit = new VbSyncUnitBase();
		DtoUtil.copyBeanToBean(dtoUnit, unitBase);
		dtoUnit.setRid(getNextKey(PP_ACNT_NEXTKEY_NAME));
		syncProjectpreDao.add(dtoUnit);
		//DM
		syncProjectpreDao.addPerson(getNextKey(PP_ACNT_PERSON_NEXTKEY_NAME), 
				            dtoUnit.getRid(), 
				            IDtoAccount.USER_TYPE_DEPT_MANAGER, 
				            dtoUnit.getDmId(), dtoUnit.getDmName());
		//BD
		syncProjectpreDao.addPerson(getNextKey(PP_ACNT_PERSON_NEXTKEY_NAME), 
							dtoUnit.getRid(), 
							IDtoAccount.USER_TYPE_BD_MANAGER, 
							dtoUnit.getBdId(), dtoUnit.getBdName());
		//TS
		syncProjectpreDao.addPerson(getNextKey(PP_ACNT_PERSON_NEXTKEY_NAME), 
							dtoUnit.getRid(), 
							IDtoAccount.USER_TYPE_TC_SIGNER, 
							dtoUnit.getTsId(), dtoUnit.getTsName());
	}
	/**
	 * 同步更新部门信息到projectpre系统
	 */
	public void updateUnit(HrbUnitBase unitBase) throws BusinessException {
		VbSyncUnitBase dtoUnit = new VbSyncUnitBase();
		DtoUtil.copyBeanToBean(dtoUnit, unitBase);
		syncProjectpreDao.update(dtoUnit);
		Long acntRid = syncProjectpreDao.getPPAcntRid(dtoUnit.getUnitCode());
		//DM
		syncProjectpreDao.updatePerson(acntRid,
				IDtoAccount.USER_TYPE_DEPT_MANAGER,
				                      dtoUnit.getDmId(), dtoUnit.getDmName());
		//BD
		syncProjectpreDao.updatePerson(acntRid,
				IDtoAccount.USER_TYPE_BD_MANAGER, 
										dtoUnit.getBdId(), dtoUnit.getBdName());
		//TS
		syncProjectpreDao.updatePerson(acntRid,
				IDtoAccount.USER_TYPE_TC_SIGNER, 
										dtoUnit.getTsId(), dtoUnit.getTsName());
	}
	
	public void setSyncProjectpreDao(ISyncProjectpreDao syncProjectpreDao) {
		this.syncProjectpreDao = syncProjectpreDao;
	}

	public void setNextKeyDao(INextKeyDao nextKeyDao) {
		this.nextKeyDao = nextKeyDao;
	}
	
	/**
	 * 处理结转失败的记录，重新结转
	 */
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

	public void setDeptDao(IDeptDao deptDao) {
		this.deptDao = deptDao;
	}
	/**
	 * 同步人员基本资料（projectpre无需操作）
	 */
	public void syncHuman(HrbHumanBaseLog humanBaseLog) {
//		System.out.println("ProjectPre does not need to synchronise Human Base");
//		return;
	}

	/**
	 * 删除projectpre部门信息（实际为修改部门is_enable状态）
	 */
	public void deleteUnit(String unitCode) {
		syncProjectpreDao.delete(unitCode);
	}

}
