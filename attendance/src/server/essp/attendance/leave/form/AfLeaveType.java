package server.essp.attendance.leave.form;

import org.apache.struts.action.*;

public class AfLeaveType extends ActionForm {
    private String leaveName;
    private String rid;
    private String settlementWay;
    private String maxDay;
    private String seq;
    private String relation;
    private String status;
    private String description;
    public String getDescription() {
        return description;
    }

    public String getRelation() {

        return relation;
    }

    public String getLeaveName() {
        return leaveName;
    }

    public String getMaxDay() {
        return maxDay;
    }

    public String getRid() {
        return rid;
    }

    public String getSettlementWay() {
        return settlementWay;
    }

    public String getStatus() {
        return status;
    }

    public String getSeq() {
        return seq;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRelation(String relation) {

        this.relation = relation;
    }

    public void setLeaveName(String leaveName) {
        this.leaveName = leaveName;
    }

    public void setMaxDay(String maxDay) {
        this.maxDay = maxDay;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public void setSettlementWay(String settlementWay) {
        this.settlementWay = settlementWay;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }
}
