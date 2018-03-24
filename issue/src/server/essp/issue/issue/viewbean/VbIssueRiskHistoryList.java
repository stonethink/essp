package server.essp.issue.issue.viewbean;

import java.util.*;

public class VbIssueRiskHistoryList {
  String time="";
  String who="";
  String riskLevel="";
  String probability="";
  List influence=new ArrayList();
  List category=new ArrayList();
    private String whoScope;
    public List getCategory() {
        return category;
    }

    public List getInfluence() {
        return influence;
    }

    public String getProbability() {
        return probability;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public String getTime() {
        return time;
    }

    public String getWho() {
        return who;
    }

    public String getWhoScope() {
        return whoScope;
    }

    public void setCategory(List category) {
        this.category = category;
    }

    public void setInfluence(List influence) {
        this.influence = influence;
    }

    public void setProbability(String probability) {
        this.probability = probability;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public void setWhoScope(String whoScope) {
        this.whoScope = whoScope;
    }
}
