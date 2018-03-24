package db.essp.pms;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class Issue implements Serializable {

    /** identifier field */
    private db.essp.pms.IssuePK pk;

    /** full constructor */
    public Issue(db.essp.pms.IssuePK pk) {
        this.pk = pk;
    }

    /** default constructor */
    public Issue() {
    }

    public db.essp.pms.IssuePK getPk() {
        return this.pk;
    }

    public void setPk(db.essp.pms.IssuePK pk) {
        this.pk = pk;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("pk", getPk())
            .toString();
    }

    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof Issue) ) return false;
        Issue castOther = (Issue) other;
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
