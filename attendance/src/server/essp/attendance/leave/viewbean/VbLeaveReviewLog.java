package server.essp.attendance.leave.viewbean;

import java.util.*;

public class VbLeaveReviewLog {
    private String loginId;
    private Date logDate;
    private String deal;
    private String comments;
    public String getComments() {
        return comments;
    }

    public String getDeal() {
        return deal;
    }

    public Date getLogDate() {
        return logDate;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public void setDeal(String deal) {
        this.deal = deal;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

}
