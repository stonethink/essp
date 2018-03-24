package server.essp.timesheet.step.management.dao;

import java.util.*;

import com.primavera.integration.client.bo.object.Activity;

import c2s.essp.timesheet.step.management.DtoActivityForStep;

/**
 * 
 * <p>
 * Title: ICalendarP3Dao
 * </p>
 * 
 * <p>
 * Description: 访问P3获取日历相关信息的接口
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * 
 * <p>
 * Company: WistronITS
 * </p>
 * 
 * @author Robin
 * @version 1.0
 */
public interface IStepManagementP3ApiDao {
	/**
	 * 获取指定项目的所有Activity
	 * 
	 * @return Activity List
	 * @throws Exception
	 */
	public List getActivityList(String projectObjectId) throws Exception;
	
	/**
	 * 获取指定用户参与的Project
	 * 
	 * @param loginId String
	 * @return Project List
	 * @throws Exception
	 */
	public Vector getAcntList(String loginId) throws Exception;

	/**
	 * 获取指定用户参与的Project
	 * 		上次选中的项目放到第一位
	 * @param loginId String
	 * @param lastAcntId String
	 * @return Project List
	 * @throws Exception
	 */
	public Vector getAcntList(String loginId, String lastAcntId) throws Exception;
	
	/**
	 * 根据Activity　ObjectId，开始日期，结束日期查询所有的工作日
	 * @param activityObjId
	 * @param start
	 * @param finish
	 * @return
	 */
	public List<Date> getAllWorkDayByProjectCalendar(String activityObjId,
			Date start, Date finish);
	
	public DtoActivityForStep getActivityByObjectId(String activityObjId);
	
	public DtoActivityForStep getActivity(Long activityId);
	
	public Map getActivityPResourcesId(Activity activity) throws Exception;
}
