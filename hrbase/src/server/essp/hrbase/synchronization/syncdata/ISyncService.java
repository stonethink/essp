package server.essp.hrbase.synchronization.syncdata;

import server.framework.common.BusinessException;
import db.essp.hrbase.*;

public interface ISyncService {
	
	
	/**
	 * 同步人员基本资料
	 * @param humanBaseLog
	 */
	public void syncHuman(HrbHumanBaseLog humanBaseLog);
	
	/**
	 * 新增部门信息到其他系统
	 * @param unitBase
	 * @throws BusinessException
	 */
	public void addUnit(HrbUnitBase unitBase) throws BusinessException;
	
	/**
	 * 更新部门信息到其他系统
	 * @param unitBase
	 * @throws BusinessException
	 */
	public void updateUnit(HrbUnitBase unitBase) throws BusinessException;
	
	/**
	 * 从其他系统删除部门信息（实际为修改状态）
	 * @param unitCode
	 */
	public void deleteUnit(String unitCode);
	
	/**
	 * 获取具体实现的类名
	 * @return
	 */
	public String getFunctionName();
	
	/**
	 * 处理结转异常记录使其再次结转
	 * @param exception
	 */
	public void carryForward(HrbExceptionTemp exception);

}
