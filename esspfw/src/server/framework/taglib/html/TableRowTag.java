package server.framework.taglib.html;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import org.apache.struts.util.*;
import server.framework.taglib.util.*;

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
public class TableRowTag extends BodyTagSupport {
    private String styleId = "";
    private String onclick = "";
    private String ondblclick = "";
    private String height = "";
    private String selfProperty = "";
    private String oddClass="";  //奇数行颜色
    private String evenClass=""; //偶数行颜色
    private String canSelected="";

    public TableRowTag() {
        super();
    }

    public int doStartTag() throws JspException {
        TableBodyTag bodyTag=null;
        /**
         * add table row count
         */
        Tag tag = this.getParent();
        while (tag != null && !(tag instanceof TableBodyTag)) {
            tag = tag.getParent();
        }
        if (tag == null) {
            throw new JspException("ERROR: Can't find TableBodyTag tag");
        }
        bodyTag=(TableBodyTag)tag;
        bodyTag.rowCount++;


        StringBuffer buf = new StringBuffer();
        buf.append("<TR");
        String sStyleClass="";
        if(this.getStyleId()!=null && !this.getStyleId().trim().equals("")) {
            sStyleClass=this.getStyleId();
        }
        if(!TagUtils.isEmpty(oddClass) && !TagUtils.isEmpty(evenClass)) {
            if(bodyTag.rowCount%2==0) {
                sStyleClass=oddClass;
            } else {
                sStyleClass=evenClass;
            }
        }

        TagUtils.appendAttribute(buf, "class", sStyleClass);

        String sOnClick="";
        if(!TagUtils.isEmpty(canSelected) && canSelected.trim().equalsIgnoreCase("true")) {
            sOnClick="changeRowColor"+bodyTag.uuid+"(this);";
        }
        if(!TagUtils.isEmpty(this.getOnclick())) {
            sOnClick=sOnClick+this.getOnclick();
        }
        TagUtils.appendAttribute(buf, "onclick", sOnClick);

        String sId=this.getId();
        if(TagUtils.isEmpty(sId)) {
            sId="tr"+bodyTag.uuid+"_"+bodyTag.rowCount;
        }
        bodyTag.idList.add(sId);
        TagUtils.appendAttribute(buf, "id", sId);

        TagUtils.appendAttribute(buf, "ondblclick", this.getOndblclick());
        TagUtils.appendAttribute(buf, "height", this.getHeight());

        buf.append(" " + this.getSelfProperty() + " ");
        buf.append(">");
        ResponseUtils.write(pageContext, buf.toString());
        return 1;
    }

    public int doEndTag() throws JspException {
        StringBuffer buf = new StringBuffer();
        buf.append("</TR>");
        ResponseUtils.write(pageContext, buf.toString());
        release();
        return 1;
    }

//add by xh
    public void release() {
        super.release();
        styleId = "";
        onclick = "";
        ondblclick = "";
        selfProperty = "";
        canSelected="";
    }

    public String getStyleId() {
        return styleId;
    }

    public String getOnclick() {
        return onclick;
    }

    public String getOndblclick() {
        return ondblclick;
    }

    public String getHeight() {
        return height;
    }

    public String getSelfProperty() {
        return selfProperty;
    }

    public String getEvenClass() {
        return evenClass;
    }

    public String getOddClass() {
        return oddClass;
    }

    public String getCanSelected() {
        return canSelected;
    }

    public void setStyleId(String styleId) {
        this.styleId = styleId;
    }

    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

    public void setOndblclick(String ondblclick) {
        this.ondblclick = ondblclick;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setSelfProperty(String selfProperty) {
        this.selfProperty = selfProperty;
    }

    public void setEvenClass(String evenClass) {
        this.evenClass = evenClass;
    }

    public void setOddClass(String oddClass) {
        this.oddClass = oddClass;
    }

    public void setCanSelected(String canSelected) {
        this.canSelected = canSelected;
    }
}
