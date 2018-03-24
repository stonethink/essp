package server.essp.pms.quality.goal.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.pms.account.DtoAcntKEY;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.pms.quality.goal.logic.LgQualityGoal;
import server.framework.common.BusinessException;
import c2s.essp.common.account.IDtoAccount;
import itf.account.AccountFactory;

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
public class AcQualityGoalList extends AbstractESSPAction {
    public AcQualityGoalList() {
    }

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
                           HttpServletResponse response,
                           TransactionData transData) throws
        BusinessException {
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        Long acntRid = (Long) inputInfo.getInputObj(DtoAcntKEY.ACNT_RID);
        IDtoAccount accountDto = (IDtoAccount) request.getSession().
                                     getAttribute(IDtoAccount.SESSION_KEY);
//        IDtoAccount account =(IDtoAccount) AccountFactory.create().
//                             getAccountByRID(acntRid);
        if (acntRid == null && accountDto != null) {
            acntRid = accountDto.getRid();
        }
        LgQualityGoal lgQualityGoal = new LgQualityGoal();

        List list = lgQualityGoal.listQualityGoal(acntRid);
        returnInfo.setReturnObj("qualityGoalList", list);
        returnInfo.setReturnObj("accountDto",accountDto);
    }
}
