package server.framework.taglib.html;

import javax.servlet.jsp.*;

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
public class FileTag extends AbstractBaseFieldTag
{
    protected String name;
    protected String imageSrc;
    protected String imageHref;
    protected String imageHrefClass;
    protected String imageId;
    protected String imageClass;
    protected String imageStyle;
    protected String imageWidth;
    protected String imageHeight;
    protected String imageTooltip;
    protected String imageOnclick;
    protected String imageOnMouseOver;
    protected String imageOnMouseOut;
    protected String imageOnMouseMove;
    protected String imageOnMouseUp;
    protected String imageOnMouseDown;

    public FileTag()
    {
        name = "";
        this.type="file";
        imageSrc = null;
        imageHref = null;
        imageHrefClass = null;
        imageWidth = null;
        imageHeight = null;
        imageTooltip = null;
        imageId = null;
        imageClass = null;
        imageStyle = null;
        imageOnclick = null;
        imageOnMouseOver = null;
        imageOnMouseOut = null;
        imageOnMouseMove = null;
        imageOnMouseUp = null;
        imageOnMouseDown = null;
    }

    public String getName()
    {
        return name;
    }

    public String getImageClass() {
        return imageClass;
    }

    public String getImageHeight() {
        return imageHeight;
    }

    public String getImageHref() {
        return imageHref;
    }

    public String getImageId() {
        return imageId;
    }

    public String getImageOnclick() {
        return imageOnclick;
    }

    public String getImageOnMouseDown() {
        return imageOnMouseDown;
    }

    public String getImageOnMouseMove() {
        return imageOnMouseMove;
    }

    public String getImageOnMouseOut() {
        return imageOnMouseOut;
    }

    public String getImageOnMouseOver() {
        return imageOnMouseOver;
    }

    public String getImageOnMouseUp() {
        return imageOnMouseUp;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public String getImageTooltip() {
        return imageTooltip;
    }

    public String getImageWidth() {
        return imageWidth;
    }

    public String getImageHrefClass() {
        return imageHrefClass;
    }

    public String getImageStyle() {
        return imageStyle;
    }

    public void setName(String string)
    {
        name = string;
    }

    public void setImageWidth(String imageWidth) {
        this.imageWidth = imageWidth;
    }

    public void setImageTooltip(String imageTooltip) {
        this.imageTooltip = imageTooltip;
    }

    public void setImageStyle(String imageStyle) {
        this.imageStyle = imageStyle;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public void setImageOnMouseUp(String imageOnMouseUp) {
        this.imageOnMouseUp = imageOnMouseUp;
    }

    public void setImageOnMouseOver(String imageOnMouseOver) {
        this.imageOnMouseOver = imageOnMouseOver;
    }

    public void setImageOnMouseOut(String imageOnMouseOut) {
        this.imageOnMouseOut = imageOnMouseOut;
    }

    public void setImageOnMouseMove(String imageOnMouseMove) {
        this.imageOnMouseMove = imageOnMouseMove;
    }

    public void setImageOnMouseDown(String imageOnMouseDown) {
        this.imageOnMouseDown = imageOnMouseDown;
    }

    public void setImageOnclick(String imageOnclick) {
        this.imageOnclick = imageOnclick;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public void setImageHref(String imageHref) {
        this.imageHref = imageHref;
    }

    public void setImageHeight(String imageHeight) {
        this.imageHeight = imageHeight;
    }

    public void setImageClass(String imageClass) {
        this.imageClass = imageClass;
    }

    public void setImageHrefClass(String imageHrefClass) {
        this.imageHrefClass = imageHrefClass;
    }

    public int doStartTag()
        throws JspException
    {
        property = name;
        super.doStartTag();
        type = "file";
        doWrite();
        return 0;
    }

    private void prepareEventScript()
    {
        if(getOnblur() == null) {
            if(Global.isIE) {
                setOnblur("doBlur();");
            } else {
                setOnblur("doBlur(this);");
            }
        }
        if(getOnfocus() == null) {
            if(Global.isIE) {
                setOnfocus("doFocus();");
            } else {
                setOnfocus("doFocus(this);");
            }
        }

    }

    protected void doWrite()
        throws JspException
    {
        if (!TagUtils.isEmpty(imageSrc)) {
            StringBuffer preBuf = new StringBuffer();
            preBuf.append(
                    "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"><tr><td>");
            ResponseUtils.write(pageContext,preBuf.toString());
        }

        prepareEventScript();
        ResponseUtils.write(pageContext, getTagString());


        if (!TagUtils.isEmpty(imageSrc)) {
            if (imageStyle == null || imageStyle.equals("")) {
                imageStyle = "CURSOR:hand";
            }
            StringBuffer buf = new StringBuffer("</td><td");
            TagUtils.appendAttribute(buf, "width", imageWidth);
            if(!TagUtils.isEmpty(imageHref)) {
                buf.append("><A href=\""+imageHref+"\"");
                TagUtils.appendAttribute(buf, "class", imageHrefClass);
                buf.append(" target=\"_blank\">");
            } else {
                buf.append(">");
            }
            buf.append("<IMG");
            TagUtils.appendAttribute(buf, "src", imageSrc);
            TagUtils.appendAttribute(buf, "alt", imageTooltip);
            TagUtils.appendAttribute(buf, "width", imageWidth);
            TagUtils.appendAttribute(buf, "height", imageHeight);
            TagUtils.appendAttribute(buf, "id", imageId);
            TagUtils.appendAttribute(buf, "class", imageClass);
            TagUtils.appendAttribute(buf, "style", imageStyle);
            TagUtils.appendAttribute(buf, "onclick", imageOnclick);
            TagUtils.appendAttribute(buf, "onmouseover", imageOnMouseOver);
            TagUtils.appendAttribute(buf, "onmouseout", imageOnMouseOut);
            TagUtils.appendAttribute(buf, "onmouseup", imageOnMouseUp);
            TagUtils.appendAttribute(buf, "onmousedown", imageOnMouseDown);
            TagUtils.appendAttribute(buf, "onmousemove", imageOnMouseMove);
            buf.append("/>");
            if(!TagUtils.isEmpty(imageHref)) {
                buf.append("</A>");
            }
            buf.append("</td></tr></table>");
            ResponseUtils.write(pageContext, buf.toString());
        }

        release();
    }

    protected String getTagString()
        throws JspException
    {
        StringBuffer results = new StringBuffer("<input");
        prepareAttribute(results);
        results.append(TagUtils.getElementClose());
        return results.toString();
    }

    protected void prepareAttribute(StringBuffer sbuf)
        throws JspException
    {
        super.prepareAttribute(sbuf);
        TagUtils.appendControlerAttribute(sbuf, "defaultvalue", defaultvalue);
        prepareFocusEvents(sbuf);
        log.debug(sbuf.toString());
    }

    public void release()
    {
        super.release();
        this.type="file";

        imageSrc = null;
        imageHref = null;
        imageHrefClass = null;
        imageWidth = null;
        imageHeight = null;
        imageTooltip = null;
        imageId = null;
        imageClass = null;
        imageStyle = null;
        imageOnclick = null;
        imageOnMouseOver = null;
        imageOnMouseOut = null;
        imageOnMouseMove = null;
        imageOnMouseUp = null;
        imageOnMouseDown = null;
    }
}
