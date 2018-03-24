package server.essp.attendance.leave.form;

import org.apache.struts.action.*;

public class AfLeaveReview extends ActionForm {
    private String rid;
    private String decision;
    private String actualDateFrom;
    private String actualDateTo;
    private String actualTimeFrom;
    private String actualTimeTo;
    private String actualTotalHours;
    private String comments;
    public String getActualDateFrom() {
        return actualDateFrom;
    }

    public String getActualDateTo() {
        return actualDateTo;
    }

    public String getActualTimeFrom() {
        return actualTimeFrom;
    }

    public String getActualTimeTo() {
        return actualTimeTo;
    }

    public String getActualTotalHours() {
        return actualTotalHours;
    }

    public String getComments() {
        return comments;
    }

    public String getDecision() {
        return decision;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setActualTotalHours(String actualTotalHours) {
        this.actualTotalHours = actualTotalHours;
    }

    public void setActualTimeTo(String actualTimeTo) {
        this.actualTimeTo = actualTimeTo;
    }

    public void setActualTimeFrom(String actualTimeFrom) {
        this.actualTimeFrom = actualTimeFrom;
    }

    public void setActualDateTo(String actualDateTo) {
        this.actualDateTo = actualDateTo;
    }

    public void setActualDateFrom(String actualDateFrom) {
        this.actualDateFrom = actualDateFrom;
    }
}
