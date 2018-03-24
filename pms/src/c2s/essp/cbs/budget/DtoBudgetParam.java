package c2s.essp.cbs.budget;

import c2s.dto.DtoBase;

public class DtoBudgetParam extends DtoBase {
    //acntRid,budgetType是必须设置的参数,currency和budgetRid可根据其查找
    private String budgetType;
    private Long budgetRid;
    private Long acntRid;
    private String currency;
    private boolean isReadOnly = false;
    private boolean isCanModifyPrice = false;
    private boolean isManager = false;
    private static final long serialVersionUID = 7628046918696105555L;
    public DtoBudgetParam(){
    }
    public String getBudgetType() {
        return budgetType;
    }

    public Long getAcntRid() {
        return acntRid;
    }

    public String getCurrency() {
        return currency;
    }

    public Long getBudgetRid() {
        return budgetRid;
    }

    public boolean isManager() {
        return isManager;
    }

    public boolean isReadOnly() {
        return isReadOnly;
    }

    public boolean isCanModifyPrice(){
        return isCanModifyPrice;
    }
    public void setBudgetType(String budgetType) {
        this.budgetType = budgetType;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setIsReadOnly(boolean isReadOnly) {
        this.isReadOnly = isReadOnly;
    }
    public void setIsCanModifyPrice(boolean isCanModifyPrice) {
        this.isCanModifyPrice = isCanModifyPrice;
    }
    public void setBudgetRid(Long budgetRid) {
        this.budgetRid = budgetRid;
    }

    public void setIsManager(boolean isManager) {
        this.isManager = isManager;
    }

}
