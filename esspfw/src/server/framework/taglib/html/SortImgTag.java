package server.framework.taglib.html;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import org.apache.struts.util.*;
import server.framework.taglib.util.*;

public class SortImgTag extends BodyTagSupport {
    private String up;
    private String down;
    private String currentup;
    private String currentdown;
    private String beanName;
    private String sortItem;
    private String isCurrentSorted;
    private String sortKind;
    private String styleClass;
    private String style;
    private String onclick="onSort(this);";
    private String scope;
    private String src;
    private String width;
    private String height;
    private String id;

    public SortImgTag() {
        super();
        release();
    }

    public String getCurrentdown() {
        return currentdown;
    }

    public String getCurrentup() {
        return currentup;
    }

    public String getDown() {
        return down;
    }

    public String getUp() {
        return up;
    }

    public String getBeanName() {
        return beanName;
    }

    public String getOnclick() {
        return onclick;
    }

    public String getSortItem() {
        return sortItem;
    }

    public String getSortKind() {
        return sortKind;
    }

    public String getStyleClass() {
        return styleClass;
    }

    public String getScope() {
        return scope;
    }

    public String getStyle() {
        return style;
    }

    public String getWidth() {
        return width;
    }

    public String getHeight() {
        return height;
    }

    public void setCurrentdown(String currentdown) {
        this.currentdown = currentdown;
    }

    public void setCurrentup(String currentup) {
        this.currentup = currentup;
    }

    public void setDown(String down) {
        this.down = down;
    }

    public void setUp(String up) {
        this.up = up;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

    public void setSortItem(String sortItem) {
        this.sortItem = sortItem;
    }

    public void setSortKind(String sortKind) {
        this.sortKind = sortKind;
    }

    public void setStyleClass(String style) {
        this.styleClass = style;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id=id;
    }

    private boolean isValid(String value) {
        if(value!=null && !value.trim().equals("")) {
            return true;
        }
        return false;
    }

    public int doStartTag() throws JspException {
        if(!isValid(up)) {
            up=(String)pageContext.getAttribute(SortImgConstantTag.SORTIMG_UP);
        }
        if(!isValid(down)) {
            down=(String)pageContext.getAttribute(SortImgConstantTag.SORTIMG_DOWN);
        }
        if(!isValid(currentup)) {
            currentup=(String)pageContext.getAttribute(SortImgConstantTag.SORTIMG_CURRENT_UP);
        }
        if(!isValid(currentdown)) {
            currentdown=(String)pageContext.getAttribute(SortImgConstantTag.SORTIMG_CURRENT_DOWN);
        }


        if(!isValid(up)) {
            throw new JspException("up property not seted!!!");
        }
        if(!isValid(down)) {
            throw new JspException("down property not seted!!!");
        }
        if(!isValid(currentup)) {
            throw new JspException("currentup property not seted!!!");
        }
        if(!isValid(currentdown)) {
            throw new JspException("currentdown property not seted!!!");
        }



        if(!isValid(sortItem)) {
            throw new JspException("sortItem property not seted!!!");
        }

        Boolean isCurrentSorted_b=(Boolean)getProperty(sortItem+"_isCurrentSorted");
        if(isCurrentSorted_b==null) {
            isCurrentSorted="false";
        } else {
            isCurrentSorted=isCurrentSorted_b.booleanValue()+"";
        }

        String sortKind_s=(String)getProperty(sortItem+"_sortKind");
        if(sortKind_s==null) {
            sortKind="down";
        } else {
            sortKind=sortKind_s;
        }

        if(sortKind.equals("down")) {
            src=down;
        } else if(sortKind.equals("up")) {
            src=up;
        } else if(sortKind.equals("currentup")) {
            src=currentup;
        } else if(sortKind.equals("currentdown")) {
            src=currentdown;
        }





        StringBuffer buf = new StringBuffer("<img ");
        TagUtils.appendAttribute(buf,"src",src);
        TagUtils.appendAttribute(buf,"sortItem",sortItem);
        TagUtils.appendAttribute(buf,"isCurrentSorted",isCurrentSorted);
        TagUtils.appendAttribute(buf,"sortKind",sortKind);
        TagUtils.appendAttribute(buf,"onclick",onclick);
        TagUtils.appendAttribute(buf,"class",styleClass);
        TagUtils.appendAttribute(buf,"style",style);

        ResponseUtils.write(pageContext,buf.toString());


        return 1;
    }

    private Object getProperty(String property) {
        try {
            if(scope!=null && scope.trim().equals("")) {
                scope=null;
            }
            if (beanName != null && !beanName.trim().equals("")) {
                if (RequestUtils.lookup(pageContext, beanName, scope) != null) {
                    Object value = RequestUtils.lookup(pageContext, beanName,
                            property, scope);
                    return value;
                }
            }
        } catch (Exception ex) {
        }
        return null;
    }

    public int doEndTag() throws JspException {
        ResponseUtils.write(pageContext,">");
        release();
        return 1;
    }

    public void release() {
        up=null;
        down=null;
        currentup=null;
        currentdown=null;
        beanName=null;
        sortItem=null;
        isCurrentSorted=null;
        sortKind=null;
        styleClass=null;
        style="CURSOR:hand";
        onclick="onSort(this);";
        src=null;
        scope=null;
        width=null;
        height=null;
        id=null;
    }
}
