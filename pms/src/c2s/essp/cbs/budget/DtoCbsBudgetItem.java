package c2s.essp.cbs.budget;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import c2s.dto.DtoUtil;
import c2s.essp.cbs.DtoSubject;

public class DtoCbsBudgetItem extends DtoSubject {
    private Double amt = new Double(0);
    private boolean show = true;
    private String description;
    Map hmMonthAmt;
    private static final long serialVersionUID = -1000864587057801853L;

    public DtoCbsBudgetItem() {
    }
    public DtoCbsBudgetItem(DtoSubject subject){
        if(subject == null)
            throw new IllegalArgumentException("subject is null!Can not convert to Budget item!");
        DtoUtil.copyBeanToBean(this,subject);
    }
    public Double getAmt() {
        this.amt = getAllMonthAmt();
        return amt;
    }

    public void setAmt(Double amt) {
        this.amt = amt;
    }

    public void setMonthAmt(String month, Double monthAmt) {
        if (hmMonthAmt == null) {
            hmMonthAmt = new HashMap();
        }
        hmMonthAmt.put(month, monthAmt);
    }

    public void addMonthAmt(String month,Double monthAmt) {
        if(monthAmt == null)
            return;
        if(hmMonthAmt == null)
            setMonthAmt(month,monthAmt);
        Double oldMonthAmt = getMonthAmt(month);
        Double newMonthAmt = new Double(oldMonthAmt.doubleValue() + monthAmt.doubleValue());
        hmMonthAmt.put(month, newMonthAmt);
    }
    public Double getMonthAmt(String month) {
        Double monthAmt = new Double(0);
        if (hmMonthAmt == null) {
            return monthAmt;
        }
        Double dbMonthAmt = (Double) hmMonthAmt.get(month);
        if (dbMonthAmt == null) {
            return monthAmt;
        }
        return dbMonthAmt;
    }

    public Double getAllMonthAmt() {
        Double allMonthAmt = new Double(0);
        if (hmMonthAmt == null) {
            return allMonthAmt;
        }
        Iterator iterator = hmMonthAmt.values().iterator();
        while (iterator.hasNext()) {
            Double unitNum = (Double) iterator.next();
            if(unitNum != null){
                double d = allMonthAmt.doubleValue() + unitNum.doubleValue();
                allMonthAmt = new Double(d);
            }
        }
        return allMonthAmt;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public String getDescription() {
        return description;
    }

    public Map getHmMonthAmt() {
        if(hmMonthAmt == null)
            hmMonthAmt = new HashMap();
        return hmMonthAmt;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
