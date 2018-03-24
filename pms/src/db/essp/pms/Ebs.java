package db.essp.pms;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class Ebs implements Serializable {

    /** identifier field */
    private Long rid;

    /** persistent field */
    private String ebsId;

    /** nullable persistent field */
    private String ebsName;

    /** nullable persistent field */
    private String ebsManager;

    /** nullable persistent field */
    private String ebsStatus;

    /** nullable persistent field */
    private String ebsDescription;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** nullable persistent field */
    private db.essp.pms.Ebs parent;

    /** persistent field */
    private Set childs;

    /** persistent field */
    private Set accounts;

    /** full constructor */
    public Ebs(Long rid, String ebsId, String ebsName, String ebsManager, String ebsStatus, String ebsDescription, String rst, Date rct, Date rut, db.essp.pms.Ebs parent, Set childs, Set accounts) {
        this.rid = rid;
        this.ebsId = ebsId;
        this.ebsName = ebsName;
        this.ebsManager = ebsManager;
        this.ebsStatus = ebsStatus;
        this.ebsDescription = ebsDescription;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
        this.parent = parent;
        this.childs = childs;
        this.accounts = accounts;
    }

    /** default constructor */
    public Ebs() {
    }

    /** minimal constructor */
    public Ebs(Long rid, String ebsId, Set childs, Set accounts) {
        this.rid = rid;
        this.ebsId = ebsId;
        this.childs = childs;
        this.accounts = accounts;
    }

    public Long getRid() {
        return this.rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getEbsId() {
        return this.ebsId;
    }

    public void setEbsId(String ebsId) {
        this.ebsId = ebsId;
    }

    public String getEbsName() {
        return this.ebsName;
    }

    public void setEbsName(String ebsName) {
        this.ebsName = ebsName;
    }

    public String getEbsManager() {
        return this.ebsManager;
    }

    public void setEbsManager(String ebsManager) {
        this.ebsManager = ebsManager;
    }

    public String getEbsStatus() {
        return this.ebsStatus;
    }

    public void setEbsStatus(String ebsStatus) {
        this.ebsStatus = ebsStatus;
    }

    public String getEbsDescription() {
        return this.ebsDescription;
    }

    public void setEbsDescription(String ebsDescription) {
        this.ebsDescription = ebsDescription;
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

    public db.essp.pms.Ebs getParent() {
        return this.parent;
    }

    public void setParent(db.essp.pms.Ebs parent) {
        this.parent = parent;
    }

    public Set getChilds() {
        return this.childs;
    }

    public void setChilds(Set childs) {
        this.childs = childs;
    }

    public Set getAccounts() {
        return this.accounts;
    }

    public void setAccounts(Set accounts) {
        this.accounts = accounts;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("rid", getRid())
            .toString();
    }

}
