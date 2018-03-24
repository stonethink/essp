package c2s.essp.timecard.worktime;

import c2s.dto.DtoBase;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

import java.util.Date;


/**
 *        @hibernate.class
 *         table="tc_worktime"
 *
 */
public class DtoTcWorktime extends DtoBase implements Serializable {
    /** identifier field */
    private Long rid;

    /** persistent field */
    private String wtStarttime;

    /** persistent field */
    private String wtEndtime;

    /** nullable persistent field */
    private String rst;

    /** persistent field */
    private Date rut;

    /** persistent field */
    private Date rct;

    /** full constructor */
    public DtoTcWorktime(Long   rid,
                         String wtStarttime,
                         String wtEndtime,
                         String rst,
                         Date   rut,
                         Date   rct) {
        this.rid         = rid;
        this.wtStarttime = wtStarttime;
        this.wtEndtime   = wtEndtime;
        this.rst         = rst;
        this.rut         = rut;
        this.rct         = rct;
    }

    /** default constructor */
    public DtoTcWorktime() {
    }

    /** minimal constructor */
    public DtoTcWorktime(Long   rid,
                         String wtStarttime,
                         String wtEndtime,
                         Date   rut,
                         Date   rct) {
        this.rid         = rid;
        this.wtStarttime = wtStarttime;
        this.wtEndtime   = wtEndtime;
        this.rut         = rut;
        this.rct         = rct;
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
     *             column="WT_STARTTIME"
     *             length="20"
     *             not-null="true"
     *
     */
    public String getWtStarttime() {
        return this.wtStarttime;
    }

    public void setWtStarttime(String wtStarttime) {
        this.wtStarttime = wtStarttime;
    }

    /**
     *            @hibernate.property
     *             column="WT_ENDTIME"
     *             length="20"
     *             not-null="true"
     *
     */
    public String getWtEndtime() {
        return this.wtEndtime;
    }

    public void setWtEndtime(String wtEndtime) {
        this.wtEndtime = wtEndtime;
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
     *             column="RUT"
     *             length="10"
     *             not-null="true"
     *
     */
    public Date getRut() {
        return this.rut;
    }

    public void setRut(Date rut) {
        this.rut = rut;
    }

    /**
     *            @hibernate.property
     *             column="RCT"
     *             length="10"
     *             not-null="true"
     *
     */
    public Date getRct() {
        return this.rct;
    }

    public void setRct(Date rct) {
        this.rct = rct;
    }

    public String toString() {
        return new ToStringBuilder(this).append("rid", getRid()).toString();
    }

    public boolean equals(Object other) {
        if (!(other instanceof DtoTcWorktime)) {
            return false;
        }

        DtoTcWorktime castOther = (DtoTcWorktime) other;

        return new EqualsBuilder().append(this.getRid(), castOther.getRid())
                                  .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder().append(getRid()).toHashCode();
    }
}
