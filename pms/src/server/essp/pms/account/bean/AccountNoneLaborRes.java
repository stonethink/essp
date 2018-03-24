package server.essp.pms.account.bean;

import java.util.Date;

public class AccountNoneLaborRes {
    private String envName;
    private String resourceId;
    private String requirement;
    private Date finishDate;
    private String remark;
    private Long resourceNumber;
    private String resourceName;
    private Date startDate;
    public AccountNoneLaborRes() {
    }

    public void setEnvName(String envName) {
        this.envName = envName;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setResourceNumber(Long resourceNumber) {
        this.resourceNumber = resourceNumber;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getEnvName() {
        return envName;
    }

    public String getResourceId() {
        return resourceId;
    }

    public String getRequirement() {
        return requirement;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public String getRemark() {
        return remark;
    }

    public Long getResourceNumber() {
        return resourceNumber;
    }

    public String getResourceName() {
        return resourceName;
    }

    public Date getStartDate() {
        return startDate;
    }
}
