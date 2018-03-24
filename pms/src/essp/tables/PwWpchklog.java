package essp.tables;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="pw_wpchklogs"
 *     
*/
public class PwWpchklog implements Serializable {

    /** identifier field */
    private Long rid;

    /** persistent field */
    private Long wpchkId;

    /** nullable persistent field */
    private Date wpchklogsDate;

    /** nullable persistent field */
    private String wpchklogsFrom;

    /** nullable persistent field */
    private String wpchklogsReason;

    /** nullable persistent field */
    private Date wpchklogsBaselinechk;

    /** nullable persistent field */
    private Date wpchklogsActchk;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** full constructor */
    public PwWpchklog(Long rid, Long wpchkId, Date wpchklogsDate, String wpchklogsFrom, String wpchklogsReason, Date wpchklogsBaselinechk, Date wpchklogsActchk, String rst, Date rct, Date rut) {
        this.rid = rid;
        this.wpchkId = wpchkId;
        this.wpchklogsDate = wpchklogsDate;
        this.wpchklogsFrom = wpchklogsFrom;
        this.wpchklogsReason = wpchklogsReason;
        this.wpchklogsBaselinechk = wpchklogsBaselinechk;
        this.wpchklogsActchk = wpchklogsActchk;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
    }

    /** default constructor */
    public PwWpchklog() {
    }

    /** minimal constructor */
    public PwWpchklog(Long rid, Long wpchkId) {
        this.rid = rid;
        this.wpchkId = wpchkId;
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
     *             column="WPCHK_ID"
     *             length="8"
     *             not-null="true"
     *         
     */
    public Long getWpchkId() {
        return this.wpchkId;
    }

    public void setWpchkId(Long wpchkId) {
        this.wpchkId = wpchkId;
    }

    /** 
     *            @hibernate.property
     *             column="WPCHKLOGS_DATE"
     *             length="10"
     *         
     */
    public Date getWpchklogsDate() {
        return this.wpchklogsDate;
    }

    public void setWpchklogsDate(Date wpchklogsDate) {
        this.wpchklogsDate = wpchklogsDate;
    }

    /** 
     *            @hibernate.property
     *             column="WPCHKLOGS_FROM"
     *             length="50"
     *         
     */
    public String getWpchklogsFrom() {
        return this.wpchklogsFrom;
    }

    public void setWpchklogsFrom(String wpchklogsFrom) {
        this.wpchklogsFrom = wpchklogsFrom;
    }

    /** 
     *            @hibernate.property
     *             column="WPCHKLOGS_REASON"
     *             length="65535"
     *         
     */
    public String getWpchklogsReason() {
        return this.wpchklogsReason;
    }

    public void setWpchklogsReason(String wpchklogsReason) {
        this.wpchklogsReason = wpchklogsReason;
    }

    /** 
     *            @hibernate.property
     *             column="WPCHKLOGS_BASELINECHK"
     *             length="10"
     *         
     */
    public Date getWpchklogsBaselinechk() {
        return this.wpchklogsBaselinechk;
    }

    public void setWpchklogsBaselinechk(Date wpchklogsBaselinechk) {
        this.wpchklogsBaselinechk = wpchklogsBaselinechk;
    }

    /** 
     *            @hibernate.property
     *             column="WPCHKLOGS_ACTCHK"
     *             length="10"
     *         
     */
    public Date getWpchklogsActchk() {
        return this.wpchklogsActchk;
    }

    public void setWpchklogsActchk(Date wpchklogsActchk) {
        this.wpchklogsActchk = wpchklogsActchk;
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
        if ( !(other instanceof PwWpchklog) ) return false;
        PwWpchklog castOther = (PwWpchklog) other;
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
