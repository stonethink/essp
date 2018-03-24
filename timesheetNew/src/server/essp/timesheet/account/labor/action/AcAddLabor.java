package server.essp.timesheet.account.labor.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import server.essp.timesheet.account.labor.service.ILaborService;
import c2s.essp.timesheet.labor.DtoLaborResource;

/**
 * <p>Title: add labor resource action</p>
 *
 * <p>Description: 在指定项目下新增人力资源</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class AcAddLabor extends AbstractESSPAction {

    /**
     * 新增一条Labor记录
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     * @todo Implement this server.framework.action.AbstractBusinessAction
     *   method
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData data) throws
            BusinessException {
        DtoLaborResource dtoLabor = (DtoLaborResource) data.getInputInfo()
                                    .getInputObj(DtoLaborResource.DTO);

        ILaborService service = (ILaborService) this.getBean("laborService");
        service.addLabor(dtoLabor);

    }
}
