package db.essp.pms;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class IssuePK implements Serializable {

    /** identifier field */
    private String joinType;

    /** identifier field */
    private Long acntRid;

    /** identifier field */
    private Long joinRid;

    /** identifier field */
    private Long issueRid;

    /** full constructor */
    public IssuePK(String joinType, Long acntRid, Long joinRid, Long issueRid) {
        this.joinType = joinType;
        this.acntRid = acntRid;
        this.joinRid = joinRid;
        this.issueRid = issueRid;
    }

    /** default constructor */
    public IssuePK() {
    }

    public String getJoinType() {
        return this.joinType;
    }

    public void setJoinType(String joinType) {
        this.joinType = joinType;
    }

    public Long getAcntRid() {
        return this.acntRid;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public Long getJoinRid() {
        return this.joinRid;
    }

    public void setJoinRid(Long joinRid) {
        this.joinRid = joinRid;
    }

    public Long getIssueRid() {
        return this.issueRid;
    }

    public void setIssueRid(Long issueRid) {
        this.issueRid = issueRid;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("joinType", getJoinType())
            .append("acntRid", getAcntRid())
            .append("joinRid", getJoinRid())
            .append("issueRid", getIssueRid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof IssuePK) ) return false;
        IssuePK castOther = (IssuePK) other;
        return new EqualsBuilder()
            .append(this.getJoinType(), castOther.getJoinType())
            .append(this.getAcntRid(), castOther.getAcntRid())
            .append(this.getJoinRid(), castOther.getJoinRid())
            .append(this.getIssueRid(), castOther.getIssueRid())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getJoinType())
            .append(getAcntRid())
            .append(getJoinRid())
            .append(getIssueRid())
            .toHashCode();
    }

}
