package server.essp.pms.activity.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import c2s.essp.common.account.IDtoAccount;
import c2s.essp.pms.wbs.DtoKEY;
import c2s.essp.pms.wbs.DtoWbsActivity;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.pms.activity.logic.LgActivity;
import server.essp.pms.wbs.logic.LgWbs;
import server.framework.common.BusinessException;

public class AcActivityGetCode extends AbstractESSPAction {

    /**
     * executeAct
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     * @todo Implement this server.framework.action.AbstractBusinessAction
     *   method
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
        BusinessException {
        //UserTest.test();
        IDtoAccount accountDto = (IDtoAccount)this.getSession().getAttribute(
            IDtoAccount.SESSION_KEY);
        Long AcntRid = (Long)data.getInputInfo().getInputObj("Acntrid");

        DtoWbsActivity dto = (DtoWbsActivity) data.getInputInfo().getInputObj(
            DtoKEY.DTO);
        if(AcntRid ==null){
            if (accountDto == null) {
                data.getReturnInfo().setError(true);
                data.getReturnInfo().setReturnMessage(
                    "Please select a account at first!!!");
                return;
            }
            if (accountDto.getRid() == null) {
                data.getReturnInfo().setError(true);
                data.getReturnInfo().setReturnMessage("Account'Rid is null!!!");
                return;
            }
            dto.setAcntRid(accountDto.getRid());
        }else{
            dto.setAcntRid(AcntRid);
        }
        LgActivity logic = new LgActivity();
        logic.getCode(dto);
        //Activity默认完工比例计算方法，工期
        dto.setCompleteMethod(DtoWbsActivity.ACTIVITY_COMPLETE_BY_DURATION);
        data.getReturnInfo().setReturnObj(DtoKEY.DTO, dto);
    }
}
