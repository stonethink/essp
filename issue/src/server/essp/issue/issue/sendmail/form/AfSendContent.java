package server.essp.issue.issue.sendmail.form;

import java.util.HashMap;
import org.apache.struts.action.ActionForm;

public class AfSendContent extends ActionForm {
    private String mailTo;
    private String cc;
    private String title;
    private String content;
    private HashMap attachments;
    public AfSendContent() {
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

    public void setContent(String content) {
        this.content = content;
    }

    public void setAttachments(HashMap attachments) {
        this.attachments = attachments;
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

    public String getContent() {
        return content;
    }

    public HashMap getAttachments() {
        return attachments;
    }
}
