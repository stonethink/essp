package server.essp.pms.modifyplan.action;

import server.framework.common.BusinessException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import com.wits.util.comDate;
import java.util.Date;
import com.wits.excel.ExcelExporter;
import java.io.OutputStream;
import com.wits.util.Parameter;
import server.essp.common.excelUtil.AbstractExcelAction;
import server.essp.pms.modifyplan.logic.PlanExporter;
import javax.servlet.http.HttpSession;

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
public class AcExportPlan extends AbstractExcelAction {
    public AcExportPlan() {
    }

    public void execute(HttpServletRequest request,
                        HttpServletResponse httpServletResponse,
                        OutputStream outputStream, Parameter parameter) throws
        Exception {
        HttpSession session = request.getSession();
        parameter.put("rootNode", session.getAttribute("rootNode"));
        session.removeAttribute("rootNode");
        parameter.put("acntRid", session.getAttribute("acntRid"));
        session.removeAttribute("acntRid");
        parameter.put("imageHeight", session.getAttribute("imageHeight"));
        session.removeAttribute("imageHeight");
        parameter.put("imageWidth", session.getAttribute("imageWidth"));
        session.removeAttribute("imageWidth");
        parameter.put("filter", session.getAttribute("filter"));
        session.removeAttribute("filter");
        parameter.put("image", session.getAttribute("image"));
        session.removeAttribute("image");
        String currentDate = comDate.dateToString(new Date());
        String outputFile = "Project Plan" + currentDate + ".xls"; //导出文件名
        ExcelExporter export = new PlanExporter(outputFile);
        try {
            export.webExport(httpServletResponse, outputStream, parameter);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BusinessException("", "error exporting data!", ex);
        }

    }

}
