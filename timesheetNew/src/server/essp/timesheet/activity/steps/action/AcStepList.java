package server.essp.timesheet.activity.steps.action;

import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import c2s.essp.common.user.DtoUser;
import c2s.essp.timesheet.activity.DtoActivityKey;
import server.essp.timesheet.activity.general.service.IGeneralService;
import server.essp.timesheet.activity.steps.service.IStepsService;

/**
 * <p>Description:ÏÔÊ¾StepListµÄAction </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class AcStepList extends AbstractESSPAction{
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData data) throws BusinessException {
        InputInfo inputInfo = data.getInputInfo();
        ReturnInfo returnInfo = data.getReturnInfo();
        DtoUser user = this.getUser();
        String loginId = user.getUserLoginId();
        IGeneralService lgGeneral = (IGeneralService) this.getBean("iGeneralService");
        IStepsService lg = (IStepsService)this.getBean("iStepsService");
        Long activityRid = (Long) inputInfo.getInputObj(DtoActivityKey.DTO_RID);
        List stepList = lg.getStepsList(activityRid);
        returnInfo.setReturnObj(DtoActivityKey.STEP_LIST, stepList);
        returnInfo.setReturnObj(DtoActivityKey.DTO_ISPRIMARY_RESOURCE, 
                lgGeneral.isPrimaryResource(activityRid,loginId));
    }

}
