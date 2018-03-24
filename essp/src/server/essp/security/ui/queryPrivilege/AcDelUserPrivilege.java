/*
 * Created on 2008-6-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.security.ui.queryPrivilege;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.security.service.queryPrivilege.IQueryPrivilegeService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
import c2s.essp.common.user.DtoUserBase;

public class AcDelUserPrivilege extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData data) throws BusinessException {
        String loginId = (String) data.getInputInfo().getInputObj(DtoUserBase.USER_LOGIN_ID);;
        IQueryPrivilegeService service = (IQueryPrivilegeService)
                                         this.getBean("queryPrivilegeService");
        service.clearQueryPrivilege(loginId);
    }
}

