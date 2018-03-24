package server.essp.attendance.overtime.viewbean;

public class VbOverTimeClean {
    private String loginId;
    private String name;
    private String orgId;
    private String orgName;
    private Double usableHours;
    private Double cleanHours;
    public VbOverTimeClean() {
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public void setUsableHours(Double usableHours) {
        this.usableHours = usableHours;
    }

    public void setCleanHours(Double cleanHours) {
        this.cleanHours = cleanHours;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getName() {
        return name;
    }

    public String getOrgId() {
        return orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public Double getUsableHours() {
        if(usableHours == null) {
            usableHours = Double.valueOf(0);
        }
        return usableHours;
    }

    public Double getCleanHours() {
        return cleanHours;
    }

    public boolean getIsEnoughHours(){
        return getCleanHours() > getUsableHours();
    }
}
