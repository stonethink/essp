package server.essp.timesheet.workscope.action;

import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.essp.timesheet.workscope.DtoAccount;
import server.essp.timesheet.workscope.service.IWorkScopeService;
import javax.servlet.http.HttpServletResponse;
import c2s.essp.timesheet.code.DtoCodeKey;

/**
 * <p>Title:AcJobCodeList </p>
 *
 * <p>Description:ÁÐ³öJobCodeList </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author tbh
 * @version 1.0
 */
public class AcJobCodeList  extends AbstractESSPAction{
    public void executeAct(HttpServletRequest request, HttpServletResponse response,
                           TransactionData data) throws BusinessException {

        IWorkScopeService lg = (IWorkScopeService) this.getBean("iWorkScopeService");
        Long codeTypeRid  = (Long) data.getInputInfo().getInputObj(DtoAccount.CODE_TYPE_RID);
        if(codeTypeRid != null){
            data.getReturnInfo().setReturnObj(DtoCodeKey.CODE_Type_LIST,
                                              lg.getJobCode(codeTypeRid));
        }
    }
}
