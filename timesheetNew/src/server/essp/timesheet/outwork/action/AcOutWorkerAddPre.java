package server.essp.timesheet.outwork.action;

import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import javax.servlet.http.HttpServletResponse;
import server.essp.timesheet.outwork.form.AfOutWorkerForm;
import java.util.List;
import java.util.ArrayList;
import server.essp.common.syscode.LgSysParameter;
import server.essp.timesheet.outwork.viewbean.vbOutWorker;

public class AcOutWorkerAddPre extends AbstractESSPAction {
    public AcOutWorkerAddPre() {
    }

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData transData) throws BusinessException {
        //根据ActionForm添加一条记录
//        AfOutWorkerForm afwf=(AfOutWorkerForm)this.getForm();

        List evectionTypeList = new ArrayList();
        LgSysParameter lg = new LgSysParameter();
        evectionTypeList = lg.listOptionSysParas("EVECTION_TYPE");
        vbOutWorker vb = new vbOutWorker();
        vb.setEvectionTypeList(evectionTypeList);
        vb.setEvectionType("");
        this.getRequest().setAttribute("webVo", vb);
    }

}
