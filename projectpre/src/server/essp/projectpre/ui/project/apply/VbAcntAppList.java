package server.essp.projectpre.ui.project.apply;

public class VbAcntAppList {
    private String rid;
    private String acntName;
    private String PMName;
    private String achieveBelong;
    private String applicationDate;
    private String applicationStatus;
    private String acntId;
    public String getAcntId() {
        return acntId;
    }
    public void setAcntId(String acntId) {
        this.acntId = acntId;
    }
    public String getAchieveBelong() {
        return achieveBelong;
    }
    public void setAchieveBelong(String achieveBelong) {
        this.achieveBelong = achieveBelong;
    }
    public String getAcntName() {
        return acntName;
    }
    public void setAcntName(String acntName) {
        this.acntName = acntName;
    }
    public String getApplicationStatus() {
        return applicationStatus;
    }
    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public String getPMName() {
        return PMName;
    }
    public void setPMName(String name) {
        PMName = name;
    }
    public String getApplicationDate() {
        return applicationDate;
    }
    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }
    public String getRid() {
        return rid;
    }
    public void setRid(String rid) {
        this.rid = rid;
    }
}
