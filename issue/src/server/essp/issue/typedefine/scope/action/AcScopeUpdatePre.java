package server.essp.issue.typedefine.scope.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import db.essp.issue.IssueScope;
import server.essp.issue.typedefine.scope.form.AfScope;
import server.essp.issue.typedefine.scope.logic.LgScope;
import server.framework.action.AbstractBusinessAction;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import server.essp.issue.typedefine.scope.viewbean.VbScope;

public class AcScopeUpdatePre extends AbstractBusinessAction {
  /**
   * 依据传入的typeName和ScopeName获取该Scope对象
   * Call: LgScope.load(AfScope)
   * ForwardID: success
   * @param request HttpServletRequest
   * @param response HttpServletResponse
   * @param data TransactionData
   * @throws BusinessException
   */
  public void executeAct(HttpServletRequest request,
                         HttpServletResponse response, TransactionData data) throws
      BusinessException {
    AfScope form = (AfScope) this.getForm();
    String typeName = form.getTypeName();
    String scope = form.getScope();
    log.info("update issue scope,typename:" + typeName +
             "\nscope:" + scope);

    LgScope logic = new LgScope();

    IssueScope issueScope = logic.load(typeName,scope);
    VbScope vBean = new VbScope();
    vBean.setTypeName(issueScope.getComp_id().getTypeName());
    vBean.setScope(issueScope.getComp_id().getScope());
    try {
        c2s.dto.DtoUtil.copyProperties(vBean, issueScope);
    } catch (Exception ex) {
        throw new BusinessException("issue.scope.updatepre.copy.exception","copy properties error!");
    }
    request.setAttribute(Constant.VIEW_BEAN_KEY, vBean);
  }
  /** @link dependency */
  /*# LgScope lnkLgScope; */
}
