package server.essp.timesheet.outwork.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import server.essp.timesheet.outwork.logic.LgAccount;
import server.essp.timesheet.outwork.logic.LgOutWork;
import db.essp.timesheet.TsOutWorker;
import server.essp.timesheet.outwork.form.AfOutWorkerForm;
import server.essp.common.syscode.LgSysParameter;
import java.util.List;
import java.util.ArrayList;
import server.essp.timesheet.outwork.viewbean.vbOutWorker;

public class AcOutWorkerModifyPre extends AbstractESSPAction {
    public AcOutWorkerModifyPre() {
    }

    /**
     * 修改一条出差信息
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     * @todo Implement this server.framework.action.AbstractBusinessAction
     *   method
     */
    public void executeAct(HttpServletRequest request, HttpServletResponse response, TransactionData data) throws BusinessException {
        if (request.getParameter("rid") != null && !request.getParameter("rid").equals("")) {
            String rid=request.getParameter("rid");
            LgOutWork logic=new LgOutWork();
            TsOutWorker outWorker = logic.loadByRid(new Long(rid));
            LgAccount lgAccount = new LgAccount();
            String orgCode = lgAccount.getAccountOrgCode(outWorker.getAcntRid());
            request.setAttribute("orgCode",orgCode);
            request.setAttribute("AOutWorker",outWorker);

        }
    }
}
