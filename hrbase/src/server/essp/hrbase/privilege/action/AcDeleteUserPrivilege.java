/*
 * Created on 2007-12-29
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.hrbase.privilege.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.hrbase.privilege.service.IPrivilegeSiteService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;

public class AcDeleteUserPrivilege extends AbstractESSPAction {

    /**
     * 列出所有Site
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     */
    public void executeAct(HttpServletRequest request, HttpServletResponse response,
            TransactionData data) throws BusinessException {
        String loginId = request.getParameter("loginId");
        IPrivilegeSiteService logic = (IPrivilegeSiteService)this.
        getBean("PrivilegeService");
        logic.deleteByLoginId(loginId);
    }

}
