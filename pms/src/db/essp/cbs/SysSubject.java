package db.essp.cbs;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import java.sql.Blob;

/** @author Hibernate CodeGenerator */
public class SysSubject implements Serializable {

    /** identifier field */
    private String cbsType;

    /** nullable persistent field */
    private Long rid;

    /** nullable persistent field */
    private Blob cbsDefine;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** full constructor */
    public SysSubject(String cbsType, Long rid, Blob cbsDefine, String rst, Date rct, Date rut) {
        this.cbsType = cbsType;
        this.rid = rid;
        this.cbsDefine = cbsDefine;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
    }

    /** default constructor */
    public SysSubject() {
    }

    /** minimal constructor */
    public SysSubject(String cbsType) {
        this.cbsType = cbsType;
    }

    public String getCbsType() {
        return this.cbsType;
    }

    public void setCbsType(String cbsType) {
        this.cbsType = cbsType;
    }

    public Long getRid() {
        return this.rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public Blob getCbsDefine() {
        return this.cbsDefine;
    }

    public void setCbsDefine(Blob cbsDefine) {
        this.cbsDefine = cbsDefine;
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
            .append("cbsType", getCbsType())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof SysSubject) ) return false;
        SysSubject castOther = (SysSubject) other;
        return new EqualsBuilder()
            .append(this.getCbsType(), castOther.getCbsType())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCbsType())
            .toHashCode();
    }

}
