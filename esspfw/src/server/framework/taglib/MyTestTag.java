package server.framework.taglib;

import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.JspException;
import java.io.IOException;

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
public class MyTestTag extends TagSupport {
//  private Object propertyValue = null;
//  public void setProperty(String property) throws JspException {
//    WithCollectionTag parent = (WithCollectionTag) getParent();
//    if (parent == null) {
//      throw new JspException("parent tag is null");
//    }
//    try {
//      //�ж��ϲ�tag���Ƿ���ڸ���������,�������,ȡ������ֵ,���򱨴�
//      propertyValue = PropertyUtils.getProperty(parent.getElement(), property);
//    } catch (Exception e) {
//      throw new JspException(e);
//    }
//  }

  public int doEndTag() throws JspException {
    try {
      //�򵥵İ�ֵ��ӡ��jspҳ��
      pageContext.getOut().print("This is mytest!");
    } catch (IOException e) {
      throw new JspException(e);
    }
    return EVAL_PAGE;
  }

}
