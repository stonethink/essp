package server.essp.timesheet.account.labor.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.essp.timesheet.labor.DtoLaborResource;
import server.essp.timesheet.account.labor.service.ILaborService;
import server.essp.timesheet.synchronization.service.ISyncMainService;

/**
 * <p>Title: delete labor resource action</p>
 *
 * <p>Description: 删除指定项目下的人力资源</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class AcDeleteLabor extends AbstractESSPAction {

    /**
     * 删除一条Labor记录
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     * @todo Implement this server.framework.action.AbstractBusinessAction
     *   method
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
            BusinessException {
        DtoLaborResource dtoLabor = (DtoLaborResource) data.getInputInfo()
                                    .getInputObj(DtoLaborResource.DTO);

        ILaborService service = (ILaborService) this.getBean("laborService");
        service.deleteLabor(dtoLabor);
        ISyncMainService syncService = (ISyncMainService) this.getBean("syncTSMainService");
        syncService.delete(dtoLabor.getLoginId(), dtoLabor.getAccountRid());
    }
}
