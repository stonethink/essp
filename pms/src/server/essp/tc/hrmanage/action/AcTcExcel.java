package server.essp.tc.hrmanage.action;

import java.io.OutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.essp.tc.weeklyreport.DtoTcKey;
import com.wits.excel.ExcelExporter;
import com.wits.util.Parameter;
import com.wits.util.comDate;
import server.essp.common.excelUtil.AbstractExcelAction;
import server.essp.tc.hrmanage.logic.TimeCardExporter;
import server.framework.common.BusinessException;


public class AcTcExcel extends AbstractExcelAction {
    public void execute(HttpServletRequest request,
                        HttpServletResponse response, OutputStream os, Parameter param) throws Exception {
        String acntRidStr = (String) param.get("acntRid");
        String beginPeriodStr = (String) param.get("beginPeriod");
        String endPeriodStr = (String) param.get("endPeriod");
        String periodType = (String) param.get("periodType");

        Long acntRid = new Long(acntRidStr);
        Date beginPeriod = comDate.toDate(beginPeriodStr, "yyyyMMdd");
        Date endPeriod = comDate.toDate(endPeriodStr, "yyyyMMdd");

        if (acntRid == null) {
            throw new BusinessException("Please select an account first.");
        } else if (beginPeriod == null || endPeriod == null) {
            throw new BusinessException("Please select from-to time.");
        }

        ExcelExporter see = new TimeCardExporter();

        Parameter inputData = new Parameter();
        inputData.put(DtoTcKey.ACNT_RID, acntRid);
        inputData.put(DtoTcKey.BEGIN_PERIOD, beginPeriod);
        inputData.put(DtoTcKey.END_PERIOD, endPeriod);
        inputData.put(DtoTcKey.PERIOD_TYPE, periodType);

        try {
            see.webExport(response, os, inputData);
        } catch (Exception ex) {
            throw new BusinessException("Error", "Error when Call export() 0f SampleExcelExporter ", ex);
        }
    }

}
