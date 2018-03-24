package essp.tables;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class SysCustomizePk implements Serializable {

    /** identifier field */
    private String userid;

    /** identifier field */
    private String name;
    private String condition;

    /** full constructor */
    public SysCustomizePk(String userid, String name) {
        this.userid = userid;
        this.name = name;
    }

    /** default constructor */
    public SysCustomizePk() {
    }

    public String getUserid() {
        return this.userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return this.name;
    }

    public String getCondition() {
        return condition;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("userid", getUserid())
            .append("name", getName())
            .toString();
    }

    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof SysCustomizePk) ) return false;
        SysCustomizePk castOther = (SysCustomizePk) other;
        return new EqualsBuilder()
            .append(this.getUserid(), castOther.getUserid())
            .append(this.getName(), castOther.getName())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getUserid())
            .append(getName())
            .toHashCode();
    }

}
