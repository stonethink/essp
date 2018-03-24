package server.essp.timesheet.report.utilizationRate.gather.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.framework.common.BusinessException;
import c2s.essp.timesheet.report.DtoUtilRateQuery;
import server.essp.timesheet.report.utilizationRate.detail.exporter.UtilRateForCycleExporter;
import com.wits.util.Parameter;
import c2s.essp.timesheet.report.DtoUtilRateKey;
import server.essp.timesheet.report.utilizationRate.gather.service.IUtilRateGatherService;
import server.essp.common.excelUtil.AbstractExcelAction;
import java.io.OutputStream;
import java.util.Date;
import com.wits.util.comDate;
import server.essp.timesheet.report.utilizationRate.gather.exporter.UtilRateGatherExporter;
import java.util.Map;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author tbh
 * @version 1.0
 */
public class AcUtilRateGatherExporter extends AbstractExcelAction {
    public void execute(HttpServletRequest request,
                        HttpServletResponse response,
                        OutputStream os, Parameter param) throws Exception {
        DtoUtilRateQuery dtoQuery = new DtoUtilRateQuery();
        String acntId = (String) param.get(DtoUtilRateKey.DTO_ACCOUNT_ID);
        String loginId = (String) param.get(DtoUtilRateKey.DTO_LOGINID);
        String bDate = (String) param.get(DtoUtilRateKey.DTO_BEGIN_DATE);
        String eDate = (String) param.get(DtoUtilRateKey.DTO_END_DATE);
        Date beginDate = comDate.toDate(bDate, "yyyy-MM-dd");
        Date endDate = comDate.toDate(eDate, "yyyy-MM-dd");
        dtoQuery.setLoginId(loginId);
        dtoQuery.setBegin(beginDate);
        dtoQuery.setEnd(endDate);
        dtoQuery.setAcntId(acntId);
        IUtilRateGatherService lg = (IUtilRateGatherService)this.getBean(
                "iUtilRateGatherService");
        lg.setExcelDto(true);
        Map dataList = lg.getDataTreeByYear(dtoQuery);
        Parameter inputData = new Parameter();
        inputData.putAll(param);
        inputData.put(DtoUtilRateKey.DTO_DEPT_LIST, dataList);
        try {
            
            UtilRateGatherExporter utilExporter = new UtilRateGatherExporter();
            utilExporter.webExport(response, os, inputData);
        } catch (Exception ex) {
            throw new BusinessException("Error",
                    "Error when Call export() 0f SampleExcelExporter ", ex);
        }
    }
}


