package server.essp.projectpre.ui.project.check;

import java.util.List;

public class VbCheckProjectChange {
    
    private String acntId;
    private String applicant;
    private String applicationDate;
    private List masterList;
    private List techInfoList;
    private List customerInfoList;
    private String comment;
    private String customerId;
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public List getCustomerInfoList() {
        return customerInfoList;
    }
    public void setCustomerInfoList(List customerInfoList) {
        this.customerInfoList = customerInfoList;
    }
    public String getAcntId() {
        return acntId;
    }
    public void setAcntId(String acntId) {
        this.acntId = acntId;
    }
    public String getApplicant() {
        return applicant;
    }
    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }
    public String getApplicationDate() {
        return applicationDate;
    }
    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }
    public List getMasterList() {
        return masterList;
    }
    public void setMasterList(List masterList) {
        this.masterList = masterList;
    }
    public List getTechInfoList() {
        return techInfoList;
    }
    public void setTechInfoList(List techInfoList) {
        this.techInfoList = techInfoList;
    }
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

}
