package server.essp.tc.hrmanage.logic;

import org.apache.log4j.Category;
import java.util.Date;
import server.essp.common.mail.LgMailSend;
import java.util.List;
import server.framework.common.BusinessException;
import server.essp.tc.hrmanage.viewbean.VbTcExcel;
import com.wits.util.Config;
import itf.hr.HrFactory;
import java.text.MessageFormat;
import com.wits.util.comDate;
import java.util.Calendar;
import c2s.essp.tc.weeklyreport.DtoWeeklyReportSumByHr;

/**
 * 实现每周末给每个员工发送自己的差勤信息的的功能
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class AttendanceEmailSender {
    private LgTcExcel lgData = new LgTcExcel();
    private LgMailSend lgMail = new LgMailSend();
    private Config mailCfg = new Config("AttendanceMail");
    private Config attendManagerCfg = new Config("manager");
    protected static Category log = Category.getInstance("server");
    public static final String DEBUG = "DEBUG";
    private String period = null;
    private String wmflag = null;
    /**
     * 发送差勤信息给每个员工:获得所有员工的差勤信息,遍历发送
     * @param acntRid:人力类型Account的Rid
     *        beginPeriod和endPeriod:差勤统计信息的区间
     *        wmflag:
     */
    public void send(Long acntRid, Date beginPeriod, Date endPeriod,String wmflag){
        if(acntRid == null || beginPeriod == null || endPeriod == null)
            throw new BusinessException("","illegal arguments!");
        period = comDate.dateToString(beginPeriod) + "~" + comDate.dateToString(endPeriod);
        this.wmflag = wmflag;
        List data = lgData.AllDetailList(acntRid,beginPeriod,endPeriod,wmflag);
        List labors = null;
        //所有员工的差勤信息List放在data[1]中
        try{
            labors = (List) data.get(1);
        }catch(Throwable tx){
            String msg = "error get all attendance data!";
            log.error(msg,tx);
            throw new BusinessException("",msg,tx);
        }
        if(labors == null || labors.size() == 0){
            log.warn("there is not attendace data!");
            return;
        }
        for(int i = 0;i < labors.size() ;i ++){
            VbTcExcel labor = (VbTcExcel) labors.get(i);
            sendAttendaceMail(labor);
        }
    }
    /**
     * 发送差勤信息给员工
     * @param labor VbTcExcel
     */
    private void sendAttendaceMail(VbTcExcel labor){
        if(labor == null)
            return;
        String loginId = labor.getName();
        if(loginId == null || "".equals(loginId)){
            log.warn("Can not send mail to null!");
            return;
        }

        String mailTitle = getMailTitle();
        String mailContent = getMailContent(labor);
        System.out.println(mailContent);
        String address = HrFactory.create().getUserEmail(loginId);
        lgMail.setTitle(mailTitle);
//        //调试时将所有Email发给RongXiao
//            lgMail.setToAddress("rongxiao@wistronits.com");
            System.out.println("____________________mail to___________________");
            lgMail.setToAddress(address);

        lgMail.setContent(mailContent);
        try{
            lgMail.setIsHtml(true);
            lgMail.send();
        }catch(Throwable tx){
            log.warn("send mail to :" + loginId + " failed!",tx);
        }
    }
    /**
     * 邮件标题
     * @return String
     */
    private String getMailTitle(){
        return mailCfg.getValue("mail.title");
    }
    /**
     * 邮件内容
     * @param labor VbTcExcel
     * @return String
     */
    private String getMailContent(VbTcExcel labor){
        StringBuffer content = new StringBuffer();
        String main = getMainConent(labor.getName());
        content.append(main);
        String table = getTableData(labor);
        content.append(table);
        return content.toString();
    }
    private String getMainConent(String name){
        String main = mailCfg.getValue("mail.main");
        Object[] args = new Object[5];
        args[0] = name;
        args[1] = mailCfg.getValue(wmflag);
        args[2] = period;
        String manager = attendManagerCfg.getValue("manager");
        String email = HrFactory.create().getUserEmail(manager);
        args[3] = email;
        args[4] = manager;
        main = MessageFormat.format(main,args);
        return main;
    }
    //每人的差勤数据Table
    private String getTableData(VbTcExcel labor){
        StringBuffer table = new StringBuffer();
        String head = mailCfg.getValue("mail.table.head");
        Object[] headArgs = new Object[1];
        headArgs[0] = period;
        head = MessageFormat.format(head,headArgs);
        String data = mailCfg.getValue("mail.table.data");
        Object[] dataArgs = new Object[15];
        dataArgs[0] = labor.getName();
        dataArgs[1] = comDate.dateToString(labor.getBeginDateOfWork());
        dataArgs[2] = comDate.dateToString(labor.getEndDateOfWork());
        dataArgs[3] = labor.getStandardWorkTime();
        dataArgs[4] = labor.getActualWorkTime();
        dataArgs[5] = labor.getNormalWorkTime();
        dataArgs[6] = labor.getSubWorkTime();
        dataArgs[7] = labor.getSalaryWorkTime();
        dataArgs[8] = labor.getOverTime();
        dataArgs[9] = labor.getFullSalaryLeave();
        dataArgs[10] = labor.getHalfSalaryLeave();
        dataArgs[11] = labor.getLeave();
        dataArgs[12] = labor.getAbsentFromWork();
        dataArgs[13] = labor.getViolat();
        dataArgs[14] = labor.getRemark();
        data = MessageFormat.format(data,dataArgs);

        String bottom = mailCfg.getValue("mail.table.bottom");
        table.append(head);
        table.append(data);
        table.append(bottom);
        return table.toString();
    }


    public static void main(String args[]){
        AttendanceEmailSender sender = new AttendanceEmailSender();
        Calendar begin = Calendar.getInstance();
        begin.set(2006,1,18,0,0,0);
        begin.set(Calendar.MILLISECOND,0);
        Calendar end = Calendar.getInstance();
        end.set(2006,1,24,0,0,0);
        end.set(Calendar.MILLISECOND,0);

        sender.send(new Long(883),
                    begin.getTime()
                    ,end.getTime(),
                    DtoWeeklyReportSumByHr.ON_WEEK);
//        VbTcExcel labor = new VbTcExcel();
//        labor.setName("RongXiao");
//        String mailContent = sender.getMailContent(labor);
//        System.out.println(mailContent);
    }
}
