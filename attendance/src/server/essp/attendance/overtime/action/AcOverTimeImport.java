package server.essp.attendance.overtime.action;

import java.io.*;

import javax.servlet.http.*;

import c2s.dto.*;
import org.apache.poi.hssf.usermodel.*;
import server.essp.attendance.overtime.form.*;
import server.essp.attendance.overtime.logic.*;
import server.essp.attendance.overtime.viewbean.*;
import server.essp.framework.action.*;
import server.framework.common.*;

public class AcOverTimeImport extends AbstractESSPAction {
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData data) throws BusinessException {
        HSSFWorkbook wb = null;
        try {
            AfOverTimeImport af = (AfOverTimeImport) getForm();
            InputStream is = af.getLocalFile().getInputStream();
            wb = new HSSFWorkbook(is);
        } catch (Exception ex) {
            throw new BusinessException("get file error.",ex);
        }
        LgOverTimeImport lg = new LgOverTimeImport();
        VbOverTimeImportInfo webVo = lg.importExcel(wb);
        request.setAttribute("webVo", webVo);
    }
}
