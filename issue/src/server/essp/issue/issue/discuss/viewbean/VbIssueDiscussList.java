package server.essp.issue.issue.discuss.viewbean;

import java.util.*;

public class VbIssueDiscussList {

      private String rid="";
      private String title="";
      private String issueRid="";
      private String filledDate="";
      private String filledBy="";
      private String attachment="";
      private String attachmentDesc="";
      private String description="";
      private String rst="";
      private String rct="";
      private String rut="";

   private List replys=new ArrayList();
    private String filledByScope;
    public String getAttachment() {
        return attachment;
    }

    public String getAttachmentDesc() {
        return attachmentDesc;
    }

    public String getDescription() {
        return description;
    }

    public String getFilledBy() {
        return filledBy;
    }

    public String getFilledDate() {
        return filledDate;
    }

    public String getIssueRid() {
        return issueRid;
    }

    public String getRct() {
        return rct;
    }

    public List getReplys() {
        return replys;
    }

    public String getRid() {
        return rid;
    }

    public String getRst() {
        return rst;
    }

    public String getRut() {
        return rut;
    }

    public String getTitle() {
        return title;
    }

    public String getFilledByScope() {
        return filledByScope;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public void setRst(String rst) {
        this.rst = rst;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public void setReplys(List replys) {
        this.replys = replys;
    }

    public void setRct(String rct) {
        this.rct = rct;
    }

    public void setIssueRid(String issueRid) {
        this.issueRid = issueRid;
    }

    public void setFilledDate(String filledDate) {
        this.filledDate = filledDate;
    }

    public void setFilledBy(String filledBy) {
        this.filledBy = filledBy;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAttachmentDesc(String attachmentDesc) {
        this.attachmentDesc = attachmentDesc;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public void setFilledByScope(String filledByScope) {
        this.filledByScope = filledByScope;
    }

}
