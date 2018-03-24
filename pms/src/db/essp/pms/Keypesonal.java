package db.essp.pms;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class Keypesonal implements Serializable {

    /** identifier field */
    private Long rid;

    /** nullable persistent field */
    private String typeName;

    /** nullable persistent field */
    private String loginId;

    /** nullable persistent field */
    private String userName;

    /** nullable persistent field */
    private String organization;

    /** nullable persistent field */
    private String title;

    /** nullable persistent field */
    private String phone;

    /** nullable persistent field */
    private String fax;

    /** nullable persistent field */
    private String email;

    /** nullable persistent field */
    private String enable;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** persistent field */
    private db.essp.pms.Account account;

    /** full constructor */
    public Keypesonal(Long rid, String typeName, String loginId, String userName, String organization, String title, String phone, String fax, String email, String enable, String rst, Date rct, Date rut, db.essp.pms.Account account) {
        this.rid = rid;
        this.typeName = typeName;
        this.loginId = loginId;
        this.userName = userName;
        this.organization = organization;
        this.title = title;
        this.phone = phone;
        this.fax = fax;
        this.email = email;
        this.enable = enable;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
        this.account = account;
    }

    /** default constructor */
    public Keypesonal() {
    }

    /** minimal constructor */
    public Keypesonal(Long rid, db.essp.pms.Account account) {
        this.rid = rid;
        this.account = account;
    }

    public Long getRid() {
        return this.rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getLoginId() {
        return this.loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrganization() {
        return this.organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return this.fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEnable() {
        return this.enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
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
            .append("rid", getRid())
            .toString();
    }

}
