package server.essp.common.humanAllocate.action;

import javax.servlet.http.*;

import c2s.dto.*;
import server.framework.action.*;
import server.framework.common.*;
import server.essp.common.humanAllocate.logic.LgUserAlloc;
import java.util.List;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.common.humanAllocate.form.AfUserAllocInAD;
import server.essp.common.humanAllocate.viewbean.VbAllocInAD;
import server.essp.common.ldap.LDAPUtil;

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
public class AcUserAllocInADGroup extends AbstractESSPAction {
    /**
     * executeAct
     *
     * @param httpServletRequest HttpServletRequest
     * @param httpServletResponse HttpServletResponse
     * @param transactionData TransactionData
     * @throws BusinessException
     * @todo Implement this server.framework.action.AbstractBusinessAction
     *   method
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse reponse,
                           TransactionData data) throws BusinessException {
        AfUserAllocInAD directForm = (AfUserAllocInAD) this.getForm();
        log.info("search condition,loginid;" + directForm.getLoginId() + "\n" +
                 "empname:" + directForm.getEmpName() + "\n" +
                 "empmail:" + directForm.getEmpMail());

        LDAPUtil ldap = new LDAPUtil();
        List result = ldap.findUserInGroup(directForm.getSelectDomain(), directForm.getEmpMail());
        VbAllocInAD vb = new VbAllocInAD();
        vb.setDetail(result);
        request.setAttribute(Constant.VIEW_BEAN_KEY,vb);
        data.getReturnInfo().setForwardID("success");
    }
}
