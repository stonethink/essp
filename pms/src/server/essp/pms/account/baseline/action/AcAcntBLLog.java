package server.essp.pms.account.baseline.action;

import server.essp.framework.action.AbstractESSPAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import c2s.dto.ReturnInfo;
import java.util.List;
import c2s.essp.pms.account.DtoAcntBL;
import c2s.dto.InputInfo;
import server.framework.action.AbstractBusinessAction;
import server.essp.pms.account.baseline.logic.LgAccountBaseline;
import server.essp.pms.account.logic.LgAccountUtilImpl;
import c2s.essp.common.account.IDtoAccount;

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
public class AcAcntBLLog extends AbstractBusinessAction {
    public AcAcntBLLog() {
    }

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
        BusinessException {

        ReturnInfo returnInfo = data.getReturnInfo();
        InputInfo inputInfo = data.getInputInfo();

        Long lRid = (Long) inputInfo.getInputObj("rid");

        LgAccountBaseline acntBL = new LgAccountBaseline();

        List acntLists = acntBL.listBaseLineLog(lRid);
        //add by zj
        //ªÒ»°acntCode
        LgAccountUtilImpl lgAccountUtilImpl=new LgAccountUtilImpl();
        IDtoAccount account=lgAccountUtilImpl.getAccountByRID(lRid);
        String accountId=account.getId();

        returnInfo.setReturnObj("acntCode",accountId);
        returnInfo.setReturnObj("BaseLineLog", acntLists);


    }
}
