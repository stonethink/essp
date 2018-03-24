package server.essp.tc.mail.send;

import server.essp.tc.mail.logic.LgGetWeeklyReportData;
import server.framework.common.BusinessException;
import server.essp.common.mail.SendHastenMail;

/**
 * 此程序用于发送周报催促邮件
 * 运行参数：参数1，项目ID；
 * 　　　　　参数2，是按周还是按月（周为"week"，月为"month"）
 * 　　　　　参数3，发送日期与当前日期的偏移量（必须为整数）
 * @author:Robin.Zhang
 */
public class SendWRHastenMail  {
    public static final String vmFile1 = "mail/template/tc/reportMailTemplate.htm";
    public static final String title="Please fill or lock these Weekly Report";
    public SendWRHastenMail() {
    }

    public static void main(String[] args) throws BusinessException {

            if (args.length == 0||args.length<3) {
                 throw new BusinessException("","illegal arguments");
            }

        System.out.println("send WR mail start run!");
        String accountId=args[0];
        boolean isWeek=args[1].equals("week");
        int offset=Integer.parseInt(args[2]);

        LgGetWeeklyReportData gwrd = new LgGetWeeklyReportData();
        SendHastenMail shm = new SendHastenMail();

        shm.sendHastenMail(vmFile1, title,gwrd.getWeeklyReportData(accountId,isWeek,offset));


    }
}
