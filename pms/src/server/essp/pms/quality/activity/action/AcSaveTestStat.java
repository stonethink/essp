package server.essp.pms.quality.activity.action;

import server.essp.framework.action.AbstractESSPAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import java.util.List;
import c2s.essp.pms.quality.activity.DtoTestStat;
import server.essp.pms.quality.activity.logic.LgTestStat;
import c2s.essp.pms.quality.activity.DtoTestRound;
import server.essp.pms.quality.activity.logic.LgTestRound;

public class AcSaveTestStat extends AbstractESSPAction {
    public AcSaveTestStat() {
    }

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
        BusinessException {
        InputInfo inputInfo = data.getInputInfo();
        ReturnInfo returnInfo = data.getReturnInfo();
        List list = (List) inputInfo.getInputObj("dtoTestStatList");
        DtoTestRound dtoTestRound = (DtoTestRound) inputInfo.getInputObj(
            "dtoTestRound");
        Long testRid;
        if (dtoTestRound != null) {
            LgTestRound lgTestRound = new LgTestRound();
            if (null != dtoTestRound.getRid()) {
                testRid=lgTestRound.updateTestRound(dtoTestRound);
            } else {
                testRid=lgTestRound.addTestRound(dtoTestRound);
            }

            LgTestStat lgTestStat = new LgTestStat();
            for (int i = 0; i < list.size(); i++) {
                DtoTestStat dtoTestStat = (DtoTestStat) list.get(i);
                dtoTestStat.setTestRid(testRid);
                if (null != dtoTestStat.getNumber() &&
                    null != dtoTestStat.getTestRid()) {
                    if (null != dtoTestStat.getRid()) {
                        if (dtoTestStat.isDelete()) {
                            lgTestStat.deleteTestStat(dtoTestStat);
                        } else if (dtoTestStat.isModify()) {
                            lgTestStat.updateTestStat(dtoTestStat);
                        }
                    }
                    if (dtoTestStat.isInsert()) {
                        lgTestStat.saveTestStat(dtoTestStat);
                    }

                }
            }

        }
    }
}
