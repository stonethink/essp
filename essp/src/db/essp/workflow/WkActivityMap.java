package db.essp.workflow;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** 
 *        @hibernate.class
 *         table="wk_activity_map"
 *     
*/
public class WkActivityMap implements Serializable {

    /** identifier field */
    private db.essp.workflow.WkActivityMapPK comp_id;

    /** nullable persistent field */
    private String waFlag;

    /** full constructor */
    public WkActivityMap(db.essp.workflow.WkActivityMapPK comp_id, String waFlag) {
        this.comp_id = comp_id;
        this.waFlag = waFlag;
    }

    /** default constructor */
    public WkActivityMap() {
    }

    /** minimal constructor */
    public WkActivityMap(db.essp.workflow.WkActivityMapPK comp_id) {
        this.comp_id = comp_id;
    }

    /** 
     *            @hibernate.id
     *             generator-class="assigned"
     *         
     */
    public db.essp.workflow.WkActivityMapPK getComp_id() {
        return this.comp_id;
    }

    public void setComp_id(db.essp.workflow.WkActivityMapPK comp_id) {
        this.comp_id = comp_id;
    }

    /** 
     *            @hibernate.property
     *             column="WA_FLAG"
     *             length="10"
     *         
     */
    public String getWaFlag() {
        return this.waFlag;
    }

    public void setWaFlag(String waFlag) {
        this.waFlag = waFlag;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("comp_id", getComp_id())
            .toString();
    }

    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof WkActivityMap) ) return false;
        WkActivityMap castOther = (WkActivityMap) other;
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
