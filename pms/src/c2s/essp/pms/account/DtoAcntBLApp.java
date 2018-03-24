package c2s.essp.pms.account;

import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import c2s.dto.DtoBase;

/** @author Hibernate CodeGenerator */
public class DtoAcntBLApp extends DtoBase {

    /** identifier field */
    private Long rid;

    /** nullable persistent field */
    private Long acntRid;

    /** nullable persistent field */
    private String baselineId;

    /** nullable persistent field */
    private String filledBy;

    /** nullable persistent field */
    private Date filledDate;

    /** nullable persistent field */
    private String appStatus;

    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** full constructor */
    public DtoAcntBLApp(Long rid, Long acntRid, String baselineId, String filledBy, Date filledDate, String appStatus, String remark, String rst, Date rct, Date rut) {
        this.rid = rid;
        this.acntRid = acntRid;
        this.baselineId = baselineId;
        this.filledBy = filledBy;
        this.filledDate = filledDate;
        this.appStatus = appStatus;
        this.remark = remark;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
    }

    /** default constructor */
    public DtoAcntBLApp() {
    }

    /** minimal constructor */
    public DtoAcntBLApp(Long rid) {
        this.rid = rid;
    }

    public Long getRid() {
        return this.rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public Long getAcntRid() {
        return this.acntRid;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public String getBaselineId() {
        return this.baselineId;
    }

    public void setBaselineId(String baselineId) {
        this.baselineId = baselineId;
    }

    public String getFilledBy() {
        return this.filledBy;
    }

    public void setFilledBy(String filledBy) {
        this.filledBy = filledBy;
    }

    public Date getFilledDate() {
        return this.filledDate;
    }

    public void setFilledDate(Date filledDate) {
        this.filledDate = filledDate;
    }

    public String getAppStatus() {
        return this.appStatus;
    }

    public void setAppStatus(String appStatus) {
        this.appStatus = appStatus;
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

    public String toString() {
        return new ToStringBuilder(this)
            .append("rid", getRid())
            .toString();
    }

}
