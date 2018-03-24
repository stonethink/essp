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
 * <p>Description: �����ʼ�����</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Enovation</p>
 * @author Xuxi.Chen
 * @version 1.0
 */

public class LgMailSend {
    /**�ʼ���������ַ,����"10.5.2.253",���뵫��Ĭ��ֵ*/
    private String mailServerAddress;

    /**�Ƿ�ʹ��smtpЭ��,���뵫��Ĭ��ֵ*/
    private String isSmtp;

    /**�������ʺ�,����*/
    private String senderId;

    /**����������,����*/
    private String senderPassword;

    /**�ռ��˵�ַ�����ж��ʱ��","���Ÿ���,����Ϊ�ջ�null*/
    private String toAddress;

    /**cc�˵�ַ�����ж��ʱ��","���Ÿ���,����Ϊ�ջ�null*/
    private String ccAddress;

    /**����,����Ϊ��*/
    private String title;
    /**����,����Ϊ��*/
    private String content;
    /**�Ƿ�ΪHtml��ʽ���ʼ�**/
    private boolean isHtml = false;
    /**�ʼ����͵ĸ���,Key:��Ϊ�������ļ���,Value:Ҫ���͸�����File����**/
    private Map attachmentFiles = null;
    /**��Ĭ�ϵ��ʼ���������ַ��Э������*/
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
            //��չΪ���Ըı䷢���˵�ַ
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

    //�����ʼ���Title,���ݼ��ռ�������һ��MimeMessage
    private MimeMessage getMimeMessage(javax.mail.Session mailSession) throws
        MessagingException {
        MimeMessage mimeMsg = new MimeMessage(mailSession);
        mimeMsg.setSubject(getTitle());
        mimeMsg.setFrom(new InternetAddress(getSenderId()));
        String msgBodys = getContent();
        //����Html�ʼ�����������ʼ�
        if (isHtml || (attachmentFiles != null && attachmentFiles.size() > 0)) {
            MimeMultipart multipart = new MimeMultipart();
            //�����ʼ�����
            BodyPart msgBodyPart = new MimeBodyPart();
            msgBodyPart.setContent(msgBodys, "text/html;charset=utf-8");
            multipart.addBodyPart(msgBodyPart);
            //���ø�������
            if (attachmentFiles != null && attachmentFiles.size() > 0) {
                Set attachmentNames = attachmentFiles.keySet();
                for (Iterator it = attachmentNames.iterator(); it.hasNext(); ) {
                    String attachmentName = (String) it.next();
                    Object file = attachmentFiles.get(attachmentName);
//                    if (!(file instanceof File)) {
//                        throw new BusinessException("E0001",
//                                "The attachment is not File object ");
//                    }
                    //��������չΪ���Է��ļ���Զ���ļ�
                    //Զ���ļ���������URI��ʾ(����ַ��httpͷ)
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
                        //���������ַ��ĸ���
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
                        //��������ڲ��Ի��������͸���
                        String address = defaultConfig.getValue("Debug_Email");
                        if (address == null || address.equals("")) {
                            multipart.addBodyPart(attachBodyPart);
                        }

                    } else { //�������͵ĸ�����֧��,javaMailҲ��֧��
                        throw new BusinessException("E001",
                            "attachments(HashMap)'s value is not URI or File");
                    }

                }
            }
//            attachBodyPart.setDataHandler();

            mimeMsg.setContent(multipart);
        }
        //������ͨ�ı��ʼ�
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
        //��������ļ����е����õĳ���MAIL��ַ�Ļ����ͻὫ����MAIL���������ó��������У����򷢵���ʵ�ĳ���������
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
        //��������ļ����е����õĳ���MAIL��ַ�Ļ����ͻ����ʼ�TITLE�����"(�����ʼ�)"
        //Modify By:Robin.Zhang
        String debug_address = "";
        debug_address = defaultConfig.getValue("Debug_Email");
        if (debug_address == null || debug_address.equals("")) {
            this.title = title;
        } else {
            this.title = title + "(�����ʼ�)";
        }
    }

    public void setToAddress(String toAddress) {
        //��������ļ����е����õ�MAIL��ַ�Ļ����ͻᷢ�����������У����򷢵���ʵ��������
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
     * �����ʼ�Ҫ���͵ĸ���,Key:��Ϊ�������ļ���,Value:Ҫ���͸�����File����
     * @param attachmentFiles Map
     */
    public void setAttachmentFiles(Map attachmentFiles) {
        this.attachmentFiles = attachmentFiles;
    }

    public static void main(String[] args) {
        LgMailSend logic = new LgMailSend();
        logic.setContent("<html>������</html>");
//      logic.setSenderId("essp");
//      logic.setSenderPassword("essp@654321!");
        logic.setTitle("���ʼ�");
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
