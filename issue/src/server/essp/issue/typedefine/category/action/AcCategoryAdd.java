package server.essp.issue.typedefine.category.action;

import javax.servlet.http.*;
import c2s.dto.*;
import server.essp.issue.typedefine.category.form.*;
import server.framework.action.*;
import server.framework.common.*;
import server.essp.issue.typedefine.category.logic.LgCategory;

public class AcCategoryAdd extends AbstractBusinessAction{
  /**
   * 依据传入的AfCategory新增IssueCategoryType对象
   * Call: LgCategory.add(AfCategory)
   * ForwardID: success
   * @param request HttpServletRequest
   * @param response HttpServletResponse
   * @param data TransactionData
   * @throws BusinessException
   */
  public void executeAct(HttpServletRequest request, HttpServletResponse response, TransactionData data) throws BusinessException {
    AfCategory form = (AfCategory)this.getForm();
    log.info("add issue category:" + form.getCategoryName() +
             "\nsequence:" + form.getSequence() +
             "\ndescription:" +form.getDescription());

    LgCategory logic = new LgCategory();
    logic.add(form);

    request.setAttribute("refresh", "opener.refreshSelf()");
  }

  /** @link dependency */
  /*# server.essp.issue.typedefine.category.logic.LgCategory lnkLgCategory; */
}
