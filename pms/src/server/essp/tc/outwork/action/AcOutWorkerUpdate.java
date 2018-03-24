package server.essp.tc.outwork.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import db.essp.tc.TcOutWorker;
import server.essp.tc.outwork.logic.LgOutWork;
import server.essp.tc.outwork.form.AfOutWorkerForm;
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
        TcOutWorker tcOutWorker=new TcOutWorker();
        tcOutWorker.setAcntRid(new Long(afwf.getAcntRid()));
        tcOutWorker.setBeginDate(comDate.toDate(afwf.getBeginDate()));
        tcOutWorker.setEndDate(comDate.toDate(afwf.getEndDate()));
        tcOutWorker.setDays(new Long(afwf.getDays()));
        if(afwf.getActivityRid()!=null&&!afwf.getActivityRid().equals("")){
            tcOutWorker.setActivityRid(new Long(afwf.getActivityRid()));
        }
        tcOutWorker.setLoginId(afwf.getLoginId());
        tcOutWorker.setIsAutoWeeklyReport(new Boolean(afwf.getIsAutoFillWR()));
        tcOutWorker.setDestAddress(afwf.getDestAddress());
        if(afwf.getIsTravellingAllowance()!=null&&!afwf.getIsTravellingAllowance().equals("")){
            tcOutWorker.setIsTravellingAllowance(afwf.getIsTravellingAllowance());
        }
        tcOutWorker.setEvectionType(afwf.getEvectionType());
        tcOutWorker.setRid(new Long(rid));
        LgOutWork logic=new LgOutWork();
        try {
            logic.update(tcOutWorker);
        } catch (Exception ex) {
            ex.printStackTrace();
            new BusinessException("Error", "error at update outWorker!");
        }

    }
}
