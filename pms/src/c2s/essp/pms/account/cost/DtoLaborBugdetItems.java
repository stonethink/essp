package c2s.essp.pms.account.cost;

import java.util.Map;
import java.util.Iterator;
import java.util.HashMap;
import c2s.dto.DtoBase;

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
public class DtoLaborBugdetItems extends DtoBase{
    public DtoLaborBugdetItems() {
    }
    private Long acntRid;
   private String resId; //JobCode¶ÔÓ¦µÄPrice
   private String resName;
   //unit
   private Double unitNum;
   private Double price;
   private String unit;
   private String currency;
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
       if(unitNum == null || price == null)
           amt = new Double(0);
       else{
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

   public void addMonthUnitNum(String month,Double addedPm) {
       if(addedPm == null)
           return;
       if (hmMonthUnitNum == null) {
           setMonthUnitNum(month, addedPm);
       }else{
           Double dbMonthPm = (Double) hmMonthUnitNum.get(month);
           if(dbMonthPm == null){
               hmMonthUnitNum.put(month, addedPm);
           }else{
               Double d = new Double(dbMonthPm.doubleValue() + addedPm.doubleValue());
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

}
