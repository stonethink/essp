package server.essp.timesheet.activity.resources.action;

import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.IDto;
import c2s.essp.timesheet.activity.DtoActivityKey;
import server.essp.timesheet.activity.resources.service.IResourceService;
import server.essp.timesheet.activity.resources.service.IResourceService;
import c2s.essp.timesheet.activity.DtoResourceUnits;

/**
 * <p>Description:ÐÞ¸ÄResourceUnitsµÄAction </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class AcUpdateResourceUnits extends AbstractESSPAction{
    public void executeAct(HttpServletRequest request, HttpServletResponse response,
                           TransactionData data) throws BusinessException {
       InputInfo inputInfo = data.getInputInfo();
       ReturnInfo returnInfo = data.getReturnInfo();
       DtoResourceUnits dto = (DtoResourceUnits) inputInfo.getInputObj(DtoActivityKey.DTO);
       IResourceService lg = (IResourceService) this.getBean("iResourceService");
       lg.updateReource(dto);
       dto.setOp(IDto.OP_NOCHANGE);
       returnInfo.setReturnObj(DtoActivityKey.DTO, dto);
   }
}
