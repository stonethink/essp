package server.essp.issue.common.action;

import server.essp.issue.common.logic.LgFilledBy;
import server.essp.issue.common.form.AfFilledByChanged;
import server.framework.action.AbstractBusinessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import server.framework.common.Constant;

public class AcFilledByChanged extends AbstractBusinessAction {
    public AcFilledByChanged() {
    }

    /**
     * executeAct
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     * @todo Implement this server.framework.action.AbstractBusinessAction
     *   method
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
        BusinessException {
        AfFilledByChanged form=(AfFilledByChanged)this.getForm();
        LgFilledBy logic=new LgFilledBy();
        Object webVo=logic.getFilledByInfo(form.getAccountRid(),form.getFilledBy());
        request.setAttribute(Constant.VIEW_BEAN_KEY,webVo);
    }
}
