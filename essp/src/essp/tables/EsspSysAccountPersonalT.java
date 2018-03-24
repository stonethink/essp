package essp.tables;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="essp_sys_account_personal_t"
 *     
*/
public class EsspSysAccountPersonalT implements Serializable {

    /** identifier field */
    private long accountId;

    /** identifier field */
    private String role;

    /** identifier field */
    private String hrCode;

    /** identifier field */
    private Date startdate;

    /** identifier field */
    private Date finishdate;

    /** identifier field */
    private long status;

    /** identifier field */
    private long seq;

    /** full constructor */
    public EsspSysAccountPersonalT(long accountId, String role, String hrCode, Date startdate, Date finishdate, long status, long seq) {
        this.accountId = accountId;
        this.role = role;
        this.hrCode = hrCode;
        this.startdate = startdate;
        this.finishdate = finishdate;
        this.status = status;
        this.seq = seq;
    }

    /** default constructor */
    public EsspSysAccountPersonalT() {
    }

    /** 
     *                @hibernate.property
     *                 column="ACCOUNT_ID"
     *             
     */
    public long getAccountId() {
        return this.accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    /** 
     *                @hibernate.property
     *                 column="ROLE"
     *             
     */
    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    /** 
     *                @hibernate.property
     *                 column="HR_CODE"
     *             
     */
    public String getHrCode() {
        return this.hrCode;
    }

    public void setHrCode(String hrCode) {
        this.hrCode = hrCode;
    }

    /** 
     *                @hibernate.property
     *                 column="STARTDATE"
     *             
     */
    public Date getStartdate() {
        return this.startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    /** 
     *                @hibernate.property
     *                 column="FINISHDATE"
     *             
     */
    public Date getFinishdate() {
        return this.finishdate;
    }

    public void setFinishdate(Date finishdate) {
        this.finishdate = finishdate;
    }

    /** 
     *                @hibernate.property
     *                 column="STATUS"
     *             
     */
    public long getStatus() {
        return this.status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    /** 
     *                @hibernate.property
     *                 column="SEQ"
     *             
     */
    public long getSeq() {
        return this.seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("accountId", getAccountId())
            .append("role", getRole())
            .append("hrCode", getHrCode())
            .append("startdate", getStartdate())
            .append("finishdate", getFinishdate())
            .append("status", getStatus())
            .append("seq", getSeq())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof EsspSysAccountPersonalT) ) return false;
        EsspSysAccountPersonalT castOther = (EsspSysAccountPersonalT) other;
        return new EqualsBuilder()
            .append(this.getAccountId(), castOther.getAccountId())
            .append(this.getRole(), castOther.getRole())
            .append(this.getHrCode(), castOther.getHrCode())
            .append(this.getStartdate(), castOther.getStartdate())
            .append(this.getFinishdate(), castOther.getFinishdate())
            .append(this.getStatus(), castOther.getStatus())
            .append(this.getSeq(), castOther.getSeq())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getAccountId())
            .append(getRole())
            .append(getHrCode())
            .append(getStartdate())
            .append(getFinishdate())
            .append(getStatus())
            .append(getSeq())
            .toHashCode();
    }

}
