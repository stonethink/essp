package c2s.essp.pms.account;

import java.util.Date;
import c2s.dto.DtoBase;

public class DtoNoneLaborResItem extends DtoBase{

    /** identifier field */
    private Long rid;

    private Long envRid;
    private Long acntRid;

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


    public Long getRid() {
        return this.rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public Long getEnvRid() {
        return this.envRid;
    }

    public void setEnvRid(Long envRid) {
        this.envRid = envRid;
    }

    public Long getAcntRid() {
        return this.acntRid;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
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

}
