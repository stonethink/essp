package server.essp.pms.quality.activity.action;

import server.essp.framework.action.AbstractESSPAction;
import c2s.dto.ReturnInfo;
import c2s.essp.pms.quality.activity.DtoQualityActivity;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.InputInfo;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import java.util.ArrayList;
import java.util.List;
import server.framework.common.BusinessException;
import server.essp.pms.quality.activity.logic.LgTestRound;
import db.essp.pms.ActivityTest;
import c2s.essp.pms.quality.activity.DtoTestRound;

public class AcGeneralQualityActivity extends AbstractESSPAction {
    public AcGeneralQualityActivity() {
    }

    public void executeAct(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           TransactionData transactionData) throws
        BusinessException {

        InputInfo inputInfo = transactionData.getInputInfo();
        ReturnInfo returnInfo = transactionData.getReturnInfo();
        DtoQualityActivity general = (DtoQualityActivity) inputInfo.getInputObj(
            "qaGeneral");
        Long acntRid = general.getAcntRid();
        Long activityRid = general.getActivityRid();
        LgTestRound tr = new LgTestRound();
        List tList = tr.FindActivityTest(acntRid,activityRid);
        List testList=new ArrayList();
        //change been to dto;
        for(int i=0;i<tList.size();i++){
        ActivityTest qt = (ActivityTest) tList.get(i);
        DtoTestRound dtoTR = new DtoTestRound();
        dtoTR.setDefectNum(new Long(tr.FindTestRoundDefectNum(qt.getAcntRid(),qt.getRid())));
        dtoTR.setRid(qt.getRid());
        dtoTR.setAcntRid(acntRid);
        dtoTR.setQulityRid(activityRid);
        dtoTR.setActivityName(general.getName());
        dtoTR.setTestRound(qt.getTestRound());
        dtoTR.setStart(qt.getStart());
        dtoTR.setFinish(qt.getFinish());
        dtoTR.setTotalTestCase(qt.getTotalTestCase());
        dtoTR.setUsingTestCase(qt.getUsingTestCase());
        dtoTR.setRemovedDefectNum(qt.getRemovedDefectNum());
        dtoTR.setComment(qt.getComment());
        dtoTR.setSeq(qt.getSeq());
        testList.add(dtoTR);
        }
        returnInfo.setReturnObj("testList", testList);
    }
}
