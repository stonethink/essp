package server.essp.issue.common.form;

import java.util.HashMap;

public class MailInputDataBean {
    private Long acntRid;
    private String issueType;
    private String cardType;
    private String mailTo;
    private String cc;
    private String title;
    private HashMap attachments;
    private Object dataBean;
    private String from;
    public MailInputDataBean() {
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAttachments(HashMap attachments) {
        this.attachments = attachments;
    }

    public void setDataBean(Object dataBean) {
        this.dataBean = dataBean;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Long getAcntRid() {
        return acntRid;
    }

    public String getIssueType() {
        return issueType;
    }

    public String getCardType() {
        return cardType;
    }

    public String getMailTo() {
        return mailTo;
    }

    public String getCc() {
        return cc;
    }

    public String getTitle() {
        return title;
    }

    public HashMap getAttachments() {
        return attachments;
    }

    public Object getDataBean() {
        return dataBean;
    }

    public String getFrom() {
        return from;
    }
}
