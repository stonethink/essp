package server.essp.attendance.leave.viewbean;

import java.util.*;

public class VbLeaveOrgStatus {
    private List allOrg;
    private List workersStatus;
    private Boolean includeSub = Boolean.FALSE;
    private String orgId;
    private String minUsableHours;
    private List leaveType;
    public List getAllOrg() {
        return allOrg;
    }

    public Boolean getIncludeSub() {
        return includeSub;
    }

    public String getMinUsableHours() {
        return minUsableHours;
    }

    public String getOrgId() {
        return orgId;
    }

    public List getWorkersStatus() {
        return workersStatus;
    }

    public List getLeaveType() {
        return leaveType;
    }

    public void setAllOrg(List allOrg) {
        this.allOrg = allOrg;
    }

    public void setIncludeSub(Boolean includeSub) {
        this.includeSub = includeSub;
    }

    public void setWorkersStatus(List workersStatus) {
        this.workersStatus = workersStatus;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public void setMinUsableHours(String minUsableHours) {
        this.minUsableHours = minUsableHours;
    }

    public void setLeaveType(List leaveType) {
        this.leaveType = leaveType;
    }

}
