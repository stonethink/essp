package server.essp.security.service.role;

import java.util.List;
import c2s.essp.common.user.DtoRole;
import itf.user.IRoleUtil;
import c2s.dto.ITreeNode;

public interface IRoleService extends IRoleUtil{
	/**
	 * �г����е�Role
	 * @return
	 */
	public List listAllRole();
	/**
	 * �г�����״̬���õĽ�ɫ
	 * @return
	 */
	public List listEnabledRole();
	/**
	 * �г�����״̬�����õĽ�ɫ
	 * @return
	 */
	public List listDisabledRole();
	/**
	 * �г��û����п��õĽ�ɫ
	 * @return
	 */
	public List listUserEnabledRole(String loginId);
	/**
	 * �г��û����еĽ�ɫ,�������úͲ�����
	 * @param loginId
	 * @return
	 */
	public List listUserAllRole(String loginId);
	/**
	 * ����һ����ɫ,��ɫID�����ظ�
	 * @param role
	 */
	public void addRole(DtoRole role);
	/**
	 * ���ҽ�ɫ����Ӧ��Role,������ʱ���ؿ�
	 * @param roleName
	 */
	public DtoRole getRole(String roleName);
	/**
	 * ����һ����ɫ��Ϣ,��ɫ�������ظ�
	 * @param role
	 */
	public void updateRole(DtoRole role,String roleId);
	/**
	 * ����������Ա��Ӧ�Ľ�ɫ
	 */
	public void saveOrUpdateUserRole(String loginId,String[] roleIds,String domain); 

        /**
         * ���ݽ�ɫ��ɾ����Ӧ��Role
         * @param rolename String
         */
        public void deleteRole(String roldId);

        /**
        * ��ȡ��ɫ��
        * @return TreeNode
        */
        public ITreeNode getRoleTree();

        /**
         * �г�����״̬���úͿɼ��Ľ�ɫ
         * @return List
         */
        public List listEnabled2VisibleRole();
        
        /**
         * �г�ĳЩ��ɫ�µ�����login id
         * @param roleId
         * @return
         */
        public List listLoginIdUnderRole(String[] roleIds);

}
