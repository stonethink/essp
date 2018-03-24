package server.essp.issue.typedefine.risk.action;

import server.framework.action.AbstractBusinessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import server.essp.issue.typedefine.risk.logic.LgRiskList;
import java.util.List;
import server.essp.issue.typedefine.risk.viewbean.VbRisk;
import c2s.dto.ReturnInfo;
import server.framework.common.Constant;
import server.essp.issue.typedefine.risk.form.AfRisk;

public class AcRiskList extends AbstractBusinessAction {
    public AcRiskList() {
    }

    /**
     * 依据传入的typeName获取该Issue Type的Risk列表
     * Call: LgRiskList.list(typeName)
     * ForwardID: list
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
        BusinessException {
        AfRisk issueRiskForm = (AfRisk)this.getForm();
        String typeName = issueRiskForm.getTypeName();
        String selectedRow = "";
        selectedRow = issueRiskForm.getSelectedRow();
        if (typeName.equals("null")){
            typeName = " ";
        }
        log.info("list Risk of issue type:" + typeName);

        LgRiskList logic = new LgRiskList();

        List result = logic.list(typeName);
        VbRisk vBean = new VbRisk();
        vBean.setRiskList(result);
        vBean.setTypeName(typeName);
        if ( selectedRow == null  || selectedRow.equals("") || selectedRow.equalsIgnoreCase("null")){
            vBean.setSelectedRow("tr0");
        }else {
            if ((new Integer(selectedRow.substring(2))).intValue() >= result.size()){
                vBean.setSelectedRow("tr0");
            }else
            vBean.setSelectedRow(selectedRow);
        }

        request.setAttribute(Constant.VIEW_BEAN_KEY, vBean);

    }


}
