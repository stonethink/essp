package server.essp.timesheet.account.labor.role.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import c2s.essp.timesheet.labor.role.DtoLaborRole;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.account.labor.role.service.ILaborRoleService;
import server.framework.common.BusinessException;

public class AcMoveDownLaborRole extends AbstractESSPAction {

    /**
     * …œ“∆Labor Role
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData data) throws BusinessException {

        DtoLaborRole dto = (DtoLaborRole) data.getInputInfo().getInputObj(DtoLaborRole.DTO);
        ILaborRoleService service = (ILaborRoleService) this.getBean("laborRoleService");
        service.moveDownLaborRole(dto);
    }

}
