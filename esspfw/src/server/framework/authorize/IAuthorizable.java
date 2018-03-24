package server.framework.authorize;

import javax.servlet.http.HttpServletRequest;

public interface IAuthorizable {
    boolean isAuthorized(HttpServletRequest request);
}
