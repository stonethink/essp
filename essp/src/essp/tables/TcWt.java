package essp.tables;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="tc_wts"
 *     
*/
public class TcWt implements Serializable {

    /** identifier field */
    private Integer wtsYear;

    /** persistent field */
    private int rid;

    /** persistent field */
    private String wtsDays;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** full constructor */
    public TcWt(Integer wtsYear, int rid, String wtsDays, String rst, Date rct, Date rut) {
        this.wtsYear = wtsYear;
        this.rid = rid;
        this.wtsDays = wtsDays;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
    }

    /** default constructor */
    public TcWt() {
    }

    /** minimal constructor */
    public TcWt(Integer wtsYear, int rid, String wtsDays) {
        this.wtsYear = wtsYear;
        this.rid = rid;
        this.wtsDays = wtsDays;
    }

    /** 
     *            @hibernate.id
     *             generator-class="assigned"
     *             type="java.lang.Integer"
     *             column="WTS_YEAR"
     *         
     */
    public Integer getWtsYear() {
        return this.wtsYear;
    }

    public void setWtsYear(Integer wtsYear) {
        this.wtsYear = wtsYear;
    }

    /** 
     *            @hibernate.property
     *             column="RID"
     *             length="8"
     *             not-null="true"
     *         
     */
    public int getRid() {
        return this.rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    /** 
     *            @hibernate.property
     *             column="WTS_DAYS"
     *             length="65535"
     *             not-null="true"
     *         
     */
    public String getWtsDays() {
        return this.wtsDays;
    }

    public void setWtsDays(String wtsDays) {
        this.wtsDays = wtsDays;
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
            .append("wtsYear", getWtsYear())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof TcWt) ) return false;
        TcWt castOther = (TcWt) other;
        return new EqualsBuilder()
            .append(this.getWtsYear(), castOther.getWtsYear())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getWtsYear())
            .toHashCode();
    }

}
