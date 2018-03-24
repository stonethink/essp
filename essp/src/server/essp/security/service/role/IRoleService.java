package server.essp.security.service.role;

import java.util.List;
import c2s.essp.common.user.DtoRole;
import itf.user.IRoleUtil;
import c2s.dto.ITreeNode;

public interface IRoleService extends IRoleUtil{
	/**
	 * 列出所有的Role
	 * @return
	 */
	public List listAllRole();
	/**
	 * 列出所有状态可用的角色
	 * @return
	 */
	public List listEnabledRole();
	/**
	 * 列出所有状态不可用的角色
	 * @return
	 */
	public List listDisabledRole();
	/**
	 * 列出用户所有可用的角色
	 * @return
	 */
	public List listUserEnabledRole(String loginId);
	/**
	 * 列出用户所有的角色,包含可用和不可用
	 * @param loginId
	 * @return
	 */
	public List listUserAllRole(String loginId);
	/**
	 * 新增一个角色,角色ID不能重复
	 * @param role
	 */
	public void addRole(DtoRole role);
	/**
	 * 查找角色名对应的Role,不存在时返回空
	 * @param roleName
	 */
	public DtoRole getRole(String roleName);
	/**
	 * 更新一个角色信息,角色名不能重复
	 * @param role
	 */
	public void updateRole(DtoRole role,String roleId);
	/**
	 * 保存或更新人员对应的角色
	 */
	public void saveOrUpdateUserRole(String loginId,String[] roleIds,String domain); 

        /**
         * 根据角色名删除相应的Role
         * @param rolename String
         */
        public void deleteRole(String roldId);

        /**
        * 获取角色树
        * @return TreeNode
        */
        public ITreeNode getRoleTree();

        /**
         * 列出所有状态可用和可见的角色
         * @return List
         */
        public List listEnabled2VisibleRole();
        
        /**
         * 列出某些角色下的所有login id
         * @param roleId
         * @return
         */
        public List listLoginIdUnderRole(String[] roleIds);

}
