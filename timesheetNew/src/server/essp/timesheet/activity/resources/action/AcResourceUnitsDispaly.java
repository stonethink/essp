package server.essp.timesheet.activity.resources.action;

import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import javax.servlet.http.HttpServletResponse;
import server.essp.timesheet.activity.resources.service.IResourceService;
import c2s.essp.timesheet.activity.DtoActivityKey;

/**
 * <p>Description:��ʾResourceUnitsDispaly��Action </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class AcResourceUnitsDispaly extends AbstractESSPAction{
    public void executeAct(HttpServletRequest request, HttpServletResponse response,
                           TransactionData data) throws BusinessException {

       InputInfo inputInfo = data.getInputInfo();
       ReturnInfo returnInfo = data.getReturnInfo();
       IResourceService lg = (IResourceService) this.getBean("iResourceService");
       Long assignmentObjectId = (Long) inputInfo.getInputObj(DtoActivityKey.ASSIGMENT_RID);
       returnInfo.setReturnObj(DtoActivityKey.DTO_RESOURCE_UNIT, lg.getResUnits(assignmentObjectId));

}
}
