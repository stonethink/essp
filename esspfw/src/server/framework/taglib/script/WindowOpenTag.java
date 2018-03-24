package server.framework.taglib.script;

import javax.servlet.jsp.JspException;
import server.framework.taglib.util.Constants;

public class WindowOpenTag extends AbstractBaseScriptTag
{

    private static final String FUNCTION_NAME = "doWindowOpen()";

    public WindowOpenTag()
    {
    }

    public int doStartTag()
        throws JspException
    {
        StringBuffer buf = new StringBuffer();
        Object url = pageContext.getAttribute("URL_KEY", 2);
        if(url != null)
        {
            buf.append("var date = new Date().getTime();");
            buf.append(Constants.LINE_END);
            buf.append("var wname = \"cocubesubwin\" + date");
            buf.append(Constants.LINE_END);
            buf.append("secondWin = window.open(\"\", wname);");
            buf.append(Constants.LINE_END);
            buf.append("window.opener=true;");
            buf.append(Constants.LINE_END);
            buf.append("secondWin.document.open();");
            buf.append(Constants.LINE_END);
            buf.append("secondWin.document.write('<HTML>');");
            buf.append(Constants.LINE_END);
            buf.append("secondWin.document.write('<HEAD>');");
            buf.append(Constants.LINE_END);
            buf.append("secondWin.document.write('<SCRIPT Language=\"JavaScript\">');");
            buf.append(Constants.LINE_END);
            buf.append("secondWin.document.write('function opnWind() {');");
            buf.append(Constants.LINE_END);
            buf.append("secondWin.document.write(' ");
            buf.append("document.location.href = \"" + url + "\";");
            buf.append("')");
            buf.append(Constants.LINE_END);
            buf.append("secondWin.document.write('window.focus();');");
            buf.append(Constants.LINE_END);
            buf.append("secondWin.document.write('}');");
            buf.append(Constants.LINE_END);
            buf.append("secondWin.document.write('</SCRIPT>');");
            buf.append(Constants.LINE_END);
            buf.append("secondWin.document.write('</HEAD>');");
            buf.append(Constants.LINE_END);
            buf.append("secondWin.document.write('<BODY onload=\"opnWind()\">');");
            buf.append(Constants.LINE_END);
            buf.append("secondWin.document.write('</BODY>');");
            buf.append(Constants.LINE_END);
            buf.append("secondWin.document.write('</HTML>');");
            buf.append(Constants.LINE_END);
            buf.append("secondWin.document.close();");
            buf.append(Constants.LINE_END);
            buf.append("secondWin.focus();");
            buf.append(Constants.LINE_END);
            buf.append("window.blur();");
            buf.append(Constants.LINE_END);
        }
        doWrite("doWindowOpen()", buf);
        return 0;
    }
}
