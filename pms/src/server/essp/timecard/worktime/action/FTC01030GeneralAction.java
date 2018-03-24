package server.essp.timecard.worktime.action;

import c2s.dto.*;

import server.essp.timecard.worktime.logic.*;
import server.framework.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.framework.common.BusinessException;
import com.wits.util.Parameter;

/**
 *
 * <p>Title: 维护工作时间</p>
 * <p>Description:依据操作选择不同处理 </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:  Wistron ITS Wuhan SDC</p>
 * @author BoXiao
 * @version 1.0
 */
public class FTC01030GeneralAction extends AbstractBusinessAction {
    public FTC01030GeneralAction() {
    }

  /**
   * execute
   *
   * @param request HttpServletRequest
   * @param response HttpServletResponse
   * @param data VDView_Data
   * @throws BusinessException
   */
  public void executeAct(HttpServletRequest request, HttpServletResponse response, TransactionData data) throws BusinessException {
    Parameter param = new Parameter();
    param.put("data", data);
    FTC01030GeneralLogic ftc = new FTC01030GeneralLogic();
  //ftc.setDbAccessor(this.getDbAccessor());
    executeLogic(ftc, param);

  }
}
