package server.essp.hrbase.synchronization.service;

import java.util.List;

import db.essp.hrbase.HrbHumanBaseLog;
import db.essp.hrbase.HrbUnitBase;

public interface ISyncMainService {
	
	/**
	 * 同步人员基本资料到其他系统
	 * @param dataList
	 */
	public boolean synchronise(List<HrbHumanBaseLog> dataList);
	
	/**
	 * 查询需要同步的人员基本资料
	 * @return
	 */
	public List<HrbHumanBaseLog> searchDataForSync();
	
	/**
	 * 同步新增部门信息的操作
	 * @param unitBase
	 */
	public boolean addUnit(HrbUnitBase unitBase);
	
	/**
	 * 同步更新部门信息的操作
	 * @param unitBase
	 */
	public boolean updateUnit(HrbUnitBase unitBase);
	
	/**
	 * 同步删除部门信息（实际为修改部门状态）
	 * @param unitBase
	 * @return
	 */
	public boolean deleteUnit(HrbUnitBase unitBase);

}
