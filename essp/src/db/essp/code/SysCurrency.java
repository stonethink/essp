package db.essp.code;

import java.io.*;
import java.util.*;

import org.apache.commons.lang.builder.*;

/** @author Hibernate CodeGenerator */
public class SysCurrency implements Serializable {

    /** identifier field */
    private String currency;

    /** persistent field */
    private Long rid;

    /** nullable persistent field */
    private String name;

    /** nullable persistent field */
    private String symbol;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private Long sequence;

    private Long decimalNum;
    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** full constructor */
    public SysCurrency(String currency, Long rid, String name, String symbol, String description, Long sequence, String rst, Date rct, Date rut) {
        this.currency = currency;
        this.rid = rid;
        this.name = name;
        this.symbol = symbol;
        this.description = description;
        this.sequence = sequence;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
    }

    /** default constructor */
    public SysCurrency() {
    }

    /** minimal constructor */
    public SysCurrency(String currency, Long rid) {
        this.currency = currency;
        this.rid = rid;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    public String getSymbol() {
        return this.symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
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

    public Long getDecimalNum() {
        return decimalNum;
    }

    public void setRut(Date rut) {
        this.rut = rut;
    }

    public void setDecimalNum(Long decimalNum) {
        this.decimalNum = decimalNum;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("currency", getCurrency())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof SysCurrency) ) return false;
        SysCurrency castOther = (SysCurrency) other;
        return new EqualsBuilder()
            .append(this.getCurrency(), castOther.getCurrency())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCurrency())
            .toHashCode();
    }

}
