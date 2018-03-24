package server.essp.issue.typedefine.category.action;

import javax.servlet.http.*;

import c2s.dto.*;
import server.framework.action.*;
import server.framework.common.*;
import com.wits.util.Parameter;
import server.essp.issue.typedefine.category.form.AfCategoryValue;
import server.essp.issue.typedefine.category.logic.LgCategoryValue;

public class AcCategoryValueUpdate extends AbstractBusinessAction {
  /**
   * 依据传入的AfCategoryValue更新IssueCategoryValue对象
   * Call: LgCategoryValue.update(AfCategoryValue)
   * ForwardID: success
   * @param request HttpServletRequest
   * @param response HttpServletResponse
   * @param data TransactionData
   * @throws BusinessException
   * @todo Implement this server.framework.action.AbstractBusinessAction method
   */
  public void executeAct(HttpServletRequest request, HttpServletResponse response, TransactionData data) throws BusinessException {
    AfCategoryValue form = (AfCategoryValue) this.getForm();
    log.info("update issue type,typename:" + form.getTypeName() +
             "\nCategoryName:" + form.getCategoryName());

    LgCategoryValue logic = new LgCategoryValue();
    logic.update(form);

    request.setAttribute("refresh", "opener.refreshSelf()");
  }

  /** @link dependency */
  /*# LgCategoryValue lnkLgCategoryValue; */
}
