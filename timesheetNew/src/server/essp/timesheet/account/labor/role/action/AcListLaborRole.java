package server.essp.timesheet.account.labor.role.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import c2s.essp.timesheet.labor.role.DtoLaborRole;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.account.labor.role.service.ILaborRoleService;
import server.framework.common.BusinessException;

public class AcListLaborRole extends AbstractESSPAction {

    /**
     * 列出所有Labor Role
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData data) throws BusinessException {

        ILaborRoleService service = (ILaborRoleService) this.getBean("laborRoleService");
        List list = service.listLaborRole();
        data.getReturnInfo().setReturnObj(DtoLaborRole.DTO_LIST, list);
    }
}
