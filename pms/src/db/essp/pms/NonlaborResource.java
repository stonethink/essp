package db.essp.pms;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class NonlaborResource implements Serializable {

    /** identifier field */
    private Long rid;

    /** nullable persistent field */
    private String envName;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** persistent field */
    private db.essp.pms.Account account;

    /** persistent field */
    private Set nonlaborResItems;

    /** full constructor */
    public NonlaborResource(Long rid, String envName, String rst, Date rct, Date rut, db.essp.pms.Account account, Set nonlaborResItems) {
        this.rid = rid;
        this.envName = envName;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
        this.account = account;
        this.nonlaborResItems = nonlaborResItems;
    }

    /** default constructor */
    public NonlaborResource() {
    }

    /** minimal constructor */
    public NonlaborResource(Long rid, db.essp.pms.Account account, Set nonlaborResItems) {
        this.rid = rid;
        this.account = account;
        this.nonlaborResItems = nonlaborResItems;
    }

    public Long getRid() {
        return this.rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getEnvName() {
        return this.envName;
    }

    public void setEnvName(String envName) {
        this.envName = envName;
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

    public Set getNonlaborResItems() {
        return this.nonlaborResItems;
    }

    public void setNonlaborResItems(Set nonlaborResItems) {
        this.nonlaborResItems = nonlaborResItems;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("rid", getRid())
            .toString();
    }

}
