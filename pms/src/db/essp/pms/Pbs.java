package db.essp.pms;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class Pbs implements Serializable {

    /** identifier field */
    private db.essp.pms.PbsPK pk;

    /** nullable persistent field */
    private String productCode;

    /** persistent field */
    private String productName;

    /** nullable persistent field */
    private String pbsReferrence;

    /** nullable persistent field */
    private String pbsManager;

    /** nullable persistent field */
    private String pbsBrief;

    /** nullable persistent field */
    private Date plannedFinish;

    /** nullable persistent field */
    private Date actualFinish;

    /** nullable persistent field */
    private String pbsStatus;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** nullable persistent field */
    private db.essp.pms.Account account;

    /** nullable persistent field */
    private db.essp.pms.Pbs parent;

    /** persistent field */
    private List childs;

    /** persistent field */
    private Set files;

    /** persistent field */
    private Set assignments;

    /** full constructor */
    public Pbs(db.essp.pms.PbsPK pk, String productCode, String productName, String pbsReferrence, String pbsManager, String pbsBrief, Date plannedFinish, Date actualFinish, String pbsStatus, String rst, Date rct, Date rut, db.essp.pms.Account account, db.essp.pms.Pbs parent, List childs, Set files, Set assignments) {
        this.pk = pk;
        this.productCode = productCode;
        this.productName = productName;
        this.pbsReferrence = pbsReferrence;
        this.pbsManager = pbsManager;
        this.pbsBrief = pbsBrief;
        this.plannedFinish = plannedFinish;
        this.actualFinish = actualFinish;
        this.pbsStatus = pbsStatus;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
        this.account = account;
        this.parent = parent;
        this.childs = childs;
        this.files = files;
        this.assignments = assignments;
    }

    /** default constructor */
    public Pbs() {
    }

    /** minimal constructor */
    public Pbs(db.essp.pms.PbsPK pk, String productName, List childs, Set files, Set assignments) {
        this.pk = pk;
        this.productName = productName;
        this.childs = childs;
        this.files = files;
        this.assignments = assignments;
    }

    public db.essp.pms.PbsPK getPk() {
        return this.pk;
    }

    public void setPk(db.essp.pms.PbsPK pk) {
        this.pk = pk;
    }

    public String getProductCode() {
        return this.productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPbsReferrence() {
        return this.pbsReferrence;
    }

    public void setPbsReferrence(String pbsReferrence) {
        this.pbsReferrence = pbsReferrence;
    }

    public String getPbsManager() {
        return this.pbsManager;
    }

    public void setPbsManager(String pbsManager) {
        this.pbsManager = pbsManager;
    }

    public String getPbsBrief() {
        return this.pbsBrief;
    }

    public void setPbsBrief(String pbsBrief) {
        this.pbsBrief = pbsBrief;
    }

    public Date getPlannedFinish() {
        return this.plannedFinish;
    }

    public void setPlannedFinish(Date plannedFinish) {
        this.plannedFinish = plannedFinish;
    }

    public Date getActualFinish() {
        return this.actualFinish;
    }

    public void setActualFinish(Date actualFinish) {
        this.actualFinish = actualFinish;
    }

    public String getPbsStatus() {
        return this.pbsStatus;
    }

    public void setPbsStatus(String pbsStatus) {
        this.pbsStatus = pbsStatus;
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

    public db.essp.pms.Pbs getParent() {
        return this.parent;
    }

    public void setParent(db.essp.pms.Pbs parent) {
        this.parent = parent;
    }

    public List getChilds() {
        return this.childs;
    }

    public void setChilds(List childs) {
        this.childs = childs;
    }

    public Set getFiles() {
        return this.files;
    }

    public void setFiles(Set files) {
        this.files = files;
    }

    public Set getAssignments() {
        return this.assignments;
    }

    public void setAssignments(Set assignments) {
        this.assignments = assignments;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("pk", getPk())
            .toString();
    }

}
