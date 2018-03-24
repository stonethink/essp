package db.essp.pms;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class ActivityRelation implements Serializable {

    /** identifier field */
    private db.essp.pms.ActivityRelationPK pk;

    /** nullable persistent field */
    private String startFinishType;

    /** nullable persistent field */
    private Long delayDays;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** nullable persistent field */
    private db.essp.pms.Activity relationActivity;

    /** full constructor */
    public ActivityRelation(db.essp.pms.ActivityRelationPK pk, String startFinishType, Long delayDays, String rst, Date rct, Date rut, db.essp.pms.Activity relationActivity) {
        this.pk = pk;
        this.startFinishType = startFinishType;
        this.delayDays = delayDays;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
        this.relationActivity = relationActivity;
    }

    /** default constructor */
    public ActivityRelation() {
    }

    /** minimal constructor */
    public ActivityRelation(db.essp.pms.ActivityRelationPK pk) {
        this.pk = pk;
    }

    public db.essp.pms.ActivityRelationPK getPk() {
        return this.pk;
    }

    public void setPk(db.essp.pms.ActivityRelationPK pk) {
        this.pk = pk;
    }

    public String getStartFinishType() {
        return this.startFinishType;
    }

    public void setStartFinishType(String startFinishType) {
        this.startFinishType = startFinishType;
    }

    public Long getDelayDays() {
        return this.delayDays;
    }

    public void setDelayDays(Long delayDays) {
        this.delayDays = delayDays;
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

    public db.essp.pms.Activity getRelationActivity() {
        return this.relationActivity;
    }

    public void setRelationActivity(db.essp.pms.Activity relationActivity) {
        this.relationActivity = relationActivity;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("pk", getPk())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ActivityRelation) ) return false;
        ActivityRelation castOther = (ActivityRelation) other;
        return new EqualsBuilder()
            .append(this.getPk(), castOther.getPk())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getPk())
            .toHashCode();
    }

}
