package db.essp.pms;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class AccountSeq implements Serializable {

    /** identifier field */
    private db.essp.pms.AccountSeqPK pk;

    /** nullable persistent field */
    private Long lastRid;

    /** nullable persistent field */
    private Long rootRid;

    /** nullable persistent field */
    private Long seq;

    /** nullable persistent field */
    private Long step;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** nullable persistent field */
    private db.essp.pms.Account account;

    /** full constructor */
    public AccountSeq(db.essp.pms.AccountSeqPK pk, Long lastRid, Long rootRid, Long seq, Long step, String rst, Date rct, Date rut, db.essp.pms.Account account) {
        this.pk = pk;
        this.lastRid = lastRid;
        this.rootRid = rootRid;
        this.seq = seq;
        this.step = step;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
        this.account = account;
    }

    /** default constructor */
    public AccountSeq() {
    }

    /** minimal constructor */
    public AccountSeq(db.essp.pms.AccountSeqPK pk) {
        this.pk = pk;
    }

    public db.essp.pms.AccountSeqPK getPk() {
        return this.pk;
    }

    public void setPk(db.essp.pms.AccountSeqPK pk) {
        this.pk = pk;
    }

    public Long getLastRid() {
        return this.lastRid;
    }

    public void setLastRid(Long lastRid) {
        this.lastRid = lastRid;
    }

    public Long getRootRid() {
        return this.rootRid;
    }

    public void setRootRid(Long rootRid) {
        this.rootRid = rootRid;
    }

    public Long getSeq() {
        return this.seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public Long getStep() {
        return this.step;
    }

    public void setStep(Long step) {
        this.step = step;
    }

    public String getRst() {
        return this.rst;
    }

    public void setRst(String rst) {
        this.rst = rst;
    }

    public Date getRct() {
        return this.rct;
    }

    public void setRct(Date rct) {
        this.rct = rct;
    }

    public Date getRut() {
        return this.rut;
    }

    public void setRut(Date rut) {
        this.rut = rut;
    }

    public db.essp.pms.Account getAccount() {
        return this.account;
    }

    public void setAccount(db.essp.pms.Account account) {
        this.account = account;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("pk", getPk())
            .toString();
    }

    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof AccountSeq) ) return false;
        AccountSeq castOther = (AccountSeq) other;
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
