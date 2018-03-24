package server.essp.timesheet.approval.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import c2s.essp.timesheet.account.DtoAccount;
import c2s.essp.timesheet.approval.DtoTsApproval;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheetPeriod;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.approval.service.IApprovalService;
import server.framework.common.BusinessException;
import server.essp.timesheet.preference.service.IPreferenceService;
import c2s.essp.timesheet.preference.DtoPreference;

/**
 * <p>Title: AcListTsByProject</p>
 *
 * <p>Description: 按项目获取指定时间区间内的工时单 Action</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class AcListTsByProject extends AbstractESSPAction {

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
        Long acntRid = (Long) data.getInputInfo().getInputObj(DtoAccount.DTO_RID);
        Date begin = (Date) data.getInputInfo().getInputObj(DtoTimeSheetPeriod.DTO_BEGIN);
        Date end = (Date) data.getInputInfo().getInputObj(DtoTimeSheetPeriod.DTO_END);
        String queryWay = (String) data.getInputInfo().getInputObj(DtoTsApproval.DTO_QUERY_WAY);
        IApprovalService service = (IApprovalService) this.getBean("approvalService");
        List list = service.listTsByProject(acntRid, begin, end, 
        		queryWay, this.getUser().getUserLoginId());
        IPreferenceService preferenceService = (IPreferenceService)
                                     this.getBean("preferenceService");
        DtoPreference dtoPreference = preferenceService.getLoadPreference();

        data.getReturnInfo().setReturnObj(DtoTsApproval.DTO_APPROVAL_LIST, list);
        data.getReturnInfo().setReturnObj(DtoTsApproval.DTO_APPROVAL_LEVEL, dtoPreference.getTsApprovalLevel());

    }

}
