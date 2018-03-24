package db.essp.issue;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *        @hibernate.class
 *         table="ISSUE_CATEGORY_TYPE"
 *
*/
public class IssueCategoryType implements Serializable {

    /** identifier field */
    private db.essp.issue.IssueCategoryTypePK comp_id;

    /** nullable persistent field */
    private Long rid;

    /** nullable persistent field */
    private Long sequence;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** nullable persistent field */
    private String description;

    /** persistent field */
    private Set categoryValues;

    /** full constructor */
    public IssueCategoryType(db.essp.issue.IssueCategoryTypePK comp_id, Long rid, Long sequence, String rst, Date rct, Date rut, String description, Set categoryValues) {
        this.comp_id = comp_id;
        this.rid = rid;
        this.sequence = sequence;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
        this.description = description;
        this.categoryValues = categoryValues;
    }

    /** default constructor */
    public IssueCategoryType() {
    }

    /** minimal constructor */
    public IssueCategoryType(db.essp.issue.IssueCategoryTypePK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     *            @hibernate.id
     *             generator-class="assigned"
     *
     */
    public db.essp.issue.IssueCategoryTypePK getComp_id() {
        return this.comp_id;
    }

    public void setComp_id(db.essp.issue.IssueCategoryTypePK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     *            @hibernate.property
     *             column="RID"
     *             length="8"
     *
     */
    public Long getRid() {
        return this.rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    /**
     *            @hibernate.property
     *             column="Sequence"
     *             length="8"
     *
     */
    public Long getSequence() {
        return this.sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    /**
     *            @hibernate.property
     *             column="RST"
     *             length="1"
     *
     */
    public String getRst() {
        return this.rst;
    }

    public void setRst(String rst) {
        this.rst = rst;
    }

    /**
     *            @hibernate.property
     *             column="RCT"
     *             length="20"
     *
     */
    public Date getRct() {
        return this.rct;
    }

    public void setRct(Date rct) {
        this.rct = rct;
    }

    /**
     *            @hibernate.property
     *             column="RUT"
     *             length="20"
     *
     */
    public Date getRut() {
        return this.rut;
    }

    public void setRut(Date rut) {
        this.rut = rut;
    }

    /**
     *            @hibernate.property
     *             column="Description"
     *             length="500"
     *
     */
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set getCategoryValues() {
        return this.categoryValues;
    }

    public void setCategoryValues(Set categoryValues) {
        this.categoryValues = categoryValues;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("comp_id", getComp_id())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof IssueCategoryType) ) return false;
        IssueCategoryType castOther = (IssueCategoryType) other;
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
