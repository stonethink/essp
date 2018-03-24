package db.essp.issue;

import java.io.*;
import java.util.*;

import com.wits.util.*;
import org.apache.commons.lang.builder.*;
import server.essp.issue.issue.sendmail.util.*;

/**
 *        @hibernate.class
 *         table="ISSUE_DISCUSS_REPLAY"
 *
*/
public class IssueDiscussReply implements Serializable {

    /** identifier field */
    private Long rid;

    /** nullable persistent field */
    private String title;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private Date filledDate;

    /** nullable persistent field */
    private String filledBy;

    /** nullable persistent field */
    private String attachment;

    /** nullable persistent field */
    private String attachmentId;

    /** nullable persistent field */
    private String attachmentDesc;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    private IssueDiscussTitle issueDiscussTitle;

    /** nullable persistent field */
    private String to;

    /** nullable persistent field */
    private String cc;
    private String sendremark;
    private String remark;
    private String filledByScope;

    /** full constructor */
    public IssueDiscussReply(Long rid, String title, String description, Date filledDate, String filledBy, String attachment, String attachmentDesc, String rst, Date rct, Date rut,String to,String cc,String sendremark,String remark) {
        this.rid = rid;
        this.title = title;
        this.description = description;
        this.filledDate = filledDate;
        this.filledBy = filledBy;
        this.attachment = attachment;
        this.attachmentDesc = attachmentDesc;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
        this.to = to;
        this.cc = cc;
        this.sendremark = sendremark;
        this.remark = remark;
    }

    /** default constructor */
    public IssueDiscussReply() {
    }

    /** minimal constructor */
    public IssueDiscussReply(Long rid) {
        this.rid = rid;
    }

    /**
     *            @hibernate.id
     *             generator-class="assigned"
     *             type="java.lang.Long"
     *             column="RID"
     *
     */
    public Long getRid() {
        return this.rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    /**
     *            @hibernate.property
     *             column="TITLE"
     *             length="100"
     *
     */
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *            @hibernate.property
     *             column="DESCRIPTION"
     *             length="500"
     *
     */
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *            @hibernate.property
     *             column="FILLED_DATE"
     *             length="20"
     *
     */
    public Date getFilledDate() {
        return this.filledDate;
    }

    public void setFilledDate(Date filledDate) {
        this.filledDate = filledDate;
    }

    /**
     *            @hibernate.property
     *             column="REPLY_BY"
     *             length="50"
     *
     */
    public String getFilledBy() {
        return this.filledBy;
    }

    public void setFilledBy(String filledBy) {
        this.filledBy = filledBy;
    }

    /**
     *            @hibernate.property
     *             column="ATTACHMENT"
     *             length="100"
     *
     */
    public String getAttachment() {
        return this.attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    /**
     *            @hibernate.property
     *             column="ATTACHMENT_DESC"
     *             length="500"
     *
     */
    public String getAttachmentDesc() {
        return this.attachmentDesc;
    }

    public void setAttachmentDesc(String attachmentDesc) {
        this.attachmentDesc = attachmentDesc;
    }

    /**
     *            @hibernate.property
     *             column="RST"
     *             length="1"
     *
     */
    public String getRst() {
        return this.rst;
    }

    public void setRst(String rst) {
        this.rst = rst;
    }

    /**
     *            @hibernate.property
     *             column="RCT"
     *             length="20"
     *
     */
    public Date getRct() {
        return this.rct;
    }

    public void setRct(Date rct) {
        this.rct = rct;
    }

    /**
     *            @hibernate.property
     *             column="RUT"
     *             length="20"
     *
     */
    public Date getRut() {
        return this.rut;
    }

    public IssueDiscussTitle getIssueDiscussTitle() {
        return issueDiscussTitle;
    }

    public String getAttachmentId() {
        return attachmentId;
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

    public void setRut(Date rut) {
        this.rut = rut;
    }

    public void setIssueDiscussTitle(IssueDiscussTitle issueDiscussTitle) {
        this.issueDiscussTitle = issueDiscussTitle;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
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

    public String returnFilledData(){
        return comDate.dateToString(this.getFilledDate());
    }

    public String returnHtmlDescription() {
        return HanderUtil.convertToHtmlFormatString(this.getDescription());
    }
    public String returnHtmlRemark() {
        return HanderUtil.convertToHtmlFormatString(this.getRemark());
    }


    public String toString() {
        return new ToStringBuilder(this)
            .append("rid", getRid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof IssueDiscussReply) ) return false;
        IssueDiscussReply castOther = (IssueDiscussReply) other;
        return new EqualsBuilder()
            .append(this.getRid(), castOther.getRid())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getRid())
            .toHashCode();
    }

}
