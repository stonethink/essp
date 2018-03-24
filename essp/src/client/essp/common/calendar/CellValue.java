package client.essp.common.calendar;

public class CellValue {
    private boolean workday  = true;
    private boolean currentmonth = true;
    private String  dayValue = "";

    public boolean isWorkday() {
        return workday;
    }

    public String getDayValue() {
        return dayValue;
    }

    public boolean isCurrentmonth() {
        return currentmonth;
    }

    public void setWorkday(boolean workday) {
        this.workday = workday;
    }

    public void setDayValue(String dayValue) {
        this.dayValue = dayValue;
    }

    public void setCurrentmonth(boolean currentmonth) {
        this.currentmonth = currentmonth;
    }
}
