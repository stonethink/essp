package server.essp.projectpre.service.account;

import java.util.List;

public interface ISelectOptionList {
	
	/**
	 * 获取執行據點
	 * @return
	 */
	public List getExecSiteList();
	
	/**
	 * 获取成本歸屬單位
	 * @return
	 */
	public List getCostAttachBdList();
	
	/**
	 * 获取業務來源
	 * @return
	 */
	public List getBizSourceList();
	
	/**
	 * 获得业绩归属
	 * @return
	 */
	public List getAchieveBelongList();
	
	/**
	 * 获取專案屬性
	 *  包括：Customer，Research，Dept
	 * @return
	 */
	public List getAcntAttributeList();
	
	/**
	 * 获取專案類型
	 * @return
	 */
	public List getAcntTypeList();
	
	/**
	 * 获取產品類型
	 * @return
	 */
	public List getProductTypeList();
	
	/**
	 * 获取產品屬性
	 * @return
	 */
	public List getProductPropertyList();
	
	/**
	 * 获取工作項目
	 * @return
	 */
	public List getWorkItemList();
	
	/**
	 * 获取技術領域
	 * @return
	 */
	public List getTechnicalAreaList();
	
	/**
	 * 支援語言
	 * @return
	 */
	List getSupportLangugeList();
	
	/**
	 *  根据kind类型获取对应的CODE LIST
	 *  kind类型有：
	 *  ProjectType，ProductType，ProductAttribute，WorkItem，TechnicalDomain，OriginalLanguage，TranslationLanguage，BusinessType，CountryCode
	 * @param kind
	 * @return
	 */
	public List getParameterListByKind(String kind);
    
	/**
     * 根据类型获取语言参数
     * @param kind
     * @return
     */
    public List getLanguageParameterListByKind(String kind);
}
