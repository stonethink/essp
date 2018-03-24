package server.essp.timesheet.workscope.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.workscope.service.IWorkScopeService;
import server.framework.common.BusinessException;
import c2s.essp.timesheet.workscope.DtoAccount;
/**
 * <p>Description:显示符合条件的Account集合 </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author tbh
 * @version 1.0
 */
public class AcAccountList extends AbstractESSPAction{
    public void executeAct(HttpServletRequest request, HttpServletResponse response,
                           TransactionData data) throws BusinessException {
    	String loginId = (String)data.getInputInfo().getInputObj(DtoAccount.SCOPE_LOGIN_ID);
        IWorkScopeService lg = (IWorkScopeService) this.getBean("iWorkScopeService");
        if(loginId == null || "".equals(loginId)) {
        	loginId = this.getUser().getUserLoginId();
        }
        data.getReturnInfo().setReturnObj(DtoAccount.KEY_PROJECT_LIST, lg.getAccountList(loginId));
    }
}
