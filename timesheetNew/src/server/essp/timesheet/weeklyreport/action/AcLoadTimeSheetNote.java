package server.essp.timesheet.weeklyreport.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheet;
import server.essp.timesheet.weeklyreport.service.ITimeSheetService;

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
public class AcLoadTimeSheetNote extends AbstractESSPAction {
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
        Long tsRid = (Long) data.getInputInfo().getInputObj(DtoTimeSheet.DTO_RID);
        ITimeSheetService service = (ITimeSheetService) this.getBean("timeSheetService");
        String notes = service.getTimeSheetNotes(tsRid);
        data.getReturnInfo().setReturnObj(DtoTimeSheet.DTO_NOTES, notes);
    }
}
