package server.framework.taglib.script;

import javax.servlet.jsp.JspException;
import server.framework.taglib.util.Constants;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.RequestUtils;

public class FocusControlTag extends AbstractBaseScriptTag
{

    private final Logger log;
    private static final String FUNCTION_NAME = "doNextFocus()";

    public FocusControlTag()
    {
      log=Logger.getLogger(this.getClass());
    }

    public int doStartTag()
        throws JspException
    {
        ActionErrors errors = searchErrors();
        StringBuffer results = new StringBuffer();
        if(errors.isEmpty())
            doWrite("doNextFocus()", getScriptCode());
        else
            doWrite("doNextFocus()", getScriptCodeErrorCase());
        return 1;
    }

    private ActionErrors searchErrors()
        throws JspException
    {
        ActionErrors errors = null;
        try
        {
            errors = RequestUtils.getActionErrors(pageContext, "org.apache.struts.action.ERROR");
        }
        catch(JspException e)
        {
            RequestUtils.saveException(pageContext, e);
            log.error(e.getStackTrace());
            throw new JspException(AbstractBaseScriptTag.messages.getMessage("taglib.error", "FocusControlTag"), e);
        }
        return errors;
    }

    private StringBuffer getScriptCode()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("var doc = window.document;");
        sb.append(Constants.LINE_END);
        sb.append("var target;");
        sb.append(Constants.LINE_END);
        sb.append("var targetObj;");
        sb.append(Constants.LINE_END);
        sb.append("var lineno;");
        sb.append(Constants.LINE_END);
        sb.append("var nextid;");
        sb.append(Constants.LINE_END);
        sb.append("var nextObj;");
        sb.append(Constants.LINE_END);
        sb.append("target = String(doc.forms[0].focus_name.value);");
        sb.append(Constants.LINE_END);
        sb.append("if((target!=null)&&(target!=\"\")){");
        sb.append(Constants.LINE_END);
        sb.append("nextid = target.split(\"+\");");
        sb.append(Constants.LINE_END);
        sb.append("targetObj=doc.all(nextid[0]);");
        sb.append(Constants.LINE_END);
        sb.append("if(targetObj.type!=\"select-one\" && targetObj.length) {");
        sb.append(Constants.LINE_END);
        sb.append("if(nextid[1]==null) {");
        sb.append(Constants.LINE_END);
        sb.append("nextObj=targetObj[0];");
        sb.append(Constants.LINE_END);
        sb.append("}else {");
        sb.append(Constants.LINE_END);
        sb.append("nextObj=targetObj[Number(nextid[1])];");
        sb.append(Constants.LINE_END);
        sb.append("}");
        sb.append(Constants.LINE_END);
        sb.append("}else{");
        sb.append(Constants.LINE_END);
        sb.append("nextObj = doc.forms[0].elements[target];");
        sb.append(Constants.LINE_END);
        sb.append("}");
        sb.append(Constants.LINE_END);
        sb.append("nextObj.focus();");
        sb.append(Constants.LINE_END);
        sb.append("}");
        sb.append(Constants.LINE_END);
        return sb;
    }

    private StringBuffer getScriptCodeErrorCase()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("var doc = window.document.forms[0];");
        sb.append(Constants.LINE_END);
        sb.append("var objs;");
        sb.append(Constants.LINE_END);
        sb.append("var target;");
        sb.append(Constants.LINE_END);
        sb.append("var i,ii;");
        sb.append(Constants.LINE_END);
        sb.append("objs = doc.elements;");
        sb.append(Constants.LINE_END);
        sb.append("ii=objs.length;");
        sb.append(Constants.LINE_END);
        sb.append("for (i=0;i<ii;i++) {\t");
        sb.append(Constants.LINE_END);
        sb.append("target = objs[i];");
        sb.append(Constants.LINE_END);
        sb.append("if(target.msg!=null){");
        sb.append(Constants.LINE_END);
        sb.append("if(target.msg!= \"\"){");
        sb.append(Constants.LINE_END);
        sb.append("target.focus();");
        sb.append(Constants.LINE_END);
        sb.append("return;");
        sb.append(Constants.LINE_END);
        sb.append("}");
        sb.append(Constants.LINE_END);
        sb.append("}");
        sb.append(Constants.LINE_END);
        sb.append("}");
        sb.append(Constants.LINE_END);
        return sb;
    }
}
