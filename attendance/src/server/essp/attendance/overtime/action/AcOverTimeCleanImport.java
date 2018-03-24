package server.essp.attendance.overtime.action;

import server.framework.common.BusinessException;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.attendance.overtime.form.AfOverTimeImport;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.io.InputStream;
import server.essp.attendance.overtime.logic.LgOverTimeClean;
import java.util.List;

public class AcOverTimeCleanImport extends AbstractESSPAction {
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData data) throws BusinessException {
        HSSFWorkbook wb = null;
        try {
            AfOverTimeImport af = (AfOverTimeImport) getForm();
            InputStream is = af.getLocalFile().getInputStream();
            wb = new HSSFWorkbook(is);
        } catch (Exception ex) {
            throw new BusinessException("","get file error.", ex);
        }
        LgOverTimeClean lg = new LgOverTimeClean();
        List list = lg.importCleanExcelConfirm(wb);
        request.getSession().setAttribute("overTimeCleanList", list);
    }
}
