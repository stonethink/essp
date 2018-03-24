package client.essp.timesheet.approval.rm;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import client.essp.timesheet.approval.VwAbstractApprovalList;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheetPeriod;
import c2s.essp.timesheet.approval.DtoTsApproval;
import c2s.dto.DtoComboItem;
import c2s.dto.ReturnInfo;
import c2s.essp.timesheet.account.DtoAccount;
import c2s.dto.InputInfo;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheet;
import java.util.ArrayList;
import c2s.essp.timesheet.preference.DtoPreference;

/**
 * <p>Title: VwRmApprovalList</p>
 *
 * <p>Description: 资源经理工时审批，工时列表界面</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class VwRmApprovalList extends VwAbstractApprovalList {

    private final static String actionId_LoadTsListForRm = "FTSLoadTsListForRm";
    private final static String actionId_ApprovalTsListForRm = "FTSApprovalTsListForRm";
    private final static String actionId_RejectTsListForRm = "FTSRejectTsListForRm";
    private String approvalLevel;
    /**
     * 确认工时单
     *
     * @param selectedRowDataList List
     * @return boolean
     */
    protected boolean actionPerformedApproval(List selectedRowDataList) {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionId_ApprovalTsListForRm);
        inputInfo.setInputObj(DtoTsApproval.DTO_APPROVAL_LIST, selectedRowDataList);
        ReturnInfo returnInfo = this.accessData(inputInfo);
        if (returnInfo.isError() == false) {
            return true;
        }
        return false;

    }

    /**
     * 驳回工时单
     *
     * @param selectedRowDataList List
     * @return boolean
     */
    protected boolean actionPerformedReject(List selectedRowDataList, String reason) {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionId_RejectTsListForRm);
        inputInfo.setInputObj(DtoTsApproval.DTO_APPROVAL_LIST, selectedRowDataList);
        inputInfo.setInputObj(DtoTsApproval.DTO_REJECT_REASON, reason);
        ReturnInfo returnInfo = this.accessData(inputInfo);
        if (returnInfo.isError() == false) {
            return true;
        }
        return false;
    }


    /**
     * 加载当前用户为主管的Project或Dept
     *
     * @return Vector<DtoComboItem>
     */
    protected Vector loadAccounts() {
        return null;
    }

    /**
     * 加载待确认的工时单
     *
     * @return List
     * @param accountRid Long
     * @param begin Date
     * @param end Date
     */
    protected List loadTsList(Long accountRid, Date begin, Date end, String queryWay) {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionId_LoadTsListForRm);
        inputInfo.setInputObj(DtoAccount.DTO_RID, accountRid);
        inputInfo.setInputObj(DtoTimeSheetPeriod.DTO_BEGIN, begin);
        inputInfo.setInputObj(DtoTimeSheetPeriod.DTO_END, end);
        inputInfo.setInputObj(DtoTsApproval.DTO_QUERY_WAY, queryWay);
        ReturnInfo returnInfo = this.accessData(inputInfo);
        List list = null;
        if(returnInfo.isError() == false){
            list = (List) returnInfo.getReturnObj(DtoTsApproval.DTO_APPROVAL_LIST);
            approvalLevel = (String) returnInfo.getReturnObj(DtoTsApproval.DTO_APPROVAL_LEVEL);
        }
        return list;
    }

    protected List<DtoTsApproval> getCanApprovalList(List<DtoTsApproval> selectedList) {
        List canApprovalList = new ArrayList();
		for (DtoTsApproval dto : selectedList) {
			if (dto.isCanOp()) {
				canApprovalList.add(dto);
			}
		}
		return canApprovalList;
    }

    protected List<DtoTsApproval> getCanRejectList(List<DtoTsApproval> selectedList) {
        List canRejectList = new ArrayList();
        if(DtoPreference.APPROVAL_LEVEL_PM_BEFORE_RM.equals(approvalLevel)) {
            for (DtoTsApproval dto : selectedList) {
                if (DtoTimeSheet.STATUS_PM_APPROVED.equals(dto.getStatus())
                    || DtoTimeSheet.STATUS_APPROVED.equals(dto.getStatus())) {
                    canRejectList.add(dto);
                }
            }

        } else {
            for (DtoTsApproval dto : selectedList) {
                if (DtoTimeSheet.STATUS_SUBMITTED.equals(dto.getStatus())
                    || DtoTimeSheet.STATUS_PM_APPROVED.equals(dto.getStatus())
                    || DtoTimeSheet.STATUS_RM_APPROVED.equals(dto.getStatus())
                    || DtoTimeSheet.STATUS_APPROVED.equals(dto.getStatus())) {
                    canRejectList.add(dto);
                }
            }
        }
        return canRejectList;

    }

    public void performeAccountOrPeriodChanged(Long rid,
                                               DtoTimeSheetPeriod period,
                                               String queryWay) {
        this.setBegin(period.getBeginDate());
        this.setEnd(period.getEndDate());
        this.setQueryWay(queryWay);
    }
}
