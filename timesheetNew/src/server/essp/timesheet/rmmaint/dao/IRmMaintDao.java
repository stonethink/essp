package server.essp.timesheet.rmmaint.dao;

import java.util.Date;
import java.util.List;

import c2s.essp.common.user.DtoUserBase;
import c2s.essp.timesheet.report.DtoTsStatusQuery;

import db.essp.timesheet.TsHumanBase;
import db.essp.timesheet.TsRmMaint;

public interface IRmMaintDao {
	
	/**
	 * 根据loginId查询特殊RM对应关系
	 * @param loginId
	 * @return
	 */
	public TsRmMaint getRmByLoginId(String loginId);
	
	/**
	 * 新增特殊RM对应关系
	 * @param tsRmMaint
	 */
	public void addRmMaint(TsRmMaint tsRmMaint);
	/**
	 * 更新特护RM对应关系
	 * @param tsRmMaint
	 */
	public void updateRmMaint(TsRmMaint tsRmMaint);
	/**
	 * 删除特殊RM对应关系
	 * @param tsRmMaint
	 */
	public void delRmMaint(TsRmMaint tsRmMaint);
	
	/**
	 * 查询RM下的人员的ID
	 * @param rmId
	 * @return
	 */
	public List<TsRmMaint> getPersonUnderRm(String rmId);
	
	/**
	 * 查询所有已经维护了的人员信息
	 * @return
	 */
	public List<TsRmMaint> getAllUserMainted();
	
	/**
	 * 根据loginId 查询员工信息
	 * @param employeeId
	 * @return
	 */
	public TsHumanBase loadHumanById(String employeeId);
	
	/**
     * 列出否签核者下的所有人员
     * @param managerId String
     * @return List
     */
    public List<DtoUserBase> listPersonByManagerFromAD(String managerId);
    
    /**
     * 列出rm下的所有人员
     * @param rmId
     * @return
     */
    public List<TsHumanBase> listHumanByRmFromDB(String rmId);
    
    /**
     * 通过site从DB中获取RM下的人员
     * @param rmId
     * @param site
     * @return
     */
    public List<TsHumanBase> listHumanByRmFromDBBySite(String rmId, String site);
    
    
    /**
     * 根据SITE得到SITE下对应的人员
     * @param site
     * @return List
     */
    public List getLoginIdList(String site, boolean viewAll);
    
    /**
     * 根据SITE得到SITE下对应有效期（入职、离职日期）范围内的人员
     * @param site
     * @param begin
     * @param end
     * @return List
     */
    public List listEnableLoginId(String site, Date begin, Date end);
    
    /**
     * 查询所有人员(其入职日期和离职日期与指定时间有交集的)
     * @param begin
     * @param end
     * @param site
     * @return
     */
    public List<TsHumanBase> listAllHuman(Date begin, Date end, String site);
    
    /**
     * 得到不重复的SITE集合
     * @return List
     */
    public List getSiteFromHumanBase(String site);
    
    /**
     * 根据SITE得到SITE下对应有效期（入职、离职日期）范围内的人员
     * @param site
     * @param begin
     * @param end
     * @return List
     */
    public List listEnableLoginId(String site, DtoTsStatusQuery dtoQuery,
            boolean viewAll);
    
    /**
     * 得到所有的員工
     * @return
     */
    public List listAllEmployee();
    
    /**
     * 根據部門得到該部門對應下的所有員工集合
     * @param deptIds
     * @return List
     */
    public List getLoginIdsByDept(String deptIds);
    
    /**
     * 根據SITE和時間范圍查詢出每個員工在每個假別中的總工時數
     * @param site
     * @param begin
     * @param end
     * @return List
     */
    public List getSumLeaveHoursByDate(Date begin,Date end,String site);
    
    /**
     * 取得直接人力
     * @return List
     */
    public List listDirectHuman();
    /**
     * 根据部门代码查询部门下的人员
     * @param deptId
     * @return
     */
    public List listPersonsByDept(String deptId);
}