package server.essp.tc.outwork.form;

import org.apache.struts.action.ActionForm;

public class AfOutWorkerForm extends ActionForm {
    private String acntRid;
    private String loginId;
    private String beginDate;
    private String endDate;
    private String days;
    private String isAutoFillWR;
    private String activityRid;
    private String destAddress;
    private String isTravellingAllowance;
    private String evectionType;
    public AfOutWorkerForm() {
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

    public void setDays(String days) {
        this.days = days;
    }

    public void setIsAutoFillWR(String isAutoFillWR) {
        this.isAutoFillWR = isAutoFillWR;
    }

    public void setActivityRid(String activityRid) {
        this.activityRid = activityRid;
    }

    public void setDestAddress(String destAddress) {
        this.destAddress = destAddress;
    }

    public void setIsTravellingAllowance(String isTravellingAllowance) {
        this.isTravellingAllowance = isTravellingAllowance;
    }

    public void setEvectionType(String evectionType) {
        this.evectionType = evectionType;
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

    public String getDays() {
        return days;
    }

    public String getIsAutoFillWR() {
        return isAutoFillWR;
    }

    public String getActivityRid() {
        return activityRid;
    }

    public String getDestAddress() {
        return destAddress;
    }

    public String getIsTravellingAllowance() {
        return isTravellingAllowance;
    }

    public String getEvectionType() {
        return evectionType;
    }
}
