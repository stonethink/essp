package server.essp.timesheet.approval.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import c2s.essp.timesheet.approval.DtoTsApproval;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.approval.service.IApprovalService;
import server.framework.common.BusinessException;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheetPeriod;

/**
 * <p>Title: AcApprovalByPm</p>
 *
 * <p>Description: PM批准工时单 Action</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class AcApprovalByPm extends AbstractESSPAction {

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
       List listForApproval = (List) data.getInputInfo()
                              .getInputObj(DtoTsApproval.DTO_APPROVAL_LIST);
       IApprovalService service = (IApprovalService) this.getBean("approvalService");
       service.approvalByPM(listForApproval, this.getUser().getUserLoginId());
    }

}
