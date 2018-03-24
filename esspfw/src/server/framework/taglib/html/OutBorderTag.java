package server.framework.taglib.html;

import javax.servlet.jsp.JspException;
import org.apache.struts.util.ResponseUtils;

public class OutBorderTag extends AbstractBaseHandlerTag {
    private String width;
    private String height;
    private String outLine="yes";

    public int doStartTag() throws JspException {
        StringBuffer results = new StringBuffer("");

        results.append( " <table  valign=\"top\" align=\"center\" " );
        results.append( " width=\"" + this.width + "\"");
        results.append( " height=\"" + this.height + "\"");
        if(outLine.equalsIgnoreCase("no")){
           results.append( " border=\"0\" cellpadding=\"0\" cellspacing=\"0\"> ");
           results.append( " <tr><td width=\"100%\" class=\"\"> ");
        }else{
        	results.append( " border=\"0\" cellpadding=\"1\" cellspacing=\"0\"> ");
            results.append( " <tr><td width=\"100%\" class=\"wind\"> ");
        }

        results.append( " <table width=\"100%\" height=\"100%\" align=\"center\" bgcolor=\"white\"  cellSpacing=0 cellPadding=0> ");
        results.append( " <tr> ");
        results.append( " <td width=\"100%\" align=\"left\" valign=\"top\"> ");

        ResponseUtils.write(pageContext, results.toString());
        return 1;
    }

    public int doEndTag() throws JspException {
        StringBuffer results = new StringBuffer("");

        results.append( " </td></tr></table> " );
        results.append( " </td></tr></table> " );

        ResponseUtils.write(pageContext, results.toString());

        return 1;
    }

    public void setWidth( String width){
        this.width = width;
    }

    public String getWidth(){
        return this.width;
    }

    public void setHeight( String height){
        this.height = height;
    }

    public String getHeight(){
        return this.height;
    }
    public void setOutLine(String outLine){
    	this.outLine=outLine;
    }
    public String getOutLine(){
    	return this.outLine;
    }

}
