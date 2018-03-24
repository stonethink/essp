package essp.tables;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *        @hibernate.class
 *         table="cm_satisfation_degree"
 *
*/
public class CmSatisfactionDegree implements Serializable {

    /** identifier field */
    private Long rid;

    /** persistent field */
    private Long visitId;

    /** persistent field */
    private Long satisfactionTypeId;

    /** nullable field */
    private Long satisfactionMark;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** nullable persistent field */
    private String rst;

    /** full constructor */
    public CmSatisfactionDegree(Long rid, Long visitId, Long satisfactionTypeId, Long satisfactionMark, Date rct, Date rut, String rst) {
        this.rid = rid;
        this.visitId = visitId;
        this.satisfactionTypeId = satisfactionTypeId;
        this.satisfactionMark = satisfactionMark;
        this.rct = rct;
        this.rut = rut;
        this.rst = rst;
    }

    /** default constructor */
    public CmSatisfactionDegree() {
    }

    /** minimal constructor */
    public CmSatisfactionDegree(Long rid, Long visitId, Long satisfactionTypeId, Long satisfactionMark) {
        this.rid = rid;
        this.visitId = visitId;
        this.satisfactionTypeId = satisfactionTypeId;
        this.satisfactionMark = satisfactionMark;
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
     *             column="VISIT_ID"
     *             length="8"
     *             not-null="true"
     *
     */
    public Long getVisitId() {
        return this.visitId;
    }

    public void setVisitId(Long visitId) {
        this.visitId = visitId;
    }

    /**
     *            @hibernate.property
     *             column="SATISFACTION_TYPE_ID"
     *             length="8"
     *             not-null="true"
     *
     */
    public Long getSatisfactionTypeId() {
        return this.satisfactionTypeId;
    }

    public void setSatisfactionTypeId(Long satisfactionTypeId) {
        this.satisfactionTypeId = satisfactionTypeId;
    }

    /**
     *            @hibernate.property
     *             column="SATISFACTION_MARK"
     *             length="8"
     *             not-null="true"
     *
     */
    public Long getSatisfactionMark() {
        return this.satisfactionMark;
    }

    public void setSatisfactionMark(Long satisfactionMark) {
        this.satisfactionMark = satisfactionMark;
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
        if ( !(other instanceof CmSatisfactionDegree) ) return false;
        CmSatisfactionDegree castOther = (CmSatisfactionDegree) other;
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
