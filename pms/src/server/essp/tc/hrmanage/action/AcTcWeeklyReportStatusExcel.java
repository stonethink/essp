package server.essp.tc.hrmanage.action;

import server.essp.common.excelUtil.AbstractExcelAction;
import javax.servlet.http.HttpServletRequest;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import java.io.OutputStream;
import com.wits.util.Parameter;
import java.util.Date;
import server.framework.common.BusinessException;
import javax.servlet.http.HttpServletResponse;
import com.wits.util.comDate;
import com.wits.excel.ExcelExporter;
import server.essp.tc.weeklyreport.logic.WeeklyReportStatusExporter;

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
public class AcTcWeeklyReportStatusExcel  extends AbstractExcelAction {
    public void execute(HttpServletRequest request,
                        HttpServletResponse response, OutputStream os, Parameter param) throws Exception {
        String acntRidStr = (String) param.get(DtoTcKey.ACNT_RID);
        String beginPeriodStr = (String) param.get(DtoTcKey.BEGIN_PERIOD);
        String endPeriodStr = (String) param.get(DtoTcKey.END_PERIOD);

        Long acntRid = new Long(acntRidStr);
        Date beginPeriod = comDate.toDate(beginPeriodStr, comDate.pattenDate);
        Date endPeriod = comDate.toDate(endPeriodStr, comDate.pattenDate);

        if (acntRid == null) {
            throw new BusinessException("Please select an account first.");
        } else if (beginPeriod == null || endPeriod == null) {
            throw new BusinessException("Please select from-to time.");
        }

        WeeklyReportStatusExporter see = new WeeklyReportStatusExporter();

        Parameter inputData = new Parameter();
        inputData.put(DtoTcKey.ACNT_RID, acntRid);
        inputData.put(DtoTcKey.BEGIN_PERIOD, beginPeriod);
        inputData.put(DtoTcKey.END_PERIOD, endPeriod);

        try {
            see.webExport(response, os, inputData);
        } catch (Exception ex) {
            throw new BusinessException("Error", "Error when Call export() 0f SampleExcelExporter ", ex);
        }
    }

}
