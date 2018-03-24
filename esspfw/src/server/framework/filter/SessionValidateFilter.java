package server.framework.filter;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.FilterChain;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import com.wits.util.StringUtil;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;

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
public class SessionValidateFilter implements Filter{
    private String[] ignoreUri= null;
    private String name = null;
    private String errorPage = null;
    public void init(FilterConfig filterConfig) throws ServletException {
        String uris =  filterConfig.getInitParameter("ignoreUri");
        if(uris != null)
            ignoreUri = StringUtil.split(uris,",");
        name =  filterConfig.getInitParameter("name");
        errorPage =  filterConfig.getInitParameter("errorPage");
    }

    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException,
            ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String uri = request.getRequestURI();
        if(isIgnoreUri(uri) || isValidateRequest(request)){
            filterChain.doFilter(servletRequest, servletResponse);
        }else{
            HttpServletResponse response = (HttpServletResponse)servletResponse;
            response.sendRedirect(errorPage);
        }
    }
    private boolean isIgnoreUri(String uri){
        if(ignoreUri == null)
            return true;
        for(int i = 0;i < ignoreUri.length ;i ++){
            if(uri.indexOf(ignoreUri[i]) != -1){
                return true;
            }
        }
        return false;
    }
    private boolean isValidateRequest(HttpServletRequest request){
        HttpSession session  = request.getSession();
        if(session == null)
            session = request.getSession(true);
        return  session.getAttribute(name) != null;
    }
    public void destroy() {
        ignoreUri = null;
        name = null;
    }

}
