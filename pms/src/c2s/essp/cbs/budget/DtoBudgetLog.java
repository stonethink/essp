package c2s.essp.cbs.budget;

import java.util.Date;

import c2s.dto.DtoBase;

public class DtoBudgetLog extends DtoBase {
    private Long rid;
    private Date logDate;
    private String baseId;  //基线版本ID
    private Double basePm;  //基线计划的人月数
    private Double baseAmt; //基线计划的金额数
    private Double totalBugetAmt;
    private Double totalBugetPm;
    private Double changeBugetAmt;
    private Double changeBugetPm;
    private String changedBy;
    private String reson;
    private Long budgetRid;

    public DtoBudgetLog() {
    }

    public Date getLogDate() {
        return logDate;
    }

    public Double getBaseAmt() {
        return baseAmt;
    }

    public String getBaseId() {
        return baseId;
    }

    public Double getBasePm() {
        return basePm;
    }

    public Long getBudgetRid() {
        return budgetRid;
    }

    public Double getChangeBugetAmt() {
        return changeBugetAmt;
    }

    public Double getChangeBugetPm() {
        return changeBugetPm;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public String getReson() {
        return reson;
    }

    public Long getRid() {
        return rid;
    }

    public Double getTotalBugetAmt() {
        return totalBugetAmt;
    }

    public Double getTotalBugetPm() {
        return totalBugetPm;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public void setBaseAmt(Double baseAmt) {
        this.baseAmt = baseAmt;
    }

    public void setBaseId(String baseId) {
        this.baseId = baseId;
    }

    public void setBasePm(Double basePm) {
        this.basePm = basePm;
    }

    public void setBudgetRid(Long budgetRid) {
        this.budgetRid = budgetRid;
    }

    public void setChangeBugetAmt(Double changeBugetAmt) {
        this.changeBugetAmt = changeBugetAmt;
    }

    public void setChangeBugetPm(Double changeBugetPm) {
        this.changeBugetPm = changeBugetPm;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }

    public void setReson(String reson) {
        this.reson = reson;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public void setTotalBugetAmt(Double totalBugetAmt) {
        this.totalBugetAmt = totalBugetAmt;
    }

    public void setTotalBugetPm(Double totalBugetPm) {
        this.totalBugetPm = totalBugetPm;
    }


}
