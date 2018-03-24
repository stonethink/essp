package server.framework.taglib.html;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import server.framework.taglib.util.SystemException;
import server.framework.taglib.util.Constants;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.util.ResponseUtils;
import org.apache.struts.validator.ValidatorForm;
import server.framework.taglib.util.TagUtils;

public class FormTag extends org.apache.struts.taglib.html.FormTag {
  private String name="";
  private String initialForcus;
  private Logger log;
  private String id;

  public FormTag() {
    initialForcus = null;
    log = Logger.getLogger(this.getClass());
  }

  public String getInitialForcus() {
    return initialForcus;
  }

  public void setInitialForcus(String str) {
    initialForcus = str;
  }

  public int doStartTag() throws JspException {
    String nameStr=this.getName();
    String actionUrl=this.getAction();
    try {
      lookup();
    } catch (Exception ex) {
      //ex.printStackTrace();
    }
    StringBuffer buf = new StringBuffer();
    String sStartElement = renderFormStartElement();
    buf.append(sStartElement.substring(0, sStartElement.length() - 1));
    if(this.id!=null && !this.id.trim().equals("")) {
      buf.append(" id=\"" + this.id + "\"");
    }
    buf.append(" message=\"\">");
    if(nameStr!=null && !nameStr.trim().equals("")) {
      int nameStartIndex=buf.indexOf("name=\"");
      if(nameStartIndex>=0) {
        int nameEndIndex=buf.substring(nameStartIndex+6).indexOf("\"")+nameStartIndex+6;
        String newStr=buf.substring(0,nameStartIndex+6)+nameStr+buf.substring(nameEndIndex);
        buf=new StringBuffer(newStr);
      }

      if(actionUrl!=null && !actionUrl.trim().equals("")) {
        int actionStartIndex=buf.indexOf("action=\"");
        if (actionStartIndex >= 0) {
          int actionEndIndex = buf.substring(actionStartIndex + 8).indexOf("\"") + actionStartIndex + 8;
          String newStr = buf.substring(0, actionStartIndex + 8) + actionUrl + buf.substring(actionEndIndex);
          buf = new StringBuffer(newStr);
        }
      }
    }
    //log.info("renderFormStartElement()=["+renderFormStartElement()+"]");
    buf.append(renderToken());
    //log.info("renderToken()=["+renderToken()+"]");
    ResponseUtils.write(pageContext, buf.toString());
    pageContext.setAttribute("server.framework.taglib.html.FORM", this, 2);
    try {
      initFormBean();
    } catch (Exception ex) {
      //ex.printStackTrace();
    }
    ResponseUtils.write(pageContext, renderHidden());
    return 1;
  }

  public int doEndTag() throws JspException {
    pageContext.removeAttribute("org.apache.struts.taglib.html.BEAN", 2);
    pageContext.removeAttribute("server.framework.taglib.html.FORM", 2);
    StringBuffer sb = new StringBuffer();
    sb.append("</form>");
    sb.append(renderRequiredPropertiesJavaScript());
    ResponseUtils.write(pageContext, sb.toString());
    return 6;
  }

  public void release() {
    super.release();
    initialForcus = null;
  }

  private String renderHidden() throws JspException {
    StringBuffer buf = new StringBuffer(Constants.LINE_END);
    appendForcusNameHiddenTag(buf);
    appendListStartIndexHidden(buf);
    appendSubSessionHiddenTag(buf);
    appendChangeFlagHidden(buf);
    buf.append(buildHiddenTag("cancel_flag", ""));
    buf.append(buildHiddenTag("forward_path", ""));
    buf.append(buildHiddenTag("querystring", ""));
    appendBackPathHiddenTag(buf);
    appendConfig(buf);
    return buf.toString();
  }

  private void appendForcusNameHiddenTag(StringBuffer buf) throws JspException {
    String propName = "focus_name";
    String value = extractPropertyValue("focus_name");
    if ( (value == null || value.length() == 0) && initialForcus != null) {
      value = initialForcus;
    }
    buf.append(buildHiddenTag("focus_name", value));
  }

  private void appendListStartIndexHidden(StringBuffer buf) throws JspException {
    String propName = "list_start_index";
    String value = extractPropertyValue("list_start_index");
    value = ResponseUtils.filter(value);
    buf.append(buildHiddenTag("list_start_index", value));
  }

  private void appendChangeFlagHidden(StringBuffer buf) throws JspException {
    String propName = "change_flag";
    String value = extractPropertyValue("change_flag");
    value = ResponseUtils.filter(value);
    buf.append(buildHiddenTag("change_flag", value));
  }

  private void appendBackPathHiddenTag(StringBuffer buf) throws JspException {
    String propName = "back_path";
    HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
    String value = (String) req.getAttribute("BACK_PATH_KEY");
    if (value == null) {
      log.debug("back_path is not exist in request.");
      value = extractPropertyValue("back_path");
    }
    log.debug("back_path is:" + value);
    buf.append(buildHiddenTag("back_path", value));
  }

  private void appendSubSessionHiddenTag(StringBuffer buf) throws JspException {
    String propName = "subsessionid";
    HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
    String value = (String) req.getAttribute("SUBSESSION_KEY");
    if (value == null) {
      log.debug("subsessionID is not exist.");
      value = "";
    }
    buf.append(buildHiddenTag("subsessionid", value));
  }

  private void appendConfig(StringBuffer buf) throws JspException {
    HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
    String contextPath = request.getContextPath();
    String urlSuffix = (String) pageContext.findAttribute("org.apache.struts.action.SERVLET_MAPPING");
    if (urlSuffix != null) {
      int dotIndex = urlSuffix.lastIndexOf(".");
      if (dotIndex != -1) {
        urlSuffix = urlSuffix.substring(dotIndex);
      }
    }
    buf.append(buildHiddenTag("context_path", contextPath));
    buf.append(buildHiddenTag("url_suffix", urlSuffix));
    String value = Constants.TEXT_LENGTH_MODE;
    buf.append(buildHiddenTag("text_length_evaluation_mode", value));
  }

  private String extractPropertyValue(String propName) {
    Object o = pageContext.findAttribute("org.apache.struts.taglib.html.BEAN");
    if (o == null || ! (o instanceof ValidatorForm)) {
      log.debug("actionForm not detected");
      return "";
    }
    String ret = null;
    try {
      ret = BeanUtils.getProperty(o, propName);
    } catch (IllegalAccessException e) {
      throw new SystemException(e.getMessage(), e);
    } catch (InvocationTargetException e) {
      throw new SystemException(e.getMessage(), e);
    } catch (NoSuchMethodException e) {
      throw new SystemException(e.getMessage(), e);
    }
    if (ret == null) {
      ret = "";
    }
    return ret;
  }

  private StringBuffer buildHiddenTag(String tagName, String value) throws JspException {
    StringBuffer buf = new StringBuffer();
    buf.append("\t<input type=\"hidden\"");
    buf.append(" name=\"");
    buf.append(tagName);
    buf.append("\"");
    buf.append(" value=\"");
    buf.append(ResponseUtils.filter(value));
    buf.append("\"");
    buf.append("/>");
    buf.append(Constants.LINE_END);
    return buf;
  }

  private StringBuffer renderRequiredPropertiesJavaScript() {
    StringBuffer buf = new StringBuffer();
    buf.append(Constants.LINE_END);
    buf.append("<script type=\"text/javascript\"");
    buf.append(">");
    buf.append(Constants.LINE_END);
    buf.append("  <!--");
    buf.append(Constants.LINE_END);
    buf.append("function getRequiredProperties() {");
    buf.append(Constants.LINE_END);
    buf.append("var array = new Array();");
    buf.append(Constants.LINE_END);
    ArrayList array = (ArrayList) pageContext.getAttribute("REQ_PROPERTIES");
    if (array != null) {
      Iterator ite = array.iterator();
      for (int i = 0; ite.hasNext(); i++) {
        buf.append("array[");
        buf.append(i);
        buf.append("] = document.forms[\"");
        buf.append(beanName);
        buf.append("\"].");
        buf.append( (String) ite.next());
        buf.append(";");
        buf.append(Constants.LINE_END);
      }

    }
    buf.append("return array;");
    buf.append(Constants.LINE_END);
    buf.append("}");
    buf.append(Constants.LINE_END);
    buf.append("  // -->");
    buf.append(Constants.LINE_END);
    buf.append("</script>");
    buf.append(Constants.LINE_END);
    return buf;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name=name;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id=id;
  }
}
