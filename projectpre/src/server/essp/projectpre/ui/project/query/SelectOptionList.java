package server.essp.projectpre.ui.project.query;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import c2s.essp.common.account.IDtoAccount;

import server.essp.projectpre.db.Bd;
import server.essp.projectpre.db.Parameter;
import server.essp.projectpre.db.Site;
import server.essp.projectpre.service.bd.IBdService;
import server.essp.projectpre.service.constant.Constant;
import server.essp.projectpre.service.parameter.IParameterService;
import server.essp.projectpre.service.site.ISiteService;
import server.framework.action.AbstractBusinessAction;
import server.framework.common.BusinessException;
import server.framework.taglib.util.SelectOptionImpl;

public class SelectOptionList extends AbstractBusinessAction{
   
	private List tmpList = null;
	private String defaultStr = "--please select--";
	private SelectOptionImpl defaultOption = new SelectOptionImpl(defaultStr, "");
	
	public void executeAct(HttpServletRequest request, HttpServletResponse response,
			TransactionData data) throws BusinessException {
	}
	
    //��ȡ���Г��c
	public List getExecSiteList(){
	
		ISiteService siteService = (ISiteService)this.getBean("AreaCodeLogic");
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
	
    //��ȡ�ɱ��w�ن�λ
	public List getCostAttachBdList(){
		IBdService  bdLogic = (IBdService) this.getBean("BdCodeLogic");
	    tmpList = bdLogic.listAllEabled();
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
	
	//�I�Ձ�Դ
	public List getBizSourceList(){
		return getCostAttachBdList();
	}
	//���ҵ������
	public List getAchieveBelongList(){
		return getCostAttachBdList();
	}
	
	/**
	 * ��ȡ��������
	 * ������Customer��Research��Dept
	 */
	public List getAcntAttributeList(){
		List acntAttributeList = new ArrayList();
		acntAttributeList.add(defaultOption);
		
		SelectOptionImpl CustomerOption = new SelectOptionImpl(IDtoAccount.ACCOUNT_ATTRIBUTE_CUSTOMER,IDtoAccount.ACCOUNT_ATTRIBUTE_CUSTOMER);
		acntAttributeList.add(CustomerOption);
		
		SelectOptionImpl ResearchOption = new SelectOptionImpl(IDtoAccount.ACCOUNT_ATTRIBUTE_RESEARCH,IDtoAccount.ACCOUNT_ATTRIBUTE_RESEARCH);
		acntAttributeList.add(ResearchOption);
		
		SelectOptionImpl DeptOption = new SelectOptionImpl(IDtoAccount.ACCOUNT_ATTRIBUTE_DEPT,IDtoAccount.ACCOUNT_ATTRIBUTE_DEPT);
		acntAttributeList.add(DeptOption);
		
		return acntAttributeList;
	}
	
	//�������
	public List getAcntTypeList(){
		IParameterService parameterService = (IParameterService)this.getBean("ParameterLogic");
		tmpList = parameterService.listAllByKindEnable(Constant.PROJECT_TYPE);
		List acntTypeList = new ArrayList();
		acntTypeList.add(defaultOption);
		Parameter parameter = null;
		SelectOptionImpl acntTypeOption = null;
		for(int i = 0;i < tmpList.size();i++){
			parameter = (Parameter)tmpList.get(i);
			acntTypeOption = new SelectOptionImpl(parameter.getCompId().getCode(),parameter.getCompId().getCode());
			acntTypeList.add(acntTypeOption);
		}
		return acntTypeList;
	}
	
	//�aƷ���
	public List getProductTypeList(){
		List productTypeList = getParameterListByKind(Constant.PRODUCT_TYPE);
		productTypeList.add(0,defaultOption);
		return productTypeList;
	}
	
	//�aƷ����
	public List getProductPropertyList(){
		List productPropertyList = getParameterListByKind(Constant.PRODUCT_ATTRIBUTE);
		productPropertyList.add(0,defaultOption);
		return productPropertyList;
	}
	
	//�����Ŀ
	public List getWorkItemList(){
		List workItemList = getParameterListByKind(Constant.WORK_ITEM);
		workItemList.add(0,defaultOption);
		return workItemList;
	}
	
	//���g�I��
	public List getTechnicalAreaList(){
		List technicalAreaList = getParameterListByKind(Constant.TECHNICAL_DOMAIN);
		technicalAreaList.add(0,defaultOption);
		return technicalAreaList;
	}
	
	//֧Ԯ�Z��
	public List getSupportLangugeList(){
		List supportLangugeList = new ArrayList();
		supportLangugeList.add(defaultOption);
		//��ȡԭ��
		List originalLanguage = getParameterListByKind(Constant.ORIGINAL_LANGUAGE);
		for(int i = 0;i < originalLanguage.size();i++){
			supportLangugeList.add(originalLanguage.get(i));
		}
		//��ȡ�g��
		List translationLanguage = getParameterListByKind(Constant.TRANSLATION_LANGUANGE);
		for(int i = 0; i< translationLanguage.size();i++){
			supportLangugeList.add(translationLanguage.get(i));
		}
		return supportLangugeList;
	}
	/**
	 * ����kind���ͻ�ȡ��Ӧ��CODE LIST
	 * kind�����У�
	 * ProjectType��ProductType��ProductAttribute��WorkItem��TechnicalDomain��OriginalLanguage��TranslationLanguage��BusinessType��CountryCode
	 * @param kind
	 * @return
	 */
	public List getParameterListByKind(String kind){
		IParameterService parameterService = (IParameterService)this.getBean("ParameterLogic");
		tmpList = parameterService.listAllByKindEnable(kind);
		List parameterList = new ArrayList();
		Parameter parameter = null;
		SelectOptionImpl parameterOption = null;
		for(int i = 0;i < tmpList.size();i++){
			parameter = (Parameter)tmpList.get(i);
			parameterOption = new SelectOptionImpl(parameter.getCompId().getCode(),parameter.getCompId().getCode());
			parameterList.add(parameterOption);
		}
		return parameterList;
	}
}
