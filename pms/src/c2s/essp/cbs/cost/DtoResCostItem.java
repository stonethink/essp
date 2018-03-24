package c2s.essp.cbs.cost;

import java.util.Date;

import c2s.dto.DtoBase;

public class DtoResCostItem extends DtoBase {
    private Long rid;
//    private Long costRid;
    private Long acntRid;

    private String resId;
    private String resName;
    private Double price;
    private String currency;
    private String costUnit;
    private Double budgetUnitNum;
    private Double budgetAmt;
    private Double costUnitNum;
    private Double costAmt;
    private Double remainUnitNum;
    private Double remainAmt;
    private String description;

    private Date rut; //记录人力成本最后一次更新时间
    public DtoResCostItem() {
    }

    public Double getBudgetUnitNum() {
        return budgetUnitNum;
    }

    public void setBudgetUnitNum(Double budgetUnitNum) {
        this.budgetUnitNum = budgetUnitNum;
    }

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getBudgetAmt() {
        if(budgetUnitNum == null || price == null)
            return new Double(0);
        double temp = budgetUnitNum.doubleValue() * price.doubleValue();
        this.budgetAmt = new Double(temp);
        return budgetAmt;
    }

    public void setBudgetAmt(Double budgetAmt) {
        this.budgetAmt = budgetAmt;
    }

    public Double getCostAmt() {
        if(costUnitNum == null || price ==null)
            return new Double(0);
        double temp = costUnitNum.doubleValue() * price.doubleValue();
        this.costAmt = new Double(temp);
        return costAmt;
    }

    public void setCostAmt(Double costAmt) {
        this.costAmt = costAmt;
    }

    public Double getCostUnitNum() {
        return costUnitNum;
    }

    public void setCostUnitNum(Double costUnitNum) {
        this.costUnitNum = costUnitNum;
    }

    public Double getRemainAmt() {
        double temp = getBudgetAmt().doubleValue() - getCostAmt().doubleValue();
        this.remainAmt = new Double(temp);
        return remainAmt;
    }

    public void setRemainAmt(Double remainAmt) {
        this.remainAmt = remainAmt;
    }

    public Double getRemainUnitNum() {
        if(budgetUnitNum == null)
            remainUnitNum = new Double(0);
        else if(costUnitNum == null)
            remainUnitNum = budgetUnitNum;
        else{
            double temp = budgetUnitNum.doubleValue() - costUnitNum.doubleValue();
            this.remainUnitNum = new Double(temp);
        }
        return remainUnitNum;
    }

    public void setRemainUnitNum(Double remainUnitNum) {
        this.remainUnitNum = remainUnitNum;
    }

    public String getDescription() {
        return description;
    }

    public Long getRid() {
        return rid;
    }

//    public Long getCostRid() {
//        return costRid;
//    }

    public String getResName() {
        return resName;
    }

    public String getCostUnit() {
        return costUnit;
    }

    public String getCurrency() {
        return currency;
    }

    public Long getAcntRid() {
        return acntRid;
    }

    public Date getRut() {

        return rut;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

//    public void setCostRid(Long costRid) {
//        this.costRid = costRid;
//    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public void setCostUnit(String costUnit) {
        this.costUnit = costUnit;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public void setRut(Date rut) {

        this.rut = rut;
    }


}
