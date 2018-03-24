package server.essp.pms.account.action;

import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.ReturnInfo;
import c2s.essp.pms.account.DtoPmsAcnt;
import c2s.dto.InputInfo;
import c2s.essp.pms.account.DtoAcntKEY;
import server.essp.pms.account.logic.LgAccount;
import c2s.dto.IDto;

public class AcAccountAdd extends AbstractESSPAction{

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws BusinessException {
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();
        //新增一个模板并保存
        DtoPmsAcnt dto = (DtoPmsAcnt) inputInfo.getInputObj(DtoAcntKEY.DTO);
        LgAccount logic = new LgAccount();

        logic.add(dto);

        dto.setOp(IDto.OP_NOCHANGE);
        returnInfo.setReturnObj(DtoAcntKEY.DTO, dto);

    }

}
