package server.essp.issue.issue.discuss.form;

import org.apache.struts.action.*;
import org.apache.struts.upload.*;
public class AfIssueDiscussReply extends ActionForm {
  private String rid="";
  private String title="";
  private String titleId="";
  private String filledDate="";
  private String filledBy;
  private FormFile attachment;
  private String attachmentDesc="";
  private String description="";
  private String rst="";
  private String rct="";
  private String rut="";
  private String to="";
  private String cc="";
    private String sendremark="";
    private String remark="";
    private String filledByScope;

    public FormFile getAttachment() {
        return attachment;
    }

    public String getAttachmentDesc() {
        return attachmentDesc;
    }

    public String getFilledBy() {
        return filledBy;
    }

    public String getDescription() {
        return description;
    }

    public String getFilledDate() {
        return filledDate;
    }

    public String getRct() {
        return rct;
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

    public String getTitleId() {
        return titleId;
    }

    public String getTo() {
        return to;
    }

    public String getCc() {
        return cc;
    }

    public String getSendremark() {
        return sendremark;
    }

    public String getRemark() {
        return remark;
    }

    public String getFilledByScope() {
        return filledByScope;
    }

    public void setTitleId(String title_id) {
        this.titleId = title_id;
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

    public void setRct(String rct) {
        this.rct = rct;
    }

    public void setFilledDate(String filled_date) {
        this.filledDate = filled_date;
    }

    public void setFilledBy(String filled_by) {
        this.filledBy = filled_by;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAttachmentDesc(String attachment_desc) {
        this.attachmentDesc = attachment_desc;
    }

    public void setAttachment(FormFile attachment) {
        this.attachment = attachment;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public void setSendremark(String sendremark) {
        this.sendremark = sendremark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setFilledByScope(String filledByScope) {
        this.filledByScope = filledByScope;
    }

}
