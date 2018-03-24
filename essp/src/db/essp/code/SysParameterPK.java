package db.essp.code;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class SysParameterPK implements Serializable {

    /** identifier field */
    private String kind;

    /** identifier field */
    private String code;

    /** full constructor */
    public SysParameterPK(String kind, String code) {
        this.kind = kind;
        this.code = code;
    }

    /** default constructor */
    public SysParameterPK() {
    }

    public String getKind() {
        return this.kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("kind", getKind())
            .append("code", getCode())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof SysParameterPK) ) return false;
        SysParameterPK castOther = (SysParameterPK) other;
        return new EqualsBuilder()
            .append(this.getKind(), castOther.getKind())
            .append(this.getCode(), castOther.getCode())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getKind())
            .append(getCode())
            .toHashCode();
    }

}
