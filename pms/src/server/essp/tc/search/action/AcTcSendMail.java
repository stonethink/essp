package server.essp.tc.search.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.tc.search.logic.LgTcSendMail;
import server.framework.common.BusinessException;


public class AcTcSendMail extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData transData) throws BusinessException {
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();
        List mailUserList = (List) inputInfo
                                         .getInputObj(DtoTcKey.MAIL_USER_LIST);

        LgTcSendMail logic = new LgTcSendMail();
        logic.send(mailUserList);
    }
}
