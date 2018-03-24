package server.essp.pms.psr.action;

import server.essp.common.excelUtil.AbstractExcelAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import com.wits.util.Parameter;
import server.essp.pms.psr.logic.PSRExporter;
import server.framework.common.BusinessException;
import com.wits.util.StringUtil;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class AcExportPSR extends AbstractExcelAction {
    public AcExportPSR() {
    }

    public void execute(HttpServletRequest request,
                        HttpServletResponse httpServletResponse,
                        OutputStream outputStream, Parameter parameter) throws
        Exception {
        String acntRidStr = (String) parameter.get("acntRid");
        Long acntRid = null;
        if("".equals(StringUtil.nvl(acntRidStr)) == false) {
            acntRid = new Long(acntRidStr);
        }
        parameter.put("acntRid", acntRid);
        PSRExporter e = new PSRExporter();
        try {
            e.webExport(httpServletResponse, outputStream, parameter);
        } catch (Exception ex) {
            throw new BusinessException("", "error exporting data!", ex);
        }
    }
}
