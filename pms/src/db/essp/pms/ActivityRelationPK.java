package db.essp.pms;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class ActivityRelationPK implements Serializable {

    /** identifier field */
    private Long acntRid;

    /** identifier field */
    private Long activityId;

    /** identifier field */
    private Long postActivityId;

    /** full constructor */
    public ActivityRelationPK(Long acntRid, Long activityId, Long postActivityId) {
        this.acntRid = acntRid;
        this.activityId = activityId;
        this.postActivityId = postActivityId;
    }

    /** default constructor */
    public ActivityRelationPK() {
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

    public Long getPostActivityId() {
        return this.postActivityId;
    }

    public void setPostActivityId(Long postActivityId) {
        this.postActivityId = postActivityId;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("acntRid", getAcntRid())
            .append("activityId", getActivityId())
            .append("postActivityId", getPostActivityId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ActivityRelationPK) ) return false;
        ActivityRelationPK castOther = (ActivityRelationPK) other;
        return new EqualsBuilder()
            .append(this.getAcntRid(), castOther.getAcntRid())
            .append(this.getActivityId(), castOther.getActivityId())
            .append(this.getPostActivityId(), castOther.getPostActivityId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getAcntRid())
            .append(getActivityId())
            .append(getPostActivityId())
            .toHashCode();
    }

}
