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
 * <p>Description: ������˹���ʱ��һЩ����ҵ�����</p>
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
     * ����P3��timesheet��״̬
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
     * ����˺�Ĺ�ʱ�����ݻ�д��P3��
     *   1.ͨ��tsId��rsrc_idɾ��RSRCHOUR������,rsrc_idͨ��user_id��RSRC��ȡ��
     *     user_id��USER��ͨ��loginId��ȡ
     *   2.����tsId��rsrc_idɾ��TIMESHT����
     *   3.����TIMESHT����
     *   4.����RSRCHOUR����
     *      ѭ��tsDay��������
     *      ͨ�����÷�������rsrc_hr_id
     *      ����task_id(activity_id)��rsrc_id��TASKRSRC���в�ѯtaskrsrc_id
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
     * ����˺�Ĺ�ʱ�����ݻ�д��P3��
     *   1.ͨ��tsId��rsrc_idɾ��RSRCHOUR������,rsrc_idͨ��user_id��RSRC��ȡ��
     *     user_id��USER��ͨ��loginId��ȡ
     *   2.����tsId��rsrc_idɾ��TIMESHT����
     *   3.����TIMESHT����
     *   4.����RSRCHOUR����
     *      ѭ��tsDay��������
     *      ͨ�����÷�������rsrc_hr_id
     *      ����task_id(activity_id)��rsrc_id��TASKRSRC���в�ѯtaskrsrc_id
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
            if (tsDetail.getActivityId() == null) {//û��activityId�Ĳ���Ҫ��д
                continue;
            }
            dayList = timeSheetDao.listTimeSheetDayByDetailRid(tsDetail.getRid());
            for (TsTimesheetDay tsDay : dayList) {
                dtoRsrcHour = new DtoRsrcHour();
                Long rsourHursId = timeSheetP3DbDao.getResourceHursId();
                //��ѯResourceHursId�Ƿ���ڣ�����������1�ٲ��룬�����½�ID1����
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
     * ����Ƿ���Ҫ��д��P3���ݿ�
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
     * �ж������Doubleֵ�Ƿ�ΪNULL
     * ������һ���µ�Double���󷵻�
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
     * �����ʼ��������Ա
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
     * ��ȡĳһʱ����ڵı�׼����ʱ��
     * ѭ��ʱ��ε�ÿһ���P3�в�������ڵĹ���ʱ��
     * �ۼ����еĹ���ʱ��
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
    * PM���ʱ
    * �����һ���˵�ʵ�ʹ���ʱ�䣬�Ӱ�ʱ�估���ʱ��
    * �����õ�dto��
    * 1.���ݵ�ǰ����Ŀ�Լ�Timesheet Master��rid
    *   ��ѯTimesheet Detail�������û���򷵻ش���
    * 2.��Timesheet Master��rid��ѯ�����е�detail����
    *   (��Ϊ��Ҫ�������Ա�����ʱ��,���ʱ��ֻ���������)
    * 3.����detail��¼��ѯ��д����صĹ���ʱ��
    * 4.����Ǳ���Ŀ�µ�ʱ�����Ǽٱ��¼���ۼ����ʱ�䣬������Ǽٱ��¼���ۼ�ʵ�ʹ���ʱ��,�Ӱ�ʱ��
    * 5.������Ǳ���Ŀ��ѯ�Ƿ�Ϊ�ٱ��¼������
    *   �ۼƵ����ʱ����
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
             return false;//�������Ա�ڸ���Ŀ��û�й�ʱ���򷵻ش���
         }
         //��������µ�����TsDetail���ϣ�����������Ŀ�����ϣ�Ϊͳ�����ʱ�䣩
         List<TsTimesheetDetail>
                 list = timeSheetDao.listTimeSheetDetailByTsRid(tsRid);
         Double actualHours = new Double(0);
         Double overtimeHours = new Double(0);
         Double leaveHours = new Double(0);
         List<TsTimesheetDay> dayList = null;
         boolean canOp = false;
         for (TsTimesheetDetail tsTimesheetDetail : list) {
             //ȡ��ÿ��TsDetail��¼�µ��ӱ�������ͳ�ƹ�ʱ
             dayList = timeSheetDao.listTimeSheetDayByDetailRid(
                     tsTimesheetDetail.getRid());
             //�����ǰTsDetail�Ǹ���Ŀ�µĹ�ʱ�������ۼ�ʵ�ʹ���ʱ��ͼӰ�ʱ��
             if (tsTimesheetDetail.getAccountRid().intValue() ==
                 acntRid.intValue()) {
            	 //�������Ǽٱ�code�ۼ����ʱ��
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
                 //�����ǰTsDetail���Ǹ���Ŀ�µĹ�ʱ��
                 //���ѯ�ù�ʱ����д������Ǽٱ��Code
                 //���ۼ����ʱ�䣬����������
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
    * RM���ʱ
    * �����һ���˵�ʵ�ʹ���ʱ�䣬�Ӱ�ʱ�估���ʱ��
    * �����õ�dto��
    * 1.���ݵ�ǰ��Timesheet Master��rid
    *   ��ѯTimesheet Detail�������û���򷵻ش���
    * 2.��Timesheet Master��rid��ѯ�����е�detail����
    * 3.����detail��¼��ѯ��д����صĹ���ʱ��
    * 4.��ѯ�Ƿ��Ǽٱ��¼
    *   �����ۼƵ����ʱ���ϣ������ۼ�ʵ�ʹ���ʱ��ͼӰ�ʱ��
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
           return false;//�������Աû���ύ�Ĺ�ʱ���򷵻�״ֵ̬
       }
       //��������µ�����TsDetail����
       List<TsTimesheetDetail>
               list = timeSheetDao.listTimeSheetDetailByTsRid(tsRid);
       Double actualHours = new Double(0);
       Double overtimeHours = new Double(0);
       Double leaveHours = new Double(0);
       List<TsTimesheetDay> dayList = null;
       boolean canOp = false;
       for (TsTimesheetDetail tsTimesheetDetail : list) {
           //ȡ��ÿ��TsDetail��¼�µ��ӱ�������ͳ�ƹ�ʱ
           dayList = timeSheetDao.listTimeSheetDayByDetailRid(
                   tsTimesheetDetail.getRid());
           //����ù�ʱ��������Ǽٱ�code
           //���ۼ����ʱ�䣬�����ۼ�ʵ�ʹ���ʱ��ͼӰ�ʱ��
           if (tsTimesheetDetail.isIsLeaveType() != null && tsTimesheetDetail.isIsLeaveType()) {
					for (TsTimesheetDay tsTimesheetDay : dayList) {
						leaveHours = leaveHours
								+ nvl(tsTimesheetDay.getWorkHours());
					}
			} else if(tsTimesheetDetail.isIsLeaveType() == null || !tsTimesheetDetail.isIsLeaveType()){
				//�����ǰ��ʱ����Ĳ��Ǽٱ��Code�����ۼ�ʵ�ʹ���ʱ��ͼӰ�ʱ��
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
       dtoTsApproval.setActualHours(actualHours + leaveHours);//ʵ�ʹ�ʱҪ������д����ٹ�ʱ by zhengwenhai 20081204
       dtoTsApproval.setOvertimeHours(overtimeHours);
       dtoTsApproval.setLeaveHours(leaveHours);
       return true;

    }
    /**
     * �ҵ�Ҫ�����ʼ��������Ա�������ʼ�
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
        //����������ΪPM��RMһ������Ҳ���˳��ʱ
        //���RM�Թ�ʱ���в��������߼�����д��Ա��RM��ͨ��mailToRM�жϣ�
        if (type.equals(DtoTsApproval.DTO_APPROVAL_PMANDRM) && mailToRM) {
            ccIds = new String[acntRidList.size() + 2];
        } else {
            ccIds = new String[acntRidList.size() + 1];
        }
        //���͸�ִ����
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
        approvalMail.setApprovaler(dtoUser.getUserName()); //ִ����
        approvalMail.setAcntId(tsAccount.getAccountId()
                               + "--" + tsAccount.getAccountName());
        approvalMail.setBegin(comDate.dateToString(
                tsMaster.getBeginDate(), "yyyy-MM-dd"));
        approvalMail.setEnd(comDate.dateToString(
                tsMaster.getEndDate(), "yyyy-MM-dd"));
        approvalMail.setReason(reason);
        //���ΪPM��RM����˳�����ʱ����ѯ��д��Ա��RM���볭�����б���
        if (type.equals(DtoTsApproval.DTO_APPROVAL_PMANDRM) && mailToRM) {
        	String directlyManager = rmMaintService.getRmByLoginId(tsMaster.getLoginId());
            ccIds[acntRidList.size()] = directlyManager;
        }
        sendMail(filledPerson, ccIds, vmFile, "Timecard Rejected by PM",
                 approvalMail);
    }
    /**
     * �ҵ�Ҫ�����ʼ��������Ա�������ʼ�
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
        approvalMail.setApprovaler(dtoUser.getUserName()); //ִ����
        approvalMail.setBegin(comDate.dateToString(
                tsMaster.getBeginDate(), "yyyy-MM-dd"));
        approvalMail.setEnd(comDate.dateToString(
                tsMaster.getEndDate(), "yyyy-MM-dd"));
        approvalMail.setReason(reason);
        ccIds = new String[acntRidList.size()+1];
        ccIds[0] = loginId;//���͸�ִ����
        //RM�������ʱ����Ҫ������PM�����ʼ�
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
     * ����������̱��
     * @param processType String
     */
    public void processLevelChanged(String processType, String site) {
        //����Ҫ������ľͷ���
        if(processType.equals("")){
            return;
        }
        //ȷ������
        //PM��RM֮ǰ��˱��PM��RM�������
        if(processType.equals(DtoPreference.APPROVAL_LEVEL_PM_BEFORE_RM
                             +DtoPreference.APPROVAL_LEVEL_PM)||
           processType.equals(DtoPreference.APPROVAL_LEVEL_PM_BEFORE_RM
                              +DtoPreference.APPROVAL_LEVEL_RM)) {
            processPBRToPMOrRM(site);
        }
        //PM��RM�����Ⱥ���˱��PM��RM�������
        if(processType.equals(DtoPreference.APPROVAL_LEVEL_PM_AND_RM
                             +DtoPreference.APPROVAL_LEVEL_PM)||
           processType.equals(DtoPreference.APPROVAL_LEVEL_PM_AND_RM
                             +DtoPreference.APPROVAL_LEVEL_RM)) {
            processPAndRToPMOrRm(site);
        }
        //PM��RM�����Ⱥ���˱��PM��RM֮ǰ���
        if(processType.equals(DtoPreference.APPROVAL_LEVEL_PM_AND_RM
                             +DtoPreference.APPROVAL_LEVEL_PM_BEFORE_RM)) {
            processPAndRToPBR(site);
        }
    }
    /**
     * ����PM��RM֮ǰ��˱��PM��RM�������ʱ�����
     */
    private void processPBRToPMOrRM(String site){
        //��ѯ״̬ΪTS_PMApprov��detail���ϣ���״̬��ΪTS_Approv
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
        //��ѯ״̬ΪTS_PMApprov��master���ϣ���״̬��ΪTS_Approv
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
     * ����PM��RM�����Ⱥ���˱��PM��RM�������
     */
    private void processPAndRToPMOrRm(String site){
        //��ѯ״̬ΪTS_PMApprov��TS_RMApprov��detail���ϣ���״̬��ΪTS_Approv
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
        //��ѯ״̬ΪTS_PMApprov��TS_RMApprov��master���ϣ���״̬��ΪTS_Approv
        List<Long> masterList = timeSheetDao.listTimeSheetMasterByStatusSite(
                 "('"+DtoTimeSheet.STATUS_PM_APPROVED+"','"+DtoTimeSheet.STATUS_RM_APPROVED+"')", site);
        if(masterList != null && masterList.size() > 0) {
        	TsTimesheetMaster tsMaster = null;
           for(Long tsMasterRid : masterList) {
        	   tsMaster = timeSheetDao.getTimeSheetMaster(tsMasterRid);
               tsMaster.setStatus(DtoTimeSheet.STATUS_APPROVED);
               timeSheetDao.updateTimeSheetMaster(tsMaster);
               //��дP3
               this.writeBackToP3(tsMaster);
           }
       }
    }
    /**
     * ����PM��RM�����Ⱥ���˱��PM��RM֮ǰ���
     */
    private void processPAndRToPBR(String site){
        //��ѯ״̬ΪTS_RMApprov��detail���ϣ���״̬��ΪTS_Approv
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
                //��дP3
                this.writeBackToP3(tsMaster);
            }
        }
    }
	


}
