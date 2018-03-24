package server.essp.timecard.timecard.action;

import c2s.dto.*;

import server.essp.timecard.timecard.logic.*;
import server.framework.action.AbstractBusinessAction;

import javax.servlet.http.*;
import com.wits.util.Parameter;
import server.framework.common.*;

public class FTC01020GeneralAction extends AbstractBusinessAction {
  public FTC01020GeneralAction() {
  }

  /**
   * execute
   *
   * @param request HttpServletRequest
   * @param response HttpServletResponse
   * @param data VDView_Data
   */
  public void executeAct(HttpServletRequest request,
                         HttpServletResponse response,
                         TransactionData data) throws BusinessException {

    Parameter param = new Parameter();
    param.put("data", data);
    FTC01020GeneralLogic ftc = new FTC01020GeneralLogic();
    //ftc.setDbAccessor(this.getDbAccessor());
    executeLogic(ftc, param);

  }

}
