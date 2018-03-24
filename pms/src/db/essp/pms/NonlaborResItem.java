package db.essp.pms;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class NonlaborResItem implements Serializable {

    /** identifier field */
    private Long rid;

    /** nullable persistent field */
    private String resourceName;

    /** nullable persistent field */
    private String resourceId;

    /** nullable persistent field */
    private String requirement;

    /** nullable persistent field */
    private Date startDate;

    /** nullable persistent field */
    private Date finishDate;

    /** nullable persistent field */
    private Long resourceNumber;

    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** persistent field */
    private db.essp.pms.NonlaborResource nonlaborResource;

    /** persistent field */
    private db.essp.pms.Account account;

    /** full constructor */
    public NonlaborResItem(Long rid, String resourceName, String resourceId, String requirement, Date startDate, Date finishDate, Long resourceNumber, String remark, String rst, Date rct, Date rut, db.essp.pms.NonlaborResource nonlaborResource, db.essp.pms.Account account) {
        this.rid = rid;
        this.resourceName = resourceName;
        this.resourceId = resourceId;
        this.requirement = requirement;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.resourceNumber = resourceNumber;
        this.remark = remark;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
        this.nonlaborResource = nonlaborResource;
        this.account = account;
    }

    /** default constructor */
    public NonlaborResItem() {
    }

    /** minimal constructor */
    public NonlaborResItem(Long rid, db.essp.pms.NonlaborResource nonlaborResource, db.essp.pms.Account account) {
        this.rid = rid;
        this.nonlaborResource = nonlaborResource;
        this.account = account;
    }

    public Long getRid() {
        return this.rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getResourceName() {
        return this.resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceId() {
        return this.resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getRequirement() {
        return this.requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return this.finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Long getResourceNumber() {
        return this.resourceNumber;
    }

    public void setResourceNumber(Long resourceNumber) {
        this.resourceNumber = resourceNumber;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public db.essp.pms.NonlaborResource getNonlaborResource() {
        return this.nonlaborResource;
    }

    public void setNonlaborResource(db.essp.pms.NonlaborResource nonlaborResource) {
        this.nonlaborResource = nonlaborResource;
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
