package server.essp.pms.psr.bean;

import com.wits.excel.ICellStyleAware;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class TimeDetailSheetBean {
    private java.util.Date begin;
    private java.util.Date end;
    private Long wpTotal;
    private Long currentPlan;
    private Long currentActual;
    private Long currentDelay;
    private Long totalPlan;
    private Long totalActual;
    private Double ratePlan;
    private Double rateActual;
    private Double variance;
    private Long score;
    private String remark;
    public TimeDetailSheetBean() {
    }

    public void setBegin(java.util.Date begin) {
        this.begin = begin;
    }

    public void setEnd(java.util.Date end) {
        this.end = end;
    }

    public void setWpTotal(Long wpTotal) {
        this.wpTotal = wpTotal;
    }

    public void setCurrentPlan(Long currentPlan) {
        this.currentPlan = currentPlan;
    }

    public void setCurrentActual(Long currentActual) {
        this.currentActual = currentActual;
    }

    public void setCurrentDelay(Long currentDelay) {
        this.currentDelay = currentDelay;
    }

    public void setTotalPlan(Long totalPlan) {
        this.totalPlan = totalPlan;
    }

    public void setTotalActual(Long totalActual) {
        this.totalActual = totalActual;
    }

    public void setRatePlan(Double ratePlan) {
        this.ratePlan = ratePlan;
    }

    public void setRateActual(Double rateActual) {
        this.rateActual = rateActual;
    }

    public void setVariance(Double variance) {
        this.variance = variance;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public java.util.Date getBegin() {
        return begin;
    }

    public java.util.Date getEnd() {
        return end;
    }

    public Long getWpTotal() {
        return wpTotal;
    }

    public Long getCurrentPlan() {
        return currentPlan;
    }

    public Long getCurrentActual() {
        return currentActual;
    }

    public Long getCurrentDelay() {
        return currentDelay;
    }

    public Long getTotalPlan() {
        return totalPlan;
    }

    public Long getTotalActual() {
        return totalActual;
    }

    public Double getRatePlan() {
        return ratePlan;
    }

    public Double getRateActual() {
        return rateActual;
    }

    public Double getVariance() {
        return variance;
    }

    public Long getScore() {
        return score;
    }

    public String getRemark() {
        return remark;
    }
}
