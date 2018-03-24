package server.essp.timesheet.approval.action;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import c2s.essp.timesheet.approval.DtoTsApproval;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.approval.service.IApprovalService;
import server.framework.common.BusinessException;

/**
 * <p>Title: AcListProjectByManager</p>
 *
 * <p>Description: 列出当前用户为项目经理的项目 Action</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class AcListProjectByManager extends AbstractESSPAction {

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
        IApprovalService service = (IApprovalService) this.getBean("approvalService");
        Vector comItems = service.listProjectByManager(this.getUser().getUserLoginId());
        data.getReturnInfo().setReturnObj(DtoTsApproval.DTO_COMBOX_ITEM, comItems);
    }
}
