package server.essp.timesheet.report.utilizationRate.detail.action;

import server.framework.common.BusinessException;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.report.utilizationRate.detail.service.IUtilRateService;
import c2s.essp.timesheet.report.DtoUtilRateKey;
import java.util.List;
import c2s.essp.timesheet.report.DtoUtilRateQuery;
import java.util.ArrayList;
import java.util.Vector;
import client.essp.timesheet.period.VwTsPeriod;

/**
 * <p>Title: </p>
 *
 * <p>Description:≤È‘É </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author tbh
 * @version 1.0
 */
public class AcQueryByType  extends AbstractESSPAction {
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData data) throws
            BusinessException {
      IUtilRateService lg = (IUtilRateService) this.getBean("iUtilRateService");
      DtoUtilRateQuery dtoQuery = (DtoUtilRateQuery) data.getInputInfo().
                                getInputObj(DtoUtilRateKey.DTO_UNTIL_RATE_QUERY);
      int type = ((Integer)data.getInputInfo().getInputObj(DtoUtilRateKey.DTO_UTIL_RATE_TYPE)).intValue();
      Vector userList = (Vector)data.getInputInfo().getInputObj(DtoUtilRateKey.DTO_TYPE_USER_LIST);
      List deptList = new ArrayList();
      lg.setExcelDto(false);
      if(type==VwTsPeriod.PERIOD_MODEL_ANY){
          deptList = lg.getUtilRateData(dtoQuery,userList);
      }else if(type==VwTsPeriod.PERIOD_MODEL_TS){
         deptList = lg.getUtilRateDataByCycle(dtoQuery,userList);
      }else if(type == VwTsPeriod.PERIOD_MODEL_MONTH){
          deptList = lg.getUtilRateDataByMonths(dtoQuery,userList);
      }
      data.getReturnInfo().setReturnObj(DtoUtilRateKey.DTO_DEPT_LIST,deptList);

    }

}
