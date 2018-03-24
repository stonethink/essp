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
 * Description: ����P3��ȡ���������Ϣ�Ľӿ�
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
	 * ��ȡָ����Ŀ������Activity
	 * 
	 * @return Activity List
	 * @throws Exception
	 */
	public List getActivityList(String projectObjectId) throws Exception;
	
	/**
	 * ��ȡָ���û������Project
	 * 
	 * @param loginId String
	 * @return Project List
	 * @throws Exception
	 */
	public Vector getAcntList(String loginId) throws Exception;

	/**
	 * ��ȡָ���û������Project
	 * 		�ϴ�ѡ�е���Ŀ�ŵ���һλ
	 * @param loginId String
	 * @param lastAcntId String
	 * @return Project List
	 * @throws Exception
	 */
	public Vector getAcntList(String loginId, String lastAcntId) throws Exception;
	
	/**
	 * ����Activity��ObjectId����ʼ���ڣ��������ڲ�ѯ���еĹ�����
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
