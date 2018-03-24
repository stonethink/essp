package server.essp.timesheet.approval.action;

import server.framework.common.BusinessException;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import c2s.essp.timesheet.approval.DtoTsApproval;
import java.util.List;
import server.essp.timesheet.approval.service.IApprovalService;

/**
 * <p>Title: AcRejectByPm</p>
 *
 * <p>Description: PM驳回工时单 Action</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class AcRejectByPm extends AbstractESSPAction {

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
        List listForReject = (List) data.getInputInfo()
                             .getInputObj(DtoTsApproval.DTO_APPROVAL_LIST);
        String reason = (String) data.getInputInfo().getInputObj(DtoTsApproval.DTO_REJECT_REASON);
        IApprovalService service = (IApprovalService)this.getBean("approvalService");
        service.rejectByPM(listForReject, this.getUser().getUserLoginId(),
        				   this.getUser().getUserLoginId(), reason);

    }

}
