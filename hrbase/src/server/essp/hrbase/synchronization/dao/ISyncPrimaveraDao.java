package server.essp.hrbase.synchronization.dao;

import db.essp.hrbase.HrbHumanBaseLog;

public interface ISyncPrimaveraDao {
	
	/**
	 * ������Դ
	 * @param hrbHumanLog
	 */
	public String addResource(HrbHumanBaseLog hbLog);
	
	/**
	 * �޸���Դ
	 * @param hrbHumanLog
	 */
	public String updateResource(HrbHumanBaseLog hbLog);
	
	/**
	 * ɾ����Դ
	 * @param hrbHumanLog
	 */
	public String deleteResource(HrbHumanBaseLog hbLog);

}
