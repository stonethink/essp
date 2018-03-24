package server.essp.timesheet.weeklyreport.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheetDetail;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.weeklyreport.service.ITimeSheetService;
import server.framework.common.BusinessException;

/**
 * <p>Title: AcDeleteTimeSheetDetail</p>
 *
 * <p>Description: 删除一条工时单明细记录</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class AcDeleteTimeSheetDetail extends AbstractESSPAction {

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

        DtoTimeSheetDetail dto = (DtoTimeSheetDetail) data.getInputInfo().getInputObj(DtoTimeSheetDetail.DTO);
        ITimeSheetService service = (ITimeSheetService) this.getBean("timeSheetService");
        service.deleteTimeSheetDetail(dto);
    }
}
