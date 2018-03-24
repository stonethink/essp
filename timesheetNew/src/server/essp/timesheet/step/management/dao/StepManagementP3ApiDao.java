package server.essp.timesheet.step.management.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import server.essp.common.primavera.PrimaveraApiBase;
import server.framework.common.BusinessException;
import c2s.dto.DtoComboItem;
import c2s.essp.timesheet.step.management.DtoActivityForStep;

import com.primavera.common.value.ObjectId;
import com.primavera.integration.client.bo.BOIterator;
import com.primavera.integration.client.bo.BusinessObjectException;
import com.primavera.integration.client.bo.enm.ProjectStatus;
import com.primavera.integration.client.bo.enm.UDFDataType;
import com.primavera.integration.client.bo.enm.UDFSubjectArea;
import com.primavera.integration.client.bo.object.Activity;
import com.primavera.integration.client.bo.object.Calendar;
import com.primavera.integration.client.bo.object.Project;
import com.primavera.integration.client.bo.object.ResourceAssignment;
import com.primavera.integration.client.bo.object.UDFType;

public class StepManagementP3ApiDao extends PrimaveraApiBase implements
		IStepManagementP3ApiDao {
	
	private final static String STEPTYPE_NAME = "StepType";
	private final static String FUNCTIONID_NAME = "FunctionID";
	private final static String SPONSOR_NAME = "Sponsor";
	
	private static UDFType functionIdType = null;
	private static UDFType stepType = null;
	private static UDFType sponsorType = null;
	
	/**
	 * 获取指定用户参与的Project
	 * @param loginId String
	 * @return Project List
	 * @throws Exception
	 */
	public Vector getAcntList(String loginId) throws Exception {
		return getAcntList(loginId, null);
	}

	/**
	 * 获取指定用户参与的Project
	 * 		上次选中的项目放到第一位
	 * @param loginId String
	 * @param lastAcntId String
	 * @return Project List
	 * @throws Exception
	 */
	public Vector getAcntList(String loginId, String lastAcntId) throws Exception {
		BOIterator boiActivities = this.getResource(loginId)
				.loadResourceAssignments(
						new String[] { "ObjectId", "ActivityObjectId",
								"ProjectObjectId", "ActivityId", "ActivityName" },
						"", "");
		String condition = "";
		while (boiActivities.hasNext()) {
			ResourceAssignment ra = ((ResourceAssignment) boiActivities.next());
			if (condition.length() > 0) {
				condition = condition + ","
						+ ra.getProjectObjectId().toString();
			} else {
				condition = ra.getProjectObjectId().toString();
			}
		}
		Vector acntList = new Vector();
		if(condition.length()<1){
			return acntList;
		}
		BOIterator projects = this.getGOM().loadProjects(
				new String[] { "ObjectId", "Id", "Name", "CheckOutStatus" },
				// "ObjectId =3873", "ObjectId");
				"ObjectId in(" + condition + ") and CheckOutStatus='N' and Status='"
				 + ProjectStatus.ACTIVE.getValue() + "'", "ObjectId");
		while (projects.hasNext()) {
			Project elem = (Project) projects.next();
			String id = elem.getId();
			String name = elem.getName();
			DtoComboItem item = new DtoComboItem(id + "--" + name, id);
			if(lastAcntId != null && lastAcntId.equalsIgnoreCase(id)) {
				acntList.add(0, item);
			} else {
				acntList.add(item);
			}
		}
		return acntList;
	}

	public List<Date> getAllWorkDayByProjectCalendar(String activityObjId,
			Date start, Date finish) {
		List<Date> result = new ArrayList<Date>();
		try {
			Activity activity = this.getActivity(new ObjectId(new Long(
					activityObjId)));
			Calendar cal = activity.loadCalendar(new String[] { "ObjectId" });
			java.util.Calendar c = java.util.Calendar.getInstance();
			c.setTime(start);
			while (c.getTime().compareTo(finish) <= 0) {

				double t = cal.getTotalWorkHours(c.getTime());
				if (t > 0) {
					result.add(c.getTime());
				}
				c.add(java.util.Calendar.DAY_OF_MONTH, 1);
			}

		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public List getActivityList(String projectId) throws Exception {
		List dtoList = new ArrayList();
		if(projectId==null||projectId.length()==0){
			return dtoList;
		}
		Project p = getProject(projectId);
		if(p == null) {
			return dtoList;
		}
		BOIterator boiActivities = p.loadAllActivities(new String[] {"ObjectId", "Id", "Name", "WBSCode",
				"WBSName", "PlannedStartDate", "PlannedDuration", "PlannedLaborUnits",
				"PlannedFinishDate", "ActualStartDate", "ActualFinishDate",
				"Status", "EarlyStartDate", "EarlyFinishDate", "LateStartDate",
				"LateFinishDate", "ExpectedFinishDate","ProjectObjectId",
				"PrimaryResourceId", "PrimaryResourceName", 
				getFctionIdType().getUDFFieldName(),
				getStepType().getUDFFieldName(),
				getSponsorType().getUDFFieldName()
				},"", "Id");
		// 转换Bean
		while (boiActivities.hasNext()) {
			DtoActivityForStep dto = p3Activity2DtoActivity((Activity) boiActivities
					.next());
			dto.setProjectCode(p.getId());
			dtoList.add(dto);
		}
		return dtoList;
	}
	
	private Project getProject(String projectId) {
		if(projectId == null || "".equals(projectId)) {
			return null;
		}
		Project project = null;
		BOIterator boi;
		try {
			boi = this.getGOM().loadProjects(new String[] { "ObjectId", "Id", "Name" }, "upper(Id) = upper('" + projectId + "')", "");
			if(boi.hasNext()) {
				project = (Project) boi.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return project;
	}
	
	public DtoActivityForStep getActivity(Long activityId) {
		DtoActivityForStep dto = null;
		try {
			Activity act = getActivity(new ObjectId(activityId));
			Project p = act.loadProject(new String[] { "ObjectId", "Id", "Name" });
			if(act == null) {
				return null;
			}
			dto = p3Activity2DtoActivity(act);
			dto.setProjectCode(p.getId());
		} catch (Exception e) {
			log.error(e);
		}
		return dto;
	}

	private Activity getActivity(ObjectId activityObjId) throws Exception {
		return Activity.load(this.getSession(), new String[] {"ObjectId", "Id", "Name", "WBSCode",
				"WBSName", "PlannedStartDate", "PlannedDuration", "PlannedLaborUnits",
				"PlannedFinishDate", "ActualStartDate", "ActualFinishDate",
				"Status", "EarlyStartDate", "EarlyFinishDate", "LateStartDate",
				"LateFinishDate", "ExpectedFinishDate","ProjectObjectId",
				"PrimaryResourceId", "PrimaryResourceName", 
				getFctionIdType().getUDFFieldName(),
				getStepType().getUDFFieldName(),
				getSponsorType().getUDFFieldName() }, activityObjId);
	}

	/**
	 * 将P3 Activity对象转换成DtoActivity
	 * 
	 * @param activity
	 *            Activity
	 * @return DtoActivity
	 */
	private DtoActivityForStep p3Activity2DtoActivity(
			Activity activity) throws Exception {
		DtoActivityForStep dto = new DtoActivityForStep();
		try {
			dto.setId(activity.getObjectId().toInteger());
			dto.setCode(activity.getId());
			dto.setName(activity.getName());

			dto.setWbsCode(activity.getWBSCode());
			dto.setWbsName(activity.getWBSName());
			dto.setFunctionId(activity.getUDFText(getFctionIdType().getObjectId()));
			dto.setStepType(activity.getUDFText(getStepType().getObjectId()));
			dto.setSponsor(activity.getUDFText(getSponsorType().getObjectId()));
			dto.setProjectId(activity.getProjectObjectId().toInteger());
			Iterator resIte = activity.loadResourceAssignments(new String[] {
					"ResourceId", "ResourceName", "IsPrimaryResource" },
					"ActivityObjectId=" + dto.getId(), "");
			String resourceIds = activity.getPrimaryResourceId();
			String resources = activity.getPrimaryResourceName();
			while (resIte.hasNext()) {
				ResourceAssignment res = (ResourceAssignment) resIte.next();
				String workerId = res.getResourceId();
				String workerName = res.getResourceName();
				if (res.getIsPrimaryResource()) {
					dto.setManagerId(workerId);
					dto.setManager(workerName);
				} else if (resourceIds != null) {
					resourceIds = resourceIds + "," + workerId;
					resources = resources + "," + workerName;
				} else {
					resourceIds = workerId;
					resources = workerName;
				}
			}
			dto.setResourceIds(resourceIds);
			dto.setResources(resources);
			if(activity.getPlannedLaborUnits() != null) {
				dto.setPlanHour(activity.getPlannedLaborUnits().doubleValue());
			}
			dto.setPlanDuration(activity.getPlannedDuration().floatValue());
			dto.setPlanStart(exchangeDate(activity.getPlannedStartDate()));
			dto.setPlanFinish(exchangeDate(activity.getPlannedFinishDate()));
			dto.setActualStart(exchangeDate(activity.getActualStartDate()));
			dto.setActualFinish(exchangeDate(activity.getActualFinishDate()));
			dto.setStatus(activity.getStatus().getDescription());
			dto.setEarliestStart(exchangeDate(activity.getEarlyStartDate()));
			dto.setEarliestFinish(exchangeDate(activity.getEarlyFinishDate()));
			dto.setAtLatestStart(exchangeDate(activity.getLateStartDate()));
			dto.setAtLatestFinish(exchangeDate(activity.getLateFinishDate()));
			dto
					.setEstimatefinish(exchangeDate(activity
							.getExpectedFinishDate()));
		} catch (BusinessObjectException ex) {
			log.error(ex);
		}
		return dto;
	}
	public Map getActivityPResourcesId(Activity activity) throws Exception {
		Iterator resIte = activity.loadResourceAssignments(new String[] {
				"ResourceId", "ResourceName", "IsPrimaryResource" },
				"ActivityObjectId=" + activity.getObjectId().toString(), "");
		String resourceIds = activity.getPrimaryResourceId();
		String resources = activity.getPrimaryResourceName();
		while (resIte.hasNext()) {
			ResourceAssignment res = (ResourceAssignment) resIte.next();
			String workerId = res.getResourceId();
			String workerName = res.getResourceName();
			if (res.getIsPrimaryResource()) {
				continue;
			} else if (resourceIds != null) {
				resourceIds = resourceIds + "," + workerId;
				resources = resources + "," + workerName;
			} else {
				resourceIds = workerId;
				resources = workerName;
			}
		}
		Map map = new HashMap();
		map.put("ID", resourceIds);
		map.put("NAME", resources);
		return map;
	}
	public DtoActivityForStep getActivityByObjectId(String activityObjId) {	
		DtoActivityForStep dto = new DtoActivityForStep();
		Activity activity=null;
		try{
			dto.setId(Integer.parseInt(activityObjId));
		  	activity = this.getActivity(new ObjectId(new Long(
		  			activityObjId)));
			Iterator resIte = activity.loadResourceAssignments(new String[] {
					"ResourceId", "ResourceName", "IsPrimaryResource","ActivityId", "ActivityName" },
					"ActivityObjectId=" + dto.getId(), "");
			String resourceIds = null;
			String resources = null;
			while (resIte.hasNext()) {
				ResourceAssignment res = (ResourceAssignment) resIte.next();				
				String workerId = res.getResourceId();
				String workerName = res.getResourceName();
				if (res.getIsPrimaryResource()) {
					dto.setManagerId(workerId);
					dto.setManager(workerName);
				}
				if (resourceIds != null) {
					resourceIds = resourceIds + "," + workerId;
					resources = resources + "," + workerName;
				} else {
					resourceIds = workerId;
					resources = workerName;
				}
			}
			dto.setCode(activity.getId());
			dto.setName(activity.getName());
			dto.setResourceIds(resourceIds);
			dto.setResources(resources);
		  	dto.setWbsCode(activity.getWBSCode());
		  	dto.setWbsName(activity.getWBSName());
		  	if(activity.getPlannedLaborUnits() != null) {
				dto.setPlanHour(activity.getPlannedLaborUnits().doubleValue());
			}
			dto.setPlanDuration(activity.getPlannedDuration().floatValue());
			dto.setPlanStart(exchangeDate(activity.getPlannedStartDate()));
			dto.setPlanFinish(exchangeDate(activity.getPlannedFinishDate()));
			dto.setActualStart(exchangeDate(activity.getActualStartDate()));
			dto.setActualFinish(exchangeDate(activity.getActualFinishDate()));
			dto.setStatus(activity.getStatus().getDescription());
			dto.setEarliestStart(exchangeDate(activity.getEarlyStartDate()));
			dto.setEarliestFinish(exchangeDate(activity.getEarlyFinishDate()));
			dto.setAtLatestStart(exchangeDate(activity.getLateStartDate()));
			dto.setAtLatestFinish(exchangeDate(activity.getLateFinishDate()));
			dto.setEstimatefinish(exchangeDate(activity.getExpectedFinishDate()));	
		}catch(Exception e){
			log.error(e);
		}
		return dto;
	}
	
	private UDFType getFctionIdType() {
		if(functionIdType == null) {
			functionIdType = getUDFtype(FUNCTIONID_NAME);
		}
		return functionIdType;
	}
	
	private UDFType getStepType() {
		if(stepType == null) {
			stepType = getUDFtype(STEPTYPE_NAME);
		}
		return stepType;
	}
	
	private UDFType getSponsorType() {
		if(sponsorType == null) {
			sponsorType = getUDFtype(SPONSOR_NAME);
		}
		return sponsorType;
	}
	
	/**
	 * 根据传进来的title寻找自定义栏位，返回自定义类型
	 * @param title
	 * @return
	 */
	private UDFType getUDFtype(String title) {
		UDFType udfType = null;
		try{
			BOIterator boiUDFTypes = this.getGOM().loadUDFTypes(
					new String[] { "DataType", "ObjectId", "Title" },
					"SubjectArea='" + UDFSubjectArea.ACTIVITY.getValue()
							+ "' AND DataType='" + UDFDataType.TEXT.getValue()
							+ "' and Title = '"+title+"'", "");
			if(boiUDFTypes.hasNext()) {
				udfType = (UDFType) boiUDFTypes.next();		
			}		
		}catch(Exception e){
			throw new BusinessException("BZ3003","Load UDF type is error.", e);
		}	
		return udfType;
	}

	public Date exchangeDate(Date p3Date) {
		if (p3Date == null) {
			return null;
		} else {
			return new Date(p3Date.getTime());
		}
	}
}
