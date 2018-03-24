package essp.tables;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *        @hibernate.class
 *         table="cm_visit_interview"
 *
*/
public class CmVisitInterview implements Serializable {

    /** identifier field */
    private Long rid;

    /** persistent field */
    private Long projectid;

    /** nullable persistent field */
    private Date visitDate;

    /** nullable persistent field */
    private String visitor;

    /** nullable persistent field */
    private String interviewee;

    /** nullable persistent field */
    private Long satisfactionDegree;

    /** nullable persistent field */
    private String suggestions;

    /** nullable persistent field */
    private String filledBy;

    /** nullable persistent field */
    private Date filledDate;

    /** nullable persistent field */
    private String feedback;

    /** nullable persistent field */
    private String feedbackBy;

    /** nullable persistent field */
    private Date feedbackDate;

    /** nullable persistent field */

    /** nullable persistent field */
    private String reportStatus;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** nullable persistent field */
    private String rst;
    private String contactMethod;

    /** full constructor */
    public CmVisitInterview(Long rid, Long projectid, Date visitDate, String visitor, String interviewee, Long satisfactionDegree, String suggestions, String filledBy, Date filledDate, String feedback, String feedbackBy, Date feedbackDate, String contactMethod, String reportStatus, Date rct, Date rut, String rst) {
        this.rid = rid;
        this.projectid = projectid;
        this.visitDate = visitDate;
        this.visitor = visitor;
        this.interviewee = interviewee;
        this.satisfactionDegree = satisfactionDegree;
        this.suggestions = suggestions;
        this.filledBy = filledBy;
        this.filledDate = filledDate;
        this.feedback = feedback;
        this.feedbackBy = feedbackBy;
        this.feedbackDate = feedbackDate;
        this.contactMethod = contactMethod;
        this.reportStatus = reportStatus;
        this.rct = rct;
        this.rut = rut;
        this.rst = rst;
    }

    /** default constructor */
    public CmVisitInterview() {
    }

    /** minimal constructor */
    public CmVisitInterview(Long rid, Long projectid) {
        this.rid = rid;
        this.projectid = projectid;
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
     *             column="VISIT_DATE"
     *             length="10"
     *
     */
    public Date getVisitDate() {
        return this.visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    /**
     *            @hibernate.property
     *             column="VISITOR"
     *             length="50"
     *
     */
    public String getVisitor() {
        return this.visitor;
    }

    public void setVisitor(String visitor) {
        this.visitor = visitor;
    }

    /**
     *            @hibernate.property
     *             column="INTERVIEWEE"
     *             length="50"
     *
     */
    public String getInterviewee() {
        return this.interviewee;
    }

    public void setInterviewee(String interviewee) {
        this.interviewee = interviewee;
    }

    /**
     *            @hibernate.property
     *             column="SATISFACTION_DEGREE"
     *             length="8"
     *
     */
    public Long getSatisfactionDegree() {
        return this.satisfactionDegree;
    }

    public void setSatisfactionDegree(Long satisfactionDegree) {
        this.satisfactionDegree = satisfactionDegree;
    }

    /**
     *            @hibernate.property
     *             column="SUGGESTIONS"
     *             length="65535"
     *
     */
    public String getSuggestions() {
        return this.suggestions;
    }

    public void setSuggestions(String suggestions) {
        this.suggestions = suggestions;
    }

    /**
     *            @hibernate.property
     *             column="FILLED_BY"
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
     *             column="FILLED_DATE"
     *             length="10"
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
     *             column="FEEDBACK"
     *             length="65535"
     *
     */
    public String getFeedback() {
        return this.feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    /**
     *            @hibernate.property
     *             column="FEEDBACK_BY"
     *             length="50"
     *
     */
    public String getFeedbackBy() {
        return this.feedbackBy;
    }

    public void setFeedbackBy(String feedbackBy) {
        this.feedbackBy = feedbackBy;
    }

    /**
     *            @hibernate.property
     *             column="FEEDBACK_DATE"
     *             length="10"
     *
     */
    public Date getFeedbackDate() {
        return this.feedbackDate;
    }

    public void setFeedbackDate(Date feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    /**
     *            @hibernate.property
     *             column="CONTACT_METHOD"
     *             length="8"
     *
     */
    public String getContactMethod() {
        return this.contactMethod;
    }

    public void setContactMethod(String contactMethod) {
        this.contactMethod = contactMethod;
    }

    /**
     *            @hibernate.property
     *             column="REPORT_STATUS"
     *             length="1"
     *
     */
    public String getReportStatus() {
        return this.reportStatus;
    }

    public void setReportStatus(String reportStatus) {
        this.reportStatus = reportStatus;
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
        if ( !(other instanceof CmVisitInterview) ) return false;
        CmVisitInterview castOther = (CmVisitInterview) other;
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
