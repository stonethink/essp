package server.essp.timesheet.tsremind;

import java.util.*;

import org.apache.log4j.Category;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import server.essp.timesheet.period.service.IPeriodService;
import server.essp.timesheet.tsremind.service.ITsRemindService;
import server.framework.primavera.IPrimaveraApi;
import c2s.essp.common.calendar.WorkCalendar;
import c2s.essp.common.user.DtoUser;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheetPeriod;

import com.wits.util.ThreadVariant;

public class TsRemind {
	protected static Category log = Category.getInstance("server");
	private static ApplicationContext context = null;
	private DtoTimeSheetPeriod period;
	private Date today = new Date();
	private Date pBegin;
    private Date pEnd;
    private List periodList;
    private static String mailType = "";
    private static String site = "";
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

    public static final String SPRING_CONFIG_MATCH="classpath*:sysCfg/applicationContext*.xml";//hibernatep配置文件的匹配规则
    /**
     * 运行多个周期的生成处理
     * @param offset
     * @param times
     */
    public void runMultiPeriod(int offset, int times) {
    	for(int i = 0; i < times; i ++) {
    		runOnePeriod(offset + i);
    	}
    }
    /**
     * 根据月份初始开始结束日期
     * @param offset
     * @param startDay
     */
    public void runOneMonth(int offset, int startDay) {
    	Calendar cToday = Calendar.getInstance();
    	int todayNum = cToday.get(Calendar.DAY_OF_MONTH);
    	int endDay = 0;
    	if(startDay == 1) {//如果开始日期为1则特殊处理
    		cToday.add(Calendar.MONTH, offset);
    		endDay = cToday.getActualMaximum(Calendar.DAY_OF_MONTH);
    		cToday.set(Calendar.DAY_OF_MONTH, startDay);
			pBegin = cToday.getTime();
			cToday.set(Calendar.DAY_OF_MONTH, endDay);
			pEnd = cToday.getTime();
    	} else {
    		endDay = startDay - 1;//月结束日期
    		if (todayNum > endDay) {
    			//今日日期如果在月结束日期之后则为当月开始及结束
				cToday.add(Calendar.MONTH, offset);
			} else {
				//今日日期如果在月开始日期之前则为前月开始及结束
				cToday.add(Calendar.MONTH, offset - 1);
			}
    		cToday.set(Calendar.DAY_OF_MONTH, startDay);
			pBegin = cToday.getTime();
			cToday.add(Calendar.MONTH, 1);
			cToday.set(Calendar.DAY_OF_MONTH, endDay);
			pEnd = cToday.getTime();
    	}	
    	pBegin = WorkCalendar.resetBeginDate(pBegin);
		pEnd = WorkCalendar.resetEndDate(pEnd);
    }
    /**
	 * 按月运行邮件催促程序
	 * 
	 */
    public void runByMonths(int offset, int times, int startDay) {
    	for(int i = 0; i < times; i ++) {
    		runOneMonth(offset + i, startDay);
    	}
    	//查询两个时间内的所有工时单区间
    	IPeriodService periodService = (IPeriodService)getBean("periodService");
        try {
			periodList = periodService.getPeriodByDate(pBegin, pEnd);
		} catch (Exception e) {
			log.error("get period list error", e);
			return;
		}
		if(periodList == null || periodList.size() == 0) {
			log.error("get period list error");
			return;
		}
		ITsRemindService service = (ITsRemindService) getBean("tsRemindService");
		String[] sites = site.split(",");
		for(String vSite : sites) {
			service.queryDataAndSendMail(pBegin, pEnd, mailType, vSite, periodList);
		}
    }
    /**
     * 运行一个周期的生成处理
     * @param offset
     */
    public void runOnePeriod(int offset) {
    	initPeriod(offset);
    	//如果period无效，直接返回
    	if(validatePeriod() == false) {
    		return;
    	}
    	periodList = new ArrayList();
    	DtoTimeSheetPeriod dtoTimeSheetPeriod = new DtoTimeSheetPeriod();
    	dtoTimeSheetPeriod.setBeginDate(pBegin);
    	dtoTimeSheetPeriod.setEndDate(pEnd);
    	periodList.add(dtoTimeSheetPeriod);
    	ITsRemindService service = (ITsRemindService) getBean("tsRemindService");
    	String[] sites = site.split(",");
    	for(String vSite : sites) {
    		service.queryDataAndSendMail(pBegin, pEnd, mailType, vSite, periodList);
    	}
    	
    }
    /**
     * 验证period是否有效
     * @return
     */
    private boolean validatePeriod() {
    	if(period == null) {
    		log.error("period is null, remind process abort!");
    		return false;
    	}
    	pBegin = period.getBeginDate();
    	if(pBegin == null) {
    		log.error("period begin date is null, remind process abort!");
    		return false;
    	}
    	pEnd = period.getEndDate();
    	if(pEnd == null) {
    		log.error("period end date is null, remind process abort!");
    		return false;
    	}
    	return true;
    }
    /**
     * 初始化当前工时单周期
     * @param weekOffset
     */
    protected void initPeriod(int weekOffset) {
    	IPeriodService periodService = (IPeriodService)getBean("periodService");
        try {
        	today = WorkCalendar.resetBeginDate(today);
			period = periodService.getNextPeriod(today, weekOffset);
		} catch (Exception e) {
			log.error("getNextPeriod error offset:" 
					+ weekOffset + "  today:" + today, e);
		}
    }
    public static void main(String[] args) {
    	System.out.println("Begin to send remind mails to people!");
    	try{
    		int offset = 0;
            int times = 1;
            String mailTypeArg = "UFS";//邮件发送对象参数默认UF（未填写人员）
            String siteArg = "WH";//发送对象的site
            String timeArg = "W";//提醒范围，W为周，M为月
            int startDay = 1;//月开始日期范围1-28
            if(args.length > 0) {
            	try {
            		offset = Integer.parseInt(args[0]);
                } catch (NumberFormatException tx) {
                	log.warn("offset Integer parse error [" + offset + "], it's will be default 0", tx);
                }
            }
            if (args.length > 1) {
                try {
                	times = Integer.parseInt(args[1]);
                } catch (NumberFormatException tx) {
                    log.warn("times Integer parse error [" + times + "], it's will be default 1", tx);
                }
            }
            if(args.length > 2) {
           	 try {
           		 startDay = Integer.parseInt(args[2]);
                } catch (NumberFormatException tx) {
                    log.warn("times Integer parse error [" + startDay + "], it's will be default 1", tx);
                }
           }
            if(args.length > 3) {
            	mailTypeArg = args[3];
            	if("".equals(mailTypeArg)) {
                	log.warn("Do not know send to whom!");
                	throw new Exception();
                }
            }
            if(args.length > 4) {
            	siteArg = args[4];
            	if("".equals(siteArg)) {
                	log.warn("There no site to load config!");
                	throw new Exception();
                }
            }
            if(args.length > 5) {
            	timeArg = args[5];
            	if("".equals(timeArg)) {
                	log.warn("Have not set time to send!");
                	throw new Exception();
                }
            }
            mailType = mailTypeArg;
            site = siteArg;
    	ThreadVariant thread = ThreadVariant.getInstance();
		DtoUser user = new DtoUser();
		user.setUserLoginId("essp");
		user.setPassword("essp@654321!");
		user.setDomain("wh");
		user.setUserName("essp");
		thread.set(DtoUser.SESSION_USER, user);
		thread.set(DtoUser.SESSION_LOGIN_USER, user);
		TsRemind tsRemind = new TsRemind();
		if(timeArg.equals("W")) {
			tsRemind.runMultiPeriod(offset, times);
		} else if(timeArg.equals("M")) {
			tsRemind.runByMonths(offset, times, startDay);
		}
		System.out.println("Mails sending program end!");
		waitExit();
    	} catch (Exception e) {
    		log.error("Mails send error!", e);
    		waitExit();
    	}
    	
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
