package server.essp.issue.issue.viewbean;

public class VbIssueRiskHistory {
   private String issueRid="";
   private String updateDate="";
   private String updateBy="";
   private String probabilityFrom="";
   private String probabilityTo="";
   private String riskLevelFrom="";
   private String riskLevelTo="";
   private String influenceFrom="";
   private String influenceTo="";
   private String categoryFrom="";
   private String categoryTo="";
   private String memo="";
    private String updateByScope;

    public String getCategoryFrom() {
        return categoryFrom;
    }

    public String getCategoryTo() {
        return categoryTo;
    }

    public String getIssueRid() {
        return issueRid;
    }

    public String getMemo() {
        return memo;
    }

    public String getProbabilityFrom() {
        return probabilityFrom;
    }

    public String getProbabilityTo() {
        return probabilityTo;
    }

    public String getRiskLevelFrom() {
        return riskLevelFrom;
    }

    public String getRiskLevelTo() {
        return riskLevelTo;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public String getInfluenceFrom() {
        return influenceFrom;
    }

    public String getInfluenceTo() {
        return influenceTo;
    }

    public String getUpdateByScope() {
        return updateByScope;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public void setRiskLevelTo(String riskLevelTo) {
        this.riskLevelTo = riskLevelTo;
    }

    public void setRiskLevelFrom(String riskLevelFrom) {
        this.riskLevelFrom = riskLevelFrom;
    }

    public void setProbabilityTo(String probabilityTo) {
        this.probabilityTo = probabilityTo;
    }

    public void setProbabilityFrom(String probabilityFrom) {
        this.probabilityFrom = probabilityFrom;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public void setIssueRid(String issueRid) {
        this.issueRid = issueRid;
    }

    public void setCategoryTo(String categoryTo) {
        this.categoryTo = categoryTo;
    }

    public void setCategoryFrom(String categoryFrom) {
        this.categoryFrom = categoryFrom;
    }

    public void setInfluenceFrom(String influenceFrom) {
        this.influenceFrom = influenceFrom;
    }

    public void setInfluenceTo(String influenceTo) {
        this.influenceTo = influenceTo;
    }

    public void setUpdateByScope(String updateByScope) {
        this.updateByScope = updateByScope;
    }

}
