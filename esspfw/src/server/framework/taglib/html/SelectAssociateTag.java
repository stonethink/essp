package server.framework.taglib.html;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.wits.util.StringUtil;

import server.framework.taglib.util.SelectContentBase;
import server.framework.taglib.util.SelectContentInterface;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author Robin.Zhnag
 * @version 1.0
 */
public class SelectAssociateTag extends TagSupport {
//        private PageContext pageContext;
//        private Tag parentTag;
    private String id;
    private String type;
    private String property;
    private String onChange;
    private String onBlur;
    private String onFocus;
    private String mydefault;
    private String includeall;
    private String orgIds;
    private String styleId;
    private String style;
    private String disabled;
    private String onmouseover;
    private String onmouseout;
    private String req;


    public String getReq() {
        return req;
    }

    public void setReq(String req) {
        this.req = req;
    }

    public String getOnmouseout() {
        return onmouseout;
    }

    public void setOnmouseout(String onmouseout) {
        this.onmouseout = onmouseout;
    }

    public String getOnmouseover() {
        return onmouseover;
    }

    public void setOnmouseover(String onmouseover) {
        this.onmouseover = onmouseover;
    }

    public void setId(String s) {
        this.id = s;
    }

    public String getId() {
        return this.id;
    }

    public void setType(String s) {
        this.type = s;
    }

    public String getType() {
        return this.type;
    }

    public void setProperty(String s) {
        this.property = s;
    }

    public String getProperty() {
        return this.property;
    }

    public void setOnchange(String s) {
        this.onChange = s;
    }

    public String getOnchange() {
        return this.onChange;
    }

    public void setDefault(String s) {
        this.mydefault = s;
    }

    public String getDefault() {
        if (mydefault == null) {
            return "";
        }
        return this.mydefault;
    }

    public void setIncludeall(String s) {
        this.includeall = s;
    }

    public void setOrgIds(String orgIds) {
        this.orgIds = orgIds;
    }

    public void setStyleId(String styleId) {
        this.styleId = styleId;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    public String getIncludeall() {
        return includeall;
    }

    public String getOrgIds() {
        return orgIds;
    }

    public String getStyleId() {
        return styleId;
    }

    public String getStyle() {
        return style;
    }

    public String getDisabled() {
        return disabled;
    }

    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            SelectContentBase values = (SelectContentBase) Class.forName(this.getType()).
                                       newInstance();
            values.setRequest(pageContext.getRequest());
            values.setSession(pageContext.getSession());
            values.setOrgIds(this.getOrgIds());
            values.initData();

            String defaultValue = pageContext.getRequest().getParameter(
                    getProperty());
            
//          如果没有参数值则默认选中在标签中指定的默认值
            if(defaultValue==null||defaultValue.equals("")){
                defaultValue=this.getDefault();
            }
            out.print("<select ");
            if (!"".equals(getId()) && getId() != null) {
                out.print("id=\"" + getId() + "\"");
            }
            out.print(" name=\"" + this.getProperty() + "\"");
            if (!"".equals(onChange) && onChange != null) {
                out.print(" onChange=\"" + onChange + "\"");
            }
            if(req != null && !"".equals(req)){
                out.print("req=\"" + getReq() + "\"");
            }
                out.print(" onBlur=\"doBlur();" + StringUtil.nvl(onBlur) + "\"");
                out.print(" onfocus=\"doFocus();" + StringUtil.nvl(onFocus) + "\"");
                out.print(" onmouseover=\"showTip(this,'select',event)" + StringUtil.nvl(onmouseover) + "\"");
                out.print(" onmouseout=\"CloseTip('select');" + StringUtil.nvl(onmouseout) + "\"");
            if(this.getStyleId()!=null&&!this.getStyleId().equals("")){
                out.print(" class=\""+this.getStyleId()+"\" ");
            }
            if(this.getStyle()!=null&&!this.getStyle().equals("")){
                out.print(" style=\""+this.getStyle()+"\" ");
            }
            if(this.getDisabled()!=null&&this.getDisabled().equals("true")){
                out.print(" disabled");
            }
            
            out.print(" >");

            if ("true".equals(this.getIncludeall())) {
                SelectContentInterface d = (SelectContentInterface) values.getDefualt();
                out.print("<option value=\"");
                out.print(d.getValue());
                out.print("\"");
                out.print(">");
                out.print(d.getShow());
                out.print("</option>");
            }
            for (int i = 0; i < values.size(); i++) {
                SelectContentInterface d = (SelectContentInterface) values.elementAt(i);
                out.print("<option value=\"");
                out.print(d.getValue());
                out.print("\"");
                if (d.getValue().equals(defaultValue)) {
                    out.print(" selected");
                }
                out.print(">");
                out.print(d.getShow());
                out.print("</option>");
            }
            out.print("</select>");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new JspException(e.getMessage());
        }
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }


    public void release() {
        this.id = null;
        this.type = null;
        this.property = null;
        this.onChange = null;
        this.mydefault = null;
        this.includeall = null;
        super.release();
    }

    public String getOnBlur() {
        return onBlur;
    }

    public void setOnBlur(String onBlur) {
        this.onBlur = onBlur;
    }

    public String getOnChange() {
        return onChange;
    }

    public void setOnChange(String onChange) {
        this.onChange = onChange;
    }

    public String getOnFocus() {
        return onFocus;
    }

    public void setOnFocus(String onFocus) {
        this.onFocus = onFocus;
    }


}
