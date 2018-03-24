package server.essp.hrbase.synchronization.dao;

import db.essp.hrbase.HrbUnitBase;

public interface ISync104HrmsDao {
	
	/**
	 * 新增部门
	 * @param unitBase
	 */
	public void add(HrbUnitBase unitBase);
	
	/**
	 * 更新部门
	 * @param unitBase
	 */
	public void update(HrbUnitBase unitBase);
	
	/**
	 * 删除部门
	 * @param unitBase
	 */
	public void delete(HrbUnitBase unitBase);

}
