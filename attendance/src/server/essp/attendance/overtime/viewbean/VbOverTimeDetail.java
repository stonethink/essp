package server.essp.attendance.overtime.viewbean;

import java.util.*;


public class VbOverTimeDetail {
    private List perDay;
    private String totalHours;
    private String isEachDay;
    private String readOnly;
    public List getPerDay() {
        return perDay;
    }

    public String getTotalHours() {
        return totalHours;
    }

    public String getIsEachDay() {
        return isEachDay;
    }

    public String getReadOnly() {
        return readOnly;
    }

    public void setPerDay(List perDay) {
        this.perDay = perDay;
    }

    public void setTotalHours(String totalHours) {
        this.totalHours = totalHours;
    }

    public void setIsEachDay(String isEachDay) {
        this.isEachDay = isEachDay;
    }

    public void setReadOnly(String readOnly) {
        this.readOnly = readOnly;
    }

    public int getDetailSize(){
        if(perDay == null)
            return 0 ;
        else
            return perDay.size();
    }
}
