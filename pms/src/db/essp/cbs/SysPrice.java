package db.essp.cbs;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class SysPrice implements Serializable {

    /** identifier field */
    private Long rid;

    /** nullable persistent field */
    private Long acntRid;

    /** nullable persistent field */
    private String catalog;

    /** nullable persistent field */
    private String id;

    /** nullable persistent field */
    private String name;

    /** nullable persistent field */
    private String subjectCode;

    /** nullable persistent field */
    private String unit;

    /** nullable persistent field */
    private String currency;

    /** nullable persistent field */
    private Double price;

    /** nullable persistent field */
    private String priceScope;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** full constructor */
    public SysPrice(Long rid, Long acntRid, String catalog, String id, String name, String subjectCode, String unit, String currency, Double price, String priceScope, String rst, Date rct, Date rut) {
        this.rid = rid;
        this.acntRid = acntRid;
        this.catalog = catalog;
        this.id = id;
        this.name = name;
        this.subjectCode = subjectCode;
        this.unit = unit;
        this.currency = currency;
        this.price = price;
        this.priceScope = priceScope;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
    }

    /** default constructor */
    public SysPrice() {
    }

    /** minimal constructor */
    public SysPrice(Long rid) {
        this.rid = rid;
    }

    public Long getRid() {
        return this.rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public Long getAcntRid() {
        return this.acntRid;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public String getCatalog() {
        return this.catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPriceScope() {
        return this.priceScope;
    }

    public void setPriceScope(String priceScope) {
        this.priceScope = priceScope;
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
            .append("rid", getRid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof SysPrice) ) return false;
        SysPrice castOther = (SysPrice) other;
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
