package server.essp.projectpre.ui.project.apply;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Acnt;
import server.essp.projectpre.db.AcntPerson;
import server.essp.projectpre.db.Bd;
import server.essp.projectpre.db.Parameter;
import server.essp.projectpre.db.Site;
import server.essp.projectpre.service.account.IAccountService;
import server.essp.projectpre.service.bd.IBdService;
import server.essp.projectpre.service.parameter.IParameterService;
import server.essp.projectpre.service.site.ISiteService;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import server.framework.taglib.util.SelectOptionImpl;
import c2s.dto.TransactionData;
import c2s.essp.common.account.IDtoAccount;
import c2s.essp.common.user.DtoUser;

import com.wits.util.comDate;

/**
 * 预览申请窗口页面中专案资料卡片的Action（包括专案新增，专案变更，专案复核功能的窗口预览）
 * @author wenhaizheng
 *
 * 
 */
public class AcPreviewAddProjectAcntApp extends AbstractESSPAction{
    private String defaultStr = "--please select--";
    private SelectOptionImpl  firstOption = new SelectOptionImpl(defaultStr,"");
    
    /**
     * 初始化弹出的专案申请新增的专案资料卡片页面
     * @param request
     * @param response
     * @param data
     * @throws BusinessException
     */
    public void executeAct(HttpServletRequest request, HttpServletResponse response, 
                 TransactionData data) throws BusinessException {
        DtoUser user = (DtoUser)request.getSession().getAttribute(DtoUser.SESSION_USER);
        String userLoginId = user.getUserLoginId();
        String appType = request.getParameter("appType");
        //构造ViewBean用于显示新增页面
        VbProjectAcntApp viewBean = new VbProjectAcntApp();
        
        viewBean.setRelPrjType(IDtoAccount.PROJECT_RELATION_MASTER);
        String isFinance = request.getParameter("isFinance");
        if("true".equalsIgnoreCase(isFinance)){
            viewBean.setRelPrjType(IDtoAccount.PROJECT_RELATION_FINANCE);
        }
        
        viewBean.setAchieveBelongList(getBdCodeList());     
        viewBean.setExecSiteList(getExecSiteList());
        viewBean.setExecUnitNameList(getExecUnitNameList());      
        viewBean.setAcntAttributeList(getAcntAttributeList());
        viewBean.setBizPropertyList(getBizPropertyList());
        viewBean.setBillTypeList(getBillTypeList());
        viewBean.setBlList(getBlList());//获取业务线部门
        if(appType.equals("change")){
            viewBean.setAcntIdList(getAcntIdList(userLoginId));
        }
        if(request.getParameter("view")!=null) {
            String str = request.getParameter("view");
            String acntId = str.substring(0, str.indexOf("---"));
            IAccountService acntlogic = (IAccountService) this.getBean("AccountLogic");
            Acnt acnt = acntlogic.loadByAcntId(acntId, "1");
//            viewBean.setRelPrjType(acnt.getRelPrjType());
//            if(acnt.getRelPrjType().equals(IDtoAccount.PROJECT_RELATION_SUB)
//                    ||acnt.getRelPrjType().equals(IDtoAccount.PROJECT_RELATION_RELATED)) {
//                acnt = acntlogic.loadMasterProjectFromAcnt(acnt.getRelParentId());
//                String parentProject =acnt.getAcntId()+"---"+acnt.getAcntName();
//                viewBean.setParentProject(parentProject);
//                
//            }
            str = acntId + "---" + acnt.getAcntName();
            viewBean.setAcntId(str);
            viewBean.setAcntName(acnt.getAcntName());
            viewBean.setAcntFullName(acnt.getAcntFullName());
            viewBean.setAcntBrief(acnt.getAcntBrief());
            viewBean.setAchieveBelong(acnt.getAchieveBelong());
            viewBean.setExecSite(acnt.getExecSite());
            viewBean.setAcntAttribute(acnt.getAcntAttribute());
            Acnt acntSite = acntlogic.loadByAcntId(acnt.getExecUnitId(), "0");
            String execUnitName = null;
            if(acntSite==null) {
                execUnitName="";
            } else {
                execUnitName = acntSite.getAcntId()+"---"+acntSite.getAcntName();
                
            }
            viewBean.setExecUnitName(execUnitName);
            viewBean.setCostAttachBd(acnt.getCostAttachBd());
            viewBean.setBizSource(acnt.getBizSource());
            viewBean.setProductName(acnt.getProductName());
            viewBean.setOtherDesc(acnt.getOtherDesc());
            viewBean.setAcntPlannedStart(comDate.dateToString(acnt.getAcntPlannedStart(), "yyyy-MM-dd"));
            viewBean.setAcntPlannedFinish(comDate.dateToString(acnt.getAcntPlannedFinish(), "yyyy-MM-dd"));
            viewBean.setAcntActualStart(comDate.dateToString(acnt.getAcntActualStart(), "yyyy-MM-dd"));
            viewBean.setAcntActualFinish(comDate.dateToString(acnt.getAcntActualFinish(), "yyyy-MM-dd"));
            viewBean.setPrimaveraAdapted(acnt.getPrimaveraAdapted());
            viewBean.setBillingBasis(acnt.getBillingBasis());
            viewBean.setBizProperty(acnt.getBizProperty());
            viewBean.setBillType(acnt.getBillType());
            viewBean.setBl(acnt.getBl());
            if(acnt.getEstManmonth()!=null){
            viewBean.setEstManmonth(acnt.getEstManmonth().toString());
            }
            if(acnt.getActualManmonth()!=null) {
            viewBean.setActualManmonth(acnt.getActualManmonth().toString());
            }
            if(acnt.getEstSize()!=null) {
            viewBean.setEstSize(acnt.getEstSize().toString());
            }
            
            //显示相关人员信息
            viewBean = previewPerson(acnt.getRid(), viewBean);
            
        }
        
        //将ViewBean设置到前台
        request.setAttribute(Constant.VIEW_BEAN_KEY,viewBean);
        
       
      
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
    * 获取业务线部门List
    * @return
    */
   private List getBlList() {
	   IAccountService service = (IAccountService) this.getBean("AccountLogic");
	   List<Acnt> blList = service.queryBlDept();
	   List blOptions = new ArrayList();
	   blOptions.add(firstOption);
	   SelectOptionImpl blOption = null; 
	   for (Acnt acnt : blList) {
		   blOption = new SelectOptionImpl(acnt.getAcntId()+"---"+acnt.getAcntName(),acnt.getAcntId(),
				   	acnt.getAcntId()+"--"+acnt.getAcntName());
		   blOptions.add(blOption);
	   }
	   return blOptions;
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
      * 获取执行单位的List，由编号和名称构成
      * @return 执行单位列表
      */
      private List getExecUnitNameList() {
//       获取执行单位
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
             execUnitOption = new SelectOptionImpl(execUnitName, execUnitName, execUnitName);
             execUnitNameList.add(execUnitOption);
         }
         return execUnitNameList;
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
     /**
      * 在新增专案变更申请中获取能变更的专案代码
      * @return 专案代码List
      */
     private List getAcntIdList(String loginId) {
         //获得专案代码
         List acntIdList = new ArrayList();
         IAccountService acntLogic = (IAccountService) this.getBean("AccountLogic");
//         IAccountApplicationService acntAppLogic = (IAccountApplicationService) this.getBean("AccountApplicationLogic");
//         List acntId = acntLogic.listAll(loginId);
         String acntIdName = null;
         SelectOptionImpl acntIdOption = null;
         acntIdList.add(firstOption);
//         for(int i = 0; i<acntId.size();i++) {
//             Acnt acnt = (Acnt) acntId.get(i);
//             AcntApp acntApp = acntAppLogic.loadByTypeAcntId(server.essp.projectpre.service.constant.Constant.PROJECTCHANGEAPP, acnt.getAcntId());
//             AcntApp acntAppNest = acntAppLogic.loadCloseByTypeAcntId(server.essp.projectpre.service.constant.Constant.PROJECTCONFIRMAPP, acnt.getAcntId());
//             if(acntApp==null&&acntAppNest==null) {
//             acntIdName = acnt.getAcntId()+"---"+acnt.getAcntName();
//             acntIdOption = new SelectOptionImpl(acntIdName, acntIdName, acntIdName);
//             acntIdList.add(acntIdOption);
//             }
//         }   
         List acntList = acntLogic.listAcntToChange(loginId);
         int size = acntList.size();
         for(int i = 0; i<size; i++){
             Acnt acnt = (Acnt) acntList.get(i);
             acntIdName = acnt.getAcntId()+"---"+acnt.getAcntName();
             acntIdOption = new SelectOptionImpl(acntIdName, acntIdName, acntIdName);
             acntIdList.add(acntIdOption);
         }
         return acntIdList;
     }
     /**
      * 用于获取该专案的相关人员信息
      * @param rid 当前专案的 rid
      * @param vb 于显示的ViewBean
      * @return 含有人员信息的ViewBean
      */
     private VbProjectAcntApp previewPerson(Long rid, VbProjectAcntApp vb) {
         IAccountService acntLogic = (IAccountService) this.getBean("AccountLogic");
         AcntPerson person = acntLogic.loadByRidFromPerson(rid, IDtoAccount.USER_TYPE_PM);
         if(person!=null){
         vb.setPMId(person.getLoginId());
         vb.setPMName(person.getName());
         vb.setPMDomain(person.getDomain());
         }
         
         person = acntLogic.loadByRidFromPerson(rid, IDtoAccount.USER_TYPE_TC_SIGNER);
         if(person!=null){
         vb.setTCSId(person.getLoginId());
         vb.setTCSName(person.getName());
         vb.setTCSDomain(person.getDomain());
         }
         
         person = acntLogic.loadByRidFromPerson(rid, IDtoAccount.USER_TYPE_BD_MANAGER);
         if(person!=null){
         vb.setBDId(person.getLoginId());
         vb.setBDMName(person.getName());
         vb.setBDDomain(person.getDomain());
         }
         
         person = acntLogic.loadByRidFromPerson(rid, IDtoAccount.USER_TYPE_LEADER);
         if(person!=null){
         vb.setLeaderId(person.getLoginId());
         vb.setLeaderName(person.getName());
         vb.setLeaderDomain(person.getDomain());
         }
         
         person = acntLogic.loadByRidFromPerson(rid, IDtoAccount.USER_TYPE_SALES);
         if(person!=null){
         vb.setSalesId(person.getLoginId());
         vb.setSalesName(person.getName());
         vb.setSalesDomain(person.getDomain());
         }
         
         return vb;
     }
 
}
