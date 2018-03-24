package essp.tables;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="essp_sys_accounttype_t"
 *     
*/
public class EsspSysAccounttypeT implements Serializable {

    /** identifier field */
    private long typeId;

    /** identifier field */
    private String description;

    /** identifier field */
    private String typeName;

    /** full constructor */
    public EsspSysAccounttypeT(long typeId, String description, String typeName) {
        this.typeId = typeId;
        this.description = description;
        this.typeName = typeName;
    }

    /** default constructor */
    public EsspSysAccounttypeT() {
    }

    /** 
     *                @hibernate.property
     *                 column="TYPE_ID"
     *                 length="10"
     *             
     */
    public long getTypeId() {
        return this.typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    /** 
     *                @hibernate.property
     *                 column="DESCRIPTION"
     *                 length="255"
     *             
     */
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /** 
     *                @hibernate.property
     *                 column="TYPE_NAME"
     *                 length="20"
     *             
     */
    public String getTypeName() {
        return this.typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("typeId", getTypeId())
            .append("description", getDescription())
            .append("typeName", getTypeName())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof EsspSysAccounttypeT) ) return false;
        EsspSysAccounttypeT castOther = (EsspSysAccounttypeT) other;
        return new EqualsBuilder()
            .append(this.getTypeId(), castOther.getTypeId())
            .append(this.getDescription(), castOther.getDescription())
            .append(this.getTypeName(), castOther.getTypeName())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getTypeId())
            .append(getDescription())
            .append(getTypeName())
            .toHashCode();
    }

}
