package db.essp.pms;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class ActivityPK implements Serializable {

    /** identifier field */
    private Long acntRid;

    /** identifier field */
    private Long activityId;

    /** full constructor */
    public ActivityPK(Long acntRid, Long activityId) {
        this.acntRid = acntRid;
        this.activityId = activityId;
    }

    /** default constructor */
    public ActivityPK() {
    }

    public Long getAcntRid() {
        return this.acntRid;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public Long getActivityId() {
        return this.activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("acntRid", getAcntRid())
            .append("activityId", getActivityId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ActivityPK) ) return false;
        ActivityPK castOther = (ActivityPK) other;
        return new EqualsBuilder()
            .append(this.getAcntRid(), castOther.getAcntRid())
            .append(this.getActivityId(), castOther.getActivityId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getAcntRid())
            .append(getActivityId())
            .toHashCode();
    }

}
