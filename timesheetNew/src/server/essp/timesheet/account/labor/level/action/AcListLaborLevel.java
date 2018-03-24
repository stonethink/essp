package server.essp.timesheet.account.labor.level.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import c2s.essp.timesheet.labor.level.DtoLaborLevel;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.account.labor.level.service.ILaborLevelService;
import server.framework.common.BusinessException;

public class AcListLaborLevel extends AbstractESSPAction {

    /**
     * 列出所有Labor Level
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData data) throws BusinessException {

        ILaborLevelService service = (ILaborLevelService) this.getBean("laborLevelService");
        List list = service.listLaborLevel();
        data.getReturnInfo().setReturnObj(DtoLaborLevel.DTO_LIST, list);
    }
}
