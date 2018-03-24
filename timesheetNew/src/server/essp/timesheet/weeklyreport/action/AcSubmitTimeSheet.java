package server.essp.timesheet.weeklyreport.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheet;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.weeklyreport.service.ITimeSheetService;
import server.framework.common.BusinessException;

/**
 * <p>Title: AcSubmitTimeSheet</p>
 *
 * <p>Description: 提交工时单</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class AcSubmitTimeSheet extends AbstractESSPAction {

    /**
     * executeAct
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData data) throws BusinessException {

        DtoTimeSheet dto = (DtoTimeSheet) data.getInputInfo().getInputObj(DtoTimeSheet.DTO);
        ITimeSheetService service = (ITimeSheetService) this.getBean("timeSheetService");
        service.submitTimeSheet(dto);
        data.getReturnInfo().setReturnObj(DtoTimeSheet.DTO, dto);
    }
}
