package server.essp.issue.issue.viewbean;

public class VbIssueStatusHistory {
   private String issueRid="";
   private String updateDate="";
   private String statusFrom="        ";
   private String statusTo="";
   private String memo="";
   private String updateBy="";
   private String status="";
    private String updateByScope;
    public String getStatus(){
       return statusFrom+"¡ª¡ª>"+statusTo;
   }
    public String getIssueRid() {
        return issueRid;
    }

    public String getMemo() {
        return memo;
    }

    public String getStatusFrom() {
        return statusFrom;
    }

    public String getStatusTo() {
        return statusTo;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public String getUpdateByScope() {
        return updateByScope;
    }

    public void setIssueRid(String issueRid) {
        this.issueRid = issueRid;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public void setStatusFrom(String statusFrom) {
        this.statusFrom = statusFrom;
    }

    public void setStatusTo(String statusTo) {
        this.statusTo = statusTo;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public void setUpdateByScope(String updateByScope) {
        this.updateByScope = updateByScope;
    }

}
