package server.essp.issue.issue.resolution.viewbean;

import java.util.ArrayList;
import java.util.List;

/**
 * Resolution Issue界面显示所需内容
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class VbIssueResolution {
    private String rid;
    private String isMail;
    private String accountId;
    private String probability;
    private String riskLevel;
    private String resolution;
    private String assignedDate;
    private String planFinishDate;
    private String resolutionBy;
    private String resolutionByCustomer;
    private List resolutionByCustomerList=new ArrayList();
    private String attachment;
    private String attachmentDesc;
    private String probabilityFlag;
    private String impactLevelFlag;
    private String weightLevelFlag;
    private String remarkFlag;
    private String categoryValueFlag;
    private String resolutionFlag;
    private String planFinishDateFlag;
    private String resolutionByFlag;
    private String assignedDateFlag;
    private String attachmentFlag;
    private String attachmentDescFlag;


    private String principalFlag;
    private String pmFlag;
    private String customerFlag;



    /**
     * 此List中的单元为VbInfluence类型
     */
    private List influences=new ArrayList();
    /**
     * 此List中的单元为VbCategory类型
     */
    private List categories=new ArrayList();
    public String getAttachment() {
        return attachment;
    }

    public String getAttachmentDesc() {
        return attachmentDesc;
    }

    public List getCategories() {
        return categories;
    }

    public List getInfluences() {
        return influences;
    }

    public String getPlanFinishDate() {
        return planFinishDate;
    }

    public String getProbability() {
        return probability;
    }

    public String getResolution() {
        return resolution;
    }

    public String getResolutionBy() {
        return resolutionBy;
    }

    public String getRid() {
        return rid;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public String getAssignedDate() {
        return assignedDate;
    }

    public String getAccountId() {
        return accountId;
    }


    public String getAttachmentDescFlag() {
        return attachmentDescFlag;
    }

    public String getAttachmentFlag() {
        return attachmentFlag;
    }

    public String getCategoryValueFlag() {
        return categoryValueFlag;
    }

    public String getImpactLevelFlag() {
        return impactLevelFlag;
    }

    public String getPlanFinishDateFlag() {
        return planFinishDateFlag;
    }

    public String getProbabilityFlag() {
        return probabilityFlag;
    }

    public String getRemarkFlag() {
        return remarkFlag;
    }

    public String getResolutionByFlag() {
        return resolutionByFlag;
    }

    public String getResolutionFlag() {
        return resolutionFlag;
    }

    public String getWeightLevelFlag() {
        return weightLevelFlag;
    }

    public String getAssignedDateFlag() {
        return assignedDateFlag;
    }

    public String getPmFlag() {
        return pmFlag;
    }

    public String getPrincipalFlag() {
        return principalFlag;
    }

    public String getCustomerFlag() {
        return customerFlag;
    }

    public String getResolutionByCustomer() {
        return resolutionByCustomer;
    }

    public List getResolutionByCustomerList() {
        return resolutionByCustomerList;
    }

    public String getIsMail() {
        return isMail;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public void setAttachmentDesc(String attachmentDesc) {
        this.attachmentDesc = attachmentDesc;
    }

    public void setCategories(List categories) {
        this.categories = categories;
    }

    public void setInfluences(List influences) {
        this.influences = influences;
    }

    public void setPlanFinishDate(String planFinishDate) {
        this.planFinishDate = planFinishDate;
    }

    public void setProbability(String probability) {
        this.probability = probability;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public void setResolutionBy(String resolutionBy) {
        this.resolutionBy = resolutionBy;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public void setAssignedDate(String assignedDate) {
        this.assignedDate = assignedDate;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setAttachmentDescFlag(String attachmentDescFlag) {
        this.attachmentDescFlag = attachmentDescFlag;
    }

    public void setAttachmentFlag(String attachmentFlag) {
        this.attachmentFlag = attachmentFlag;
    }

    public void setCategoryValueFlag(String categoryValueFlag) {
        this.categoryValueFlag = categoryValueFlag;
    }

    public void setImpactLevelFlag(String impactLevelFlag) {
        this.impactLevelFlag = impactLevelFlag;
    }

    public void setProbabilityFlag(String probabilityFlag) {
        this.probabilityFlag = probabilityFlag;
    }

    public void setPlanFinishDateFlag(String planFinishDateFlag) {
        this.planFinishDateFlag = planFinishDateFlag;
    }

    public void setRemarkFlag(String remarkFlag) {
        this.remarkFlag = remarkFlag;
    }

    public void setResolutionByFlag(String resolutionByFlag) {
        this.resolutionByFlag = resolutionByFlag;
    }

    public void setResolutionFlag(String resolutionFlag) {
        this.resolutionFlag = resolutionFlag;
    }

    public void setWeightLevelFlag(String weightLevelFlag) {
        this.weightLevelFlag = weightLevelFlag;
    }

    public void setAssignedDateFlag(String assignedDateFlag) {
        this.assignedDateFlag = assignedDateFlag;
    }

    public void setPmFlag(String pmFlag) {
        this.pmFlag = pmFlag;
    }

    public void setPrincipalFlag(String principalFlag) {
        this.principalFlag = principalFlag;
    }

    public void setCustomerFlag(String customerFlag) {
        this.customerFlag = customerFlag;
    }

    public void setResolutionByCustomer(String resolutionByCustomer) {
        this.resolutionByCustomer = resolutionByCustomer;
    }

    public void setResolutionByCustomerList(List resolutionByCustomerList) {
        this.resolutionByCustomerList = resolutionByCustomerList;
    }

    public void setIsMail(String isMail) {
        this.isMail = isMail;
    }
}
