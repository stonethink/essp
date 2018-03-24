package server.essp.tc.attcompare;

import server.framework.action.AbstractBusinessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import server.essp.framework.action.AbstractESSPAction;

/**
 * 对请假信息和差勤信息进行比较的Action
 */
public class AcCompare extends AbstractESSPAction {
    public void executeAct(
            HttpServletRequest request, HttpServletResponse response, TransactionData data) throws BusinessException {
        AfCompare afc = (AfCompare)this.getForm();
        LgCompare lgc = new LgCompare();

        request.setAttribute("webVo", lgc.compareAtt(afc));
        data.getReturnInfo().setForwardID("compare");

    }

}
