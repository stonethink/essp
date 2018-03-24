package server.essp.common.mail;

import java.io.*;
import java.util.*;

import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.*;

import com.wits.util.*;
import server.framework.common.*;
import javax.activation.FileDataSource;
import javax.activation.DataHandler;
import java.net.URL;
import javax.activation.URLDataSource;
import java.net.*;

/**
 * <p>Title: </p>
 * <p>Description: 发送邮件的类</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Enovation</p>
 * @author Xuxi.Chen
 * @version 1.0
 */

public class LgMailSend {
    /**邮件服务器地址,形如"10.5.2.253",必须但有默认值*/
    private String mailServerAddress;

    /**是否使用smtp协议,必须但有默认值*/
    private String isSmtp;

    /**发件人帐号,必须*/
    private String senderId;

    /**发件人密码,必须*/
    private String senderPassword;

    /**收件人地址，当有多个时用","逗号隔开,可以为空或null*/
    private String toAddress;

    /**cc人地址，当有多个时用","逗号隔开,可以为空或null*/
    private String ccAddress;

    /**标题,可以为空*/
    private String title;
    /**内容,可以为空*/
    private String content;
    /**是否为Html格式的邮件**/
    private boolean isHtml = false;
    /**邮件发送的附件,Key:作为附件的文件名,Value:要发送附件的File对象**/
    private Map attachmentFiles = null;
    /**有默认的邮件服务器地址和协议类型*/
    private Config defaultConfig = null;

    private String from;

    public String getFrom() {
        return this.from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public LgMailSend() {
        defaultConfig = new Config("BaseMail");
    }

    public void send() {

        Properties pos = System.getProperties();
        pos.put("mail.smtp.host", getMailServerAddress());
        pos.put("mail.smtp.auth", getIsSmtp());

        javax.mail.Session MailSession = null;
        MailSession = javax.mail.Session.getInstance(pos);

        try {
            MimeMessage mimeMsg = getMimeMessage(MailSession);

            Transport transport = MailSession.getTransport("smtp");
            transport.connect(pos.getProperty("mail.smtp.host"),
                              getSenderId(),
                              getSenderPassword());
            //扩展为可以改变发件人地址
            //Modified by:Robin.zhang
            if (this.getFrom() != null && !this.getFrom().equals("")) {
                mimeMsg.setFrom(new InternetAddress(this.getFrom()));
            }
            if (mimeMsg.getRecipients(RecipientType.TO) != null) {
                transport.sendMessage(mimeMsg,
                                      mimeMsg.getRecipients(RecipientType.TO));
            }
            if (mimeMsg.getRecipients(RecipientType.CC) != null) {
                transport.sendMessage(mimeMsg,
                                      mimeMsg.getRecipients(RecipientType.CC));
            }
            //transport.send(mimeMsg);
        } catch (MessagingException ex) {
            throw new BusinessException("E0000", "Send mail error.", ex);

        } catch (Exception e) {

        }

    }

    //依据邮件的Title,内容及收件者生成一个MimeMessage
    private MimeMessage getMimeMessage(javax.mail.Session mailSession) throws
        MessagingException {
        MimeMessage mimeMsg = new MimeMessage(mailSession);
        mimeMsg.setSubject(getTitle());
        mimeMsg.setFrom(new InternetAddress(getSenderId()));
        String msgBodys = getContent();
        //发送Html邮件或带附件的邮件
        if (isHtml || (attachmentFiles != null && attachmentFiles.size() > 0)) {
            MimeMultipart multipart = new MimeMultipart();
            //设置邮件内容
            BodyPart msgBodyPart = new MimeBodyPart();
            msgBodyPart.setContent(msgBodys, "text/html;charset=utf-8");
            multipart.addBodyPart(msgBodyPart);
            //设置附件内容
            if (attachmentFiles != null && attachmentFiles.size() > 0) {
                Set attachmentNames = attachmentFiles.keySet();
                for (Iterator it = attachmentNames.iterator(); it.hasNext(); ) {
                    String attachmentName = (String) it.next();
                    Object file = attachmentFiles.get(attachmentName);
//                    if (!(file instanceof File)) {
//                        throw new BusinessException("E0001",
//                                "The attachment is not File object ");
//                    }
                    //将附件扩展为可以发文件和远程文件
                    //远程文件用完整的URI表示(即地址带http头)
                    //Modified by:Robin.zhang
                    BodyPart attachBodyPart = new MimeBodyPart();
                    if (file instanceof File) {
                        FileDataSource fds = new FileDataSource((File) file);
                        attachBodyPart.setDataHandler(new DataHandler(fds));

                        try {
                            attachBodyPart.setFileName(MimeUtility.encodeText(
                                attachmentName, "utf-8", "B"));
                        } catch (UnsupportedEncodingException ex) {
                            throw new BusinessException("E0002",
                                "can not encode attachment file name!", ex);
                        }
                        multipart.addBodyPart(attachBodyPart);
                    } else if (file instanceof String) {
                        //处理网络地址类的附件
                        String fileUrl = (String) file;
                        if (!fileUrl.startsWith("http")) {
                            throw new BusinessException("E003",
                                "file name path not full URI.");
                        }
                        URL url = null;
                        try {
                            url = new URL(fileUrl);
                        } catch (MalformedURLException ex1) {
                            ex1.printStackTrace();
                        }
                        URLDataSource ur = new URLDataSource(url);
                        attachBodyPart.setDataHandler(new DataHandler(ur));
                        try {
                            attachBodyPart.setFileName(MimeUtility.encodeText(
                                attachmentName, "utf-8", "B"));
                        } catch (UnsupportedEncodingException ex) {
                            throw new BusinessException("E0002",
                                "can not encode attachment file name!", ex);
                        }
                        //如果不是在测试环境，则发送附件
                        String address = defaultConfig.getValue("Debug_Email");
                        if (address == null || address.equals("")) {
                            multipart.addBodyPart(attachBodyPart);
                        }

                    } else { //其它类型的附件不支持,javaMail也不支持
                        throw new BusinessException("E001",
                            "attachments(HashMap)'s value is not URI or File");
                    }

                }
            }
//            attachBodyPart.setDataHandler();

            mimeMsg.setContent(multipart);
        }
        //发送普通文本邮件
        else {
            mimeMsg.setText(msgBodys);
        }
        if (getToAddress() != null && !getToAddress().trim().equals("")) {
            Address[] adds = InternetAddress.parse(getToAddress());

            for (int i = 0; i < adds.length; i++) {
                System.out.println(" mail address " + adds[i].toString());
            }
            mimeMsg.addRecipients(javax.mail.Message.RecipientType.TO, adds);
        }

        if (getCcAddress() != null && !getCcAddress().trim().equals("")) {
            Address[] ccadds = InternetAddress.parse(getCcAddress());
            for (int i = 0; i < ccadds.length; i++) {
                System.out.println(" mail address " + ccadds[i].toString());
            }
            mimeMsg.addRecipients(javax.mail.Message.RecipientType.CC,
                                  ccadds);
        }

        mimeMsg.saveChanges();

        return mimeMsg;

    }

    public String getCcAddress() {
        return ccAddress;
    }

    public String getContent() {
        if (content == null) {
            return "";
        } else {
            return content;
        }
    }

    public String getMailServerAddress() {
        if (mailServerAddress == null) {
            mailServerAddress = defaultConfig.getValue("Mail_Server");
        }
        return mailServerAddress;
    }

    public String getSenderId() {
        if (senderId == null) {
            senderId = defaultConfig.getValue("Essp_Id");
        }
        return senderId;
    }

    public String getSenderPassword() {
        if (senderPassword == null) {
            senderPassword = defaultConfig.getValue("Essp_Password");
        }
        return senderPassword;
    }

    public String getTitle() {
        if (title == null) {
            return "";
        } else {
            return title;
        }
    }

    public String getToAddress() {
        return toAddress;
    }

    public String getIsSmtp() {
        if (isSmtp == null) {
            isSmtp = defaultConfig.getValue("Mail_SMTP");
        }
        return isSmtp;
    }

    public boolean isHtml() {
        return isHtml;
    }

    public void setCcAddress(String ccAddress) {
        //如果配置文件中有调试用的抄送MAIL地址的话，就会将抄送MAIL发到调试用抄送邮箱中，否则发到真实的抄送邮箱中
        //Modify By:Robin.Zhang
        String debug_ccAddress = "";
        debug_ccAddress = defaultConfig.getValue("Debug_ccEmail");
        if (debug_ccAddress == null || debug_ccAddress.equals("")) {
            this.ccAddress = ccAddress;
        } else {
            this.ccAddress = debug_ccAddress;
        }
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setIsSmtp(String isSmtp) {
        this.isSmtp = isSmtp;
    }

    public void setMailServerAddress(String mailServerAddress) {
        this.mailServerAddress = mailServerAddress;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public void setSenderPassword(String senderPassword) {
        this.senderPassword = senderPassword;
    }

    public void setTitle(String title) {
        //如果配置文件中有调试用的抄送MAIL地址的话，就会在邮件TITLE后加入"(调试邮件)"
        //Modify By:Robin.Zhang
        String debug_address = "";
        debug_address = defaultConfig.getValue("Debug_Email");
        if (debug_address == null || debug_address.equals("")) {
            this.title = title;
        } else {
            this.title = title + "(调试邮件)";
        }
    }

    public void setToAddress(String toAddress) {
        //如果配置文件中有调试用的MAIL地址的话，就会发到调试邮箱中，否则发到真实的邮箱中
        //Modify By:Robin.Zhang
        String address = "";
        address = defaultConfig.getValue("Debug_Email");
        if (address == null || address.equals("")) {
            this.toAddress = toAddress;
        } else {
            this.toAddress = address;
        }
    }

    public void setIsHtml(boolean isHtml) {
        this.isHtml = isHtml;
    }

    /**
     * 设置邮件要发送的附件,Key:作为附件的文件名,Value:要发送附件的File对象
     * @param attachmentFiles Map
     */
    public void setAttachmentFiles(Map attachmentFiles) {
        this.attachmentFiles = attachmentFiles;
    }

    public static void main(String[] args) {
        LgMailSend logic = new LgMailSend();
        logic.setContent("<html>窝窝瓦</html>");
//      logic.setSenderId("essp");
//      logic.setSenderPassword("essp@654321!");
        logic.setTitle("新邮件");
        //logic.setToAddress("huaxiao@wistronits.com");
        logic.setToAddress("mingxingzhang@wistronits.com");
        logic.setFrom("fastzch@163.com");
//        File att = new File(
//                "F:\\essp\\essp2\\issue\\attachmentFiles\\ISSUE\\002645W\\F00000039.txt");
        Map atts = new HashMap();
        atts.put("home.html", "http://www.baidu.com/home.html");
        logic.setAttachmentFiles(atts);
//        logic.setIsHtml(true);
        logic.send();
    }
}
