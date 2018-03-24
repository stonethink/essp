package server.essp.hrbase.humanbase.form;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class AfSearchCondition extends ActionForm {
    private String CHECK = "checked";
    
	private String showMainData;//主要数据
	private String showEmployeeId;//是否显示工號
	private String showEnglishName;//是否显示英文姓名
	private String showChineseName;//是否显示中文姓名
	private String showTitle;//是否显示職稱
	private String showRank;//是否显示職等
	private String showResManagerId;//是否显示授權主管工號
	private String showResManagerName;//是否显示授權主管
	private String showSite;//是否显示所屬區域
	private String showUnitCode;//是否显示部門代碼
	private String showIsDirect;//是否显示直/間接
	private String showInDate;//是否显示到職日
	private String showOutDate;//是否显示離職日
	private String showEffectiveDate;//是否显示生效日
	private String showHrAttribute;//是否显示HR 屬性
	private String showAttribute;//是否显示人員屬性
	private String showIsFormal;//是否显示正式員工
	private String showEmail;//是否显示郵件地址
	private String showOnJob;//是否显示是否在职

   
    private Map conditionMap = new HashMap();//用于记录excel文件中栏位是否显示的Map
    
    //判断是否勾选了要显示的栏位
    public boolean isSelectSomething() {
        return getShowMainData()!=null
               ||
               getShowEmployeeId()!=null
               ||
               getShowEnglishName()!=null
               ||
               getShowChineseName()!=null
               ||
               getShowTitle()!=null
               ||
               getShowRank()!=null
               ||
               getShowResManagerId()!=null
               ||
               getShowResManagerName()!=null
               ||
               getShowSite()!=null
               ||
               getShowUnitCode()!=null
               ||
               getShowIsDirect()!=null
               ||
               getShowOutDate()!=null
               ||
               getShowEffectiveDate()!=null
               ||
               getShowHrAttribute()!=null
               ||
               getShowAttribute()!=null
               ||
               getShowIsFormal()!=null
               ||
               getShowEmail()!=null
               ||
               getShowOnJob()!=null;
    }
    //设置默认的显示的栏位
    public void createDefaultSelect(){
    	setShowMainData(CHECK);
    	setShowEmployeeId(CHECK);
    	setShowEnglishName(CHECK);
    	setShowChineseName(CHECK);
    	setShowTitle(CHECK);
    	setShowRank(CHECK);
    	setShowResManagerId(CHECK);
    	setShowResManagerName(CHECK);
    	setShowSite(CHECK);
    	setShowUnitCode(CHECK);
    	setShowIsDirect(CHECK);
    	setShowInDate(CHECK);
    	setShowOutDate(CHECK);
    	setShowEffectiveDate(CHECK);
    	setShowHrAttribute(CHECK);
    	setShowAttribute(CHECK);
    	setShowIsFormal(CHECK);
    	setShowEmail(CHECK);
    	setShowOnJob(CHECK);
    }
	public String getShowMainData() {
		return showMainData;
	}
	public void setShowMainData(String showMainData) {
		this.showMainData = showMainData;
	}
	public String getShowEmployeeId() {
		return showEmployeeId;
	}
	public void setShowEmployeeId(String showEmployeeId) {
		conditionMap.put("employeeId", CHECK);
		this.showEmployeeId = showEmployeeId;
	}
	public String getShowEnglishName() {
		return showEnglishName;
	}
	public void setShowEnglishName(String showEnglishName) {
		conditionMap.put("englishName", CHECK);
		this.showEnglishName = showEnglishName;
	}
	public String getShowChineseName() {
		return showChineseName;
	}
	public void setShowChineseName(String showChineseName) {
		conditionMap.put("chineseName", CHECK);
		this.showChineseName = showChineseName;
	}
	public String getShowTitle() {
		return showTitle;
	}
	public void setShowTitle(String showTitle) {
		conditionMap.put("title", CHECK);
		this.showTitle = showTitle;
	}
	public String getShowRank() {
		return showRank;
	}
	public void setShowRank(String showRank) {
		conditionMap.put("rank", CHECK);
		this.showRank = showRank;
	}
	public String getShowResManagerId() {
		return showResManagerId;
	}
	public void setShowResManagerId(String showResManagerId) {
		conditionMap.put("resManagerId", CHECK);
		this.showResManagerId = showResManagerId;
	}
	public String getShowResManagerName() {
		return showResManagerName;
	}
	public void setShowResManagerName(String showResManagerName) {
		conditionMap.put("resManagerName", CHECK);
		this.showResManagerName = showResManagerName;
	}
	public String getShowSite() {
		return showSite;
	}
	public void setShowSite(String showSite) {
		conditionMap.put("site", CHECK);
		this.showSite = showSite;
	}
	public String getShowUnitCode() {
		return showUnitCode;
	}
	public void setShowUnitCode(String showUnitCode) {
		conditionMap.put("unitCode", CHECK);
		this.showUnitCode = showUnitCode;
	}
	public String getShowIsDirect() {
		return showIsDirect;
	}
	public void setShowIsDirect(String showIsDirect) {
		conditionMap.put("isDirectName", CHECK);
		this.showIsDirect = showIsDirect;
	}
	public String getShowInDate() {
		return showInDate;
	}
	public void setShowInDate(String showInDate) {
		conditionMap.put("inDate", CHECK);
		this.showInDate = showInDate;
	}
	public String getShowOutDate() {
		return showOutDate;
	}
	public void setShowOutDate(String showOutDate) {
		conditionMap.put("outDate", CHECK);
		this.showOutDate = showOutDate;
	}
	public String getShowEffectiveDate() {
		return showEffectiveDate;
	}
	public void setShowEffectiveDate(String showEffectiveDate) {
		conditionMap.put("effectiveDate", CHECK);
		this.showEffectiveDate = showEffectiveDate;
	}
	public String getShowHrAttribute() {
		return showHrAttribute;
	}
	public void setShowHrAttribute(String showHrAttribute) {
		conditionMap.put("hrAttribute", CHECK);
		this.showHrAttribute = showHrAttribute;
	}
	public String getShowIsFormal() {
		return showIsFormal;
	}
	public void setShowIsFormal(String showIsFormal) {
		conditionMap.put("attribute", CHECK);
		this.showIsFormal = showIsFormal;
	}
	public String getShowEmail() {
		return showEmail;
	}
	public void setShowEmail(String showEmail) {
		conditionMap.put("isFormalName", CHECK);
		this.showEmail = showEmail;
	}
	public String getShowAttribute() {
		return showAttribute;
	}
	public void setShowAttribute(String showAttribute) {
		conditionMap.put("email", CHECK);
		this.showAttribute = showAttribute;
	}
	public Map getConditionMap() {
		return conditionMap;
	}
	public void setConditionMap(Map conditionMap) {
		this.conditionMap = conditionMap;
	}
	public String getShowOnJob() {
		return showOnJob;
	}
	public void setShowOnJob(String showOnJob) {
		conditionMap.put("onJob", CHECK);
		this.showOnJob = showOnJob;
	}
	
	

	 
}
