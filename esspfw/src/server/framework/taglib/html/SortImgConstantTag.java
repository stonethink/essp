package server.framework.taglib.html;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import org.apache.struts.util.ResponseUtils;

public class SortImgConstantTag extends BodyTagSupport {
    public final static String SORTIMG_UP = "SORTIMG_UP";
    public final static String SORTIMG_DOWN = "SORTIMG_DOWN";
    public final static String SORTIMG_CURRENT_UP = "SORTIMG_CURRENT_UP";
    public final static String SORTIMG_CURRENT_DOWN = "SORTIMG_CURRENT_DOWN";
    public final static String SORT_FORM="SORT_FORM";

    private String up;
    private String down;
    private String currentup;
    private String currentdown;
    private String form;

    public SortImgConstantTag() {
        super();
        release();
    }

    public String getCurrentdown() {
        return currentdown;
    }

    public String getDown() {
        return down;
    }

    public String getCurrentup() {
        return currentup;
    }

    public String getUp() {
        return up;
    }

    public String getForm() {
        return form;
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

    public void setForm(String form) {
        this.form = form;
    }

    private boolean isValid(String value) {
        if (value != null && !value.trim().equals("")) {
            return true;
        }
        return false;
    }

    public int doStartTag() throws JspException {
        if (isValid(up)) {
            pageContext.setAttribute(SORTIMG_UP, up);
        }
        if (isValid(down)) {
            pageContext.setAttribute(SORTIMG_DOWN, down);
        }
        if (isValid(currentup)) {
            pageContext.setAttribute(SORTIMG_CURRENT_UP, currentup);
        }
        if (isValid(currentdown)) {
            pageContext.setAttribute(SORTIMG_CURRENT_DOWN, currentdown);
        }
        if (isValid(form)) {
            pageContext.setAttribute(SORT_FORM, form);
        }

        rendScript();

        return 1;
    }

    public int doEndTag() throws JspException {
        return 1;
    }

    private void rendScript() throws JspException {
        StringBuffer buffer=new StringBuffer("");
        buffer.append("function onSort(imgInstance) {\n");
        buffer.append("	try {\n");
        buffer.append("		var curSrc=\"\";\n");
        buffer.append("		if(imgInstance.sortKind==\"up\" && imgInstance.isCurrentSorted==\"false\") {\n");
        buffer.append("			curSrc=\""+up+"\";\n");
        buffer.append("			document."+form+".sortKind.value=\"up\";\n");
        buffer.append("		} else if(imgInstance.sortKind==\"up\" && imgInstance.isCurrentSorted==\"true\") {\n");
        buffer.append("			curSrc=\""+currentdown+"\";\n");
        buffer.append("			document."+form+".sortKind.value=\"down\";\n");
        buffer.append("		} else if(imgInstance.sortKind==\"down\" && imgInstance.isCurrentSorted==\"true\") {\n");
        buffer.append("			curSrc=\""+currentup+"\";\n");
        buffer.append("			document."+form+".sortKind.value=\"up\";\n");
        buffer.append("		} else if(imgInstance.sortKind==\"down\" && imgInstance.isCurrentSorted==\"false\") {\n");
        buffer.append("			curSrc=\""+down+"\";\n");
        buffer.append("			document."+form+".sortKind.value=\"down\";\n");
        buffer.append("		}\n");
//        buffer.append("		var imgObjList=document.getElementsByTagName(\"IMG\");\n");
//        buffer.append("		var i=0;\n");
//        buffer.append("		if(imgObjList!=null) {\n");
//        buffer.append("			for(i=0;i<imgObjList.length;i++) {\n");
//        buffer.append("				var imgObj=imgObjList[i];\n");
//        buffer.append("				if(imgObj.sortItem!=null) {\n");
//        buffer.append("					if(imgObj.sortKind==\"up\" && imgObj.isCurrentSorted==\"true\") {\n");
//        buffer.append("						imgObj.src=\""+up+"\";\n");
//        buffer.append("						imgObj.isCurrentSorted=\"false\";\n");
//        buffer.append("						window.eval('"+form+".'+imgObj.sortItem+'_isCurrentSorted.value=\"false\"');\n");
//        buffer.append("						window.eval('"+form+".'+imgObj.sortItem+'_sortKind.value=\"up\"');\n");
//        buffer.append("				        }\n");
//        buffer.append("					if(imgObj.sortKind==\"down\" && imgObj.isCurrentSorted==\"true\") {\n");
//        buffer.append("						imgObj.src=\""+down+"\";\n");
//        buffer.append("						imgObj.isCurrentSorted=\"false\";\n");
//        buffer.append("						window.eval('"+form+".'+imgObj.sortItem+'_isCurrentSorted.value=\"false\"');\n");
//        buffer.append("						window.eval('"+form+".'+imgObj.sortItem+'_sortKind.value=\"down\"');\n");
//        buffer.append("				        }\n");
//        buffer.append("				 }\n");
//        buffer.append("			}\n");
//        buffer.append("		}\n");
        buffer.append("		imgInstance.src=curSrc;\n");
        buffer.append("		imgInstance.sortKind=document.sortForm.sortKind.value;\n");
        buffer.append("		imgInstance.isCurrentSorted=\"true\";\n");
//        buffer.append("		window.eval('"+form+".'+imgInstance.sortItem+'_isCurrentSorted.value=\"true\"');\n");
//        buffer.append("		window.eval('"+form+".'+imgInstance.sortItem+'_sortKind.value=\"'+imgInstance.sortKind+'\"');\n");
        buffer.append("		document."+form+".sortItem.value=imgInstance.sortItem;\n");
        buffer.append("		document."+form+".submit();\n");
        buffer.append("	} catch(e) {\n");
        buffer.append("		//alert(e);\n");
        buffer.append("	}\n");
        buffer.append("}\n");
        ResponseUtils.write(pageContext,buffer.toString());
    }

    public void release() {
        up = null;
        down = null;
        currentup = null;
        currentdown = null;
    }

}
