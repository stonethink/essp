package db.essp.issue;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;


public class IssueDiscussMailBak implements Serializable{
    /** identifier field */
    private Long rid;
    /** nullable persistent field */
    private String acntrid;
    /** nullable persistent field */
    private String issuetype;

    /** nullable persistent field */
    private String discusstype;

    /** nullable persistent field */
    private String mailto;

    /** nullable persistent field */
    private String mailcc;

    public IssueDiscussMailBak (String acntrid,String issuetype,String discusstype,String to,String cc){
        this.acntrid = acntrid;
        this.issuetype = issuetype;
        this.discusstype = discusstype;
        this.mailto = to;
        this.mailcc = cc;
    }
    public IssueDiscussMailBak(){

    }
    public Long getRid() {
        return rid;
    }

    public String getIssuetype() {
        return issuetype;
    }

    public String getDiscusstype() {
        return discusstype;
    }

    public String getMailto() {
        return mailto;
    }

    public String getMailcc() {
        return mailcc;
    }

    public String getAcntrid() {
        return acntrid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public void setIssuetype(String issuetype) {
        this.issuetype = issuetype;
    }

    public void setDiscusstype(String discusstype) {
        this.discusstype = discusstype;
    }

    public void setMailto(String mailto) {
        this.mailto = mailto;
    }

    public void setMailcc(String mailcc) {
        this.mailcc = mailcc;
    }

    public void setAcntrid(String acntrid) {
        this.acntrid = acntrid;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("rid", getRid())
            .toString();
    }

    public boolean equals(Object other) {
        if (!(other instanceof IssueDiscussMailBak))return false;
        IssueDiscussMailBak castOther = (IssueDiscussMailBak) other;
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
