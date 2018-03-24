package server.essp.timesheet.activity.general.action;

import server.framework.common.BusinessException;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import server.essp.timesheet.activity.general.service.IGeneralService;
import c2s.essp.common.user.DtoUser;
import c2s.essp.timesheet.activity.DtoActivityKey;

/**
 * <p>Description:显示General的信息的Action</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author tbh
 * @version 1.0
 */
public class AcGeneralDisplay extends AbstractESSPAction{
    public void executeAct(HttpServletRequest request, HttpServletResponse response,
                           TransactionData data) throws BusinessException {

       InputInfo inputInfo = data.getInputInfo();
       ReturnInfo returnInfo = data.getReturnInfo();
       DtoUser user = this.getUser();
       String loginId = user.getUserLoginId();
       IGeneralService lg = (IGeneralService) this.getBean("iGeneralService");
       Long activityRid = (Long) inputInfo.getInputObj(DtoActivityKey.DTO_RID);
       returnInfo.setReturnObj(DtoActivityKey.DTO_GENERAL, lg.getActivityGeneral(activityRid));
       returnInfo.setReturnObj(DtoActivityKey.DTO_ISPRIMARY_RESOURCE,
               lg.isPrimaryResource(activityRid, loginId));

}
}
