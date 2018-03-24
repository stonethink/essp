package server.essp.timesheet.approval.service;

import java.util.Date;
import java.util.List;

import server.essp.timesheet.account.dao.IAccountDao;
import server.essp.timesheet.approval.dao.IApprovalDao;
import server.essp.timesheet.preference.dao.IPreferenceDao;
import server.essp.timesheet.rmmaint.service.IRmMaintService;
import server.essp.timesheet.weeklyreport.dao.ITimeSheetDao;
import server.framework.common.BusinessException;
import c2s.essp.timesheet.approval.DtoTsApproval;
import c2s.essp.timesheet.preference.DtoPreference;
import db.essp.timesheet.TsHumanBase;
import db.essp.timesheet.TsParameter;

/**
 * <p>Title: PmApprovalProxy</p>
 *
 * <p>Description: ��������
 *        ���ݹ������������ȷ��ʹ����һ����������</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class ApprovalProxy implements IApproval {
    private ITimeSheetDao timeSheetDao;
    private IApprovalDao approvalDao;
    private IPreferenceDao preferenceDao;
    private IAccountDao accountDao;
    private IApproval approval;
    private IApprovalAssistService approvalAssistService;
    private IRmMaintService rmMaintService;
    private String currentLevel = "";//���浱ǰ��������
    private static String loginId;
    public static void setEmployeeId(String employeeId) {
    	loginId = employeeId;
	}

	/**
     * �ж����������Ƿ����仯
     * @return boolean
     */
    private boolean checkLevelChange() {
        boolean isChanged = false;
        String site = rmMaintService.getSite(loginId);
        if(site == null) {
        	throw new BusinessException("error.logic.TimeSheetServiceImp.noSite", 
        			loginId + " has no site info! ");
        }
        TsParameter tsPara = preferenceDao.loadPreferenceBySite(site);
        if(tsPara.getTsApprovalLevel() == null || tsPara.getTsApprovalLevel().equals("")) {
        	throw new BusinessException("error.logic.common.noLevel","There is no approval level in the user's site!");
        }
        if(!tsPara.getTsApprovalLevel().equals(currentLevel)) {
            currentLevel = tsPara.getTsApprovalLevel();
            isChanged = true;
        }
        return isChanged;
    }

    /**
     * �г�ָ����Ŀ��ʱ������Ĺ�ʱ��
     * @param acntRid Long
     * @param begin Date
     * @param end Date
     * @param queryWay String
     * @param managerId String
     * @param site String 
     * @return List
     */
    public List<DtoTsApproval> pmList(Long acntRid, Date begin, Date end, 
    		String queryWay, String managerId) {
        if(approval == null || checkLevelChange()){
            setApproval();
        }
        return approval.pmList(acntRid, begin, end, queryWay, managerId);
    }

    /**
     * �г�ָ��ʱ�������ڣ���ǰ�û�ΪRM������Ա���Ĺ�ʱ��
     * @param begin Date
     * @param end Date
     * @param managerId String
     * @param queryWay String
     * @return List
     */
    public List<DtoTsApproval> rmList(String managerId, Date begin, Date end, String queryWay) {
        if(approval == null || checkLevelChange()){
            setApproval();
        }

        return approval.rmList(managerId, begin, end, queryWay);
    }

    /**
     * PM��׼��ʱ��
     * @param tsList List
     */
    public void pmApproval(List tsList) {
        if(approval == null || checkLevelChange()){
            setApproval();
        }

        approval.pmApproval(tsList);
    }

    /**
     * RM��׼��ʱ��
     * @param tsList List
     */
    public void rmApproval(List tsList) {
        if(approval == null || checkLevelChange()){
            setApproval();
        }

        approval.rmApproval(tsList);
    }

    /**
     * PM���ع�ʱ������ҪeMail֪ͨ�����Ա
     * @param tsList List
     * @param loginId String
     * @param reason String
     */
    public void pmReject(List tsList, String loginId, String reason) {
        if(approval == null || checkLevelChange()){
            setApproval();
        }

        approval.pmReject(tsList, loginId, reason);
    }

    /**
     * RM���ع�ʱ������ҪeMail֪ͨ�����Ա
     * @param tsList List
     * @param loginId
     */
    public void rmReject(List tsList, String loginId,String reason) {
        if(approval == null || checkLevelChange()){
            setApproval();
        }

        approval.rmReject(tsList, loginId, reason);
    }

    public void setPreferenceDao(IPreferenceDao preferenceDao) {
        this.preferenceDao = preferenceDao;
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


    private void setApproval() {
    	String site = rmMaintService.getSite(loginId);
    	if(site == null) {
        	throw new BusinessException("error.logic.TimeSheetServiceImp.noSite", 
        			loginId + " has no site info! ");
        }
    	TsParameter tsPara = preferenceDao.loadPreferenceBySite(site);
    	if(tsPara.getTsApprovalLevel() == null || tsPara.getTsApprovalLevel().equals("")) {
        	throw new BusinessException("error.logic.common.noLevel","There is no approval level in the user's site!");
        }
        String level = tsPara.getTsApprovalLevel();
       if(DtoPreference.APPROVAL_LEVEL_RM.equals(level)) {
           approval = new ApprovalForRmOnly(timeSheetDao,
                                            approvalAssistService,
                                            rmMaintService);
       } else if(DtoPreference.APPROVAL_LEVEL_PM.equals(level)) {
           approval = new ApprovalForPmOnly(timeSheetDao,
                                            approvalAssistService, accountDao,
                                            approvalDao);
       } else if(DtoPreference.APPROVAL_LEVEL_PM_AND_RM.equals(level)) {
           approval = new ApprovalForPmAndRm(approvalDao, timeSheetDao,
                                             approvalAssistService,
                                             accountDao, rmMaintService);
       } else {
           approval = new ApprovalForPmBeforeRm(approvalDao, timeSheetDao,
                                                approvalAssistService,
                                                accountDao, rmMaintService);
       }
    }

	public void setAccountDao(IAccountDao accountDao) {
		this.accountDao = accountDao;
	}

	public void setRmMaintService(IRmMaintService rmMaintService) {
		this.rmMaintService = rmMaintService;
	}
}
