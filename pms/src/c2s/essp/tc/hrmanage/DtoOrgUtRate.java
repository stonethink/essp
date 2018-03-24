package c2s.essp.tc.hrmanage;

import java.math.BigDecimal;
import java.math.RoundingMode;

import c2s.dto.DtoBase;

public class DtoOrgUtRate extends DtoBase {
    private String unitId;
    private String unitCode;
    private String unitName;
    private String parentId;
    private Double indirectWH;
    private Double directWH;
    private Double incomeWH;
    private Double partTimeEmpWH;
    private BigDecimal monthWorkHours;

    public String getUnitId() {
        return unitId;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public String getUnitName() {
        return unitName;
    }

    public String getParentId() {
        return parentId;
    }

    /**
     * 实际工时数=间接工时数+直接工时数
     */
    public Double getActualWH() {
        return getIndirectWH() + getDirectWH();
    }

    public Double getIndirectWH() {
        if(indirectWH == null) return new Double(0);
        return indirectWH;
    }

    public Double getDirectWH() {
        if(directWH == null) return new Double(0);
        return directWH;
    }

    public Double getIncomeWH() {
        if(incomeWH == null) return new Double(0);
        return incomeWH;
    }

    public Double getPartTimeEmpWH() {
        if(partTimeEmpWH == null) return new Double(0);
        return partTimeEmpWH;
    }

    public BigDecimal getMonthWorkHours() {
        return monthWorkHours;
    }

    /**
     * 人员使用率=收入工时数/直接工时数
     * @return Double
     */
    public Double getUtRate() {
        //分母为0时，返回null
        if(getDirectWH().equals(new Double(0))) return null;

        BigDecimal direct = new BigDecimal(getDirectWH());
        BigDecimal income = new BigDecimal(getIncomeWH()).setScale(6, RoundingMode.HALF_UP);
        BigDecimal rate = income.divide(direct, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP);
        return rate.doubleValue();
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setIndirectWH(Double indirectWH) {

        this.indirectWH = indirectWH;
    }

    public void setDirectWH(Double directWH) {

        this.directWH = directWH;
    }

    public void setIncomeWH(Double incomeWH) {

        this.incomeWH = incomeWH;
    }

    public void setPartTimeEmpWH(Double partTimeEmpWH) {

        this.partTimeEmpWH = partTimeEmpWH;
    }

    public void setMonthWorkHours(BigDecimal monthWorkHours) {
        this.monthWorkHours = monthWorkHours;
    }

    public Double getActualPM() {
        return exchangeWH2PM(getActualWH());
    }

    public Double getIndirectPM() {
        return exchangeWH2PM(getIndirectWH());
    }

    public Double getDirectPM() {
        return exchangeWH2PM(getDirectWH());
    }

    public Double getIncomePM() {
        return exchangeWH2PM(getIncomeWH());
    }

    public Double getPartTimeEmpPM() {
        return exchangeWH2PM(getPartTimeEmpWH());
    }

    /**
     * 换算工时数到人月数
     * @param workHours Double
     * @return Double
     */
    private Double exchangeWH2PM(Double workHours) {
        BigDecimal wh = new BigDecimal(workHours).setScale(4, RoundingMode.HALF_UP);
        BigDecimal pm = wh.divide(monthWorkHours, RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP);
        return pm.doubleValue();
    }
}
