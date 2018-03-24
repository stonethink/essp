package server.essp.common.mail;

import server.essp.common.mail.LgMailSend;
import java.util.Iterator;
import java.util.HashMap;

import java.util.Map;

import server.framework.common.BusinessException;

import java.util.Calendar;
import server.framework.logic.AbstractBusinessLogic;

/**
 *�������ڸ���ģ���ļ�������������������EMAIL
 *author:Robin.Zhang
 */

public class SendHastenMail extends AbstractBusinessLogic {

    /**
     *��ȡ������û�б�CONFIRM�Ĺ�������Ϣ
     *��������vmFile��ģ���ļ�
     *       hm��Ҫ���͵���������
     *�����أ���
     */
  
    public void sendHastenMail(String vmFile, String title, HashMap hm) {

        Iterator iter = hm.entrySet().iterator();
        Calendar cal = Calendar.getInstance();
        try {
            for (; iter.hasNext(); ) {
                Map.Entry item = (Map.Entry) iter.next();
                String t = item.getKey().toString();
                ContentBean cb = new ContentBean();
                cb = (ContentBean) hm.get(t);

                cb.getUser();
                LgMailSend logic = new LgMailSend();
                GenMailContent gmc = new GenMailContent();

                //��ӡ�ʼ���������
//                System.out.println(gmc.generateMailContent(vmFile, cb));
                logic.setContent(gmc.generateMailContent(vmFile, cb));
                logic.setTitle(title);

                //��������
                HashMap att = cb.getAttachments();
                if (att != null) {
                    logic.setAttachmentFiles(att);
                }

                //log��Ϣ
                log.info(cal.getTime().toString() + "--" + title + "--" +
                         cb.getEmail());

                //�������ʼ���ַ
                logic.setToAddress(cb.getEmail());
                if (cb.getCcAddress() != null && !cb.getCcAddress().equals("")) {
                    logic.setCcAddress(cb.getCcAddress());
                }
            
                logic.setIsHtml(true);
                logic.send();

            }
        } catch (Throwable tx) {
            String msg = "mail send error ";
            log.error(msg);
            throw new BusinessException("", msg, tx);
        }
    }

}
