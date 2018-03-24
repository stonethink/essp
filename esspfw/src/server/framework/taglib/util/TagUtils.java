package server.framework.taglib.util;

import javax.servlet.jsp.PageContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import org.apache.log4j.Category;
import java.util.List;
import java.util.ArrayList;
import c2s.dto.DtoUtil;

public final class TagUtils {
    static org.apache.struts.taglib.TagUtils wrappedUtil = org.apache.struts.taglib.
            TagUtils.getInstance();
    static Category log = Category.getInstance("server");

    public TagUtils() {
    }

    public static void appendAttribute(StringBuffer sbuf, String name,
                                       String value) {
        if (value != null && !value.trim().equals("")) {
            sbuf.append(" ");
            sbuf.append(name);
            sbuf.append("=\"");
            sbuf.append(value);
            sbuf.append("\"");
        }
    }

    public static void appendControlerAttribute(StringBuffer sbuf, String name,
                                                String value) {
        sbuf.append(" ");
        sbuf.append(name);
        sbuf.append("=\"");
        if (value != null) {
            sbuf.append(value);
        }
        sbuf.append("\"");
    }

    public static String getElementClose() {
        return " />";
    }

    public static boolean isIE(PageContext context) {
        boolean result = true;
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        String user_agent = request.getHeader("user-agent");
        if (user_agent.indexOf("Firefox") >= 0) {
            result = false;
        } else if (user_agent.indexOf("Netscape") >= 0) {
            result = false;
            if (user_agent.indexOf("Gecko") >= 0) {
                result = false;
            } else if (user_agent.indexOf("MSIE") >= 0) {
                result = true;
            }
        }
        Global.isIE = result;
        return result;
    }

    public static String nvl(String value) {
        if (isEmpty(value)) {
            return "";
        }
        return value;
    }

    public static boolean isEmpty(String value) {
        if (value == null || value.trim().equals("")) {
            return true;
        }
        return false;
    }

    public static int getScope(String scopeName) throws JspException {
        return wrappedUtil.getScope(scopeName);
    }

    public static Object lookup(PageContext pageContext, String name, String scopeName) throws
            JspException {
        Object value = null;
        String strScope=scopeName;
        if(strScope!=null) {
            if(strScope.trim().equals("")) {
                strScope = null;
                log.warn("scope is \"\" when lookup bean \""+name+"\"");
            } else if(strScope.trim().equalsIgnoreCase("null")) {
                strScope = null;
                log.warn("scope is \"null\" when lookup bean \""+name+"\"");
            }
        }
        try {
            value = wrappedUtil.lookup(pageContext, name, strScope);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static Object lookup(
            PageContext pageContext,
            String name,
            String property,
            String scope) throws JspException {
        Object value = null;
        String strScope=scope;
        if(strScope!=null) {
            if(strScope.trim().equals("")) {
                strScope = null;
                log.warn("scope is \"\" when lookup property \""+property+"\" of bean \""+name+"\"");
            } else if(strScope.trim().equalsIgnoreCase("null")) {
                strScope = null;
                log.warn("scope is \"null\" when lookup property \""+property+"\" of bean \""+name+"\"");
            }
        }

        try {
            value = wrappedUtil.lookup(pageContext, name, property, strScope);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static void write(PageContext pageContext, String text) {
        try {
            wrappedUtil.write(pageContext, text);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * add option into SelectTag
     * @param options List
     * @param label String
     * @param value String
     */
    public static void addSelectedOption(List options,String label,String value) {
        if(options!=null && label!=null && value!=null) {
            boolean hasFind=false;
            for(int i=0;i<options.size();i++) {
                ISelectOption option=(ISelectOption)options.get(i);
                if(option.getValue()!=null && option.getValue().equals(value)) {
                    hasFind=true;
                }
            }
            if(!hasFind) {
                options.add(new SelectOptionImpl(label,value));
            }
        }
    }

    /**
     * add option into SelectTag
     * @param options List
     * @param label String
     * @param value String
     */
    public static void addSelectedOption(int index,List options,String label,String value) {
        if(options!=null && label!=null && value!=null) {
            boolean hasFind=false;
            for(int i=0;i<options.size();i++) {
                ISelectOption option=(ISelectOption)options.get(i);
                if(option.getValue()!=null && option.getValue().equals(value)) {
                    hasFind=true;
                }
            }
            if(!hasFind) {
                options.add(index,new SelectOptionImpl(label,value));
            }
        }
    }
    /**
     * 将data中所有对象转换成选项列表,labelProperty为显示文本对应的属性,valueProperty为选择的值对应的属性,
     * titleProperty为选项的toolTip对象的属性
     * @param data List
     * @param labelPropety String
     * @param valueProperty String
     * @param titleProperty String
     */
    public static List list2Options(List data,String labelPropety,String valueProperty,String titleProperty){
        List result = new ArrayList();
        if(data == null || data.size() == 0)
            return result;
        for(int i = 0;i < data.size() ;i ++){
            Object obj = data.get(i);
            try {
                String label = DtoUtil.getProperty(obj, labelPropety).toString();
                String value = DtoUtil.getProperty(obj, valueProperty).toString();
                String title = null;
                if(titleProperty != null){
                    title = (String) DtoUtil.getProperty(obj, titleProperty);
                }
                ISelectOption option = new SelectOptionImpl(label,value,title);
                result.add(option);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
    public static List list2Options(List data,String labelPropety,String valueProperty){
        return list2Options(data,labelPropety,valueProperty,null);
    }
    public static List array2Options(String[] array){
        List result = new ArrayList();
        if(array == null || array.length == 0)
            return result;
        for(int i = 0;i < array.length;i ++){
            ISelectOption option = new SelectOptionImpl(array[i], array[i]);
            result.add(option);
        }
        return result;
    }
}
