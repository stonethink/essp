package server.essp.common.excelUtil;

import java.io.OutputStream;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wits.util.Parameter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.struts.ActionSupport;
import server.framework.action.AbstractBusinessAction;

public abstract class AbstractExcelAction extends ActionSupport implements IExcelBusinessAction{
    private WebApplicationContext webContext;
    private static ApplicationContext context = null;
    public void execute(HttpServletRequest request,
                        HttpServletResponse response, OutputStream os) throws Exception{
        Parameter param = new Parameter();
        Enumeration en = request.getParameterNames();
        while( en.hasMoreElements() ){
            String name = (String)en.nextElement();
            String value = request.getParameter(name);
            param.put(name, value);
        }

        execute(request, response, os, param);
    }

    /**
     * 从Spring的配置中取得Service Bean
     *
     * @param beanName
     * @return Object
     * @author Robin
     */
    protected Object getBean(String beanName) {
        ApplicationContext context = getWebContext();
        if(context == null){
            context = getContext();
        }
        return context.getBean(beanName);
    }

    /**
     * context 单例
     * @return ApplicationContext
     * @throws BeansException
     */
    private ApplicationContext getContext() throws BeansException {
        if(context == null) {
            context = new ClassPathXmlApplicationContext(AbstractBusinessAction.SPRING_CONFIG_MATCH);
        }
        return context;
    }

    private WebApplicationContext getWebContext() {
        if(webContext == null) {
            this.getWebApplicationContext();
        }
        return webContext;
    }


    //param里为名值对,名字和值都为String
    public abstract void execute(HttpServletRequest request,
                        HttpServletResponse response, OutputStream os, Parameter param) throws Exception;

    public void setWebContext(WebApplicationContext webContext) {
        this.webContext = webContext;
    }
}
