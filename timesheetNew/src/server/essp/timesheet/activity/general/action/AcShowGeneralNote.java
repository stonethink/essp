package server.essp.timesheet.activity.general.action;

import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import javax.servlet.http.HttpServletResponse;
import server.essp.timesheet.activity.general.service.IGeneralService;
import c2s.essp.timesheet.activity.DtoActivityKey;

/**
 * <p>Description:ÐÞ¸ÄNoteµÄAction </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author TBH
 * @version 1.0
 */
public class AcShowGeneralNote extends AbstractESSPAction{
    public void executeAct(HttpServletRequest request, HttpServletResponse response,
                           TransactionData data) throws BusinessException {

       InputInfo inputInfo = data.getInputInfo();
       ReturnInfo returnInfo = data.getReturnInfo();
       IGeneralService lg = (IGeneralService) this.getBean("iGeneralService");
       Long activityRid = (Long) inputInfo.getInputObj(DtoActivityKey.DTO_RID);
       returnInfo.setReturnObj(DtoActivityKey.DTO_GENERAL, lg.getGeneralNote(activityRid));

}
}
