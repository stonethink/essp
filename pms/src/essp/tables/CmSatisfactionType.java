package essp.tables;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="cm_satisfaction_type"
 *     
*/
public class CmSatisfactionType implements Serializable {

    /** identifier field */
    private Long rid;

    /** persistent field */
    private String typeName;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** nullable persistent field */
    private String rst;

    /** full constructor */
    public CmSatisfactionType(Long rid, String typeName, Date rct, Date rut, String rst) {
        this.rid = rid;
        this.typeName = typeName;
        this.rct = rct;
        this.rut = rut;
        this.rst = rst;
    }

    /** default constructor */
    public CmSatisfactionType() {
    }

    /** minimal constructor */
    public CmSatisfactionType(Long rid, String typeName) {
        this.rid = rid;
        this.typeName = typeName;
    }

    /** 
     *            @hibernate.id
     *             generator-class="assigned"
     *             type="java.lang.Long"
     *             column="RID"
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
     *             column="TYPE_NAME"
     *             length="50"
     *             not-null="true"
     *         
     */
    public String getTypeName() {
        return this.typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /** 
     *            @hibernate.property
     *             column="RCT"
     *             length="10"
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
     *             length="10"
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

    public String toString() {
        return new ToStringBuilder(this)
            .append("rid", getRid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof CmSatisfactionType) ) return false;
        CmSatisfactionType castOther = (CmSatisfactionType) other;
        return new EqualsBuilder()
            .append(this.getRid(), castOther.getRid())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getRid())
            .toHashCode();
    }

}
