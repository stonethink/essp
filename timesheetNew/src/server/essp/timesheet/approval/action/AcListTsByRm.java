package server.essp.timesheet.approval.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import c2s.essp.timesheet.approval.DtoTsApproval;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheetPeriod;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.approval.service.IApprovalService;
import server.framework.common.BusinessException;
import server.essp.timesheet.preference.service.IPreferenceService;
import c2s.essp.timesheet.preference.DtoPreference;

/**
 * <p>Title: AcListTsByRm</p>
 *
 * <p>Description: 列出当前用户为RM的所有员工在指定时间段内的工时单 Action</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class AcListTsByRm extends AbstractESSPAction {

    /**
     * executeAct
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData data) throws BusinessException {
        Date begin = (Date) data.getInputInfo().getInputObj(DtoTimeSheetPeriod.DTO_BEGIN);
        Date end = (Date) data.getInputInfo().getInputObj(DtoTimeSheetPeriod.DTO_END);
        String queryWay = (String) data.getInputInfo().getInputObj(DtoTsApproval.DTO_QUERY_WAY);
        IApprovalService service = (IApprovalService) this.getBean("approvalService");
        List list = service.listTsByRm(this.getUser().getUserLoginId(), 
        		begin, end, queryWay);
        IPreferenceService preferenceService = (IPreferenceService)
                                               this.getBean("preferenceService");
        DtoPreference dtoPreference = preferenceService.getLoadPreference();

        data.getReturnInfo().setReturnObj(DtoTsApproval.DTO_APPROVAL_LIST, list);
        data.getReturnInfo().setReturnObj(DtoTsApproval.DTO_APPROVAL_LEVEL, dtoPreference.getTsApprovalLevel());
    }

}
