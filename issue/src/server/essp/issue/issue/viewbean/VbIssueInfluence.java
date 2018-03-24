package server.essp.issue.issue.viewbean;

public class VbIssueInfluence {
   private String influenceName="";
   private String impactLevel="";
   private String weight="";
   private String remark="";
    public String getImpactLevel() {
        return impactLevel;
    }

    public String getInfluenceName() {
        return influenceName;
    }

    public String getRemark() {
        return remark;
    }

    public String getWeight() {
        return weight;
    }

    public void setImpactLevel(String impactLevel) {
        this.impactLevel = impactLevel;
    }

    public void setInfluenceName(String influenceName) {
        this.influenceName = influenceName;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
