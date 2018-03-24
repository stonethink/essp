package server.essp.issue.typedefine.priority.action;

import server.framework.action.AbstractBusinessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import server.essp.issue.typedefine.priority.logic.LgPriority;
import com.wits.util.StringUtil;
import server.essp.issue.typedefine.priority.form.AfPriority;
public class AcPriorityDelete extends AbstractBusinessAction {
    public AcPriorityDelete() {
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
      AfPriority form=(AfPriority)this.getForm();
      String typeName=form.getTypeName();
      String priority=form.getPriority();
      if (StringUtil.nvl(typeName).equals("")==true){
                typeName="";
      }
      if (StringUtil.nvl(priority).equals("")==true){
               priority="";
      }

      LgPriority logic = new LgPriority();
      logic.setDbAccessor(this.getDbAccessor());
      logic.delete(typeName,priority);

      request.setAttribute("typeName",typeName.trim());
  }

}
