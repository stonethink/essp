package server.essp.projectpre.ui.project.apply;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Acnt;
import server.essp.projectpre.db.AcntTechinfo;
import server.essp.projectpre.db.AcntTechinfoApp;
import server.essp.projectpre.db.Parameter;
import server.essp.projectpre.service.account.IAccountService;
import server.essp.projectpre.service.account.ISelectOptionList;
import server.essp.projectpre.service.accountapplication.IAccountApplicationService;
import server.essp.projectpre.service.constant.Constant;
import server.essp.projectpre.service.parameter.IParameterService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;

/**
 * Ԥ���޸������м������ϵ�Action
 * @author wenhaizheng
 *
 * 
 */
public class AcPreviewTechInfoApp extends AbstractESSPAction {

    private final static String RID="rid";
    /**
     * Ԥ���޸������м�������
     *    1.ҳ���ϻ�ȡ������ܵı�־λ��ֵ����ֵ��Ϊ�������
     *    2.��ȡ�Ѵ��ڵļ�������ѡ��
     *    3.��ȡ��ѡ��ļ�������ѡ��
     *    4.��������õ�ǰ̨ViewBean
     * @param request
     * @param response
     * @param data
     * @throws BusinessException
     */
    public void executeAct(HttpServletRequest request, HttpServletResponse response, 
                      TransactionData data) throws BusinessException {
        String accessType = request.getParameter("accessType");
        //���ø���ר���ı�־λ������������ר����������������
        String selectStatus = null;
        if(request.getParameter("selectAcnt")!=null){
            selectStatus = request.getParameter("selectAcnt");
        }
        VbTechInfoApp viewBean = new VbTechInfoApp();
        Long rid =null;
        Long changeRid=null;
        List selectedProjectTypeList = new ArrayList();
        String projectType = "";
        List selectedProductTypeList = new ArrayList();
        List selectedProductAttributeList = new ArrayList();
        List selectedWorkItemList = new ArrayList();
        List selectedTechnicalDomainList = new ArrayList();
        List selectedOriginalLanguageList = new ArrayList();
        List selectedTranslationLanguageList = new ArrayList();
        
        
        
        
       
        if (this.getRequest().getParameter(RID) != null) {
            rid = Long.valueOf(this.getRequest().getParameter(RID));
        }
        //ҳ���ϻ�ȡ������ܵı�־λ��ֵ����ֵ��Ϊ�������
        if(request.getParameter("view")!=null){
            String str = request.getParameter("view");
            String acntId = str.substring(0, str.indexOf("---"));
            IAccountService acntLogic = (IAccountService) this.getBean("AccountLogic");
            Acnt acnt = acntLogic.loadByAcntId(acntId, "1");
            changeRid =acnt.getRid();
            
            viewBean = disInfo(changeRid);
            
            //��ѡ�еļ������ϵĵ��б�����
            selectedProjectTypeList = acntLogic.listByRidKindFromTechInfo(changeRid, Constant.PROJECT_TYPE);
            if(selectedProjectTypeList.size()>0){
                   AcntTechinfo techInfo =  (AcntTechinfo)selectedProjectTypeList.get(0);
                   projectType = techInfo.getId().getCode(); 
            }
            selectedProductTypeList = acntLogic.listByRidKindFromTechInfo(changeRid, Constant.PRODUCT_TYPE);
            selectedProductAttributeList = acntLogic.listByRidKindFromTechInfo(changeRid, Constant.PRODUCT_ATTRIBUTE);
            selectedWorkItemList = acntLogic.listByRidKindFromTechInfo(changeRid, Constant.WORK_ITEM);
            selectedTechnicalDomainList = acntLogic.listByRidKindFromTechInfo(changeRid, Constant.TECHNICAL_DOMAIN);
            selectedOriginalLanguageList = acntLogic.listByRidKindFromTechInfo(changeRid, Constant.ORIGINAL_LANGUAGE);
            selectedTranslationLanguageList = acntLogic.listByRidKindFromTechInfo(changeRid, Constant.TRANSLATION_LANGUANGE);
        } else if(selectStatus!=null){
            
            viewBean = disInfo(rid);
            
            IAccountService acntLogic = (IAccountService) this.getBean("AccountLogic");
            selectedProjectTypeList = acntLogic.listByRidKindFromTechInfo(rid, Constant.PROJECT_TYPE);
            if(selectedProjectTypeList.size()>0){
                 AcntTechinfo techInfo =  (AcntTechinfo)selectedProjectTypeList.get(0);
                 projectType = techInfo.getId().getCode();
            }
            selectedProductTypeList = acntLogic.listByRidKindFromTechInfo(rid, Constant.PRODUCT_TYPE);
            selectedProductAttributeList = acntLogic.listByRidKindFromTechInfo(rid, Constant.PRODUCT_ATTRIBUTE);
            selectedWorkItemList = acntLogic.listByRidKindFromTechInfo(rid, Constant.WORK_ITEM);
            selectedTechnicalDomainList = acntLogic.listByRidKindFromTechInfo(rid, Constant.TECHNICAL_DOMAIN);
            selectedOriginalLanguageList = acntLogic.listByRidKindFromTechInfo(rid, Constant.ORIGINAL_LANGUAGE);
            selectedTranslationLanguageList = acntLogic.listByRidKindFromTechInfo(rid, Constant.TRANSLATION_LANGUANGE);
            
        } else {
            viewBean = disInfoApp(rid);
            
            IAccountApplicationService logic = (IAccountApplicationService) this.getBean("AccountApplicationLogic");
            selectedProjectTypeList = logic.listByRidKindFromTechInfoApp(rid, Constant.PROJECT_TYPE);
            if(selectedProjectTypeList.size()>0){
                  AcntTechinfoApp techInfoApp =  (AcntTechinfoApp)selectedProjectTypeList.get(0);
                  projectType = techInfoApp.getId().getCode(); 
            }
            selectedProductTypeList = logic.listByRidKindFromTechInfoApp(rid, Constant.PRODUCT_TYPE);
            selectedProductAttributeList = logic.listByRidKindFromTechInfoApp(rid, Constant.PRODUCT_ATTRIBUTE);
            selectedWorkItemList = logic.listByRidKindFromTechInfoApp(rid, Constant.WORK_ITEM);
            selectedTechnicalDomainList = logic.listByRidKindFromTechInfoApp(rid, Constant.TECHNICAL_DOMAIN);
            selectedOriginalLanguageList = logic.listByRidKindFromTechInfoApp(rid, Constant.ORIGINAL_LANGUAGE);
            selectedTranslationLanguageList = logic.listByRidKindFromTechInfoApp(rid, Constant.TRANSLATION_LANGUANGE);
        }
        
        //���ݿ������еļ�������ѡ��
        IParameterService parameterLogic = (IParameterService) this.getBean("ParameterLogic");
//        List projectTypeList = parameterLogic.listAllByKindEnable(Constant.PROJECT_TYPE);
        List productTypeList = parameterLogic.listAllByKindEnable(Constant.PRODUCT_TYPE);
        List productAttributeList = parameterLogic.listAllByKindEnable(Constant.PRODUCT_ATTRIBUTE);
        List workItemList = parameterLogic.listAllByKindEnable(Constant.WORK_ITEM);
        List technicalDomainList = parameterLogic.listAllByKindEnable(Constant.TECHNICAL_DOMAIN);
        List originalLanguageList = parameterLogic.listAllByKindEnable(Constant.ORIGINAL_LANGUAGE);
        List translationLanguageList = parameterLogic.listAllByKindEnable(Constant.TRANSLATION_LANGUANGE);
 
        
        
        //�Ƚ�����List
//        List newProjectTypeList =  compare2List(projectTypeList, selectedProjectTypeList);
        List newProductTypeList =  compare2List(productTypeList, selectedProductTypeList);
        List newProductAttributeList =  compare2List(productAttributeList, selectedProductAttributeList);
        List newWorkItemList =  compare2List(workItemList, selectedWorkItemList);
        List newTechnicalDomainList =  compare2List(technicalDomainList, selectedTechnicalDomainList);
        List newOriginalLanguageList =  compare2List(originalLanguageList, selectedOriginalLanguageList);
        List newTranslationLanguageList =  compare2List(translationLanguageList, selectedTranslationLanguageList);
        
        ISelectOptionList selectOptionListService = (ISelectOptionList)this.getBean("SelectOptionListLogic");
        viewBean.setProjectTypeList(selectOptionListService.getAcntTypeList());
        viewBean.setProjectType(projectType);
        viewBean.setProductTypeList(newProductTypeList);
        viewBean.setProductAttribute(newProductAttributeList);
        viewBean.setWorkItem(newWorkItemList);
        viewBean.setTechnicalDomain(newTechnicalDomainList);
        viewBean.setOriginalLanguage(newOriginalLanguageList);
        viewBean.setTranslationLanguage(newTranslationLanguageList);
        
  
        request.setAttribute(server.framework.common.Constant.VIEW_BEAN_KEY,viewBean);
        request.setAttribute("accessType", accessType);
        
    }
    /**
     * �޸�����ʱ��ʾ�����������ı���Ϣ
     * @param rid ��ǰ���뵥��
     * @return ������ʾ��ViewBean
     */
    private VbTechInfoApp disInfoApp(Long rid) {
  
      
      IAccountApplicationService logic = (IAccountApplicationService) this.getBean("AccountApplicationLogic");
    
      AcntTechinfoApp techInfoApp = new AcntTechinfoApp();
      VbTechInfoApp vbTechInfoApp = new VbTechInfoApp();
      //��������
      techInfoApp = logic.loadByRidKindCodeFromTechInfoApp(rid, Constant.DEVELOPENVIRONMENT, Constant.JOBSYSTEM);
      if(techInfoApp != null){
      vbTechInfoApp.setDevelopJobSystem(techInfoApp.getDescription());
      } 
      techInfoApp = logic.loadByRidKindCodeFromTechInfoApp(rid, Constant.DEVELOPENVIRONMENT, Constant.DATABASE);
      if(techInfoApp != null) {
      vbTechInfoApp.setDevelopDataBase(techInfoApp.getDescription());
      }
      techInfoApp = logic.loadByRidKindCodeFromTechInfoApp(rid, Constant.DEVELOPENVIRONMENT, Constant.TOOL);
      if(techInfoApp != null) {
      vbTechInfoApp.setDevelopTool(techInfoApp.getDescription());
      }
      techInfoApp = logic.loadByRidKindCodeFromTechInfoApp(rid, Constant.DEVELOPENVIRONMENT, Constant.NETWORK);
      if(techInfoApp != null) {
      vbTechInfoApp.setDevelopNetWork(techInfoApp.getDescription());
      }
      techInfoApp = logic.loadByRidKindCodeFromTechInfoApp(rid, Constant.DEVELOPENVIRONMENT, Constant.PROGRAMLANGUAGE);
      if(techInfoApp != null) {
      vbTechInfoApp.setDevelopProgramLanguage(techInfoApp.getDescription());
      }
      techInfoApp = logic.loadByRidKindCodeFromTechInfoApp(rid, Constant.DEVELOPENVIRONMENT, Constant.OTHERS);
      if(techInfoApp != null) {
      vbTechInfoApp.setDevelopOthers(techInfoApp.getDescription());
      }
      
      //����/�Ű�/����
      techInfoApp = logic.loadByRidKindCodeFromTechInfoApp(rid, Constant.TRNSLATEPUBLISHTYPE, Constant.JOBSYSTEM);
      if(techInfoApp != null) {
      vbTechInfoApp.setTypeJobSystem(techInfoApp.getDescription());
      }
      techInfoApp = logic.loadByRidKindCodeFromTechInfoApp(rid, Constant.TRNSLATEPUBLISHTYPE, Constant.DATABASE);
      if(techInfoApp != null) {
      vbTechInfoApp.setTypeDataBase(techInfoApp.getDescription());
      }
      techInfoApp = logic.loadByRidKindCodeFromTechInfoApp(rid, Constant.TRNSLATEPUBLISHTYPE, Constant.TOOL);
      if(techInfoApp != null) {
      vbTechInfoApp.setTypeTool(techInfoApp.getDescription());
      }
      techInfoApp = logic.loadByRidKindCodeFromTechInfoApp(rid, Constant.TRNSLATEPUBLISHTYPE, Constant.NETWORK);
      if(techInfoApp != null) {
      vbTechInfoApp.setTypeNetWork(techInfoApp.getDescription());
      }
      techInfoApp = logic.loadByRidKindCodeFromTechInfoApp(rid, Constant.TRNSLATEPUBLISHTYPE, Constant.PROGRAMLANGUAGE);
      if(techInfoApp != null) {
      vbTechInfoApp.setTypeProgramLanguage(techInfoApp.getDescription());
      }
      techInfoApp = logic.loadByRidKindCodeFromTechInfoApp(rid, Constant.TRNSLATEPUBLISHTYPE, Constant.OTHERS);
      if(techInfoApp != null) {
      vbTechInfoApp.setTypeOthers(techInfoApp.getDescription());
      }
      
      //Ӳ��/�������
      techInfoApp = logic.loadByRidKindCodeFromTechInfoApp(rid, Constant.HARDREQ, Constant.HARDREQ);
      if(techInfoApp != null) {
      vbTechInfoApp.setHardReq(techInfoApp.getDescription());
      }
      techInfoApp = logic.loadByRidKindCodeFromTechInfoApp(rid, Constant.SOFTREQ, Constant.SOFTREQ);
      if(techInfoApp != null) {
      vbTechInfoApp.setSoftReq(techInfoApp.getDescription());
      }
      
      return vbTechInfoApp;
    }
    /**
     * ��������ʱ��ʾ���������е��ı���Ϣ
     * @param rid ��ǰ���뵥��
     * @return ������ʾ��ViewBean
     */
    private VbTechInfoApp disInfo(Long rid) {
        
        IAccountService logic = (IAccountService) this.getBean("AccountLogic");
        
        AcntTechinfo techInfo = new AcntTechinfo();
        VbTechInfoApp vbTechInfoApp = new VbTechInfoApp();
        //��������
        techInfo = logic.loadByRidKindCodeFromTechInfo(rid, Constant.DEVELOPENVIRONMENT, Constant.JOBSYSTEM);
        if(techInfo != null){
        vbTechInfoApp.setDevelopJobSystem(techInfo.getDescription());
        } 
        techInfo = logic.loadByRidKindCodeFromTechInfo(rid, Constant.DEVELOPENVIRONMENT, Constant.DATABASE);
        if(techInfo != null) {
        vbTechInfoApp.setDevelopDataBase(techInfo.getDescription());
        }
        techInfo = logic.loadByRidKindCodeFromTechInfo(rid, Constant.DEVELOPENVIRONMENT, Constant.TOOL);
        if(techInfo != null) {
        vbTechInfoApp.setDevelopTool(techInfo.getDescription());
        }
        techInfo = logic.loadByRidKindCodeFromTechInfo(rid, Constant.DEVELOPENVIRONMENT, Constant.NETWORK);
        if(techInfo != null) {
        vbTechInfoApp.setDevelopNetWork(techInfo.getDescription());
        }
        techInfo = logic.loadByRidKindCodeFromTechInfo(rid, Constant.DEVELOPENVIRONMENT, Constant.PROGRAMLANGUAGE);
        if(techInfo != null) {
        vbTechInfoApp.setDevelopProgramLanguage(techInfo.getDescription());
        }
        techInfo = logic.loadByRidKindCodeFromTechInfo(rid, Constant.DEVELOPENVIRONMENT, Constant.OTHERS);
        if(techInfo != null) {
        vbTechInfoApp.setDevelopOthers(techInfo.getDescription());
        }
        
        //����/�Ű�/����
        techInfo = logic.loadByRidKindCodeFromTechInfo(rid, Constant.TRNSLATEPUBLISHTYPE, Constant.JOBSYSTEM);
        if(techInfo != null) {
        vbTechInfoApp.setTypeJobSystem(techInfo.getDescription());
        }
        techInfo = logic.loadByRidKindCodeFromTechInfo(rid, Constant.TRNSLATEPUBLISHTYPE, Constant.DATABASE);
        if(techInfo != null) {
        vbTechInfoApp.setTypeDataBase(techInfo.getDescription());
        }
        techInfo = logic.loadByRidKindCodeFromTechInfo(rid, Constant.TRNSLATEPUBLISHTYPE, Constant.TOOL);
        if(techInfo != null) {
        vbTechInfoApp.setTypeTool(techInfo.getDescription());
        }
        techInfo = logic.loadByRidKindCodeFromTechInfo(rid, Constant.TRNSLATEPUBLISHTYPE, Constant.NETWORK);
        if(techInfo != null) {
        vbTechInfoApp.setTypeNetWork(techInfo.getDescription());
        }
        techInfo = logic.loadByRidKindCodeFromTechInfo(rid, Constant.TRNSLATEPUBLISHTYPE, Constant.PROGRAMLANGUAGE);
        if(techInfo != null) {
        vbTechInfoApp.setTypeProgramLanguage(techInfo.getDescription());
        }
        techInfo = logic.loadByRidKindCodeFromTechInfo(rid, Constant.TRNSLATEPUBLISHTYPE, Constant.OTHERS);
        if(techInfo != null) {
        vbTechInfoApp.setTypeOthers(techInfo.getDescription());
        }
        
        //Ӳ��/�������
        techInfo = logic.loadByRidKindCodeFromTechInfo(rid, Constant.HARDREQ, Constant.HARDREQ);
        if(techInfo != null) {
        vbTechInfoApp.setHardReq(techInfo.getDescription());
        }
        techInfo = logic.loadByRidKindCodeFromTechInfo(rid, Constant.SOFTREQ, Constant.SOFTREQ);
        if(techInfo != null) {
        vbTechInfoApp.setSoftReq(techInfo.getDescription());
        }
        
        return vbTechInfoApp;
        
    }
    /**
     * �������Ͽ�Ƭ�бȽ����е�ѡ�������ݿ�����ѡ��
     * @param allList ����ѡ���б�
     * @param selectedList ���ݿ�����ѡ���б�
     * @return ������ʾ���б����ݿ���ѡ�����ʾΪѡ��״̬��
     */
    private List compare2List(List allList, List selectedList) {
        Parameter parameter = new Parameter();
        List newList = new ArrayList();
        if(this.getRequest().getParameter("view")!=null||this.getRequest().getParameter("selectAcnt")!=null) {
            AcntTechinfo techInfo = new AcntTechinfo();
            for(int i=0;i<allList.size();i++){
                VbSelectedOption viewBean = new VbSelectedOption();
                viewBean.setStatus("");
                parameter = (Parameter) allList.get(i);
               for(int j=0;j<selectedList.size();j++){
                   techInfo = (AcntTechinfo) selectedList.get(j); 
                    if(techInfo.getId().getCode().equals(parameter.getCompId().getCode())) {
                       viewBean.setStatus("checked");
                    }     
                }
               viewBean.setCode(parameter.getCompId().getCode());
               viewBean.setName(parameter.getName());
               newList.add(viewBean); 
            }
        } else {
            AcntTechinfoApp techInfoApp = new AcntTechinfoApp();
          for(int i=0;i<allList.size();i++){
            VbSelectedOption viewBean = new VbSelectedOption();
            viewBean.setStatus("");
            parameter = (Parameter) allList.get(i);
           for(int j=0;j<selectedList.size();j++){
                techInfoApp = (AcntTechinfoApp) selectedList.get(j); 
                if(techInfoApp.getId().getCode().equals(parameter.getCompId().getCode())) {
                   viewBean.setStatus("checked");
                }     
            }
           viewBean.setCode(parameter.getCompId().getCode());
           viewBean.setName(parameter.getName());
           newList.add(viewBean);
           
         }
        }
        return newList;
        
    }
    

}
