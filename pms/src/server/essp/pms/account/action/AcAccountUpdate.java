package server.essp.pms.account.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.IDto;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.pms.account.DtoAcntKEY;
import c2s.essp.pms.account.DtoPmsAcnt;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.pms.account.logic.LgAccount;
import server.framework.common.BusinessException;
import server.essp.pms.account.logic.LgOsp;

public class AcAccountUpdate extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws BusinessException {

        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        DtoPmsAcnt dto = (DtoPmsAcnt) inputInfo.getInputObj(DtoAcntKEY.DTO);
        if(dto.isOSP()){//如果是osp,则调用lgOsp.ospUpdate()进行update
            LgOsp lgOsp = new LgOsp();
            lgOsp.ospUpdate(dto);
        }else{
            LgAccount logic = new LgAccount();

            logic.update(dto);
        }
        dto.setOp(IDto.OP_NOCHANGE);
        returnInfo.setReturnObj(DtoAcntKEY.DTO, dto);
    }
}
