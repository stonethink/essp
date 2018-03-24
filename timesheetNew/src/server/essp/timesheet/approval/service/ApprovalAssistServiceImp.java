package server.essp.timesheet.approval.service;

import java.util.*;

import server.essp.common.ldap.LDAPUtil;
import server.essp.common.mail.ContentBean;
import server.essp.common.mail.SendHastenMail;
import server.essp.timesheet.account.dao.IAccountDao;
import server.essp.timesheet.calendar.dao.ICalendarP3Dao;
import server.essp.timesheet.rmmaint.service.IRmMaintService;
import server.essp.timesheet.weeklyreport.dao.ITimeSheetDao;
import server.essp.timesheet.weeklyreport.dao.ITimeSheetP3DbDao;
import server.framework.common.BusinessException;
import c2s.essp.common.user.DtoUser;
import c2s.essp.common.user.DtoUserInfo;
import c2s.essp.timesheet.approval.DtoApprovalMail;
import c2s.essp.timesheet.approval.DtoTsApproval;
import c2s.essp.timesheet.preference.DtoPreference;
import c2s.essp.timesheet.weeklyreport.*;

import com.wits.util.comDate;

import db.essp.timesheet.*;

/**
 * <p>Title: ApprovalAssistServiceImp</p>
 *
 * <p>Description: 进行审核功能时的一些辅助业务操作</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author wenhaizheng
 * @version 1.0
 */
public class ApprovalAssistServiceImp implements IApprovalAssistService{
    private ITimeSheetDao timeSheetDao;
    private ITimeSheetP3DbDao timeSheetP3DbDao;
    private ICalendarP3Dao calendarP3Dao;
    private IAccountDao accountDao;
    private IRmMaintService rmMaintService;
    
    public void setRmMaintService(IRmMaintService rmMaintService) {
		this.rmMaintService = rmMaintService;
	}

	/**
     * 设置P3中timesheet的状态
     */
    public void setTimesheetStatusInP3(TsTimesheetMaster tsMaster, String status) {
    	if(!checkNeedToWrite(tsMaster.getRid())) {
            return;
        }
    	Long userId = timeSheetP3DbDao.getUserId(tsMaster.getLoginId());
        Long rsrcId = timeSheetP3DbDao.getRsrcId(userId);
        Long tsId = tsMaster.getTsId();
        DtoTimeSht dtoTimesht = new DtoTimeSht();
        dtoTimesht.setTsId(tsId);
        dtoTimesht.setRsrcId(rsrcId);
        timeSheetP3DbDao.updateTimeSheetStatus(dtoTimesht, status);
	}
    
    /**
     * 将审核后的工时单数据回写到P3中
     *   1.通过tsId和rsrc_id删除RSRCHOUR下资料,rsrc_id通过user_id到RSRC中取，
     *     user_id从USER表通过loginId获取
     *   2.根据tsId和rsrc_id删除TIMESHT资料
     *   3.新增TIMESHT资料
     *   4.新增RSRCHOUR资料
     *      循环tsDay资料新增
     *      通过调用方法新增rsrc_hr_id
     *      根据task_id(activity_id)和rsrc_id到TASKRSRC表中查询taskrsrc_id
     *
     * @param tsRid Long
     */
    public void writeBackToP3(Long tsRid) {
    	if(tsRid == null) return;
    	TsTimesheetMaster master = timeSheetDao.getTimeSheetMaster(tsRid);
    	if(master == null) return;
    	writeBackToP3(master);
    	
    }
    /**
     * 将审核后的工时单数据回写到P3中
     *   1.通过tsId和rsrc_id删除RSRCHOUR下资料,rsrc_id通过user_id到RSRC中取，
     *     user_id从USER表通过loginId获取
     *   2.根据tsId和rsrc_id删除TIMESHT资料
     *   3.新增TIMESHT资料
     *   4.新增RSRCHOUR资料
     *      循环tsDay资料新增
     *      通过调用方法新增rsrc_hr_id
     *      根据task_id(activity_id)和rsrc_id到TASKRSRC表中查询taskrsrc_id
     *
     * @param tsMaster TsTimesheetMaster
     */
    public void writeBackToP3(TsTimesheetMaster tsMaster) {
    	Long userId = timeSheetP3DbDao.getUserId(tsMaster.getLoginId());
        Long rsrcId = timeSheetP3DbDao.getRsrcId(userId);
        Long tsId = tsMaster.getTsId();
        timeSheetP3DbDao.deleteRsrchour(tsId, rsrcId);
        timeSheetP3DbDao.deleteTimesht(tsId, rsrcId);
        if(!checkNeedToWrite(tsMaster.getRid())) {
            return;
        }
        DtoTimeSht dtoTimesht = new DtoTimeSht();
        dtoTimesht.setTsId(tsId);
        dtoTimesht.setRsrcId(rsrcId);
        dtoTimesht.setDailyFlag("Y");
        dtoTimesht.setStatusCode(tsMaster.getStatus());
        dtoTimesht.setUserId(userId);
        dtoTimesht.setLastRecvDate(tsMaster.getStatusDate());
        dtoTimesht.setStatusDate(tsMaster.getStatusDate());
        if(tsMaster.getNotes() != null){
            dtoTimesht.setTsNotes(tsMaster.getNotes());
        } else {
            dtoTimesht.setTsNotes("");
        }
        timeSheetP3DbDao.insertTimeSheet(dtoTimesht);
        List<TsTimesheetDetail> detailList = timeSheetDao
                                             .listTimeSheetDetailByTsRid(tsMaster.getRid());
        DtoRsrcHour dtoRsrcHour = null;
        List<TsTimesheetDay> dayList = null;
        Double hours = null;
        Double otHours = null;
        for(TsTimesheetDetail tsDetail : detailList) {
            if (tsDetail.getActivityId() == null) {//没有activityId的不需要回写
                continue;
            }
            dayList = timeSheetDao.listTimeSheetDayByDetailRid(tsDetail.getRid());
            for (TsTimesheetDay tsDay : dayList) {
                dtoRsrcHour = new DtoRsrcHour();
                Long rsourHursId = timeSheetP3DbDao.getResourceHursId();
                //查询ResourceHursId是否存在，存在则自增1再插入，否则新建ID1插入
                if(rsourHursId != null) {
                	dtoRsrcHour.setRsrcHrId(timeSheetP3DbDao.getNewResourceHursId());
                } else {
                	dtoRsrcHour.setRsrcHrId(Long.valueOf(1));
                }
                dtoRsrcHour.setRsrcId(rsrcId);
                dtoRsrcHour.setTsId(tsId);
                dtoRsrcHour.setTaskTsFlag(DtoRsrcHour.TASK_TS_FLAG_FALSE);
                dtoRsrcHour.setTaskrsrcId(timeSheetP3DbDao.getTaskRsrcId(
                                          tsDetail.getActivityId(), rsrcId));
                dtoRsrcHour.setProjId(timeSheetP3DbDao.getProjIdFromTask(
                                        tsDetail.getActivityId()));
                hours = nvl(tsDay.getWorkHours());
                otHours = nvl(tsDay.getOvertimeHours());
                dtoRsrcHour.setPendHrCnt(hours);
                dtoRsrcHour.setHrCnt(hours);
                dtoRsrcHour.setPendOtHrCnt(otHours);
                dtoRsrcHour.setOtHrCnt(otHours);
                dtoRsrcHour.setWorkDate(tsDay.getWorkDate());
                dtoRsrcHour.setStatusCode(tsDetail.getStatus());
                timeSheetP3DbDao.insertResourceHours(dtoRsrcHour);
            }
        }
    }
    /**
     * 检查是否需要回写到P3数据库
     * @param tsRid Long
     * @return boolean
     */
    private boolean checkNeedToWrite(Long tsRid) {
        boolean needToWrite = false;
        List<TsTimesheetDetail> detailList = timeSheetDao
                                             .listTimeSheetDetailByTsRid(tsRid);
        for(TsTimesheetDetail tsDetail : detailList) {
            if (tsDetail.getActivityId() != null) {
                needToWrite = true;
                break;
            }
        }
        return needToWrite;
    }
    /**
     * 判断输入的Double值是否为NULL
     * 是则构造一个新的Double对象返回
     * @param d Double
     * @return Double
     */
    private Double nvl(Double d) {
        if(d == null){
            return new Double(0);
        }
        return d;
    }

    /**
     * 发送邮件给相关人员
     * @param loginId String
     * @param ccId String[]
     * @param vmFile String
     * @param title String
     * @param obj Object
     */
    public void sendMail(final String loginId, final  String[] ccIds,
                         final String vmFile, final  String title,
                         final Object obj) {
        final HashMap hm = new HashMap();
        try {
            ArrayList al = new ArrayList();
            al.add(obj);

            LDAPUtil ldapUtil = new LDAPUtil();
            DtoUserInfo dtoUser = ldapUtil.findUser(LDAPUtil.DOMAIN_ALL, loginId);
            if (dtoUser != null) {
                String userMail = dtoUser.getEmail();
                String userName = dtoUser.getUserName();
                ContentBean cb = new ContentBean();

                cb.setUser(userName);

                cb.setEmail(userMail);
                cb.setMailcontent(al);
                cb.setCcAddress(getUserMail(ccIds));
                hm.put(userName, cb);
                final SendHastenMail shMail = new SendHastenMail();
                Thread t = new Thread(new Runnable() {
                    public void run() {
                        shMail.sendHastenMail(vmFile, title, hm);
                    }
                });
                try {
                    t.start();
                } catch (Throwable tx) {
                    tx.printStackTrace();
                    t.stop();
                }
            }
        } catch (Throwable tx) {
            throw new BusinessException("error.logic.ApprovalAssistServiceImp.noPmApproval",
                                        "error get all Email data", tx);
        }
    }

    private String getUserMail(String loginIds) {
        if(loginIds == null) return "";
        return getUserMail(loginIds.split(","));
    }

    private String getUserMail(String[] loginIds) {
        if(loginIds == null || loginIds.length == 0) return "";

        LDAPUtil ldapUtil = new LDAPUtil();
        Map ccMap = new HashMap();
        String mails = "";

        for (int i = 0; i < loginIds.length; i++) {
            String loginId = loginIds[i];
            if(loginId == null || "".equals(loginId)) continue;
            DtoUserInfo dtoUser = ldapUtil.findUser(LDAPUtil.DOMAIN_ALL, loginId);
            if(dtoUser == null) continue;
            String mail = dtoUser.getEmail();
            if (ccMap.containsKey(loginId) == false) {
                if("".equals(mails)) {
                    mails = mail;
                } else {
                    mails += "," + mail;
                }
                ccMap.put(loginId, dtoUser.getEmail());
            }
        }
        return mails;
    }

    /**
     * 获取某一时间段内的标准工作时间
     * 循环时间段的每一天从P3中查出该日期的工作时间
     * 累计所有的工作时间
     * @param begin Date
     * @param end Date
     * @param loginId String
     * @return Double
     */
    public Double getStandarHours(Date begin, Date end, String loginId) {
        Double standarHours = new Double(0);
       Calendar ca = Calendar.getInstance();
       ca.setTime(begin);
       try {
           standarHours = calendarP3Dao.getWorkHoursBetweenDatesByLoginId(begin, end, loginId);
       } catch (Exception ex) {
           throw new BusinessException("error.logic.ApprovalAssistServiceImp.standerHoursErr",
                                       "Get standar hours of day error!", ex);
       }
       return standarHours;
    }

    public void setTimeSheetP3DbDao(ITimeSheetP3DbDao timeSheetP3DbDao) {
        this.timeSheetP3DbDao = timeSheetP3DbDao;
    }

    public void setTimeSheetDao(ITimeSheetDao timeSheetDao) {
        this.timeSheetDao = timeSheetDao;
    }

    public void setCalendarP3Dao(ICalendarP3Dao calendarP3Dao) {
        this.calendarP3Dao = calendarP3Dao;
    }

    public void setAccountDao(IAccountDao accountDao) {
        this.accountDao = accountDao;
    }


    /**
    * PM审核时
    * 计算否一个人的实际工作时间，加班时间及请假时间
    * 并设置到dto中
    * 1.根据当前的项目以及Timesheet Master的rid
    *   查询Timesheet Detail资料如果没有则返回错误
    * 2.按Timesheet Master的rid查询出所有的detail资料
    *   (因为需要计算该人员的请假时间,请假时间只会填到部门下)
    * 3.根据detail记录查询填写的相关的工作时间
    * 4.如果是本项目下的时间且是假别记录则累计请假时间，如果不是假别记录则累计实际工作时间,加班时间
    * 5.如果不是本项目查询是否为假别记录，是则
    *   累计到请假时间上
    * @param dtoTsApproval DtoTsApproval
    * @param acntRid Long
    * @param tsRid Long
    * @param approvalLevel String
    * @return boolean
    */
    public boolean setHoursOk(DtoTsApproval dtoTsApproval, Long acntRid,
                              Long tsRid, String approvalLevel) {
        List<TsTimesheetDetail> detailList = timeSheetDao
                                              .listTimeSheetDetailByAcntRidTsRid(
                 acntRid, tsRid);
         if (detailList == null || detailList.size() == 0) {
             return false;//如果该人员在该项目下没有工时单则返回错误
         }
         //查出主表下的所有TsDetail资料（包括其他项目的资料，为统计请假时间）
         List<TsTimesheetDetail>
                 list = timeSheetDao.listTimeSheetDetailByTsRid(tsRid);
         Double actualHours = new Double(0);
         Double overtimeHours = new Double(0);
         Double leaveHours = new Double(0);
         List<TsTimesheetDay> dayList = null;
         boolean canOp = false;
         for (TsTimesheetDetail tsTimesheetDetail : list) {
             //取出每条TsDetail记录下的子表资料来统计工时
             dayList = timeSheetDao.listTimeSheetDayByDetailRid(
                     tsTimesheetDetail.getRid());
             //如果当前TsDetail是该项目下的工时单，则累计实际工作时间和加班时间
             if (tsTimesheetDetail.getAccountRid().intValue() ==
                 acntRid.intValue()) {
            	 //如果填的是假别code累计请假时间
            	 if(tsTimesheetDetail.isIsLeaveType() != null && tsTimesheetDetail.isIsLeaveType()) {
            		 for (TsTimesheetDay tsTimesheetDay : dayList) {
                         leaveHours = leaveHours +
                                      nvl(tsTimesheetDay.getWorkHours());
                     }
            	 } else if(tsTimesheetDetail.isIsLeaveType() == null || !tsTimesheetDetail.isIsLeaveType()) {
            		 for (TsTimesheetDay tsTimesheetDay : dayList) {
                         actualHours = actualHours
                                       + nvl(tsTimesheetDay.getWorkHours());
                         overtimeHours = overtimeHours +
                                         nvl(tsTimesheetDay.getOvertimeHours());
                     }
            	 }
                 if((DtoPreference.APPROVAL_LEVEL_PM.equals(approvalLevel) 
                  || DtoPreference.APPROVAL_LEVEL_PM_BEFORE_RM.equals(approvalLevel))
                    && tsTimesheetDetail.getStatus().equals(DtoTsApproval.STATUS_SUBMITTED)) {
                	 canOp = true;
                 } else if(DtoPreference.APPROVAL_LEVEL_PM_AND_RM.equals(approvalLevel)
                		 && (tsTimesheetDetail.getStatus().equals(DtoTsApproval.STATUS_SUBMITTED)
                			 || tsTimesheetDetail.getStatus().equals(DtoTsApproval.STATUS_RM_APPROVED))) {
                	 canOp = true;
                 }
             } else {
                 //如果当前TsDetail不是该项目下的工时单
                 //则查询该工时单填写的如果是假别的Code
                 //则累计请假时间，否则不做操作
                 if (tsTimesheetDetail.isIsLeaveType() != null && tsTimesheetDetail.isIsLeaveType()) {
                     for (TsTimesheetDay tsTimesheetDay : dayList) {
                         leaveHours = leaveHours +
                                      nvl(tsTimesheetDay.getWorkHours());
                     }
                 }
             }
         }
         dtoTsApproval.setCanOp(canOp);
         dtoTsApproval.setActualHours(actualHours);
         dtoTsApproval.setOvertimeHours(overtimeHours);
         dtoTsApproval.setLeaveHours(leaveHours);
         return true;

    }
    /**
    * RM审核时
    * 计算否一个人的实际工作时间，加班时间及请假时间
    * 并设置到dto中
    * 1.根据当前的Timesheet Master的rid
    *   查询Timesheet Detail资料如果没有则返回错误
    * 2.按Timesheet Master的rid查询出所有的detail资料
    * 3.根据detail记录查询填写的相关的工作时间
    * 4.查询是否是假别记录
    *   是则累计到请假时间上，否则累计实际工作时间和加班时间
    * @param dtoTsApproval DtoTsApproval
    * @param tsRid Long
    * @param approvalLevel String
    * @return boolean
    */
    public boolean setHoursOk(DtoTsApproval dtoTsApproval, Long tsRid, 
    		                  String approvalLevel) {
        List<TsTimesheetDetail> detailList = timeSheetDao
                                            .listTimeSheetDetailByTsRid(
                               tsRid);
       if (detailList == null || detailList.size() == 0) {
           return false;//如果该人员没有提交的工时单则返回状态值
       }
       //查出主表下的所有TsDetail资料
       List<TsTimesheetDetail>
               list = timeSheetDao.listTimeSheetDetailByTsRid(tsRid);
       Double actualHours = new Double(0);
       Double overtimeHours = new Double(0);
       Double leaveHours = new Double(0);
       List<TsTimesheetDay> dayList = null;
       boolean canOp = false;
       for (TsTimesheetDetail tsTimesheetDetail : list) {
           //取出每条TsDetail记录下的子表资料来统计工时
           dayList = timeSheetDao.listTimeSheetDayByDetailRid(
                   tsTimesheetDetail.getRid());
           //如果该工时单是填的是假别code
           //则累计请假时间，否则累计实际工作时间和加班时间
           if (tsTimesheetDetail.isIsLeaveType() != null && tsTimesheetDetail.isIsLeaveType()) {
					for (TsTimesheetDay tsTimesheetDay : dayList) {
						leaveHours = leaveHours
								+ nvl(tsTimesheetDay.getWorkHours());
					}
			} else if(tsTimesheetDetail.isIsLeaveType() == null || !tsTimesheetDetail.isIsLeaveType()){
				//如果当前工时单填的不是假别的Code，则累计实际工作时间和加班时间
				for (TsTimesheetDay tsTimesheetDay : dayList) {
					actualHours = actualHours
							+ nvl(tsTimesheetDay.getWorkHours());
					overtimeHours = overtimeHours
							+ nvl(tsTimesheetDay.getOvertimeHours());
				}
			}
           if(DtoPreference.APPROVAL_LEVEL_RM.equals(approvalLevel)
            && tsTimesheetDetail.getStatus().equals(DtoTsApproval.STATUS_SUBMITTED)) {
        	   canOp = true;
           } else if(DtoPreference.APPROVAL_LEVEL_PM_BEFORE_RM.equals(approvalLevel) 
        		   && dtoTsApproval.getStatus().equals(DtoTsApproval.STATUS_PM_APPROVED)) {
        	   canOp = true;
           } else if(DtoPreference.APPROVAL_LEVEL_PM_AND_RM.equals(approvalLevel) 
        		    && (tsTimesheetDetail.getStatus().equals(DtoTsApproval.STATUS_PM_APPROVED)
        		       || tsTimesheetDetail.getStatus().equals(DtoTsApproval.STATUS_SUBMITTED))) {
        	   canOp = true;
           }
       }
       dtoTsApproval.setCanOp(canOp);
       dtoTsApproval.setActualHours(actualHours + leaveHours);//实际工时要包括填写的请假工时 by zhengwenhai 20081204
       dtoTsApproval.setOvertimeHours(overtimeHours);
       dtoTsApproval.setLeaveHours(leaveHours);
       return true;

    }
    /**
     * 找到要发送邮件的相关人员并发送邮件
     * @param tsMaster TsTimesheetMaster
     * @param vmFile String
     * @param title String
     * @param object Object
     * @param type String
     * @param loginId String
     * @param acntRidList List
     * @param rejectedAcntRid Long
     * @param mailToRM boolean
     */
    public void searchPersonAndMailForPM(TsTimesheetMaster tsMaster,
                                    String vmFile, String type,
                                    String loginId, List<Long> acntRidList,
                                    Long rejectedAcntRid, boolean mailToRM, String reason) {
        String[] ccIds = null;
        DtoApprovalMail approvalMail = null;
        String filledPerson = tsMaster.getLoginId();
        LDAPUtil ldap = new LDAPUtil();
        DtoUser dtoUser = null;
        Long acntRid = null;
        //如果审核流程为PM和RM一起审核且不分顺序时
        //如果RM对工时单有操作则抄送者加入填写人员的RM（通过mailToRM判断）
        if (type.equals(DtoTsApproval.DTO_APPROVAL_PMANDRM) && mailToRM) {
            ccIds = new String[acntRidList.size() + 2];
        } else {
            ccIds = new String[acntRidList.size() + 1];
        }
        //抄送给执行者
        ccIds[0] = loginId;
        TsAccount tsAccount = null;
        for (int i = 0; i < acntRidList.size(); i++) {
            acntRid = acntRidList.get(i);
            tsAccount = accountDao.loadAccount(acntRid);
            if(tsAccount != null) {
                ccIds[i + 1] = tsAccount.getManager();
            }
        }
        tsAccount = accountDao.loadAccount(rejectedAcntRid);
        if(tsAccount == null) {
            throw new BusinessException("error.logic.ApprovalAssistServiceImp.accountNotExist",
                                        "Account is not exist!");
        }
        approvalMail = new DtoApprovalMail();
        dtoUser = ldap.findUser(LDAPUtil.DOMAIN_ALL, loginId);
        approvalMail.setApprovaler(dtoUser.getUserName()); //执行者
        approvalMail.setAcntId(tsAccount.getAccountId()
                               + "--" + tsAccount.getAccountName());
        approvalMail.setBegin(comDate.dateToString(
                tsMaster.getBeginDate(), "yyyy-MM-dd"));
        approvalMail.setEnd(comDate.dateToString(
                tsMaster.getEndDate(), "yyyy-MM-dd"));
        approvalMail.setReason(reason);
        //如果为PM和RM不分顺序审核时，查询填写人员的RM放入抄送者列表中
        if (type.equals(DtoTsApproval.DTO_APPROVAL_PMANDRM) && mailToRM) {
        	String directlyManager = rmMaintService.getRmByLoginId(tsMaster.getLoginId());
            ccIds[acntRidList.size()] = directlyManager;
        }
        sendMail(filledPerson, ccIds, vmFile, "Timecard Rejected by PM",
                 approvalMail);
    }
    /**
     * 找到要发送邮件的相关人员并发送邮件
     * @param tsMaster TsTimesheetMaster
     * @param vmFile String
     * @param type String
     * @param loginId String
     * @param acntRidList List
     */
    public void searchPersonAndMailForRM(TsTimesheetMaster tsMaster,
                                        String vmFile, String type,
                                        String loginId, List<Long> acntRidList, String reason) {
        DtoApprovalMail approvalMail = null;
        String[] ccIds = null;
        LDAPUtil ldap = new LDAPUtil();
        String filledPerson = tsMaster.getLoginId();
        DtoUser dtoUser = null;
        
        approvalMail = new DtoApprovalMail();
        dtoUser = ldap.findUser(LDAPUtil.DOMAIN_ALL, loginId);
        approvalMail.setApprovaler(dtoUser.getUserName()); //执行者
        approvalMail.setBegin(comDate.dateToString(
                tsMaster.getBeginDate(), "yyyy-MM-dd"));
        approvalMail.setEnd(comDate.dateToString(
                tsMaster.getEndDate(), "yyyy-MM-dd"));
        approvalMail.setReason(reason);
        ccIds = new String[acntRidList.size()+1];
        ccIds[0] = loginId;//抄送给执行者
        //RM单独审核时不需要给其他PM发送邮件
        if(!DtoTsApproval.DTO_APPROVAL_RMONLY.equals(type)) {
            TsAccount tsAccount = null;
            Long acntRid = null;
            for (int i = 0; i < acntRidList.size(); i++) {
                acntRid = acntRidList.get(i);
                tsAccount = accountDao.loadAccount(acntRid);
                if(tsAccount != null) {
                    ccIds[i + 1] = tsAccount.getManager();
                }
            }
        }
        sendMail(filledPerson, ccIds, vmFile, "Timecard Rejected by RM",
                 approvalMail);
   }

    /**
     * 处理审核流程变更
     * @param processType String
     */
    public void processLevelChanged(String processType, String site) {
        //不需要做处理的就返回
        if(processType.equals("")){
            return;
        }
        //确定流程
        //PM在RM之前审核变成PM或RM单独审核
        if(processType.equals(DtoPreference.APPROVAL_LEVEL_PM_BEFORE_RM
                             +DtoPreference.APPROVAL_LEVEL_PM)||
           processType.equals(DtoPreference.APPROVAL_LEVEL_PM_BEFORE_RM
                              +DtoPreference.APPROVAL_LEVEL_RM)) {
            processPBRToPMOrRM(site);
        }
        //PM和RM不分先后审核变成PM或RM单独审核
        if(processType.equals(DtoPreference.APPROVAL_LEVEL_PM_AND_RM
                             +DtoPreference.APPROVAL_LEVEL_PM)||
           processType.equals(DtoPreference.APPROVAL_LEVEL_PM_AND_RM
                             +DtoPreference.APPROVAL_LEVEL_RM)) {
            processPAndRToPMOrRm(site);
        }
        //PM和RM不分先后审核变成PM在RM之前审核
        if(processType.equals(DtoPreference.APPROVAL_LEVEL_PM_AND_RM
                             +DtoPreference.APPROVAL_LEVEL_PM_BEFORE_RM)) {
            processPAndRToPBR(site);
        }
    }
    /**
     * 处理PM在RM之前审核变成PM或RM单独审核时的情况
     */
    private void processPBRToPMOrRM(String site){
        //查询状态为TS_PMApprov的detail资料，将状态改为TS_Approv
        List<Long> detailList = timeSheetDao.listTimeSheetDetailByStatusSite(
                "('"+DtoTimeSheet.STATUS_PM_APPROVED+"')", site);
        if(detailList != null && detailList.size() > 0) {
        	TsTimesheetDetail tsDetail = null;
            for(Long tsDetailRid : detailList) {
            	tsDetail = timeSheetDao.geTimeSheetDetail(tsDetailRid);
                tsDetail.setStatus(DtoTimeSheet.STATUS_APPROVED);
                timeSheetDao.updateTimeSheetDetail(tsDetail);
            }
        }
        //查询状态为TS_PMApprov的master资料，将状态改为TS_Approv
        List<Long> masterList = timeSheetDao.listTimeSheetMasterByStatusSite(
                 "('"+DtoTimeSheet.STATUS_PM_APPROVED+"')", site);
        if(masterList != null && masterList.size() > 0) {
        	TsTimesheetMaster tsMaster = null;
            for(Long tsMasterRid : masterList) {
            	tsMaster = timeSheetDao.getTimeSheetMaster(tsMasterRid);
                tsMaster.setStatus(DtoTimeSheet.STATUS_APPROVED);
                timeSheetDao.updateTimeSheetMaster(tsMaster);
                this.writeBackToP3(tsMaster);
            }
        }
    }
    /**
     * 处理PM和RM不分先后审核变成PM或RM单独审核
     */
    private void processPAndRToPMOrRm(String site){
        //查询状态为TS_PMApprov和TS_RMApprov的detail资料，将状态改为TS_Approv
        List<Long> detailList = timeSheetDao.listTimeSheetDetailByStatusSite(
                "('"+DtoTimeSheet.STATUS_PM_APPROVED+"','"+DtoTimeSheet.STATUS_RM_APPROVED+"')", site);
        if(detailList != null && detailList.size() > 0) {
        	TsTimesheetDetail tsDetail = null;
            for(Long tsDetailRid : detailList) {
            	tsDetail = timeSheetDao.geTimeSheetDetail(tsDetailRid);
                tsDetail.setStatus(DtoTimeSheet.STATUS_APPROVED);
                timeSheetDao.updateTimeSheetDetail(tsDetail);
            }
        }
        //查询状态为TS_PMApprov和TS_RMApprov的master资料，将状态改为TS_Approv
        List<Long> masterList = timeSheetDao.listTimeSheetMasterByStatusSite(
                 "('"+DtoTimeSheet.STATUS_PM_APPROVED+"','"+DtoTimeSheet.STATUS_RM_APPROVED+"')", site);
        if(masterList != null && masterList.size() > 0) {
        	TsTimesheetMaster tsMaster = null;
           for(Long tsMasterRid : masterList) {
        	   tsMaster = timeSheetDao.getTimeSheetMaster(tsMasterRid);
               tsMaster.setStatus(DtoTimeSheet.STATUS_APPROVED);
               timeSheetDao.updateTimeSheetMaster(tsMaster);
               //回写P3
               this.writeBackToP3(tsMaster);
           }
       }
    }
    /**
     * 处理PM和RM不分先后审核变成PM在RM之前审核
     */
    private void processPAndRToPBR(String site){
        //查询状态为TS_RMApprov的detail资料，将状态改为TS_Approv
        List<Long> detailList = timeSheetDao.listTimeSheetDetailByStatusSite(
                "('"+DtoTimeSheet.STATUS_RM_APPROVED+"')", site);
        Map tsRidMap = new HashMap();
        if(detailList != null && detailList.size() > 0) {
            Long tsrid = null;
            TsTimesheetDetail tsDetail = null;
            for(Long tsDetailRid : detailList) {
            	tsDetail = timeSheetDao.geTimeSheetDetail(tsDetailRid);
                tsDetail.setStatus(DtoTimeSheet.STATUS_APPROVED);
                tsrid = tsDetail.getTsRid();
                timeSheetDao.updateTimeSheetDetail(tsDetail);
                if(tsRidMap.get("tsRid"+String.valueOf(tsrid)) == null) {
                    tsRidMap.put("tsRid"+String.valueOf(tsrid), tsrid);
                }
            }
        }
        List<Long> tsRidList = new ArrayList(tsRidMap.values());
        if (tsRidList != null && tsRidList.size() > 0) {
            TsTimesheetMaster tsMaster = null;
            for (Long tsRid : tsRidList) {
                tsMaster = timeSheetDao.getTimeSheetMaster(tsRid);
                tsMaster.setStatus(DtoTimeSheet.STATUS_APPROVED);
                timeSheetDao.updateTimeSheetMaster(tsMaster);
                //回写P3
                this.writeBackToP3(tsMaster);
            }
        }
    }
	


}
