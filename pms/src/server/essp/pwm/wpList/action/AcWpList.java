package server.essp.pwm.wpList.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.common.account.IDtoAccount;
import server.framework.action.AbstractBusinessAction;
import server.framework.common.BusinessException;
import server.essp.pwm.wpList.logic.LgWpList;
import c2s.essp.pms.wbs.DtoWbsActivity;
import c2s.essp.pms.wbs.DtoActivity;

public class AcWpList extends AbstractBusinessAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws BusinessException {

        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        Long inAcntRid = null;
        String inAcntName = "";
        String inAcntCode = "";
        Long activityRid = (Long)inputInfo.getInputObj("activityRid");
        DtoWbsActivity dtoActivity = (DtoWbsActivity)inputInfo.getInputObj("Activity");

        IDtoAccount account = (IDtoAccount) request.getSession().getAttribute(
            IDtoAccount.SESSION_KEY);
        if (account != null) {
            inAcntRid = account.getRid();
            inAcntName = account.getName();
            inAcntCode = account.getId();
        } else if(dtoActivity !=null){
            inAcntRid = dtoActivity.getAcntRid();
            inAcntName = dtoActivity.getAccountName();
            inAcntCode = dtoActivity.getAcntCode();
        }else {
            throw new BusinessException("E000","Can't get the account information from session.Please choose one account first.");
        }

        LgWpList lgWpList = new LgWpList();
        List wpList = lgWpList.list(inAcntRid, activityRid);

        returnInfo.setReturnObj("wpList", wpList);
        returnInfo.setReturnObj("acntName", inAcntName);
        returnInfo.setReturnObj("acntRid", inAcntRid);
        returnInfo.setReturnObj("acntCode", inAcntCode);
    }
}
