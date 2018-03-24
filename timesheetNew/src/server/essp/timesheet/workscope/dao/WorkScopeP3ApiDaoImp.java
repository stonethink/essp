package server.essp.timesheet.workscope.dao;

import java.util.*;
import c2s.dto.DtoUtil;
import c2s.essp.timesheet.workscope.DtoActivity;
import c2s.essp.timesheet.workscope.DtoAccount;
import com.primavera.integration.client.bo.BOIterator;
import com.primavera.integration.client.bo.BusinessObjectException;
import com.primavera.integration.client.bo.enm.ActivityStatus;
import com.primavera.integration.client.bo.object.ActivityCode;
import com.primavera.integration.client.bo.object.ActivityCodeAssignment;
import com.primavera.integration.client.bo.object.ActivityCodeType;
import com.primavera.integration.client.bo.object.Project;
import server.essp.common.primavera.PrimaveraApiBase;
import com.wits.util.ThreadVariant;
import c2s.essp.common.user.DtoUser;
import com.primavera.common.value.BeginDate;
import com.primavera.common.value.EndDate;
import com.primavera.common.value.ObjectId;
import com.primavera.integration.util.WhereClauseHelper;
import com.primavera.integration.client.bo.object.Activity;
import com.wits.util.comDate;
import com.primavera.integration.client.bo.object.ResourceAssignment;

/**
 * <p>
 * Title: WorkScopeP3ApiDaoImp类
 * </p>
 * 
 * <p>
 * Description:从P3中取得符合条件的数据
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
 * @author tbh
 * @version 1.0
 */
public class WorkScopeP3ApiDaoImp extends PrimaveraApiBase implements
		IWorkScopeP3ApiDao {

	private final static Map<String, String> AccountPropertiesMap;

	private final static Map<String, String> activityPropertiesMap;

	private String activityCodeTypeName = "JobCode";

	/**
	 * 获取指定ObjectId的DtoAccount
	 * 
	 * @param AccountId
	 *            Integer
	 * @return DtoAccount
	 * @throws Exception
	 */
	public DtoAccount getAccount(Integer AccountId) throws Exception {
		DtoAccount dtoAccount = null;
		String strWhere = "ObjectId = " + AccountId;
		BOIterator boiAccounts = getGOM().loadProjects(
				new String[] { "ObjectId", "Id", "Name" }, strWhere, "");
		if (boiAccounts.hasNext()) {
			Project project = (Project) boiAccounts.next();
			dtoAccount = new DtoAccount();
			dtoAccount.setActivities(new ArrayList());
			DtoUtil.copyBeanToBeanWithPropertyMap(dtoAccount, project,
					AccountPropertiesMap);
		}
		return dtoAccount;
	}

	/**
	 * 获取当前用户对指定Account下有存取权限的所有Activity
	 * 
	 * @return List<DtoActivity>
	 * @throws Exception
	 */
	public List getActivityList() throws Exception {
//		BeginDate bDate = null;
//		EndDate eDate = null;
//		if (beginDate != null) {
//			bDate = BeginDate.createBeginDate(beginDate);
//		}
//		if (endDate != null) {
//			eDate = EndDate.createEndDate(endDate);
//		}
//		String strWhere = " 1=1";
//		// 交集
//		if (eDate != null) {
//			strWhere += " and PlannedStartDate <= "
//					+ WhereClauseHelper.formatDate(eDate);
//		}
//		if (bDate != null) {
//			strWhere += " and PlannedFinishDate >= "
//					+ WhereClauseHelper.formatDate(bDate);
//		}
		BOIterator boiActivities = getCurrentResource()
				.loadResourceAssignments(
						new String[] { "ObjectId", "ActivityObjectId",
								"ProjectObjectId", "ActivityId",
								"ActivityName", "PlannedStartDate",
								"PlannedFinishDate","StartDate",
								"FinishDate", "RoleName",
								"IsPrimaryResource" }, "", "ActivityId");

		// 转换Bean
		List dtoList = new ArrayList();
		while (boiActivities.hasNext()) {
			DtoActivity dto = p3Activity2DtoActivity((ResourceAssignment) boiActivities
					.next());
//			if (isDtoEnable(dto, noStart, finished)) {
				dtoList.add(dto);
//			}
		}
		return dtoList;
	}

//	private boolean isDtoEnable(DtoActivity dto, boolean noStart,
//			boolean finished) {
//		String status = dto.getActivityStatus();
//
//		if (status == null) {
//			return true;
//		}
//		if (noStart == false
//				&& ActivityStatus.NOT_STARTED.toString().equals(status)) {
//			return false;
//		}
//		if (finished == false
//				&& ActivityStatus.COMPLETED.toString().equals(status)) {
//			return false;
//		}
//		return true;
//	}

	/**
	 * 根据ActivityId获取ActivityName,如果没找到则返回空字符串
	 * 
	 * @param activityId
	 *            Long
	 * @return String
	 */
	public String getActivityShowName(Long activityId) {
		String name = "";
		Activity activity = null;
		try {
			activity = Activity.load(this.getSession(), new String[] { "Id",
					"Name" }, new ObjectId(activityId));
			if (activity != null) {
				name = activity.getId() + " -- " + activity.getName();
			}
		} catch (Exception e) {
			log.warn("getActivityName error", e);
		}
		return name;
	}

	private Activity getActivity(ObjectId activityObjId) throws Exception {
		return Activity.load(this.getSession(), new String[] { "WBSCode", "WBSName", "StartDate",
				"FinishDate", "Status" }, activityObjId);
	}

	/**
	 * 将P3 Activity对象转换成DtoActivity
	 * 
	 * @param activity
	 *            Activity
	 * @return DtoActivity
	 */
	public DtoActivity p3Activity2DtoActivity(ResourceAssignment assignment)
			throws Exception {
		DtoActivity dto = new DtoActivity();
		try {
			Activity activity = getActivity(assignment.getActivityObjectId());
			dto.setActivityStart(exchangeDate(activity.getStartDate()));
			dto.setActivityFinish(exchangeDate(activity.getFinishDate()));
			dto.setActivityStatus(activity.getStatus() == null ? null
					: activity.getStatus().toString());
			dto.setCode(assignment.getActivityId());
			// 将StartDate和EndDate转换成Date类型
			dto.setPlannedFinishDate(exchangeDate(assignment
					.getFinishDate()));
			dto.setPlannedStartDate(exchangeDate(assignment
					.getStartDate()));
			dto.setWbsCode(activity.getWBSCode());
			dto.setWbsName(activity.getWBSName());
			dto.setName(assignment.getActivityName());
			dto.setId(assignment.getActivityObjectId().toInteger());
			dto.setAssignmentId(assignment.getObjectId().toInteger());
			dto.setProjectId(assignment.getProjectObjectId().toInteger());
			dto.setRoleName(assignment.getRoleName());
			dto.setIsPrimaryResource(assignment.getIsPrimaryResource());
			dto.setCodeValueName(getActivityCode(activity));
		} catch (BusinessObjectException ex) {
			log.error(ex);
		}
		return dto;
	}

	/**
	 * 获取作业分类码
	 * 
	 * @param projObjId
	 * @return String
	 * @throws Exception
	 */
	public String getActivityCode(Activity activity)
			throws Exception {

		if (activity == null) {
			return null;
		}
		String strWhere = "ActivityCodeTypeName='" + activityCodeTypeName +"'";
		BOIterator<ActivityCodeAssignment> iter = activity.loadActivityCodeAssignments(
				new String[] { "ActivityCodeValue" }, strWhere, null);
		if(iter.hasNext()) {
			return iter.next().getActivityCodeValue();
		}
		return null;
	}

	/**
	 * 将StartDate和EndDate转换成Date类型
	 * 
	 * @param p3Date
	 * @return Date
	 */
	public Date exchangeDate(Date p3Date) {
		if (p3Date == null) {
			return null;
		} else {
			return new Date(p3Date.getTime());
		}

	}

	static {
		AccountPropertiesMap = new HashMap();
		AccountPropertiesMap.put("objectId", "id");
		AccountPropertiesMap.put("id", "code");
		AccountPropertiesMap.put("name", "name");

		activityPropertiesMap = new HashMap<String, String>(
				AccountPropertiesMap);
		activityPropertiesMap.put("projectObjectId", "projectId");
		activityPropertiesMap.put("objectId", "id");
		activityPropertiesMap.put("id", "code");
		activityPropertiesMap.put("name", "name");
		activityPropertiesMap.put("plannedStartDate", "startDate");
		activityPropertiesMap.put("plannedFinishDate", "endDate");
	}

	public static void main(String[] args) {
		System.out.println(ActivityStatus.NOT_STARTED.toString());
		System.out.println(ActivityStatus.IN_PROGRESS.toString());
		System.out.println(ActivityStatus.COMPLETED.toString());
	}

	public void setActivityCodeTypeName(String activityCodeTypeName) {
		this.activityCodeTypeName = activityCodeTypeName;
	}

}
