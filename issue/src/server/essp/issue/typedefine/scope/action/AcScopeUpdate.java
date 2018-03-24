package server.essp.issue.typedefine.scope.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import server.essp.issue.typedefine.scope.form.AfScope;
import server.essp.issue.typedefine.scope.logic.LgScope;
import server.framework.action.AbstractBusinessAction;
import server.framework.common.BusinessException;

public class AcScopeUpdate extends AbstractBusinessAction {
  /**
   * 依据传入的AfScope修改IssueScope对象
   * Call: LgScope.update(AfScope)
   * ForwardID: success
   * @param request HttpServletRequest
   * @param response HttpServletResponse
   * @param data TransactionData
   * @throws BusinessException
   */
  public void executeAct(HttpServletRequest request,
                         HttpServletResponse response, TransactionData data) throws
      BusinessException {
    AfScope form = (AfScope)this.getForm();
    log.info("update issue type,typename:" + form.getTypeName() );

    LgScope logic = new LgScope();
    logic.update(form);

    request.setAttribute("refresh", "opener.refreshSelf()");
  }
  /** @link dependency */
  /*# LgScope lnkLgScope; */
}
