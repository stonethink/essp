package server.framework.authorize;

import javax.servlet.http.*;

public class DefaultAuthorizeImp implements IAuthorizable {
    public DefaultAuthorizeImp() {
    }

    /**
     * isAuthorized
     *
     * @param request HttpServletResponse
     * @return boolean
     * @todo Implement this server.framework.authorize.IAuthorizable method
     */
    public boolean isAuthorized(HttpServletRequest request) {
        return true;
    }
}
