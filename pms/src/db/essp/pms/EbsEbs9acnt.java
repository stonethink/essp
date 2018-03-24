package db.essp.pms;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** 
 *        @hibernate.classd
 *         table="EBS_EBS9ACNT"
 *     
*/
public class EbsEbs9acnt implements Serializable {

    /** identifier field */
    private db.essp.pms.EbsEbs9acntPK comp_id;

    /** full constructor */
    public EbsEbs9acnt(db.essp.pms.EbsEbs9acntPK comp_id) {
        this.comp_id = comp_id;
    }

    /** default constructor */
    public EbsEbs9acnt() {
    }

    /** 
     *            @hibernate.id
     *             generator-class="assigned"
     *         
     */
    public db.essp.pms.EbsEbs9acntPK getComp_id() {
        return this.comp_id;
    }

    public void setComp_id(db.essp.pms.EbsEbs9acntPK comp_id) {
        this.comp_id = comp_id;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("comp_id", getComp_id())
            .toString();
    }

    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof EbsEbs9acnt) ) return false;
        EbsEbs9acnt castOther = (EbsEbs9acnt) other;
        return new EqualsBuilder()
            .append(this.getComp_id(), castOther.getComp_id())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getComp_id())
            .toHashCode();
    }

}
