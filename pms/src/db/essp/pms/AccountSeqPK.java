package db.essp.pms;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class AccountSeqPK implements Serializable {

    /** identifier field */
    private Long rid;

    /** identifier field */
    private String seqType;

    /** full constructor */
    public AccountSeqPK(Long rid, String seqType) {
        this.rid = rid;
        this.seqType = seqType;
    }

    /** default constructor */
    public AccountSeqPK() {
    }

    public Long getRid() {
        return this.rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getSeqType() {
        return this.seqType;
    }

    public void setSeqType(String seqType) {
        this.seqType = seqType;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("rid", getRid())
            .append("seqType", getSeqType())
            .toString();
    }

    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof AccountSeqPK) ) return false;
        AccountSeqPK castOther = (AccountSeqPK) other;
        return new EqualsBuilder()
            .append(this.getRid(), castOther.getRid())
            .append(this.getSeqType(), castOther.getSeqType())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getRid())
            .append(getSeqType())
            .toHashCode();
    }

}
