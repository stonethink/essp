package server.essp.timesheet.activity.general.action;

import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.IDto;
import c2s.essp.timesheet.activity.DtoActivityGeneral;
import c2s.essp.timesheet.activity.DtoActivityKey;
import server.essp.timesheet.activity.general.service.IGeneralService;

/**
 * <p>Description: 更新General的详细信息的Action</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author TBH
 * @version 1.0
 */
public class AcGeneralUpdate  extends AbstractESSPAction{
    public void executeAct(HttpServletRequest request, HttpServletResponse response,
                           TransactionData data) throws BusinessException {


       InputInfo inputInfo = data.getInputInfo();
       ReturnInfo returnInfo = data.getReturnInfo();
       DtoActivityGeneral dto = (DtoActivityGeneral) inputInfo.getInputObj(DtoActivityKey.DTO_GENERAL);
       IGeneralService lg = (IGeneralService) this.getBean("iGeneralService");
       lg.updateGeneral(dto);
       dto.setOp(IDto.OP_NOCHANGE);
       returnInfo.setReturnObj(DtoActivityKey.DTO, dto);
}
}
