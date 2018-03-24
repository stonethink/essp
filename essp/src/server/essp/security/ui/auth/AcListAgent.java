package server.essp.security.ui.auth;

import server.framework.common.BusinessException;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import javax.servlet.http.HttpServletResponse;
import server.framework.action.AbstractBusinessAction;
import c2s.essp.common.user.DtoUser;
import server.essp.security.service.authorize.IAuthService;
import java.util.List;
import server.framework.common.Constant;
import server.essp.framework.action.AbstractESSPAction;

public class AcListAgent extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
            BusinessException {
        DtoUser dtoUser =(DtoUser)this.getSession().getAttribute(DtoUser.SESSION_LOGIN_USER);
        IAuthService logic = (IAuthService)this.getBean("AuthService");
        List list = (List)logic.listAgent(dtoUser.getUserLoginId());
        if(request.getSession().getAttribute("SelectUser")==null){
            request.getSession().setAttribute("SelectUser","");
        }
        request.setAttribute(Constant.VIEW_BEAN_KEY, list);

    }
}
