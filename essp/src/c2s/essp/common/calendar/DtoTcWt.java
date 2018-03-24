package c2s.essp.common.calendar;

import c2s.dto.DtoBase;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

import java.util.Date;
import java.math.BigDecimal;


/**
 *        @hibernate.class
 *         table="tc_wts"
 *
 */
public class DtoTcWt extends DtoBase implements Serializable {
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
    public DtoTcWt(Integer wtsYear,
                   int     rid,
                   String  wtsDays,
                   String  rst,
                   Date    rct,
                   Date    rut) {
        this.wtsYear = wtsYear;
        this.rid     = rid;
        this.wtsDays = wtsDays;
        this.rst     = rst;
        this.rct     = rct;
        this.rut     = rut;
    }

    /** default constructor */
    public DtoTcWt() {
    }

    /** minimal constructor */
    public DtoTcWt(Integer wtsYear,
                   String  wtsDays) {
        this.wtsYear = wtsYear;
        this.wtsDays = wtsDays;
    }
//

//    public DtoTcWt(BigDecimal wtsYear,
//                   String  wtsDays) {
//        this.wtsYear = new Integer(wtsYear.intValue());
//        this.wtsDays = wtsDays;
//    }

//    public DtoTcWt(String wtsYear,
//                   String  wtsDays) {
//        this.wtsYear = new Integer(wtsYear);
//        this.wtsDays = wtsDays;
//    }

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
        return new ToStringBuilder(this).append("wtsYear", getWtsYear())
                                        .toString();
    }

    public boolean equals(Object other) {
        if (!(other instanceof DtoTcWt)) {
            return false;
        }

        DtoTcWt castOther = (DtoTcWt) other;

        return new EqualsBuilder().append(this.getWtsYear(),
                                          castOther.getWtsYear()).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder().append(getWtsYear()).toHashCode();
    }
}
