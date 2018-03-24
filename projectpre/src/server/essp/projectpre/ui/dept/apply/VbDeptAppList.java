package server.essp.projectpre.ui.dept.apply;

public class VbDeptAppList {
    private String rid;
    private String deptManager;
    private String acntName;
    private String acntId;
    private String applicationDate;
    private String applicationStatus;
    public String getAcntName() {
        return acntName;
    }
    public void setAcntName(String acntName) {
        this.acntName = acntName;
    }
    public String getApplicationDate() {
        return applicationDate;
    }
    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }
    public String getApplicationStatus() {
        return applicationStatus;
    }
    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public String getRid() {
        return rid;
    }
    public void setRid(String rid) {
        this.rid = rid;
    }
    public String getDeptManager() {
        return deptManager;
    }
    public void setDeptManager(String deptManager) {
        this.deptManager = deptManager;
    }
    public String getAcntId() {
        return acntId;
    }
    public void setAcntId(String acntId) {
        this.acntId = acntId;
    }
    
}