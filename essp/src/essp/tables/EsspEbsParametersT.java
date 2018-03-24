package essp.tables;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="essp_ebs_parameters_t"
 *     
*/
public class EsspEbsParametersT implements Serializable {

    /** identifier field */
    private essp.tables.EsspEbsParametersTPK comp_id;

    /** nullable persistent field */
    private String name;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private String rolefield;

    /** full constructor */
    public EsspEbsParametersT(essp.tables.EsspEbsParametersTPK comp_id, String name, String description, String rolefield) {
        this.comp_id = comp_id;
        this.name = name;
        this.description = description;
        this.rolefield = rolefield;
    }

    /** default constructor */
    public EsspEbsParametersT() {
    }

    /** minimal constructor */
    public EsspEbsParametersT(essp.tables.EsspEbsParametersTPK comp_id) {
        this.comp_id = comp_id;
    }

    /** 
     *            @hibernate.id
     *             generator-class="assigned"
     *         
     */
    public essp.tables.EsspEbsParametersTPK getComp_id() {
        return this.comp_id;
    }

    public void setComp_id(essp.tables.EsspEbsParametersTPK comp_id) {
        this.comp_id = comp_id;
    }

    /** 
     *            @hibernate.property
     *             column="NAME"
     *             length="50"
     *         
     */
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /** 
     *            @hibernate.property
     *             column="DESCRIPTION"
     *             length="100"
     *         
     */
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /** 
     *            @hibernate.property
     *             column="ROLEFIELD"
     *             length="20"
     *         
     */
    public String getRolefield() {
        return this.rolefield;
    }

    public void setRolefield(String rolefield) {
        this.rolefield = rolefield;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("comp_id", getComp_id())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof EsspEbsParametersT) ) return false;
        EsspEbsParametersT castOther = (EsspEbsParametersT) other;
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
