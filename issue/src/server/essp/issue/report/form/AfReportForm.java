package server.essp.issue.report.form;

import org.apache.struts.action.*;

public class AfReportForm extends ActionForm {

    private String account;

    private String userName;

    private String accountId;

    private String dateBegin;

    private String dateEnd;

    private String dateBy;

    private String rejected;

    private String processing;

    private String delivered;

    private String closed;

    private String duplation;

    private String issueType;

    public AfReportForm() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
    }

    public String getAccountId() {
        return accountId;
    }

    public String getDateBegin() {
        return dateBegin;
    }

    public String getDateBy() {
        return dateBy;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public String getClosed() {
        return closed;
    }

    public String getDelivered() {
        return delivered;
    }

    public String getDuplation() {
        return duplation;
    }

    public String getProcessing() {
        return processing;
    }

    public String getRejected() {
        return rejected;
    }

    public String getIssueType() {
        return issueType;
    }

    public String getUserName() {
        return userName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setDateBegin(String dateBegin) {
        this.dateBegin = dateBegin;
    }

    public void setDateBy(String dateBy) {
        this.dateBy = dateBy;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public void setClosed(String closed) {
        this.closed = closed;
    }

    public void setDelivered(String delivered) {
        this.delivered = delivered;
    }

    public void setDuplation(String duplation) {
        this.duplation = duplation;
    }

    public void setProcessing(String processing) {
        this.processing = processing;
    }

    public void setRejected(String rejected) {
        this.rejected = rejected;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
