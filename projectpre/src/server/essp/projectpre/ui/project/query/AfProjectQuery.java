package server.essp.projectpre.ui.project.query;



import org.apache.struts.action.ActionForm;

public class AfProjectQuery extends ActionForm {
	
	private String acntId;//專案代碼
    private boolean includeClosedProject;//是否包含已結案之專案代碼
    private String relParentId;//父專案代碼
    private String applicationDateBegin;//專案代碼申請日期
    private String applicationDateEnd;//專案代碼申請日期
    private String acntPlannedStart;//專案预计起始日期
    private String acntPlannedFinish;//專案预计结束日期
    private String applicant;//申請人
    private String applicantId;//申请人LoginId
    private String achieveBelong;//業績歸屬
    private String bdManager;//BD主管
    private String BDMId;//BD主管LoginId
    private String pmName;//PM
    private String PMId;//PM的LoginId
    private String execUnitId;//執行單位
    private String execSite;//執行據點
    private String costAttachBd;//成本歸屬單位
    private String bizSource;//業務來源
    private String customerId;//客戶代號
    private String acntAttribute;//專案屬性
    private String acntType;//專案類型
    private String productType;//產品類型
    private String productProperty;//產品屬性
    private String workItem;//工作項目
    private String technicalArea;//技術領域
    private String supportLanguge;//支援語言
    private String primaveraAdapted;//是否使用P6
    private String billingBasis;//是否向客戶收費
    private String bizProperty;//業務屬性
    private String billType;//收費類型
    private String relPrjType;//專案關系
    
   
    
	public String getRelPrjType() {
		return relPrjType;
	}
	public void setRelPrjType(String relPrjType) {
		this.relPrjType = relPrjType;
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
	public String getAcntId() {
		return acntId;
	}
	public void setAcntId(String acntId) {
		this.acntId = acntId;
	}
	public String getAcntType() {
		return acntType;
	}
	public void setAcntType(String acntType) {
		this.acntType = acntType;
	}
	public String getApplicant() {
		return applicant;
	}
	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}

    public String getApplicationDateBegin() {
        return applicationDateBegin;
    }
    public void setApplicationDateBegin(String applicationDateBegin) {
        this.applicationDateBegin = applicationDateBegin;
    }
    public String getApplicationDateEnd() {
        return applicationDateEnd;
    }
    public void setApplicationDateEnd(String applicationDateEnd) {
        this.applicationDateEnd = applicationDateEnd;
    }
    public String getBdManager() {
		return bdManager;
	}
	public void setBdManager(String bdManager) {
		this.bdManager = bdManager;
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
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getExecSite() {
		return execSite;
	}
	public void setExecSite(String execSite) {
		this.execSite = execSite;
	}
	public String getExecUnitId() {
		return execUnitId;
	}
	public void setExecUnitId(String execUnitId) {
		this.execUnitId = execUnitId;
	}
	public boolean getIncludeClosedProject() {
		return includeClosedProject;
	}
	public void setIncludeClosedProject(boolean includeClosedProject) {
		this.includeClosedProject = includeClosedProject;
	}
	public String getProductProperty() {
		return productProperty;
	}
	public void setProductProperty(String productProperty) {
		this.productProperty = productProperty;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	
	public String getRelParentId() {
        return relParentId;
    }
    public void setRelParentId(String relParentId) {
        this.relParentId = relParentId;
    }
    public String getSupportLanguge() {
		return supportLanguge;
	}
	public void setSupportLanguge(String supportLanguge) {
		this.supportLanguge = supportLanguge;
	}
	public String getTechnicalArea() {
		return technicalArea;
	}
	public void setTechnicalArea(String technicalArea) {
		this.technicalArea = technicalArea;
	}
	public String getWorkItem() {
		return workItem;
	}
	public void setWorkItem(String workItem) {
		this.workItem = workItem;
	}
    public String getApplicantId() {
        return applicantId;
    }
    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
    }
    public String getBDMId() {
        return BDMId;
    }
    public void setBDMId(String id) {
        BDMId = id;
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
	public String getPMId() {
		return PMId;
	}
	public void setPMId(String id) {
		PMId = id;
	}
	public String getPmName() {
		return pmName;
	}
	public void setPmName(String pmName) {
		this.pmName = pmName;
	}
	public String getPrimaveraAdapted() {
		return primaveraAdapted;
	}
	public void setPrimaveraAdapted(String primaveraAdapted) {
		this.primaveraAdapted = primaveraAdapted;
	}
	public String getBillingBasis() {
		return billingBasis;
	}
	public void setBillingBasis(String billingBasis) {
		this.billingBasis = billingBasis;
	}
	public String getBizProperty() {
		return bizProperty;
	}
	public void setBizProperty(String bizProperty) {
		this.bizProperty = bizProperty;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	

    
	
  
}
