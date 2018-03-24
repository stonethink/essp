package server.essp.projectpre.ui.customer.check;

import java.util.List;

public class VbCustAppList {
    private String rid;
    private String applicantId;
    private String applicantName;
    private String applicationType;
    private String customerShort;
    private String regId;
    private String applicationDate;
    private String customerId;
    private List alterList;
    private String applicationStatus;
    private String description;
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getApplicationStatus() {
        return applicationStatus;
    }
    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public String getApplicantId() {
        return applicantId;
    }
    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
    }
    public String getApplicantName() {
        return applicantName;
    }
    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }
    public String getApplicationDate() {
        return applicationDate;
    }
    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }
    public String getApplicationType() {
        return applicationType;
    }
    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }
    public String getCustomerShort() {
        return customerShort;
    }
    public void setCustomerShort(String customerShort) {
        this.customerShort = customerShort;
    }
    public String getRegId() {
        return regId;
    }
    public void setRegId(String regId) {
        this.regId = regId;
    }
    public String getRid() {
        return rid;
    }
    public void setRid(String rid) {
        this.rid = rid;
    }
    public List getAlterList() {
        return alterList;
    }
    public void setAlterList(List alterList) {
        this.alterList = alterList;
    }
}
