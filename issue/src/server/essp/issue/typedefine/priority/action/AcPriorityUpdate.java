package server.essp.issue.typedefine.priority.action;

import server.framework.action.AbstractBusinessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import server.essp.issue.typedefine.priority.logic.LgPriority;
import server.essp.issue.typedefine.priority.form.AfPriority;
public class AcPriorityUpdate extends AbstractBusinessAction {
    public AcPriorityUpdate() {
    }
    /**
   * 依据传入的AfPriority修改IssuePriority对象
   * Call: LgPriority.update(AfPriority)
   * ForwardID: success
   * @param request HttpServletRequest
   * @param response HttpServletResponse
   * @param data TransactionData
   * @throws BusinessException
   */
  public void executeAct(HttpServletRequest request,
                         HttpServletResponse response, TransactionData data) throws
      BusinessException {
    AfPriority form = (AfPriority)this.getForm();
    //log.info("update issue type,typename:" + form.getTypeName() );

    LgPriority logic = new LgPriority();
    logic.setDbAccessor(this.getDbAccessor());
    logic.update(form);

    request.setAttribute("refresh", "opener.refreshSelf()");
    data.getReturnInfo().setForwardID("success");
  }

}
