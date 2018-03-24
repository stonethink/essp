package server.essp.common.mail;

import server.essp.common.mail.LgMailSend;
import java.util.Iterator;
import java.util.HashMap;

import java.util.Map;

import server.framework.common.BusinessException;

import java.util.Calendar;
import server.framework.logic.AbstractBusinessLogic;

/**
 *此类用于根据模版文件和欲发送数据来发送EMAIL
 *author:Robin.Zhang
 */

public class SendHastenMail extends AbstractBusinessLogic {

    /**
     *　取得所有没有被CONFIRM的工作流信息
     *　参数：vmFile　模版文件
     *       hm　要发送的所有内容
     *　返回：空
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

                //打印邮件发送内容
//                System.out.println(gmc.generateMailContent(vmFile, cb));
                logic.setContent(gmc.generateMailContent(vmFile, cb));
                logic.setTitle(title);

                //附件发送
                HashMap att = cb.getAttachments();
                if (att != null) {
                    logic.setAttachmentFiles(att);
                }

                //log信息
                log.info(cal.getTime().toString() + "--" + title + "--" +
                         cb.getEmail());

                //发到此邮件地址
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
