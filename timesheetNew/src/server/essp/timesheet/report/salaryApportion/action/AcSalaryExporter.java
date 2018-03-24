/*
 * Created on 2008-1-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.report.salaryApportion.action;

import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.common.excelUtil.AbstractExcelAction;
import server.essp.timesheet.report.salaryApportion.exporter.SalaryWkTsExporter;
import server.essp.timesheet.report.salaryApportion.service.ISalaryWorkHourService;
import server.framework.common.BusinessException;
import c2s.essp.common.user.DtoUser;
import c2s.essp.timesheet.report.DtoSalaryWkHrQuery;
import com.wits.util.Parameter;
import com.wits.util.ThreadVariant;
import com.wits.util.comDate;

  public class AcSalaryExporter extends AbstractExcelAction {
       public void execute(HttpServletRequest request,
                    HttpServletResponse response,
                    OutputStream os, Parameter param) throws Exception {
        
        String bDate = (String) param.get(DtoSalaryWkHrQuery.DTO_BEGIN_DATE);
        Date beginDate = null;
        DtoSalaryWkHrQuery dtoQry = new DtoSalaryWkHrQuery();
        if(bDate != null){
           beginDate = comDate.toDate(bDate, comDate.pattenDate);
        }
        String balanceOne = (String)param.get(DtoSalaryWkHrQuery.DTO_BALANCE_ONT);
        String balanceTwo = (String)param.get(DtoSalaryWkHrQuery.DTO_BALANCE_TWO);
        String site = (String)param.get(DtoSalaryWkHrQuery.DTO_SITE);
        if(balanceOne != null && balanceOne.equals("true")){
            dtoQry.setIsBalanceOne(true);
        }else{
            dtoQry.setIsBalanceOne(false);
        }
        dtoQry.setSite(site);
        if(balanceTwo != null && balanceTwo.equals("true")){
            dtoQry.setIsBalanceTwo(true);
        }else{
            dtoQry.setIsBalanceTwo(false);
        }
        ISalaryWorkHourService lg = (ISalaryWorkHourService) this.getBean("salaryWkHrService");
        ThreadVariant thread = ThreadVariant.getInstance();
        DtoUser user = (DtoUser)request.getSession().getAttribute(DtoUser.SESSION_USER);
        thread.set(DtoUser.SESSION_USER, user);
        String loginId = user.getUserLoginId();
        dtoQry.setBeginDate(beginDate);
        List resultList = lg.queryByCondition(loginId,dtoQry);
        String fileName = SalaryWkTsExporter.OUT_FILE_PREFIX
                      + SalaryWkTsExporter.OUT_FILE_POSTFIX;
        Parameter inputData = new Parameter();
        inputData.putAll(param);
        inputData.put(DtoSalaryWkHrQuery.DTO_QUERY_LIST, resultList);
        try {
            SalaryWkTsExporter exporter = new SalaryWkTsExporter(fileName);
            exporter.webExport(response, os, inputData);
          } catch (Exception ex) {
             throw new BusinessException("Error",
                "Error when Call export() 0f SampleExcelExporter ", ex);
           }
       }
  }
        
    
