package server.essp.timesheet.weeklyreport.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheet;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.weeklyreport.service.ITimeSheetService;
import server.framework.common.BusinessException;

/**
 * <p>Title: AcLoadTimeSheetByRid</p>
 *
 * <p>Description: 根据RID获取TimeSheet</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class AcLoadTimeSheetByRid extends AbstractESSPAction {

    /**
     * executeAct
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     * @todo Implement this server.framework.action.AbstractBusinessAction
     *   method
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData data) throws BusinessException {

        Long tsRid = (Long) data.getInputInfo().getInputObj(DtoTimeSheet.DTO_RID);
        ITimeSheetService service = (ITimeSheetService) this.getBean("timeSheetService");
        DtoTimeSheet dto = service.getTimeSheet(tsRid);
        data.getReturnInfo().setReturnObj(DtoTimeSheet.DTO, dto);
    }
}
