package server.essp.timesheet.report.utilizationRate.detail.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.framework.common.BusinessException;
import com.wits.util.Parameter;
import server.essp.timesheet.report.utilizationRate.detail.exporter.UtilRateExporter;
import c2s.essp.timesheet.report.DtoUtilRateKey;
import java.util.List;
import server.essp.timesheet.report.utilizationRate.detail.service.IUtilRateService;
import java.util.ArrayList;
import server.essp.timesheet.report.utilizationRate.detail.exporter.UtilRateForCycleExporter;
import java.util.Vector;
import server.essp.common.excelUtil.AbstractExcelAction;
import java.io.OutputStream;
import c2s.essp.timesheet.report.DtoUtilRateQuery;
import server.essp.timesheet.report.utilizationRate.detail.exporter.UtilRateForMonthsExporter;
import client.essp.timesheet.period.VwTsPeriod;
import com.wits.util.comDate;
import java.util.Date;
import c2s.dto.DtoComboItem;

/**
 * <p>Title: AcUtilRateExport</p>
 *
 * <p>Description: Œ§³ö</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author TuBaohui
 * @version 1.0
 */

public class AcUtilRateExport extends AbstractExcelAction {
    public void execute(HttpServletRequest request,
                        HttpServletResponse response,
                        OutputStream os, Parameter param) throws Exception {
        DtoUtilRateQuery dtoQuery = new DtoUtilRateQuery();
        String typeS = (String)param.get(DtoUtilRateKey.DTO_UTIL_RATE_TYPE);
        int type = Integer.valueOf(typeS);
        String acntId = (String)param.get(DtoUtilRateKey.DTO_ACCOUNT_ID);
        String loginId = (String)param.get(DtoUtilRateKey.DTO_LOGINID);
        String bDate = (String)param.get(DtoUtilRateKey.DTO_BEGIN_DATE);
        String eDate = (String)param.get(DtoUtilRateKey.DTO_END_DATE);
        String includeSub = (String)param.get(DtoUtilRateKey.DTO_INCLUDE_SUB);
        Date beginDate = comDate.toDate(bDate,"yyyy-MM-dd");
        Date endDate = comDate.toDate(eDate,"yyyy-MM-dd");
        dtoQuery.setLoginId(loginId);
        dtoQuery.setBegin(beginDate);
        dtoQuery.setEnd(endDate);
        dtoQuery.setAcntId(acntId);
        if(includeSub.equals("true")){
            dtoQuery.setIsSub(true);
        }else{
            dtoQuery.setIsSub(false);
        }
        IUtilRateService lg = (IUtilRateService) this.getBean("iUtilRateService");
        lg.setExcelDto(true);
        Vector userList;
        if(loginId == null || "".equals(loginId)) {
            userList = lg.getUserList(acntId);
        } else {
            userList = new Vector();
            DtoComboItem item = new DtoComboItem(loginId);
            userList.add(item);
        }

        String typeStr = "";
        List deptList = new ArrayList();
        if (type == VwTsPeriod.PERIOD_MODEL_ANY) {
            deptList = lg.getUtilRateData(dtoQuery, userList);
            typeStr = DtoUtilRateKey.DTO_TYPE_PERIOD;
        } else if (type == VwTsPeriod.PERIOD_MODEL_TS) {
            deptList = lg.getUtilRateDataByCycle(dtoQuery, userList);
            typeStr = DtoUtilRateKey.DTO_TYPE_TIMESHEET;
        } else if (type == VwTsPeriod.PERIOD_MODEL_MONTH) {
            deptList = lg.getUtilRateDataByMonths(dtoQuery, userList);
            typeStr = DtoUtilRateKey.DTO_TYPE_MONTH;
        }
        String fileName = UtilRateForCycleExporter.OUT_FILE_PREFIX
                          + "-" + typeStr + "-"
                          + UtilRateForCycleExporter.OUT_FILE_POSTFIX;
        Parameter inputData = new Parameter();
        inputData.putAll(param);
        inputData.put(DtoUtilRateKey.DTO_DEPT_LIST, deptList);
        try {
            if (type == VwTsPeriod.PERIOD_MODEL_ANY) {
                UtilRateExporter utilExporter = new UtilRateExporter(fileName);
                utilExporter.webExport(response, os, inputData);
            } else if (type == VwTsPeriod.PERIOD_MODEL_TS) {
                UtilRateForCycleExporter utilCycle = new
                        UtilRateForCycleExporter(fileName);
                utilCycle.webExport(response, os, inputData);
            } else if (type == VwTsPeriod.PERIOD_MODEL_MONTH) {
                UtilRateForMonthsExporter utilMonth = new
                        UtilRateForMonthsExporter(fileName);
                utilMonth.webExport(response, os, inputData);
            }
        } catch (Exception ex) {
            throw new BusinessException("Error", "Error when Call export() 0f SampleExcelExporter ", ex);
        }
    }
}


