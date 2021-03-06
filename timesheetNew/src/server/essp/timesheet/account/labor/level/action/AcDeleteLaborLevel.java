package server.essp.timesheet.account.labor.level.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;

import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.essp.timesheet.labor.level.DtoLaborLevel;
import server.essp.timesheet.account.labor.level.service.ILaborLevelService;

public class AcDeleteLaborLevel extends AbstractESSPAction {

    /**
     * ɾ��Labor Level
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData data) throws BusinessException {

        DtoLaborLevel dto = (DtoLaborLevel) data.getInputInfo().getInputObj(DtoLaborLevel.DTO);
        ILaborLevelService service = (ILaborLevelService) this.getBean("laborLevelService");
        service.deleteLaborLevel(dto);
    }
}
