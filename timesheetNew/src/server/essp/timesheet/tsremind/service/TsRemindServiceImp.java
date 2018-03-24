package server.essp.timesheet.tsremind.service;

import java.util.*;

import server.essp.common.ldap.LDAPUtil;
import server.essp.common.mail.ContentBean;
import server.essp.common.mail.SendHastenMail;
import server.essp.timesheet.activity.resources.dao.IResourceDao;
import server.essp.timesheet.preference.dao.IPreferenceDao;
import server.essp.timesheet.report.timesheetStatus.service.ITimesheetStausService;
import server.essp.timesheet.rmmaint.service.IRmMaintService;
import server.essp.timesheet.weeklyreport.dao.ITimeSheetDao;
import c2s.essp.common.user.DtoUserInfo;
import c2s.essp.timesheet.approval.DtoTsApproval;
import c2s.essp.timesheet.preference.DtoPreference;
import c2s.essp.timesheet.report.DtoTimesheetStatus;
import c2s.essp.timesheet.report.DtoTsStatusQuery;
import c2s.essp.timesheet.tsremind.DtoRemindMail;
import c2s.essp.timesheet.tsremind.DtoRmMailContent;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheet;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheetPeriod;

import com.wits.util.comDate;

import db.essp.timesheet.TsParameter;

public class TsRemindServiceImp implements ITsRemindService {
	
	private IPreferenceDao preferenceDao;
	private ITimeSheetDao timeSheetDao;
	private IRmMaintService rmMaintService;
	private ITimesheetStausService timesheetStausService;
	private IResourceDao iresDao;
	private static final String vmFile = "mail/template/ts/RemindFillMailTemplate.htm";
	private static final String vmFile1 = "mail/template/ts/RemindPmApprove.htm";
	private static final String vmFile2 = "mail/template/ts/RemindRmApprove.htm";
	private static final String MAIL_TYPE_UFS = "UFS";
	private static final String MAIL_TYPE_UF = "UF";
	private static final String MAIL_TYPE_US = "US";
	private static final String MAIL_TYPE_PM = "PM";
	private static final String MAIL_TYPE_RM = "RM";
	private List<DtoTimeSheetPeriod> periodList;
	public void setTimeSheetDao(ITimeSheetDao timeSheetDao) {
		this.timeSheetDao = timeSheetDao;
	}
	public void setPreferenceDao(IPreferenceDao preferenceDao) {
		this.preferenceDao = preferenceDao;
	}
	/**
	 * 发送邮件
	 * @param vmFile
	 * @param title
	 * @param hm
	 */
	private void sendMail(final String vmFile, final String title, final HashMap hm) {
		final SendHastenMail shMail = new SendHastenMail();
        shMail.sendHastenMail(vmFile, title, hm);
	}
	/**
	 * 生成邮件标题
	 * @param begin
	 * @param end
	 * @param type
	 * @return
	 */
	private String genTitle(Date begin, Date end, String type) {
		String title = "";
		String date = "("+comDate.dateToString(begin, "yyyy/MM/dd")+"-"
        				+comDate.dateToString(end, "yyyy/MM/dd")+")";
		if(MAIL_TYPE_PM.equals(type) || MAIL_TYPE_RM.equals(type)) {
			 title = " Timecard to be Approved by "+type;
		} else if(MAIL_TYPE_UF.equals(type) || MAIL_TYPE_US.equals(type)
			   || MAIL_TYPE_UFS.equals(type)) {
			 title = " Timecard to be Submitted";
		}
		return date + title;
	}
	/**
	 * 查询没有提交或没有审核的周报并发邮件给相关人员
	 */
	public void queryDataAndSendMail(Date begin, Date end, String mailType, 
			String site, List periodListArg) {
		periodList = periodListArg;//工时单区间列表
		if(mailType.equals(MAIL_TYPE_UFS)) {
			final HashMap hmUnFS = searchUnFS(begin, end, site);
			sendMail(vmFile, genTitle(begin, end, MAIL_TYPE_UFS), hmUnFS);
		}
//		if(mailType.equals(MAIL_TYPE_UF)){
//			final HashMap hmUnFilled = sendMailForUnFilled(begin, end, site);
//			sendMail(vmFile, genTitle(begin, end, MAIL_TYPE_UF), hmUnFilled);
//		}
//		if(mailType.equals(MAIL_TYPE_US)) {
//			final HashMap hmNotSubmit = sendMailForNotSubmit(begin, end, site);
//			sendMail(vmFile1, genTitle(begin, end, MAIL_TYPE_US), hmNotSubmit);
//		}
		if(mailType.equals(MAIL_TYPE_PM)) {
			mail2Pm(begin, end, site);
		}
		if(mailType.equals(MAIL_TYPE_RM)) {
			mail2Rm(begin, end, site);
		}
	}
	/**
	 * 发送催促邮件给PM
	 * @param begin
	 * @param end
	 * @param site
	 */
	private void mail2Pm(Date begin, Date end, String site) {
		TsParameter tsParameter = preferenceDao.loadPreferenceBySite(site);
		String approvalLevel = tsParameter.getTsApprovalLevel();
		if(DtoPreference.APPROVAL_LEVEL_PM.equals(approvalLevel)) {
			//PM单独审核
			HashMap hm = sendMailForPmApproval("('"+DtoTimeSheet.STATUS_SUBMITTED+"')", begin, end, site);
			sendMail(vmFile1, genTitle(begin, end, MAIL_TYPE_PM), hm);
		} else if(DtoPreference.APPROVAL_LEVEL_PM_BEFORE_RM.equals(approvalLevel)){
			//PM在RM审核前审核
			HashMap hmPm = sendMailForPmApproval("('"+DtoTimeSheet.STATUS_SUBMITTED+"')", begin, end, site);
			sendMail(vmFile1, genTitle(begin, end, MAIL_TYPE_PM), hmPm);
		} else if(DtoPreference.APPROVAL_LEVEL_PM_AND_RM.equals(approvalLevel)){
			//PM和RM部分先后审核
			HashMap hmPm = sendMailForPmApproval("('"+DtoTimeSheet.STATUS_SUBMITTED+"', '"+DtoTimeSheet.STATUS_RM_APPROVED+"')",
												 begin, end, site);
			sendMail(vmFile1, genTitle(begin, end, MAIL_TYPE_PM), hmPm);
		} 
	}
	/**
	 * 发送催促邮件给RM
	 * @param begin
	 * @param end
	 * @param site
	 */
	private void mail2Rm(Date begin, Date end, String site) {
		TsParameter tsParameter = preferenceDao.loadPreferenceBySite(site);
		String approvalLevel = tsParameter.getTsApprovalLevel();
		if(DtoPreference.APPROVAL_LEVEL_RM.equals(approvalLevel)){
			//RM单独审核
			HashMap hm = sendMailForRmApproval("('"+DtoTimeSheet.STATUS_SUBMITTED+"')", begin, end, site);
			sendMail(vmFile2, genTitle(begin, end, MAIL_TYPE_RM), hm);
		} else if(DtoPreference.APPROVAL_LEVEL_PM_BEFORE_RM.equals(approvalLevel)){
			//PM在RM审核前审核
			HashMap hmRm = sendMailForRmApproval("('"+DtoTimeSheet.STATUS_PM_APPROVED+"')", begin, end, site);
			sendMail(vmFile2, genTitle(begin, end, MAIL_TYPE_RM), hmRm);
		} else if(DtoPreference.APPROVAL_LEVEL_PM_AND_RM.equals(approvalLevel)){
			//PM和RM部分先后审核
			HashMap hmRm = sendMailForRmApproval("('"+DtoTimeSheet.STATUS_SUBMITTED+"', '"+DtoTimeSheet.STATUS_PM_APPROVED+"')",
											     begin, end, site);
			sendMail(vmFile2, genTitle(begin, end, MAIL_TYPE_RM), hmRm);
		} 
	}
	/**
	 * 查询没有填写或没有提交工时单的人
	 * @param begin
	 * @param end
	 * @param site
	 * @return
	 */
	private HashMap searchUnFS(Date begin, Date end, String site) {
		List<DtoTimesheetStatus> list = null;
		Map<String, List> humanMap = new HashMap<String, List>();
		List<DtoTimeSheetPeriod> timeList = null;
		List<String> personList = null;
		
		String status = "('"+DtoTimeSheet.STATUS_ACTIVE+"', '"+DtoTimeSheet.STATUS_REJECTED+"')";
		
		List<String> unSubList = null;
		DtoTimeSheetPeriod dtoView = null;
		try {
			//根据工时区间列表获得在区间内没有填写工时单的人员
			//查询没有提交工时单的人员
			DtoTsStatusQuery dtoQuery = null;
			for(DtoTimeSheetPeriod dto : periodList) {
				dtoQuery = new DtoTsStatusQuery();
				dtoQuery.setBeginDate(dto.getBeginDate());
				dtoQuery.setEndDate(dto.getEndDate());
				dtoQuery.setIsDeptQuery(false);
				List pList = new ArrayList();
				pList.add(dto);
				timesheetStausService.initMap();
				dtoQuery.setSite(site);
				list = timesheetStausService.getUnfilled(pList, dtoQuery, null);
				unSubList = timeSheetDao.getPersonForRemindMail(status, dto.getBeginDate(), 
						                                        dto.getEndDate(), site);
				//记录没有填写的人员
				if(list != null && list.size() > 0) {
					String employeeId = "";
					for(DtoTimesheetStatus dtoData : list) {
						//如果不需填写则不提醒
						if(DtoTsStatusQuery.DTO_ISFILL_NO
								.equals(dtoData.getIsFillTimesheet())) {
							continue;
						}
						employeeId = dtoData.getEmpId();
						dtoView = new DtoTimeSheetPeriod();
						dtoView.setOp("UnFilled");
						dtoView.setBeginDate(dto.getBeginDate());
						dtoView.setEndDate(dto.getEndDate());
						if(!humanMap.containsKey(employeeId)) {
							timeList = new ArrayList<DtoTimeSheetPeriod>();
							timeList.add(dtoView);
							humanMap.put(employeeId, timeList);
						} else {
							timeList = humanMap.get(employeeId);
							timeList.add(dtoView);
						}
					}
				}
				//记录没有提交的人
				if(unSubList != null && unSubList.size() > 0) {
					for(String employeeId : unSubList) {
						if(!iresDao.isFillTimesheet(employeeId)) {
							continue;
						}
						dtoView = new DtoTimeSheetPeriod();
						dtoView.setOp("UnSubmitted");
						dtoView.setBeginDate(dto.getBeginDate());
						dtoView.setEndDate(dto.getEndDate());
						if(!humanMap.containsKey(employeeId)) {
							timeList = new ArrayList<DtoTimeSheetPeriod>();
							timeList.add(dtoView);
							humanMap.put(employeeId, timeList);
						} else {
							timeList = humanMap.get(employeeId);
							timeList.add(dtoView);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		personList = new ArrayList(humanMap.keySet());
		HashMap hm = new HashMap();
		if(personList == null || personList.size() == 0) {
			return hm;
		}
		DtoUserInfo dtoUser = null;
		ArrayList al = null;
		ContentBean cb = null;
		String userMail = null;
		String userName = null;
		DtoRemindMail dtoRemindMail = null;
		LDAPUtil ldapUtil = new LDAPUtil();
		for (String employeeId : personList) {
			al = new ArrayList();
			cb = new ContentBean();
			if(!humanMap.containsKey(employeeId)) {
				continue;
			}
			timeList = humanMap.get(employeeId);
			for(DtoTimeSheetPeriod dto : timeList) {
				dtoRemindMail = new DtoRemindMail();
				dtoRemindMail.setBeginDate(comDate.dateToString(dto.getBeginDate()));
				dtoRemindMail.setEndDate(comDate.dateToString(dto.getEndDate()));
				dtoRemindMail.setType(dto.getOp());
				al.add(dtoRemindMail);
			}
			
			dtoUser = ldapUtil.findUser(LDAPUtil.DOMAIN_ALL, employeeId);
			if (dtoUser != null) {
				userMail = dtoUser.getEmail();
				userName = dtoUser.getUserName();
				cb.setUser(userName);
				cb.setEmail(userMail);
				cb.setMailcontent(al);
				hm.put(userName, cb);
			}
		}
		return hm;
	}

	/**
	 * 查询没有审核的周报和相关PM的信息
	 * @param status
	 * @return
	 */
	private HashMap sendMailForPmApproval(String status, Date begin, Date end, String site) {
		List<String> list = null;
		List<String> listAll = new ArrayList<String>();
		Map personMap = new HashMap();
		for(DtoTimeSheetPeriod dto : periodList) {
			list = timeSheetDao.getPmForRemindMail(status, dto.getBeginDate(), dto.getEndDate());
			listAll.addAll(list);
		}
		for(String loginId : listAll) {
			personMap.put(loginId, loginId);
		}
		List<String> personList = new ArrayList(personMap.keySet());
		HashMap hm = new HashMap();
		if(personList == null || personList.size() == 0) {
			return hm;
		}
		
		LDAPUtil ldapUtil = new LDAPUtil();
		DtoUserInfo dtoUser = null;
		ArrayList al = null;
		ContentBean cb = null;
		String userMail = null;
		String userName = null;
		DtoRemindMail dtoRemindMail = null;
		List<Object[]> objList = null;
		for(String loginId : personList) {
			al = new ArrayList();
			cb = new ContentBean();
			for(DtoTimeSheetPeriod dto : periodList) {
				objList = timeSheetDao.getDataForRemindPmMail(status, loginId, 
						dto.getBeginDate(), dto.getEndDate(), site);
				if(objList == null || objList.size() ==0) {
					continue;
				}
				for(Object[] obj : objList) {
					dtoRemindMail = setValues(obj);
					al.add(dtoRemindMail);
				}
			}
			//没有邮件内容则不发
			if(al.size() == 0) {
				continue;
			}
			dtoUser = ldapUtil.findUser(LDAPUtil.DOMAIN_ALL, loginId);
			if (dtoUser != null) {
                userMail = dtoUser.getEmail();
                userName = dtoUser.getUserName();
                cb.setUser(userName);
                cb.setEmail(userMail);
                cb.setMailcontent(al);
                hm.put(userName, cb);
			}
		}
		return hm;
	}
	/**
	 * 
	 * @param unFillList
	 * @param unSubmitList
	 * @param rmMap
	 * @param site
	 */
	private void genUnListAndRm(Map rmMap, Map unFillMap, Map unSubmitMap, String site) {
		List<DtoTimesheetStatus> list = null;
		
		String status = "('"+DtoTimeSheet.STATUS_ACTIVE+"', '"+DtoTimeSheet.STATUS_REJECTED+"')";
		
		List<Object[]> unSubList = null;
		DtoRemindMail dtoRemindMail = null;
		Map tempMap = null;
		ArrayList unFillList = null;
		ArrayList unSubmitList = null;
		String rmId = "";
		try {
			//根据工时区间列表获得在区间内没有填写工时单的人员
			//查询没有提交工时单的人员
			DtoTsStatusQuery dtoQuery = null;
			for(DtoTimeSheetPeriod dto : periodList) {
				dtoQuery = new DtoTsStatusQuery();
				dtoQuery.setBeginDate(dto.getBeginDate());
				dtoQuery.setEndDate(dto.getEndDate());
				dtoQuery.setIsDeptQuery(false);
				List pList = new ArrayList();
				pList.add(dto);
				timesheetStausService.initMap();
				dtoQuery.setSite(site);
				list = timesheetStausService.getUnfilled(pList, dtoQuery, null);
				unSubList = timeSheetDao.getPersonAcntForRemindMail(status, dto.getBeginDate(), 
						                                        dto.getEndDate(), site);
				//记录没有填写的人员
				if(list != null && list.size() > 0) {
					for(DtoTimesheetStatus dtoData : list) {
						//如果不需填写则不提醒
						if(DtoTsStatusQuery.DTO_ISFILL_NO
								.equals(dtoData.getIsFillTimesheet())) {
							continue;
						}
						String employeeId = dtoData.getEmpId();
						dtoRemindMail = new DtoRemindMail();
						dtoRemindMail.setEmployeeId(employeeId);
						dtoRemindMail.setSubmiter(dtoData.getEmpName());
						dtoRemindMail.setBeginDate(comDate.dateToString(dto.getBeginDate()));
						dtoRemindMail.setEndDate(comDate.dateToString(dto.getEndDate()));
						rmId = rmMaintService.getRmByLoginId(employeeId);
						if(!"".equals(rmId)) {
							if(rmMap.containsKey(rmId)) {
								tempMap = (Map) rmMap.get(rmId);
								tempMap.put(employeeId, employeeId);
								rmMap.put(rmId, tempMap);
							} else {
								tempMap = new HashMap();
								tempMap.put(employeeId, employeeId);
								rmMap.put(rmId, tempMap);
								
							}
							if(unFillMap.containsKey(rmId)) {
								unFillList = (ArrayList)unFillMap.get(rmId);
								unFillList.add(dtoRemindMail);
								unFillMap.put(rmId, unFillList);
							} else {
								unFillList = new ArrayList();
								unFillList.add(dtoRemindMail);
								unFillMap.put(rmId, unFillList);
							}
						}
					}
				}
				//记录没有提交的人
				if(unSubList != null && unSubList.size() > 0) {
					for(Object[] obj : unSubList) {
						if(!iresDao.isFillTimesheet((String)obj[0])) {
							continue;
						}
						dtoRemindMail = new DtoRemindMail();
						dtoRemindMail.setEmployeeId((String)obj[0]);
						dtoRemindMail.setSubmiter(rmMaintService.getUserName((String)obj[0]));
						dtoRemindMail.setBeginDate(comDate.dateToString(dto.getBeginDate()));
						dtoRemindMail.setEndDate(comDate.dateToString(dto.getEndDate()));
						dtoRemindMail.setAccountInfo((String)obj[1]+"--"+(String)obj[2]);
						rmId = rmMaintService.getRmByLoginId((String)obj[0]);
						if(!"".equals(rmId)) {
							if(rmMap.containsKey(rmId)) {
								tempMap = (Map) rmMap.get(rmId);
								tempMap.put((String)obj[0], (String)obj[0]);
								rmMap.put(rmId, tempMap);
							} else {
								tempMap = new HashMap();
								tempMap.put((String)obj[0], (String)obj[0]);
								rmMap.put(rmId, tempMap);
							}
							if(unSubmitMap.containsKey(rmId)) {
								unSubmitList = (ArrayList) unSubmitMap.get(rmId);
								unSubmitList.add(dtoRemindMail);
								unSubmitMap.put(rmId, unSubmitList);
							} else {
								unSubmitList = new ArrayList();
								unSubmitList.add(dtoRemindMail);
								unSubmitMap.put(rmId, unSubmitList);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 查询没有审核的周报和相关RM的信息
	 * @param status
	 * @return
	 */
	private HashMap sendMailForRmApproval(String status, Date begin, Date end, String site) {
		List<String> list = null;
		List<String> listAll = new ArrayList<String>();
		for(DtoTimeSheetPeriod dto : periodList) {
			list = timeSheetDao.getPersonForRemindMail(status, dto.getBeginDate(), 
						                                   dto.getEndDate(), site);
			listAll.addAll(list);
		}
		Map rmMap = new HashMap();
		Map rmAppMap = new HashMap();
		Map tempMap = null;
		String rmIdApp = "";
		for(String loginId : listAll) {
			rmIdApp = rmMaintService.getRmByLoginId(loginId);
			if(!"".equals(rmIdApp)) {
				if(rmMap.containsKey(rmIdApp)) {
					tempMap = (Map) rmMap.get(rmIdApp);
					tempMap.put(loginId, loginId);
					rmMap.put(rmIdApp, tempMap);
					tempMap = (Map) rmAppMap.get(rmIdApp);
					tempMap.put(loginId, loginId);
					rmAppMap.put(rmIdApp, tempMap);
				} else {
					tempMap = new HashMap();
					tempMap.put(loginId, loginId);
					rmMap.put(rmIdApp, tempMap);
					tempMap = new HashMap();
					tempMap.put(loginId, loginId);
					rmAppMap.put(rmIdApp, tempMap);
				}
			}
		}
		
		Map unFillMap = new HashMap();
		Map unSbumitMap = new HashMap();
		ArrayList unFillList = new ArrayList();
		ArrayList unSubmitList = new ArrayList();
		genUnListAndRm(rmMap, unFillMap, unSbumitMap, site);
		
		List<String> rmList = new ArrayList<String>(rmMap.keySet());
		HashMap hm = new HashMap();
		LDAPUtil ldapUtil = new LDAPUtil();
		DtoUserInfo dtoUser = null;
		ArrayList al = null;
		
		ArrayList approveList = null;
		DtoRmMailContent dtoRmMailContent = null;
		ContentBean cb = null;
		String userMail = null;
		String userName = null;
		DtoRemindMail dtoRemindMail = null;
		List<Object[]> objList = null;
		List<String> personList = null;
		for(String rmId : rmList) {
//			if(!iresDao.isFillTimesheet(rmId)) {
//				continue;
//			}
			dtoRmMailContent = new DtoRmMailContent();
			al = new ArrayList();
			cb = new ContentBean();
			if(rmAppMap.containsKey(rmId)){
				personList = new ArrayList(((Map)rmAppMap.get(rmId)).keySet()) ;
			} else {
				personList = new ArrayList();
			}
			if(unFillMap.containsKey(rmId)) {
				unFillList = (ArrayList) unFillMap.get(rmId);
			} else {
				unFillList = new ArrayList();
			}
			if(unSbumitMap.containsKey(rmId)) {
				unSubmitList = (ArrayList) unSbumitMap.get(rmId);
			} else {
				unSubmitList = new ArrayList();
			}
		
			approveList = new ArrayList();
			if (personList != null && personList.size() > 0) {
				for (String loginId : personList) {
					for (DtoTimeSheetPeriod dto : periodList) {
						objList = timeSheetDao.getDataForRemindRmMail(status,
								loginId, dto.getBeginDate(), dto.getEndDate());
						if (objList == null || objList.size() == 0) {
							continue;
						}
						for (Object[] obj : objList) {
							dtoRemindMail = setValuesForRm(obj);
							approveList.add(dtoRemindMail);
						}
					}
				}
			}
			dtoRmMailContent.setUnFillList(unFillList);
			dtoRmMailContent.setUnSubmitList(unSubmitList);
			dtoRmMailContent.setApproveList(approveList);
			al.add(dtoRmMailContent);
			
			dtoUser = ldapUtil.findUser(LDAPUtil.DOMAIN_ALL, rmId);
			if (dtoUser != null) {
                userMail = dtoUser.getEmail();
                userName = dtoUser.getUserName();
                cb.setUser(userName);
                cb.setEmail(userMail);
                cb.setMailcontent(al);
                hm.put(userName, cb);
			}
		}
		return hm;
	}
	/**
	 * 设置邮件内容(RM使用)
	 * @param ldapUtil
	 * @param obj
	 * @return
	 */
	private DtoRemindMail setValuesForRm(Object[] obj) {
		DtoRemindMail dtoRemindMail = new DtoRemindMail();
		dtoRemindMail.setBeginDate(comDate.dateToString((Date)obj[1]));
		dtoRemindMail.setEndDate(comDate.dateToString((Date)obj[2]));
		//如果是查询未审核的周报
		dtoRemindMail.setType("Not Confirmed");
		dtoRemindMail.setAccountInfo((String)obj[3]+"--"+(String)obj[4]);
		dtoRemindMail.setSubmiter(rmMaintService.getUserName((String)obj[0]));
		dtoRemindMail.setEmployeeId((String)obj[0]);
		dtoRemindMail.setPmId((String)obj[5]);
		dtoRemindMail.setPmName(rmMaintService.getUserName((String)obj[5]));
		return dtoRemindMail;
	}
	/**
	 * 设置邮件内容
	 * @param ldapUtil
	 * @param obj
	 * @return
	 */
	private DtoRemindMail setValues(Object[] obj) {
		DtoRemindMail dtoRemindMail = new DtoRemindMail();
		dtoRemindMail.setBeginDate(comDate.dateToString((Date)obj[6]));
		dtoRemindMail.setEndDate(comDate.dateToString((Date)obj[7]));
		String status = (String)obj[5];
		if(DtoTsApproval.STATUS_ACTIVE.equals(status)) {
			dtoRemindMail.setType("Active");
		} else if(DtoTsApproval.STATUS_REJECTED.equals(status)){
			dtoRemindMail.setType("Rejected");
		}
		//如果是查询未审核的周报
		dtoRemindMail.setAccountInfo((String)obj[3]+"--"+(String)obj[4]);
		dtoRemindMail.setEmployeeId((String)obj[0]);
		dtoRemindMail.setSubmiter(rmMaintService.getUserName((String)obj[0]));
		
		return dtoRemindMail;
	}

	public void setRmMaintService(IRmMaintService rmMaintService) {
		this.rmMaintService = rmMaintService;
	}
	public void setIresDao(IResourceDao iresDao) {
		this.iresDao = iresDao;
	}
	public void setTimesheetStausService(
			ITimesheetStausService timesheetStausService) {
		this.timesheetStausService = timesheetStausService;
	}
	
}
