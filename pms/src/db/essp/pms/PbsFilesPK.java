package db.essp.pms;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class PbsFilesPK implements Serializable {

    /** identifier field */
    private Long acntRid;

    /** identifier field */
    private Long pbsRid;

    /** identifier field */
    private Long filesRid;

    /** full constructor */
    public PbsFilesPK(Long acntRid, Long pbsRid, Long filesRid) {
        this.acntRid = acntRid;
        this.pbsRid = pbsRid;
        this.filesRid = filesRid;
    }

    /** default constructor */
    public PbsFilesPK() {
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

    public Long getFilesRid() {
        return this.filesRid;
    }

    public void setFilesRid(Long filesRid) {
        this.filesRid = filesRid;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("acntRid", getAcntRid())
            .append("pbsRid", getPbsRid())
            .append("filesRid", getFilesRid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof PbsFilesPK) ) return false;
        PbsFilesPK castOther = (PbsFilesPK) other;
        return new EqualsBuilder()
            .append(this.getAcntRid(), castOther.getAcntRid())
            .append(this.getPbsRid(), castOther.getPbsRid())
            .append(this.getFilesRid(), castOther.getFilesRid())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getAcntRid())
            .append(getPbsRid())
            .append(getFilesRid())
            .toHashCode();
    }

}
