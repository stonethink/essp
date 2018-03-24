package db.essp.pms;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class PbsAssignment implements Serializable {

    /** identifier field */
    private db.essp.pms.PbsAssignmentPK pk;

    /** nullable persistent field */
    private String isWorkproduct;

    /** nullable persistent field */
    private db.essp.pms.Pbs pbs;

    /** full constructor */
    public PbsAssignment(db.essp.pms.PbsAssignmentPK pk, String isWorkproduct, db.essp.pms.Pbs pbs) {
        this.pk = pk;
        this.isWorkproduct = isWorkproduct;
        this.pbs = pbs;
    }

    /** default constructor */
    public PbsAssignment() {
    }

    /** minimal constructor */
    public PbsAssignment(db.essp.pms.PbsAssignmentPK pk) {
        this.pk = pk;
    }

    public db.essp.pms.PbsAssignmentPK getPk() {
        return this.pk;
    }

    public void setPk(db.essp.pms.PbsAssignmentPK pk) {
        this.pk = pk;
    }

    public String getIsWorkproduct() {
        return this.isWorkproduct;
    }

    public void setIsWorkproduct(String isWorkproduct) {
        this.isWorkproduct = isWorkproduct;
    }

    public db.essp.pms.Pbs getPbs() {
        return this.pbs;
    }

    public void setPbs(db.essp.pms.Pbs pbs) {
        this.pbs = pbs;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("pk", getPk())
            .toString();
    }

    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof PbsAssignment) ) return false;
        PbsAssignment castOther = (PbsAssignment) other;
        return new EqualsBuilder()
            .append(this.getPk(), castOther.getPk())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getPk())
            .toHashCode();
    }

}
