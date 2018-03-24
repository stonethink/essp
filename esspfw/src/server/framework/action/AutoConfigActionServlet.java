package server.framework.action;

import org.apache.struts.action.*;
import javax.servlet.ServletException;
import java.io.File;

public class AutoConfigActionServlet extends ActionServlet {
    public static final String CONFIG_PREFIX= "struts-config";
    public static final String CONFIG_POSTFIX = ".xml";
    //自动装载WEB-INF下的struts-config*.xml;在web.xml中无需配置ActionServlet的config参数
    protected void initOther() throws ServletException {
        String path = this.getServletContext().getRealPath("/WEB-INF");
        File dir = new File(path);
        String[] fileNames = dir.list();
        if(fileNames == null || fileNames.length == 0){
            super.initOther();
        }
        String defaultCfg = null;
        for(int i =0; i < fileNames.length;i ++){
            String file = fileNames[i];
            if(file != null && file.startsWith(CONFIG_PREFIX) && file.endsWith(CONFIG_POSTFIX)){
                if(defaultCfg == null)
                    defaultCfg = "/WEB-INF/" + file;
                else
                    defaultCfg = defaultCfg + ",/WEB-INF/" + file;
            }
        }
        if(defaultCfg != null)
            this.config = defaultCfg;
        else
            super.initOther();
    }
}
