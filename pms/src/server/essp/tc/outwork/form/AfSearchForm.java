package server.essp.tc.outwork.form;

import org.apache.struts.action.ActionForm;

public class AfSearchForm extends ActionForm {
    private String rid;
    private String acntRid;
    private String loginId;
    private String beginDate;
    private String endDate;
    private int days;
    private boolean isAutoFillWR;
    private boolean activityRid;
    private String departRid;
    public AfSearchForm() {
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public void setAcntRid(String acntRid) {

        this.acntRid = acntRid;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public void setIsAutoFillWR(boolean isAutoFillWR) {
        this.isAutoFillWR = isAutoFillWR;
    }

    public void setActivityRid(boolean activityRid) {
        this.activityRid = activityRid;
    }

    public void setDepartRid(String departRid) {
        this.departRid = departRid;
    }

    public String getRid() {
        return rid;
    }

    public String getAcntRid() {

        return acntRid;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public int getDays() {
        return days;
    }

    public boolean isIsAutoFillWR() {
        return isAutoFillWR;
    }

    public boolean isActivityRid() {
        return activityRid;
    }

    public String getDepartRid() {
        return departRid;
    }
}
