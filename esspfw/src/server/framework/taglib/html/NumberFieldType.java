package server.framework.taglib.html;

import java.util.StringTokenizer;
import server.framework.taglib.util.FormatUtils;

public class NumberFieldType extends AbstractFieldType
{

    public NumberFieldType()
    {
    }

    public String getMaxLength(String fmt)
    {
        if(fmt != null || !"".equals(fmt))
        {
            int sei;
            int syou;
            if(fmt.indexOf(46) == -1)
            {
                sei = Integer.parseInt(fmt);
                syou = 0;
            } else
            {
                StringTokenizer st = new StringTokenizer(fmt, ".");
                String seisu = null;
                String syousu = null;
                while(st.hasMoreTokens())
                {
                    seisu = st.nextToken();
                    if(st.hasMoreTokens())
                        syousu = st.nextToken();
                }
                sei = Integer.parseInt(seisu);
                syou = Integer.parseInt(syousu);
            }
            if(syou == 0)
                return Integer.toString(sei + 1);
            else
                return Integer.toString(sei + syou + 2);
        } else
        {
            return null;
        }
    }

    public String getStyleClass(String req, String sreq, boolean readonly, boolean errorFlg)
    {
        StringBuffer sbuf = new StringBuffer(" Ntext");
        renderCommons(sbuf, req, sreq, readonly, errorFlg);
        return sbuf.toString();
    }

    public String doFormat(String value, String defaultvalue, String fmt)
    {
        String ret = null;
        if(value != null && value.length() != 0)
            ret = FormatUtils.formatNumber(value, fmt);
        else
        if("true".equals(defaultvalue))
            ret = FormatUtils.getDefaultNumberValue(fmt);
        else
            ret = null;
        return ret;
    }
}
