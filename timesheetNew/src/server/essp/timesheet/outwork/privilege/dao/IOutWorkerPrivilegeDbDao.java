package server.essp.timesheet.outwork.privilege.dao;

import java.util.List;

public interface IOutWorkerPrivilegeDbDao {

	
	/**
	 * ���ݴ�������loginId����ɾ��
	 *
	 */
	public void delete(String loginId);
	
	/**
	 * �г�������Ȩ�޵��û�
	 * @return
	 */
	public List listUser();
	
    /**
     * �õ��������T����ǰ�T�����ڙಿ�T
     * @param loginId
     * @param accountId
     * @param isRoot
     * @return List
     */
	public List loadPrivilege(String loginId,String accountId,boolean isRoot);
    
    /**
     * ����loginId�õ������Ľ�ɫ
     * @param loginId
     * @return List
     */
    public List getRoleList(String loginId);
	
}
