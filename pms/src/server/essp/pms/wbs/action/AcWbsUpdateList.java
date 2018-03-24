package server.essp.pms.wbs.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.essp.pms.wbs.DtoWbsActivity;
import c2s.essp.pms.wbs.DtoKEY;
import server.essp.pms.wbs.logic.LgWbs;
import java.util.List;

public class AcWbsUpdateList extends AbstractESSPAction {
    public AcWbsUpdateList() {
    }

    /**
     * executeAct
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
        UserTest.test();
        List nodeList=(List)data.getInputInfo().getInputObj(DtoKEY.DTO);
        LgWbs logic=new LgWbs();
        logic.updateList(nodeList);
        data.getReturnInfo().setReturnObj(DtoKEY.DTO,nodeList);
    }
}
