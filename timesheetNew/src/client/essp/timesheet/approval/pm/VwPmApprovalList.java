package client.essp.timesheet.approval.pm;

import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import c2s.dto.DtoComboItem;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.timesheet.account.DtoAccount;
import c2s.essp.timesheet.approval.DtoTsApproval;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheetPeriod;
import client.essp.common.loginId.VWJLoginId;
import client.essp.timesheet.approval.VwAbstractApprovalList;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.*;

import java.util.ArrayList;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheet;
import c2s.essp.timesheet.preference.DtoPreference;

/**
 * <p>Title: VwPmApprovalList</p>
 *
 * <p>Description: 项目经理工时审批，工时列表界面</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class VwPmApprovalList extends VwAbstractApprovalList {

     private final static String actionId_LoadAccounts = "FTSLoadAccounts";
     private final static String actionId_LoadTsListForPm = "FTSLoadTsListForPm";
     private final static String actionId_ApprovalTsListForPm = "FTSApprovalTsListForPm";
     private final static String actionId_RejectTsListForPm = "FTSRejectTsListForPm";
     /**
      * 初始化UI
      * @throws Exception
      */
     protected void jbInit() throws Exception {
         Object[][] configs = new Object[][] { {"rsid.common.user", "loginId",
                              VMColumnConfig.UNEDITABLE, new VWJLoginId(),
                              Boolean.FALSE},{"rsid.timesheet.startDate", "startDate",
                              VMColumnConfig.UNEDITABLE, new VWJDate(),
                              Boolean.FALSE}, {"rsid.timesheet.finishDate", "finishDate",
                              VMColumnConfig.UNEDITABLE, new VWJDate(),
                              Boolean.FALSE}, {"rsid.timesheet.standardHours", "standarHours",
                              VMColumnConfig.UNEDITABLE, new VWJReal(),
                              Boolean.FALSE}, {"rsid.timesheet.accountCode", "acntCode",
                              VMColumnConfig.UNEDITABLE, new VWJText(),
                              Boolean.FALSE}, {"rsid.timesheet.actualHours", "actualHours",
                              VMColumnConfig.UNEDITABLE, new VWJReal(),
                              Boolean.FALSE}, {"rsid.timesheet.overtimeHours", "overtimeHours",
                              VMColumnConfig.UNEDITABLE, new VWJReal(),
                              Boolean.FALSE}, {"rsid.timesheet.leaveHours", "leaveHours",
                              VMColumnConfig.UNEDITABLE, new VWJReal(),
                              Boolean.FALSE}, {"rsid.common.status", "statusName",
                              VMColumnConfig.UNEDITABLE, new VWJText(),
                              Boolean.FALSE}
         };
         super.jbInit(configs, DtoTsApproval.class);
//       可排序
         this.getTable().setSortable(true);
     }
    /**
     * 确认工时单
     *
     * @param selectedRowDataList List
     * @return boolean
     */
    protected boolean actionPerformedApproval(List selectedRowDataList) {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionId_ApprovalTsListForPm);
        inputInfo.setInputObj(DtoTsApproval.DTO_APPROVAL_LIST, selectedRowDataList);
        ReturnInfo returnInfo = this.accessData(inputInfo);
        if(returnInfo.isError() == false) {
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
        inputInfo.setActionId(actionId_RejectTsListForPm);
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
    protected Vector<DtoComboItem> loadAccounts() {
        Vector accountItems = null;
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionId_LoadAccounts);
        ReturnInfo returnInfo = this.accessData(inputInfo);
        if(returnInfo.isError() == false) {
            accountItems = (Vector) returnInfo.getReturnObj(
                    DtoTsApproval.DTO_COMBOX_ITEM);
        }
        return accountItems;
    }

    /**
     * 加载待确认的工时单
     *
     * @return List
     * @param accountRid Long
     * @param begin Date
     * @param end Date
     * @param queryWay String
     */
    protected List loadTsList(Long accountRid, Date begin, Date end, String queryWay) {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionId_LoadTsListForPm);
        inputInfo.setInputObj(DtoAccount.DTO_RID, accountRid);
        inputInfo.setInputObj(DtoTimeSheetPeriod.DTO_BEGIN, begin);
        inputInfo.setInputObj(DtoTimeSheetPeriod.DTO_END, end);
        inputInfo.setInputObj(DtoTsApproval.DTO_QUERY_WAY, queryWay);
        ReturnInfo returnInfo = this.accessData(inputInfo);
        List list = null;
        if(returnInfo.isError() == false){
            list = (List) returnInfo.getReturnObj(DtoTsApproval.DTO_APPROVAL_LIST);
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
        for(DtoTsApproval dto : selectedList) {
            if(DtoTimeSheet.STATUS_SUBMITTED.equals(dto.getStatus())
               ||DtoTimeSheet.STATUS_PM_APPROVED.equals(dto.getStatus())
               ||DtoTimeSheet.STATUS_RM_APPROVED.equals(dto.getStatus())
               ||DtoTimeSheet.STATUS_APPROVED.equals(dto.getStatus())){
                canRejectList.add(dto);
            }
        }
        return canRejectList;
    }

    public void performeAccountOrPeriodChanged(Long rid,
                                               DtoTimeSheetPeriod period,
                                               String queryWay) {
        this.setBegin(period.getBeginDate());
        this.setEnd(period.getEndDate());
        this.setAcntRid(rid);
        this.setQueryWay(queryWay);
    }
	


}
