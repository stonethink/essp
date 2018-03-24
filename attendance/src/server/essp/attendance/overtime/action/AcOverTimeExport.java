package server.essp.attendance.overtime.action;

import server.essp.common.excelUtil.AbstractExcelAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import com.wits.util.Parameter;
import server.essp.attendance.overtime.logic.TcOverTimeExporter;
import server.framework.common.BusinessException;

public class AcOverTimeExport extends AbstractExcelAction{
    public AcOverTimeExport() {
    }

    public void execute(HttpServletRequest request,
                        HttpServletResponse response,
                        OutputStream os, Parameter parameter) throws
            Exception {
        String beginDateStr = (String) parameter.get("beginDate");
        String endDateStr = (String) parameter.get("endDate");
        String fileName = "TC_Overtime " +  beginDateStr.replaceAll("/","-") +
                          " ~ " + endDateStr.replace("/","-") + ".xls";
        TcOverTimeExporter exporter = new TcOverTimeExporter(fileName);
        try {
            exporter.webExport(response, os, parameter);
        } catch (Exception ex) {
            throw new BusinessException("Error",
                    "Error when Call export() 0f SampleExcelExporter ", ex);
        }

    }

}
