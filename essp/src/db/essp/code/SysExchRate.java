package db.essp.code;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class SysExchRate implements Serializable {

    /** identifier field */
    private db.essp.code.SysExchRatePK comp_id;

    /** nullable persistent field */
    private Long rid;

    /** nullable persistent field */
    private Double rate;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** full constructor */
    public SysExchRate(db.essp.code.SysExchRatePK comp_id, Long rid, Double rate, String rst, Date rct, Date rut) {
        this.comp_id = comp_id;
        this.rid = rid;
        this.rate = rate;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
    }

    /** default constructor */
    public SysExchRate() {
    }

    /** minimal constructor */
    public SysExchRate(db.essp.code.SysExchRatePK comp_id) {
        this.comp_id = comp_id;
    }

    public db.essp.code.SysExchRatePK getComp_id() {
        return this.comp_id;
    }

    public void setComp_id(db.essp.code.SysExchRatePK comp_id) {
        this.comp_id = comp_id;
    }

    public Long getRid() {
        return this.rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public Double getRate() {
        return this.rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getRst() {
        return this.rst;
    }

    public void setRst(String rst) {
        this.rst = rst;
    }

    public Date getRct() {
        return this.rct;
    }

    public void setRct(Date rct) {
        this.rct = rct;
    }

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
        if ( !(other instanceof SysExchRate) ) return false;
        SysExchRate castOther = (SysExchRate) other;
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
