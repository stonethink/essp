package server.essp.issue.issue.sendmail.viewbean;

import java.util.HashMap;
import java.util.List;

public class VbSendMailInfo {
    private List templateList;
    private String defaultTemp;
    private String mailTo;
    private String cc;
    private String content;
    private HashMap attachments;
    private String title;
    public VbSendMailInfo() {
    }

    public void setTemplateList(List templateList) {
        this.templateList = templateList;
    }

    public void setDefaultTemp(String defaultTemp) {
        this.defaultTemp = defaultTemp;
    }

    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public void setContent(String content) {

        this.content = content;
    }

    public void setAttachments(HashMap attachments) {
        this.attachments = attachments;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List getTemplateList() {
        return templateList;
    }

    public String getDefaultTemp() {
        return defaultTemp;
    }

    public String getMailTo() {
        return mailTo;
    }

    public String getCc() {
        return cc;
    }

    public String getContent() {

        return content;
    }

    public HashMap getAttachments() {
        return attachments;
    }

    public String getTitle() {
        return title;
    }

}
