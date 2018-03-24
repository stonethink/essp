package server.essp.timesheet.outwork.privilege.dao;

import java.util.List;

public interface IOutWorkerPrivilegeDbDao {

	
	/**
	 * 根据传进来的loginId批量删除
	 *
	 */
	public void delete(String loginId);
	
	/**
	 * 列出所有有权限的用户
	 * @return
	 */
	public List listUser();
	
    /**
     * 得到各個部門及當前員工能授權部門
     * @param loginId
     * @param accountId
     * @param isRoot
     * @return List
     */
	public List loadPrivilege(String loginId,String accountId,boolean isRoot);
    
    /**
     * 根據loginId得到對應的角色
     * @param loginId
     * @return List
     */
    public List getRoleList(String loginId);
	
}
