package essp.tables;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class SysCustomize implements Serializable {

    /** identifier field */
    private essp.tables.SysCustomizePk pk;

    /** nullable persistent field */
    private String value;

    /** full constructor */
    public SysCustomize(essp.tables.SysCustomizePk pk, String value) {
        this.pk = pk;
        this.value = value;
    }

    /** default constructor */
    public SysCustomize() {
    }

    /** minimal constructor */
    public SysCustomize(essp.tables.SysCustomizePk pk) {
        this.pk = pk;
    }

    public essp.tables.SysCustomizePk getPk() {
        return this.pk;
    }

    public void setPk(essp.tables.SysCustomizePk pk) {
        this.pk = pk;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("pk", getPk())
            .toString();
    }

    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof SysCustomize) ) return false;
        SysCustomize castOther = (SysCustomize) other;
        return new EqualsBuilder()
            .append(this.getPk(), castOther.getPk())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getPk())
            .toHashCode();
    }

}
