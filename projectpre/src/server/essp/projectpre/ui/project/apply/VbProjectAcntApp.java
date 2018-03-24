package server.essp.projectpre.ui.project.apply;

import java.util.List;

public class VbProjectAcntApp {
    
    private String rid;
    private String applicantName;
    private String applicationStatus;
    private String relPrjType;
    private String acntName;
    private String PMName;
    private String acntFullName;
    private String TCSName;
    private String acntBrief;
    private String achieveBelong;
    private List achieveBelongList;
    private String BDMName;
    private String leaderName;
    private String execSite;
    private List execSiteList;
    private String execUnitName;
    private List execUnitNameList;
    private String costAttachBd;
    private String bizSource;
    private String salesName;
    private String acntAttribute;
    private List acntAttributeList;
    private String productName;
    private String acntPlannedStart;
    private String acntPlannedFinish;
    private String estManmonth;
    private String estSize;
    private String acntActualStart;
    private String acntActualFinish;
    private String actualManmonth;
    private String comment;
    private String parentProject;
    private String acntId;
    private List acntIdList;
    private String PMId;
    private String PMDomain;
    private String TCSId;
    private String TCSDomain;
    private String BDId;
    private String BDDomain;
    private String leaderId;
    private String leaderDomain;
    private String salesId;
    private String salesDomain;
    private String otherDesc;
    private String contractAcntId;
    private String contractAcntName;
    private String primaveraAdapted;
    private String billingBasis;
    private String bizProperty;
    private List bizPropertyList;
    private String billType;
    private List billTypeList;
    private List blList;
    private String bl;
    
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
		return billingBasis;
	}
	public void setBillingBasis(String billingBasis) {
		this.billingBasis = billingBasis;
	}
	public String getPrimaveraAdapted() {
		return primaveraAdapted;
	}
	public void setPrimaveraAdapted(String primaveraAdapted) {
		this.primaveraAdapted = primaveraAdapted;
	}
	public String getContractAcntId() {
        return contractAcntId;
    }
    public void setContractAcntId(String contractAcntId) {
        this.contractAcntId = contractAcntId;
    }
    public String getContractAcntName() {
        return contractAcntName;
    }
    public void setContractAcntName(String contractAcntName) {
        this.contractAcntName = contractAcntName;
    }
    public String getOtherDesc() {
        return otherDesc;
    }
    public void setOtherDesc(String otherDesc) {
        this.otherDesc = otherDesc;
    }
    public String getAcntId() {
        return acntId;
    }
    public void setAcntId(String acntId) {
        this.acntId = acntId;
    }
   
 
    public String getParentProject() {
        return parentProject;
    }
    public void setParentProject(String parentProject) {
        this.parentProject = parentProject;
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
    public String getActualManmonth() {
        return actualManmonth;
    }
    public void setActualManmonth(String actualManmonth) {
        this.actualManmonth = actualManmonth;
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
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getCostAttachBd() {
        return costAttachBd;
    }
    public void setCostAttachBd(String costAttachBd) {
        this.costAttachBd = costAttachBd;
    }
    public String getEstManmonth() {
        return estManmonth;
    }
    public void setEstManmonth(String estManmonth) {
        this.estManmonth = estManmonth;
    }
    public String getEstSize() {
        return estSize;
    }
    public void setEstSize(String estSize) {
        this.estSize = estSize;
    }
    public String getExecSite() {
        return execSite;
    }
    public void setExecSite(String execSite) {
        this.execSite = execSite;
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
    public String getRelPrjType() {
        return relPrjType;
    }
    public void setRelPrjType(String relPrjType) {
        this.relPrjType = relPrjType;
    }
    public String getRid() {
        return rid;
    }
    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getLeaderName() {
        return leaderName;
    }
    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
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
    public List getAchieveBelongList() {
        return achieveBelongList;
    }
    public void setAchieveBelongList(List achieveBelongList) {
        this.achieveBelongList = achieveBelongList;
    }
    public List getExecSiteList() {
        return execSiteList;
    }
    public void setExecSiteList(List execSiteList) {
        this.execSiteList = execSiteList;
    }
 
    public String getExecUnitName() {
        return execUnitName;
    }
    public void setExecUnitName(String execUnitName) {
        this.execUnitName = execUnitName;
    }
    public List getExecUnitNameList() {
        return execUnitNameList;
    }
    public void setExecUnitNameList(List execUnitNameList) {
        this.execUnitNameList = execUnitNameList;
    }
    public List getAcntIdList() {
        return acntIdList;
    }
    public void setAcntIdList(List acntIdList) {
        this.acntIdList = acntIdList;
    }
    public String getBDId() {
        return BDId;
    }
    public void setBDId(String id) {
        BDId = id;
    }
    public String getLeaderId() {
        return leaderId;
    }
    public void setLeaderId(String leaderId) {
        this.leaderId = leaderId;
    }
    public String getPMId() {
        return PMId;
    }
    public void setPMId(String id) {
        PMId = id;
    }
    public String getSalesId() {
        return salesId;
    }
    public void setSalesId(String salesId) {
        this.salesId = salesId;
    }
    public String getTCSId() {
        return TCSId;
    }
    public void setTCSId(String id) {
        TCSId = id;
    }
    public String getBDDomain() {
        return BDDomain;
    }
    public void setBDDomain(String domain) {
        BDDomain = domain;
    }
    public String getLeaderDomain() {
        return leaderDomain;
    }
    public void setLeaderDomain(String leaderDomain) {
        this.leaderDomain = leaderDomain;
    }
    public String getPMDomain() {
        return PMDomain;
    }
    public void setPMDomain(String domain) {
        PMDomain = domain;
    }
    public String getSalesDomain() {
        return salesDomain;
    }
    public void setSalesDomain(String salesDomain) {
        this.salesDomain = salesDomain;
    }
    public String getTCSDomain() {
        return TCSDomain;
    }
    public void setTCSDomain(String domain) {
        TCSDomain = domain;
    }
    public List getAcntAttributeList() {
        return acntAttributeList;
    }
    public void setAcntAttributeList(List acntAttributeList) {
        this.acntAttributeList = acntAttributeList;
    }

	public List getBlList() {
		return blList;
	}

	public void setBlList(List blList) {
		this.blList = blList;
	}

	public String getBl() {
		return bl;
	}

	public void setBl(String bl) {
		this.bl = bl;
	}
  
   
    

}
