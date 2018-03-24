package db.essp.pms;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class LaborResourceDetail implements Serializable {

    /** identifier field */
    private Long rid;

    /** nullable persistent field */
    private Date beginDate;

    /** nullable persistent field */
    private Date endDate;

    /** nullable persistent field */
    private Long acntRid;

    /** nullable persistent field */
    private String loginId;

    /** nullable persistent field */
    private Long seq;

    /** nullable persistent field */
    private Long percent;

    /** nullable persistent field */
    private Double hour;

    /** persistent field */
    private db.essp.pms.LaborResource laborResource;

    /** full constructor */
    public LaborResourceDetail(Long rid, Date beginDate, Date endDate, Long acntRid, String loginId, Long seq, Long percent, db.essp.pms.LaborResource laborResource) {
        this.rid = rid;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.acntRid = acntRid;
        this.loginId = loginId;
        this.seq = seq;
        this.percent = percent;
        this.laborResource = laborResource;
    }

    /** default constructor */
    public LaborResourceDetail() {
    }

    /** minimal constructor */
    public LaborResourceDetail(Long rid, db.essp.pms.LaborResource laborResource) {
        this.rid = rid;
        this.laborResource = laborResource;
    }

    public Long getRid() {
        return this.rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public Date getBeginDate() {
        return this.beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getAcntRid() {
        return this.acntRid;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public String getLoginId() {
        return this.loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public Long getSeq() {
        return this.seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public Long getPercent() {
        return this.percent;
    }

    public void setPercent(Long percent) {
        this.percent = percent;
    }

    public Double getHour() {
        return this.hour;
    }

    public void setHour(Double hour) {
        this.hour = hour;
    }

    public db.essp.pms.LaborResource getLaborResource() {
        return this.laborResource;
    }

    public void setLaborResource(db.essp.pms.LaborResource laborResource) {
        this.laborResource = laborResource;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("rid", getRid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof LaborResourceDetail) ) return false;
        LaborResourceDetail castOther = (LaborResourceDetail) other;
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
