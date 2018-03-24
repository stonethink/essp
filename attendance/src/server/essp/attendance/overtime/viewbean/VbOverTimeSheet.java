package server.essp.attendance.overtime.viewbean;

import java.util.*;


public class VbOverTimeSheet {
    private String acntId;
    private String name;
    private String loginId;
    private Date beginDate;
    private Date endDate;
    private Double hours;
    private String desc;

    public String getAcntId() {
        return acntId;
    }

    public String getName() {
        return name;
    }

    public String getLoginId() {
        return loginId;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Double getHours() {
        return hours;
    }

    public String getDesc() {
        return desc;
    }

    public void setAcntId(String acntId) {
        this.acntId = acntId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
