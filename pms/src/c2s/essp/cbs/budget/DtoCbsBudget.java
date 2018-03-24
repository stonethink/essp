package c2s.essp.cbs.budget;

import java.util.Date;

import c2s.dto.DtoBase;
import c2s.dto.DtoTreeNode;
import java.util.Calendar;
import java.util.List;
import c2s.essp.common.calendar.WorkCalendar;
import java.util.ArrayList;
import com.wits.util.comDate;

public class DtoCbsBudget extends DtoBase {
    private String budgetType;
    private Long rid;
    private Long acntRid;
    private String currency;
//    private String currencySymbol;
    DtoTreeNode cbsRoot;

    private Double lastPm;
    private Double lastAmt;
    private Double currentPm;
    private Double currentAmt;
    private Date fromMonth;
    private Date toMonth;
    public static final String MONTH_STYLE = "yyyy/MM";
    /**
     * 计算起始日期间所有的月份
     * @return List
     */
    public List getMonthList(){
        List result = new ArrayList();
        Date from = this.getFromMonth();
        Date to = this.getToMonth();
        Calendar begin = Calendar.getInstance();
        begin.setTime(from);
        Calendar end = Calendar.getInstance();
        end.setTime(to);

        List monthBE = WorkCalendar.getBEMonthList(begin,end);
        for(int i = 0; i < monthBE.size(); i ++){
            Calendar mEnd = ((Calendar[]) monthBE.get(i))[1];
            String month = comDate.dateToString(mEnd.getTime(),MONTH_STYLE);
            result.add(month);
        }
        return result;
    }

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public Long getAcntRid() {
        return acntRid;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public DtoTreeNode getCbsRoot() {
        return cbsRoot;
    }

    public void setCbsRoot(DtoTreeNode cbsRoot) {
        this.cbsRoot = cbsRoot;
    }

    public String getBudgetType() {
        return budgetType;
    }

    public void setBudgetType(String budgetType) {
        this.budgetType = budgetType;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getLastPm() {
        return lastPm;
    }

    public void setLastPm(Double lastPm) {
        this.lastPm = lastPm;
    }

    public Double getLastAmt() {
        return lastAmt;
    }

    public void setLastAmt(Double lastAmt) {
        this.lastAmt = lastAmt;
    }

    public Date getFromMonth() {
        return fromMonth;
    }

    public void setFromMonth(Date fromMonth) {
        this.fromMonth = fromMonth;
    }

    public java.util.Date getToMonth() {
        return toMonth;
    }

    public Double getCurrentAmt() {
        return currentAmt;
    }

    public Double getCurrentPm() {
        return currentPm;
    }

    public void setToMonth(java.util.Date toMonth) {
        this.toMonth = toMonth;
    }

    public void setCurrentAmt(Double currentAmt) {
        this.currentAmt = currentAmt;
    }

    public void setCurrentPm(Double currentPm) {
        this.currentPm = currentPm;
    }
}
