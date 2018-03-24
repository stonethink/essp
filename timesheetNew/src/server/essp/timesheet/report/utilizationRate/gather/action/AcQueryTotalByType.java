package server.essp.timesheet.report.utilizationRate.gather.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import c2s.essp.timesheet.report.DtoUtilRateKey;
import c2s.essp.timesheet.report.DtoUtilRateQuery;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.report.utilizationRate.gather.service.IUtilRateGatherService;
import server.framework.common.BusinessException;
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
public class AcQueryTotalByType  extends AbstractESSPAction {
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData data) throws
            BusinessException {
      IUtilRateGatherService lg = (IUtilRateGatherService) this.getBean("iUtilRateGatherService");
      DtoUtilRateQuery dtoQuery = (DtoUtilRateQuery) data.getInputInfo().
                                getInputObj(DtoUtilRateKey.DTO_UNTIL_RATE_QUERY);
      Map deptList = lg.getDataTreeByYear(dtoQuery);
      data.getReturnInfo().setReturnObj(DtoUtilRateKey.DTO_DEPT_LIST,deptList);
    }
}
