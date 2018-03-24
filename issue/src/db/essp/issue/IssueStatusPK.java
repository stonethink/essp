package db.essp.issue;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class IssueStatusPK implements Serializable {

    /** identifier field */
    private String typeName;

    /** identifier field */
    private String statusName;

    /** full constructor */
    public IssueStatusPK(String typeName, String statusName) {
        this.typeName = typeName;
        this.statusName = statusName;
    }

    /** default constructor */
    public IssueStatusPK() {
    }

    /** 
     *                @hibernate.property
     *                 column="Type_Name"
     *                 length="100"
     *             
     */
    public String getTypeName() {
        return this.typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /** 
     *                @hibernate.property
     *                 column="StatusName"
     *                 length="100"
     *             
     */
    public String getStatusName() {
        return this.statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("typeName", getTypeName())
            .append("statusName", getStatusName())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof IssueStatusPK) ) return false;
        IssueStatusPK castOther = (IssueStatusPK) other;
        return new EqualsBuilder()
            .append(this.getTypeName(), castOther.getTypeName())
            .append(this.getStatusName(), castOther.getStatusName())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getTypeName())
            .append(getStatusName())
            .toHashCode();
    }

}
