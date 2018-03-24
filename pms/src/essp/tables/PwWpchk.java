package essp.tables;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="pw_wpchk"
 *     
*/
public class PwWpchk implements Serializable {

    /** identifier field */
    private Long rid;

    /** persistent field */
    private Long wpId;

    /** nullable persistent field */
    private String wpchkName;

    /** nullable persistent field */
    private Date wpchkDate;

    /** nullable persistent field */
    private String wpchkStatus;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** full constructor */
    public PwWpchk(Long rid, Long wpId, String wpchkName, Date wpchkDate, String wpchkStatus, String rst, Date rct, Date rut) {
        this.rid = rid;
        this.wpId = wpId;
        this.wpchkName = wpchkName;
        this.wpchkDate = wpchkDate;
        this.wpchkStatus = wpchkStatus;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
    }

    /** default constructor */
    public PwWpchk() {
    }

    /** minimal constructor */
    public PwWpchk(Long rid, Long wpId) {
        this.rid = rid;
        this.wpId = wpId;
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
     *             column="WP_ID"
     *             length="8"
     *             not-null="true"
     *         
     */
    public Long getWpId() {
        return this.wpId;
    }

    public void setWpId(Long wpId) {
        this.wpId = wpId;
    }

    /** 
     *            @hibernate.property
     *             column="WPCHK_NAME"
     *             length="65535"
     *         
     */
    public String getWpchkName() {
        return this.wpchkName;
    }

    public void setWpchkName(String wpchkName) {
        this.wpchkName = wpchkName;
    }

    /** 
     *            @hibernate.property
     *             column="WPCHK_DATE"
     *             length="10"
     *         
     */
    public Date getWpchkDate() {
        return this.wpchkDate;
    }

    public void setWpchkDate(Date wpchkDate) {
        this.wpchkDate = wpchkDate;
    }

    /** 
     *            @hibernate.property
     *             column="WPCHK_STATUS"
     *             length="50"
     *         
     */
    public String getWpchkStatus() {
        return this.wpchkStatus;
    }

    public void setWpchkStatus(String wpchkStatus) {
        this.wpchkStatus = wpchkStatus;
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

    public String toString() {
        return new ToStringBuilder(this)
            .append("rid", getRid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof PwWpchk) ) return false;
        PwWpchk castOther = (PwWpchk) other;
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
