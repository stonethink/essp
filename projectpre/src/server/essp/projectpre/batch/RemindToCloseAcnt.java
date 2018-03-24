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
 * ��������ר������������ڵ�ר��
 * @author wenhaizheng
 */
public class RemindToCloseAcnt {
    public static final String vmFile11 = "mail/template/proj/NearToCloseMail.html"; 
    private static Config cfg = new Config("BaseMail");
   /**
    * ����ר������᰸
    *   1.��ȡ�����ļ��е�essp�ʺ�����(ʹ��AD����ʱʹ��)
    *   2.��ѯ��Ҫ���ڵ�ר��
    *   3.�������ר����ר���������ʼ�
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
                //����ר��Ԥ�ƽ��������뵱ǰ������������
                int daysBetween = daysBetween(view.getAcntPlannedFinish(),new Date());
                if(daysBetween==-7){//���ר�����������ڵ�ǰ����֮�������һ�ܣ����ʼ����ѣ���Ϊ��ǰһ������
//                    System.out.println("Send mail! Finish-7=today");
                    //�����ʼ���ר������
                    sendLogic.sendMail(view.getPmLoginid(), view.getPmDomain(), 
                                       vmFile11, "Project Reaching Date", view);
                }else if(daysBetween==0){//���ר���������ھ��ǵ�ǰ���ڣ����ʼ�����
//                    System.out.println("Send mail! Finish=today");
                    //�����ʼ���ר������
                    sendLogic.sendMail(view.getPmLoginid(), view.getPmDomain(), 
                                       vmFile11, "Project Reaching Date", view);
                }else if(daysBetween%7==0){//�����ǰ�����Ѿ�����ר���������ڣ��ҵ�ǰ����������������7�ı�����ʱ���ʼ�����
//                    System.out.println("Send mail! Finish<today (today-Finish)%7=0");
                    //�����ʼ���ר������
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
     * ������������֮�������
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
     * ��������Calendar���ͱ���֮�����������
     * @param early
     * @param late
     * @return
     */
    public static final int daysBetween(Calendar early, Calendar late){

        return (int) (toJulian(late) - toJulian(early));
    }
    /**
     * ��Calendar���͵ı���ת��ΪJulian���������ݲ�����
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
