package server.essp.issue.typedefine.category.action;

import javax.servlet.http.*;
import c2s.dto.*;
import server.essp.issue.typedefine.category.form.*;
import server.framework.action.*;
import server.framework.common.*;
import server.essp.issue.typedefine.category.logic.LgCategory;

public class AcCategoryUpdate extends AbstractBusinessAction {
  /**
   * 依据传入的AfCategory修改IssueCategoryType
   * Call: LgCategory.update(AfCategory)
   * ForwardID: success
   * @param request HttpServletRequest
   * @param response HttpServletResponse
   * @param data TransactionData
   * @throws BusinessException
   * @todo Implement this server.framework.action.AbstractBusinessAction method
   */
  public void executeAct(HttpServletRequest request, HttpServletResponse response, TransactionData data) throws BusinessException {
    AfCategory form = (AfCategory) this.getForm();
    log.info("update issue category,typename:" + form.getTypeName() +
            "\nCategoryName:" + form.getCategoryName());

    LgCategory logic = new LgCategory();
    logic.update(form);

    request.setAttribute("refresh", "opener.refreshSelf()");
  }

  /** @link dependency */
  /*# server.essp.issue.typedefine.category.logic.LgCategory lnkLgCategory; */
}
