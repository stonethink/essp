package server.essp.timesheet.outwork.privilege.service;

import java.util.List;

import c2s.dto.ITreeNode;

public interface IOutWorkerPriService {
	  /**
       * �г�������Ȩ��ά������Ա����Ϣ
       * @return List
	   */
		public List listUserInfo();
        
        /**
         * ������Աʱ�����ɸ�Ա��������Ա�б���
         * @param loginIds
         * @param userList
         * @return List
         */
        public List addUserInfo(String loginIds,List uList);
		
		/**
		 * �г����в��ź͵�ǰԱ��������ά���Ĳ��ţ�����ѡ��
		 * @param loginId
		 * @return ITreeNode
		 */
		public ITreeNode loadQueryPrivilege(String loginId);
		
        /**
         * ����
         * @param loginId
         * @param root
         */
		public void saveOutWorkerPri(String loginId,ITreeNode root);
	
        /**
         * ɾ��
         * @param LoginId
         */
		public void delOutworkerPrivilege(String LoginId);
}
