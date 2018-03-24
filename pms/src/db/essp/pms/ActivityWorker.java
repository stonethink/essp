package db.essp.pms;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class ActivityWorker implements Serializable {

    /** identifier field */
    private Long rid;

    /** nullable persistent field */
    private String loginId;

    /** nullable persistent field */
    private String jobcodeId;

    /** nullable persistent field */
    private String empName;

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

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** nullable persistent field */
    private db.essp.pms.Activity activity;

    /** full constructor */
    public ActivityWorker(Long rid, String loginId, String jobcodeId, String empName, String roleName, String jobDescription, Date planStart, Date planFinish, Long planWorkTime, Date actualStart, Date actualFinish, Long personWorkTime, Long actualWorkTime, Long etcWorkTime, String remark, String rst, Date rct, Date rut, db.essp.pms.Activity activity) {
        this.rid = rid;
        this.loginId = loginId;
        this.jobcodeId = jobcodeId;
        this.empName = empName;
        this.roleName = roleName;
        this.jobDescription = jobDescription;
        this.planStart = planStart;
        this.planFinish = planFinish;
        this.planWorkTime = planWorkTime;
        this.actualStart = actualStart;
        this.actualFinish = actualFinish;
        this.personWorkTime = personWorkTime;
        this.actualWorkTime = actualWorkTime;
        this.etcWorkTime = etcWorkTime;
        this.remark = remark;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
        this.activity = activity;
    }

    /** default constructor */
    public ActivityWorker() {
    }

    /** minimal constructor */
    public ActivityWorker(Long rid) {
        this.rid = rid;
    }

    public Long getRid() {
        return this.rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getLoginId() {
        return this.loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getJobcodeId() {
        return this.jobcodeId;
    }

    public void setJobcodeId(String jobcodeId) {
        this.jobcodeId = jobcodeId;
    }

    public String getEmpName() {
        return this.empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getJobDescription() {
        return this.jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public Date getPlanStart() {
        return this.planStart;
    }

    public void setPlanStart(Date planStart) {
        this.planStart = planStart;
    }

    public Date getPlanFinish() {
        return this.planFinish;
    }

    public void setPlanFinish(Date planFinish) {
        this.planFinish = planFinish;
    }

    public Long getPlanWorkTime() {
        return this.planWorkTime;
    }

    public void setPlanWorkTime(Long planWorkTime) {
        this.planWorkTime = planWorkTime;
    }

    public Date getActualStart() {
        return this.actualStart;
    }

    public void setActualStart(Date actualStart) {
        this.actualStart = actualStart;
    }

    public Date getActualFinish() {
        return this.actualFinish;
    }

    public void setActualFinish(Date actualFinish) {
        this.actualFinish = actualFinish;
    }

    public Long getPersonWorkTime() {
        return this.personWorkTime;
    }

    public void setPersonWorkTime(Long personWorkTime) {
        this.personWorkTime = personWorkTime;
    }

    public Long getActualWorkTime() {
        return this.actualWorkTime;
    }

    public void setActualWorkTime(Long actualWorkTime) {
        this.actualWorkTime = actualWorkTime;
    }

    public Long getEtcWorkTime() {
        return this.etcWorkTime;
    }

    public void setEtcWorkTime(Long etcWorkTime) {
        this.etcWorkTime = etcWorkTime;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRst() {
        return this.rst;
    }

    public void setRst(String rst) {
        this.rst = rst;
    }

    public Date getRct() {
        return this.rct;
    }

    public void setRct(Date rct) {
        this.rct = rct;
    }

    public Date getRut() {
        return this.rut;
    }

    public void setRut(Date rut) {
        this.rut = rut;
    }

    public db.essp.pms.Activity getActivity() {
        return this.activity;
    }

    public void setActivity(db.essp.pms.Activity activity) {
        this.activity = activity;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("rid", getRid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ActivityWorker) ) return false;
        ActivityWorker castOther = (ActivityWorker) other;
        return new EqualsBuilder()
            .append(this.getRid(), castOther.getRid())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getRid())
            .toHashCode();
    }

}
