package db.essp.issue;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class IssuePriorityPK implements Serializable {

    /** identifier field */
    private String typeName;

    /** identifier field */
    private String priority;

    /** full constructor */
    public IssuePriorityPK(String typeName, String priority) {
        this.typeName = typeName;
        this.priority = priority;
    }

    /** default constructor */
    public IssuePriorityPK() {
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
     *                 column="Priority"
     *                 length="100"
     *             
     */
    public String getPriority() {
        return this.priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("typeName", getTypeName())
            .append("priority", getPriority())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof IssuePriorityPK) ) return false;
        IssuePriorityPK castOther = (IssuePriorityPK) other;
        return new EqualsBuilder()
            .append(this.getTypeName(), castOther.getTypeName())
            .append(this.getPriority(), castOther.getPriority())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getTypeName())
            .append(getPriority())
            .toHashCode();
    }

}
