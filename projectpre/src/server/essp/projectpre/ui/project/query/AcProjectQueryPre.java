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
 * 预览查询条件显示页面的Action
 * @author Stephen.zheng
 *
 *
 */
public class AcProjectQueryPre extends AbstractESSPAction {
    private String defaultStr = "--please select--";
    private SelectOptionImpl  firstOption = new SelectOptionImpl(defaultStr,"");
	/**
	 * 预览查询条件显示页面
	 *   1.生成查询页面所需的数据vbProjectQuery
	 *   2.获取執行據點
	 *   3.获取成本歸屬單位
	 *   4.获取業務來源
	 *   5.获取專案屬性
	 *   6.获取專案類型
	 *   7.获取產品類型
	 *   8.获取產品屬性
	 *   9.获取工作項目
	 *   10.获取技術領域
	 *   11.获取支援語文
	 * @param request
	 * @param response
	 * @param data
	 * @throws BusinessException
	 */
	public void executeAct(HttpServletRequest request, HttpServletResponse response,
			TransactionData data) throws BusinessException {
		   ISelectOptionList selectOptionListService = (ISelectOptionList)this.getBean("SelectOptionListLogic");
		  
		    //生成查询页面所需的数据vbProjectQuery
		    VbProjectQuery vbProjectQuery = new VbProjectQuery();
		    //獲取執行單位
            vbProjectQuery.setExecUnitList(getExecUnitNameList());
		    //获取執行據點
		    vbProjectQuery.setExecSiteList(selectOptionListService.getExecSiteList());
		    
		    //获取成本歸屬單位
		    vbProjectQuery.setCostAttachBdList(selectOptionListService.getCostAttachBdList());
		    
		    //获取業務來源
		    vbProjectQuery.setBizSourceList(selectOptionListService.getBizSourceList());
		    
		    //获取專案屬性
		    vbProjectQuery.setAcntAttributeList(selectOptionListService.getAcntAttributeList());
		    
		    //获取專案類型
		    vbProjectQuery.setAcntTypeList(selectOptionListService.getAcntTypeList());
		    
		    //获取產品類型
		    vbProjectQuery.setProductTypeList(selectOptionListService.getProductTypeList());
		    
		    //获取產品屬性
		    vbProjectQuery.setProductPropertyList(selectOptionListService.getProductPropertyList());
		    
		    //获取工作項目
		    vbProjectQuery.setWorkItemList(selectOptionListService.getWorkItemList());
		    
            //获取技術領域
		    vbProjectQuery.setTechnicalAreaList(selectOptionListService.getTechnicalAreaList());
		    
            //获取支援語文
		    vbProjectQuery.setSupportLangugeList(selectOptionListService.getSupportLangugeList());
		    
		    //獲取業務屬性
		    vbProjectQuery.setBizPropertyList(getBizPropertyList());
		    
		    //獲取收費類型
		    vbProjectQuery.setBillTypeList(getBillTypeList());
		    
		    request.setAttribute(Constant.VIEW_BEAN_KEY,vbProjectQuery);
	}
     /**
     * 获取执行单位的List，由编号和名称构成
     * @return 执行单位列表
     */
     private List getExecUnitNameList() {
//      获取执行单位
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
      * 获取业务属性下拉框选项
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
      * 获取收费类型下拉框菜单
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
