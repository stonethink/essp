package db.essp.issue;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class IssueScopePK implements Serializable {

    /** identifier field */
    private String typeName;

    /** identifier field */
    private String scope;

    /** full constructor */
    public IssueScopePK(String typeName, String scope) {
        this.typeName = typeName;
        this.scope = scope;
    }

    /** default constructor */
    public IssueScopePK() {
    }

    /** 
     *                @hibernate.property
     *                 column="TypeName"
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
     *                 column="Scope"
     *                 length="100"
     *             
     */
    public String getScope() {
        return this.scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("typeName", getTypeName())
            .append("scope", getScope())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof IssueScopePK) ) return false;
        IssueScopePK castOther = (IssueScopePK) other;
        return new EqualsBuilder()
            .append(this.getTypeName(), castOther.getTypeName())
            .append(this.getScope(), castOther.getScope())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getTypeName())
            .append(getScope())
            .toHashCode();
    }

}
