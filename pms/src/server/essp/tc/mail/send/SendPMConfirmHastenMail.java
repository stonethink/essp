package server.essp.tc.mail.send;

import server.essp.tc.mail.logic.LgGetPMConfirmDate;
import server.framework.common.BusinessException;
import server.essp.common.mail.SendHastenMail;

/**
 * 此程序用于发送项目经理需要确认周报的催促邮件，发送至PM
 * 运行参数：无
 * author:Robin.Zhang
 */
public class SendPMConfirmHastenMail  {
    public static final String vmFile1 = "mail/template/tc/PMConfirmMailTemplate.htm";
    public static final String title="Please confirm these Weekly Report";
    public SendPMConfirmHastenMail() {
    }

    public static void main(String[] args) throws BusinessException {


        System.out.println("send PM confirm mail start run!");
        LgGetPMConfirmDate gwfd = new LgGetPMConfirmDate();
        SendHastenMail shm = new SendHastenMail();

        shm.sendHastenMail(vmFile1,title,gwfd.getPMConfirmDate());


    }
}
