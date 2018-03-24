package server.essp.timesheet.period.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheetPeriod;
import java.util.Date;
import server.essp.timesheet.period.service.IPeriodService;
/**
 *
 * <p>Title: AcQueryPeriod</p>
 *
 * <p>Description: 查询某所在的工作区间</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author wenhaizheng
 * @version 1.0
 */
public class AcQueryPeriod extends AbstractESSPAction {
    /**
     * executeAct
     *
     * @param httpServletRequest HttpServletRequest
     * @param httpServletResponse HttpServletResponse
     * @param transactionData TransactionData
     * @throws BusinessException
     * @todo Implement this server.framework.action.AbstractBusinessAction
     *   method
     */
    public void executeAct(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           TransactionData data) throws
            BusinessException {
        Date day = (Date) data.getInputInfo().getInputObj(DtoTimeSheetPeriod.DTO_DAY);
        IPeriodService service = (IPeriodService) this.getBean("periodService");
        DtoTimeSheetPeriod period = service.queryPeriodByDay(day);
        data.getReturnInfo().setReturnObj(DtoTimeSheetPeriod.DTO_PERIOD, period);
    }
}
