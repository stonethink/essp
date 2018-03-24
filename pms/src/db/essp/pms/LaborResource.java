package db.essp.pms;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class LaborResource implements Serializable {

    /** identifier field */
    private Long rid;

    /** nullable persistent field */
    private String loginId;

    /** nullable persistent field */
    private String empName;

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
    private Double planWorkTime;

    /** nullable persistent field */
    private Date actualStart;

    /** nullable persistent field */
    private Date actualFinish;

    /** nullable persistent field */
    private Double personWorkTime;

    /** nullable persistent field */
    private Double actualWorkTime;

    /** nullable persistent field */
    private Double etcWorkTime;

    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    private String resStatus;

    /** nullable persistent field */
    private String loginidStatus;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** persistent field */
    private db.essp.pms.Account account;

    /** persistent field */
    private Set laborResourceDetails;

    /** full constructor */
    public LaborResource(Long rid, String loginId, String empName, String jobcodeId, String roleName, String jobDescription, Date planStart, Date planFinish, Date actualStart, Date actualFinish, String remark, String resStatus, String loginidStatus, String rst, Date rct, Date rut, db.essp.pms.Account account, Set laborResourceDetails) {
        this.rid = rid;
        this.loginId = loginId;
        this.empName = empName;
        this.jobcodeId = jobcodeId;
        this.roleName = roleName;
        this.jobDescription = jobDescription;
        this.planStart = planStart;
        this.planFinish = planFinish;
        this.actualStart = actualStart;
        this.actualFinish = actualFinish;
        this.remark = remark;
        this.resStatus = resStatus;
        this.loginidStatus = loginidStatus;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
        this.account = account;
        this.laborResourceDetails = laborResourceDetails;
    }

    /** default constructor */
    public LaborResource() {
    }

    /** minimal constructor */
    public LaborResource(Long rid, db.essp.pms.Account account, Set laborResourceDetails) {
        this.rid = rid;
        this.account = account;
        this.laborResourceDetails = laborResourceDetails;
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

    public String getEmpName() {
        return this.empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getJobcodeId() {
        return this.jobcodeId;
    }

    public void setJobcodeId(String jobcodeId) {
        this.jobcodeId = jobcodeId;
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

    public Double getPlanWorkTime() {
        return this.planWorkTime;
    }

    public void setPlanWorkTime(Double planWorkTime) {
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

    public Double getPersonWorkTime() {
        return this.personWorkTime;
    }

    public void setPersonWorkTime(Double personWorkTime) {
        this.personWorkTime = personWorkTime;
    }

    public Double getActualWorkTime() {
        return this.actualWorkTime;
    }

    public void setActualWorkTime(Double actualWorkTime) {
        this.actualWorkTime = actualWorkTime;
    }

    public Double getEtcWorkTime() {
        return this.etcWorkTime;
    }

    public void setEtcWorkTime(Double etcWorkTime) {
        this.etcWorkTime = etcWorkTime;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getResStatus() {
        return this.resStatus;
    }

    public void setResStatus(String resStatus) {
        this.resStatus = resStatus;
    }

    public String getLoginidStatus() {
        return this.loginidStatus;
    }

    public void setLoginidStatus(String loginidStatus) {
        this.loginidStatus = loginidStatus;
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

    public db.essp.pms.Account getAccount() {
        return this.account;
    }

    public void setAccount(db.essp.pms.Account account) {
        this.account = account;
    }

    public Set getLaborResourceDetails() {
        return this.laborResourceDetails;
    }

    public void setLaborResourceDetails(Set laborResourceDetails) {
        this.laborResourceDetails = laborResourceDetails;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("rid", getRid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof LaborResource) ) return false;
        LaborResource castOther = (LaborResource) other;
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
