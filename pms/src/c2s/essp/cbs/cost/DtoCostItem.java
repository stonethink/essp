package c2s.essp.cbs.cost;

import java.util.Date;

import c2s.dto.DtoBase;

public class DtoCostItem extends DtoBase {
    private Long rid;
    private Long acntRid;
    private Long activityId;
    private String scope;
    private String attribute;
    private String subjectCode;
    private String name;
    private Double amt;
    private String currency;
    private String remark;
    private Date costDate;

    public static final String ACCOUNT_SCOPE = "account";
    public static final String ACTIVITY_SCOPE = "activity";
    public static final String WBS_SCOPE = "wbs";
    public Long getAcntRid() {
        return acntRid;
    }

    public Long getActivityId() {
        return activityId;
    }

    public Double getAmt() {
        return amt;
    }

    public String getAttribute() {
        return attribute;
    }

    public String getRemark() {
        return remark;
    }

    public Long getRid() {
        return rid;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public String getName() {
        return name;
    }

    public String getScope() {
        return scope;
    }

    public Date getCostDate() {
        return costDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public void setAmt(Double amt) {
        this.amt = amt;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setCostDate(Date costDate) {
        this.costDate = costDate;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

}
