package db.essp.issue;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="ISSUE_PRIORITY"
 *     
*/
public class IssuePriority implements Serializable {

    /** identifier field */
    private db.essp.issue.IssuePriorityPK comp_id;

    /** nullable persistent field */
    private Long rid;

    /** nullable persistent field */
    private Long duration;

    /** nullable persistent field */
    private Long sequence;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** full constructor */
    public IssuePriority(db.essp.issue.IssuePriorityPK comp_id, Long rid, Long duration, Long sequence, String description, String rst, Date rct, Date rut) {
        this.comp_id = comp_id;
        this.rid = rid;
        this.duration = duration;
        this.sequence = sequence;
        this.description = description;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
    }

    /** default constructor */
    public IssuePriority() {
    }

    /** minimal constructor */
    public IssuePriority(db.essp.issue.IssuePriorityPK comp_id) {
        this.comp_id = comp_id;
    }

    /** 
     *            @hibernate.id
     *             generator-class="assigned"
     *         
     */
    public db.essp.issue.IssuePriorityPK getComp_id() {
        return this.comp_id;
    }

    public void setComp_id(db.essp.issue.IssuePriorityPK comp_id) {
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
     *             column="Duration"
     *             length="8"
     *         
     */
    public Long getDuration() {
        return this.duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
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

    public String toString() {
        return new ToStringBuilder(this)
            .append("comp_id", getComp_id())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof IssuePriority) ) return false;
        IssuePriority castOther = (IssuePriority) other;
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
