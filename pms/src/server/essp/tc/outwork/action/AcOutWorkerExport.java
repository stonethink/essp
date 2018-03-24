package server.essp.tc.outwork.action;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wits.util.Parameter;
import server.essp.common.excelUtil.AbstractExcelAction;
import server.essp.tc.outwork.form.AfSearchForm;
import java.util.Date;
import com.wits.util.comDate;
import com.wits.excel.ExcelExporter;
import server.framework.common.BusinessException;

public class AcOutWorkerExport extends AbstractExcelAction {
    public AcOutWorkerExport() {
    }

    /**
     * 导出出差报表
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param os OutputStream
     * @param param Parameter
     * @throws Exception
     * @todo Implement this server.essp.common.excelUtil.AbstractExcelAction
     *   method
     */
    public void execute(HttpServletRequest request, HttpServletResponse response, OutputStream os, Parameter param) throws Exception {
        AfSearchForm searchCon = new AfSearchForm();

        if (request.getSession().getAttribute("searchCondition") != null) {
            searchCon = (AfSearchForm) request.getSession().getAttribute(
                    "searchCondition");
        } else {
            searchCon = new AfSearchForm();
        }

        Parameter inputParam = new Parameter();
        inputParam.put("searchConditionForm", searchCon);

        String currentDate = comDate.dateToString(new Date());
        String outputFile = "TravelListReport_" + currentDate + ".xls"; //导出文件名

        ExcelExporter export = new OutWorkerExporter(outputFile);
        try {
            export.webExport(response, os, inputParam);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BusinessException("", "error exporting travels data!", ex);
        }

    }
}
