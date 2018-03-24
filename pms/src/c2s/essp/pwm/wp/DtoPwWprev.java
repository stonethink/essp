package c2s.essp.pwm.wp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import c2s.dto.DtoBase;

public class DtoPwWprev extends DtoBase implements Serializable {
    private Long rid;
    private Long wpId;

    private Long wpDefectRmv;  //table="pw_wpsum"
    private Long wpDefectRmn;  //table="pw_wpsum"
    private Long wprevQuality;
    private BigDecimal wprevWkyield;

    private Date wpPlanFihish;
    private Date wpActFinish;
    private Long wprevTime;

    private BigDecimal wpPlanWkhr;
    private BigDecimal wpActWkhr;
    private Long wprevCost;

    private Long wprevPerformance;
    private String wprevComment;

    private String rst;
    private Date rct;
    private Date rut;
    private boolean isAssignBy = false;

    public void setWprevTime(Long wprevTime) {
        this.wprevTime = wprevTime;
    }

    public void setWprevQuality(Long wprevQuality) {
        this.wprevQuality = wprevQuality;
    }

    public void setWprevCost(Long wprevCost) {
        this.wprevCost = wprevCost;
    }

    public void setWprevPerformance(Long wprevPerformance) {
        this.wprevPerformance = wprevPerformance;
    }

    public void setWprevComment(String wprevComment) {
        this.wprevComment = wprevComment;
    }

    public void setWpPlanFihish(Date wpPlanFihish) {
        this.wpPlanFihish = wpPlanFihish;
    }

    public void setWpActFinish(Date wpActFinish) {
        this.wpActFinish = wpActFinish;
    }

    public void setWpPlanWkhr(BigDecimal wpPlanWkhr) {
        this.wpPlanWkhr = wpPlanWkhr;
        this.wpPlanWkhr = this.wpPlanWkhr.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void setWpActWkhr(BigDecimal wpActWkhr) {
        this.wpActWkhr = wpActWkhr;
        this.wpActWkhr = this.wpActWkhr.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void setWpDefectRmv(Long wpDefectRmv) {
        this.wpDefectRmv = wpDefectRmv;
    }

    public void setWpDefectRmn(Long wpDefectRmn) {
        this.wpDefectRmn = wpDefectRmn;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public void setWpId(Long wpId) {
        this.wpId = wpId;
    }

    public void setRut(Date rut) {
        this.rut = rut;
    }

    public void setRct(Date rct) {
        this.rct = rct;
    }

    public void setRst(String rst) {
        this.rst = rst;
    }

    public void setWprevWkyield(BigDecimal wprevWkyield) {
        this.wprevWkyield = wprevWkyield;
        this.wprevWkyield = this.wprevWkyield.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void setIsAssignBy(boolean isAssignBy) {
        this.isAssignBy = isAssignBy;
    }

    public Long getWprevTime() {
        return wprevTime;
    }

    public Long getWprevQuality() {
        return wprevQuality;
    }

    public Long getWprevCost() {
        return wprevCost;
    }

    public Long getWprevPerformance() {
        return wprevPerformance;
    }

    public String getWprevComment() {
        return wprevComment;
    }

    public Date getWpPlanFihish() {
        return wpPlanFihish;
    }

    public Date getWpActFinish() {
        return wpActFinish;
    }

    public BigDecimal getWpPlanWkhr() {
        return wpPlanWkhr;
    }

    public BigDecimal getWpActWkhr() {
        return wpActWkhr;
    }

    public Long getWpDefectRmv() {
        return wpDefectRmv;
    }

    public Long getWpDefectRmn() {
        return wpDefectRmn;
    }

    public Long getRid() {
        return rid;
    }

    public Long getWpId() {
        return wpId;
    }

    public Date getRut() {
        return rut;
    }

    public Date getRct() {
        return rct;
    }

    public String getRst() {
        return rst;
    }

    public BigDecimal getWprevWkyield() {
        return wprevWkyield;
    }

    public boolean isIsAssignBy() {
        return isAssignBy;
    }

}
