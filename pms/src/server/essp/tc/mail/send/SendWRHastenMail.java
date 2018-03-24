package server.essp.tc.mail.send;

import server.essp.tc.mail.logic.LgGetWeeklyReportData;
import server.framework.common.BusinessException;
import server.essp.common.mail.SendHastenMail;

/**
 * �˳������ڷ����ܱ��ߴ��ʼ�
 * ���в���������1����ĿID��
 * ��������������2���ǰ��ܻ��ǰ��£���Ϊ"week"����Ϊ"month"��
 * ��������������3�����������뵱ǰ���ڵ�ƫ����������Ϊ������
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
