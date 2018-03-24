package server.essp.common.mail;

import com.wits.util.Config;

/**
 * <p>Title: </p>
 * <p>Description: 可配置的消息发送类
 * 读取配置文件中的：
 * Mail_User : 发件人帐号 （必需项）
 * Mail_Password : 发件人密码 （必需项）
 * Address : 收件人地址
 * CC_Address  : cc地址
 * Title : 信件标题
 * Content : 信件默认开头
 * BeginText : 信件默认结尾
 * EndText : 信件内容
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

    /**这里的参数会覆盖config文件中的参数*/
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
