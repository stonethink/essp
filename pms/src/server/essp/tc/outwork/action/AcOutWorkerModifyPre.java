package server.essp.tc.outwork.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import server.essp.tc.outwork.logic.LgOutWork;
import db.essp.tc.TcOutWorker;
import server.essp.tc.outwork.form.AfOutWorkerForm;
import server.essp.common.syscode.LgSysParameter;
import java.util.List;
import java.util.ArrayList;
import server.essp.tc.outwork.viewbean.vbOutWorker;

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
            TcOutWorker outWorker=logic.loadByRid(new Long(rid));
            List evectionTypeList = new ArrayList();
            LgSysParameter lg = new LgSysParameter();
            evectionTypeList = lg.listOptionSysParas("EVECTION_TYPE");
            vbOutWorker vb = new vbOutWorker();
            vb.setEvectionTypeList(evectionTypeList);
            request.setAttribute("webVo",vb);
//            AfOutWorkerForm afOutWorker=new AfOutWorkerForm();
//            afOutWorker.setAcntRid(outWorker.getAcntRid().toString());
            request.setAttribute("AOutWorker",outWorker);

        }
    }
}
