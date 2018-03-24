package c2s.essp.pms.account;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import c2s.dto.DtoBase;
import c2s.dto.DtoUtil;

public class DtoAcntLoaborRes extends DtoBase implements Cloneable{
    public static final String RESOURCE_STATUS_IN = "I";
    public static final String RESOURCE_STATUS_OUT = "O";
    public static final String RESOURCE_STATUS_NOT_READY = "N";

    public static final String LOGINID_STATUS_IN = "1";
    public static final String LOGINID_STATUS_OUT = "0";

    public static final String[] RESOURCE_STATUS_NAME = {"In",
        "Out", "Not Ready"};

    public static final String[] RESOURCE_STATUS_VALUE = {RESOURCE_STATUS_IN,
        RESOURCE_STATUS_OUT, RESOURCE_STATUS_NOT_READY};


    /** nullable persistent field */
    private String loginId;


    /** nullable persistent field */
    private String empName;


    /** nullable persistent field */
    private String jobcodeId;

    private String jobCode;

    /** nullable persistent field */
    private String roleName;


    /** nullable persistent field */
    private String jobDescription;

    private Long acntRid;

    private String remark;
    private Long rid;


    /**
     * 表示该资源的状态：1－该资源还未获得；2－该资源在项目内；3－该资源已调出
     */
    private String resStatus;


    /**
     * 表示该LoginId的状态，用于判断该人员是内部，还是外部的，0－资源属于外部人员；1－表示内部人员
     */
    private String loginidStatus;
    private List planDetail = new ArrayList();

    public String getEmpName() {
        return empName;
    }

    public String getJobcodeId() {
        return jobcodeId;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public String getLoginId() {
        return loginId;
    }

    public Date getPlanFinish() {
        if( planDetail.size() <= 0)
            return null;
        DtoAcntLaborResDetail detail = null;
        Date planFinish = null;
        for(int i = 0;i < planDetail.size() ;i ++){
            detail = (DtoAcntLaborResDetail) planDetail.get(i);
            if(detail.isDelete())
                continue;
            Date endDate = detail.getEndDate();
            if(planFinish == null ||
               (endDate != null && endDate.after(planFinish)))
                planFinish = endDate;
        }
        return planFinish;
    }
    public Date getEndDate(){
        return getPlanFinish();
    }
    public Date getBeginDate(){
        return getPlanStart();
    }
    public Date getPlanStart() {
        if( planDetail.size() <= 0)
            return null;
        DtoAcntLaborResDetail detail = null;
        Date planStart = null;
        for(int i = 0;i < planDetail.size() ;i ++){
            detail = (DtoAcntLaborResDetail) planDetail.get(i);
            if(detail.isDelete())
                continue;
            Date beginDate = detail.getBeginDate();
            if(planStart == null ||
               (beginDate != null && beginDate.before(planStart)))
                planStart = beginDate;
        }
        return planStart;
    }
    public Double getHour(){
        return getPlanWorkTime();
    }
    public Double getPlanWorkTime() {
        double sum = 0;
        for(int i = 0;i < planDetail.size() ;i ++){
            DtoAcntLaborResDetail detail = (DtoAcntLaborResDetail) planDetail.get(i);
            if(detail.isDelete())
                continue;
            Double hour = detail.getHour();
            sum += (hour == null) ? 0 : hour.doubleValue();
        }
        return new Double(sum);
    }

    public String getRoleName() {
        return roleName;
    }

    public String getRemark() {
        return remark;
    }

    public Long getAcntRid() {
        return acntRid;
    }

    public Long getRid() {
        return rid;
    }

    public String getResStatus() {
        return resStatus;
    }

    public String getLoginidStatus() {
        return loginidStatus;
    }

    public String getJobCode() {
        return jobCode;
    }

    public List getPlanDetail() {
        return planDetail;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public void setJobcodeId(String jobcodeId) {
        this.jobcodeId = jobcodeId;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public void setResStatus(String resStatus) {
        this.resStatus = resStatus;
    }

    public void setLoginidStatus(String loginidStatus) {
        this.loginidStatus = loginidStatus;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public void setPlanDetail(List planDetail) {
        this.planDetail = planDetail;
    }

    public void addDetail(DtoAcntLaborResDetail d){
        planDetail.add(d);
    }

    public void removeDetail(DtoAcntLaborResDetail d){
        planDetail.remove(d);
    }

    public void removeAllDetail(){
        planDetail.clear();
    }
    public Object clone(){
        DtoAcntLoaborRes copy = new DtoAcntLoaborRes();
        try {
            DtoUtil.copyBeanToBean(copy, this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return copy;
    }
}
