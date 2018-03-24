package server.essp.pms.pbs.pbsAndFiles.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import server.essp.pms.pbs.pbsAndFiles.logic.LgPbsList;
import server.framework.action.AbstractBusinessAction;
import server.framework.common.BusinessException;

public class AcPbsUpdate extends AbstractBusinessAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws BusinessException {
         InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        List pbsList = (List) inputInfo.getInputObj("pbsList");

        LgPbsList logic = new LgPbsList();
        logic.setDbAccessor(this.getDbAccessor());
        logic.update(pbsList);

        returnInfo.setReturnObj("pbsList", pbsList);
    }
}
