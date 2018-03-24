package db.essp.pms;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class AccountBaseline implements Serializable {

    /** identifier field */
    private Long rid;

    /** nullable persistent field */
    private String baselineId;

    /** nullable persistent field */
    private Date appDate;

    /** nullable persistent field */
    private String appReason;

    /** nullable persistent field */
    private Date approveDate;

    /** nullable persistent field */
    private String approveUser;

    /** nullable persistent field */
    private String appStatus;

    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** full constructor */
    public AccountBaseline(Long rid, String baselineId, Date appDate, String appReason, Date approveDate, String approveUser, String appStatus, String remark, String rst, Date rct, Date rut) {
        this.rid = rid;
        this.baselineId = baselineId;
        this.appDate = appDate;
        this.appReason = appReason;
        this.approveDate = approveDate;
        this.approveUser = approveUser;
        this.appStatus = appStatus;
        this.remark = remark;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
    }

    /** default constructor */
    public AccountBaseline() {
    }

    /** minimal constructor */
    public AccountBaseline(Long rid) {
        this.rid = rid;
    }

    public Long getRid() {
        return this.rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getBaselineId() {
        return this.baselineId;
    }

    public void setBaselineId(String baselineId) {
        this.baselineId = baselineId;
    }

    public Date getAppDate() {
        return this.appDate;
    }

    public void setAppDate(Date appDate) {
        this.appDate = appDate;
    }

    public String getAppReason() {
        return this.appReason;
    }

    public void setAppReason(String appReason) {
        this.appReason = appReason;
    }

    public Date getApproveDate() {
        return this.approveDate;
    }

    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }

    public String getApproveUser() {
        return this.approveUser;
    }

    public void setApproveUser(String approveUser) {
        this.approveUser = approveUser;
    }

    public String getAppStatus() {
        return this.appStatus;
    }

    public void setAppStatus(String appStatus) {
        this.appStatus = appStatus;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRst() {
        return this.rst;
    }

    public void setRst(String rst) {
        this.rst = rst;
    }

    public Date getRct() {
        return this.rct;
    }

    public void setRct(Date rct) {
        this.rct = rct;
    }

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
        if ( !(other instanceof AccountBaseline) ) return false;
        AccountBaseline castOther = (AccountBaseline) other;
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
