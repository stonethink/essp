package server.essp.attendance.leave.viewbean;

import java.util.*;

import server.framework.taglib.util.*;

public class VbLeavePersonalStatus {
    private String loginId;
    private List leaveTypeList;
    private List usedList;//每种假别已使用的天数
    private List usableList;//每种假别可使用的天数(未使用)
    private List maxHoursList;////每种假别最多可请天数

    public static int LIST_YEAR_NUM = 10;
    private int selectedYear;
    private int thisYear;
    private List yearList;
    public VbLeavePersonalStatus() {
        Calendar today = Calendar.getInstance();
        thisYear = today.get(Calendar.YEAR);
        yearList = new ArrayList();
        for (int i = thisYear; i > (thisYear - LIST_YEAR_NUM); i--) {
            SelectOptionImpl option = new SelectOptionImpl(i + "", i + "");
            yearList.add(option);
        }
        int nextYear = thisYear + 1;
        SelectOptionImpl option = new SelectOptionImpl(nextYear + "", nextYear + "");
        yearList.add(0,option);
        selectedYear = thisYear;
    }
    public double getSumUsaleHours(){
        if(usableList == null || usableList.size() == 0)
            return 0;
        double sum = 0D;
        for(int i = 0;i < usableList.size() ;i ++){
            Double usable = (Double) usableList.get(i);
            sum += usable.doubleValue();
        }
        return sum;
    }

    public int getSelectedYear() {
        return selectedYear;
    }

    public int getThisYear() {
        return thisYear;
    }

    public List getYearList() {
        return yearList;
    }

    public List getLeaveTypeList() {
        return leaveTypeList;
    }

    public String getLoginId() {
        return loginId;
    }

    public List getUsableList() {
        return usableList;
    }

    public List getUsedList() {
        return usedList;
    }

    public List getMaxHoursList() {
        return maxHoursList;
    }

    public void setSelectedYear(int selectedYear) {
        this.selectedYear = selectedYear;
    }

    public void setYearList(List yearList) {
        this.yearList = yearList;
    }

    public void setLeaveTypeList(List leaveTypeList) {
        this.leaveTypeList = leaveTypeList;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setUsableList(List unUsedList) {
        this.usableList = unUsedList;
    }

    public void setUsedList(List usedList) {
        this.usedList = usedList;
    }

    public void setMaxHoursList(List maxHoursList) {
        this.maxHoursList = maxHoursList;
    }
}
