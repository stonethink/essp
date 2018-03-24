package server.essp.tc.hrmanage.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import java.util.Date;
import server.essp.tc.hrmanage.logic.LgNonattend;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import java.util.List;

public class AcNonattendList extends AbstractESSPAction {

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
    public void executeAct(HttpServletRequest request, HttpServletResponse response, TransactionData data) throws BusinessException {
          String userId = (String)data.getInputInfo().getInputObj(DtoTcKey.USER_ID);
          Date beginPeriod = (Date)data.getInputInfo().getInputObj(DtoTcKey.BEGIN_PERIOD);
          Date endPeriod = (Date)data.getInputInfo().getInputObj(DtoTcKey.END_PERIOD);
          LgNonattend lgNonattend=new LgNonattend();
          List nonattendList=lgNonattend.nonattendList(userId,beginPeriod,endPeriod);

          data.getReturnInfo().setReturnObj("nonattendList",nonattendList);

    }
}
