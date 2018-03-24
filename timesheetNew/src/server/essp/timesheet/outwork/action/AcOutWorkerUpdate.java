package server.essp.timesheet.outwork.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import db.essp.timesheet.TsOutWorker;
import server.essp.timesheet.outwork.logic.LgOutWork;
import server.essp.timesheet.outwork.form.AfOutWorkerForm;
import com.wits.util.comDate;

public class AcOutWorkerUpdate extends AbstractESSPAction {
    public AcOutWorkerUpdate() {
    }

    /**
     * ¸üÐÂ
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     * @todo Implement this server.framework.action.AbstractBusinessAction
     *   method
     */
    public void executeAct(HttpServletRequest request, HttpServletResponse response, TransactionData data)
            throws BusinessException {
        String rid="";
        if(request.getParameter("rid")!=null){
            rid=request.getParameter("rid");
        }else{
            throw new BusinessException("Error", "Miss rid at update!");
        }
        AfOutWorkerForm afwf=(AfOutWorkerForm)this.getForm();
        TsOutWorker tcOutWorker=new TsOutWorker();
        tcOutWorker.setAcntRid(new Long(afwf.getAcntRid()));
        tcOutWorker.setBeginDate(comDate.toDate(afwf.getBeginDate()));
        tcOutWorker.setEndDate(comDate.toDate(afwf.getEndDate()));
        tcOutWorker.setDays(new Long(afwf.getDays()));
        tcOutWorker.setCodeRid(new Long(afwf.getCodeRid()));
        if(afwf.getActivityRid()!=null&&!afwf.getActivityRid().equals("")){
            tcOutWorker.setActivityRid(new Long(afwf.getActivityRid()));
        }
        tcOutWorker.setLoginId(afwf.getLoginId());
        tcOutWorker.setIsAutoWeeklyReport(new Boolean(afwf.getIsFillTimesheet()));
        tcOutWorker.setDestAddress(afwf.getDestAddress());
        if(afwf.getIsTravellingAllowance()!=null&&!afwf.getIsTravellingAllowance().equals("")){
            tcOutWorker.setIsTravellingAllowance(afwf.getIsTravellingAllowance());
        }
        tcOutWorker.setEvectionType(afwf.getEvectionType());
        tcOutWorker.setRid(new Long(rid));
        LgOutWork logic=new LgOutWork();
        logic.update(tcOutWorker);
    }
}
