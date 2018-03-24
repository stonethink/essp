package db.essp.issue;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class IssueRiskPK implements Serializable {

    /** identifier field */
    private String typeName;

    /** identifier field */
    private String influence;

    /** full constructor */
    public IssueRiskPK(String typeName, String influence) {
        this.typeName = typeName;
        this.influence = influence;
    }

    /** default constructor */
    public IssueRiskPK() {
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
     *                 column="Influence"
     *                 length="100"
     *             
     */
    public String getInfluence() {
        return this.influence;
    }

    public void setInfluence(String influence) {
        this.influence = influence;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("typeName", getTypeName())
            .append("influence", getInfluence())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof IssueRiskPK) ) return false;
        IssueRiskPK castOther = (IssueRiskPK) other;
        return new EqualsBuilder()
            .append(this.getTypeName(), castOther.getTypeName())
            .append(this.getInfluence(), castOther.getInfluence())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getTypeName())
            .append(getInfluence())
            .toHashCode();
    }

}
