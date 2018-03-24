package server.essp.timesheet.account.labor.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.essp.timesheet.account.DtoAccount;
import server.essp.timesheet.account.labor.service.ILaborService;
import java.util.List;
import c2s.essp.timesheet.labor.DtoLaborResource;
import server.essp.timesheet.account.labor.role.service.ILaborRoleService;
import server.essp.timesheet.account.labor.level.service.ILaborLevelService;
import c2s.essp.timesheet.labor.level.DtoLaborLevel;
import c2s.essp.timesheet.labor.role.DtoLaborRole;

/**
 * <p>Title: list labor resource action</p>
 *
 * <p>Description: 列出指定项目下所有人力资源</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class AcListLabor extends AbstractESSPAction {

    /**
     * executeAct
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
            BusinessException {
        Long rid = (Long) data.getInputInfo()
                              .getInputObj(DtoAccount.DTO_RID);
        ILaborService laborService = (ILaborService) this.getBean("laborService");
        ILaborRoleService roleService = (ILaborRoleService) this.getBean("laborRoleService");
        ILaborLevelService levelService = (ILaborLevelService) this.getBean("laborLevelService");

        List list = laborService.listLabor(rid);
        data.getReturnInfo().setReturnObj(DtoLaborResource.DTO_LIST, list);
        data.getReturnInfo().setReturnObj(DtoLaborRole.DTO_LIST, roleService.ListLaborRoleDtoComboItem());
        data.getReturnInfo().setReturnObj(DtoLaborLevel.DTO_LIST, levelService.ListLaborLevelDtoComboItem());
    }
}
