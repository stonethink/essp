package c2s.essp.pms.quality.activity;

import c2s.dto.DtoBase;

public class DtoQualityActivity extends DtoBase {
    public DtoQualityActivity() {
    }

    public String name;
    public String type;
    public String production;
    public String criterion;
    public String method;
    public String remark;
    public Double planSize;
    public Double actualSize;
    public Long actualCaseNum;
    public Double actualDensity;
    public Long actualDefectNum;
    public Double actualDefectRate;
    public String conclusionBy;
    public String conclusion;
    public Long acntRid;
    public Long activityRid;
    private Double planDensity;
    private Double planDefectRate;
    private String densityUnits;
    private String defectRateUnits;
    private String code;
    private String reason;
    private String isPass;
    public String getName() {
       return this.name;
   }

   public void setName(String name) {
       this.name = name;
   }

    public Long getActualCaseNum() {
        return actualCaseNum;
    }

    public Double getActualDefectRate() {
        return actualDefectRate;
    }

    public Long getActualDefectNum() {
        return actualDefectNum;
    }

    public Double getActualDensity() {
        return actualDensity;
    }

    public Double getActualSize() {
        return actualSize;
    }

    public String getConclusion() {
        return conclusion;
    }

    public String getConclusionBy() {
        return conclusionBy;
    }

    public String getCriterion() {
        return criterion;
    }

    public String getMethod() {
        return method;
    }

    public Double getPlanSize() {
        return planSize;
    }

    public String getProduction() {
        return production;
    }

    public String getRemark() {
        return remark;
    }

    public String getType() {
        return type;
    }


    public Long getAcntRid() {
        return acntRid;
    }

    public Long getActivityRid() {
        return activityRid;
    }

    public Double getPlanDensity() {
        return planDensity;
    }

    public Double getPlanDefectRate() {
        return planDefectRate;
    }

    public String getDensityUnits() {
        return densityUnits;
    }

    public String getDefectRateUnits() {

        return defectRateUnits;
    }

    public String getCode() {
        return code;
    }

    public String getReason() {
        return reason;
    }

    public String getIsPass() {
        return isPass;
    }


    public void setActualCaseNum(Long actualCaseNum) {
        this.actualCaseNum = actualCaseNum;
    }

    public void setActualSize(Double actualSize) {
        this.actualSize = actualSize;
    }

    public void setActualDensity(Double actualDensity) {
        this.actualDensity = actualDensity;
    }

    public void setActualDefectNum(Long actualDefectNum) {
        this.actualDefectNum = actualDefectNum;
    }

    public void setActualDefectRate(Double actualDefectRate) {
        this.actualDefectRate = actualDefectRate;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public void setConclusionBy(String conclusionBy) {
        this.conclusionBy = conclusionBy;
    }

    public void setCriterion(String criterion) {
        this.criterion = criterion;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setPlanSize(Double planSize) {
        this.planSize = planSize;
    }

    public void setProduction(String production) {
        this.production = production;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;

    }

    public void setActivityRid(Long activityRid) {
        this.activityRid = activityRid;

    }

    public void setPlanDensity(Double planDensity) {
        this.planDensity = planDensity;
    }

    public void setPlanDefectRate(Double planDefectRate) {
        this.planDefectRate = planDefectRate;
    }

    public void setDensityUnits(String densityUnits) {
        this.densityUnits = densityUnits;
    }

    public void setDefectRateUnits(String defectRateUnits) {

        this.defectRateUnits = defectRateUnits;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setIsPass(String isPass) {
        this.isPass = isPass;
    }
}
