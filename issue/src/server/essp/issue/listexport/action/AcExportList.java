package server.essp.issue.listexport.action;

import server.framework.common.BusinessException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.issue.issue.form.AfIssueSearch;
import server.essp.issue.listexport.logic.lgGetExportData;
import java.util.HashMap;
import com.wits.util.comDate;
import java.util.Date;
import com.wits.excel.ExcelExporter;
import java.io.OutputStream;
import com.wits.util.Parameter;
import server.essp.common.excelUtil.AbstractExcelAction;

public class AcExportList extends AbstractExcelAction {
    public AcExportList() {
    }

    public void execute(HttpServletRequest request,
                        HttpServletResponse httpServletResponse,
                        OutputStream outputStream, Parameter parameter) throws
        Exception {

        String server_root="";
        server_root="http://"+request.getServerName();
        if(request.getServerPort()!=80){
        server_root=server_root+":"+request.getServerPort();
        }
//        System.out.println(server_root);
//        request.getSession().getServletContext().getRealPath();
//System.out.println(        request.getServerName());

        String type="";
        if(parameter.get("ExportType")!=null){
            type=(String)parameter.get("ExportType");
        }

        AfIssueSearch searchform = new AfIssueSearch();
        if (request.getSession().getAttribute("RoprtConditionForm") != null) {
            searchform = (AfIssueSearch) request.getSession().getAttribute(
                "RoprtConditionForm");
        } else {
            searchform = new AfIssueSearch();
        }

        Parameter inputParam = new Parameter();
        inputParam.put("searchConditionForm",searchform);
        inputParam.put("serverRoot",server_root);
        inputParam.put("ExportType",type);
//        lgGetExportData ged = new lgGetExportData();
//        HashMap hm = ged.getListExportDate(searchform);
//        System.out.println("*****************************" + hm.size());

        String currentDate = comDate.dateToString(new Date());
        String outputFile = "IssueListReport_" + currentDate + ".xls"; //导出文件名
        ExcelExporter export = new IssueListExporter(outputFile);
        try {
            export.webExport(httpServletResponse, outputStream, inputParam);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BusinessException("", "error exporting data!", ex);
        }


    }

}
