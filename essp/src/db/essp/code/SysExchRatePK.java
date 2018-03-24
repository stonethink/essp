package db.essp.code;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class SysExchRatePK implements Serializable {

    /** identifier field */
    private Date exchDate;

    /** identifier field */
    private String fromCurrency;

    /** identifier field */
    private String toCurrency;

    /** full constructor */
    public SysExchRatePK(Date exchDate, String fromCurrency, String toCurrency) {
        this.exchDate = exchDate;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
    }

    /** default constructor */
    public SysExchRatePK() {
    }

    public Date getExchDate() {
        return this.exchDate;
    }

    public void setExchDate(Date exchDate) {
        this.exchDate = exchDate;
    }

    public String getFromCurrency() {
        return this.fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public String getToCurrency() {
        return this.toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("exchDate", getExchDate())
            .append("fromCurrency", getFromCurrency())
            .append("toCurrency", getToCurrency())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof SysExchRatePK) ) return false;
        SysExchRatePK castOther = (SysExchRatePK) other;
        return new EqualsBuilder()
            .append(this.getExchDate(), castOther.getExchDate())
            .append(this.getFromCurrency(), castOther.getFromCurrency())
            .append(this.getToCurrency(), castOther.getToCurrency())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getExchDate())
            .append(getFromCurrency())
            .append(getToCurrency())
            .toHashCode();
    }

}
