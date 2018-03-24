package server.essp.tc.attimport;

import server.framework.action.AbstractBusinessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import server.essp.framework.action.AbstractESSPAction;

/**
 * 处理差勤数据导入的Action
 */
public class AcImport extends AbstractESSPAction {
    public void executeAct(
            HttpServletRequest request, HttpServletResponse response, TransactionData data) throws BusinessException {
        AfImport afImport = (AfImport)this.getForm();
        LgImport lgi = new LgImport();
        request.setAttribute("webVo", lgi.importAtt(afImport));
        data.getReturnInfo().setForwardID("import");

    }


}
