package db.essp.issue;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class IssueCategoryTypePK implements Serializable {

    /** identifier field */
    private String typeName;

    /** identifier field */
    private String categoryName;

    /** full constructor */
    public IssueCategoryTypePK(String typeName, String categoryName) {
        this.typeName = typeName;
        this.categoryName = categoryName;
    }

    /** default constructor */
    public IssueCategoryTypePK() {
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
     *                 column="CategoryName"
     *                 length="100"
     *             
     */
    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("typeName", getTypeName())
            .append("categoryName", getCategoryName())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof IssueCategoryTypePK) ) return false;
        IssueCategoryTypePK castOther = (IssueCategoryTypePK) other;
        return new EqualsBuilder()
            .append(this.getTypeName(), castOther.getTypeName())
            .append(this.getCategoryName(), castOther.getCategoryName())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getTypeName())
            .append(getCategoryName())
            .toHashCode();
    }

}
