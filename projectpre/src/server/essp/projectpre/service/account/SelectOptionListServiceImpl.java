package server.essp.projectpre.service.account;

import java.util.ArrayList;
import java.util.List;




import server.essp.projectpre.db.Bd;
import server.essp.projectpre.db.Parameter;
import server.essp.projectpre.db.Site;
import server.essp.projectpre.service.bd.IBdService;
import server.essp.projectpre.service.constant.Constant;
import server.essp.projectpre.service.parameter.IParameterService;
import server.essp.projectpre.service.site.ISiteService;
import server.framework.logic.AbstractBusinessLogic;
import server.framework.taglib.util.SelectOptionImpl;

public class SelectOptionListServiceImpl extends AbstractBusinessLogic implements
		ISelectOptionList {
    
	private List tmpList = null;
	private String defaultStr = "--please select--";
	private SelectOptionImpl defaultOption = new SelectOptionImpl(defaultStr, "");
	private ISiteService siteService;
	private IBdService  bdService;
	private IParameterService parameterService;
	
	public void setBdService(IBdService bdService) {
		this.bdService = bdService;
	}

	public void setParameterService(IParameterService parameterService) {
		this.parameterService = parameterService;
	}

	public void setSiteService(ISiteService siteService) {
		this.siteService = siteService;
	}

    /**
     * 获取執行據點
     */
	public List getExecSiteList(){
	    tmpList = siteService.listAllEabled();
	
	    List execSiteList = new ArrayList();
	    execSiteList.add(defaultOption);
	    Site site = null;
	    SelectOptionImpl execSiteOption = null;
	    
	    for(int i=0;i<tmpList.size();i++){
	    	site=(Site)tmpList.get(i);
	    	execSiteOption=new SelectOptionImpl(site.getSiteName(),site.getSiteCode());
	    	execSiteList.add(execSiteOption);
	    }
		return execSiteList;
	}
	
    /**
     * 获取成本歸屬單位
     */
	public List getCostAttachBdList(){
	    tmpList = bdService.listAllEabled();
	    List costAttachBdList = new ArrayList();
	    costAttachBdList.add(defaultOption);
	    Bd bd = null;
	    SelectOptionImpl costAttachBdOption = null;
	    
	    for(int i = 0;i < tmpList.size();i++){
	    	bd = (Bd)tmpList.get(i);
	    	costAttachBdOption = new SelectOptionImpl(bd.getBdName(),bd.getBdCode());
	    	costAttachBdList.add(costAttachBdOption);
	    }
		return costAttachBdList;
	}
	
	/**
	 * 業務來源
	 */
	public List getBizSourceList(){
		return getCostAttachBdList();
	}
	/**
	 * 获得业绩归属
	 */
	public List getAchieveBelongList(){
		return getCostAttachBdList();
	}
	
	/**
	 * 获取專案屬性
	 * 包括：Customer，Research，Dept
	 */
	public List getAcntAttributeList(){
        List acntAttributeList = getParameterListByKind(Constant.PROJEC_TYPE);
        acntAttributeList.add(0,defaultOption);
		return acntAttributeList;
	}
	
	/**
	 * 專案類型
	 */
	public List getAcntTypeList(){
		List acntTypeList = getParameterListByKind(Constant.PROJECT_TYPE);
		acntTypeList.add(0,defaultOption);
		return acntTypeList;
	}
	
	/**
	 * 產品類型
	 */
	public List getProductTypeList(){
		List productTypeList = getParameterListByKind(Constant.PRODUCT_TYPE);
		productTypeList.add(0,defaultOption);
		return productTypeList;
	}
	
	/**
	 * 產品屬性
	 */
	public List getProductPropertyList(){
		List productPropertyList = getParameterListByKind(Constant.PRODUCT_ATTRIBUTE);
		productPropertyList.add(0,defaultOption);
		return productPropertyList;
	}
	
	/**
	 * 工作項目
	 */
	public List getWorkItemList(){
		List workItemList = getParameterListByKind(Constant.WORK_ITEM);
		workItemList.add(0,defaultOption);
		return workItemList;
	}
	
	/**
	 * 技術領域
	 */
	public List getTechnicalAreaList(){
		List technicalAreaList = getParameterListByKind(Constant.TECHNICAL_DOMAIN);
		technicalAreaList.add(0,defaultOption);
		return technicalAreaList;
	}
	
	/**
	 * 支援語言
	 */
	public List getSupportLangugeList(){
		List supportLangugeList = new ArrayList();
		supportLangugeList.add(defaultOption);
		//获取原文
		List originalLanguage = getLanguageParameterListByKind(Constant.ORIGINAL_LANGUAGE);
		for(int i = 0;i < originalLanguage.size();i++){
			supportLangugeList.add(originalLanguage.get(i));
		}
		//获取譯文
		List translationLanguage = getLanguageParameterListByKind(Constant.TRANSLATION_LANGUANGE);
		for(int i = 0; i< translationLanguage.size();i++){
			supportLangugeList.add(translationLanguage.get(i));
		}
		return supportLangugeList;
	}
	/**
	 * 根据kind类型获取对应的CODE LIST
	 * kind类型有：
	 * ProjectType，ProductType，ProductAttribute，WorkItem，TechnicalDomain，OriginalLanguage，TranslationLanguage，BusinessType，CountryCode
	 * @param kind
	 * @return
	 */
	public List getParameterListByKind(String kind){
		tmpList = parameterService.listAllByKindEnable(kind);
		List parameterList = new ArrayList();
		Parameter parameter = null;
		SelectOptionImpl parameterOption = null;
		for(int i = 0;i < tmpList.size();i++){
			parameter = (Parameter)tmpList.get(i);
			parameterOption = new SelectOptionImpl(parameter.getName(),parameter.getCompId().getCode());
			parameterList.add(parameterOption);
		}
		return parameterList;
	}

	
	/**
	 * 根据类型获取语言参数
	 */
    public List getLanguageParameterListByKind(String kind) {
        tmpList = parameterService.listAllByKindEnable(kind);
        List parameterList = new ArrayList();
        Parameter parameter = null;
        SelectOptionImpl parameterOption = null;
        for(int i = 0;i < tmpList.size();i++){
            parameter = (Parameter)tmpList.get(i);
            parameterOption = new SelectOptionImpl(kind+"---"+parameter.getName(),kind+"---"+parameter.getCompId().getCode());
            parameterList.add(parameterOption);
        }
        return parameterList;
    }
}
