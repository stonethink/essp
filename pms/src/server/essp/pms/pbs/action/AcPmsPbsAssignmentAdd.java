package server.essp.pms.pbs.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.pms.pbs.DtoPmsPbsAssignment;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.pms.pbs.logic.LgPmsPbsAssignment;
import server.framework.common.BusinessException;

public class AcPmsPbsAssignmentAdd extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws BusinessException {
         InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        DtoPmsPbsAssignment dtoPmsPbsAssignment = (DtoPmsPbsAssignment) inputInfo.getInputObj("dto");

        LgPmsPbsAssignment logic = new LgPmsPbsAssignment();
        logic.add(dtoPmsPbsAssignment);

        returnInfo.setReturnObj("dto", dtoPmsPbsAssignment);
    }
}
