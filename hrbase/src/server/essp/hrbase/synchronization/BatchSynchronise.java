package server.essp.hrbase.synchronization;

import java.util.*;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import server.essp.common.mail.ContentBean;
import server.essp.common.mail.SendHastenMail;
import server.essp.hrbase.synchronization.service.ISyncMainService;
import server.framework.primavera.IPrimaveraApi;

import com.wits.util.*;

import db.essp.hrbase.HrbHumanBaseLog;

public class BatchSynchronise {

	private static ApplicationContext context = null;

	public static final String vmFile1 = "mail/template/hrbase/SyncErrorMail.htm";

	public static final String vmFileRemind = "mail/template/hrbase/HumanSyncLog.htm";

	/**
	 * 从Spring的配置中取得Service Bean
	 * 
	 * @param beanName
	 * @return Object
	 */
	protected static Object getBean(String beanName) {
		if (context == null) {
			context = getContext();
		}
		return context.getBean(beanName);
	}

	/**
	 * context 单例
	 * 
	 * @return ApplicationContext
	 * @throws BeansException
	 */
	private static ApplicationContext getContext() throws BeansException {
		if (context == null) {
			context = new ClassPathXmlApplicationContext(SPRING_CONFIG_MATCH);
		}
		return context;
	}

	public static final String SPRING_CONFIG_MATCH = "classpath*:sysCfg/applicationContext*.xml";// hibernatep配置文件的匹配规则

	public static void main(String[] arg) {
		ISyncMainService syncMainService = (ISyncMainService) getBean("syncMainService");
		List<HrbHumanBaseLog> dataList = syncMainService.searchDataForSync();
		boolean isError = false;
		if (dataList == null || dataList.size() == 0) {
			System.out.println("There is no data to Synchronise!");
		} else {
			isError = syncMainService.synchronise(dataList);
		}
		if (isError) {
			System.out.println("Synchronise error!");
			HashMap hm = getMailMap();
			sendMail(vmFile1, "Synchronise error", hm);
		} else {
			System.out.println("Synchronise successfully end!");
		}
		sendRemindMail(dataList);
		waitExit();
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
		com.primavera.integration.client.Session apiSession = (com.primavera.integration.client.Session) thread
				.get(IPrimaveraApi.PRIMAVERA_API_SESSION);
		if (apiSession != null) {
			apiSession.logout();
			thread.remove(IPrimaveraApi.PRIMAVERA_API_SESSION);
		}
	}

	public static HashMap getMailMap() {
		Config cf = new Config("TimesheetMail");
		String userName = cf.getValue("MasterName");
		String userMail = cf.getValue("MasterMail");
		HashMap hm = new HashMap();
		ArrayList al = new ArrayList();
		ContentBean cb = new ContentBean();
		cb.setUser(userName);
		cb.setEmail(userMail);
		cb.setMailcontent(al);
		hm.put(userName, cb);
		return hm;
	}
	
	private static void sendRemindMail(List dataList) {
		HashMap rhm = getRemindMailMap(dataList);
		String dateStr = comDate.dateToString(new Date());
		sendMail(vmFileRemind, "Synchronized human information at " + dateStr,
				rhm);
	}

	private static HashMap getRemindMailMap(List content) {
		Config cf = new Config("TimesheetMail");
		String userName = cf.getValue("RemindUserName");
		String userMail = cf.getValue("RemindUserMail");
		String ccUserMail = cf.getValue("RemindCcUserMail");
		HashMap hm = new HashMap();
		ArrayList al = new ArrayList(content);
		ContentBean cb = new ContentBean();
		cb.setUser(userName);
		cb.setEmail(userMail);
		cb.setCcAddress(ccUserMail);
		cb.setMailcontent(al);
		hm.put(userName, cb);
		return hm;
	}

	/**
	 * 发送邮件
	 * 
	 * @param vmFile
	 * @param title
	 * @param hm
	 */
	public static void sendMail(final String vmFile, final String title,
			final HashMap hm) {
		final SendHastenMail shMail = new SendHastenMail();
		shMail.sendHastenMail(vmFile, title, hm);
	}

}
