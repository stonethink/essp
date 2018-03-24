package server.essp.ebs.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.ebs.DtoPmsAcnt;
import server.essp.ebs.logic.LgAcnt;
import server.framework.action.AbstractBusinessAction;
import server.framework.common.BusinessException;

public class AcAcntDelete extends AbstractBusinessAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws BusinessException {

        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        DtoPmsAcnt dto = (DtoPmsAcnt) inputInfo.getInputObj("dto");
        Long parentId = (Long) inputInfo.getInputObj("parentId");

//        Long lParentId;
//        try {
//            lParentId = new Long(parentId);
//        } catch (NumberFormatException ex) {
//            throw new BusinessException("E010101", "ParentId is not a number.");
//        }

        LgAcnt logic = new LgAcnt();
        logic.setDbAccessor(this.getDbAccessor());
        logic.delete(dto, parentId);
    }
}
