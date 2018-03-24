package server.framework.taglib.util;

import javax.servlet.http.*;

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
public class TabbedBean {
  private String title="";
  private String css="";
  private String scriptArea="";
  private String paramArea="";
  private String contentArea="";
  private HttpServletRequest request;

  public TabbedBean() {
    super();
    try {
      jbInit();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public String getContentArea() {
    if(request!=null) {
      String servletPath=request.getServletPath();
      int lastIndex=servletPath.lastIndexOf("/");
      String dir=servletPath.substring(0,lastIndex);
      return dir+"/"+contentArea;
    }

    return contentArea;
  }

  public String getCss() {
    return css;
  }

  public String getParamArea() {
    if(request!=null) {
      String servletPath=request.getServletPath();
      int lastIndex=servletPath.lastIndexOf("/");
      String dir=servletPath.substring(0,lastIndex);
      return dir+"/"+paramArea;
    }
    return paramArea;
  }

  public String getScriptArea() {
    if(request!=null) {
      String servletPath=request.getServletPath();
      int lastIndex=servletPath.lastIndexOf("/");
      String dir=servletPath.substring(0,lastIndex);
      return dir+"/"+scriptArea;
    }
    return scriptArea;
  }

  public String getTitle() {
    return title;
  }

  public void setContentArea(String contentArea) {
    this.contentArea = contentArea;
  }

  public void setCss(String css) {
    this.css = css;
  }

  public void setParamArea(String paramArea) {
    this.paramArea = paramArea;
  }

  public void setScriptArea(String scriptArea) {
    this.scriptArea = scriptArea;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setRequest(HttpServletRequest request) {
    this.request = request;
  }

  private void jbInit() throws Exception {
  }

  public HttpServletRequest getRequest() {
    return request;
  }

}
