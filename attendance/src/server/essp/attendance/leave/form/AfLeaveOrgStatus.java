package server.essp.attendance.leave.form;

import org.apache.struts.action.*;

public class AfLeaveOrgStatus extends ActionForm {
    private String orgId;
    private String includeSub;
    private String minUsableHours;
    public String getIncludeSub() {
        return includeSub;
    }

    public String getOrgId() {
        return orgId;
    }

    public String getMinUsableHours() {
        return minUsableHours;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public void setIncludeSub(String includeSub) {
        this.includeSub = includeSub;
    }

    public void setMinUsableHours(String minUsableHours) {
        this.minUsableHours = minUsableHours;
    }
}
