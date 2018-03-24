package server.essp.projectpre.ui.project.apply;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Parameter;
import server.essp.projectpre.service.account.ISelectOptionList;
import server.essp.projectpre.service.constant.Constant;
import server.essp.projectpre.service.parameter.IParameterService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;

/**
 * Ԥ�����봰��ҳ���м������Ͽ�Ƭ��Action
 * @author wenhaizheng
 *
 * 
 */
public class AcPreviewAddTechInfoApp extends AbstractESSPAction {
	/**
	 * ��ʼ��ר�������ļ������Ͽ�Ƭҳ��
	 * @param request
	 * @param response
	 * @param data
	 * @throws BusinessException
	 */
    public void executeAct(HttpServletRequest request, HttpServletResponse response, 
            TransactionData data) throws BusinessException {
        //���ݿ������еļ�������ѡ��
        IParameterService parameterLogic = (IParameterService) this.getBean("ParameterLogic");
//        List projectTypeList = parameterLogic.listAllByKindEnable(Constant.PROJECT_TYPE);
        List productTypeList = parameterLogic.listAllByKindEnable(Constant.PRODUCT_TYPE);
        List productAttributeList = parameterLogic.listAllByKindEnable(Constant.PRODUCT_ATTRIBUTE);
        List workItemList = parameterLogic.listAllByKindEnable(Constant.WORK_ITEM);
        List technicalDomainList = parameterLogic.listAllByKindEnable(Constant.TECHNICAL_DOMAIN);
        List originalLanguageList = parameterLogic.listAllByKindEnable(Constant.ORIGINAL_LANGUAGE);
        List translationLanguageList = parameterLogic.listAllByKindEnable(Constant.TRANSLATION_LANGUANGE);
        
//        List newProjectTypeList = viewBeanList(projectTypeList);
        List newProductTypeList = viewBeanList(productTypeList);
        List newProductAttributeList = viewBeanList(productAttributeList);
        List newWorkItemList = viewBeanList(workItemList);
        List newTechnicalDomainList = viewBeanList(technicalDomainList);
        List newOriginalLanguageList = viewBeanList(originalLanguageList);
        List newTranslationLanguageList = viewBeanList(translationLanguageList);
    
        ISelectOptionList selectOptionListService = (ISelectOptionList)this.getBean("SelectOptionListLogic");
        VbTechInfoApp viewBean = new VbTechInfoApp();
        viewBean.setProjectTypeList(selectOptionListService.getAcntTypeList());
        viewBean.setProductTypeList(newProductTypeList);
        viewBean.setProductAttribute(newProductAttributeList);
        viewBean.setWorkItem(newWorkItemList);
        viewBean.setTechnicalDomain(newTechnicalDomainList);
        viewBean.setOriginalLanguage(newOriginalLanguageList);
        viewBean.setTranslationLanguage(newTranslationLanguageList);
        
        request.setAttribute(server.framework.common.Constant.VIEW_BEAN_KEY,viewBean);
    
    }
    /**
     * ת��ѡ�����б�ת����������ʾ��List
     * @param allList ѡ����б�
     * @return ת���õ��б�
     */
    private List viewBeanList (List allList) {
        List newList = new ArrayList();
        for(int i=0;i<allList.size();i++) {
            VbSelectedOption vb = new VbSelectedOption();
            Parameter parameter = (Parameter) allList.get(i);
            vb.setCode(parameter.getCompId().getCode());
            vb.setName(parameter.getName());
            vb.setStatus("");
            newList.add(vb);
        }
     return newList;   
    }

}
