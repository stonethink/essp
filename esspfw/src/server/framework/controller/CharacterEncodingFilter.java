package server.framework.controller;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import server.framework.common.Constant;

public class CharacterEncodingFilter implements Filter {
  protected String responseEncoding= null;
  protected String requestEncoding=null;
  //protected String encoding = null;
  protected FilterConfig filterConfig = null;
  protected boolean ignore = true;

  public void destroy() {
    this.responseEncoding = null;
    this.requestEncoding = null;
    this.filterConfig = null;
  }

  public void doFilter(ServletRequest request, ServletResponse response,
                       FilterChain chain) throws IOException, ServletException {

// 设置正确的编码方式
    if (!ignore) {
      //String encoding = selectEncoding(request);
      if (requestEncoding != null) {
        request.setCharacterEncoding(requestEncoding);
      }
      if(responseEncoding!=null) {
        response.setCharacterEncoding(responseEncoding);
      }
    }

// 传递到下一层过滤器
    chain.doFilter(request, response);

  }

  public void init(FilterConfig filterConfig) throws ServletException {

    this.filterConfig = filterConfig;
    this.requestEncoding=filterConfig.getInitParameter("requestEncoding");
    this.responseEncoding=filterConfig.getInitParameter("responseEncoding");
    //this.encoding = filterConfig.getInitParameter("encoding");
    String value = filterConfig.getInitParameter("ignore");
    if (value == null) {
      this.ignore = true;
    } else if (value.equalsIgnoreCase("true")) {
      this.ignore = true;
    } else if (value.equalsIgnoreCase("yes")) {
      this.ignore = true;
    } else {
      this.ignore = false;
    }
    Constant.IGNORE_ENCODING=this.ignore;
  }
}
