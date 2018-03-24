package server.essp.projectpre.ui.customer.add;

import java.util.List;

import org.apache.struts.action.ActionForm;

public class AfCustomerApplication extends ActionForm {
   /**
    * …Í«Î∫≈
    */
    private String rid;
    /**
     * …Í«Î¿‡–Õ
     */
    private String applicationType;
    /**
     * …Í«Î◊¥Ã¨
     */
    private String applicationStatus;
    /**
     * …Í«ÎID
     */
    private String applicantId;
    /**
     * …Í«Î»À–’√˚
     */
    private String applicantName;
    private String applicationDate;
    private String attribute;
    private String customerRid;
    private String customerId;
    private List customerIdList;
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
    private List customerClassList;
    private String customerCountry;
    private String countryCode;
    private List customerCountryList;
    private String custDescription;
    private String description;
    private String createDate;
    private String alterDate;
    private String shortName;
    private String duplicate;
    private String creator;
    public String getCreator() {
        return creator;
    }
    public void setCreator(String creator) {
        this.creator = creator;
    }
    public String getDuplicate() {
        return duplicate;
    }
    public void setDuplicate(String duplicate) {
        this.duplicate = duplicate;
    }
    public String getShortName() {
        return shortName;
    }
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
    public List getCustomerIdList() {
        return customerIdList;
    }
    public void setCustomerIdList(List customerIdList) {
        this.customerIdList = customerIdList;
    }
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
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
    
  
    public List getCustomerClassList() {
        return customerClassList;
    }
    public void setCustomerClassList(List customerClassList) {
        this.customerClassList = customerClassList;
    }
    public List getCustomerCountryList() {
        return customerCountryList;
    }
    public void setCustomerCountryList(List customerCountryList) {
        this.customerCountryList = customerCountryList;
    }
    public String getCountryCode() {
        return countryCode;
    }
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
