package server.essp.timesheet.outwork.privilege.service;

import java.util.List;

import c2s.dto.ITreeNode;

public interface IOutWorkerPriService {
	  /**
       * 列出所有有权限维护差人员的信息
       * @return List
	   */
		public List listUserInfo();
        
        /**
         * 新增人员时将若干个员工加入人员列表中
         * @param loginIds
         * @param userList
         * @return List
         */
        public List addUserInfo(String loginIds,List uList);
		
		/**
		 * 列出所有部门和当前员工所可以维护的部门（被勾选）
		 * @param loginId
		 * @return ITreeNode
		 */
		public ITreeNode loadQueryPrivilege(String loginId);
		
        /**
         * 保存
         * @param loginId
         * @param root
         */
		public void saveOutWorkerPri(String loginId,ITreeNode root);
	
        /**
         * 删除
         * @param LoginId
         */
		public void delOutworkerPrivilege(String LoginId);
}
