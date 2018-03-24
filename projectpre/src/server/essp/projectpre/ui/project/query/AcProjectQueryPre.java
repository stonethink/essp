package server.essp.projectpre.ui.project.query;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Acnt;
import server.essp.projectpre.service.account.IAccountService;
import server.essp.projectpre.service.account.ISelectOptionList;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import server.framework.taglib.util.SelectOptionImpl;
import c2s.dto.TransactionData;

/**
 * Ԥ����ѯ������ʾҳ���Action
 * @author Stephen.zheng
 *
 *
 */
public class AcProjectQueryPre extends AbstractESSPAction {
    private String defaultStr = "--please select--";
    private SelectOptionImpl  firstOption = new SelectOptionImpl(defaultStr,"");
	/**
	 * Ԥ����ѯ������ʾҳ��
	 *   1.���ɲ�ѯҳ�����������vbProjectQuery
	 *   2.��ȡ���Г��c
	 *   3.��ȡ�ɱ��w�ن�λ
	 *   4.��ȡ�I�Ձ�Դ
	 *   5.��ȡ��������
	 *   6.��ȡ�������
	 *   7.��ȡ�aƷ���
	 *   8.��ȡ�aƷ����
	 *   9.��ȡ�����Ŀ
	 *   10.��ȡ���g�I��
	 *   11.��ȡ֧Ԯ�Z��
	 * @param request
	 * @param response
	 * @param data
	 * @throws BusinessException
	 */
	public void executeAct(HttpServletRequest request, HttpServletResponse response,
			TransactionData data) throws BusinessException {
		   ISelectOptionList selectOptionListService = (ISelectOptionList)this.getBean("SelectOptionListLogic");
		  
		    //���ɲ�ѯҳ�����������vbProjectQuery
		    VbProjectQuery vbProjectQuery = new VbProjectQuery();
		    //�@ȡ���І�λ
            vbProjectQuery.setExecUnitList(getExecUnitNameList());
		    //��ȡ���Г��c
		    vbProjectQuery.setExecSiteList(selectOptionListService.getExecSiteList());
		    
		    //��ȡ�ɱ��w�ن�λ
		    vbProjectQuery.setCostAttachBdList(selectOptionListService.getCostAttachBdList());
		    
		    //��ȡ�I�Ձ�Դ
		    vbProjectQuery.setBizSourceList(selectOptionListService.getBizSourceList());
		    
		    //��ȡ��������
		    vbProjectQuery.setAcntAttributeList(selectOptionListService.getAcntAttributeList());
		    
		    //��ȡ�������
		    vbProjectQuery.setAcntTypeList(selectOptionListService.getAcntTypeList());
		    
		    //��ȡ�aƷ���
		    vbProjectQuery.setProductTypeList(selectOptionListService.getProductTypeList());
		    
		    //��ȡ�aƷ����
		    vbProjectQuery.setProductPropertyList(selectOptionListService.getProductPropertyList());
		    
		    //��ȡ�����Ŀ
		    vbProjectQuery.setWorkItemList(selectOptionListService.getWorkItemList());
		    
            //��ȡ���g�I��
		    vbProjectQuery.setTechnicalAreaList(selectOptionListService.getTechnicalAreaList());
		    
            //��ȡ֧Ԯ�Z��
		    vbProjectQuery.setSupportLangugeList(selectOptionListService.getSupportLangugeList());
		    
		    //�@ȡ�I�Ռ���
		    vbProjectQuery.setBizPropertyList(getBizPropertyList());
		    
		    //�@ȡ���M���
		    vbProjectQuery.setBillTypeList(getBillTypeList());
		    
		    request.setAttribute(Constant.VIEW_BEAN_KEY,vbProjectQuery);
	}
     /**
     * ��ȡִ�е�λ��List���ɱ�ź����ƹ���
     * @return ִ�е�λ�б�
     */
     private List getExecUnitNameList() {
//      ��ȡִ�е�λ
        IAccountService acntLogic = (IAccountService) this.getBean("AccountLogic");
        Acnt acnt = new Acnt();
        List acntList = acntLogic.listDept();
        List execUnitNameList = new ArrayList();
        String execUnitName = null;
        SelectOptionImpl execUnitOption = null;
        execUnitNameList.add(firstOption);
        for(int i=0;i<acntList.size();i++) {
            acnt = (Acnt) acntList.get(i);
            execUnitName = acnt.getAcntId()+"---"+acnt.getAcntName();
            execUnitOption = new SelectOptionImpl(execUnitName, acnt.getAcntId(), execUnitName); 
            execUnitNameList.add(execUnitOption);
        }
        return execUnitNameList;
    }
     /**
      * ��ȡҵ������������ѡ��
      * @return
      */
     private List getBizPropertyList() {
    	 List bizPropertyList = new ArrayList();
         SelectOptionImpl siteOption = null;
         bizPropertyList.add(firstOption);
         siteOption = new SelectOptionImpl("Contract", "Contract", "Contract");
         bizPropertyList.add(siteOption);
         siteOption = new SelectOptionImpl("Sub-Contract ", "Sub-Contract", "Sub-Contract");
         bizPropertyList.add(siteOption);
         siteOption = new SelectOptionImpl("Investment", "Investment", "Investment");
         bizPropertyList.add(siteOption);
         siteOption = new SelectOptionImpl("Purchase Order", "Purchase Order", "Purchase Order");
         bizPropertyList.add(siteOption);
         siteOption = new SelectOptionImpl("others", "others", "others");
         bizPropertyList.add(siteOption);
         return bizPropertyList;
     }
     /**
      * ��ȡ�շ�����������˵�
      * @return
      */
     private List getBillTypeList() {
    	 List billTypeList = new ArrayList();
    	 SelectOptionImpl siteOption = null;
    	 billTypeList.add(firstOption);
         siteOption = new SelectOptionImpl("Fixed Price", "Fixed Price", "Fixed Price");
         billTypeList.add(siteOption);
         siteOption = new SelectOptionImpl("Time&Material", "Time&Material", "Time&Material");
         billTypeList.add(siteOption);
         siteOption = new SelectOptionImpl("others", "others", "others");
         billTypeList.add(siteOption);
    	 return billTypeList;
     }

}
