package server.essp.timesheet.report.timesheet.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.essp.timesheet.report.DtoQueryCondition;
import c2s.essp.timesheet.report.DtoTsDetailReport;
import server.essp.timesheet.report.timesheet.service.ITsReportService;
import java.util.List;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author wenhaizheng
 * @version 1.0
 */
public class AcQueryReport extends AbstractESSPAction {
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
        DtoQueryCondition dto = (DtoQueryCondition) data.getInputInfo()
                                .getInputObj(DtoTsDetailReport.DTO_CONDITION);
        ITsReportService service = (ITsReportService) this.getBean("tsReportService");
        service.setExcelDto(false);
        List list = service.queryReport(dto, this.getUser().getUserLoginId());
        data.getReturnInfo().setReturnObj(DtoTsDetailReport.DTO_QUERY_RESULT, list);

    }
}
