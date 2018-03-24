package db.essp.pms;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class WbsPK implements Serializable {

    /** identifier field */
    private Long wbsRid;

    /** identifier field */
    private Long acntRid;

    /** full constructor */
    public WbsPK(Long acntRid,Long wbsRid) {
        this.wbsRid = wbsRid;
        this.acntRid = acntRid;
    }

    /** default constructor */
    public WbsPK() {
    }

    public Long getWbsRid() {
        return this.wbsRid;
    }

    public void setWbsRid(Long wbsRid) {
        this.wbsRid = wbsRid;
    }

    public Long getAcntRid() {
        return this.acntRid;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("wbsRid", getWbsRid())
            .append("account", getAcntRid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof WbsPK) ) return false;
        WbsPK castOther = (WbsPK) other;
        return new EqualsBuilder()
            .append(this.getWbsRid(), castOther.getWbsRid())
            .append(this.getAcntRid(), castOther.getAcntRid())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getWbsRid())
            .append(getAcntRid())
            .toHashCode();
    }

}
