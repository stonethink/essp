package server.essp.timesheet.dailyreport;

import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Category;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import server.essp.timesheet.dailyreport.service.IDailyReportService;
import server.framework.primavera.IPrimaveraApi;
import c2s.essp.common.user.DtoUser;

import com.wits.util.ThreadVariant;

public class MailDailyReport {
	
	protected static Category log = Category.getInstance("server");
	private static ApplicationContext context = null;
	private static Date today;
	private static Date yesterday;
	/**
     * context 单例
     * @return ApplicationContext
     * @throws BeansException
     */
    private static ApplicationContext getContext() throws BeansException {
        if(context == null) {
            context = new ClassPathXmlApplicationContext(SPRING_CONFIG_MATCH);
        }
        return context;
    }
    /**
     * 从Spring的配置中取得Service Bean
     *
     * @param beanName
     * @return Object
     */
    protected static Object getBean(String beanName) {
        if(context == null){
            context = getContext();
        }
        return context.getBean(beanName);
    }

    public static final String SPRING_CONFIG_MATCH="classpath*:sysCfg/applicationContext*.xml";//hibernatep配置文件的匹配规则
    
    public static void main(String[] args) {
    	System.out.println("Begin to send daily report mails!");
    	try {
    		int offset = 0;
			if(args.length > 0) {
            	try {
            		offset = Integer.parseInt(args[0]);
                } catch (NumberFormatException tx) {
                	log.warn("offset Integer parse error [" + offset + "], it's will be default 0", tx);
                }
            }

			initDate(offset);
			ThreadVariant thread = ThreadVariant.getInstance();
			DtoUser user = new DtoUser();
			user.setUserLoginId("essp");
			user.setPassword("essp@654321!");
			user.setDomain("wh");
			user.setUserName("essp");
			thread.set(DtoUser.SESSION_USER, user);
			thread.set(DtoUser.SESSION_LOGIN_USER, user);
			IDailyReportService dailyReportService = (IDailyReportService) getBean("dailyReportService");
			dailyReportService.exportAndSendMail(today, yesterday);
			
			System.out.println("Mails sending program end!");
			waitExit();
		} catch (Exception e) {
			log.error("Mails send error!", e);
    		waitExit();
		}
    }
    private static void initDate(int offset) {
    	Calendar ca = Calendar.getInstance();
    	ca.add(Calendar.DAY_OF_MONTH, offset);
    	today = ca.getTime();
    	ca.add(Calendar.DAY_OF_MONTH, -1);
    	yesterday = ca.getTime();
    }
    private static void waitExit() {
		Thread t = new Thread(new Thread() {
			public void run() {
				try {
					sleep(60000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				logoutPrimaveraApi();
				System.exit(0);
			}
		});
		try {
			t.start();
		} catch (Throwable tx) {
			tx.printStackTrace();
			t.stop();
		}
	}
    /**
     * logout primavera api in current thread
     */
    private static void logoutPrimaveraApi() {
        ThreadVariant thread = ThreadVariant.getInstance();
        com.primavera.integration.client.Session apiSession =  
         (com.primavera.integration.client.Session) thread.get(IPrimaveraApi.PRIMAVERA_API_SESSION);
        if(apiSession != null) {
            apiSession.logout();
            thread.remove(IPrimaveraApi.PRIMAVERA_API_SESSION);
        }
    }

}
