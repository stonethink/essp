package server.essp.projectpre.ui.project.query;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class AfSearchCondition extends ActionForm {
    private String CHECK = "checked";
    
	private String showMainData;//��Ҫ����
	private String showApplicant;//�Ƿ���ʾ��Ո����λ
	private String showAchieveBelong;//�Ƿ���ʾ�I���w����λ
	private String showLeader;//�Ƿ���ʾ�I�ս�����λ
	private String showDivisionManager;//�Ƿ���ʾBD������λ
	private String showNickName;//�Ƿ���ʾ�������Q��λ
	private String showProjectManager;//�Ƿ���ʾ����������λ
	private String showTCSigner;//�Ƿ���ʾ���r��������λ
	private String showProjName;//�Ƿ���ʾ����ȫ����λ
	private String showProductName;//�Ƿ���ʾ�aƷ���Q��λ
	private String showProjectDesc;//�Ƿ���ʾ�������ݺ�����λ
	private String showProjectExecUnit;//�Ƿ���ʾ���І�λ��λ
	private String showProjectExecuteSite;//�Ƿ���ʾ���Г��c��λ
	private String showCostBelong;//�Ƿ���ʾ�ɱ��w�ن�λ��λ
	private String showSales;//�Ƿ���ʾ�I�մ�����λ
	private String showCustomerServiceManager;//�Ƿ���ʾ���տ��O��λ
	private String showEngageManager;//�Ƿ���ʾ�N��֧Ԯ������λ
	private String showBizSource;//�Ƿ���ʾ�I�Ձ�Դ��λ
	private String showParentProject;//�Ƿ���ʾ��������λ
	private String showContractAcnt;//�Ƿ���ʾǩԼ������λ
    private String showCustomerInfo;//�Ƿ���ʾ�ͻ���Ϣ��λ
    private String showAcntId;//�Ƿ���ʾר��������λ
    private String showPrimaveraAdapted;//�Ƿ��@ʾʹ��PrimaveraAdapted��λ
    private String showBillingBasis;//�Ƿ��@ʾ��͑����M��λ
    private String showRelPrjType;//�Ƿ��@ʾ�������
    
    private String showTechData;//��������
    private String showProjectType;//�Ƿ���ʾר��������λ
    private String showProductType;//�Ƿ���ʾ��Ʒ������λ
    private String showProductAttribute;//�Ƿ���ʾ��Ʒ������λ
    private String showWorkItem;//�Ƿ���ʾ������Ŀ��λ
    private String showTechnicalDomain;//�Ƿ���ʾ����������λ
    private String showSupportLanguage;//�Ƿ���ʾ֧��������λ
    private String showDevelopEnvironment;//�Ƿ���ʾ����������λ
    private String showTranslatePublishType;//�Ƿ���ʾ����/�Ű�/������λ
    private String showHardReq;//�Ƿ���ʾӲ��������λ
    private String showSoftReq;//�Ƿ���ʾ����������λ
    
    private String showProjectData;//ר������
    private String showProjectScheduleAnticipate;//�Ƿ���ʾר��Ԥ�ƽ�����λ
    private String showProjectScheduleFact;//�Ƿ���ʾר��ʵ�ʽ�����λ
    private String showAnticipateWorkQuantity;//�Ƿ���ʾԤ�ƹ�������λ
    private String showOthers;//�Ƿ���ʾ����������λ
    private String showManMonth;//�Ƿ���ʾԤ��������λ
    private Map conditionMap = new HashMap();//���ڼ�¼excel�ļ�����λ�Ƿ���ʾ��Map
    
    //�ж��Ƿ�ѡ��Ҫ��ʾ����λ
    public boolean isSelectSomething() {
        conditionMap.put("hasAttachment", CHECK);
        return getShowAchieveBelong()!=null
               ||
               getShowApplicant()!=null
               ||
               getShowLeader()!=null
               ||
               getShowDivisionManager()!=null
               ||
               getShowNickName()!=null
               ||
               getShowProjectManager()!=null
               ||
               getShowTCSigner()!=null
               ||
               getShowProjName()!=null
               ||
               getShowProductName()!=null
               ||
               getShowRelPrjType()!=null
               ||
               getShowProjectDesc()!=null
               ||
               getShowProjectExecUnit()!=null
               ||
               getShowProjectExecuteSite()!=null
               ||
               getShowCostBelong()!=null
               ||
               getShowSales()!=null
               ||
               getShowCustomerServiceManager()!=null
               ||
               getShowEngageManager()!=null
               ||
               getShowBizSource()!=null
               ||
               getShowParentProject()!=null
               ||
               getShowContractAcnt()!=null  
               ||
               getShowCustomerInfo()!=null
               ||
               getShowAcntId()!=null
               ||
               getShowPrimaveraAdapted()!=null
               ||
               getShowBillingBasis()!=null
               ||
               getShowProjectType()!=null  
               ||
               getShowProductType()!=null
               ||
               getShowProductAttribute()!=null
               ||
               getShowWorkItem()!=null
               ||
               getShowTechnicalDomain()!=null
               ||
               getShowSupportLanguage()!=null
               ||
               getShowDevelopEnvironment()!=null
               ||
               getShowTranslatePublishType()!=null
               ||
               getShowHardReq()!=null
               ||
               getShowSoftReq()!=null
               ||
               getShowProjectScheduleAnticipate()!=null
               ||
               getShowProjectScheduleFact()!=null
               ||
               getShowAnticipateWorkQuantity()!=null
               ||
               getShowOthers()!=null
               ||
               getShowManMonth()!=null;
    }
    //����Ĭ�ϵ���ʾ����λ
    public void createDefaultSelect(){
        setShowAcntId("checked");
        setShowApplicant("checked");
        setShowAchieveBelong("checked");
        setShowNickName("checked");
        setShowProjectManager("checked");
        setShowLeader("checked");
        setShowDivisionManager("checked");
        setShowTCSigner("checked");
        setShowProjName("checked");
        setShowProductName("checked");
        setShowProjectDesc("checked");
        setShowProjectExecUnit("checked");
        setShowProjectExecuteSite("checked");
        setShowCostBelong("checked");
        setShowSales("checked");
        setShowCustomerServiceManager("checked");
        setShowEngageManager("checked");
        setShowBizSource("checked");
        setShowParentProject("checked");
        setShowContractAcnt("checked");
        setShowCustomerInfo("checked");
        setShowPrimaveraAdapted("checked");
        setShowBillingBasis("checked");
        setShowRelPrjType("checked");
       
        setShowProjectType("checked");
        setShowProductType("checked");
        setShowProductAttribute("checked");
        setShowWorkItem("checked");
        setShowTechnicalDomain("checked");
        setShowSupportLanguage("checked");
        setShowDevelopEnvironment("checked");
        setShowTranslatePublishType("checked");
        setShowHardReq("checked");
        setShowSoftReq("checked");
        
        setShowProjectScheduleAnticipate("checked");
        setShowProjectScheduleFact("checked");
        setShowAnticipateWorkQuantity("checked");
        setShowOthers("checked");
        setShowManMonth("checked");
        conditionMap.put("hasAttachment", CHECK);
    }
	
	
	
	
    public String getShowAnticipateWorkQuantity() {
        return showAnticipateWorkQuantity;
    }
    public void setShowAnticipateWorkQuantity(String showAnticipateWorkQuantity) {
            this.conditionMap.put("estSize", CHECK);
        this.showAnticipateWorkQuantity = showAnticipateWorkQuantity;
    }
    public String getShowDevelopEnvironment() {
        return showDevelopEnvironment;
    }
    public void setShowDevelopEnvironment(String showDevelopEnvironment) {
        this.conditionMap.put("developEnvironmentToolTip", CHECK);
        this.showDevelopEnvironment = showDevelopEnvironment;
    }
    public String getShowHardReq() {
        return showHardReq;
    }
    public void setShowHardReq(String showHardReq) {
        this.conditionMap.put("hardreq", CHECK);
        this.showHardReq = showHardReq;
    }
    public String getShowOthers() {
        return showOthers;
    }
    public void setShowOthers(String showOthers) {
        this.conditionMap.put("otherDesc", CHECK);
        this.showOthers = showOthers;
    }
    public String getShowProductAttribute() {
        return showProductAttribute;
    }
    public void setShowProductAttribute(String showProductAttribute) {
        this.conditionMap.put("productAttributeName", CHECK);
        this.showProductAttribute = showProductAttribute;
    }
    public String getShowProductType() {
        return showProductType;
    }
    public void setShowProductType(String showProductType) {
        this.conditionMap.put("productTypeName", CHECK);
        this.showProductType = showProductType;
    }
    public String getShowProjectData() {
        return showProjectData;
    }
    public void setShowProjectData(String showProjectData) {
        this.showProjectData = showProjectData;
    }
    public String getShowProjectScheduleAnticipate() {
        return showProjectScheduleAnticipate;
    }
    public void setShowProjectScheduleAnticipate(
            String showProjectScheduleAnticipate) {
        this.conditionMap.put("projectScheduleAnticipate", CHECK);
        this.showProjectScheduleAnticipate = showProjectScheduleAnticipate;
    }
    public String getShowProjectScheduleFact() {
        return showProjectScheduleFact;
    }
    public void setShowProjectScheduleFact(String showProjectScheduleFact) {
        this.conditionMap.put("projectScheduleFact", CHECK);
        this.showProjectScheduleFact = showProjectScheduleFact;
    }
    public String getShowProjectType() {
        return showProjectType;
    }
    public void setShowProjectType(String showProjectType) {
        this.conditionMap.put("projectTypeName", CHECK);
        this.showProjectType = showProjectType;
    }
    public String getShowSoftReq() {
        return showSoftReq;
    }
    public void setShowSoftReq(String showSoftReq) {
        this.conditionMap.put("softreq", CHECK);
        this.showSoftReq = showSoftReq;
    }
    public String getShowSupportLanguage() {
        return showSupportLanguage;
    }
    public void setShowSupportLanguage(String showSupportLanguage) {
        this.conditionMap.put("supportLanguage", CHECK);
        this.showSupportLanguage = showSupportLanguage;
    }
    public String getShowTechData() {
        return showTechData;
    }
    public void setShowTechData(String showTechData) {
        this.showTechData = showTechData;
    }
    public String getShowTechnicalDomain() {
        return showTechnicalDomain;
    }
    public void setShowTechnicalDomain(String showTechnicalDomain) {
        this.conditionMap.put("technicalDomainName", CHECK);
        this.showTechnicalDomain = showTechnicalDomain;
    }
    public String getShowTranslatePublishType() {
        return showTranslatePublishType;
    }
    public void setShowTranslatePublishType(String showTranslatePublishType) {
        this.conditionMap.put("translatePublishTypeToolTip", CHECK);
        this.showTranslatePublishType = showTranslatePublishType;
    }
    public String getShowWorkItem() {
        return showWorkItem;
    }
    public void setShowWorkItem(String showWorkItem) {
        this.conditionMap.put("workItemName", CHECK);
        this.showWorkItem = showWorkItem;
    }
  
	public String getShowBizSource() {
		return showBizSource;
	}
	public void setShowBizSource(String showBizSource) {
        this.conditionMap.put("bizSource", CHECK);
		this.showBizSource = showBizSource;
	}
	public String getShowCostBelong() {
		return showCostBelong;
	}
	public void setShowCostBelong(String showCostBelong) {
        this.conditionMap.put("costAttachBd", CHECK);
		this.showCostBelong = showCostBelong;
	}
	public String getShowCustomerServiceManager() {
		return showCustomerServiceManager;
	}
	public void setShowCustomerServiceManager(String showCustomerServiceManager) {
        this.conditionMap.put("serviceManagerName", CHECK);
		this.showCustomerServiceManager = showCustomerServiceManager;
	}
	public String getShowDivisionManager() {
		return showDivisionManager;
	}
	public void setShowDivisionManager(String showDivisionManager) {
        this.conditionMap.put("bdName", CHECK);
		this.showDivisionManager = showDivisionManager;
	}
	public String getShowEngageManager() {
		return showEngageManager;
	}
	public void setShowEngageManager(String showEngageManager) {
        this.conditionMap.put("engageManagerName", CHECK);
		this.showEngageManager = showEngageManager;
	}
	public String getShowLeader() {
		return showLeader;
	}
	public void setShowLeader(String showLeader) {
        this.conditionMap.put("leaderName", CHECK);
		this.showLeader = showLeader;
	}
	
	public String getShowParentProject() {
        return showParentProject;
    }
    public void setShowParentProject(String showParentProject) {
        this.conditionMap.put("relParentId", CHECK);
        this.showParentProject = showParentProject;
    }
    public String getShowNickName() {
		return showNickName;
	}
	public void setShowNickName(String showNickName) {
        this.conditionMap.put("acntName", CHECK);
		this.showNickName = showNickName;
	}
	public String getShowProductName() {
		return showProductName;
	}
	public void setShowProductName(String showProductName) {
        this.conditionMap.put("productName", CHECK);
		this.showProductName = showProductName;
	}
	public String getShowProjectDesc() {
		return showProjectDesc;
	}
	public void setShowProjectDesc(String showProjectDesc) {
        this.conditionMap.put("acntBrief", CHECK);
		this.showProjectDesc = showProjectDesc;
	}
	public String getShowProjectExecUnit() {
		return showProjectExecUnit;
	}
	public void setShowProjectExecUnit(String showProjectExecUnit) {
        this.conditionMap.put("execUnitId", CHECK);
		this.showProjectExecUnit = showProjectExecUnit;
	}
	public String getShowProjectExecuteSite() {
		return showProjectExecuteSite;
	}
	public void setShowProjectExecuteSite(String showProjectExecuteSite) {
        this.conditionMap.put("siteName", CHECK);
		this.showProjectExecuteSite = showProjectExecuteSite;
	}
	public String getShowProjectManager() {
		return showProjectManager;
	}
	public void setShowProjectManager(String showProjectManager) {
        this.conditionMap.put("pmName", CHECK);
		this.showProjectManager = showProjectManager;
	}
	public String getShowProjName() {
		return showProjName;
	}
	public void setShowProjName(String showProjName) {
        this.conditionMap.put("acntFullName", CHECK);
		this.showProjName = showProjName;
	}

	public String getShowSales() {
		return showSales;
	}
	public void setShowSales(String showSales) {
        this.conditionMap.put("salesName", CHECK);
		this.showSales = showSales;
	}
	public String getShowTCSigner() {
		return showTCSigner;
	}
	public void setShowTCSigner(String showTCSigner) {
        this.conditionMap.put("tcName", CHECK);
		this.showTCSigner = showTCSigner;
	}
	
	public String getShowAchieveBelong() {
		return showAchieveBelong;
	}
	public void setShowAchieveBelong(String showAchieveBelong) {
        this.conditionMap.put("achieveBelong", CHECK);
		this.showAchieveBelong = showAchieveBelong;
	}
	public String getShowApplicant() {
		return showApplicant;
	}
	public void setShowApplicant(String showApplicant) {
        this.conditionMap.put("appName", CHECK);
		this.showApplicant = showApplicant;
	}
    public String getShowMainData() {
        return showMainData;
    }
    public void setShowMainData(String showMainData) {
        this.showMainData = showMainData;
    }
    public String getShowCustomerInfo() {
        return showCustomerInfo;
    }
    public void setShowCustomerInfo(String showCustomerInfo) {
        this.conditionMap.put("cusShort", CHECK);
        this.showCustomerInfo = showCustomerInfo;
    }
    public String getShowAcntId() {
        return showAcntId;
    }
    public void setShowAcntId(String showAcntId) {
        this.conditionMap.put("acntId", CHECK);
        this.showAcntId = showAcntId;
    }
    public String getShowContractAcnt() {
        return showContractAcnt;
    }
    public void setShowContractAcnt(String showContractAcnt) {
        this.conditionMap.put("contractAcntId", CHECK);
        this.showContractAcnt = showContractAcnt;
    }
    public String getShowManMonth() {
        return showManMonth;
    }
    public void setShowManMonth(String showManMonth) {
        this.conditionMap.put("estManmonth", CHECK);
        this.showManMonth = showManMonth;
    }
    public Map getConditionMap() {
        return conditionMap;
    }
    public void setConditionMap(Map conditionMap) {
        this.conditionMap = conditionMap;
    }
	public String getShowBillingBasis() {
		return showBillingBasis;
	}
	public void setShowBillingBasis(String showBillingBasis) {
		this.conditionMap.put("billingBasisValue", CHECK);
		this.showBillingBasis = showBillingBasis;
	}
	public String getShowPrimaveraAdapted() {
		return showPrimaveraAdapted;
	}
	public void setShowPrimaveraAdapted(String showPrimaveraAdapted) {
		this.conditionMap.put("primaveraAdaptedValue", CHECK);
		this.showPrimaveraAdapted = showPrimaveraAdapted;
	}
	public String getShowRelPrjType() {
		return showRelPrjType;
	}
	public void setShowRelPrjType(String showRelPrjType) {
		this.conditionMap.put("relPrjType", CHECK);
		this.showRelPrjType = showRelPrjType;
	}

	 
}
