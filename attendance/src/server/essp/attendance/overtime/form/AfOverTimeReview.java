package server.essp.attendance.overtime.form;

import org.apache.struts.action.*;

public class AfOverTimeReview extends ActionForm {
    private String rid;
    private String actualDateFrom;
    private String actualDateTo;
    private String actualTimeFrom;
    private String actualTimeTo;
    private String actualTotalHours;
    private String comments;
    private String decision;
    private String actualIsEachDay;
    private String detailInfo;
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

    public String getActualIsEachDay() {

        return actualIsEachDay;
    }

    public String getDetailInfo() {
        return detailInfo;
    }

    public void setActualDateFrom(String actualDateFrom) {

        this.actualDateFrom = actualDateFrom;
    }

    public void setActualDateTo(String actualDateTo) {

        this.actualDateTo = actualDateTo;
    }

    public void setActualTimeFrom(String actualTimeFrom) {

        this.actualTimeFrom = actualTimeFrom;
    }

    public void setActualTimeTo(String actualTimeTo) {

        this.actualTimeTo = actualTimeTo;
    }

    public void setActualTotalHours(String actualTotalHours) {

        this.actualTotalHours = actualTotalHours;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public void setActualIsEachDay(String actualIsEachDay) {

        this.actualIsEachDay = actualIsEachDay;
    }

    public void setDetailInfo(String detailInfo) {
        this.detailInfo = detailInfo;
    }

}
