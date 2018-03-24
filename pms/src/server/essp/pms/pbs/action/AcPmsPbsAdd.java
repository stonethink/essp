package server.essp.pms.pbs.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.IDto;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.common.account.IDtoAccount;
import c2s.essp.pms.pbs.DtoPmsPbs;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.pms.pbs.logic.LgPmsPbs;
import server.framework.common.BusinessException;

public class AcPmsPbsAdd extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws BusinessException {

        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        DtoPmsPbs dto = (DtoPmsPbs) inputInfo.getInputObj("dto");
        IDtoAccount account = (IDtoAccount)request.getSession().getAttribute(IDtoAccount.SESSION_KEY);
        if( account != null ){
            dto.setAcntRid(account.getRid());
        }else{

            throw new BusinessException("Can't get the account information from session.");
        }

        LgPmsPbs logic = new LgPmsPbs();
        logic.setDbAccessor(this.getDbAccessor());
        logic.add(dto);

        dto.setOp( IDto.OP_NOCHANGE );
        returnInfo.setReturnObj("dto", dto);
    }
}