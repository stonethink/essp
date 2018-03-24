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
 * <p>Description: ��������ΪRM��������ʱ����������ʵ��</p>
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
     * �г�ָ����Ŀ��ʱ������Ĺ�ʱ��
     *      �����̲���PM����������ִ�иö������׳��쳣
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
     * �г�ָ��ʱ�������ڣ���ǰ�û�ΪRM������Ա���Ĺ�ʱ��
     *       �г�״̬Ϊ��TS_Review�Ĺ�ʱ��
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
        				   loginId, begin, end, "('"+DtoTsApproval.STATUS_SUBMITTED+"')");
        	   } else if(DtoTsApproval.DTO_QUERY_WAY_DEALED.equals(queryWay)) {
        		   list = timeSheetDao.listTimeSheetMasterByDateStatus(
        				   loginId, begin, end, "('"+DtoTsApproval.STATUS_APPROVED+"')");
        	   }
               
               if(list == null || list.size() == 0) {
                   continue;//��������ȡ��һ�˵�����
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
					continue;// ���������Ӧ��ʱ�䷢�������򲻽���¼�����б��У�����ѭ����һ��
				}
				resultList.add(dtoTsApproval);
			}
           }
       
        return resultList;
    }

    /**
	 * PM��׼��ʱ�� �����̲���PM����������ִ�иö������׳��쳣
	 * 
	 * @param tsList
	 *            List<DtoTsApproval>
	 */
    public void pmApproval(List<DtoTsApproval> tsList) {
        throw new BusinessException("error.logic.common.noPmApproval","This method should not be invoked!");
    }

    /**
     * RM��׼��ʱ��
     *     �����ǰ��ʱ��״̬ΪTS_Review��ת��״̬ΪTS_PMApprov
     * @param tsList List<DtoTsApproval>
     */
    public void rmApproval(List<DtoTsApproval> tsList) {
        List<TsTimesheetDetail> tsDetailList = null;
        TsTimesheetMaster tsMaster = null;
        for(DtoTsApproval dtoTsApproval : tsList) {
            tsDetailList = timeSheetDao.listTimeSheetDetailByTsRidStatus(
                    dtoTsApproval.getTsRid(), "('"+DtoTimeSheet.STATUS_SUBMITTED+"')");
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
     *     �����̲���PM����������ִ�иö������׳��쳣
     * @param tsList List<DtoTsApproval>
     * @param loginId String
     */
    public void pmReject(List<DtoTsApproval> tsList, String loginId, String reason) {
        throw new BusinessException("error.logic.common.noPmApproval","This method should not be invoked!");
    }

    /**
     * RM���ع�ʱ������ҪeMail֪ͨ�����Ա
     *     Reject TsMaster��������TsDetail
     *     ����eMail֪ͨ��д��Ա
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
                continue;//������¼��û�п���������¼����������¼
            }

            tsDetailList = timeSheetDao.listTimeSheetDetailByTsRid(dtoTsApproval.
                    getTsRid());
            //���������µ�����detail����״̬��ΪTS_Reject
            for (TsTimesheetDetail tsDetail : tsDetailList) {
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
