package server.essp.pms.pbs.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.pms.pbs.logic.LgPmsPbsAssignmentList;
import server.framework.common.BusinessException;

public class AcPmsPbsAssignmentUpdate extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws BusinessException {
         InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        List assignmentList = (List) inputInfo.getInputObj("assignmentList");

        LgPmsPbsAssignmentList logic = new LgPmsPbsAssignmentList();
        logic.setDbAccessor(this.getDbAccessor());
        logic.update(assignmentList);

        returnInfo.setReturnObj("assignmentList", assignmentList);
    }
}
