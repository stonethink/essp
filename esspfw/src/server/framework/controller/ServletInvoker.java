package server.framework.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Wistron ITS Wuhan SDC</p>
 *
 * @author not attributable
 * @version 1.0
 */


public class ServletInvoker extends HttpServlet {
  private static final String CONTENT_TYPE = "text/html; charset=GBK";

  //Initialize global variables
  public void init() throws ServletException {
  }

  //Process the HTTP Get request
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType(CONTENT_TYPE);
    String pathInfo=request.getPathInfo();
    if(pathInfo!=null) {
      if(pathInfo.startsWith("/") && pathInfo.length()>0) {
        pathInfo=pathInfo.substring(1);
      }
      String servletClassName=pathInfo;
      try {
        Class servletClass = Class.forName(servletClassName);
        Class[] paramTypes=new Class[2];
        paramTypes[0]=HttpServletRequest.class;
        paramTypes[1]=HttpServletResponse.class;
        java.lang.reflect.Method method=servletClass.getDeclaredMethod("doGet",paramTypes);
        Object[] params=new Object[2];
        params[0]=request;
        params[1]=response;
        method.invoke(servletClass.newInstance(),params);
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  //Process the HTTP Post request
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType(CONTENT_TYPE);
    String pathInfo=request.getPathInfo();
    if(pathInfo!=null) {
      if(pathInfo.startsWith("/") && pathInfo.length()>0) {
        pathInfo=pathInfo.substring(1);
      }
      String servletClassName=pathInfo;
      try {
        Class servletClass = Class.forName(servletClassName);
        Class[] paramTypes=new Class[2];
        paramTypes[0]=HttpServletRequest.class;
        paramTypes[1]=HttpServletResponse.class;
        java.lang.reflect.Method method=servletClass.getDeclaredMethod("doPost",paramTypes);
        Object[] params=new Object[2];
        params[0]=request;
        params[1]=response;
        method.invoke(servletClass.newInstance(),params);
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  //Clean up resources
  public void destroy() {
  }
}
