package server.essp.pms.account.baseline.action;

import server.framework.action.AbstractBusinessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import c2s.dto.ReturnInfo;
import c2s.essp.pms.account.DtoAcntBL;
import c2s.dto.InputInfo;
import server.essp.pms.account.baseline.logic.LgAccountBaseline;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class AcAcntBLSave extends AbstractBusinessAction {
    public AcAcntBLSave() {
    }

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
        BusinessException {

        ReturnInfo returnInfo = data.getReturnInfo();
        InputInfo inputInfo = data.getInputInfo();
        DtoAcntBL dtoAcntBL = (DtoAcntBL) inputInfo.getInputObj("AcntBaseLine");
        Boolean bIsPM = (Boolean) inputInfo.getInputObj("IsPM");
        Boolean bIsManager = (Boolean) inputInfo.getInputObj("IsManager");
        Boolean bIsApplication = (Boolean) inputInfo.getInputObj("IsApplication");

        LgAccountBaseline acntBL = new LgAccountBaseline();

        acntBL.saveOrUpdateBaseLine(dtoAcntBL,bIsPM,bIsManager,bIsApplication);

    }
}
