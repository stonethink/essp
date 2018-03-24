package server.essp.tc.attcompare;

import org.apache.struts.action.ActionForm;

public class VbCompareResult extends ActionForm{
    private String loginId;
    private String date;
    private String appTime;
    private String appHours;
    private String appStatus;
    private String actualTime;
    private String actualHours;
    private String isDelete;
    private String noattRid;

    public VbCompareResult() {
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAppTime(String appTime) {

        this.appTime = appTime;
    }

    public void setAppHours(String appHours) {

        this.appHours = appHours;
    }

    public void setAppStatus(String appStatus) {
        this.appStatus = appStatus;
    }

    public void setActualTime(String actualTime) {
        this.actualTime = actualTime;
    }

    public void setActualHours(String actualHours) {
        this.actualHours = actualHours;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public void setNoattRid(String noattRid) {
        this.noattRid = noattRid;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getDate() {
        return date;
    }

    public String getAppTime() {

        return appTime;
    }

    public String getAppHours() {

        return appHours;
    }

    public String getAppStatus() {
        return appStatus;
    }

    public String getActualTime() {
        return actualTime;
    }

    public String getActualHours() {
        return actualHours;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public String getNoattRid() {
        return noattRid;
    }
}
