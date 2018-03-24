package server.essp.attendance.overtime.form;

import org.apache.struts.action.*;

public class AfOverTimeAppCaculate extends ActionForm {
    private String dateFrom;
    private String dateTo;
    private String timeFrom;
    private String timeTo;
    private String isEachDay;
    public String getDateFrom() {
        return dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public String getIsEachDay() {
        return isEachDay;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public void setIsEachDay(String isEachDay) {
        this.isEachDay = isEachDay;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }
}
