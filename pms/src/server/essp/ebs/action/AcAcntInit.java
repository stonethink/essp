package server.essp.ebs.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.IDto;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.ebs.DtoPmsAcnt;
import server.essp.ebs.logic.LgAcnt;
import server.framework.action.AbstractBusinessAction;
import server.framework.common.BusinessException;
import server.essp.ebs.logic.LgAcntServices;

public class AcAcntInit extends AbstractBusinessAction {
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData transData) throws BusinessException {
        LgAcntServices logic = new LgAcntServices();
        logic.init(transData);
    }
}
