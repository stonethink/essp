package c2s.essp.common.mail;

public class DtoMail {
    public final static String MAIL_KEY = "mailKey";

    /**发件人帐号*/
    private String senderId;

    /**发件人密码*/
    private String senderPassword;

    /**收件人地址，当有多个时用","逗号隔开,收件人不能为空或null*/
    private String toAddress;
    private String toUserId;

    /**cc人地址，当有多个时用","逗号隔开,可以为空或null*/
    private String ccAddress;
    private String ccUserId;

    /**固定开头*/
    private String beginText;

    /**固定结尾*/
    private String endText;

    /**内容*/
    private String content;

    /**标题*/
    private String title;

    /**配置文件名*/
    private String configFile;

    public String getBeginText() {
        return beginText;
    }

    public String getCcAddress() {
        return ccAddress;
    }

    public String getEndText() {
        return endText;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getSenderPassword() {
        return senderPassword;
    }

    public String getToAddress() {
        return toAddress;
    }

    public String getConfigFile() {
        return configFile;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public String getCcUserId() {
        return ccUserId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public void setSenderPassword(String senderPassword) {
        this.senderPassword = senderPassword;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public void setEndText(String endText) {
        this.endText = endText;
    }

    public void setCcAddress(String ccAddress) {
        this.ccAddress = ccAddress;
    }

    public void setBeginText(String beginText) {
        this.beginText = beginText;
    }

    public void setConfigFile(String configFile) {
        this.configFile = configFile;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCcUserId(String ccUserId) {
        this.ccUserId = ccUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }
}
