package server.framework.hibernate;

import net.sf.hibernate.*;
import net.sf.hibernate.cfg.*;
import org.apache.log4j.*;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.Resource;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.beans.factory.FactoryBean;

/**
 * 管理和加载Hibernate的配置及SessionFactory
 * 且为了让Spring调用SessionFactory,所以实现接口 FactoryBean
 * @author not attributable
 * @version 1.0
 */
public class HibernateUtil implements FactoryBean{

    static Category log = Category.getInstance("server");

    public static final String HBM_CONFIG_MATCH="classpath*:sysCfg/hbm*.cfg.xml";//hibernatep配置文件的匹配规则
    public static final String HBM_CONFIG_MAIN="hbmMain.cfg.xml";//hibenate主配置文件,必需有的

    private static HibernateUtil instance = null;
    private static Configuration config = null;
    private static SessionFactory sessionFactory;

    private HibernateUtil() {
        try {
            loadConfig();
            sessionFactory = config.buildSessionFactory();
        } catch (Throwable ex) {
            log.error("Initial SessionFactory creation failed.", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    //利用Spring装载配置文件的方式,查找所有hibernate配置文件
    private void loadConfig() throws IOException, MappingException,HibernateException {
        config = new HibernateConfig();

        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] reses = resolver.getResources(HBM_CONFIG_MATCH);
        if(reses == null || reses.length == 0)
            throw new ExceptionInInitializerError("Can not find hibernate config files which match '"+HBM_CONFIG_MATCH+"'");
        boolean cfgMain = false;
        for(int i = 0; i < reses.length ;i ++){
            Resource res = reses[i];
            String fileName = res.getFilename();
            System.out.println("load " + fileName + " ...... ");
            ( (HibernateConfig)config ).configure(res.getInputStream(),
                                                  fileName);
            if(res.getFilename().equals(HBM_CONFIG_MAIN))
                cfgMain = true;
        }
        //没有主配置文件,则不能运行
        if(cfgMain == false)
            throw new ExceptionInInitializerError("Can not find main hibernate config file:hbmMain.xml");
    }

    public synchronized static HibernateUtil getInstance() {
        if (instance == null) {
            instance = new HibernateUtil();
        }
        return instance;
    }

    public static SessionFactory getSessionFactory() throws HibernateException {
        if (sessionFactory == null) {
            getInstance();
        }
        return sessionFactory;
    }

    public static final ThreadLocal session = new ThreadLocal();

    public static Session getSession() throws HibernateException {
        Session s = (Session) session.get();
        // Open a new Session, if this Thread has none yet
        if (s == null) {
            getInstance();
            s = sessionFactory.openSession();
            session.set(s);
        }
        return s;
    }

    public static void closeSession() throws HibernateException {
        Session s = (Session) session.get();
        session.set(null);
        if (s != null) {
            s.close();
        }
    }

    public static Configuration getConfig() {
        return config;
    }

    public class HibernateConfig extends Configuration {
        public Configuration configure(InputStream in, String configFileName) throws
                HibernateException {
            return doConfigure(in,
                               configFileName);
        }
    }


    public static void main(String args[]) {
        try {
            HibernateUtil.getSessionFactory();
            System.out.print("Ok.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Object getObject() throws Exception {
        return getSessionFactory();
    }

    public Class getObjectType() {
        return SessionFactory.class;
    }

    public boolean isSingleton() {
        return true;
    }

}
