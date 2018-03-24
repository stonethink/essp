package server.essp.tc.mail.genmail.contbean;

/**
 *此类用于PM确认信息催促EMAIL的内容Bean
 *author:Robin.Zhang
 */
public class PMConfirmBean {
    private String submiter;
    private String beginDate;
    private String endDate;
    private String accountInfo;
    public PMConfirmBean() {
    }

    public void setSubmiter(String submiter) {
        this.submiter = submiter;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setAccountInfo(String accountInfo) {
        this.accountInfo = accountInfo;
    }

    public String getSubmiter() {
        return submiter;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getAccountInfo() {
        return accountInfo;
    }
}
