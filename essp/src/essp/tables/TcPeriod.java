package essp.tables;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="tc_period"
 *     
*/
public class TcPeriod implements Serializable {

    /** identifier field */
    private String periodPid;

    /** nullable persistent field */
    private Integer rid;

    /** persistent field */
    private String peridoRule;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** full constructor */
    public TcPeriod(String periodPid, Integer rid, String peridoRule, String rst, Date rct, Date rut) {
        this.periodPid = periodPid;
        this.rid = rid;
        this.peridoRule = peridoRule;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
    }

    /** default constructor */
    public TcPeriod() {
    }

    /** minimal constructor */
    public TcPeriod(String periodPid, String peridoRule) {
        this.periodPid = periodPid;
        this.peridoRule = peridoRule;
    }

    /** 
     *            @hibernate.id
     *             generator-class="assigned"
     *             type="java.lang.String"
     *             column="PERIOD_PID"
     *         
     */
    public String getPeriodPid() {
        return this.periodPid;
    }

    public void setPeriodPid(String periodPid) {
        this.periodPid = periodPid;
    }

    /** 
     *            @hibernate.property
     *             column="RID"
     *             length="8"
     *         
     */
    public Integer getRid() {
        return this.rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    /** 
     *            @hibernate.property
     *             column="PERIDO_RULE"
     *             length="65535"
     *             not-null="true"
     *         
     */
    public String getPeridoRule() {
        return this.peridoRule;
    }

    public void setPeridoRule(String peridoRule) {
        this.peridoRule = peridoRule;
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
            .append("periodPid", getPeriodPid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof TcPeriod) ) return false;
        TcPeriod castOther = (TcPeriod) other;
        return new EqualsBuilder()
            .append(this.getPeriodPid(), castOther.getPeriodPid())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getPeriodPid())
            .toHashCode();
    }

}
