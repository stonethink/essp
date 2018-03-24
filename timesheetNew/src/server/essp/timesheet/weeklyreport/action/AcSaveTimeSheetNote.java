package server.essp.timesheet.weeklyreport.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheet;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.weeklyreport.service.ITimeSheetService;
import server.framework.common.BusinessException;

/**
 * <p>Title: AcSaveTimeSheetNote</p>
 *
 * <p>Description: 保存工时单记事</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class AcSaveTimeSheetNote extends AbstractESSPAction {
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

        Long tsRid = (Long) data.getInputInfo().getInputObj(DtoTimeSheet.DTO_RID);
        String note = (String) data.getInputInfo().getInputObj(DtoTimeSheet.DTO_NOTES);
        ITimeSheetService service = (ITimeSheetService) this.getBean("timeSheetService");
        String newNotes = service.saveTimeSheetNotes(note, tsRid);
        data.getReturnInfo().setReturnObj(DtoTimeSheet.DTO_NOTES, newNotes);
    }
}
