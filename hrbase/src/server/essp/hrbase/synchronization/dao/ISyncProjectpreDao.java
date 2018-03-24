package server.essp.hrbase.synchronization.dao;

import server.essp.hrbase.dept.viewbean.VbSyncUnitBase;

public interface ISyncProjectpreDao {
	
	
	/**
	 * 向projectpre系统插入部门信息记录
	 * @param unitBase
	 */
	public void add(VbSyncUnitBase unitBase);
	
	/**
	 * 更新projectpre系统的部门信息
	 * @param unitBase
	 */
	public void update(VbSyncUnitBase unitBase);
	
	/**
	 * 删除projectpre系统的部门（修改is_enable状态为0）
	 * @param unitCode
	 */
	public void delete(String unitCode);
	
	/**
	 * 新增与部门关联的人员
	 * @param rid
	 * @param acntRid
	 * @param personType
	 * @param loginId
	 * @param name
	 */
	public void addPerson(Long rid, Long acntRid, 
			       String personType, String loginId, String name);
	
	/**
	 * 根据与部门相关的人员资料
	 * @param acntRid
	 * @param personType
	 * @param loginId
	 * @param name
	 */
	public void updatePerson(Long acntRid, String personType, 
			                 String loginId, String name);
	
	/**
	 * 根据专案代码获取该笔记录的RID
	 * @param acntId
	 * @return
	 */
	public Long getPPAcntRid(String acntId);

}
