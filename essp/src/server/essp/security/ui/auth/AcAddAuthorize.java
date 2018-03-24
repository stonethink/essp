package server.essp.security.ui.auth;

import server.framework.common.BusinessException;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import javax.servlet.http.HttpServletResponse;
import c2s.essp.common.user.DtoUser;
import server.essp.security.service.authorize.IAuthService;
import server.essp.framework.action.AbstractESSPAction;


public class AcAddAuthorize extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
            BusinessException {
        AfAuthorize af = (AfAuthorize)this.getForm();
        String loginIds =af.getLoginId();
        String names = af.getLoginName();
        DtoUser dtoUser =(DtoUser)this.getSession().getAttribute(DtoUser.SESSION_LOGIN_USER);
        IAuthService logic = (IAuthService)this.getBean("AuthService");
        String[] loginId = loginIds.split(",");
        String[] name = names.split(",");
        logic.addAuthorize(dtoUser.getUserLoginId(),dtoUser.getUserName(),loginId,name);
    }
}
