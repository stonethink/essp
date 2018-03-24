package server.essp.hrbase.synchronization.dao;

import db.essp.hrbase.HrbHumanBaseLog;

public interface ISyncPrimaveraDao {
	
	/**
	 * 新增资源
	 * @param hrbHumanLog
	 */
	public String addResource(HrbHumanBaseLog hbLog);
	
	/**
	 * 修改资源
	 * @param hrbHumanLog
	 */
	public String updateResource(HrbHumanBaseLog hbLog);
	
	/**
	 * 删除资源
	 * @param hrbHumanLog
	 */
	public String deleteResource(HrbHumanBaseLog hbLog);

}
