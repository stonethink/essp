package server.essp.timesheet.weeklyreport.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheet;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.weeklyreport.service.ITimeSheetService;
import server.framework.common.BusinessException;

/**
 * <p>Title: AcLoadTimeSheetByDate</p>
 *
 * <p>Description: 根据日期和当前用户获取TimeSheet</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class AcLoadTimeSheetByDate extends AbstractESSPAction {

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
                           HttpServletResponse response, TransactionData data) throws
            BusinessException {
        Date workDay = (Date) data.getInputInfo().getInputObj("workDay");
        ITimeSheetService service = (ITimeSheetService) this.getBean("timeSheetService");
        String loginId = this.getUser().getUserLoginId();
        DtoTimeSheet dto = service.getTimeSheet(loginId, workDay);
        data.getReturnInfo().setReturnObj(DtoTimeSheet.DTO, dto);
    }
}
