package server.essp.attendance.leave.viewbean;

import java.util.*;

public class VbLeaveType {
    private String leaveName;
    private Long rid;
    private String settlementWay;
    private Long maxDay;
    private Long seq;
    private String relation;
    private String status;
    private String description;
    private List settlementWayList = c2s.essp.attendance.Constant.SETTLEMENT_WAY_OPTIONS;
    private List relationList = c2s.essp.attendance.Constant.LEAVE_RELATION_OPTIONS;
    private String functionId;
    public static final String FUN_ID_ADD = "Add";
    public static final String FUN_ID_EDIT = "Edit";
    public String getDescription() {
        return description;
    }

    public String getRelation() {

        return relation;
    }

    public String getLeaveName() {
        return leaveName;
    }

    public Long getMaxDay() {
        return maxDay;
    }

    public Long getRid() {
        return rid;
    }

    public String getStatus() {
        return status;
    }

    public String getSettlementWay() {
        return settlementWay;
    }

    public List getSettlementWayList() {
        return settlementWayList;
    }

    public String getFunctionId() {
        return functionId;
    }

    public List getRelationList() {
        return relationList;
    }

    public Long getSeq() {
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

    public void setMaxDay(Long maxDay) {
        this.maxDay = maxDay;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public void setSettlementWay(String settlementWay) {
        this.settlementWay = settlementWay;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSettlementWayList(List settlementWayList) {
        this.settlementWayList = settlementWayList;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }


}
