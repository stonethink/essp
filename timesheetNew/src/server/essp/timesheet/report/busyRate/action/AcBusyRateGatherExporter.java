/*
 * Created on 2008-6-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.report.busyRate.action;

import java.io.OutputStream;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.common.excelUtil.AbstractExcelAction;
import server.essp.timesheet.report.busyRate.exporter.BusyRateGatherExporter;
import server.essp.timesheet.report.busyRate.service.IBusyRateGatherService;
import server.framework.common.BusinessException;
import c2s.essp.timesheet.report.DtoBusyRateQuery;
import com.wits.util.Parameter;
import com.wits.util.comDate;

public class AcBusyRateGatherExporter extends AbstractExcelAction {
        public void execute(HttpServletRequest request,
                HttpServletResponse response,
                OutputStream os, Parameter param) throws Exception {
            DtoBusyRateQuery dtoQuery = new DtoBusyRateQuery();
            String acntId = (String) param.get(DtoBusyRateQuery.DTO_ACCOUNT_ID);
            String loginId = (String) param.get(DtoBusyRateQuery.DTO_LOGINID);
            String bDate = (String) param.get(DtoBusyRateQuery.DTO_BEGIN_DATE);
            String eDate = (String) param.get(DtoBusyRateQuery.DTO_END_DATE);
            Date beginDate = comDate.toDate(bDate, "yyyy-MM-dd");
            Date endDate = comDate.toDate(eDate, "yyyy-MM-dd");
            dtoQuery.setLoginId(loginId);
            dtoQuery.setBegin(beginDate);
            dtoQuery.setEnd(endDate);
            dtoQuery.setAcntId(acntId);
            IBusyRateGatherService lg = (IBusyRateGatherService)this.getBean(
                "busyRateGatherService");
            lg.setExcelDto(true);
            Map dataList = lg.getBusyRateInfoByYear(dtoQuery);
            Parameter inputData = new Parameter();
            inputData.putAll(param);
            inputData.put(DtoBusyRateQuery.DTO_DEPT_LIST, dataList);
            try {
              BusyRateGatherExporter byExporter = new BusyRateGatherExporter();
              byExporter.webExport(response, os, inputData);
            } catch (Exception ex) {
              throw new BusinessException("Error",
                    "Error when Call export() 0f SampleExcelExporter ", ex);
            }
            }
}