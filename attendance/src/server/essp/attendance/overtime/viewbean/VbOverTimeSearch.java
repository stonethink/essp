package server.essp.attendance.overtime.viewbean;

import java.util.*;

public class VbOverTimeSearch {
    private String loginId;
    private String empName;
    private String orgName;
    private String acntName;
    private Date beginDate;
    private Date endDate;
    private Double totalHours;
    private Double shiftHours;
    private Double payedHours;
    private Long rid;
    private String chineseName;
    public VbOverTimeSearch() {
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setEmpName(String empName) {

        this.empName = empName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public void setAcntName(String acntName) {
        this.acntName = acntName;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setTotalHours(Double totalHours) {
        this.totalHours = totalHours;
    }

    public void setShiftHours(Double shiftHours) {
        this.shiftHours = shiftHours;
    }

    public void setPayedHours(Double payedHours) {
        this.payedHours = payedHours;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getEmpName() {

        return empName;
    }

    public String getOrgName() {
        return orgName;
    }

    public String getAcntName() {
        return acntName;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Double getTotalHours() {
        return totalHours;
    }

    public Double getShiftHours() {
        return shiftHours;
    }

    public Double getPayedHours() {
        return payedHours;
    }

    public Long getRid() {
        return rid;
    }

    public String getChineseName() {
        return chineseName;
    }

    public Double getUsableHours() {
        return totalHours - shiftHours - payedHours;
    }
}
