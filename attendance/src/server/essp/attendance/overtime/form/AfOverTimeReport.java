package server.essp.attendance.overtime.form;

import java.util.*;

import org.apache.struts.action.*;

public class AfOverTimeReport extends ActionForm{
    private Long acntRid;
    private List acntList;
    private String beginDate;
    private String endDate;
    public AfOverTimeReport() {
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public void setAcntList(List acntList) {
        this.acntList = acntList;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Long getAcntRid() {
        return acntRid;
    }

    public List getAcntList() {
        return acntList;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public String getEndDate() {
        return endDate;
    }
}
