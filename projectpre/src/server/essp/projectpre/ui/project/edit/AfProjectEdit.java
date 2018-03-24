/*
 * Created on 2007-4-23
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.projectpre.ui.project.edit;


import java.util.List;

import org.apache.struts.action.ActionForm;

public class AfProjectEdit extends ActionForm {

    private String applicantName;
    private String acntStatus;
    private String acntName;
    private String PMName;
    private String acntFullName;
    private String TCSName;
    private String acntBrief;
    private String achieveBelong;
    private String BDMName;
    private String leaderName;
    private String execSite;
    private String execUnitName;
    private String costAttachBd;
    private String bizSource;
    private String salesName;
    private String acntAttribute;
    private String productName;
    private String acntPlannedStart;
    private String acntPlannedFinish;
    private Double estManmonth;
    private Long estSize;
    private String acntActualStart;
    private String acntActualFinish;
    private Double actualManmonth;
    private String parentProject;
    private String acntId;
    private String PMId;
    private String PMDomain;
    private String APId;
    private String APDomain;
    private String TCSId;
    private String TCSDomain;
    private String BDId;
    private String BDDomain;
    private String leaderId;
    private String leaderDomain;
    private String salesId;
    private String salesDomain;
    private String otherDesc;
    private List achieveBelongList;
    private List execSiteList;
    private List execUnitNameList;
    private List acntAttributeList;
    private String primaveraAdapted;
    private String billingBasis;
    private String bizProperty;
    private List bizPropertyList;
    private String billType;
    private List billTypeList;
    
    public String getChkBillingBasis() {
    	if("1".equals(getBillingBasis())) {
    		return "checked";
    	} 
		return "";
	}
    public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public List getBillTypeList() {
		return billTypeList;
	}
	public void setBillTypeList(List billTypeList) {
		this.billTypeList = billTypeList;
	}
	public String getBizProperty() {
		return bizProperty;
	}
	public void setBizProperty(String bizProperty) {
		this.bizProperty = bizProperty;
	}
	public List getBizPropertyList() {
		return bizPropertyList;
	}
	public void setBizPropertyList(List bizPropertyList) {
		this.bizPropertyList = bizPropertyList;
	}
	public String getBillingBasis() {
    	if(billingBasis == null || "".equals(billingBasis)) {
    		return "0";
    	}
		return billingBasis;
	}
	public void setBillingBasis(String billingBasis) {
		this.billingBasis = billingBasis;
	}
	public String getPrimaveraAdapted() {
		if(primaveraAdapted == null || "".equals(primaveraAdapted)) {
			return "0";
		}
		return primaveraAdapted;
	}
	public void setPrimaveraAdapted(String primaveraAdapted) {
		this.primaveraAdapted = primaveraAdapted;
	}
	public List getAchieveBelongList() {
        return achieveBelongList;
    }
    public void setAchieveBelongList(List achieveBelongList) {
        this.achieveBelongList = achieveBelongList;
    }
    public List getAcntAttributeList() {
        return acntAttributeList;
    }
    public void setAcntAttributeList(List acntAttributeList) {
        this.acntAttributeList = acntAttributeList;
    }
    public List getExecSiteList() {
        return execSiteList;
    }
    public void setExecSiteList(List execSiteList) {
        this.execSiteList = execSiteList;
    }
    public List getExecUnitNameList() {
        return execUnitNameList;
    }
    public void setExecUnitNameList(List execUnitNameList) {
        this.execUnitNameList = execUnitNameList;
    }
    public String getAchieveBelong() {
        return achieveBelong;
    }
    public void setAchieveBelong(String achieveBelong) {
        this.achieveBelong = achieveBelong;
    }
    public String getAcntActualFinish() {
        return acntActualFinish;
    }
    public void setAcntActualFinish(String acntActualFinish) {
        this.acntActualFinish = acntActualFinish;
    }
    public String getAcntActualStart() {
        return acntActualStart;
    }
    public void setAcntActualStart(String acntActualStart) {
        this.acntActualStart = acntActualStart;
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
    public String getAcntId() {
        return acntId;
    }
    public void setAcntId(String acntId) {
        this.acntId = acntId;
    }
    public String getAcntName() {
        return acntName;
    }
    public void setAcntName(String acntName) {
        this.acntName = acntName;
    }
    public String getAcntPlannedFinish() {
        return acntPlannedFinish;
    }
    public void setAcntPlannedFinish(String acntPlannedFinish) {
        this.acntPlannedFinish = acntPlannedFinish;
    }
    public String getAcntPlannedStart() {
        return acntPlannedStart;
    }
    public void setAcntPlannedStart(String acntPlannedStart) {
        this.acntPlannedStart = acntPlannedStart;
    }
    
    public String getApplicantName() {
        return applicantName;
    }
    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }
    public String getAcntStatus() {
        return acntStatus;
    }
    public void setAcntStatus(String acntStatus) {
        this.acntStatus = acntStatus;
    }
    public String getBDDomain() {
        return BDDomain;
    }
    public void setBDDomain(String domain) {
        BDDomain = domain;
    }
    public String getBDId() {
        return BDId;
    }
    public void setBDId(String id) {
        BDId = id;
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
    public String getLeaderDomain() {
        return leaderDomain;
    }
    public void setLeaderDomain(String leaderDomain) {
        this.leaderDomain = leaderDomain;
    }
    public String getLeaderId() {
        return leaderId;
    }
    public void setLeaderId(String leaderId) {
        this.leaderId = leaderId;
    }
    public String getLeaderName() {
        return leaderName;
    }
    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }
    public String getOtherDesc() {
        return otherDesc;
    }
    public void setOtherDesc(String otherDesc) {
        this.otherDesc = otherDesc;
    }
    public String getParentProject() {
        return parentProject;
    }
    public void setParentProject(String parentProject) {
        this.parentProject = parentProject;
    }
    public String getPMDomain() {
        return PMDomain;
    }
    public void setPMDomain(String domain) {
        PMDomain = domain;
    }
    public String getPMId() {
        return PMId;
    }
    public void setPMId(String id) {
        PMId = id;
    }
    public String getPMName() {
        return PMName;
    }
    public void setPMName(String name) {
        PMName = name;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getSalesDomain() {
        return salesDomain;
    }
    public void setSalesDomain(String salesDomain) {
        this.salesDomain = salesDomain;
    }
    public String getSalesId() {
        return salesId;
    }
    public void setSalesId(String salesId) {
        this.salesId = salesId;
    }
    public String getSalesName() {
        return salesName;
    }
    public void setSalesName(String salesName) {
        this.salesName = salesName;
    }
    public String getTCSDomain() {
        return TCSDomain;
    }
    public void setTCSDomain(String domain) {
        TCSDomain = domain;
    }
    public String getTCSId() {
        return TCSId;
    }
    public void setTCSId(String id) {
        TCSId = id;
    }
    public String getTCSName() {
        return TCSName;
    }
    public void setTCSName(String name) {
        TCSName = name;
    }
    public Double getActualManmonth() {
        return actualManmonth;
    }
    public void setActualManmonth(Double actualManmonth) {
        this.actualManmonth = actualManmonth;
    }
    public Double getEstManmonth() {
        return estManmonth;
    }
    public void setEstManmonth(Double estManmonth) {
        this.estManmonth = estManmonth;
    }
    public Long getEstSize() {
        return estSize;
    }
    public void setEstSize(Long estSize) {
        this.estSize = estSize;
    }
	public String getAPDomain() {
		return APDomain;
	}
	public void setAPDomain(String domain) {
		APDomain = domain;
	}
	public String getAPId() {
		return APId;
	}
	public void setAPId(String id) {
		APId = id;
	}
}
