/*
 * Created on 2007-4-6
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.projectpre.batch;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import server.essp.projectpre.db.QueryAcntView;
import server.essp.projectpre.service.account.AccountServiceImpl;
import server.essp.projectpre.service.account.IAccountService;
import server.essp.projectpre.service.sendMail.ISendMailService;
import server.essp.projectpre.service.sendMail.SendMailServiceImpl;
import server.framework.hibernate.HBComAccess;
import c2s.essp.common.user.DtoUser;

import com.wits.util.Config;
import com.wits.util.ThreadVariant;
/**
 * 用于提醒专案经理结束到期的专案
 * @author wenhaizheng
 */
public class RemindToCloseAcnt {
    public static final String vmFile11 = "mail/template/proj/NearToCloseMail.html"; 
    private static Config cfg = new Config("BaseMail");
   /**
    * 提醒专案经理结案
    *   1.获取配置文件中的essp帐号密码(使用AD找人时使用)
    *   2.查询将要到期的专案
    *   3.给查出的专案的专案经理发送邮件
    * @param args
    */
    public static void main(String[] args) {
        String psw = cfg.getValue("Essp_Password");
        ThreadVariant thread = ThreadVariant.getInstance();
        DtoUser dtoUser = new DtoUser();
        dtoUser.setUserLoginId("essp");
        dtoUser.setPassword(psw);
        dtoUser.setDomain("wh");
        thread.set(DtoUser.SESSION_LOGIN_USER, dtoUser);
        IAccountService  acntLogic = new AccountServiceImpl();
        ISendMailService sendLogic = new SendMailServiceImpl();
        List pmList = null;
        try {
            pmList = acntLogic.queryPMIdNearToClose();
            if(pmList==null||pmList.size()==0){
                System.out.println("no account found that is near to close!");
            }
            int size = pmList.size();
            QueryAcntView view = null;
            for(int i = 0;i<size;i++){
                view = (QueryAcntView)pmList.get(i);
                //计算专案预计结束日期与当前日期相差的天数
                int daysBetween = daysBetween(view.getAcntPlannedFinish(),new Date());
                if(daysBetween==-7){//如果专案结束日期在当前日期之后且相差一周，则发邮件提醒，即为提前一周提醒
//                    System.out.println("Send mail! Finish-7=today");
                    //发送邮件给专案经理
                    sendLogic.sendMail(view.getPmLoginid(), view.getPmDomain(), 
                                       vmFile11, "Project Reaching Date", view);
                }else if(daysBetween==0){//如果专案结束日期就是当前日期，则发邮件提醒
//                    System.out.println("Send mail! Finish=today");
                    //发送邮件给专案经理
                    sendLogic.sendMail(view.getPmLoginid(), view.getPmDomain(), 
                                       vmFile11, "Project Reaching Date", view);
                }else if(daysBetween%7==0){//如果当前日期已经超过专案结束日期，且当前日期与结束日期相差7的倍数天时则发邮件提醒
//                    System.out.println("Send mail! Finish<today (today-Finish)%7=0");
                    //发送邮件给专案经理
                    sendLogic.sendMail(view.getPmLoginid(), view.getPmDomain(), 
                                       vmFile11, "Project Reaching Date", view);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                HBComAccess.getInstance().close();
            } catch (Exception e) {
            }
            
        }
    }
    /**
     * 计算两个日期之间的天数
     * @param early
     * @param late
     * @return
     */
    public static final int daysBetween(Date early, Date late){

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(early);
        c2.setTime(late);

        return daysBetween(c1, c2);
    }
    /**
     * 计算两个Calendar类型变量之间相隔的天数
     * @param early
     * @param late
     * @return
     */
    public static final int daysBetween(Calendar early, Calendar late){

        return (int) (toJulian(late) - toJulian(early));
    }
    /**
     * 将Calendar类型的变量转化为Julian日期型数据并返回
     * @param c
     * @return
     */
    public static final float toJulian(Calendar c){

        int Y = c.get(Calendar.YEAR);
        int M = c.get(Calendar.MONTH);
        int D = c.get(Calendar.DATE);
        int A = Y / 100;
        int B = A / 4;
        int C = 2 - A + B;
        float E = (int) (365.25f * (Y + 4716));
        float F = (int) (30.6001f * (M + 1));
        float JD = C + D + E + F - 1524.5f;

        return JD;
    }
   
}
