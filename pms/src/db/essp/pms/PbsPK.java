package db.essp.pms;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class PbsPK implements Serializable {

    /** identifier field */
    private Long acntRid;

    /** identifier field */
    private Long pbsRid;

    /** full constructor */
    public PbsPK(Long acntRid, Long pbsRid) {
        this.acntRid = acntRid;
        this.pbsRid = pbsRid;
    }

    /** default constructor */
    public PbsPK() {
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

    public String toString() {
        return new ToStringBuilder(this)
            .append("acntRid", getAcntRid())
            .append("pbsRid", getPbsRid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof PbsPK) ) return false;
        PbsPK castOther = (PbsPK) other;
        return new EqualsBuilder()
            .append(this.getAcntRid(), castOther.getAcntRid())
            .append(this.getPbsRid(), castOther.getPbsRid())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getAcntRid())
            .append(getPbsRid())
            .toHashCode();
    }

}
