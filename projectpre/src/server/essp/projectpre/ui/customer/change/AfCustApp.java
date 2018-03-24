package server.essp.projectpre.ui.customer.change;

import java.util.Date;
import java.util.List;

import org.apache.struts.action.ActionForm;

public class AfCustApp extends ActionForm {
 
    private String rid;
    private String applicationType;
    private String applicationStatus;
    private String applicant;
    private Date applicationDate;
    private String attribute;
    private String customerRid;
    private String customerId;
    private List idList;
    private String regId;
    private String groupId;
    private String customerShort;
    private String customerNameCn;
    private String customerNameEn;
    private String belongBd;
    private List belongBdList;
    private String belongSite;
    private List belongSiteList;
    private String customerClass;
    private String customerCountry;
    private String custDescription;
    private String description;
    private String createDate;
    private String alterDate;
    public String getAlterDate() {
        return alterDate;
    }
    public void setAlterDate(String alterDate) {
        this.alterDate = alterDate;
    }
    public String getCreateDate() {
        return createDate;
    }
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
    public String getApplicant() {
        return applicant;
    }
    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }
    public Date getApplicationDate() {
        return applicationDate;
    }
    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }
    public String getApplicationStatus() {
        return applicationStatus;
    }
    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }
    public String getApplicationType() {
        return applicationType;
    }
    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }
    public String getAttribute() {
        return attribute;
    }
    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
    public String getBelongBd() {
        return belongBd;
    }
    public void setBelongBd(String belongBd) {
        this.belongBd = belongBd;
    }
    public String getBelongSite() {
        return belongSite;
    }
    public void setBelongSite(String belongSite) {
        this.belongSite = belongSite;
    }
    public String getCustDescription() {
        return custDescription;
    }
    public void setCustDescription(String custDescription) {
        this.custDescription = custDescription;
    }
    public String getCustomerClass() {
        return customerClass;
    }
    public void setCustomerClass(String customerClass) {
        this.customerClass = customerClass;
    }
    public String getCustomerCountry() {
        return customerCountry;
    }
    public void setCustomerCountry(String customerCountry) {
        this.customerCountry = customerCountry;
    }
    public String getCustomerNameCn() {
        return customerNameCn;
    }
    public void setCustomerNameCn(String customerNameCn) {
        this.customerNameCn = customerNameCn;
    }
    public String getCustomerNameEn() {
        return customerNameEn;
    }
    public void setCustomerNameEn(String customerNameEn) {
        this.customerNameEn = customerNameEn;
    }
    public String getCustomerRid() {
        return customerRid;
    }
    public void setCustomerRid(String customerRid) {
        this.customerRid = customerRid;
    }
    public String getCustomerShort() {
        return customerShort;
    }
    public void setCustomerShort(String customerShort) {
        this.customerShort = customerShort;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getGroupId() {
        return groupId;
    }
    public void setGroupId(String groupId) {
        this.groupId = groupId;
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
    public List getBelongBdList() {
        return belongBdList;
    }
    public void setBelongBdList(List belongBdList) {
        this.belongBdList = belongBdList;
    }
    public List getBelongSiteList() {
        return belongSiteList;
    }
    public void setBelongSiteList(List belongSiteList) {
        this.belongSiteList = belongSiteList;
    }
    public List getIdList() {
        return idList;
    }
    public void setIdList(List idList) {
        this.idList = idList;
    }
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
