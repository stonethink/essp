package server.essp.timesheet.approval.service;

import java.util.*;

import server.essp.common.ldap.LDAPUtil;
import server.essp.timesheet.rmmaint.service.IRmMaintService;
import server.essp.timesheet.weeklyreport.dao.ITimeSheetDao;
import server.framework.common.BusinessException;
import c2s.essp.common.user.DtoUser;
import c2s.essp.timesheet.approval.DtoTsApproval;
import c2s.essp.timesheet.preference.DtoPreference;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheet;

import com.wits.util.comDate;

import db.essp.timesheet.TsTimesheetDetail;
import db.essp.timesheet.TsTimesheetMaster;

/**
 * <p>Title: ApprovalForRmOnly</p>
 *
 * <p>Description: 审批设置为RM单独审批时，审批功能实现</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu(implemented by wenhaizheng)
 * @version 1.0
 */
public class ApprovalForRmOnly implements IApproval {

    private ITimeSheetDao timeSheetDao;
    private IApprovalAssistService approvalAssistService;
    private IRmMaintService rmMaintService;
    public static final String vmFile = "mail/template/ts/WeeklyReportRejectedByRM.htm";

    public ApprovalForRmOnly(ITimeSheetDao timeSheetDao,
                             IApprovalAssistService approvalAssistService,
                             IRmMaintService rmMaintService) {
        this.setTimeSheetDao(timeSheetDao);
        this.setApprovalAssistService(approvalAssistService);
        this.setRmMaintService(rmMaintService);
    }
    /**
     * 列出指定项目和时间区间的工时单
     *      此流程不用PM审批，不会执行该动作，抛出异常
     * @param acntRid Long
     * @param begin Date
     * @param end Date
     * @param queryWay String
     * @param managerId String
     * @return List<DtoTsApproval>
     */
    public List<DtoTsApproval> pmList(Long acntRid, Date begin, Date end, 
    		                          String queryWay, String managerId) {
        throw new BusinessException("error.logic.common.noPmApproval","This method should not be invoked!");
    }

    /**
     * 列出指定时间区间内，当前用户为RM的所有员工的工时单
     *       列出状态为：TS_Review的工时单
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
        				   loginId, begin, end, "('"+DtoTsApproval.STATUS_SUBMITTED+"')");
        	   } else if(DtoTsApproval.DTO_QUERY_WAY_DEALED.equals(queryWay)) {
        		   list = timeSheetDao.listTimeSheetMasterByDateStatus(
        				   loginId, begin, end, "('"+DtoTsApproval.STATUS_APPROVED+"')");
        	   }
               
               if(list == null || list.size() == 0) {
                   continue;//不存在则取下一人的资料
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
								tsTimesheetMaster.getEndDate(), dtoTsApproval.getLoginId()));
				if (!approvalAssistService.setHoursOk(dtoTsApproval,
						tsTimesheetMaster.getRid(),
						DtoPreference.APPROVAL_LEVEL_RM)) {
					continue;// 如果设置相应的时间发生错误则不将记录加入列表中，继续循环下一人
				}
				resultList.add(dtoTsApproval);
			}
           }
       
        return resultList;
    }

    /**
	 * PM批准工时单 此流程不用PM审批，不会执行该动作，抛出异常
	 * 
	 * @param tsList
	 *            List<DtoTsApproval>
	 */
    public void pmApproval(List<DtoTsApproval> tsList) {
        throw new BusinessException("error.logic.common.noPmApproval","This method should not be invoked!");
    }

    /**
     * RM批准工时单
     *     如果当前工时单状态为TS_Review，转变状态为TS_PMApprov
     * @param tsList List<DtoTsApproval>
     */
    public void rmApproval(List<DtoTsApproval> tsList) {
        List<TsTimesheetDetail> tsDetailList = null;
        TsTimesheetMaster tsMaster = null;
        for(DtoTsApproval dtoTsApproval : tsList) {
            tsDetailList = timeSheetDao.listTimeSheetDetailByTsRidStatus(
                    dtoTsApproval.getTsRid(), "('"+DtoTimeSheet.STATUS_SUBMITTED+"')");
            if(tsDetailList == null || tsDetailList.size() == 0) {
                continue;//此条记录下没有可以审批记录跳到下条记录
            }
            for (TsTimesheetDetail tsDetail : tsDetailList) {
                timeSheetDao.updateTimeSheetDetailStatus(tsDetail.getRid(),
                        DtoTimeSheet.STATUS_APPROVED);
            }
            //查询该TimesheetMaster下是否还有没有被审核的detail资料
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
     *     此流程不用PM审批，不会执行该动作，抛出异常
     * @param tsList List<DtoTsApproval>
     * @param loginId String
     */
    public void pmReject(List<DtoTsApproval> tsList, String loginId, String reason) {
        throw new BusinessException("error.logic.common.noPmApproval","This method should not be invoked!");
    }

    /**
     * RM驳回工时单，需要eMail通知相关人员
     *     Reject TsMaster及其所有TsDetail
     *     发送eMail通知填写人员
     * @param tsList List<DtoTsApproval>
     * @param loginId String
     */
    public void rmReject(List<DtoTsApproval> tsList, String loginId, String reason) {
        List<TsTimesheetDetail> tsDetailList = null;
        for (DtoTsApproval dtoTsApproval : tsList) {
            tsDetailList = timeSheetDao.listTimeSheetDetailByTsRidStatus(
                    dtoTsApproval.getTsRid(), "('"+DtoTimeSheet.STATUS_SUBMITTED+"','"
                                                  +DtoTimeSheet.STATUS_APPROVED+"')");
            if(tsDetailList == null || tsDetailList.size() == 0) {
                continue;//此条记录下没有可以审批记录跳到下条记录
            }

            tsDetailList = timeSheetDao.listTimeSheetDetailByTsRid(dtoTsApproval.
                    getTsRid());
            //将该主表下的所有detail资料状态置为TS_Reject
            for (TsTimesheetDetail tsDetail : tsDetailList) {
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
            approvalAssistService.searchPersonAndMailForRM(tsMaster, vmFile,
                                                      DtoTsApproval.DTO_APPROVAL_RMONLY,
                                                      loginId, new ArrayList(), reason);
        }
    }


    public void setTimeSheetDao(ITimeSheetDao timeSheetDao) {
        this.timeSheetDao = timeSheetDao;
    }

    public void setApprovalAssistService(IApprovalAssistService
                                         approvalAssistService) {
        this.approvalAssistService = approvalAssistService;
    }

	public void setRmMaintService(IRmMaintService rmMaintService) {
		this.rmMaintService = rmMaintService;
	}

}
