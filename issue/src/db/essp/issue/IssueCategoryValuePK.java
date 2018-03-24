package db.essp.issue;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class IssueCategoryValuePK implements Serializable {

    /** identifier field */
    private String typeName;

    /** identifier field */
    private String categoryName;

    /** identifier field */
    private String categoryValue;

    /** full constructor */
    public IssueCategoryValuePK(String typeName, String categoryName, String categoryValue) {
        this.typeName = typeName;
        this.categoryName = categoryName;
        this.categoryValue = categoryValue;
    }

    /** default constructor */
    public IssueCategoryValuePK() {
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

    /** 
     *                @hibernate.property
     *                 column="CategoryValue"
     *                 length="100"
     *             
     */
    public String getCategoryValue() {
        return this.categoryValue;
    }

    public void setCategoryValue(String categoryValue) {
        this.categoryValue = categoryValue;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("typeName", getTypeName())
            .append("categoryName", getCategoryName())
            .append("categoryValue", getCategoryValue())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof IssueCategoryValuePK) ) return false;
        IssueCategoryValuePK castOther = (IssueCategoryValuePK) other;
        return new EqualsBuilder()
            .append(this.getTypeName(), castOther.getTypeName())
            .append(this.getCategoryName(), castOther.getCategoryName())
            .append(this.getCategoryValue(), castOther.getCategoryValue())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getTypeName())
            .append(getCategoryName())
            .append(getCategoryValue())
            .toHashCode();
    }

}
