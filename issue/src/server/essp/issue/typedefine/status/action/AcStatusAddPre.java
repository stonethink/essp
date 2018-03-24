package server.essp.issue.typedefine.status.action;

import server.framework.action.AbstractBusinessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import c2s.dto.ReturnInfo;
import server.essp.issue.typedefine.status.viewbean.VbStatus;
import server.framework.common.Constant;
import server.essp.issue.typedefine.status.logic.LgStatus;
import server.essp.issue.typedefine.status.form.AfStatus;

public class AcStatusAddPre extends AbstractBusinessAction {
    public AcStatusAddPre() {
    }
    /**
     * 依据传入的typeName转向（必要时可以取dropdown list）
     * Call:
     * ForwardID: to_add
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) {
        AfStatus form=(AfStatus)this.getForm();
        String typeName = form.getTypeName();

        VbStatus vBean = new VbStatus();
        vBean.setTypeName(typeName);

        LgStatus logic=new LgStatus();
        long sequence=logic.getMaxSequence(typeName);
        vBean.setStatusSequence(sequence+"");
        request.setAttribute(Constant.VIEW_BEAN_KEY, vBean);
    }
}
