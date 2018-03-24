package server.essp.hrbase.humanbase.form;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class AfSearchCondition extends ActionForm {
    private String CHECK = "checked";
    
	private String showMainData;//��Ҫ����
	private String showEmployeeId;//�Ƿ���ʾ��̖
	private String showEnglishName;//�Ƿ���ʾӢ������
	private String showChineseName;//�Ƿ���ʾ��������
	private String showTitle;//�Ƿ���ʾ�Q
	private String showRank;//�Ƿ���ʾ��
	private String showResManagerId;//�Ƿ���ʾ�ڙ����ܹ�̖
	private String showResManagerName;//�Ƿ���ʾ�ڙ�����
	private String showSite;//�Ƿ���ʾ���م^��
	private String showUnitCode;//�Ƿ���ʾ���T���a
	private String showIsDirect;//�Ƿ���ʾֱ/�g��
	private String showInDate;//�Ƿ���ʾ����
	private String showOutDate;//�Ƿ���ʾ�x��
	private String showEffectiveDate;//�Ƿ���ʾ��Ч��
	private String showHrAttribute;//�Ƿ���ʾHR ����
	private String showAttribute;//�Ƿ���ʾ�ˆT����
	private String showIsFormal;//�Ƿ���ʾ��ʽ�T��
	private String showEmail;//�Ƿ���ʾ�]����ַ
	private String showOnJob;//�Ƿ���ʾ�Ƿ���ְ

   
    private Map conditionMap = new HashMap();//���ڼ�¼excel�ļ�����λ�Ƿ���ʾ��Map
    
    //�ж��Ƿ�ѡ��Ҫ��ʾ����λ
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
    //����Ĭ�ϵ���ʾ����λ
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
