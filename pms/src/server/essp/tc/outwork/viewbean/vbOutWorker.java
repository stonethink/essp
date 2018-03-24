package server.essp.tc.outwork.viewbean;

import java.util.List;

public class vbOutWorker {
    private String account;
    private String loginId;
    private String chineseName;
    private String beginDate;
    private String endDate;
    private String days;
    private String activity;
    private String isAutoFillWR;
    private String destAddress;
    private Long rid;
    private String organization;
    private String isTravellingAllowance;
    private String evectionType;
    private List evectionTypeList;
    public vbOutWorker() {
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
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

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public void setIsAutoFillWR(String isAutoFillWR) {
        this.isAutoFillWR = isAutoFillWR;
    }

    public void setDestAddress(String destAddress) {
        this.destAddress = destAddress;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public void setIsTravellingAllowance(String isTravellingAllowance) {
        this.isTravellingAllowance = isTravellingAllowance;
    }

    public void setEvectionType(String evectionType) {
        this.evectionType = evectionType;
    }

    public void setEvectionTypeList(List evectionTypeList) {
        this.evectionTypeList = evectionTypeList;
    }


    public String getAccount() {
        return account;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getChineseName() {
        return chineseName;
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

    public String getActivity() {
        return activity;
    }

    public String getIsAutoFillWR() {
        return isAutoFillWR;
    }

    public String getDestAddress() {
        return destAddress;
    }

    public Long getRid() {
        return rid;
    }

    public String getOrganization() {
        return organization;
    }

    public String getIsTravellingAllowance() {
        return isTravellingAllowance;
    }

    public String getEvectionType() {
        return evectionType;
    }

    public List getEvectionTypeList() {
        return evectionTypeList;
    }


}
