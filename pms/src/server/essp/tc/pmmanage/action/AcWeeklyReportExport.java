package server.essp.tc.pmmanage.action;

import server.essp.common.excelUtil.AbstractExcelAction;
import javax.servlet.http.HttpServletRequest;
import java.io.OutputStream;
import com.wits.util.Parameter;
import java.util.Date;
import server.framework.common.BusinessException;
import javax.servlet.http.HttpServletResponse;
import com.wits.util.comDate;
import com.wits.excel.ExcelExporter;
import server.essp.tc.pmmanage.logic.WeeklyReportExporter;
import server.essp.tc.pmmanage.logic.PeriodReportExporter;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class AcWeeklyReportExport extends AbstractExcelAction {
    public AcWeeklyReportExport() {
    }

    public void execute(HttpServletRequest request,
                        HttpServletResponse httpServletResponse,
                        OutputStream outputStream, Parameter parameter) throws
        Exception {
        String beginPeriod = parameter.get("beginPeriod").toString();
        String endPeriod = parameter.get("endPeriod").toString();
        String exportType = parameter.get("exportType").toString();
        ExcelExporter export;
        String outputFile;
        if(exportType.equals("week")) {
            outputFile = "TC_WeeklyReport_PM" + beginPeriod + " ~~ " + endPeriod + ".xls";
            export = new WeeklyReportExporter(outputFile);
        } else {
            outputFile = "TC_PeriodReport_PM" + beginPeriod + " ~~ " + endPeriod + ".xls";
            export = new PeriodReportExporter(outputFile);
        }
        try {
            export.webExport(httpServletResponse, outputStream, parameter);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BusinessException("", "error exporting data!", ex);
        }

    }

}
