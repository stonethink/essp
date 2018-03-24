package db.essp.workflow;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class WkActivityMapPK implements Serializable {

    /** identifier field */
    private Long activityID;

    /** identifier field */
    private Long activityIDNext;

    /** full constructor */
    public WkActivityMapPK(Long activityID, Long activityIDNext) {
        this.activityID = activityID;
        this.activityIDNext = activityIDNext;
    }

    /** default constructor */
    public WkActivityMapPK() {
    }

    /** 
     *                @hibernate.property
     *                 column="ACTIVITY_ID"
     *                 length="8"
     *             
     */
    public Long getActivityID() {
        return this.activityID;
    }

    public void setActivityID(Long activityID) {
        this.activityID = activityID;
    }

    /** 
     *                @hibernate.property
     *                 column="ACTIVITY_ID_NEXT"
     *                 length="8"
     *             
     */
    public Long getActivityIDNext() {
        return this.activityIDNext;
    }

    public void setActivityIDNext(Long activityIDNext) {
        this.activityIDNext = activityIDNext;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("activityID", getActivityID())
            .append("activityIDNext", getActivityIDNext())
            .toString();
    }

    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof WkActivityMapPK) ) return false;
        WkActivityMapPK castOther = (WkActivityMapPK) other;
        return new EqualsBuilder()
            .append(this.getActivityID(), castOther.getActivityID())
            .append(this.getActivityIDNext(), castOther.getActivityIDNext())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getActivityID())
            .append(getActivityIDNext())
            .toHashCode();
    }

}
