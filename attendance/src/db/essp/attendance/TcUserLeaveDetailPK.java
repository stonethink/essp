package db.essp.attendance;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class TcUserLeaveDetailPK implements Serializable {

    /** identifier field */
    private String loginId;

    /** identifier field */
    private String leaveName;

    /** identifier field */
    private Long yearth;

    /** full constructor */
    public TcUserLeaveDetailPK(String loginId, String leaveName, Long yearth) {
        this.loginId = loginId;
        this.leaveName = leaveName;
        this.yearth = yearth;
    }

    /** default constructor */
    public TcUserLeaveDetailPK() {
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

    public Long getYearth() {
        return this.yearth;
    }

    public void setYearth(Long yearth) {
        this.yearth = yearth;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("loginId", getLoginId())
            .append("leaveName", getLeaveName())
            .append("yearth", getYearth())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof TcUserLeaveDetailPK) ) return false;
        TcUserLeaveDetailPK castOther = (TcUserLeaveDetailPK) other;
        return new EqualsBuilder()
            .append(this.getLoginId(), castOther.getLoginId())
            .append(this.getLeaveName(), castOther.getLeaveName())
            .append(this.getYearth(), castOther.getYearth())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getLoginId())
            .append(getLeaveName())
            .append(getYearth())
            .toHashCode();
    }

}
