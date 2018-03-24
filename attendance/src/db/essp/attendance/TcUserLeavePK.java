package db.essp.attendance;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class TcUserLeavePK implements Serializable {

    /** identifier field */
    private String loginId;

    /** identifier field */
    private String leaveName;

    /** full constructor */
    public TcUserLeavePK(String loginId, String leaveName) {
        this.loginId = loginId;
        this.leaveName = leaveName;
    }

    /** default constructor */
    public TcUserLeavePK() {
    }

    public String getLoginId() {
        return this.loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getLeaveName() {
        return this.leaveName;
    }

    public void setLeaveName(String leaveName) {
        this.leaveName = leaveName;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("loginId", getLoginId())
            .append("leaveName", getLeaveName())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof TcUserLeavePK) ) return false;
        TcUserLeavePK castOther = (TcUserLeavePK) other;
        return new EqualsBuilder()
            .append(this.getLoginId(), castOther.getLoginId())
            .append(this.getLeaveName(), castOther.getLeaveName())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getLoginId())
            .append(getLeaveName())
            .toHashCode();
    }

}
