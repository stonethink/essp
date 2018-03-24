package server.essp.timesheet.rmmaint.service;

import java.util.List;

public interface IRmMaintService {
	/**
	 *  获取选择的人员的信息列表
	 *  @param loginIds
	 *  @return List
	 */
	public List getUserInfo(String loginIds);
	
	/**
	 * 根据loginId获取Rm的loginId
	 * @param loginId
	 * @return
	 */
	public String getRmByLoginId(String loginId);
	
	/**
	 * 新增或更新RM对应关系
	 * @param loginId
	 * @param rmId
	 */
	public void saveOrUpdateRm(String loginId, String rmId);
	
	/**
	 * 删除维护的RM对应关系
	 * @param loginId
	 */
	public void delRm(String loginId);
	
	/**
	 * 获取已经维护了RM的人员列表
	 * @return
	 */
	public List getUserList();
	
	/**
	 * 获取rm下的相关人员
	 * @param rmId
	 * @return
	 */
	public List<String> getHumanUnderRM(String rmId);
	/**
	 * 根据site获取rm下的相关人员
	 * @param rmId
	 * @param site
	 * @return
	 */
	public List<String> getHumanUnderRMBySite(String rmId, String site);
	/**
     * 获取用户名
     * @param loginId String
     * @return String
     */
    public String getUserName(String loginId);
    
    /**
     * 根据loginId获取site
     * @param loginId
     * @return
     */
    public String getSite(String employeeId);
    
    /**
     * 根据loginId获取email
     * @param loginId
     * @return
     */
    public String getUserEmail(String loginId);
    
    /**
     * 获取部门名称
     * @param loginId
     * @return
     */
    public String getDept(String loginId);

}
