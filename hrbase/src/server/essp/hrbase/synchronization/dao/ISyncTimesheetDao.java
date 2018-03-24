package server.essp.hrbase.synchronization.dao;

import server.essp.hrbase.dept.viewbean.VbSyncUnitBase;
import server.essp.hrbase.humanbase.viewbean.VbHumanBase;
import server.essp.hrbase.humanbase.viewbean.VbHumanBaseLog;

public interface ISyncTimesheetDao {
	
	/**
	 * 向timesheet系统中新增人员基本信息
	 * @param hrbHuman
	 */
	public void add(VbHumanBaseLog hrbHumanLog);
	
	/**
	 * 更新timesheet系统中人员基本信息
	 * @param hrbHumanLog HrbHumanBaseLog
	 */
	public void update(VbHumanBaseLog hrbHumanLog);
	
	/**
	 * 删除timesheet系统中的人员信息记录
	 * @param employeeId
	 */
	public void delete(String employeeId);
	
	/**
	 * 新增timesheet系统的部门信息
	 * @param unitBase
	 */
	public void addUnit(VbSyncUnitBase unitBase);
	
	/**
	 * 更新timesheet系统的部门信息
	 * @param unitBase
	 */
	public void updateUnit(VbSyncUnitBase unitBase);
	
	/**
	 * 删除timesheet系统的部门信息（实际修改状态）
	 * @param unitCode
	 */
	public void deleteUnit(String unitCode);
	
	/**
	 * 新增人员到部门中
	 * @param hrbHumanBase
	 * @param accountRid
	 */
	public void insertLabor(VbHumanBase hrbHumanBase, Long accountRid);
	
	/**
	 * 将人加入Labor Resources
	 * @param hrbHumanLog
	 */
	public void addHuman2Labor(VbHumanBaseLog hrbHumanLog);
	
	/**
	 * 更新Labor Resources人的部门
	 * @param hrbHumanLog VbHumanBaseLog
	 */
	public boolean updateHumanInLabor(VbHumanBaseLog hrbHumanLog);
	
	/**
	 * 删除Labor Resources中部门下已经被删除了的人的资料
	 * @param employeeId
	 * @param unitCode
	 */
	public void deleteHumanInLabor(String employeeId, String unitCode);

}
