package c2s.essp.pms.account.budget;

import c2s.dto.DtoBase;
import java.util.Date;
//import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import c2s.essp.common.calendar.WorkCalendar;
import java.util.ArrayList;
import com.wits.util.comDate;
import java.util.Map;
import java.util.Iterator;
import java.util.HashMap;

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
public class DtoLaborBudget extends DtoBase {
    private String jobCode;
    private String currency;
    private Double price;
    private Double budgetPm;
    private Double budgetAmt;
    private Date fromMonth;
    private Date toMonth;
    private List monthList;
    public static final String MONTH_STYLE = "yyyy/MM";

    private Long acntRid;
    private String resId; //JobCode对应的Price
    private String resName;
    //unit
    private Double unitNum;
    private String unit;
    private Double amt;
    private String description;
    Map hmMonthUnitNum;
    private Long rid;
    private Long budgetRid;


    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public Double getUnitNum() {
        this.unitNum = getAllMonthUnitNum();
        return unitNum;
    }

    public void setUnitNum(Double unitNum) {
        this.unitNum = unitNum;
    }

    public Double getAmt() {
        this.unitNum = getUnitNum();
        if (unitNum == null || price == null) {
            amt = new Double(0);
        } else {
            amt = new Double(unitNum.doubleValue() * price.doubleValue());
        }
        return amt;
    }

    public void setAmt(Double amt) {
        this.amt = amt;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setMonthUnitNum(String month, Double monthPm) {
        if (hmMonthUnitNum == null) {
            hmMonthUnitNum = new HashMap();
        }
        hmMonthUnitNum.put(month, monthPm);
    }

    public void addMonthUnitNum(String month, Double addedPm) {
        if (addedPm == null) {
            return;
        }
        if (hmMonthUnitNum == null) {
            setMonthUnitNum(month, addedPm);
        } else {
            Double dbMonthPm = (Double) hmMonthUnitNum.get(month);
            if (dbMonthPm == null) {
                hmMonthUnitNum.put(month, addedPm);
            } else {
                Double d = new Double(dbMonthPm.doubleValue() +
                                      addedPm.doubleValue());
                hmMonthUnitNum.put(month, d);
            }
        }
    }

    public Double getMonthUnitNum(String month) {
        if (hmMonthUnitNum == null) {
            return new Double(0);
        }
        try {
            Double dbMonthPm = (Double) hmMonthUnitNum.get(month);
            if (dbMonthPm == null) {
                return new Double(0);
            }
            return dbMonthPm;
        } catch (Exception e) {
            return new Double(0);
        }
    }

    public Double getAllMonthUnitNum() {
        double allMonthUnitNum = 0;
        if (hmMonthUnitNum == null) {
            return new Double(0);
        }
        Iterator iterator = hmMonthUnitNum.values().iterator();
        while (iterator.hasNext()) {
            Double unitNum = (Double) iterator.next();
            allMonthUnitNum += unitNum.doubleValue();
        }
        return new Double(allMonthUnitNum);

    }

    public String getDescription() {
        return description;
    }

    public String getResName() {
        return resName;
    }

    public String getCurrency() {
        return currency;
    }

    public String getUnit() {
        return unit;
    }

    public Long getAcntRid() {
        return acntRid;
    }

    public Map getHmMonthUnitNum() {
        return hmMonthUnitNum;
    }

    public Long getRid() {
        return rid;
    }

    public Long getBudgetRid() {
        return budgetRid;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public void setHmMonthUnitNum(Map hmMonthUnitNum) {
        this.hmMonthUnitNum = hmMonthUnitNum;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public void setBudgetRid(Long budgetRid) {
        this.budgetRid = budgetRid;
    }

    public DtoLaborBudget() {
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public void setBudgetPm(Double budgetPm) {
        this.budgetPm = budgetPm;
    }

    public void setBudgetAmt(Double budgetAmt) {
        this.budgetAmt = budgetAmt;
    }

    public void setFromMonth(Date fromMonth) {
        this.fromMonth = fromMonth;
    }

    public void setMonthList(List monthList) {
        this.monthList = monthList;
    }

    public String getJobCode() {
        return jobCode;
    }


    public Double getBudgetPm() {
        return budgetPm;
    }

    public Double getBudgetAmt() {
        return budgetAmt;
    }

    public Date getFromMonth() {
        return fromMonth;
    }

    public java.util.Date getToMonth() {
        return toMonth;
    }

    public void setToMonth(java.util.Date toMonth) {
        this.toMonth = toMonth;
    }


    /**
     * 计算起始日期间所有的月份
     * @return List
     */
    public List getMonthList() {
        List result = new ArrayList();
        Date from = this.getFromMonth();
        Date to = this.getToMonth();
        Calendar begin = Calendar.getInstance();
        begin.setTime(from);
        Calendar end = Calendar.getInstance();
        end.setTime(to);

        List monthBE = WorkCalendar.getBEMonthList(begin, end);
        for (int i = 0; i < monthBE.size(); i++) {
            Calendar mEnd = ((Calendar[]) monthBE.get(i))[1];
            String month = comDate.dateToString(mEnd.getTime(), MONTH_STYLE);
            result.add(month);
        }
        return result;
    }

}
