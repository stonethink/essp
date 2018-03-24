package server.essp.projectpre.db;

import java.util.Date;


/**
 * PpAcntApp generated by MyEclipse - Hibernate Tools
 */

public class AcntApp  implements java.io.Serializable {


    // Fields    

     private Long rid;
     private String applicationType;
     private String applicantId;
     private String applicantName;
     private String domain;
     private String applicationStatus;
     private Date applicationDate;
     private String relPrjType;
     private String relParentId;
     private Long acntRid;
     private String acntId;
     private String acntName;
     private String acntFullName;
     private String customerId;
     private String achieveBelong;
     private String execSite;
     private String execUnitId;
     private String costAttachBd;
     private String bizSource;
     private String productName;
     private String acntAttribute;
     private String acntType;
     private String isAcnt;
     private String isEnable;
     private Date acntAnticipatedStart;
     private Date acntAnticipatedFinish;
     private Date acntPlannedStart;
     private Date acntPlannedFinish;
     private Date acntActualStart;
     private Date acntActualFinish;
     private Double estManmonth;
     private Double actualManmonth;
     private String acntOrganization;
     private String acntCurrency;
     private Long estSize;
     private String acntBrief;
     private String remark;
     private String rst;
     private Date rct;
     private Date rut;
     private String rcu;
     private String ruu;
     private String otherDesc;
     private String contractAcntId;
     private String contractAcntName;
     private String hasAttachment;
     private String primaveraAdapted;
     private String billingBasis;
     private String bizProperty;
     private String billType;
     private String bl;

    // Constructors

    public String getBl() {
		return bl;
	}

	public void setBl(String bl) {
		this.bl = bl;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getBizProperty() {
		return bizProperty;
	}

	public void setBizProperty(String bizProperty) {
		this.bizProperty = bizProperty;
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

	public String getHasAttachment() {
        return hasAttachment;
    }

    public void setHasAttachment(String hasAttachment) {
        this.hasAttachment = hasAttachment;
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

    /** default constructor */
    public AcntApp() {
    }

	/** minimal constructor */
    public AcntApp(Long rid) {
        this.rid = rid;
    }
    
    /** full constructor */
    public AcntApp(Long rid, String applicationType, String applicantId, String applicantName,String domain, String applicationStatus, Date applicationDate, String relPrjType, String relParentId, Long acntRid, String acntId, String acntName, String acntFullName, String customerId, String achieveBelong, String execSite, String execUnitId, String costAttachBd, String bizSource, String productName, String acntAttribute, String acntType, Date acntAnticipatedStart, Date acntAnticipatedFinish, Date acntPlannedStart, Date acntPlannedFinish, Date acntActualStart, Date acntActualFinish, Double estManmonth, Double actualManmonth, String acntOrganization, String acntCurrency, Long estSize, String acntBrief, String remark, String rst, Date rct, Date rut, String rcu, String ruu, String otherDesc, String contractAcntId, String contractAcntName,String isAcnt, String isEnable, String hasAttachment, String primaveraAdapted, String billingBasis, String bizPorperty, String billType, String bl) {
        this.rid = rid;
        this.applicationType = applicationType;
        this.applicantId = applicantId;
        this.applicantName = applicantName;
        this.domain = domain;
        this.applicationStatus = applicationStatus;
        this.applicationDate = applicationDate;
        this.relPrjType = relPrjType;
        this.relParentId = relParentId;
        this.acntRid = acntRid;
        this.acntId = acntId;
        this.acntName = acntName;
        this.acntFullName = acntFullName;
        this.customerId = customerId;
        this.achieveBelong = achieveBelong;
        this.execSite = execSite;
        this.execUnitId = execUnitId;
        this.costAttachBd = costAttachBd;
        this.bizSource = bizSource;
        this.productName = productName;
        this.acntAttribute = acntAttribute;
        this.acntType = acntType;
        this.acntAnticipatedStart = acntAnticipatedStart;
        this.acntAnticipatedFinish = acntAnticipatedFinish;
        this.acntPlannedStart = acntPlannedStart;
        this.acntPlannedFinish = acntPlannedFinish;
        this.acntActualStart = acntActualStart;
        this.acntActualFinish = acntActualFinish;
        this.estManmonth = estManmonth;
        this.actualManmonth = actualManmonth;
        this.acntOrganization = acntOrganization;
        this.acntCurrency = acntCurrency;
        this.estSize = estSize;
        this.acntBrief = acntBrief;
        this.remark = remark;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
        this.rcu = rcu;
        this.ruu = ruu;
        this.otherDesc = otherDesc;
        this.contractAcntId = contractAcntId;
        this.contractAcntName = contractAcntName;
        this.isAcnt = isAcnt;
        this.isEnable = isEnable;
        this.hasAttachment = hasAttachment;
        this.primaveraAdapted = primaveraAdapted;
        this.billingBasis = billingBasis;
        this.bizProperty = bizPorperty;
        this.billType = billType;
        this.bl = bl;
    }

   
    // Property accessors

    public Long getRid() {
        return this.rid;
    }
    
    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getApplicationType() {
        return this.applicationType;
    }
    
    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
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
        return this.applicationStatus;
    }
    
    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public Date getApplicationDate() {
        return this.applicationDate;
    }
    
    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getRelPrjType() {
        return this.relPrjType;
    }
    
    public void setRelPrjType(String relPrjType) {
        this.relPrjType = relPrjType;
    }

 

    public String getRelParentId() {
        return relParentId;
    }

    public void setRelParentId(String relParentId) {
        this.relParentId = relParentId;
    }

    public Long getAcntRid() {
        return this.acntRid;
    }
    
    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public String getAcntId() {
        return this.acntId;
    }
    
    public void setAcntId(String acntId) {
        this.acntId = acntId;
    }

    public String getAcntName() {
        return this.acntName;
    }
    
    public void setAcntName(String acntName) {
        this.acntName = acntName;
    }

    public String getAcntFullName() {
        return this.acntFullName;
    }
    
    public void setAcntFullName(String acntFullName) {
        this.acntFullName = acntFullName;
    }

    public String getCustomerId() {
        return this.customerId;
    }
    
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAchieveBelong() {
        return this.achieveBelong;
    }
    
    public void setAchieveBelong(String achieveBelong) {
        this.achieveBelong = achieveBelong;
    }

    public String getExecSite() {
        return this.execSite;
    }
    
    public void setExecSite(String execSite) {
        this.execSite = execSite;
    }

    public String getExecUnitId() {
        return this.execUnitId;
    }
    
    public void setExecUnitId(String execUnitId) {
        this.execUnitId = execUnitId;
    }

    public String getCostAttachBd() {
        return this.costAttachBd;
    }
    
    public void setCostAttachBd(String costAttachBd) {
        this.costAttachBd = costAttachBd;
    }

    public String getBizSource() {
        return this.bizSource;
    }
    
    public void setBizSource(String bizSource) {
        this.bizSource = bizSource;
    }

    public String getProductName() {
        return this.productName;
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getAcntAttribute() {
        return this.acntAttribute;
    }
    
    public void setAcntAttribute(String acntAttribute) {
        this.acntAttribute = acntAttribute;
    }

    public String getAcntType() {
        return this.acntType;
    }
    
    public void setAcntType(String acntType) {
        this.acntType = acntType;
    }

    public Date getAcntAnticipatedStart() {
        return this.acntAnticipatedStart;
    }
    
    public void setAcntAnticipatedStart(Date acntAnticipatedStart) {
        this.acntAnticipatedStart = acntAnticipatedStart;
    }

    public Date getAcntAnticipatedFinish() {
        return this.acntAnticipatedFinish;
    }
    
    public void setAcntAnticipatedFinish(Date acntAnticipatedFinish) {
        this.acntAnticipatedFinish = acntAnticipatedFinish;
    }

    public Date getAcntPlannedStart() {
        return this.acntPlannedStart;
    }
    
    public void setAcntPlannedStart(Date acntPlannedStart) {
        this.acntPlannedStart = acntPlannedStart;
    }

    public Date getAcntPlannedFinish() {
        return this.acntPlannedFinish;
    }
    
    public void setAcntPlannedFinish(Date acntPlannedFinish) {
        this.acntPlannedFinish = acntPlannedFinish;
    }

    public Date getAcntActualStart() {
        return this.acntActualStart;
    }
    
    public void setAcntActualStart(Date acntActualStart) {
        this.acntActualStart = acntActualStart;
    }

    public Date getAcntActualFinish() {
        return this.acntActualFinish;
    }
    
    public void setAcntActualFinish(Date acntActualFinish) {
        this.acntActualFinish = acntActualFinish;
    }

    public Double getEstManmonth() {
        return this.estManmonth;
    }
    
    public void setEstManmonth(Double estManmonth) {
        this.estManmonth = estManmonth;
    }

    public Double getActualManmonth() {
        return this.actualManmonth;
    }
    
    public void setActualManmonth(Double actualManmonth) {
        this.actualManmonth = actualManmonth;
    }

    public String getAcntOrganization() {
        return this.acntOrganization;
    }
    
    public void setAcntOrganization(String acntOrganization) {
        this.acntOrganization = acntOrganization;
    }

    public String getAcntCurrency() {
        return this.acntCurrency;
    }
    
    public void setAcntCurrency(String acntCurrency) {
        this.acntCurrency = acntCurrency;
    }

    public Long getEstSize() {
        return this.estSize;
    }
    
    public void setEstSize(Long estSize) {
        this.estSize = estSize;
    }

    public String getAcntBrief() {
        return this.acntBrief;
    }
    
    public void setAcntBrief(String acntBrief) {
        this.acntBrief = acntBrief;
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

    public String getRcu() {
        return this.rcu;
    }
    
    public void setRcu(String rcu) {
        this.rcu = rcu;
    }

    public String getRuu() {
        return this.ruu;
    }
    
    public void setRuu(String ruu) {
        this.ruu = ruu;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOtherDesc() {
        return otherDesc;
    }

    public void setOtherDesc(String otherDesc) {
        this.otherDesc = otherDesc;
    }

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getIsAcnt() {
		return isAcnt;
	}

	public void setIsAcnt(String isAcnt) {
		this.isAcnt = isAcnt;
	}

    public String getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(String isEnable) {
        this.isEnable = isEnable;
    }
   








}