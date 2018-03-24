package server.essp.attendance.leave.viewbean;

import java.util.*;

public class VbLeaveList {
    private List leaveList;

    private int selectedYear;
    public VbLeaveList(){
    }

    public int getSelectedYear() {
        return selectedYear;
    }

    public List getLeaveList() {
        return leaveList;
    }

    public void setSelectedYear(int selectedYear) {
        this.selectedYear = selectedYear;
    }

    public void setLeaveList(List leaveList) {
        this.leaveList = leaveList;
    }
}
