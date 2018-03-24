package server.framework.taglib.html;


public abstract class AbstractFieldType
    implements IFieldType
{

    public AbstractFieldType()
    {
    }

    public abstract String getMaxLength(String s);

    public abstract String getStyleClass(String s, String s1, boolean flag, boolean flag1);

    public abstract String doFormat(String s, String s1, String s2);

    protected void renderCommons(StringBuffer sbuf, String req, String sreq, boolean readonly, boolean errorFlg)
    {
        if(req != null && "true".equals(req))
            sbuf.append(" Req");
        else
        if(sreq != null && "true".equals(sreq))
            sbuf.append(" Sreq");
        else
            sbuf.append(" Nreq");
        //输入框只读且不是必填时,才显示灰底色,mod by XR 2006/11/1    
        //if(readonly)
        if(	readonly && 
        	(req == null || (req != null && !"true".equals(req)) )
          )
            sbuf.append(" Display");
        if(errorFlg)
            sbuf.append(" Err");
    }
}
