package server.essp.projectpre.ui.project.query;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class AfSearchCondition extends ActionForm {
    private String CHECK = "checked";
    
	private String showMainData;//主要数据
	private String showApplicant;//是否显示申請人栏位
	private String showAchieveBelong;//是否显示業績歸屬栏位
	private String showLeader;//是否显示業務經理栏位
	private String showDivisionManager;//是否显示BD主管栏位
	private String showNickName;//是否显示專案簡稱栏位
	private String showProjectManager;//是否显示專案經理栏位
	private String showTCSigner;//是否显示工時表簽核者栏位
	private String showProjName;//是否显示專案全名栏位
	private String showProductName;//是否显示產品名稱栏位
	private String showProjectDesc;//是否显示專案內容簡述栏位
	private String showProjectExecUnit;//是否显示執行單位栏位
	private String showProjectExecuteSite;//是否显示執行據點栏位
	private String showCostBelong;//是否显示成本歸屬單位栏位
	private String showSales;//是否显示業務代表栏位
	private String showCustomerServiceManager;//是否显示服務總監栏位
	private String showEngageManager;//是否显示銷售支援經理栏位
	private String showBizSource;//是否显示業務來源栏位
	private String showParentProject;//是否显示父專案栏位
	private String showContractAcnt;//是否显示签约專案栏位
    private String showCustomerInfo;//是否显示客户信息栏位
    private String showAcntId;//是否显示专案代码栏位
    private String showPrimaveraAdapted;//是否顯示使用PrimaveraAdapted欄位
    private String showBillingBasis;//是否顯示向客戶收費欄位
    private String showRelPrjType;//是否顯示專案類型
    
    private String showTechData;//技术数据
    private String showProjectType;//是否显示专案类型栏位
    private String showProductType;//是否显示产品类型栏位
    private String showProductAttribute;//是否显示产品属性栏位
    private String showWorkItem;//是否显示工作项目栏位
    private String showTechnicalDomain;//是否显示技术领域栏位
    private String showSupportLanguage;//是否显示支持语文栏位
    private String showDevelopEnvironment;//是否显示开发环境栏位
    private String showTranslatePublishType;//是否显示翻译/排版/打字栏位
    private String showHardReq;//是否显示硬体需求栏位
    private String showSoftReq;//是否显示软体需求栏位
    
    private String showProjectData;//专案数据
    private String showProjectScheduleAnticipate;//是否显示专案预计进度栏位
    private String showProjectScheduleFact;//是否显示专案实际进度栏位
    private String showAnticipateWorkQuantity;//是否显示预计工作量栏位
    private String showOthers;//是否显示其他叙述栏位
    private String showManMonth;//是否显示预计人月栏位
    private Map conditionMap = new HashMap();//用于记录excel文件中栏位是否显示的Map
    
    //判断是否勾选了要显示的栏位
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
    //设置默认的显示的栏位
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
