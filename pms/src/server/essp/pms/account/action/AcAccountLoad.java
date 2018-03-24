package server.essp.pms.account.action;

import server.essp.framework.action.AbstractESSPAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import c2s.dto.InputInfo;
import c2s.essp.common.user.DtoUser;
import c2s.dto.ReturnInfo;
import server.essp.pms.account.logic.LgAccountUtilImpl;
import c2s.essp.common.account.IDtoAccount;
import c2s.essp.pms.account.DtoPmsAcnt;
import c2s.dto.DtoUtil;

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
public class AcAccountLoad extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData transData) throws BusinessException {
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();
        Long acntRid = (Long) inputInfo.getInputObj("acntRid");
        DtoUser user = (DtoUser) getSession().getAttribute("user");
        LgAccountUtilImpl logic = new LgAccountUtilImpl();
        IDtoAccount dtoAccount = logic.getAccountByRID(acntRid);
        DtoPmsAcnt acnt=new DtoPmsAcnt();
        try {
            DtoUtil.copyProperties(acnt, dtoAccount);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (user != null && user.getUserLoginId().equals(dtoAccount.getManager())) {
            acnt.setPm(true);
        }
        returnInfo.setReturnObj("DtoAccount", acnt);
    }
}
