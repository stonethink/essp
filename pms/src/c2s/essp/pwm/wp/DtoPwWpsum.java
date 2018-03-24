package c2s.essp.pwm.wp;

import java.io.Serializable;
import java.math.BigDecimal;

import c2s.dto.DtoBase;

public class DtoPwWpsum extends DtoBase implements Serializable {
    private Long rid;
    //private Long wpId;
    private String acntCode;

    private String wpTechtype;
    private String wpSizeUnit;
    private String wpProductivityUnit;
    private String wpDensityrateUnit;
    private String wpDefectrateUnit;
    private String wpFilename;

    private BigDecimal wpSizePlan;
    private BigDecimal wpSizeAct;
    private BigDecimal wpProductivityPlan;
    private BigDecimal wpProductivityAct;
    private BigDecimal wpDensityratePlan;
    private BigDecimal wpDensityrateAct;
    private BigDecimal wpDefectratePlan;
    private BigDecimal wpDefectrateAct;
    private Long wpDensityPlan;
    private Long wpDensityAct;
    private Long wpDefectPlan;
    private Long wpDefectAct;
    private Long wpDefectRmv;
    private String wpRemark;
    //即使wpDefectRmn字段画面上不显示，也要写在这。避免update时该字段被清空。rst,rct同
//    private Long wpDefectRmn;

//    private String wpAttachUrl;

    private BigDecimal wpPlanWkhr; //table="pw_wp"
    private BigDecimal wpActWkhr;  //table="pw_wp"

//    private Date rct;
//    private Date rut;
//    private String rst;


    public void setWpTechtype(String wpTechtype) {
        this.wpTechtype = wpTechtype;
    }

    public void setWpSizeUnit(String wpSizeUnit) {
        this.wpSizeUnit = wpSizeUnit;
    }

    public void setWpProductivityUnit(String wpProductivityUnit) {
        this.wpProductivityUnit = wpProductivityUnit;
    }

    public void setWpDensityrateUnit(String wpDensityrateUnit) {
        this.wpDensityrateUnit = wpDensityrateUnit;
    }

    public void setWpFilename(String wpFilename) {
        this.wpFilename = wpFilename;
    }

    public void setWpDefectrateUnit(String wpDefectrateUnit) {
        this.wpDefectrateUnit = wpDefectrateUnit;
    }

    public void setWpActWkhr(BigDecimal wpActWkhr) {
        this.wpActWkhr = wpActWkhr;
        this.wpActWkhr = this.wpActWkhr.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public void setWpPlanWkhr(BigDecimal wpPlanWkhr) {
        this.wpPlanWkhr = wpPlanWkhr;
        this.wpPlanWkhr = this.wpPlanWkhr.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void setWpSizePlan(BigDecimal wpSizePlan) {
        this.wpSizePlan = wpSizePlan;
        this.wpSizePlan = this.wpSizePlan.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void setWpDefectPlan(Long wpDefectPlan) {
        this.wpDefectPlan = wpDefectPlan;
    }

    public void setWpProductivityAct(BigDecimal wpProductivityAct) {
        this.wpProductivityAct = wpProductivityAct;
        this.wpProductivityAct = this.wpProductivityAct.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void setWpSizeAct(BigDecimal wpSizeAct) {
        this.wpSizeAct = wpSizeAct;
        this.wpSizeAct = this.wpSizeAct.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void setWpDensityratePlan(BigDecimal wpDensityratePlan) {
        this.wpDensityratePlan = wpDensityratePlan;
        this.wpDensityratePlan = this.wpDensityratePlan.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

//    public void setWpAttachUrl(String wpAttachUrl) {
//        this.wpAttachUrl = wpAttachUrl;
//    }
//
//    public void setWpRemark(String wpRemark) {
//        this.wpRemark = wpRemark;
//    }

    public void setWpDensityrateAct(BigDecimal wpDensityrateAct) {
        this.wpDensityrateAct = wpDensityrateAct;
        this.wpDensityrateAct = this.wpDensityrateAct.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void setWpDefectratePlan(BigDecimal wpDefectratePlan) {
        this.wpDefectratePlan = wpDefectratePlan;
        this.wpDefectratePlan = this.wpDefectratePlan.setScale(2, BigDecimal.ROUND_HALF_UP);
    }


    public void setWpDefectAct(Long wpDefectAct) {
        this.wpDefectAct = wpDefectAct;
    }

    public void setWpProductivityPlan(BigDecimal wpProductivityPlan) {
        this.wpProductivityPlan = wpProductivityPlan;
        this.wpProductivityPlan = this.wpProductivityPlan.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void setWpDefectrateAct(BigDecimal wpDefectrateAct) {
        this.wpDefectrateAct = wpDefectrateAct;
        this.wpDefectrateAct = this.wpDefectrateAct.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void setWpDefectRmv(Long wpDefectRmv) {
        this.wpDefectRmv = wpDefectRmv;
    }

    public void setWpDensityPlan(Long wpDensityPlan) {
        this.wpDensityPlan = wpDensityPlan;
    }

    public void setWpDensityAct(Long wpDensityAct) {
        this.wpDensityAct = wpDensityAct;
    }

    public void setAcntCode(String acntCode) {

        this.acntCode = acntCode;
    }

    public void setWpRemark(String wpRemark) {
        this.wpRemark = wpRemark;
    }

    //    public void setWpId(Long wpId) {
//        this.wpId = wpId;
//    }

//    public void setWpDefectRmn(Long wpDefectRmn) {
//        this.wpDefectRmn = wpDefectRmn;
//    }
//
//    public void setRut(Date rut) {
//        this.rut = rut;
//    }
//
//    public void setRct(Date rct) {
//        this.rct = rct;
//    }
//
//    public void setRst(String rst) {
//        this.rst = rst;
//    }

    public String getWpTechtype() {
        return wpTechtype;
    }

    public String getWpSizeUnit() {
        return wpSizeUnit;
    }

    public String getWpProductivityUnit() {
        return wpProductivityUnit;
    }

    public String getWpDensityrateUnit() {
        return wpDensityrateUnit;
    }

    public String getWpFilename() {
        return wpFilename;
    }

    public String getWpDefectrateUnit() {
        return wpDefectrateUnit;
    }

    public BigDecimal getWpActWkhr() {
        return wpActWkhr;
    }

    public Long getRid() {
        return rid;
    }

    public BigDecimal getWpPlanWkhr() {
        return wpPlanWkhr;
    }

    public Long getWpDefectPlan() {
        return wpDefectPlan;
    }

    public BigDecimal getWpProductivityAct() {
        return wpProductivityAct;
    }

    public BigDecimal getWpSizeAct() {
        return wpSizeAct;
    }

    public BigDecimal getWpDensityratePlan() {
        return wpDensityratePlan;
    }

//    public String getWpAttachUrl() {
//        return wpAttachUrl;
//    }
//
//    public String getWpRemark() {
//        return wpRemark;
//    }

    public BigDecimal getWpDensityrateAct() {
        return wpDensityrateAct;
    }

    public BigDecimal getWpDefectratePlan() {
        return wpDefectratePlan;
    }

    public Long getWpDefectAct() {
        return wpDefectAct;
    }

    public BigDecimal getWpProductivityPlan() {
        return wpProductivityPlan;
    }

    public BigDecimal getWpDefectrateAct() {
        return wpDefectrateAct;
    }

    public BigDecimal getWpSizePlan() {
        return wpSizePlan;
    }

    public Long getWpDefectRmv() {
        return wpDefectRmv;
    }

    public Long getWpDensityPlan() {
        return wpDensityPlan;
    }

    public Long getWpDensityAct() {
        return wpDensityAct;
    }

    public String getAcntCode() {

        return acntCode;
    }

    public String getWpRemark() {
        return wpRemark;
    }

    //    public Long getWpId() {
//        return wpId;
//    }

//    public Long getWpDefectRmn() {
//        return wpDefectRmn;
//    }
//
//    public Date getRut() {
//        return rut;
//    }
//
//    public Date getRct() {
//        return rct;
//    }
//
//    public String getRst() {
//        return rst;
//    }

}
