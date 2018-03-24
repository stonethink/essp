package db.essp.code;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class SysParameter implements Serializable {

    /** identifier field */
    private db.essp.code.SysParameterPK comp_id;

    /** nullable persistent field */
    private Long rid;

    /** nullable persistent field */
    private String name;

    /** nullable persistent field */
    private String alias;

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

    /** full constructor */
    public SysParameter(db.essp.code.SysParameterPK comp_id, Long rid, String name, String alias, String description, Long sequence, String rst, Date rct, Date rut) {
        this.comp_id = comp_id;
        this.rid = rid;
        this.name = name;
        this.alias = alias;
        this.description = description;
        this.sequence = sequence;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
    }

    /** default constructor */
    public SysParameter() {
    }

    /** minimal constructor */
    public SysParameter(db.essp.code.SysParameterPK comp_id) {
        this.comp_id = comp_id;
    }

    public db.essp.code.SysParameterPK getComp_id() {
        return this.comp_id;
    }

    public void setComp_id(db.essp.code.SysParameterPK comp_id) {
        this.comp_id = comp_id;
    }

    public Long getRid() {
        return this.rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
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

    public String toString() {
        return new ToStringBuilder(this)
            .append("comp_id", getComp_id())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof SysParameter) ) return false;
        SysParameter castOther = (SysParameter) other;
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
