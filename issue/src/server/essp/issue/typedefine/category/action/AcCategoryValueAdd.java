package server.essp.issue.typedefine.category.action;

import javax.servlet.http.*;

import c2s.dto.*;
import server.essp.issue.typedefine.category.form.*;
import server.essp.issue.typedefine.category.logic.*;
import server.framework.action.*;
import server.framework.common.*;

public class AcCategoryValueAdd extends AbstractBusinessAction {
  public AcCategoryValueAdd() {
    super();
  }

  /**
   * 依据传入的AfCategoryValue新增IssueCategoryValue对象
   * Call: LgCategoryValue.add(AfCategoryValue)
   * ForwardID: success
   * @param request HttpServletRequest
   * @param response HttpServletResponse
   * @param data TransactionData
   * @throws BusinessException
   * @todo Implement this server.framework.action.AbstractBusinessAction method
   */
  public void executeAct(HttpServletRequest request, HttpServletResponse response, TransactionData data) throws BusinessException {
    AfCategoryValue form = (AfCategoryValue)this.getForm();
    log.info("add issue category value,category:" + form.getCategoryName() +
             "\nvalue:" + form.getCategoryValue() +
             "\nsequence:" + form.getSequence() +
             "\ndescription:" + form.getDescription());

    LgCategoryValue logic = new LgCategoryValue();
    logic.add(form);

    request.setAttribute("refresh", "opener.refreshSelf()");
  }

  /** @link dependency */
  /*# server.essp.issue.typedefine.category.logic.LgCategoryValue lnkLgCategoryValue; */
}
