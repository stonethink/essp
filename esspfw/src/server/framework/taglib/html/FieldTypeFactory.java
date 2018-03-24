package server.framework.taglib.html;

import org.apache.log4j.Logger;
import org.apache.struts.util.MessageResources;
import server.framework.taglib.util.Constants;

public final class FieldTypeFactory
{

    private static Logger logger;

    private FieldTypeFactory()
    {
      logger=Logger.getLogger(FieldTypeFactory.class);
    }

    public static IFieldType doCreate(String fieldtype)
        throws FieldTypeException
    {
        IFieldType type = null;
        if("number".equals(fieldtype))
            type = new NumberFieldType();
        else
        if("money".equals(fieldtype))
        {
            logger.warn("fieldtype 'money' is deprecated !!");
            type = new CommaNumberFieldType();
        } else
        if("text".equals(fieldtype))
            type = new TextFieldType();
        else
        if("alpha".equals(fieldtype))
            type = new AlphaFieldType();
        else
        if("numbercode".equals(fieldtype))
            type = new NumberCodeFieldType();
        else
        if("index".equals(fieldtype))
            type = new IndexFieldType();
        else
        if("dateyymm".equals(fieldtype))
            type = new DateyymmFieldType();
        else
        if("dateyymmdd".equals(fieldtype))
            type = new DateyymmddFieldType();
        else
        if("dateyyyymmdd".equals(fieldtype))
            type = new DateyyyymmddFieldType();
        else
        if("dateyyyymm".equals(fieldtype))
            type = new DateyyyymmFieldType();
        else
        if("datemmdd".equals(fieldtype))
            type = new DatemmddFieldType();
        else
        if("timehhmmss".equals(fieldtype))
            type = new TimehhmmssFieldType();
        else
        if("timehhmm".equals(fieldtype))
            type = new TimehhmmFieldType();
        else
        if("capitalcode".equals(fieldtype))
            type = new CapitalCodeFieldType();
        else
        if("commanumber".equals(fieldtype))
        {
            type = new CommaNumberFieldType();
        } else
        {
            String message = MessageResources.getMessageResources(Constants.MESSAGE_CONFIG).getMessage("fieldtype.factory.error", fieldtype);
            throw new FieldTypeException(message);
        }
        return type;
    }
}
