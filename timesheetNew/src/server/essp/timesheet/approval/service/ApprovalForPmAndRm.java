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
 * <p>Description: ��������ΪPM��RMͬʱ����ʱ����������ʵ��</p>
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
     * �г�ָ����Ŀ��ʱ������Ĺ�ʱ��
     *    �г�״̬Ϊ��TS_Review��TS_RMApprov�Ĺ�ʱ��
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
        //ѭ��ȡ��ÿ���˵�Timesheet
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
			//�޸�Ϊʵ�ʹ�ʱ�����Ӱ๤ʱ by zhengwenhai 20081204
			dtoTsApproval.setActualHours(dtoTsApproval.getActualHours() + dtoTsApproval.getLeaveHours());
			resultList.add(dtoTsApproval);
        }
        return resultList;
    }

    /**
	 * �г�ָ��ʱ�������ڣ���ǰ�û�ΪRM������Ա���Ĺ�ʱ�� �г�״̬Ϊ��TS_Review��TS_PMApprov�Ĺ�ʱ��
	 * 
	 * @param begin Date
	 * @param end Date
	 * @param managerId String 
	 * @param queryWay String
	 * @return List<DtoTsApproval>
	 */
    public List<DtoTsApproval> rmList(String managerId, Date begin, Date end, String queryWay) {
        	List<DtoTsApproval> resultList = new ArrayList<DtoTsApproval>();
        	//�г���ǰ��Դ��������µ�������Ա
            List<String> personList = rmMaintService.getHumanUnderRM(managerId);
            DtoTsApproval dtoTsApproval = null;
            List<TsTimesheetMaster> list = null;
            //ѭ��ȡ��ÿ���˵�Timesheet
            for (String loginId : personList) {
                //ͨ��loginId�Ϳ�ʼ��������ȡ��TimeSheet����
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
                    continue; //��������ȡ��һ�˵�����
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
                		continue; // ���������Ӧ��ʱ�䷢�������򲻽���¼�����б��У�����ѭ����һ��
                	}
                	resultList.add(dtoTsApproval);
                }
            }
        
        return resultList;
    }

    /**
	 * PM��׼��ʱ�� �����ǰ��ʱ��״̬ΪTS_RMApprov��ת��״̬ΪTS_Approv �����ǰ��ʱ��״̬ΪTS_Review��{
	 * �����ʱ����RM����ǵ�ǰ�û��� ת��״̬ΪTS_Approv������ת��״̬ΪTS_PMApprov }
	 * ���������ִ���κζ�����������ǰ̨�鿴��ʱ�򱻸ı�״̬�ˣ��类����PM Reject��
	 * 
	 * ��飺����TsDetail״̬һ�£�����TsMaster��ͬ����ı�TsMaster״̬һ��
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
                continue;//������¼��û�п���������¼����������¼
            }
            String updateStatus = "";
            //ѭ��ȡ��tsDetail��ʱ���������ʱ��״̬ΪTS_Review����˺����ΪTS_PMApprov
            //�����ʱ��״̬ΪTS_RMApprov����˺����ΪTS_Approv
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
            //��ѯ��TimesheetMaster���Ƿ񻹴���û�б�PM��detail����
            tsDetailList = timeSheetDao.listTimeSheetDetailNotEqualsStatusByTsRid(
                    dtoTsApproval.getTsRid(), DtoTimeSheet.STATUS_PM_APPROVED);
            //���û���������е�״̬��ΪTS_PMApprov
            if (tsDetailList == null || tsDetailList.size() == 0) {
                timeSheetDao.updateTimeSheetMasterStatus(dtoTsApproval.getTsRid(),
                        DtoTimeSheet.STATUS_PM_APPROVED);
                //����״̬�ı�������λֵ
                tsMaster = timeSheetDao.getTimeSheetMaster(dtoTsApproval.
                        getTsRid());
                tsMaster.setStatusDate(new Date());
                timeSheetDao.updateTimeSheetMaster(tsMaster);
            }
            //��ѯ��TimesheetMaster���Ƿ񻹴���û�б���ȫ��ˣ�����PM��RM������ˣ���detail����
            tsDetailList = timeSheetDao.listTimeSheetDetailNotEqualsStatusByTsRid(
                    dtoTsApproval.getTsRid(), DtoTimeSheet.STATUS_APPROVED);
            //���û���������е�״̬��ΪTS_Approv
            if (tsDetailList == null || tsDetailList.size() == 0) {
                timeSheetDao.updateTimeSheetMasterStatus(dtoTsApproval.getTsRid(),
                        DtoTimeSheet.STATUS_APPROVED);
                //����״̬�ı�������λֵ
                tsMaster = timeSheetDao.getTimeSheetMaster(dtoTsApproval.getTsRid());
                tsMaster.setStatusDate(new Date());
                timeSheetDao.updateTimeSheetMaster(tsMaster);
                //��дP3����
                approvalAssistService.writeBackToP3(tsMaster);

            }
        }
    }

    /**
     * RM��׼��ʱ��
     *     �����ǰ��ʱ��״̬ΪTS_PMApprov��ת��״̬ΪTS_Approv
     *     �����ǰ��ʱ��״̬ΪTS_Review��{
     *                   �����ʱ��������Detail������Ŀ��PM���ǵ�ǰ�û���
     *                   ת��״̬ΪTS_Approv������ת��״̬ΪTS_RMApprov }
     *     ���������ִ���κζ�����������ǰ̨�鿴��ʱ�򱻸ı�״̬�ˣ��类PM Reject��
     *
     *     ��飺����TsDetail״̬һ�£�����TsMaster��ͬ����ı�TsMaster״̬һ��
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
                continue;//������¼��û�п���������¼����������¼
            }
            String updateStatus = "";
            //ѭ��ȡ��tsDetail��ʱ���������ʱ��״̬ΪTS_Review����˺����ΪTS_RMApprov
            //�����ʱ��״̬ΪTS_PMApprov����˺����ΪTS_Approv
            for (TsTimesheetDetail tsDetail : tsDetailList) {
                if (tsDetail.getStatus().equals(DtoTimeSheet.STATUS_SUBMITTED)) {
                   updateStatus = DtoTimeSheet.STATUS_RM_APPROVED;
               } else if (tsDetail.getStatus().equals(DtoTimeSheet.STATUS_PM_APPROVED)) {
                   updateStatus = DtoTimeSheet.STATUS_APPROVED;
               }
                timeSheetDao.updateTimeSheetDetailStatus(tsDetail.getRid(),
                        updateStatus);
            }
            //��ѯ��TimesheetMaster���Ƿ���û�б�RM��˵�detail����
            tsDetailList = timeSheetDao.listTimeSheetDetailNotEqualsStatusByTsRid(
                    dtoTsApproval.getTsRid(), DtoTimeSheet.STATUS_RM_APPROVED);
            //���û���������е�״̬��ΪTS_RMApprov
            if (tsDetailList == null || tsDetailList.size() == 0) {
                timeSheetDao.updateTimeSheetMasterStatus(dtoTsApproval.getTsRid(),
                        DtoTimeSheet.STATUS_RM_APPROVED);
                //����״̬�ı�������λֵ
                tsMaster = timeSheetDao.getTimeSheetMaster(dtoTsApproval.
                        getTsRid());
                tsMaster.setStatusDate(new Date());
                timeSheetDao.updateTimeSheetMaster(tsMaster);
            }
            //��ѯ��TimesheetMaster���Ƿ񻹴���û�б���ȫ��ˣ�RM��PM����ˣ���detail����
            tsDetailList = timeSheetDao.listTimeSheetDetailNotEqualsStatusByTsRid(
                    dtoTsApproval.getTsRid(), DtoTimeSheet.STATUS_APPROVED);
            //���û���������е�״̬��ΪTS_Approv
            if (tsDetailList == null || tsDetailList.size() == 0) {
                timeSheetDao.updateTimeSheetMasterStatus(dtoTsApproval.getTsRid(),
                        DtoTimeSheet.STATUS_APPROVED);
                //����״̬�ı�������λֵ
                tsMaster = timeSheetDao.getTimeSheetMaster(dtoTsApproval.getTsRid());
                tsMaster.setStatusDate(new Date());
                timeSheetDao.updateTimeSheetMaster(tsMaster);
                //��дP3����
                approvalAssistService.writeBackToP3(tsMaster);

            }
        }
    }

    /**
     * PM���ع�ʱ������ҪeMail֪ͨ�����Ա
     *     Reject TsMaster��������TsDetail
     *     ����eMail֪ͨ��д��Ա�����͸��Դ˹�ʱ��ִ�й�������������Ա��RM������PM��
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
                continue;//������¼��û�п���������¼����������¼
            }
            Map acntRidMap = new HashMap();
            boolean mailToRM = false;
           tsDetailList = timeSheetDao.listTimeSheetDetailByTsRid(dtoTsApproval.
                   getTsRid());
           //���������µ�����detail����״̬��ΪTS_Reject
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
           //������������״̬��ΪTS_Reject
           timeSheetDao.updateTimeSheetMasterStatus(dtoTsApproval.getTsRid(),
                                                    DtoTimeSheet.STATUS_REJECTED);
           //����״̬�ı�������λֵ
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
           
           //����P3�е�Timesheet״̬ΪTS_Reject
           approvalAssistService.setTimesheetStatusInP3(tsMaster, DtoTimeSheet.STATUS_REJECTED);

           //�����ʼ�
           acntRidList = new ArrayList<Long>(acntRidMap.values());
           approvalAssistService.searchPersonAndMailForPM(tsMaster, vmFilePM,
                                   DtoTsApproval.DTO_APPROVAL_PMANDRM,
                                   loginId, acntRidList, dtoTsApproval.getAcntRid(),
                                   mailToRM, reason);
       }
    }

    /**
     * RM���ع�ʱ������ҪeMail֪ͨ�����Ա
     *     Reject TsMaster��������TsDetail
     *     ����eMail֪ͨ��д��Ա�����͸��Դ˹�ʱ��ִ�й�������������Ա��RM������PM��
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
                continue;//������¼��û�п���������¼����������¼
            }
            Map acntRidMap = new HashMap();
            tsDetailList = timeSheetDao.listTimeSheetDetailByTsRid(dtoTsApproval.
                    getTsRid());
            //���������µ�����detail����״̬��ΪTS_Reject
            for (TsTimesheetDetail tsDetail : tsDetailList) {
                if(DtoTimeSheet.STATUS_PM_APPROVED.equals(tsDetail.getStatus())
                 ||DtoTimeSheet.STATUS_APPROVED.equals(tsDetail.getStatus())){
                    acntRidMap.put(tsDetail.getAccountRid(), tsDetail.getAccountRid());
                }
                timeSheetDao.updateTimeSheetDetailStatus(tsDetail.getRid(),
                        DtoTimeSheet.STATUS_REJECTED);
            }
            //������������״̬��ΪTS_Reject
            timeSheetDao.updateTimeSheetMasterStatus(dtoTsApproval.getTsRid(),
                                                     DtoTimeSheet.STATUS_REJECTED);
            //����״̬�ı�������λֵ
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
            
            //����P3�е�Timesheet״̬ΪTS_Reject
            approvalAssistService.setTimesheetStatusInP3(tsMaster, DtoTimeSheet.STATUS_REJECTED);
            
            //�����ʼ�
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
