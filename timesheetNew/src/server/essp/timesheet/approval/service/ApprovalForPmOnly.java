package server.essp.timesheet.approval.service;

import java.util.*;

import server.essp.common.ldap.LDAPUtil;
import server.essp.timesheet.account.dao.IAccountDao;
import server.essp.timesheet.approval.dao.IApprovalDao;
import server.essp.timesheet.weeklyreport.dao.ITimeSheetDao;
import server.framework.common.BusinessException;
import c2s.essp.common.user.DtoUser;
import c2s.essp.timesheet.approval.DtoTsApproval;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheet;

import com.wits.util.comDate;

import db.essp.timesheet.*;

/**
 * <p>Title: ApprovalForPmOnly</p>
 *
 * <p>Description: ��������ΪPM��������ʱ����������ʵ��</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu(implemented by wenhaizheng)
 * @version 1.0
 */
public class ApprovalForPmOnly implements IApproval {

    private ITimeSheetDao timeSheetDao;
    private IAccountDao accountDao;
    private IApprovalDao approvalDao;
    private IApprovalAssistService approvalAssistService;
    public static final String vmFile = "mail/template/ts/WeeklyReportRejectedByPM.htm";

    public ApprovalForPmOnly(ITimeSheetDao timeSheetDao,
                            IApprovalAssistService approvalAssistService, 
                            IAccountDao accountDao, IApprovalDao approvalDao) {
       this.setTimeSheetDao(timeSheetDao);
       this.setApprovalAssistService(approvalAssistService);
       this.setAccountDao(accountDao);
       this.setApprovalDao(approvalDao);
   }


    /**
     * �г�ָ����Ŀ��ʱ������Ĺ�ʱ��
     *      �г�״̬Ϊ��TS_Review�Ĺ�ʱ��
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
			status = "('"+DtoTsApproval.STATUS_SUBMITTED+"')";			
		} else if(DtoTsApproval.DTO_QUERY_WAY_DEALED.equals(queryWay)) {
			status = "('"+DtoTsApproval.STATUS_APPROVED+"')";
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
	 * �г�ָ��ʱ�������ڣ���ǰ�û�ΪRM������Ա���Ĺ�ʱ�� �����̲���RM����������ִ�иö������׳��쳣
	 * 
	 * @param begin
	 *            Date
	 * @param end
	 *            Date
	 * @param managerId
	 *            String
	 * @param queryWay
	 *            String
	 * @return List<DtoTsApproval>
	 */
    public List<DtoTsApproval> rmList(String managerId, Date begin, Date end, String queryWay) {
        throw new BusinessException("error.logic.common.noPmApproval","This method should not be invoked!");
    }

    /**
     * PM��׼��ʱ��
     *     �����ǰ��ʱ��״̬ΪTS_Review��ת��״̬ΪTS_Approv
     *     ���������ִ���κζ�����������ǰ̨�鿴��ʱ�򱻸ı�״̬�ˣ��类����PM Reject��
     *
     *     ��飺����TsDetail״̬һ�£�����TsMaster��ͬ����ı�TsMaster״̬һ��
     *          (���TsMaster�µ�����TsDetail���Ѿ���������޸�TsMaster��״̬ΪTS_Approv)
     * @param tsList List<DtoTsApproval>
     */
    public void pmApproval(List<DtoTsApproval> tsList) {
        List<TsTimesheetDetail> tsDetailList = null;
        TsTimesheetMaster tsMaster = null;
        for(DtoTsApproval dtoTsApproval : tsList) {
            tsDetailList = timeSheetDao.listTimeSheetDetailByAcntRidTsRidByStatus(dtoTsApproval
                    .getAcntRid(), dtoTsApproval.getTsRid(),
                    "('" + DtoTimeSheet.STATUS_SUBMITTED + "')");
            if(tsDetailList == null || tsDetailList.size() == 0) {
                continue;//������¼��û�п���������¼����������¼
            }
            for (TsTimesheetDetail tsDetail : tsDetailList) {
                timeSheetDao.updateTimeSheetDetailStatus(tsDetail.getRid(),
                        DtoTimeSheet.STATUS_APPROVED);
            }
            //��ѯ��TimesheetMaster���Ƿ���û�б���˵�detail����
            tsDetailList = timeSheetDao.listTimeSheetDetailNotEqualsStatusByTsRid(
                    dtoTsApproval.getTsRid(), DtoTimeSheet.STATUS_APPROVED);
            //���û���������е�״̬��ΪTS_Approv
            if(tsDetailList == null || tsDetailList.size() == 0) {
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
     *     �����̲���RM����������ִ�иö������׳��쳣
     * @param tsList List<DtoTsApproval>
     */
    public void rmApproval(List<DtoTsApproval> tsList) {
        throw new BusinessException("error.logic.common.noPmApproval","This method should not be invoked!");
    }

    /**
     * PM���ع�ʱ������ҪeMail֪ͨ�����Ա
     *     Reject TsMaster��������TsDetail
     *     ����eMail֪ͨ��д��Ա�����͸��Դ˹�ʱ��ִ�й�������������Ա������PM��
     * @param tsList List<DtoTsApproval>
     * @param loginId String
     */
    public void pmReject(List<DtoTsApproval> tsList, String loginId, String reason) {
        List<TsTimesheetDetail> tsDetailList = null;
        List<Long> acntRidList = null;
        for(DtoTsApproval dtoTsApproval : tsList) {
            tsDetailList = timeSheetDao.listTimeSheetDetailByAcntRidTsRidByStatus(dtoTsApproval
                    .getAcntRid(), dtoTsApproval.getTsRid(),
                    "('" + DtoTimeSheet.STATUS_SUBMITTED
                 + "','" + DtoTimeSheet.STATUS_APPROVED + "')");
            if(tsDetailList == null || tsDetailList.size() == 0) {
                continue;//������¼��û�п���������¼����������¼
            }
            Map acntRidMap = new HashMap();
            tsDetailList = timeSheetDao.listTimeSheetDetailByTsRid(dtoTsApproval.getTsRid());
            //���������µ�����detail����״̬��ΪTS_Reject
            for(TsTimesheetDetail tsDetail : tsDetailList) {
                //�������ר���б���˹�����¼ר��Rid�Ա㷢�ʼ�ʱʹ��
                if(!dtoTsApproval.getAcntRid().equals(tsDetail.getAccountRid())
                  &&DtoTimeSheet.STATUS_APPROVED.equals(tsDetail.getStatus())) {
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
            approvalAssistService.searchPersonAndMailForPM(tsMaster, vmFile,
                                                      DtoTsApproval.DTO_APPROVAL_PMONLY,
                                                      loginId, acntRidList,
                                                      dtoTsApproval.getAcntRid(), false, reason);

        }
    }

    /**
     * RM���ع�ʱ������ҪeMail֪ͨ�����Ա
     *     �����̲���RM����������ִ�иö������׳��쳣
     * @param tsList List<DtoTsApproval>
     * @param loginId String
     */
    public void rmReject(List<DtoTsApproval> tsList, String loginId, String reason) {
        throw new BusinessException("error.logic.common.noPmApproval","This method should not be invoked!");
    }

    public void setTimeSheetDao(ITimeSheetDao timeSheetDao) {
        this.timeSheetDao = timeSheetDao;
    }

    public void setApprovalAssistService(IApprovalAssistService
                                         approvalAssistService) {
        this.approvalAssistService = approvalAssistService;
    }


	public void setAccountDao(IAccountDao accountDao) {
		this.accountDao = accountDao;
	}


	public void setApprovalDao(IApprovalDao approvalDao) {
		this.approvalDao = approvalDao;
	}


}
