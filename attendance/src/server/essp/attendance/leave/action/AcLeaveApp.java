package server.essp.attendance.leave.action;

import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import javax.servlet.http.HttpServletResponse;
import server.essp.attendance.leave.form.AfLeaveApp;
import server.essp.attendance.leave.logic.LgLeave;

public class AcLeaveApp extends AbstractESSPAction {
        /**
         * executeAct
         *
         * @param request HttpServletRequest
         * @param response HttpServletResponse
         * @param data TransactionData
         * @throws BusinessException
         * @todo Implement this server.framework.action.AbstractBusinessAction
         *   method
         */
        public void executeAct(HttpServletRequest request,
                               HttpServletResponse response, TransactionData data) throws
                BusinessException {
            AfLeaveApp form = (AfLeaveApp) this.getForm();
            LgLeave lg = new LgLeave();
            lg.addLeaveApp(form);
            data.getReturnInfo().setForwardID("success");
        }

}
