package server.essp.issue.issue.resolution.viewbean;

import java.util.*;

public class VbInfluence {
    private String influence;
    private String impactLevel;
    private String weight;
    private String remark;
    private List   impactLevelOptions=new ArrayList();

    public VbInfluence() {
    }

    public String getImpactLevel() {
        return impactLevel;
    }

    public String getInfluence() {
        return influence;
    }

    public String getRemark() {
        return remark;
    }

    public String getWeight() {
        return weight;
    }

    public List getImpactLevelOptions() {
        return impactLevelOptions;
    }

    public void setImpactLevel(String impactLevel) {
        this.impactLevel = impactLevel;
    }

    public void setInfluence(String influence) {
        this.influence = influence;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setImpactLevelOptions(List impactLevelOptions) {
        this.impactLevelOptions = impactLevelOptions;
    }
}
