package server.essp.attendance.overtime.viewbean;

import java.io.*;
import java.text.DecimalFormat;
import java.math.BigDecimal;

public class VbOverTimeSum implements Serializable {
    private String loginId;
    private Double totalOverTimeHours;
    private Double totalShiftHours;
    private Double totalPayedHours;
    public VbOverTimeSum(){}
    public VbOverTimeSum(Double totalOverTimeHours,Double totalShiftHours,Double totalPayedHours){
        this.totalOverTimeHours =  totalOverTimeHours;
        this.totalShiftHours =  totalShiftHours;
        this.totalPayedHours =  totalPayedHours;
    }

    public String getLoginId() {
        return loginId;
    }

    public Double getTotalOverTimeHours() {
        if(totalOverTimeHours == null)
            return new Double(0);
        return totalOverTimeHours;
    }

    public Double getTotalPayedHours() {
        if(totalPayedHours == null)
            return new Double(0);
        return totalPayedHours;
    }

    public Double getTotalShiftHours() {
        if(totalShiftHours == null)
            return new Double(0);
        return totalShiftHours;
    }

    public void setTotalOverTimeHours(Double totalOverTimeHours) {
        this.totalOverTimeHours = totalOverTimeHours;
    }


    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setTotalPayedHours(Double totalPayedHours) {
        this.totalPayedHours = totalPayedHours;
    }

    public void setTotalShiftHours(Double totalShiftHours) {
        this.totalShiftHours = totalShiftHours;
    }
    //总共可用时间=总加班时间-总调休时间-总支付时间
    public Double getUsableHours(){
        double useable = getTotalOverTimeHours().doubleValue() -
                         getTotalPayedHours().doubleValue() -
                         getTotalShiftHours().doubleValue();
        BigDecimal big = new BigDecimal(useable).setScale(2,BigDecimal.ROUND_HALF_UP);
        return new Double(big.doubleValue());
//        return new Double(useable);
    }
    public Double getUsedHours(){
        double used = getTotalPayedHours().doubleValue() +
                          getTotalShiftHours().doubleValue();

        BigDecimal big = new BigDecimal(used).setScale(2,BigDecimal.ROUND_HALF_UP);
        return new Double(big.doubleValue());
    }
}
