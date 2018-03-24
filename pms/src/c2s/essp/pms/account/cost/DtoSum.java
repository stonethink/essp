package c2s.essp.pms.account.cost;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import c2s.dto.DtoBase;
import com.wits.excel.ICellStyleAware;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;

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
public class DtoSum extends DtoBase  {
    public DtoSum() {
    }

    public String getActivityId() {
        return activityId;
    }

    public Double getActualAmt() {
        return actualAmt;
    }

    public Double getBudgetAmt() {
        return budgetAmt;
    }

    public Double getBudgetPh() {
        return budgetPh;
    }

    public Double getRemainAmt() {
        if(actualAmt == null)
            actualAmt = new Double(0);
        if(budgetAmt == null)
            budgetAmt = new Double(0);
        return new Double(budgetAmt.doubleValue() - actualAmt.doubleValue());
    }

    public Double getRemainPh() {
        if(actualPh == null)
            actualPh = new Double(0);
        if(budgetPh == null)
            budgetPh = new Double(0);
        return new Double(budgetPh.doubleValue() - actualPh.doubleValue());
    }

    public String getSum() {
        return sum;
    }

    public Double getActualPh() {
        return actualPh;
    }

    public Double getBudgetPm() {
        return budgetPm;
    }

    public String getJobCode() {
        return jobCode;
    }

    public Long getAcntRid() {
        return acntRid;
    }

    public Double getActualPm() {
        return actualPm;
    }

    public Double getRemainPm() {
        if(actualPm == null)
            actualPm = new Double(0);
        if(budgetPm == null)
            budgetPm = new Double(0);
        return new Double(budgetPm.doubleValue() - actualPm.doubleValue());
    }

    public Double getAmt() {
        return amt;
    }

    public Long getBudgetRid() {
        return budgetRid;
    }

    public String getCurrency() {
        return currency;
    }

    public String getDescription() {
        return description;
    }

    public Date getFromMonth() {
        return fromMonth;
    }

    public Double getPrice() {
        return price;
    }

    public String getResId() {
        return resId;
    }

    public String getResName() {
        return resName;
    }

    public Long getRid() {
        return rid;
    }

    public Double getUnitNum() {
        return unitNum;
    }

    public String getUnit() {
        return unit;
    }

    public Date getToMonth() {
        return toMonth;
    }

    public String getCode() {
        return code;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public void setBudgetPh(Double budgetPh) {
        this.budgetPh = budgetPh;
    }

    public void setBudgetAmt(Double budgetAmt) {
        this.budgetAmt = budgetAmt;
    }

    public void setActualPh(Double actualPh) {
        this.actualPh = actualPh;
    }

    public void setActualAmt(Double actualAmt) {
        this.actualAmt = actualAmt;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public void setBudgetPm(Double budgetPm) {
        this.budgetPm = budgetPm;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public void setActualPm(Double actualPm) {
        this.actualPm = actualPm;
    }

    public void setAmt(Double amt) {
        this.amt = amt;
    }

    public void setFromMonth(Date fromMonth) {
        this.fromMonth = fromMonth;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setBudgetRid(Long budgetRid) {
        this.budgetRid = budgetRid;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public void setToMonth(Date toMonth) {
        this.toMonth = toMonth;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setUnitNum(Double unitNum) {
        this.unitNum = unitNum;
    }

    public void setCode(String code) {
        this.code = code;
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


    private String sum;
    private Double budgetPm;
    private String activityId;
    private Double budgetPh;
    private Double actualPh;
    private Double budgetAmt;
    private Double actualAmt;
    private String jobCode;

    private Double actualPm;


    private Long acntRid;

    private String currency;
    private Double price;


    private Date fromMonth;
    private Date toMonth;
    private List monthList;
    public static final String MONTH_STYLE = "yyyy/MM";


    private String resId; //JobCode¶ÔÓ¦µÄPrice
    private String resName;
    //unit
    private Double unitNum;
    private String unit;
    private Double amt;
    private String description;
    Map hmMonthUnitNum;
    private Long rid;
    private Long budgetRid;
    private String code;
}
