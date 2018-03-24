package server.framework.filter;

import java.io.IOException;
import javax.servlet.*;

public class CharsetEncodingFilter
    implements Filter
{
                protected FilterConfig conf;
    protected String defEncoding;
    protected String encoding;
    protected boolean ignore;

    public CharsetEncodingFilter()
    {
        conf = null;
        defEncoding = "GBK";
        encoding = null;
        ignore = true;
    }

    public void init(FilterConfig filterconfig)
        throws ServletException
    {
        conf = filterconfig;
        encoding = filterconfig.getInitParameter("encoding");
        String s = filterconfig.getInitParameter("ignore");
        if(encoding == null)
            encoding = defEncoding;
        if(s == null)
            ignore = true;
        else
        if(s.equalsIgnoreCase("true"))
            ignore = true;
        else
        if(s.equalsIgnoreCase("yes"))
            ignore = true;
        else
            ignore = false;
    }

    public void doFilter(ServletRequest servletrequest, ServletResponse servletresponse, FilterChain filterchain)
        throws IOException, ServletException
    {
        if((ignore || servletrequest.getCharacterEncoding() == null) && encoding != null)
            servletrequest.setCharacterEncoding(encoding);
        filterchain.doFilter(servletrequest, servletresponse);
    }

    public void destroy()
    {
        conf = null;
        encoding = null;
    }


}
