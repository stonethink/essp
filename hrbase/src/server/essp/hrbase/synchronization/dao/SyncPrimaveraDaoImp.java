package server.essp.hrbase.synchronization.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import server.essp.common.primavera.PrimaveraApiBase;
import server.framework.common.BusinessException;
import c2s.essp.common.calendar.WorkCalendar;

import com.primavera.common.value.BeginDate;
import com.primavera.common.value.Cost;
import com.primavera.common.value.ObjectId;
import com.primavera.common.value.UnitsPerTime;
import com.primavera.integration.client.bo.BOIterator;
import com.primavera.integration.client.bo.object.Calendar;
import com.primavera.integration.client.bo.object.Currency;
import com.primavera.integration.client.bo.object.Resource;
import com.primavera.integration.client.bo.object.ResourceCode;
import com.primavera.integration.client.bo.object.ResourceCodeAssignment;
import com.primavera.integration.client.bo.object.ResourceCodeType;
import com.primavera.integration.client.bo.object.ResourceRate;
import com.primavera.integration.client.bo.object.User;
import com.primavera.integration.util.WhereClauseHelper;
import com.wits.util.comDate;

import db.essp.hrbase.HrbHumanBaseLog;

public class SyncPrimaveraDaoImp extends PrimaveraApiBase implements
		ISyncPrimaveraDao {
	
	private String jobCodeType = "JobCode";
	private static Map<String, String> site2CalendarMap = new HashMap();
	private static Map<String, String> site2CurrencyMap = new HashMap();
	private ResourceCodeType resourceCodeType;
	
	/**
	 * 新增资源
	 *  @param hbLog HrbHumanBaseLog
	 * @return remark String
	 */
	public String addResource(HrbHumanBaseLog hbLog) {
		StringBuffer remark = new StringBuffer();
		Resource rsc = this.getResourceById(hbLog.getEmployeeId());
		if(rsc != null) {
			remark.append("\n\t" + "exist resource with same employeeId, goto update.");
			remark.append(updateResource(hbLog));
			hbLog.setRemark(hbLog.getRemark() + remark.toString());
			return remark.toString();
		}
		rsc = new Resource(this.getSession());
		try {
			Resource parent = this.getResourceById(hbLog.getUnitCode());
			if(parent != null) {
				rsc.setParentObjectId(parent.getObjectId());
			} else {
				remark.append("\n\t" + "Didn't find OU resource.");
			}
			rsc.setId(hbLog.getEmployeeId());
			rsc.setName(hbLog.getFullName());
			rsc.setEmployeeId(hbLog.getEmployeeId());
			rsc.setTitle(hbLog.getTitle());
			rsc.setEmailAddress(hbLog.getEmail());
			rsc.setIsActive(hbLog.getOutDate() == null);
			rsc.setIsOverTimeAllowed(true);
			rsc.setUseTimesheets(true);
			
			//设置登录用户，如果没有就新增用户
			ObjectId userId = this.getUserId(hbLog.getEmployeeId());
			if(userId == null) {
				userId = this.addUser(hbLog);
				if(userId == null) {
					remark.append("\n\t" + "Can't add user.");
				} else {
					remark.append("\n\t" + "Added user.");
				}
			}
			
			//设置工时单签核者
			rsc.setUserObjectId(userId);
			ObjectId mUserId = this.getUserId(hbLog.getResManagerId());
			if(userId != null) {
				rsc.setTimesheetApprovalManagerObjectId(mUserId);
			} else {
				remark.append("\n\t" + "Didn't set resource manager user.");
			}
			
			//创建资源，获取ObjectId
			ObjectId rscId = null;
			try{
				rscId = rsc.create();
				rsc.setObjectId(rscId);
			} catch (Exception e) {
				log.error(e);
				remark.append("\n\t" + "Resource create error.");
				return remark.toString();
			}
			
			//根据职等设置资源分类码
			setResourceCodeAssignment(rscId, hbLog.getRank());
//			if(== false) {
//				remark.append("\n\t" + "Didn't set rank.");
//			}
			
			//解析并设置资源日历、货币
			remark.append(setCalendarCurrency(rsc, hbLog.getSite()));
			
			//设定入职日期
			this.cleardResourceRate(rsc);
			this.setInDate(rscId, hbLog.getInDate());
			
			//设定离职日期
			this.setOutDate(rscId, hbLog.getOutDate());
			
			rsc.update();
		} catch (Exception e) {
			log.error(e);
			remark.append("\n\t" + "Error exist.");
		}
		hbLog.setRemark(hbLog.getRemark() + remark.toString());
		return remark.toString();
	}
	
	/**
	 * 修改资源
	 * @param hbLog HrbHumanBaseLog
	 * @return remark String
	 */
	public String updateResource(HrbHumanBaseLog hbLog) {
		StringBuffer remark = new StringBuffer();
		Resource rsc = this.getResourceById(hbLog.getEmployeeId());
		if(rsc == null) {
			remark.append("\n\t" + "This resource dosn't exist.");
			hbLog.setRemark(hbLog.getRemark() + remark.toString());
			return remark.toString();
		}
		try {
			Resource parent = this.getResourceById(hbLog.getUnitCode());
			if(parent != null) {
				rsc.setParentObjectId(parent.getObjectId());
			} else {
				remark.append("\n\t" + "Didn't find OU resource.");
			}
			rsc.setId(hbLog.getEmployeeId());
			rsc.setName(hbLog.getFullName());
			rsc.setEmployeeId(hbLog.getEmployeeId());
			rsc.setTitle(hbLog.getTitle());
			rsc.setEmailAddress(hbLog.getEmail());
			rsc.setIsActive(hbLog.getOutDate() == null);
//			rsc.setIsOverTimeAllowed(true);
//			rsc.setUseTimesheets(true);
			
			ObjectId userId = this.getUserId(hbLog.getEmployeeId());
			if(userId == null) {
				userId = this.addUser(hbLog);
				if(userId == null) {
					remark.append("\n\t" + "Can't add user.");
				} else {
					remark.append("\n\t" + "Added user.");
				}
			}
			rsc.setUserObjectId(userId);
			ObjectId mUserId = this.getUserId(hbLog.getResManagerId());
			if(userId != null) {
				rsc.setTimesheetApprovalManagerObjectId(mUserId);
			} else {
				remark.append("\n\t" + "Didn't set resource manager user.");
			}
			
			//根据职等设置资源分类码
			ObjectId rscId = rsc.getObjectId();
			
			if(ResourceCodeAssignmentExist(rsc, hbLog.getRank()) == false) {
				setResourceCodeAssignment(rscId, hbLog.getRank());
			}
//			if(ResourceCodeAssignmentExist(rsc, hbLog.getRank())) {
////				remark.append("\n\t" + "Job code exist.");
//			} else if(setResourceCodeAssignment(rscId, hbLog.getRank()) == false) {
//				remark.append("\n\t" + "Didn't set rank.");
//			}
			
			//解析并设置资源日历、货币
			remark.append(setCalendarCurrency(rsc, hbLog.getSite()));
			
			//设定入职日期
			if(this.inDateExist(rsc, hbLog.getInDate())) {
				remark.append("\n\t" + "resource in date exist.");
			} else {
				this.setInDate(rscId, hbLog.getInDate());
			}
			
			//设定离职日期
			if(this.outDateExist(rsc, hbLog.getOutDate())) {
				remark.append("\n\t" + "resource out date exist.");
			} else {
				this.setOutDate(rscId, hbLog.getOutDate());
			}
			
			//执行修改
			rsc.update();
		} catch (Exception e) {
			log.error(e);
			remark.append("\n\t" + "Error exist.");
		}
		hbLog.setRemark(hbLog.getRemark() + remark.toString());
		return remark.toString();
	}
	
	/**
	 * 删除资源
	 *   将资源设为无效
	 * @param hbLog HrbHumanBaseLog
	 * @return remark String
	 */
	public String deleteResource(HrbHumanBaseLog hbLog) {
		StringBuffer remark = new StringBuffer();
		Resource rsc = this.getResourceById(hbLog.getEmployeeId());
		if(rsc == null) {
			remark.append("\n\t" + "This resource dosn't exist.");
		} else {
			try{
				rsc.setIsActive(false);
				rsc.update();
			} catch (Exception e) {
				log.error(e);
			}
		}
		User u = this.getUser(hbLog.getEmployeeId());
		if(u != null) {
			try {
				u.delete();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			remark.append("\n\t" + "This user dosn't exist.");
		}
		hbLog.setRemark(hbLog.getRemark() + remark.toString());
		return remark.toString();
	}
	
	/**
	 * 解析并设置资源日历、货币
	 * @param rsc Resource
	 * @param site String
	 * @return String remark
	 * @throws Exception
	 */
	private String setCalendarCurrency(Resource rsc, String site) throws Exception {
		StringBuffer remark = new StringBuffer();
		//解析并设置资源日历
		ObjectId cldId = this.getCalendarId(site);
		if(cldId != null) {
			rsc.setCalendarObjectId(cldId);
		} else {
			remark.append("\n\t" + "Didn't set resource calendar.");
		}
		
		//解析并设置资源货币
		ObjectId crcId = this.getCurrencyId(site);
		if(crcId != null) {
			rsc.setCurrencyObjectId(crcId);
		} else {
			remark.append("\n\t" + "Didn't set resource currency.");
		}
		return remark.toString();
	}
	
	/**
	 * 给指定资源设定入职日期
	 * 	增加一个入职日期100%的“ResourceRate”
	 *  和一个入职日期前一天0%的“ResourceRate”
	 * @param rscId ObjectId
	 * @param inDate Date
	 * @throws Exception
	 */
	private void setInDate(ObjectId rscId, Date inDate) throws Exception {
		if(inDate == null) {
			return;
		}
		ResourceRate preInRate = new ResourceRate(this.getSession());
		preInRate.setEffectiveDate(new BeginDate(preDay(inDate)));
		preInRate.setMaxUnitsPerTime(new UnitsPerTime(0));
		preInRate.setPricePerUnit(new Cost(0));
		preInRate.setResourceObjectId(rscId);
		preInRate.create();
		
		ResourceRate inRate = new ResourceRate(this.getSession());
		inRate.setEffectiveDate(new BeginDate(inDate));
		inRate.setMaxUnitsPerTime(new UnitsPerTime(1));
		inRate.setPricePerUnit(new Cost(1));
		inRate.setResourceObjectId(rscId);
		inRate.create();
	}
	
	/**
	 * 给指定资源设定离职日期
	 * 	增加一个离职日期0%的“ResourceRate”
	 * @param rscId ObjectId
	 * @param outDate Date
	 * @throws Exception
	 */
	private void setOutDate(ObjectId rscId, Date outDate) throws Exception {
		if(outDate == null) {
			return;
		}
		//离职日期+1
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(outDate);
		Date outNextDate = WorkCalendar.getNextDay(c).getTime();
		
		ResourceRate outRate = new ResourceRate(this.getSession());
		outRate.setEffectiveDate(new BeginDate(outNextDate));
		outRate.setMaxUnitsPerTime(new UnitsPerTime(0));
		outRate.setPricePerUnit(new Cost(0));
		outRate.setResourceObjectId(rscId);
		outRate.create();
	}
	
	/**
	 * 清除已存在的ResourceRate
	 * 应对新增资源时，系统自动新增一个本月处的ResourceRate
	 */
	private void cleardResourceRate(Resource rsc) {
		BOIterator<ResourceRate> iter;
		try{
			iter = rsc.loadResourceRates(new String[] {"ObjectId"}, "", "");
			while(iter.hasNext()) {
				iter.next().delete();
			}
		} catch (Exception e) {
			log.error(e);
		}
	}
	
	/**
	 * 确认指定资源是否已经维护相同的入职日期
	 *  存在一个入职日期的“ResourceRate”
	 *  且一个入职日期前一天的“ResourceRate”
	 * @param rsc Resource
	 * @param inDate Date
	 * @return boolean
	 */
	private boolean inDateExist(Resource rsc, Date inDate) {
		if(inDate == null) {
			return false;
		}
		String where = "EffectiveDate = " + WhereClauseHelper.formatDate(preDay(inDate))
						+ " or EffectiveDate = " + WhereClauseHelper.formatDate(inDate);
		BOIterator iter;
		try{
			iter = rsc.loadResourceRates(new String[] {"ObjectId"}, where, "");
			if(iter.getCount() >= 2) {
				return true;
			}
		} catch (Exception e) {
			log.error(e);
			return false;
		}
		return false;
	}
	
	/**
	 * 确认指定资源是否已经维护相同的离职日期
	 *  存在一个离职日期的“ResourceRate”
	 * @param rsc Resource
	 * @param outDate Date
	 * @return boolean
	 */
	private boolean outDateExist(Resource rsc, Date outDate) {
		if(outDate == null) {
			return false;
		}
		String where = "EffectiveDate = " + WhereClauseHelper.formatDate(outDate);
		BOIterator iter;
		try{
			iter = rsc.loadResourceRates(new String[] {"ObjectId"}, where, "");
			if(iter.hasNext()) {
				return true;
			}
		} catch (Exception e) {
			log.error(e);
			return false;
		}
		return false;
	}
	
	/**
	 * 获取指定日期的前一天
	 * @param date Date
	 * @return Date
	 */
	private static Date preDay(Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return WorkCalendar.getNextDay(c, -1).getTime();
	}

	/**
	 * 按Rank设置Resource的资源分类码
	 * @param rscId
	 * @param rank
	 * @return boolean 是否分配成功
	 */
	private boolean setResourceCodeAssignment(ObjectId rscId, String rank) {
		ObjectId codeId = this.getResourceCodeObjectId(rank);
		if(codeId == null) {
			return false;
		}
		if(rank ==null || "".equals(rank)) {
			return true;
		}
		ResourceCodeAssignment codeAssignment =
			new ResourceCodeAssignment(this.getSession());
		try {
			codeAssignment.setResourceCodeObjectId(codeId);
			codeAssignment.setResourceObjectId(rscId);
			codeAssignment.create();
		} catch (Exception e) {
			log.warn(e);
			return false;
		}
		return true;
	}
	
	private boolean ResourceCodeAssignmentExist(Resource rsc, String rank) {
		if(rank == null || "".equals(rank)) {
			return false;
		}
		try {
			String where = "ResourceObjectId = '" + rsc.getObjectId() + "'"
				+ " and ResourceCodeValue = '" + rank + "'";
			BOIterator iter = rsc.loadResourceCodeAssignments(new String[]{"ResourceObjectId"}, where, "");
			return iter.hasNext();
		} catch (Exception e) {
			log.error(e);
		}
		return false;
	}
	
	/**
	 * 根据codeValue获取ResourceCode的ObjectId (CodeType为jobCodeType)
	 * @param codeValue
	 * @return ResourceCode ObjectId
	 */
	private ObjectId getResourceCodeObjectId(String codeValue) {
		if(codeValue == null || "".equals(codeValue)) {
			return null;
		}
		String where = "CodeValue = '" + codeValue + "'";
		BOIterator iter;
		try {
			iter = getResourceCodeType().loadAllResourceCodes(new String[]{"ObjectId"}, where, "");
			if(iter.hasNext()) {
				return ((ResourceCode) iter.next()).getObjectId();
			}
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}
	
	/**
	 * 获取jobCodeType
	 * @return ResourceCodeType
	 */
	private ResourceCodeType getResourceCodeType() {
		if(resourceCodeType == null) {
			resourceCodeType = this.loadResourceCodeType();
		}
		return resourceCodeType;
	}
	
	/**
	 * 第一次获取jobCodeType
	 * @return ResourceCodeType
	 */
	private ResourceCodeType loadResourceCodeType() {
		String where = "Name = '" + this.jobCodeType + "'";
		BOIterator iter;
		try {
			iter = this.getGOM().loadResourceCodeTypes(new String[]{"ObjectId"}, where, "");
			if(iter.hasNext()) {
				ResourceCodeType type = (ResourceCodeType) iter.next();
				return type;
			}
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}
	
	/**
	 * 根据工号获取Resource
	 * @param id 工号
	 * @return Resource
	 */
	private Resource getResourceById(String id) {
		String where = " Id = '" + id + "'"; 
		BOIterator iter;
		try {
			iter = this.getGOM().loadResources(Resource.getAllFields(), where, null);
			if(iter.hasNext()) {
				return (Resource) iter.next();
			}
		} catch (Exception e) {
			throw new BusinessException("error.logic.PrimaveraApiBase.getSessionError",
									    "login primavera integration api error", e);
		}
		return null;
	}
	
	/**
	 * 根据工号获取User ObjectId
	 * @param loginId
	 * @return ObjectId
	 * @throws Exception
	 */
	private ObjectId getUserId(String loginId) throws Exception {
		if(loginId == null || "".equals(loginId.trim())) {
			return null;
		}
		User u = this.getUser(loginId);
		if(u == null) {
			return null;
		} else {
			return u.getObjectId();
		}
	}
	
	/**
	 * 为人员资料增加一个User
	 * @param hbLog
	 * @return
	 * @throws Exception
	 */
	public ObjectId addUser(HrbHumanBaseLog hbLog) throws Exception {
		User user = new User(this.getSession());
		user.setName(hbLog.getEmployeeId());
		user.setPersonalName(hbLog.getFullName());
		user.setEmailAddress(hbLog.getEmail());
		return user.create();
	}
	
	/**
	 * 根据资源所属Site解析资源日历ObjectId
	 * @param site
	 * @return ObjectId
	 */
	private ObjectId getCalendarId(String site) {
		if(site == null || "".equals(site)) {
			return null;
		}
		String cName = getCalendarName(site);
		String where = " Name = '" + cName + "'"; 
		BOIterator iter;
		try {
			iter = this.getGOM().loadCalendars(new String[]{"ObjectId"}, where, null);
			if(iter.hasNext()) {
				return ((Calendar) iter.next()).getObjectId();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据资源所属Site解析货币ObjectId
	 * @param site
	 * @return ObjectId
	 */
	private ObjectId getCurrencyId(String site) {
		if(site == null || "".equals(site)) {
			return null;
		}
		String cName = getCurrencyName(site);
		String where = " Id = '" + cName + "'"; 
		BOIterator iter;
		try {
			iter = this.getGOM().loadCurrencies(new String[]{"ObjectId"}, where, null);
			if(iter.hasNext()) {
				return ((Currency) iter.next()).getObjectId();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String getCalendarName(String site) {
		return site2CalendarMap.get(site.toUpperCase());
	}
	
	private String getCurrencyName(String site) {
		return site2CurrencyMap.get(site.toUpperCase());
	}
	
	public void setJobCodeType(String jobCodeType) {
		this.jobCodeType = jobCodeType;
	}

	public void setSite2CalendarMap(Map site2CalendarMap) {
		this.site2CalendarMap = site2CalendarMap;
	}

	public void setSite2CurrencyMap(Map site2CurrencyMap) {
		this.site2CurrencyMap = site2CurrencyMap;
	}
	
	static {
		site2CalendarMap.put("WH", "WITS-CN");
		site2CalendarMap.put("DL", "WITS-CN");
		site2CalendarMap.put("BJ", "WITS-CN");
		site2CalendarMap.put("GZ", "WITS-CN");
		site2CalendarMap.put("ZH", "WITS-ZH");
		site2CalendarMap.put("TP", "WITS-TW");
		
		site2CurrencyMap.put("WH", "RMB");
		site2CurrencyMap.put("DL", "RMB");
		site2CurrencyMap.put("BJ", "RMB");
		site2CurrencyMap.put("GZ", "RMB");
		site2CurrencyMap.put("ZH", "RMB");
		site2CurrencyMap.put("TP", "NTD");
	}
	
	public static void main(String[] args) {
		SyncPrimaveraDaoImp imp = new SyncPrimaveraDaoImp();
		HrbHumanBaseLog hbLog = new HrbHumanBaseLog();
		hbLog.setEmployeeId("WH00Test_5");
		hbLog.setUnitCode("W3501");
		hbLog.setEnglishName("TestName_5");
		hbLog.setChineseName("测试名");
		hbLog.setEmail("test@wistronits.com");
		hbLog.setTitle("助理工程师");
		hbLog.setInDate(comDate.toDate("2007-10-21"));
		hbLog.setSite("zH");
		hbLog.setRank("E05");
		hbLog.setResManagerId("WH00Test_2");
		String remark = imp.updateResource(hbLog);
		imp.getSession().logout();
		System.out.println("Compelet: " + remark);
	}
}
