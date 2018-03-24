/*
 *
 * CreatedDate: 2004/07/05
 * $Revision: 1.2 $ <br>
 * $Date: 2005/10/13 07:09:22 $
 */
package junitpack;

import org.apache.commons.digester.Digester;

import org.xml.sax.SAXException;

import java.io.IOException;

import java.lang.reflect.*;

import java.util.ArrayList;
import java.util.HashMap;
/**
 *
 *<br>
 * @author yerj
 *
 * @version
 * $Revision: 1.2 $ <br>
 * $Date: 2005/10/13 07:09:22 $
 */
public class InputDataUtil extends Digester{
    ArrayList properties = new ArrayList();
    HashMap propertyTypeSet=new HashMap();
    String className = "";

    /**
     *
     */
    public InputDataUtil() {
        super();
    }

    public void setClassName(String newClassName) throws Exception {
        Class curClass = Class.forName(newClassName);
        this.className = newClassName;
        properties.clear();

        Method[] allMethod = curClass.getMethods();

        for (int i = 0; (allMethod != null) && (i < allMethod.length); i++) {
            Method method = allMethod[i];
            String methodName = method.getName();

            if (methodName.startsWith("set") == true) {
                String propertyName = methodName.substring(3);
                properties.add(propertyName);
				propertyTypeSet.put(propertyName,method.getParameterTypes());
            }
        }
    }
    
    public void setPropertyType(String propertyName,String propertyType) {
		propertyTypeSet.put(propertyName,propertyType);
    }

    public Object parse(String xml) throws IOException, SAXException {
        if (properties.size() == 0) {
            throw new IOException("Error : you must invoke setClassName first");
        }
        
		//String pattern = "yyyyMMdd";
		//Locale locale = Locale.getDefault();
		//DateLocaleConverter converter = new DateLocaleConverter(locale, pattern);
		//converter.setLenient(true);
		//ConvertUtils.register(converter, java.util.Date.class);


        addObjectCreate("inputs", "java.util.Vector");
        addObjectCreate("inputs/input", className);

        for (int i = 0; i < properties.size(); i++) {
            String propertyName = (String) properties.get(i);
            Class[] parameterTypes=(Class[])propertyTypeSet.get(propertyName);
			addCallMethod("inputs/input/" + propertyName, "set" + propertyName,1,parameterTypes);
            
            addCallParam("inputs/input/" + propertyName, 0);
        }

        addSetNext("inputs/input", "add");

        return super.parse(xml);
    }

    public static void main(String[] args) {
    }
}
