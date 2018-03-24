package server.essp.issue.issue.viewbean;

import java.util.ArrayList;
import java.util.List;

import server.essp.issue.issue.conclusion.viewbean.VbIssueConclusion;
import server.essp.issue.issue.resolution.viewbean.VbIssueResolution;

public class VbAllDetailOfIssue {
   private VbIssue issue=new VbIssue();
   private VbIssueResolution issueResolution=new VbIssueResolution();
   private VbIssueConclusion issueConclusion=new VbIssueConclusion();
   private List issueStatusHistoryList=new ArrayList();
//   private VbIssueRiskHistoryList firstIssueRiskHistory=new VbIssueRiskHistoryList();//the first record of IssueRiskHistory
   private List  issueRiskHistoryList=new ArrayList();//the others records of IssueRiskHistory

    public VbIssue getIssue() {
        return issue;
    }

    public VbIssueConclusion getIssueConclusion() {
        return issueConclusion;
    }

    public VbIssueResolution getIssueResolution() {
        return issueResolution;
    }

    public List getIssueRiskHistoryList() {
        return issueRiskHistoryList;
    }

    public List getIssueStatusHistoryList() {
        return issueStatusHistoryList;
    }

    public void setIssue(VbIssue issue) {
        this.issue = issue;
    }

    public void setIssueConclusion(VbIssueConclusion issueConclusion) {
        this.issueConclusion = issueConclusion;
    }

    public void setIssueResolution(VbIssueResolution issueResolution) {
        this.issueResolution = issueResolution;
    }

    public void setIssueRiskHistoryList(List issueRiskHistoryList) {
        this.issueRiskHistoryList = issueRiskHistoryList;
    }

    public void setIssueStatusHistoryList(List issueStatusHistoryList) {
        this.issueStatusHistoryList = issueStatusHistoryList;
    }
}
