package db.essp.pms;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class EbsEbs9acntPK implements Serializable {

    /** identifier field */
    private Long ebsRid;

    /** identifier field */
    private Long acntRid;

    /** full constructor */
    public EbsEbs9acntPK(Long ebsRid, Long acntRid) {
        this.ebsRid = ebsRid;
        this.acntRid = acntRid;
    }

    /** default constructor */
    public EbsEbs9acntPK() {
    }

    /** 
     *                @hibernate.property
     *                 column="EBS_RID"
     *                 length="8"
     *             
     */
    public Long getEbsRid() {
        return this.ebsRid;
    }

    public void setEbsRid(Long ebsRid) {
        this.ebsRid = ebsRid;
    }

    /** 
     *                @hibernate.property
     *                 column="ACNT_RID"
     *                 length="8"
     *             
     */
    public Long getAcntRid() {
        return this.acntRid;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("ebsRid", getEbsRid())
            .append("acntRid", getAcntRid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof EbsEbs9acntPK) ) return false;
        EbsEbs9acntPK castOther = (EbsEbs9acntPK) other;
        return new EqualsBuilder()
            .append(this.getEbsRid(), castOther.getEbsRid())
            .append(this.getAcntRid(), castOther.getAcntRid())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getEbsRid())
            .append(getAcntRid())
            .toHashCode();
    }

}
