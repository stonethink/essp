package server.essp.security.ui.auth;

import server.framework.common.BusinessException;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import javax.servlet.http.HttpServletResponse;
import server.framework.action.AbstractBusinessAction;
import c2s.essp.common.user.DtoUser;
import server.essp.security.service.authorize.IAuthService;
import server.essp.framework.action.AbstractESSPAction;


public class AcDeleteAgent extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
            BusinessException {
         String loginId =(String)request.getParameter("loginId");
         DtoUser dtoUser =(DtoUser)this.getSession().getAttribute(DtoUser.SESSION_LOGIN_USER);
         IAuthService logic = (IAuthService)this.getBean("AuthService");
         logic.delAuthorize(dtoUser.getUserLoginId(),loginId);
    }
}
