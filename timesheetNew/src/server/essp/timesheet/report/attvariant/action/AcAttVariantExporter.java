/*
 * Created on 2008-1-29
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.report.attvariant.action;

import java.io.OutputStream;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.wits.util.Parameter;
import com.wits.util.comDate;
import server.essp.common.excelUtil.AbstractExcelAction;
import server.essp.timesheet.report.attvariant.exporter.AttVariantExporter;
import server.essp.timesheet.report.attvariant.service.IAttVariantService;
import server.framework.common.BusinessException;
import c2s.essp.timesheet.report.DtoAttVariantQuery;

/**
 * AcAttVariantExporter
 * @author TuBaoHui
 */
  public class AcAttVariantExporter extends AbstractExcelAction {
            public void execute(HttpServletRequest request,
                    HttpServletResponse response,
                    OutputStream os, Parameter param) throws Exception {
        IAttVariantService lg = (IAttVariantService) this.getBean("attVariantService");
        String bDate = (String) param.get(DtoAttVariantQuery.DTO_BEGIN_DATE);
        String eDate = (String) param.get(DtoAttVariantQuery.DTO_END_DATE);
        String empId = (String) param.get(DtoAttVariantQuery.DTO_EMP_ID);
        String site = (String)param.get(DtoAttVariantQuery.DTO_SITE);
        String selectAll = (String)param.get(DtoAttVariantQuery.DTO_SELECT_ALL);
        DtoAttVariantQuery dtoQuery = new DtoAttVariantQuery();
        Date beginDate = null;
        Date endDate = null;
        if(bDate != null){
           beginDate = comDate.toDate(bDate, comDate.pattenDate);
        }
        if(eDate != null){
           endDate = comDate.toDate(eDate, comDate.pattenDate);
        }
        if(selectAll.equals("true")){
            dtoQuery.setSelectAll(true);
        }else{
            dtoQuery.setSelectAll(false);
        }
        dtoQuery.setBeginDate(beginDate);
        dtoQuery.setEndDate(endDate);
        dtoQuery.setEmpId(empId);
        dtoQuery.setSite(site);
        Map map = lg.queryByCondition(dtoQuery);
        String fileName = AttVariantExporter.OUT_FILE_PREFIX
        			  + bDate + "_" + eDate
                      + AttVariantExporter.OUT_FILE_POSTFIX;
        Parameter inputData = new Parameter();
        inputData.putAll(param);
        inputData.put(DtoAttVariantQuery.DTO_RESULT_LIST, map);
        try {
            AttVariantExporter exporter = new AttVariantExporter(fileName);
            exporter.webExport(response, os, inputData);
          } catch (Exception ex) {
             throw new BusinessException("Error",
                "Error when Call export() 0f SampleExcelExporter ", ex);
           }
         }
       }



