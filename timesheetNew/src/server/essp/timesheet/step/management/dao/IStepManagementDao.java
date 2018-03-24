package server.essp.timesheet.step.management.dao;

import java.util.Date;
import java.util.List;

import db.essp.timesheet.TsStep;

public interface IStepManagementDao {
	/**
	 * 通过作业ID查询所有Step
	 * 
	 * @param activityId
	 * @return
	 */
	public List listStepByActivityId(Long activityId);
	
	public List listDeaultStepsByLongId(String loginId, Long activityId);
	
	public TsStep loadByRid(Long rid);

	/**
	 * 新增一个Step
	 * 
	 * @param step
	 */
	public void addStep(TsStep step);

	/**
	 * 更新一个Step
	 * 
	 * @param step
	 */
	public void updateStep(TsStep step);
    
    public List getAllStepByPrjId(Long projId);
    
    /**
     * 获取某Step下在日报中已填写的所有累计工时
     * @param stepRid Long
     * @return Double
     */
    public Double getStepCumulativeHours(Long stepRid);
    
    /**
     * 列出某专案下某人今天昨天的step信息
     * @param projId
     * @param resourceId
     * @param today
     * @param yesterday
     * @return
     */
    public List listPersonStep(Long projId, String resourceId, 
    		Date today, Date yesterday);
    
    /**
     * 列出某专案下所有人今天昨天的step信息
     * @param projId
     * @param today
     * @param yesterday
     * @return
     */
    public List listProjectStep(Long projId, Date day, String date);
    
    /**
     * 列出所有StepDetail中的信息
     * @return List
     */
    public List getStepDetailInfo();
}
