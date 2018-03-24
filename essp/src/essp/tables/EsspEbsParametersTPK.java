package essp.tables;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class EsspEbsParametersTPK implements Serializable {

    /** identifier field */
    private String kindCode;

    /** identifier field */
    private String code;

    /** full constructor */
    public EsspEbsParametersTPK(String kindCode, String code) {
        this.kindCode = kindCode;
        this.code = code;
    }

    /** default constructor */
    public EsspEbsParametersTPK() {
    }

    /** 
     *                @hibernate.property
     *                 column="KIND_CODE"
     *                 length="20"
     *             
     */
    public String getKindCode() {
        return this.kindCode;
    }

    public void setKindCode(String kindCode) {
        this.kindCode = kindCode;
    }

    /** 
     *                @hibernate.property
     *                 column="CODE"
     *                 length="10"
     *             
     */
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("kindCode", getKindCode())
            .append("code", getCode())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof EsspEbsParametersTPK) ) return false;
        EsspEbsParametersTPK castOther = (EsspEbsParametersTPK) other;
        return new EqualsBuilder()
            .append(this.getKindCode(), castOther.getKindCode())
            .append(this.getCode(), castOther.getCode())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getKindCode())
            .append(getCode())
            .toHashCode();
    }

}
