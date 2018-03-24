package server.essp.common.mail;

import com.wits.util.Config;

/**
 * <p>Title: </p>
 * <p>Description: �����õ���Ϣ������
 * ��ȡ�����ļ��еģ�
 * Mail_User : �������ʺ� �������
 * Mail_Password : ���������� �������
 * Address : �ռ��˵�ַ
 * CC_Address  : cc��ַ
 * Title : �ż�����
 * Content : �ż�Ĭ�Ͽ�ͷ
 * BeginText : �ż�Ĭ�Ͻ�β
 * EndText : �ż�����
 * </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Enovation</p>
 * @author Xuxi.Chen
 * @version 1.0
 */

public class LgMailSendByConfig extends LgMailSend{
    private String beginText = null;
    private String endText = null;

    public LgMailSendByConfig() {
        this("EsspSystemMail");
    }

    public LgMailSendByConfig(String mailConfigFile) {
        Config con = new Config(mailConfigFile);
        setSenderId(con.getValue("Mail_User"));
        setSenderPassword(con.getValue("Mail_Password"));
        setToAddress(con.getValue("Address"));
        setCcAddress(con.getValue("CC_Address"));
        setTitle(con.getValue("Title"));
        setContent(con.getValue("Content"));

        beginText = con.getValue("BeginText");
        if( beginText == null ){
            beginText = "";
        }
        endText = con.getValue("EndText");
        if( endText == null ){
            endText = "";
        }
    }

    /**����Ĳ����Ḳ��config�ļ��еĲ���*/
    public void send(String senderId, String senderPassword,
                     String address, String ccAddress,
                     String title, String content) {
        if( senderId != null )   setSenderId(senderId);
        if( senderPassword != null )   setSenderPassword(senderPassword);
        if( address != null )   setToAddress(address);
        if( ccAddress != null )   setCcAddress(ccAddress);
        if( title != null )   setTitle(title);
        if( content != null )   setContent(content);
        send();
    }

    public String getContent() {
        String content = super.getContent();
        return beginText + content + endText;
    }

    public static void main(String[] args) {
        LgMailSendByConfig logic = new LgMailSendByConfig("TestMail");
        logic.send();

    }
}
