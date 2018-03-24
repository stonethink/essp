package server.essp.attendance.overtime.viewbean;

import java.util.*;
import server.framework.taglib.util.SelectOptionImpl;

public class VbOverTimeList {
    public static int LIST_YEAR_NUM = 10;
    private List overTimeList;
    private List yearList;
    private int selectedYear;
    private int thisYear;
    public VbOverTimeList(){
        Calendar today = Calendar.getInstance();
        thisYear = today.get(Calendar.YEAR);
        yearList = new ArrayList();
        for(int i = thisYear; i > (thisYear - LIST_YEAR_NUM); i --){
            SelectOptionImpl option = new SelectOptionImpl(i + "",i + "");
            yearList.add(option);
        }
        int nextYear = thisYear + 1;
        SelectOptionImpl option = new SelectOptionImpl(nextYear + "",nextYear + "");
        yearList.add(0,option);
        selectedYear = thisYear ;
    }

    public List getOverTimeList() {
        return overTimeList;
    }

    public int getSelectedYear() {
        return selectedYear;
    }

    public int getThisYear() {
        return thisYear ;
    }

    public List getYearList() {
        return yearList;
    }

    public void setOverTimeList(List overTimeList) {
        this.overTimeList = overTimeList;
    }

    public void setSelectedYear(int selectedYear) {
        this.selectedYear = selectedYear;
    }

}
