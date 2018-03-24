package server.essp.pms.quality.activity.action;

import server.essp.framework.action.AbstractESSPAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import server.essp.pms.quality.activity.logic.LgTestStat;
import java.util.List;
import db.essp.pms.ActivityTestStat;
import c2s.essp.pms.quality.activity.DtoTestStat;
import java.util.ArrayList;

public class AcListTestStat extends AbstractESSPAction {
    public AcListTestStat() {
    }

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
        BusinessException {
        InputInfo inputInfo = data.getInputInfo();
        ReturnInfo returnInfo = data.getReturnInfo();
        Long testRid = (Long) inputInfo.getInputObj("testRid");
        LgTestStat tr = new LgTestStat();
        List list= tr.listTestStat(testRid);
        List testStatList = new ArrayList();
        int defectNum = 0;
        for(int i=0; i<list.size(); i++){
        ActivityTestStat testStat = (ActivityTestStat) list.get(i);
        DtoTestStat dtoTestStat = new DtoTestStat();
        dtoTestStat.setInjectedPhase(testStat.getInjectedPhase());
        dtoTestStat.setNumber(testStat.getDefectNum());
        dtoTestStat.setRid(testStat.getRid());
        dtoTestStat.setAcntRid(testStat.getAcntRid());
        dtoTestStat.setTestRid(testStat.getTestRid());
        defectNum += testStat.getDefectNum().intValue();
        testStatList.add(dtoTestStat);
        }
        returnInfo.setReturnObj("testStatList",testStatList);
        returnInfo.setReturnObj("defectNum",new Long(defectNum));
    }
}
