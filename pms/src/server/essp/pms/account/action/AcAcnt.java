package server.essp.pms.account.action;

import c2s.dto.*;
import c2s.essp.common.user.DtoUser;
import c2s.essp.pms.account.DtoPmsAcnt;
import java.util.List;
import javax.servlet.http.*;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.pms.account.logic.LgAccountList;
import server.framework.common.BusinessException;

public class AcAcnt extends AbstractESSPAction{

    public void executeAct(HttpServletRequest request, HttpServletResponse response, TransactionData transData)
        throws BusinessException
    {
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();
        Long acntrid = (Long)inputInfo.getInputObj("acntRid");
        DtoUser user = (DtoUser)getSession().getAttribute("user");
        DtoPmsAcnt dtoAccount = null;
        LgAccountList logic = new LgAccountList();
        List accountList = null;
        accountList = logic.listAccountByLoginID(user.getUserLoginId(), null, null);
        if(accountList !=null){
            for (int i = 0; i < accountList.size(); i++) {
                DtoPmsAcnt dto = (DtoPmsAcnt) accountList.get(i);
                if (dto.getRid().equals(acntrid)) {
                    dtoAccount = dto;
                }
            }
            returnInfo.setReturnObj("DtoAccount", dtoAccount);
        }
    }
}
