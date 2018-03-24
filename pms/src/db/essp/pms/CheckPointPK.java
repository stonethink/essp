package db.essp.pms;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class CheckPointPK implements Serializable {

    /** identifier field */
    private Long acntRid;

    /** identifier field */
    private Long wbsRid;

    /** identifier field */
    private Long rid;

    /** full constructor */
    public CheckPointPK(Long acntRid, Long wbsRid, Long rid) {
        this.acntRid = acntRid;
        this.wbsRid = wbsRid;
        this.rid = rid;
    }

    /** default constructor */
    public CheckPointPK() {
    }

    public Long getAcntRid() {
        return this.acntRid;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public Long getWbsRid() {
        return this.wbsRid;
    }

    public void setWbsRid(Long wbsRid) {
        this.wbsRid = wbsRid;
    }

    public Long getRid() {
        return this.rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("acntRid", getAcntRid())
            .append("wbsRid", getWbsRid())
            .append("rid", getRid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof CheckPointPK) ) return false;
        CheckPointPK castOther = (CheckPointPK) other;
        return new EqualsBuilder()
            .append(this.getAcntRid(), castOther.getAcntRid())
            .append(this.getWbsRid(), castOther.getWbsRid())
            .append(this.getRid(), castOther.getRid())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getAcntRid())
            .append(getWbsRid())
            .append(getRid())
            .toHashCode();
    }

}
