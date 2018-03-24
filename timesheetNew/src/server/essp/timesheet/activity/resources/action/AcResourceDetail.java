package server.essp.timesheet.activity.resources.action;

import server.framework.common.BusinessException;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import server.essp.timesheet.activity.resources.service.IResourceService;
import c2s.essp.timesheet.activity.DtoActivityKey;

/**
 * <p>Description:ÏÔÊ¾ResourceDetailµÄAction</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class AcResourceDetail extends AbstractESSPAction{
    public void executeAct(HttpServletRequest request, HttpServletResponse response,
                           TransactionData data) throws BusinessException {
        InputInfo inputInfo = data.getInputInfo();
        ReturnInfo returnInfo = data.getReturnInfo();
        IResourceService lg = (IResourceService) this.getBean("iResourceService");
        Long assignmentObjectId = (Long) inputInfo.getInputObj(DtoActivityKey.ASSIGMENT_RID);
        returnInfo.setReturnObj(DtoActivityKey.DTO_RESOURCE_DETAIL, lg.getResDetail(assignmentObjectId));
     }
}
