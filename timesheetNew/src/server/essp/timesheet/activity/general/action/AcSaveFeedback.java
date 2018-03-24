package server.essp.timesheet.activity.general.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.essp.timesheet.activity.DtoActivityGeneral;
import server.essp.timesheet.activity.general.service.IGeneralService;
import c2s.essp.timesheet.activity.DtoActivityKey;

/**
 * <p>Title: AcSaveFeedback</p>
 *
 * <p>Description: ±£´æ×÷Òµfeedback×Ö¶Î</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class AcSaveFeedback extends AbstractESSPAction {

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
        Long rid = (Long) data.getInputInfo().getInputObj(DtoActivityKey.DTO_RID);
        String feedback = (String) data.getInputInfo().getInputObj(DtoActivityGeneral.KEY_FEEDBACK);
        IGeneralService service = (IGeneralService)this.getBean("iGeneralService");
        service.saveFeeedback(rid, feedback);
    }
}
