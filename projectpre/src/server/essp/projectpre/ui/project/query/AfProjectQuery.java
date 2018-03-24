package server.essp.projectpre.ui.project.query;



import org.apache.struts.action.ActionForm;

public class AfProjectQuery extends ActionForm {
	
	private String acntId;//�������a
    private boolean includeClosedProject;//�Ƿ�����ѽY��֮�������a
    private String relParentId;//���������a
    private String applicationDateBegin;//�������a��Ո����
    private String applicationDateEnd;//�������a��Ո����
    private String acntPlannedStart;//����Ԥ����ʼ����
    private String acntPlannedFinish;//����Ԥ�ƽ�������
    private String applicant;//��Ո��
    private String applicantId;//������LoginId
    private String achieveBelong;//�I���w��
    private String bdManager;//BD����
    private String BDMId;//BD����LoginId
    private String pmName;//PM
    private String PMId;//PM��LoginId
    private String execUnitId;//���І�λ
    private String execSite;//���Г��c
    private String costAttachBd;//�ɱ��w�ن�λ
    private String bizSource;//�I�Ձ�Դ
    private String customerId;//�͑���̖
    private String acntAttribute;//��������
    private String acntType;//�������
    private String productType;//�aƷ���
    private String productProperty;//�aƷ����
    private String workItem;//�����Ŀ
    private String technicalArea;//���g�I��
    private String supportLanguge;//֧Ԯ�Z��
    private String primaveraAdapted;//�Ƿ�ʹ��P6
    private String billingBasis;//�Ƿ���͑����M
    private String bizProperty;//�I�Ռ���
    private String billType;//���M���
    private String relPrjType;//�����Pϵ
    
   
    
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
