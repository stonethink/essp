package server.essp.security.ui.auth;

import server.framework.common.BusinessException;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import javax.servlet.http.HttpServletResponse;
import server.framework.action.AbstractBusinessAction;
import server.essp.security.service.authorize.IAuthService;
import c2s.essp.common.user.DtoUser;
import server.framework.common.Constant;
import java.util.List;
import server.essp.framework.action.AbstractESSPAction;

public class AcListAuthorize extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
            BusinessException {
        DtoUser dtoUser = (DtoUser)this.getSession().getAttribute(DtoUser.
                SESSION_LOGIN_USER);
        IAuthService logic = (IAuthService)this.getBean("AuthService");
        List list =(List)logic.listAuthorize(dtoUser.getUserLoginId());
        request.setAttribute(Constant.VIEW_BEAN_KEY, list);

    }
}
