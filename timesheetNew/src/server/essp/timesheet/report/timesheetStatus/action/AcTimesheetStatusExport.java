/*
 * Created on 2007-11-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.report.timesheetStatus.action;

import java.io.OutputStream;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.common.excelUtil.AbstractExcelAction;
import server.essp.timesheet.report.timesheetStatus.exporter.TimesheetStatusExporter;
import server.essp.timesheet.report.timesheetStatus.exporter.TsStatusByDeptExporter;
import server.essp.timesheet.report.timesheetStatus.service.ITimesheetStausService;
import server.framework.common.BusinessException;
import c2s.essp.common.user.DtoUser;
import c2s.essp.timesheet.report.DtoTsStatusQuery;
import com.wits.util.Parameter;
import com.wits.util.ThreadVariant;
import com.wits.util.comDate;
    /**
     * <p>Title: AcTimesheetStatusExport</p>
     *
     * <p>Description: 工時狀態報表導出</p>
     *
     * <p>Copyright: Copyright (c) 2007</p>
     *
     * <p>Company: WistronITS</p>
     *
     * @author tbh
     * @version 1.0
     */
     public class AcTimesheetStatusExport extends AbstractExcelAction {
            public void execute(HttpServletRequest request,
                    HttpServletResponse response,
                    OutputStream os, Parameter param) throws Exception {
            //將開始，結束日期轉換成DATE類型，并放入查詢DTO中
            DtoTsStatusQuery dtoQuery = new DtoTsStatusQuery();
            String bDate = (String) param.get(DtoTsStatusQuery.DTO_BEGIN_DATE);
            String eDate = (String) param.get(DtoTsStatusQuery.DTO_END_DATE);
            String deptId = (String) param.get(DtoTsStatusQuery.DTO_DEPT_ID);
            String isDeptQuery = (String)param.get(DtoTsStatusQuery.DTO_IS_DEPT_QUERY);
            String includeSub = (String)param.get(DtoTsStatusQuery.DTO_INCLUDE_SUB);
            String site = (String)param.get(DtoTsStatusQuery.DTO_SITE);
            Date beginDate = comDate.toDate(bDate, "yyyy-MM-dd");
            Date endDate = comDate.toDate(eDate, "yyyy-MM-dd");
            dtoQuery.setBeginDate(beginDate);
            dtoQuery.setEndDate(endDate);
            dtoQuery.setDeptId(deptId);
            if(site != null && !"".equals(site)){
                dtoQuery.setSite(site);
            }
            if(isDeptQuery.equals("true")){
                dtoQuery.setIsDeptQuery(true);
            }else{
                dtoQuery.setIsDeptQuery(false);
            }
            if(includeSub != null && includeSub.equals("true")){
                dtoQuery.setIsSub(true);
            }else if(includeSub != null && includeSub.equals("false")){
                dtoQuery.setIsSub(false);
            }
            //得到當前登錄者的LOGINID
            ThreadVariant thread = ThreadVariant.getInstance();
            DtoUser user = (DtoUser)request.getSession().getAttribute(DtoUser.SESSION_USER);
            thread.set(DtoUser.SESSION_USER, user);
            String loginId = user.getUserLoginId();
            
            ITimesheetStausService tsStatusLg = (ITimesheetStausService) 
                                                 this.getBean("tsStatusService");
            tsStatusLg.setExcelDto(true);
            Map resultList = tsStatusLg.queryByPeriod(dtoQuery, loginId,true);
            Parameter inputData = new Parameter();
            inputData.putAll(param);
            inputData.put(DtoTsStatusQuery.DTO_RESULT_LIST, resultList);
            try {
                if(isDeptQuery.equals("true")){
                   TsStatusByDeptExporter exporter = new TsStatusByDeptExporter();
                   exporter.webExport(response, os, inputData);
                }else{
                  TimesheetStatusExporter exporter = new TimesheetStatusExporter();
                  exporter.webExport(response, os, inputData);
                }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BusinessException("Error",
                "Error when Call export() 0f SampleExcelExporter ", ex);
         }
        }
       }


