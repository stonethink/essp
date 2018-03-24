package server.essp.framework.authorize;

import javax.servlet.http.*;

import server.framework.authorize.*;
import c2s.essp.common.user.DtoUser;

public class ESSPAuthorizeImp implements IAuthorizable {
    public ESSPAuthorizeImp() {
    }

    /**
     * isAuthorized
     *
     * @param request HttpServletRequest
     * @return boolean
     * @todo Implement this server.framework.authorize.IAuthorizable method
     */
    public boolean isAuthorized(HttpServletRequest request) {
        HttpSession session=request.getSession();
        if(session==null) {
            session=request.getSession(true);
        }
        DtoUser user = (DtoUser)session.getAttribute(DtoUser.SESSION_USER);
        if(user==null) {
            return false;
        }
        return true;
    }
}
