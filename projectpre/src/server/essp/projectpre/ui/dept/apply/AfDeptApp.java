package server.essp.projectpre.ui.dept.apply;

import java.util.List;

import org.apache.struts.action.ActionForm;

public class AfDeptApp extends ActionForm {
    
    private String rid;
    private String applicantId;
    private String applicantName;
    private String applicationStatus;
    private String relPrjType;
    private String acntName;
    private String deptManager;
    private String acntFullName;
    private String TCSName;
    private String acntBrief;
    private String achieveBelong;
    private List BelongBdList;
    private String BDMName;
    private String leaderName;
    private String execSite;
    private String execUnitName;
    private String costAttachBd;
    private String bizSource;
    private String salesName;
    private String acntAttribute;
    private String acntId;
    private List acntIdList;
    private String remark;
    private String applicationDate;
    private String DMLoginId;
    private String BDLoginId;
    private String DMDomain;
    private String TCSLoginId;
    private String duplicate;
    private String status;
    private String parentDept;
    
    public String getParentDept() {
		return parentDept;
	}
	public void setParentDept(String parentDept) {
		this.parentDept = parentDept;
	}
	public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getBDLoginId() {
        return BDLoginId;
    }
    public void setBDLoginId(String loginId) {
        BDLoginId = loginId;
    }
    public String getTCSLoginId() {
        return TCSLoginId;
    }
    public void setTCSLoginId(String loginId) {
        TCSLoginId = loginId;
    }
    public String getApplicationDate() {
        return applicationDate;
    }
    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public List getAcntIdList() {
        return acntIdList;
    }
    public void setAcntIdList(List acntIdList) {
        this.acntIdList = acntIdList;
    }
    public String getAcntId() {
        return acntId;
    }
    public void setAcntId(String acntId) {
        this.acntId = acntId;
    }
    public String getAchieveBelong() {
        return achieveBelong;
    }
    public void setAchieveBelong(String achieveBelong) {
        this.achieveBelong = achieveBelong;
    }
    public String getAcntAttribute() {
        return acntAttribute;
    }
    public void setAcntAttribute(String acntAttribute) {
        this.acntAttribute = acntAttribute;
    }
    public String getAcntBrief() {
        return acntBrief;
    }
    public void setAcntBrief(String acntBrief) {
        this.acntBrief = acntBrief;
    }
    public String getAcntFullName() {
        return acntFullName;
    }
    public void setAcntFullName(String acntFullName) {
        this.acntFullName = acntFullName;
    }
    public String getAcntName() {
        return acntName;
    }
    public void setAcntName(String acntName) {
        this.acntName = acntName;
    }
    public String getApplicantId() {
        return applicantId;
    }
    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
    }
    public String getApplicantName() {
        return applicantName;
    }
    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }
    public String getApplicationStatus() {
        return applicationStatus;
    }
    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }
    public String getBDMName() {
        return BDMName;
    }
    public void setBDMName(String name) {
        BDMName = name;
    }
    public String getBizSource() {
        return bizSource;
    }
    public void setBizSource(String bizSource) {
        this.bizSource = bizSource;
    }
    public String getCostAttachBd() {
        return costAttachBd;
    }
    public void setCostAttachBd(String costAttachBd) {
        this.costAttachBd = costAttachBd;
    }
    public String getDeptManager() {
        return deptManager;
    }
    public void setDeptManager(String deptManager) {
        this.deptManager = deptManager;
    }
    public String getExecSite() {
        return execSite;
    }
    public void setExecSite(String execSite) {
        this.execSite = execSite;
    }
    public String getExecUnitName() {
        return execUnitName;
    }
    public void setExecUnitName(String execUnitName) {
        this.execUnitName = execUnitName;
    }
    public String getLeaderName() {
        return leaderName;
    }
    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }
    public String getRelPrjType() {
        return relPrjType;
    }
    public void setRelPrjType(String relPrjType) {
        this.relPrjType = relPrjType;
    }

    public String getSalesName() {
        return salesName;
    }
    public void setSalesName(String salesName) {
        this.salesName = salesName;
    }
    public String getTCSName() {
        return TCSName;
    }
    public void setTCSName(String name) {
        TCSName = name;
    }
    public List getBelongBdList() {
        return BelongBdList;
    }
    public void setBelongBdList(List belongBdList) {
        BelongBdList = belongBdList;
    }
    public void setRid(String rid) {
        this.rid = rid;
    }
    public String getRid() {
        return rid;
    }
    public String getDMLoginId() {
        return DMLoginId;
    }
    public void setDMLoginId(String loginId) {
        DMLoginId = loginId;
    }
    public String getDuplicate() {
        return duplicate;
    }
    public void setDuplicate(String duplicate) {
        this.duplicate = duplicate;
    }


    public String getDMDomain() {
        return DMDomain;
    }
    public void setDMDomain(String domain) {
        DMDomain = domain;
    }

}