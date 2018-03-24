package essp.tables;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="essp_hbseq"
 *     
*/
public class EsspHbseq implements Serializable {

    /** identifier field */
    private String seqType;

    /** nullable persistent field */
    private Long seqNo;

    /** full constructor */
    public EsspHbseq(String seqType, Long seqNo) {
        this.seqType = seqType;
        this.seqNo = seqNo;
    }

    /** default constructor */
    public EsspHbseq() {
    }

    /** minimal constructor */
    public EsspHbseq(String seqType) {
        this.seqType = seqType;
    }

    /** 
     *            @hibernate.id
     *             generator-class="assigned"
     *             type="java.lang.String"
     *             column="SEQ_TYPE"
     *         
     */
    public String getSeqType() {
        return this.seqType;
    }

    public void setSeqType(String seqType) {
        this.seqType = seqType;
    }

    /** 
     *            @hibernate.property
     *             column="SEQ_NO"
     *             length="11"
     *         
     */
    public Long getSeqNo() {
        return this.seqNo;
    }

    public void setSeqNo(Long seqNo) {
        this.seqNo = seqNo;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("seqType", getSeqType())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof EsspHbseq) ) return false;
        EsspHbseq castOther = (EsspHbseq) other;
        return new EqualsBuilder()
            .append(this.getSeqType(), castOther.getSeqType())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getSeqType())
            .toHashCode();
    }

}
