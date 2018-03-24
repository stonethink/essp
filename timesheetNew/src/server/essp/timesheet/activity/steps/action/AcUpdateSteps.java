package server.essp.timesheet.activity.steps.action;

import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import server.essp.timesheet.activity.steps.service.IStepsService;
import c2s.essp.timesheet.activity.DtoActivityKey;

/**
 * <p>Description: ÐÞ¸ÄStepµÄAction</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class AcUpdateSteps extends AbstractESSPAction{
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData data) throws BusinessException {
        InputInfo inputInfo = data.getInputInfo();
        ReturnInfo returnInfo = data.getReturnInfo();
        IStepsService lg = (IStepsService)this.getBean("iStepsService");
        List list = (List) inputInfo.getInputObj(DtoActivityKey.STEP_LIST);
        List stepList = lg.updateActivityStep(list);
        returnInfo.setReturnObj(DtoActivityKey.STEP_LIST, stepList);
    }
}
