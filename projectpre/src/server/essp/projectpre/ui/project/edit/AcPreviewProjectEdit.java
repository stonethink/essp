/*
 * Created on 2007-4-23
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.projectpre.ui.project.edit;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Bd;
import server.essp.projectpre.db.Parameter;
import server.essp.projectpre.db.Site;
import server.essp.projectpre.service.bd.IBdService;
import server.essp.projectpre.service.parameter.IParameterService;
import server.essp.projectpre.service.site.ISiteService;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import server.framework.taglib.util.SelectOptionImpl;
import c2s.dto.TransactionData;

public class AcPreviewProjectEdit extends AbstractESSPAction {
    private String defaultStr = "--please select--";
    private SelectOptionImpl  firstOption = new SelectOptionImpl(defaultStr,"");
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {
        AfProjectEdit af = new AfProjectEdit();
        af.setAcntAttributeList(getAcntAttributeList());
        af.setAchieveBelongList(getBdCodeList());
        af.setExecSiteList(getExecSiteList());
        af.setBizPropertyList(getBizPropertyList());
        af.setBillTypeList(getBillTypeList());
        this.getRequest().setAttribute(Constant.VIEW_BEAN_KEY, af);
    }
    /**
     * 获取专案属性
     * @return  属性列表
     */
   private List getAcntAttributeList(){
       List acntAttributeList = new ArrayList();
       IParameterService parameterLogic = (IParameterService) this.getBean("ParameterLogic");
       List parameterList = parameterLogic.listAllByKindEnable(server.essp.projectpre.service.constant.Constant.PROJEC_TYPE);
       SelectOptionImpl parameterOption = null; 
       acntAttributeList.add(firstOption);
       Parameter parameter = new Parameter();
       for(int i = 0; i<parameterList.size();i++) {
           parameter = (Parameter) parameterList.get(i);
           parameterOption = new SelectOptionImpl(parameter.getName(), parameter.getCompId().getCode(), parameter.getName());
          
           acntAttributeList.add(parameterOption);
       }
       
       return acntAttributeList;
       
   }
    /**
     * 获取业绩归属，业务来源的List
     * @return BD列表
     */
     private List getBdCodeList() {
         //获得业绩归属，业务来源
         IBdService  bdLogic = (IBdService) this.getBean("BdCodeLogic");
         List bdList = bdLogic.listEabledBelongs(true);
         List achieveBelongList = new ArrayList();
         SelectOptionImpl bdOption = null;  
         achieveBelongList.add(firstOption);
         Bd bd = new Bd();
        for(int i = 0; i<bdList.size();i++) {
            bd = (Bd) bdList.get(i);
            bdOption = new SelectOptionImpl(bd.getBdName(),bd.getBdCode(),bd.getBdName());
           
            achieveBelongList.add(bdOption);
        }
        return achieveBelongList;
     }
     
     
     /**
      * 获取执行据点的List
      * @return 执行据点的列表
      */
     private List getExecSiteList() {
//       获得执行据点
         ISiteService siteLogic = (ISiteService) this.getBean("AreaCodeLogic");                     
         List siteList = siteLogic.listAllEabled();
         List execSiteList = new ArrayList();
         SelectOptionImpl siteOption = null;
         execSiteList.add(firstOption);
         for(int i = 0; i<siteList.size();i++) {
             Site site = (Site) siteList.get(i);
             siteOption = new SelectOptionImpl(site.getSiteName(), site.getSiteCode(), site.getSiteName());
         
             execSiteList.add(siteOption);
         }         
         return execSiteList;
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
