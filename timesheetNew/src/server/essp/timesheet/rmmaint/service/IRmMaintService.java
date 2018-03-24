package server.essp.timesheet.rmmaint.service;

import java.util.List;

public interface IRmMaintService {
	/**
	 *  ��ȡѡ�����Ա����Ϣ�б�
	 *  @param loginIds
	 *  @return List
	 */
	public List getUserInfo(String loginIds);
	
	/**
	 * ����loginId��ȡRm��loginId
	 * @param loginId
	 * @return
	 */
	public String getRmByLoginId(String loginId);
	
	/**
	 * ���������RM��Ӧ��ϵ
	 * @param loginId
	 * @param rmId
	 */
	public void saveOrUpdateRm(String loginId, String rmId);
	
	/**
	 * ɾ��ά����RM��Ӧ��ϵ
	 * @param loginId
	 */
	public void delRm(String loginId);
	
	/**
	 * ��ȡ�Ѿ�ά����RM����Ա�б�
	 * @return
	 */
	public List getUserList();
	
	/**
	 * ��ȡrm�µ������Ա
	 * @param rmId
	 * @return
	 */
	public List<String> getHumanUnderRM(String rmId);
	/**
	 * ����site��ȡrm�µ������Ա
	 * @param rmId
	 * @param site
	 * @return
	 */
	public List<String> getHumanUnderRMBySite(String rmId, String site);
	/**
     * ��ȡ�û���
     * @param loginId String
     * @return String
     */
    public String getUserName(String loginId);
    
    /**
     * ����loginId��ȡsite
     * @param loginId
     * @return
     */
    public String getSite(String employeeId);
    
    /**
     * ����loginId��ȡemail
     * @param loginId
     * @return
     */
    public String getUserEmail(String loginId);
    
    /**
     * ��ȡ��������
     * @param loginId
     * @return
     */
    public String getDept(String loginId);

}
