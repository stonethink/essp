package server.essp.issue.issue.resolution.form;

import org.apache.struts.action.*;
import org.apache.struts.upload.*;
public class AfIssueResolution extends ActionForm {
    private String rid;
    private String accountId;
    private String probability;
    private String riskLevel;
    private String[] influences;
    private String[] impactLevels;
    private String[] weights;
    private String[] remarks;
    private String[] categories;
    private String[] categoryValues;
    private String resolution;
    private String resolutionBy;
    private String resolutionByCustomer;
    private String planFinishDate;
    private String assignedDate;
    private FormFile attachment;
    private String attachmentDesc;
    private String readOnly;
    public String getAccountId() {
        return accountId;
    }

    public String getAssignedDate() {
        return assignedDate;
    }

    public String getAttachmentDesc() {
        return attachmentDesc;
    }

    public String[] getCategories() {
        return categories;
    }

    public String[] getCategoryValues() {
        return categoryValues;
    }

    public String[] getImpactLevels() {
        return impactLevels;
    }

    public String[] getInfluences() {
        return influences;
    }

    public String getPlanFinishDate() {
        return planFinishDate;
    }

    public String getProbability() {
        return probability;
    }

    public String[] getRemarks() {
        return remarks;
    }

    public String getResolution() {
        return resolution;
    }

    public String getRid() {
        return rid;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public String[] getWeights() {
        return weights;
    }

    public FormFile getAttachment() {
        return attachment;
    }

    public String getResolutionBy() {
        return resolutionBy;
    }

    public String getReadOnly() {
        return readOnly;
    }

    public String getResolutionByCustomer() {
        return resolutionByCustomer;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setAssignedDate(String assignedDate) {
        this.assignedDate = assignedDate;
    }

    public void setAttachmentDesc(String attachmentDesc) {
        this.attachmentDesc = attachmentDesc;
    }

    public void setCategories(String[] categorys) {
        this.categories = categorys;
    }

    public void setCategoryValues(String[] categoryValues) {
        this.categoryValues = categoryValues;
    }

    public void setImpactLevels(String[] impactLevels) {
        this.impactLevels = impactLevels;
    }

    public void setInfluences(String[] influences) {
        this.influences = influences;
    }

    public void setPlanFinishDate(String planFinishDate) {
        this.planFinishDate = planFinishDate;
    }

    public void setProbability(String probability) {
        this.probability = probability;
    }

    public void setRemarks(String[] remarks) {
        this.remarks = remarks;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public void setWeights(String[] weights) {
        this.weights = weights;
    }

    public void setAttachment(FormFile attachment) {
        this.attachment = attachment;
    }

    public void setResolutionBy(String resolutionBy) {
        this.resolutionBy = resolutionBy;
    }

    public void setReadOnly(String readOnly) {
        this.readOnly = readOnly;
    }

    public void setResolutionByCustomer(String resolutionByCustomer) {
        this.resolutionByCustomer = resolutionByCustomer;
    }
}
