package server.essp.pms.account.baseline.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.framework.action.AbstractBusinessAction;
import server.framework.common.BusinessException;
import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import server.essp.pms.account.baseline.logic.LgAccountBLTiming;
import java.util.List;
import c2s.essp.pms.account.DtoAcntTiming;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class AcAcntTiming extends AbstractBusinessAction {
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
        ReturnInfo returnInfo=data.getReturnInfo();
        InputInfo inputInfo=data.getInputInfo();

        Long acntRid=(Long)inputInfo.getInputObj("acntRid");

        LgAccountBLTiming lgTiming=new LgAccountBLTiming(acntRid);
        List TimingList=lgTiming.listBaseLineTiming();
        List lastBaseLine=(List)TimingList.get(0);
        List currentMilestone=(List)TimingList.get(1);
//        if(lastBaseLine.size()>0){
//            milestoneHistory = (DtoAcntTiming) lastBaseLine.get(0);
//            returnInfo.setReturnObj("baseLineId",
//                                    milestoneHistory.getBaselineId());
//        }
//        else{
//            returnInfo.setReturnObj("baseLineId","");
//        }
        returnInfo.setReturnObj("baseLineId",lgTiming.getLastBaseLineId());
        returnInfo.setReturnObj("lastBaseLine",lastBaseLine);
        returnInfo.setReturnObj("currentMilestone",currentMilestone);

    }
}
