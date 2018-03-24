package server.essp.projectpre.ui.project.apply;





import org.apache.struts.action.ActionForm;

public class AfProjectAcntApp extends ActionForm {
    
    private String rid;
    private String applicantId;
    private String applicantName;
    private String applicationStatus;
    private String relPrjType;
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
    private String comment;
    private String parentProject;
    private String acntId;
    private String projectType;
    private String productType;
    private String productAttribute;
    private String workItem;
    private String technicalDomain;
    private String originalLanguage;
    private String translationLanguage;
    private String developJobSystem;
    private String developDataBase;
    private String developTool;
    private String developNetWork;
    private String developProgramLanguage;
    private String developOthers;
    private String typeJobSystem;
    private String typeDataBase;
    private String typeTool;
    private String typeNetWork;
    private String typeProgramLanguage;
    private String typeOthers;
    private String hardReq;
    private String softReq;
    private String customerId;
    private String contract;
    private String contractTel;
    private String contractEmail;
    private String executive;
    private String executiveTel;
    private String executiveEmail;
    private String financial;
    private String financialTel;
    private String financialEmail;
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
    private String custServiceManagerName;
    private String engageManagerName;
    private String custServiceManagerId;
    private String custServiceManagerDomain;
    private String engageManagerId;
    private String engageManagerDomain;
    private String otherDesc;
    private String contractAcntId;
    private String contractAcntName;
    private String createSubmit;
    private String primaveraAdapted;
    private String billingBasis;
    private String bizProperty;
    private String billType;
    private String bl;
    
    
  
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
	public String getCreateSubmit() {
        return createSubmit;
    }
    public void setCreateSubmit(String createSubmit) {
        this.createSubmit = createSubmit;
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
    public String getContract() {
        return contract;
    }
    public void setContract(String contract) {
        this.contract = contract;
    }
    public String getContractEmail() {
        return contractEmail;
    }
    public void setContractEmail(String contractEmail) {
        this.contractEmail = contractEmail;
    }
    public String getContractTel() {
        return contractTel;
    }
    public void setContractTel(String contractTel) {
        this.contractTel = contractTel;
    }
  
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getExecutive() {
        return executive;
    }
    public void setExecutive(String executive) {
        this.executive = executive;
    }
    public String getExecutiveEmail() {
        return executiveEmail;
    }
    public void setExecutiveEmail(String executiveEmail) {
        this.executiveEmail = executiveEmail;
    }
    public String getExecutiveTel() {
        return executiveTel;
    }
    public void setExecutiveTel(String executiveTel) {
        this.executiveTel = executiveTel;
    }
    public String getFinancial() {
        return financial;
    }
    public void setFinancial(String financial) {
        this.financial = financial;
    }
    public String getFinancialEmail() {
        return financialEmail;
    }
    public void setFinancialEmail(String financialEmail) {
        this.financialEmail = financialEmail;
    }
    public String getFinancialTel() {
        return financialTel;
    }
    public void setFinancialTel(String financialTel) {
        this.financialTel = financialTel;
    }
    public String getHardReq() {
        return hardReq;
    }
    public void setHardReq(String hardReq) {
        this.hardReq = hardReq;
    }
    public String getSoftReq() {
        return softReq;
    }
    public void setSoftReq(String softReq) {
        this.softReq = softReq;
    }
    public String getDevelopDataBase() {
        return developDataBase;
    }
    public void setDevelopDataBase(String developDataBase) {
        this.developDataBase = developDataBase;
    }
    public String getDevelopJobSystem() {
        return developJobSystem;
    }
    public void setDevelopJobSystem(String developJobSystem) {
        this.developJobSystem = developJobSystem;
    }
    public String getDevelopNetWork() {
        return developNetWork;
    }
    public void setDevelopNetWork(String developNetWork) {
        this.developNetWork = developNetWork;
    }
    public String getDevelopOthers() {
        return developOthers;
    }
    public void setDevelopOthers(String developOthers) {
        this.developOthers = developOthers;
    }
    public String getDevelopProgramLanguage() {
        return developProgramLanguage;
    }
    public void setDevelopProgramLanguage(String developProgramLanguage) {
        this.developProgramLanguage = developProgramLanguage;
    }
    public String getDevelopTool() {
        return developTool;
    }
    public void setDevelopTool(String developTool) {
        this.developTool = developTool;
    }
    public String getTypeDataBase() {
        return typeDataBase;
    }
    public void setTypeDataBase(String typeDataBase) {
        this.typeDataBase = typeDataBase;
    }
    public String getTypeJobSystem() {
        return typeJobSystem;
    }
    public void setTypeJobSystem(String typeJobSystem) {
        this.typeJobSystem = typeJobSystem;
    }
    public String getTypeNetWork() {
        return typeNetWork;
    }
    public void setTypeNetWork(String typeNetWork) {
        this.typeNetWork = typeNetWork;
    }
    public String getTypeOthers() {
        return typeOthers;
    }
    public void setTypeOthers(String typeOthers) {
        this.typeOthers = typeOthers;
    }
    public String getTypeProgramLanguage() {
        return typeProgramLanguage;
    }
    public void setTypeProgramLanguage(String typeProgramLanguage) {
        this.typeProgramLanguage = typeProgramLanguage;
    }
    public String getTypeTool() {
        return typeTool;
    }
    public void setTypeTool(String typeTool) {
        this.typeTool = typeTool;
    }
    public String getOriginalLanguage() {
        return originalLanguage;
    }
    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }
    public String getProductAttribute() {
        return productAttribute;
    }
    public void setProductAttribute(String productAttribute) {
        this.productAttribute = productAttribute;
    }
    public String getProductType() {
        return productType;
    }
    public void setProductType(String productType) {
        this.productType = productType;
    }
    public String getTechnicalDomain() {
        return technicalDomain;
    }
    public void setTechnicalDomain(String technicalDomain) {
        this.technicalDomain = technicalDomain;
    }
    public String getTranslationLanguage() {
        return translationLanguage;
    }
    public void setTranslationLanguage(String translationLanguage) {
        this.translationLanguage = translationLanguage;
    }
    public String getWorkItem() {
        return workItem;
    }
    public void setWorkItem(String workItem) {
        this.workItem = workItem;
    }
    public String getProjectType() {
        return projectType;
    }
    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }
    public String getApplicationStatus() {
        return applicationStatus;
    }
    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
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
   
 
  
 
    public String getAcntActualFinish() {
        return acntActualFinish;
    }
    public void setAcntActualFinish(String acntActualFinish) {
        this.acntActualFinish = acntActualFinish;
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
 
    public String getRid() {
        return rid;
    }
    public void setRid(String rid) {
        this.rid = rid;
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
  
   
    public String getParentProject() {
        return parentProject;
    }
    public void setParentProject(String parentProject) {
        this.parentProject = parentProject;
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
    public String getAcntActualStart() {
        return acntActualStart;
    }
    public void setAcntActualStart(String acntActualStart) {
        this.acntActualStart = acntActualStart;
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
    public String getBDId() {
        return BDId;
    }
    public void setBDId(String id) {
        BDId = id;
    }
    public String getCustServiceManagerId() {
        return custServiceManagerId;
    }
    public void setCustServiceManagerId(String custServiceManagerId) {
        this.custServiceManagerId = custServiceManagerId;
    }
    public String getCustServiceManagerName() {
        return custServiceManagerName;
    }
    public void setCustServiceManagerName(String custServiceManagerName) {
        this.custServiceManagerName = custServiceManagerName;
    }
    public String getEngageManagerId() {
        return engageManagerId;
    }
    public void setEngageManagerId(String engageManagerId) {
        this.engageManagerId = engageManagerId;
    }
    public String getEngageManagerName() {
        return engageManagerName;
    }
    public void setEngageManagerName(String engageManagerName) {
        this.engageManagerName = engageManagerName;
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
    public String getCustServiceManagerDomain() {
        return custServiceManagerDomain;
    }
    public void setCustServiceManagerDomain(String custServiceManagerDomain) {
        this.custServiceManagerDomain = custServiceManagerDomain;
    }
    public String getEngageManagerDomain() {
        return engageManagerDomain;
    }
    public void setEngageManagerDomain(String engageManagerDomain) {
        this.engageManagerDomain = engageManagerDomain;
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
  
 
}
