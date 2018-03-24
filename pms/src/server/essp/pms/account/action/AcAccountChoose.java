package server.essp.pms.account.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.TransactionData;
import c2s.essp.common.account.IDtoAccount;
import c2s.essp.pms.account.DtoAcntKEY;
import c2s.essp.pms.account.DtoPmsAcnt;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import server.essp.pms.account.logic.LgAccountList;

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
public class AcAccountChoose extends AbstractESSPAction {


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

        InputInfo inputInfo = data.getInputInfo();
        DtoPmsAcnt dtoPmsAcnt = (DtoPmsAcnt)inputInfo.getInputObj( DtoAcntKEY.ACCOUNT_CHOOSED  );
        String condition = (String)inputInfo.getInputObj(DtoAcntKEY.ACCOUNT_LIST_CONDITION);
        String entryFunType = (String) inputInfo.getInputObj(DtoAcntKEY.ACCOUNT_ENTRY_FUN_TYPE);
        if( dtoPmsAcnt != null ){
            this.getSession().setAttribute( IDtoAccount.SESSION_KEY , dtoPmsAcnt );

            LgAccountList logic = new LgAccountList(condition, entryFunType);
            logic.setCustomizeAccount(dtoPmsAcnt.getRid());
        }
    }
}
