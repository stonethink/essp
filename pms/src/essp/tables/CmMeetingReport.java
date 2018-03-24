package essp.tables;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="cm_meeting_report"
 *     
*/
public class CmMeetingReport implements Serializable {

    /** identifier field */
    private Long rid;

    /** persistent field */
    private Long projectid;

    /** persistent field */
    private Date meetingDate;

    /** persistent field */
    private Date meetingTime;

    /** nullable persistent field */
    private String meetingTopic;

    /** nullable persistent field */
    private String meetingSponsor;

    /** nullable persistent field */
    private String meetingPrincipal;

    /** nullable persistent field */
    private String meetingParticipator;

    /** nullable persistent field */
    private String meetingDescription;

    /** nullable persistent field */
    private String meetingAttachmentName;

    /** nullable persistent field */
    private String meetingAttachmentUrl;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** nullable persistent field */
    private String rst;

    /** full constructor */
    public CmMeetingReport(Long rid, Long projectid, Date meetingDate, Date meetingTime, String meetingTopic, String meetingSponsor, String meetingPrincipal, String meetingParticipator, String meetingDescription, String meetingAttachmentName, String meetingAttachmentUrl, Date rct, Date rut, String rst) {
        this.rid = rid;
        this.projectid = projectid;
        this.meetingDate = meetingDate;
        this.meetingTime = meetingTime;
        this.meetingTopic = meetingTopic;
        this.meetingSponsor = meetingSponsor;
        this.meetingPrincipal = meetingPrincipal;
        this.meetingParticipator = meetingParticipator;
        this.meetingDescription = meetingDescription;
        this.meetingAttachmentName = meetingAttachmentName;
        this.meetingAttachmentUrl = meetingAttachmentUrl;
        this.rct = rct;
        this.rut = rut;
        this.rst = rst;
    }

    /** default constructor */
    public CmMeetingReport() {
    }

    /** minimal constructor */
    public CmMeetingReport(Long rid, Long projectid, Date meetingDate, Date meetingTime) {
        this.rid = rid;
        this.projectid = projectid;
        this.meetingDate = meetingDate;
        this.meetingTime = meetingTime;
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
     *             column="PROJECTID"
     *             length="8"
     *             not-null="true"
     *         
     */
    public Long getProjectid() {
        return this.projectid;
    }

    public void setProjectid(Long projectid) {
        this.projectid = projectid;
    }

    /** 
     *            @hibernate.property
     *             column="MEETING_DATE"
     *             length="10"
     *             not-null="true"
     *         
     */
    public Date getMeetingDate() {
        return this.meetingDate;
    }

    public void setMeetingDate(Date meetingDate) {
        this.meetingDate = meetingDate;
    }

    /** 
     *            @hibernate.property
     *             column="MEETING_TIME"
     *             length="10"
     *             not-null="true"
     *         
     */
    public Date getMeetingTime() {
        return this.meetingTime;
    }

    public void setMeetingTime(Date meetingTime) {
        this.meetingTime = meetingTime;
    }

    /** 
     *            @hibernate.property
     *             column="MEETING_TOPIC"
     *             length="65535"
     *         
     */
    public String getMeetingTopic() {
        return this.meetingTopic;
    }

    public void setMeetingTopic(String meetingTopic) {
        this.meetingTopic = meetingTopic;
    }

    /** 
     *            @hibernate.property
     *             column="MEETING_SPONSOR"
     *             length="50"
     *         
     */
    public String getMeetingSponsor() {
        return this.meetingSponsor;
    }

    public void setMeetingSponsor(String meetingSponsor) {
        this.meetingSponsor = meetingSponsor;
    }

    /** 
     *            @hibernate.property
     *             column="MEETING_PRINCIPAL"
     *             length="50"
     *         
     */
    public String getMeetingPrincipal() {
        return this.meetingPrincipal;
    }

    public void setMeetingPrincipal(String meetingPrincipal) {
        this.meetingPrincipal = meetingPrincipal;
    }

    /** 
     *            @hibernate.property
     *             column="MEETING_PARTICIPATOR"
     *             length="65535"
     *         
     */
    public String getMeetingParticipator() {
        return this.meetingParticipator;
    }

    public void setMeetingParticipator(String meetingParticipator) {
        this.meetingParticipator = meetingParticipator;
    }

    /** 
     *            @hibernate.property
     *             column="MEETING_DESCRIPTION"
     *             length="65535"
     *         
     */
    public String getMeetingDescription() {
        return this.meetingDescription;
    }

    public void setMeetingDescription(String meetingDescription) {
        this.meetingDescription = meetingDescription;
    }

    /** 
     *            @hibernate.property
     *             column="MEETING_ATTACHMENT_NAME"
     *             length="200"
     *         
     */
    public String getMeetingAttachmentName() {
        return this.meetingAttachmentName;
    }

    public void setMeetingAttachmentName(String meetingAttachmentName) {
        this.meetingAttachmentName = meetingAttachmentName;
    }

    /** 
     *            @hibernate.property
     *             column="MEETING_ATTACHMENT_URL"
     *             length="200"
     *         
     */
    public String getMeetingAttachmentUrl() {
        return this.meetingAttachmentUrl;
    }

    public void setMeetingAttachmentUrl(String meetingAttachmentUrl) {
        this.meetingAttachmentUrl = meetingAttachmentUrl;
    }

    /** 
     *            @hibernate.property
     *             column="RCT"
     *             length="10"
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
     *             length="10"
     *         
     */
    public Date getRut() {
        return this.rut;
    }

    public void setRut(Date rut) {
        this.rut = rut;
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

    public String toString() {
        return new ToStringBuilder(this)
            .append("rid", getRid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof CmMeetingReport) ) return false;
        CmMeetingReport castOther = (CmMeetingReport) other;
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
