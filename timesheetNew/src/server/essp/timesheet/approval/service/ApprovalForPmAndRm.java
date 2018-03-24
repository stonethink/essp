package server.essp.timesheet.approval.service;

import java.util.*;

import server.essp.common.ldap.LDAPUtil;
import server.essp.timesheet.account.dao.IAccountDao;
import server.essp.timesheet.approval.dao.IApprovalDao;
import server.essp.timesheet.rmmaint.service.IRmMaintService;
import server.essp.timesheet.weeklyreport.dao.ITimeSheetDao;
import c2s.essp.common.user.DtoUser;
import c2s.essp.timesheet.approval.DtoTsApproval;
import c2s.essp.timesheet.preference.DtoPreference;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheet;

import com.wits.util.comDate;

import db.essp.timesheet.*;

/**
 * <p>Title: PmApprovalForPmAndRm</p>
 *
 * <p>Description: 审批设置为PM和RM同时审批时，审批功能实现</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu(implemented by wenhaizheng)
 * @version 1.0
 */
public class ApprovalForPmAndRm implements IApproval {
    private IApprovalDao approvalDao;
    private ITimeSheetDao timeSheetDao;
    private IAccountDao accountDao;
    private IRmMaintService rmMaintService;
    private IApprovalAssistService approvalAssistService;
    public static final String vmFilePM =
            "mail/template/ts/WeeklyReportRejectedByPM.htm";
    public static final String vmFileRM =
            "mail/template/ts/WeeklyReportRejectedByRM.htm";

    public ApprovalForPmAndRm(IApprovalDao approvalDao,
                              ITimeSheetDao timeSheetDao,
                              IApprovalAssistService approvalAssistService,
                              IAccountDao accountDao, 
                              IRmMaintService rmMaintService) {
              this.setApprovalDao(approvalDao);
              this.setTimeSheetDao(timeSheetDao);
              this.setApprovalAssistService(approvalAssistService);
              this.setAccountDao(accountDao);
              this.setRmMaintService(rmMaintService);
    }

    /**
     * 列出指定项目和时间区间的工时单
     *    列出状态为：TS_Review和TS_RMApprov的工时单
     * @param acntRid Long
     * @param begin Date
     * @param end Date
     * @param queryWay String
     * @param managerId String
     * @return List<DtoTsApproval>
     */
    public List<DtoTsApproval> pmList(Long acntRid, Date begin, Date end, 
    		 					      String queryWay, String managerId) {
    	List<DtoTsApproval> resultList = new ArrayList<DtoTsApproval>();
        String accountRids = "(-1)";
        List<Object[]> timesheetList = null;
        if(!acntRid.equals(Long.valueOf(-1))) {
        	accountRids = "("+acntRid+")";
        } else {
        	List accountList = accountDao.listAccounts(managerId);
        	if(accountList == null || accountList.size() == 0) {
        		return resultList;
        	}
        	String temp = "";
        	int size = accountList.size();
        	TsAccount tsAccount = null;
        	for(int i = 0;i<size;i++){
        		tsAccount = (TsAccount)accountList.get(i);
        		if(i == 0) {
        			temp = String.valueOf(tsAccount.getRid());
        		} else {
        			temp = temp + "," + tsAccount.getRid();
        		}
        	}
        	if(!"".equals(temp)) {
        		accountRids = "("+temp+")";
        	}
        }
        String status = "";
        if (DtoTsApproval.DTO_QUERY_WAY_ALL.equals(queryWay)) {
        	status = null;
		} else if(DtoTsApproval.DTO_QUERY_WAY_TODEAL.equals(queryWay)) {
			status = "('"+DtoTsApproval.STATUS_SUBMITTED+"','"
                		 +DtoTsApproval.STATUS_RM_APPROVED+"')";			
		} else if(DtoTsApproval.DTO_QUERY_WAY_DEALED.equals(queryWay)) {
			status = "('"+DtoTsApproval.STATUS_PM_APPROVED+"','"
        				 +DtoTsApproval.STATUS_APPROVED+"')";
		}
        timesheetList = approvalDao.listPmApproval(accountRids, begin, end, status);
        if(timesheetList == null || timesheetList.size() == 0){
            return resultList;
        }
        DtoTsApproval dtoTsApproval = null;
        boolean isLeaveType = false;
        //循环取得每个人的Timesheet
        for (Object[] obj : timesheetList) {
            dtoTsApproval = new DtoTsApproval();
			dtoTsApproval.setLoginId((String) obj[0]);
			dtoTsApproval.setStartDate((Date) obj[1]);
			dtoTsApproval.setFinishDate((Date) obj[2]);
			dtoTsApproval.setStatus((String) obj[3]);
			dtoTsApproval.setAcntRid((Long) obj[4]);
			dtoTsApproval.setTsRid((Long) obj[5]);
			dtoTsApproval.setAcntCode((String)obj[9]);
			dtoTsApproval.setStandarHours(approvalAssistService
					.getStandarHours(dtoTsApproval.getStartDate(),
							dtoTsApproval.getFinishDate(), dtoTsApproval.getLoginId()));
			if (DtoTsApproval.STATUS_SUBMITTED
					.equals((String)obj[10])
				||DtoTsApproval.STATUS_RM_APPROVED
				    .equals((String)obj[10])) {
				dtoTsApproval.setCanOp(true);
			} else {
				dtoTsApproval.setCanOp(false);
			}
			if (obj[8] != null) {
				isLeaveType = (Boolean) obj[8];
			}
			dtoTsApproval.setActualHours(0.00);
			dtoTsApproval.setLeaveHours(0.00);
			if (isLeaveType) {
				if(obj[6] != null) {
					dtoTsApproval.setLeaveHours((Double) obj[6]);
				}
			} else {
				if(obj[6] != null) {
					dtoTsApproval.setActualHours((Double) obj[6]);
				}
			}
			if(obj[7] != null) {
				dtoTsApproval.setOvertimeHours((Double) obj[7]);
			} else {
				dtoTsApproval.setOvertimeHours(0.00);
			}
			//修改为实际工时包含加班工时 by zhengwenhai 20081204
			dtoTsApproval.setActualHours(dtoTsApproval.getActualHours() + dtoTsApproval.getLeaveHours());
			resultList.add(dtoTsApproval);
        }
        return resultList;
    }

    /**
	 * 列出指定时间区间内，当前用户为RM的所有员工的工时单 列出状态为：TS_Review和TS_PMApprov的工时单
	 * 
	 * @param begin Date
	 * @param end Date
	 * @param managerId String 
	 * @param queryWay String
	 * @return List<DtoTsApproval>
	 */
    public List<DtoTsApproval> rmList(String managerId, Date begin, Date end, String queryWay) {
        	List<DtoTsApproval> resultList = new ArrayList<DtoTsApproval>();
        	//列出当前资源经理管理下的所有人员
            List<String> personList = rmMaintService.getHumanUnderRM(managerId);
            DtoTsApproval dtoTsApproval = null;
            List<TsTimesheetMaster> list = null;
            //循环取得每个人的Timesheet
            for (String loginId : personList) {
                //通过loginId和开始结束日期取得TimeSheet主表
            	if(DtoTsApproval.DTO_QUERY_WAY_ALL.equals(queryWay)) {
            		list = timeSheetDao.listTimeSheetMasterByLoginIdDate(
                            loginId, begin, end);
            	} else if(DtoTsApproval.DTO_QUERY_WAY_TODEAL.equals(queryWay)) {
            		list = timeSheetDao.listTimeSheetMasterByDateStatus(
            				loginId, begin, end, "('"+DtoTsApproval.STATUS_SUBMITTED+"','"
            				                         +DtoTsApproval.STATUS_PM_APPROVED+"')");
            	} else if(DtoTsApproval.DTO_QUERY_WAY_DEALED.equals(queryWay)) {
        		   list = timeSheetDao.listTimeSheetMasterByDateStatus(
        				   loginId, begin, end, "('"+DtoTsApproval.STATUS_RM_APPROVED+"','"
        				                            +DtoTsApproval.STATUS_APPROVED+"')");
        	    }
                
                if (list == null || list.size() == 0) {
                    continue; //不存在则取下一人的资料
                }
                for (TsTimesheetMaster tsTimesheetMaster : list) {
                	dtoTsApproval = new DtoTsApproval();
                	dtoTsApproval.setLoginId(loginId);
                	dtoTsApproval.setStatus(tsTimesheetMaster.getStatus());
                	dtoTsApproval.setTsRid(tsTimesheetMaster.getRid());
                	dtoTsApproval.setStartDate(tsTimesheetMaster.getBeginDate());
                	dtoTsApproval.setFinishDate(tsTimesheetMaster.getEndDate());
                	dtoTsApproval.setStandarHours(approvalAssistService
						.getStandarHours(tsTimesheetMaster.getBeginDate(),
								tsTimesheetMaster.getEndDate(),dtoTsApproval.getLoginId()));
                	if (!approvalAssistService.setHoursOk(dtoTsApproval,
						tsTimesheetMaster.getRid(),
						DtoPreference.APPROVAL_LEVEL_PM_AND_RM)) {
                		continue; // 如果设置相应的时间发生错误则不将记录加入列表中，继续循环下一人
                	}
                	resultList.add(dtoTsApproval);
                }
            }
        
        return resultList;
    }

    /**
	 * PM批准工时单 如果当前工时单状态为TS_RMApprov，转变状态为TS_Approv 如果当前工时单状态为TS_Review，{
	 * 如果工时单的RM如果是当前用户， 转变状态为TS_Approv。否则转变状态为TS_PMApprov }
	 * 其它情况不执行任何动作（可能在前台查看的时候被改变状态了，如被其他PM Reject）
	 * 
	 * 检查：所有TsDetail状态一致，且与TsMaster不同，则改变TsMaster状态一致
	 * 
	 * @param tsList
	 *            List<DtoTsApproval>
	 */
    public void pmApproval(List<DtoTsApproval> tsList) {
        List<TsTimesheetDetail> tsDetailList = null;
        TsTimesheetMaster tsMaster = null;
        for (DtoTsApproval dtoTsApproval : tsList) {
            tsDetailList = timeSheetDao.listTimeSheetDetailByAcntRidTsRidByStatus(
                    dtoTsApproval
                    .getAcntRid(), dtoTsApproval.getTsRid(),
                    "('" + DtoTimeSheet.STATUS_SUBMITTED
                 + "','" + DtoTimeSheet.STATUS_RM_APPROVED + "')");
            if (tsDetailList == null || tsDetailList.size() == 0) {
                continue;//此条记录下没有可以审批记录跳到下条记录
            }
            String updateStatus = "";
            //循环取出tsDetail工时单，如果工时单状态为TS_Review则审核后更新为TS_PMApprov
            //如果工时单状态为TS_RMApprov则审核后更新为TS_Approv
            for (TsTimesheetDetail tsDetail : tsDetailList) {
                if (tsDetail.getStatus().equals(DtoTimeSheet.STATUS_SUBMITTED)) {
                    updateStatus = DtoTimeSheet.STATUS_PM_APPROVED;
                } else if (tsDetail.getStatus().equals(DtoTimeSheet.
                        STATUS_RM_APPROVED)) {
                    updateStatus = DtoTimeSheet.STATUS_APPROVED;
                }
                timeSheetDao.updateTimeSheetDetailStatus(tsDetail.getRid(),
                        updateStatus);
            }
            //查询该TimesheetMaster下是否还存在没有被PM的detail资料
            tsDetailList = timeSheetDao.listTimeSheetDetailNotEqualsStatusByTsRid(
                    dtoTsApproval.getTsRid(), DtoTimeSheet.STATUS_PM_APPROVED);
            //如果没有则将主表中的状态置为TS_PMApprov
            if (tsDetailList == null || tsDetailList.size() == 0) {
                timeSheetDao.updateTimeSheetMasterStatus(dtoTsApproval.getTsRid(),
                        DtoTimeSheet.STATUS_PM_APPROVED);
                //更新状态改变日期栏位值
                tsMaster = timeSheetDao.getTimeSheetMaster(dtoTsApproval.
                        getTsRid());
                tsMaster.setStatusDate(new Date());
                timeSheetDao.updateTimeSheetMaster(tsMaster);
            }
            //查询该TimesheetMaster下是否还存在没有被完全审核（经过PM和RM两次审核）的detail资料
            tsDetailList = timeSheetDao.listTimeSheetDetailNotEqualsStatusByTsRid(
                    dtoTsApproval.getTsRid(), DtoTimeSheet.STATUS_APPROVED);
            //如果没有则将主表中的状态置为TS_Approv
            if (tsDetailList == null || tsDetailList.size() == 0) {
                timeSheetDao.updateTimeSheetMasterStatus(dtoTsApproval.getTsRid(),
                        DtoTimeSheet.STATUS_APPROVED);
                //更新状态改变日期栏位值
                tsMaster = timeSheetDao.getTimeSheetMaster(dtoTsApproval.getTsRid());
                tsMaster.setStatusDate(new Date());
                timeSheetDao.updateTimeSheetMaster(tsMaster);
                //回写P3数据
                approvalAssistService.writeBackToP3(tsMaster);

            }
        }
    }

    /**
     * RM批准工时单
     *     如果当前工时单状态为TS_PMApprov，转变状态为TS_Approv
     *     如果当前工时单状态为TS_Review，{
     *                   如果工时单的所有Detail所在项目的PM都是当前用户，
     *                   转变状态为TS_Approv。否则转变状态为TS_RMApprov }
     *     其它情况不执行任何动作（可能在前台查看的时候被改变状态了，如被PM Reject）
     *
     *     检查：所有TsDetail状态一致，且与TsMaster不同，则改变TsMaster状态一致
     * @param tsList List<DtoTsApproval>
     */
    public void rmApproval(List<DtoTsApproval> tsList) {
        List<TsTimesheetDetail> tsDetailList = null;
        TsTimesheetMaster tsMaster = null;
        for (DtoTsApproval dtoTsApproval : tsList) {
            tsDetailList = timeSheetDao.listTimeSheetDetailByTsRidStatus(
                    dtoTsApproval.getTsRid(),
                    "('" + DtoTimeSheet.STATUS_SUBMITTED + "','"
                         + DtoTimeSheet.STATUS_PM_APPROVED + "')");
            if (tsDetailList == null || tsDetailList.size() == 0) {
                continue;//此条记录下没有可以审批记录跳到下条记录
            }
            String updateStatus = "";
            //循环取出tsDetail工时单，如果工时单状态为TS_Review则审核后更新为TS_RMApprov
            //如果工时单状态为TS_PMApprov则审核后更新为TS_Approv
            for (TsTimesheetDetail tsDetail : tsDetailList) {
                if (tsDetail.getStatus().equals(DtoTimeSheet.STATUS_SUBMITTED)) {
                   updateStatus = DtoTimeSheet.STATUS_RM_APPROVED;
               } else if (tsDetail.getStatus().equals(DtoTimeSheet.STATUS_PM_APPROVED)) {
                   updateStatus = DtoTimeSheet.STATUS_APPROVED;
               }
                timeSheetDao.updateTimeSheetDetailStatus(tsDetail.getRid(),
                        updateStatus);
            }
            //查询该TimesheetMaster下是否还有没有被RM审核的detail资料
            tsDetailList = timeSheetDao.listTimeSheetDetailNotEqualsStatusByTsRid(
                    dtoTsApproval.getTsRid(), DtoTimeSheet.STATUS_RM_APPROVED);
            //如果没有则将主表中的状态置为TS_RMApprov
            if (tsDetailList == null || tsDetailList.size() == 0) {
                timeSheetDao.updateTimeSheetMasterStatus(dtoTsApproval.getTsRid(),
                        DtoTimeSheet.STATUS_RM_APPROVED);
                //更新状态改变日期栏位值
                tsMaster = timeSheetDao.getTimeSheetMaster(dtoTsApproval.
                        getTsRid());
                tsMaster.setStatusDate(new Date());
                timeSheetDao.updateTimeSheetMaster(tsMaster);
            }
            //查询该TimesheetMaster下是否还存在没有被完全审核（RM和PM都审核）的detail资料
            tsDetailList = timeSheetDao.listTimeSheetDetailNotEqualsStatusByTsRid(
                    dtoTsApproval.getTsRid(), DtoTimeSheet.STATUS_APPROVED);
            //如果没有则将主表中的状态置为TS_Approv
            if (tsDetailList == null || tsDetailList.size() == 0) {
                timeSheetDao.updateTimeSheetMasterStatus(dtoTsApproval.getTsRid(),
                        DtoTimeSheet.STATUS_APPROVED);
                //更新状态改变日期栏位值
                tsMaster = timeSheetDao.getTimeSheetMaster(dtoTsApproval.getTsRid());
                tsMaster.setStatusDate(new Date());
                timeSheetDao.updateTimeSheetMaster(tsMaster);
                //回写P3数据
                approvalAssistService.writeBackToP3(tsMaster);

            }
        }
    }

    /**
     * PM驳回工时单，需要eMail通知相关人员
     *     Reject TsMaster及其所有TsDetail
     *     发送eMail通知填写人员，抄送给对此工时单执行过审批动作的人员（RM和其他PM）
     * @param tsList List<DtoTsApproval>
     * @param loginId String
     */
    public void pmReject(List<DtoTsApproval> tsList, String loginId, String reason) {
        List<TsTimesheetDetail> tsDetailList = null;
        TsTimesheetMaster tsMaster = null;
        List<Long> acntRidList = null;
       for (DtoTsApproval dtoTsApproval : tsList) {
           tsDetailList = timeSheetDao.listTimeSheetDetailByAcntRidTsRidByStatus(
                    dtoTsApproval
                    .getAcntRid(), dtoTsApproval.getTsRid(),
                    "('" + DtoTimeSheet.STATUS_SUBMITTED
                 + "','" + DtoTimeSheet.STATUS_RM_APPROVED
                 + "','" + DtoTimeSheet.STATUS_APPROVED
                 + "','" + DtoTimeSheet.STATUS_PM_APPROVED + "')");
            if (tsDetailList == null || tsDetailList.size() == 0) {
                continue;//此条记录下没有可以审批记录跳到下条记录
            }
            Map acntRidMap = new HashMap();
            boolean mailToRM = false;
           tsDetailList = timeSheetDao.listTimeSheetDetailByTsRid(dtoTsApproval.
                   getTsRid());
           //将该主表下的所有detail资料状态置为TS_Reject
           for (TsTimesheetDetail tsDetail : tsDetailList) {
               if(!dtoTsApproval.getAcntRid().equals(tsDetail.getAccountRid())
                 &&(DtoTimeSheet.STATUS_PM_APPROVED.equals(tsDetail.getStatus())
                    ||DtoTimeSheet.STATUS_APPROVED.equals(tsDetail.getStatus()))) {
                   acntRidMap.put(tsDetail.getAccountRid(), tsDetail.getAccountRid());
               }
               if(DtoTimeSheet.STATUS_PM_APPROVED.equals(tsDetail.getStatus())) {
                   mailToRM = true;
               }
               timeSheetDao.updateTimeSheetDetailStatus(tsDetail.getRid(),
                       DtoTimeSheet.STATUS_REJECTED);
           }
           tsMaster = timeSheetDao.getTimeSheetMaster(dtoTsApproval.getTsRid());
           //将该主表资料状态置为TS_Reject
           timeSheetDao.updateTimeSheetMasterStatus(dtoTsApproval.getTsRid(),
                                                    DtoTimeSheet.STATUS_REJECTED);
           //更新状态改变日期栏位值
           tsMaster = timeSheetDao.getTimeSheetMaster(dtoTsApproval.getTsRid());
           tsMaster.setStatusDate(new Date());
           LDAPUtil ldap = new LDAPUtil();
           String userName = "";
           DtoUser user = ldap.findUser(loginId);
           if(user != null) {
           	userName = user.getUserName(); 
           }
           String notes = "This Timesheet had been rejected by "
           	           +userName+" at "+comDate.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss");
           if(reason != null && !reason.equals("")){
           	notes += "\nReason :" + reason + "\n\n";
           } else {
           	notes += "\n\n";
           }
           if(tsMaster.getNotes() != null) {
           	tsMaster.setNotes(notes + tsMaster.getNotes());
           } else {
           	tsMaster.setNotes(notes);
           }
           timeSheetDao.updateTimeSheetMaster(tsMaster);
           
           //设置P3中的Timesheet状态为TS_Reject
           approvalAssistService.setTimesheetStatusInP3(tsMaster, DtoTimeSheet.STATUS_REJECTED);

           //发送邮件
           acntRidList = new ArrayList<Long>(acntRidMap.values());
           approvalAssistService.searchPersonAndMailForPM(tsMaster, vmFilePM,
                                   DtoTsApproval.DTO_APPROVAL_PMANDRM,
                                   loginId, acntRidList, dtoTsApproval.getAcntRid(),
                                   mailToRM, reason);
       }
    }

    /**
     * RM驳回工时单，需要eMail通知相关人员
     *     Reject TsMaster及其所有TsDetail
     *     发送eMail通知填写人员，抄送给对此工时单执行过审批动作的人员（RM和其他PM）
     * @param tsList List<DtoTsApproval>
     * @param loginId String
     */
    public void rmReject(List<DtoTsApproval> tsList, String loginId, String reason) {
        List<TsTimesheetDetail> tsDetailList = null;
        List<Long> acntRidList = null;
        for (DtoTsApproval dtoTsApproval : tsList) {
            tsDetailList = timeSheetDao.listTimeSheetDetailByTsRidStatus(
                    dtoTsApproval.getTsRid(),
                    "('" + DtoTimeSheet.STATUS_SUBMITTED + "','"
                         + DtoTimeSheet.STATUS_PM_APPROVED + "','"
                         + DtoTimeSheet.STATUS_APPROVED + "','"
                         + DtoTimeSheet.STATUS_RM_APPROVED + "')");
            if (tsDetailList == null || tsDetailList.size() == 0) {
                continue;//此条记录下没有可以审批记录跳到下条记录
            }
            Map acntRidMap = new HashMap();
            tsDetailList = timeSheetDao.listTimeSheetDetailByTsRid(dtoTsApproval.
                    getTsRid());
            //将该主表下的所有detail资料状态置为TS_Reject
            for (TsTimesheetDetail tsDetail : tsDetailList) {
                if(DtoTimeSheet.STATUS_PM_APPROVED.equals(tsDetail.getStatus())
                 ||DtoTimeSheet.STATUS_APPROVED.equals(tsDetail.getStatus())){
                    acntRidMap.put(tsDetail.getAccountRid(), tsDetail.getAccountRid());
                }
                timeSheetDao.updateTimeSheetDetailStatus(tsDetail.getRid(),
                        DtoTimeSheet.STATUS_REJECTED);
            }
            //将该主表资料状态置为TS_Reject
            timeSheetDao.updateTimeSheetMasterStatus(dtoTsApproval.getTsRid(),
                                                     DtoTimeSheet.STATUS_REJECTED);
            //更新状态改变日期栏位值
            TsTimesheetMaster tsMaster = timeSheetDao.getTimeSheetMaster(
                    dtoTsApproval.getTsRid());
            tsMaster.setStatusDate(new Date());
            LDAPUtil ldap = new LDAPUtil();
            String userName = "";
            DtoUser user = ldap.findUser(loginId);
            if(user != null) {
            	userName = user.getUserName(); 
            }
            String notes = "This Timesheet had been rejected by "
            	           +userName+" at "+comDate.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss");
            if(reason != null && !reason.equals("")){
               	notes += "\nReason :" + reason + "\n\n";
               } else {
               	notes += "\n\n";
               }
            if(tsMaster.getNotes() != null) {
            	tsMaster.setNotes(notes + tsMaster.getNotes());
            } else {
            	tsMaster.setNotes(notes);
            }
            timeSheetDao.updateTimeSheetMaster(tsMaster);
            
            //设置P3中的Timesheet状态为TS_Reject
            approvalAssistService.setTimesheetStatusInP3(tsMaster, DtoTimeSheet.STATUS_REJECTED);
            
            //发送邮件
            acntRidList = new ArrayList<Long>(acntRidMap.values());
            approvalAssistService.searchPersonAndMailForRM(tsMaster, vmFileRM,
                                                      DtoTsApproval.DTO_APPROVAL_PMANDRM,
                                                      loginId, acntRidList, reason);
        }
    }

    public void setTimeSheetDao(ITimeSheetDao timeSheetDao) {
        this.timeSheetDao = timeSheetDao;
    }

    public void setApprovalAssistService(IApprovalAssistService
                                        approvalAssistService) {
        this.approvalAssistService = approvalAssistService;
    }

    public void setApprovalDao(IApprovalDao approvalDao) {
        this.approvalDao = approvalDao;
    }

	public void setAccountDao(IAccountDao accountDao) {
		this.accountDao = accountDao;
	}

	public void setRmMaintService(IRmMaintService rmMaintService) {
		this.rmMaintService = rmMaintService;
	}

}
