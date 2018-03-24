package server.essp.timesheet.workscope.action;

import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import server.essp.timesheet.workscope.service.IWorkScopeService;
import javax.servlet.http.HttpServletResponse;
import c2s.essp.timesheet.code.DtoCodeKey;
import c2s.essp.timesheet.workscope.DtoAccount;

/**
 * <p>Title:AcLeaveCodeList </p>
 *
 * <p>Description:列出所有的LeaveCodeList </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author tbh
 * @version 1.0
 */
public class AcLeaveCodeList  extends AbstractESSPAction{
    public void executeAct(HttpServletRequest request, HttpServletResponse response,
                           TransactionData data) throws BusinessException {
    	Long leaveCodeTypeRid  = (Long) data.getInputInfo().getInputObj(DtoAccount.LEAVE_CODE_TYPE_RID);
        IWorkScopeService lg = (IWorkScopeService) this.getBean("iWorkScopeService");
        data.getReturnInfo().setReturnObj(DtoCodeKey.CODE_LEAVE_LIST, lg.getCodeLeave(leaveCodeTypeRid));
    }

}
