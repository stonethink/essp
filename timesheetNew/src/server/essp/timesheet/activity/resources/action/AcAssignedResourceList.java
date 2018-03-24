package server.essp.timesheet.activity.resources.action;

import server.framework.common.BusinessException;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import java.util.List;

import server.essp.timesheet.activity.general.service.IGeneralService;
import server.essp.timesheet.activity.resources.service.IResourceService;
import c2s.essp.common.user.DtoUser;
import c2s.essp.timesheet.activity.DtoActivityKey;

/**
 * <p>Description:ÏÔÊ¾AssignedResourceListµÄAction </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class AcAssignedResourceList extends AbstractESSPAction{
    public void executeAct(HttpServletRequest request, HttpServletResponse response,
                           TransactionData data) throws BusinessException {
        InputInfo inputInfo = data.getInputInfo();
        ReturnInfo returnInfo = data.getReturnInfo();
        DtoUser user = this.getUser();
        String loginId = user.getUserLoginId();
        IResourceService lg = (IResourceService)this.getBean("iResourceService");
        IGeneralService lgGeneral = (IGeneralService) this.getBean("iGeneralService");
        Long activityRid = (Long) inputInfo.getInputObj(DtoActivityKey.DTO_RID);
        List resourceAssignList = lg.getAssignedResourceList(activityRid);
        returnInfo.setReturnObj(DtoActivityKey.RESOURCE_LIST,resourceAssignList);
        returnInfo.setReturnObj(DtoActivityKey.DTO_ISPRIMARY_RESOURCE,lgGeneral.isPrimaryResource(activityRid,loginId));
}

}
