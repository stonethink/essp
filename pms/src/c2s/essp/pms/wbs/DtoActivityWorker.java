package c2s.essp.pms.wbs;

import java.util.Date;

import c2s.dto.DtoBase;

public class DtoActivityWorker extends DtoBase {
    /** identifier field */
    private Long acntRid;

    /** identifier field */
    private Long activityRid;

    /** identifier field */
    private Long rid;

    /** nullable persistent field */
    private String loginId;

    private String empName;
    /** nullable persistent field */
    private String jobCode;

    /** nullable persistent field */
    private String jobcodeId;

    /** nullable persistent field */
    private String roleName;

    /** nullable persistent field */
    private String jobDescription;

    /** nullable persistent field */
    private Date planStart;

    /** nullable persistent field */
    private Date planFinish;

    /** nullable persistent field */
    private Long planWorkTime;

    /** nullable persistent field */
    private Date actualStart;

    /** nullable persistent field */
    private Date actualFinish;

    /** nullable persistent field */
    private Long personWorkTime;

    /** nullable persistent field */
    private Long actualWorkTime;

    /** nullable persistent field */
    private Long etcWorkTime;

    /** nullable persistent field */
    private String remark;

    public Long getAcntRid() {
        return acntRid;
    }

    public Long getActivityRid() {
        return activityRid;
    }

    public Date getActualFinish() {
        return actualFinish;
    }

    public Date getActualStart() {
        return actualStart;
    }

    public Long getActualWorkTime() {
        return actualWorkTime;
    }

    public Long getEtcWorkTime() {
        return etcWorkTime;
    }

    public String getJobCode() {

        return jobCode;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public String getLoginId() {
        return loginId;
    }

    public Long getPersonWorkTime() {

        return personWorkTime;
    }

    public Date getPlanFinish() {
        return planFinish;
    }

    public Date getPlanStart() {
        return planStart;
    }

    public Long getPlanWorkTime() {
        return planWorkTime;
    }

    public String getRoleName() {
        return roleName;
    }

    public Long getRid() {

        return rid;
    }

    public String getRemark() {
        return remark;
    }

    public String getJobcodeId() {
        return jobcodeId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setRid(Long rid) {

        this.rid = rid;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setPlanWorkTime(Long planWorkTime) {
        this.planWorkTime = planWorkTime;
    }

    public void setPlanStart(Date planStart) {
        this.planStart = planStart;

    }

    public void setPlanFinish(Date planFinish) {
        this.planFinish = planFinish;
    }

    public void setPersonWorkTime(Long personWorkTime) {

        this.personWorkTime = personWorkTime;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public void setJobCode(String jobCode) {

        this.jobCode = jobCode;
    }

    public void setEtcWorkTime(Long etcWorkTime) {
        this.etcWorkTime = etcWorkTime;
    }

    public void setActualWorkTime(Long actualWorkTime) {
        this.actualWorkTime = actualWorkTime;
    }

    public void setActualStart(Date actualStart) {
        this.actualStart = actualStart;
    }

    public void setActualFinish(Date actualFinish) {
        this.actualFinish = actualFinish;
    }

    public void setActivityRid(Long activityRid) {
        this.activityRid = activityRid;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public void setJobcodeId(String jobcodeId) {
        this.jobcodeId = jobcodeId;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }
}
