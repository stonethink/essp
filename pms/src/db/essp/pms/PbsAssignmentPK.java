package db.essp.pms;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class PbsAssignmentPK implements Serializable {

    /** identifier field */
    private Long acntRid;

    /** identifier field */
    private Long pbsRid;

    /** identifier field */
    private Long joinType;

    /** identifier field */
    private Long joinRid;

    /** full constructor */
    public PbsAssignmentPK(Long acntRid, Long pbsRid, Long joinType, Long joinRid) {
        this.acntRid = acntRid;
        this.pbsRid = pbsRid;
        this.joinType = joinType;
        this.joinRid = joinRid;
    }

    /** default constructor */
    public PbsAssignmentPK() {
    }

    public Long getAcntRid() {
        return this.acntRid;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public Long getPbsRid() {
        return this.pbsRid;
    }

    public void setPbsRid(Long pbsRid) {
        this.pbsRid = pbsRid;
    }

    public Long getJoinType() {
        return this.joinType;
    }

    public void setJoinType(Long joinType) {
        this.joinType = joinType;
    }

    public Long getJoinRid() {
        return this.joinRid;
    }

    public void setJoinRid(Long joinRid) {
        this.joinRid = joinRid;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("acntRid", getAcntRid())
            .append("pbsRid", getPbsRid())
            .append("joinType", getJoinType())
            .append("joinRid", getJoinRid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof PbsAssignmentPK) ) return false;
        PbsAssignmentPK castOther = (PbsAssignmentPK) other;
        return new EqualsBuilder()
            .append(this.getAcntRid(), castOther.getAcntRid())
            .append(this.getPbsRid(), castOther.getPbsRid())
            .append(this.getJoinType(), castOther.getJoinType())
            .append(this.getJoinRid(), castOther.getJoinRid())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getAcntRid())
            .append(getPbsRid())
            .append(getJoinType())
            .append(getJoinRid())
            .toHashCode();
    }

}
