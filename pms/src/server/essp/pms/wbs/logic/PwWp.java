package server.essp.pms.wbs.logic;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *        @hibernate.class
 *         table="pw_wp"
 *
*/
public class PwWp implements Serializable {

    /** identifier field */
    private Long rid;

    /** persistent field */
    private Long projectId;

    /** nullable persistent field */
    private Long activityId;

    /** nullable persistent field */
    private int wpSequence;

    /** persistent field */
    private String wpPwporwp;

    /** persistent field */
    private String wpCode;

    /** nullable persistent field */
    private String wpName;

    /** nullable persistent field */
    private String wpType;

    /** nullable persistent field */
    private String wpAssignby;

    /** nullable persistent field */
    private Date wpAssigndate;

    /** persistent field */
    private String wpWorker;

    /** nullable persistent field */
    private BigDecimal wpReqWkhr;

    /** nullable persistent field */
    private BigDecimal wpPlanWkhr;

    /** nullable persistent field */
    private BigDecimal wpActWkhr;

    /** nullable persistent field */
    private Date wpPlanStart;

    /** nullable persistent field */
    private Date wpPlanFihish;

    /** nullable persistent field */
    private Date wpActStart;

    /** nullable persistent field */
    private Date wpActFinish;

    /** nullable persistent field */
    private String wpStatus;

    /** nullable persistent field */
    private String wpCmpltrateType;

    /** nullable persistent field */
    private BigDecimal wpCmpltrate;

    /** nullable persistent field */
    private String wpRequirement;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** default constructor */
    public PwWp() {
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
     *             column="PROJECT_ID"
     *             length="8"
     *             not-null="true"
     *
     */
    public Long getProjectId() {
        return this.projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    /**
     *            @hibernate.property
     *             column="ACTIVITY_ID"
     *             length="8"
     *
     */
    public Long getActivityId() {
        return this.activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    /**
     *            @hibernate.property
     *             column="WP_SEQUENCE"
     *             length="8"
     *
     */
    public int getWpSequence() {
        return this.wpSequence;
    }

    public void setWpSequence(int wpSequence) {
        this.wpSequence = wpSequence;
    }

    /**
     *            @hibernate.property
     *             column="WP_PWPORWP"
     *             length="1"
     *             not-null="true"
     *
     */
    public String getWpPwporwp() {
        return this.wpPwporwp;
    }

    public void setWpPwporwp(String wpPwporwp) {
        this.wpPwporwp = wpPwporwp;
    }

    /**
     *            @hibernate.property
     *             column="WP_CODE"
     *             length="50"
     *             not-null="true"
     *
     */
    public String getWpCode() {
        return this.wpCode;
    }

    public void setWpCode(String wpCode) {
        this.wpCode = wpCode;
    }

    /**
     *            @hibernate.property
     *             column="WP_NAME"
     *             length="50"
     *
     */
    public String getWpName() {
        return this.wpName;
    }

    public void setWpName(String wpName) {
        this.wpName = wpName;
    }

    /**
     *            @hibernate.property
     *             column="WP_TYPE"
     *             length="50"
     *
     */
    public String getWpType() {
        return this.wpType;
    }

    public void setWpType(String wpType) {
        this.wpType = wpType;
    }

    /**
     *            @hibernate.property
     *             column="WP_ASSIGNBY"
     *             length="50"
     *
     */
    public String getWpAssignby() {
        return this.wpAssignby;
    }

    public void setWpAssignby(String wpAssignby) {
        this.wpAssignby = wpAssignby;
    }

    /**
     *            @hibernate.property
     *             column="WP_ASSIGNDATE"
     *             length="10"
     *
     */
    public Date getWpAssigndate() {
        return this.wpAssigndate;
    }

    public void setWpAssigndate(Date wpAssigndate) {
        this.wpAssigndate = wpAssigndate;
    }

    /**
     *            @hibernate.property
     *             column="WP_WORKER"
     *             length="65535"
     *             not-null="true"
     *
     */
    public String getWpWorker() {
        return this.wpWorker;
    }

    public void setWpWorker(String wpWorker) {
        this.wpWorker = wpWorker;
    }

    /**
     *            @hibernate.property
     *             column="WP_REQ_WKHR"
     *             length="8"
     *
     */
    public BigDecimal getWpReqWkhr() {
        return this.wpReqWkhr;
    }

    public void setWpReqWkhr(BigDecimal wpReqWkhr) {
        this.wpReqWkhr = wpReqWkhr;
    }

    /**
     *            @hibernate.property
     *             column="WP_PLAN_WKHR"
     *             length="8"
     *
     */
    public BigDecimal getWpPlanWkhr() {
        return this.wpPlanWkhr;
    }

    public void setWpPlanWkhr(BigDecimal wpPlanWkhr) {
        this.wpPlanWkhr = wpPlanWkhr;
    }

    /**
     *            @hibernate.property
     *             column="WP_ACT_WKHR"
     *             length="8"
     *
     */
    public BigDecimal getWpActWkhr() {
        return this.wpActWkhr;
    }

    public void setWpActWkhr(BigDecimal wpActWkhr) {
        this.wpActWkhr = wpActWkhr;
    }

    /**
     *            @hibernate.property
     *             column="WP_PLAN_START"
     *             length="10"
     *
     */
    public Date getWpPlanStart() {
        return this.wpPlanStart;
    }

    public void setWpPlanStart(Date wpPlanStart) {
        this.wpPlanStart = wpPlanStart;
    }

    /**
     *            @hibernate.property
     *             column="WP_PLAN_FIHISH"
     *             length="10"
     *
     */
    public Date getWpPlanFihish() {
        return this.wpPlanFihish;
    }

    public void setWpPlanFihish(Date wpPlanFihish) {
        this.wpPlanFihish = wpPlanFihish;
    }

    /**
     *            @hibernate.property
     *             column="WP_ACT_START"
     *             length="10"
     *
     */
    public Date getWpActStart() {
        return this.wpActStart;
    }

    public void setWpActStart(Date wpActStart) {
        this.wpActStart = wpActStart;
    }

    /**
     *            @hibernate.property
     *             column="WP_ACT_FINISH"
     *             length="10"
     *
     */
    public Date getWpActFinish() {
        return this.wpActFinish;
    }

    public void setWpActFinish(Date wpActFinish) {
        this.wpActFinish = wpActFinish;
    }

    /**
     *            @hibernate.property
     *             column="WP_STATUS"
     *             length="50"
     *
     */
    public String getWpStatus() {
        return this.wpStatus;
    }

    public void setWpStatus(String wpStatus) {
        this.wpStatus = wpStatus;
    }

    /**
     *            @hibernate.property
     *             column="WP_CMPLTRATE_TYPE"
     *             length="1"
     *
     */
    public String getWpCmpltrateType() {
        return this.wpCmpltrateType;
    }

    public void setWpCmpltrateType(String wpCmpltrateType) {
        this.wpCmpltrateType = wpCmpltrateType;
    }

    /**
     *            @hibernate.property
     *             column="WP_CMPLTRATE"
     *             length="8"
     *
     */
    public BigDecimal getWpCmpltrate() {
        return this.wpCmpltrate;
    }

    public void setWpCmpltrate(BigDecimal wpCmpltrate) {
        this.wpCmpltrate = wpCmpltrate;
    }

    /**
     *            @hibernate.property
     *             column="WP_REQUIREMENT"
     *             length="65535"
     *
     */
    public String getWpRequirement() {
        return this.wpRequirement;
    }

    public void setWpRequirement(String wpRequirement) {
        this.wpRequirement = wpRequirement;
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

    public String toString() {
        return new ToStringBuilder(this)
            .append("rid", getRid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof PwWp) ) return false;
        PwWp castOther = (PwWp) other;
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
