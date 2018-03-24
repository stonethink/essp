package server.essp.tc.mail.send;

import server.framework.common.BusinessException;
import server.essp.tc.mail.logic.LgGetWorkFlowDate;
import server.essp.common.mail.SendHastenMail;

/**
 * �˳������ڷ��͹������ߴ��ʼ�(��������)
 * ���в�������
 * author:Robin.Zhang
 */
public class SendWFHastenMail  {
    public static final String vmFile1 = "mail/template/tc/workFlowMailTemplate.htm";
        public static final String title="Please confirm these WorkFlow";
    public SendWFHastenMail() {
    }

    public static void main(String[] args) throws BusinessException {


        System.out.println("send WF mail start run!");
        LgGetWorkFlowDate gwfd = new LgGetWorkFlowDate();
        SendHastenMail shm = new SendHastenMail();

        shm.sendHastenMail(vmFile1,title, gwfd.getWorkFlowDate());


    }
}
