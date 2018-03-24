package server.framework.filter;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.FilterChain;
import java.io.IOException;
import server.framework.hibernate.HBComAccess;
import com.wits.util.ThreadVariant;

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
public class CloseConnectionFilter implements Filter {
    public CloseConnectionFilter() {
    }

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException,
            ServletException {

        filterChain.doFilter(servletRequest, servletResponse);

        ThreadVariant thread = ThreadVariant.getInstance();
        String key = HBComAccess.class.getName();
        Object obj = thread.get(key);
        if (obj != null && obj instanceof HBComAccess) {
            HBComAccess dbAccessor = (HBComAccess) obj;
            try {
                dbAccessor.close();
            } catch (Exception ex) {
            }
        }
    }

    public void destroy() {
    }
}
