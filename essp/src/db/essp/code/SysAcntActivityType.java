package db.essp.code;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class SysAcntActivityType implements Serializable {

    /** identifier field */
    private Long rid;

    /** persistent field */
    private String type;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private Long sequence;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** persistent field */
    private Set sysActivityCodes;

    /** full constructor */
    public SysAcntActivityType(Long rid, String type, String description, Long sequence, String rst, Date rct, Date rut, Set sysActivityCodes) {
        this.rid = rid;
        this.type = type;
        this.description = description;
        this.sequence = sequence;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
        this.sysActivityCodes = sysActivityCodes;
    }

    /** default constructor */
    public SysAcntActivityType() {
    }

    /** minimal constructor */
    public SysAcntActivityType(Long rid, String type, Set sysActivityCodes) {
        this.rid = rid;
        this.type = type;
        this.sysActivityCodes = sysActivityCodes;
    }

    public Long getRid() {
        return this.rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getSequence() {
        return this.sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
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

    public Set getSysActivityCodes() {
        return this.sysActivityCodes;
    }

    public void setSysActivityCodes(Set sysActivityCodes) {
        this.sysActivityCodes = sysActivityCodes;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("rid", getRid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof SysAcntActivityType) ) return false;
        SysAcntActivityType castOther = (SysAcntActivityType) other;
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
