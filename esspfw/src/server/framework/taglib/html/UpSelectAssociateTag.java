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
 * @author not attributable
 * @version 1.0
 */
public class UpSelectAssociateTag extends TagSupport {
//        private PageContext pageContext;
//        private Tag parentTag;
    private String id;
    private String type;
    private String property;
    private String onChange;
    private String mydefault;
    private String up;
    private String orgIds;
    private String style;
    private String styleId;
    private String disabled;
    private String onBlur;
    private String onFocus;
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

    public void setId(String id) {
        this.id = id;
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

    public void setUp(String s) {
        this.up = s;
    }

    public void setOrgIds(String orgIds) {
        this.orgIds = orgIds;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    public String getUp() {
        return this.up;
    }

    public String getOrgIds() {
        return orgIds;
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
            values.setOrgIds(this.getOrgIds());
            values.initData();

            SelectContentInterface dd = (SelectContentInterface) values.getDefualt();

            //从参数值中取值来显示默认
            String defaultValue = pageContext.getRequest().getParameter(
                    getProperty());

            //如果没有参数值则默认选中在标签中指定的默认值
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
            out.print("<option value=\"");
            out.print(dd.getValue());
            out.print("\"");
            out.print(">");
            out.print(dd.getShow());
            out.print("</option>");
            out.print("</select>");
//生成如下样式：
//<select id="class" name="class_code">　　　　说明：id和name均为标签中设定的参数
//<option value="-1">---</option>
//</select>

            out.println("<script language=\"javascript\">");
            out.print("var  " + getUp() + "Array"+getId()+"= new Array();");
            out.print(getUp() + "Array"+getId()+"['" + dd.getUp() +
                      "']=new Array(new Array(\"" + dd.getValue() + "\",\"" +
                      dd.getShow() + "\"));");
//生成如下样式：初值
//            <script language="javascript">
//            var  study_codeArray= new Array();
//            study_codeArray['-1']=new Array(new Array("-1","--Please Select--"));

            String tmp = "";
            for (int i = 0; i < values.size(); i++) {
                SelectContentInterface d = (SelectContentInterface) values.elementAt(i);
                if (!"".equals(tmp) && !tmp.equals(d.getUp())) {
                    out.println(");");
                }
                if (!tmp.equals(d.getUp())) {
                    out.print(getUp() + "Array"+getId()+"['" + d.getUp() +
                              "']=new Array(new Array(\"" + dd.getValue() +
                              "\",\"" + dd.getShow() + "\")");
                }
                out.print(",new Array(\"" + d.getValue() + "\",\"" +
                          d.getShow() + "\")");
                tmp = d.getUp();
            }
            out.println(");");
            out.print( //"document.all." + getUp() + ".onchange=
                    "function " + getUp() +
                    "SelectChangeEvent"+getId()+"(selected){");
            out.print("src=document.all." + getUp() + ";");
            out.print("des=document.all." + getProperty() + ";");
            out.print("_array=" + getUp() + "Array"+getId()+";");
            out.print("includeall=false;");
            out.print("");
            out.print("if(!src.value) return;");
            out.print("position = _array[src.value];");
            out.print("if(position==null) position = _array['" + dd.getValue() +
                      "'];");
            out.print("if(!position || !des) return;");
            out.print("oOs = des.options;");
            out.print("while(oOs.length>0){");
            out.print("oOs.remove(0);}");
            out.print("var i = 0;if(!includeall && position.length>1) i=1;");
            out.print("for(;i<position.length;i++){");
            out.print("var oOption = document.createElement(\"OPTION\");");
            out.print("oOption.text=position[i][1];");
            out.print("oOption.value=position[i][0];");
            out.print(
                    "oOs.add(oOption);if(selected) if(position[i][0]==selected) ");
            out.print("oOption.selected=true;");
            out.print("}");
            out.print("try{des.onchange();}catch(error){}");
            out.print("}");
            out.print(getUp() + "SelectChangeEvent"+getId()+"('" + defaultValue + "');");
            out.print("</script>");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
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
        this.up = null;
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

    public String getStyleId() {
        return styleId;
    }

    public void setStyleId(String styleId) {
        this.styleId = styleId;
    }


}
