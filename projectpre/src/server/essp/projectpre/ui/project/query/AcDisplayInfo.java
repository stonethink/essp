
package server.essp.projectpre.ui.project.query;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Acnt;
import server.essp.projectpre.db.AcntCustContactor;
import server.essp.projectpre.db.AcntPerson;
import server.essp.projectpre.db.AcntTechinfo;
import server.essp.projectpre.db.Bd;
import server.essp.projectpre.db.Customer;
import server.essp.projectpre.db.Parameter;
import server.essp.projectpre.db.ParameterId;
import server.essp.projectpre.db.Site;
import server.essp.projectpre.service.account.IAccountService;
import server.essp.projectpre.service.bd.IBdService;
import server.essp.projectpre.service.constant.Constant;
import server.essp.projectpre.service.customer.ICustomerService;
import server.essp.projectpre.service.parameter.IParameterService;
import server.essp.projectpre.service.site.ISiteService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
import c2s.essp.common.account.IDtoAccount;

import com.wits.util.comDate;
/**
 * 显示查询到的专案信息
 * @author wenhaizheng
 *
 */
public class AcDisplayInfo extends AbstractESSPAction{
	/**
	 * 显示查询到的专案信息
	 *   1.获取人员信息
	 *   2.获取技术资料
	 *   3.获取用户信息
	 * @param request
	 * @param response
	 * @param data
	 * @throws BusinessException
	 */
    public void executeAct(HttpServletRequest request, 
            HttpServletResponse response, TransactionData data) 
    throws BusinessException {
        
        VbDisplayInfo vb = new VbDisplayInfo();
        String acntId = "";
        if(request.getParameter("acntId")!=null){
            acntId = request.getParameter("acntId");
        }
       
        IAccountService  acntLogic = (IAccountService) this.getBean("AccountLogic"); 
        Acnt acnt = acntLogic.loadByAcntId(acntId, "1");
      vb.setAcntId(acntId);
      vb.setIsPrinted(acnt.getIsPrinted());
      vb.setApplicationStatus(acnt.getAcntStatus());
      vb.setRelPrjType(acnt.getRelPrjType());
      vb.setAcntName(acnt.getAcntName());
      vb.setAcntFullName(acnt.getAcntFullName());
      vb.setAcntBrief(acnt.getAcntBrief());
      vb.setProductName(acnt.getProductName());
      ISiteService siteLogic = (ISiteService)this.getBean("AreaCodeLogic");
      Site site = siteLogic.loadByCode(acnt.getExecSite());
      if(site!=null){
      vb.setExecSite(site.getSiteName());
      }
      IBdService bdLogic = (IBdService)this.getBean("BdCodeLogic");
      Bd bd = bdLogic.loadByBdCode(acnt.getCostAttachBd());
      if(bd!=null){
      vb.setCostAttachBd(bd.getBdName());
      }
      bd = bdLogic.loadByBdCode(acnt.getBizSource());
      if(bd!=null){
      vb.setBizSource(bd.getBdName());
      }
      bd = bdLogic.loadByBdCode(acnt.getAchieveBelong());
      if(bd!=null){
      vb.setAchieveBelong(bd.getBdName());
      }
      IParameterService parameterLogic = (IParameterService)this.getBean("ParameterLogic");
      Parameter parameter = parameterLogic.loadByKindCode(Constant.PROJEC_TYPE, acnt.getAcntAttribute());
      if(parameter!=null){
      vb.setAcntAttribute(parameter.getName());
      }
      vb.setExecUnitId(acnt.getExecUnitId());
      vb.setEstSize(acnt.getEstSize());
      vb.setEstManmonth(acnt.getEstManmonth());
      vb.setActualManmonth(acnt.getActualManmonth());
      vb.setAcntPlannedStart(comDate.dateToString(acnt.getAcntPlannedStart(), "yyyy-MM-dd"));
      vb.setAcntPlannedFinish(comDate.dateToString(acnt.getAcntPlannedFinish(), "yyyy-MM-dd"));
      vb.setAcntActualStart(comDate.dateToString(acnt.getAcntActualStart(), "yyyy-MM-dd"));
      vb.setAcntActualFinish(comDate.dateToString(acnt.getAcntActualFinish(), "yyyy-MM-dd"));
      if("1".equals(acnt.getPrimaveraAdapted())) {
    	  vb.setPrimaveraAdapted("Y");
      } else {
    	  vb.setPrimaveraAdapted("N");
      }
      if("1".equals(acnt.getBillingBasis())) {
    	  vb.setBillingBasis("Y");
      } else {
    	  vb.setBillingBasis("N");
      }
      vb.setBizProperty(acnt.getBizProperty());
      vb.setBillType(acnt.getBillType());
      
      //获取人员信息
      vb = getPerson(acnt, vb);
      
      //获取技术资料
      vb = getTechInfo(acnt, vb); 
      vb = getTechInfoText(vb, acnt.getRid());
      vb = getTechInfoTextDetail(vb, acnt.getRid(), Constant.DEVELOPENVIRONMENT);
      vb = getTechInfoTextDetail(vb, acnt.getRid(), Constant.TRNSLATEPUBLISHTYPE);
      
      //获取用户信息
      vb = getCustomer(acnt, vb);
      ICustomerService custLogic = (ICustomerService)this.getBean("CustomerLogic");
      Customer cust = custLogic.loadByCustomerId(acnt.getCustomerId());
      if(cust!=null){
          vb.setCustomerId(acnt.getCustomerId()+"---"+cust.getShort_());
      } else {
          vb.setCustomerId(acnt.getCustomerId());
      }
     
      request.setAttribute(server.framework.common.Constant.VIEW_BEAN_KEY,vb);
    }
    /**
     * 获取技术资料文本资料
     * @param vb
     * @param rid
     * @return
     */
    private VbDisplayInfo getTechInfoText(VbDisplayInfo vb,Long rid) {
      AcntTechinfo techInfo = new AcntTechinfo();
      IAccountService acntLogic = (IAccountService) this.getBean("AccountLogic");
    
      techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, Constant.HARDREQ, Constant.HARDREQ);
      vb.setHardReq(techInfo.getDescription());
      
      techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, Constant.SOFTREQ, Constant.SOFTREQ);
      vb.setSoftReq(techInfo.getDescription());

      return vb;
  }
    /**
     * 根据专案的RID和技术资料类型获取技术资料
     * @param rid
     * @param type
     * @return
     */
    private String getTechInfoVb(Long rid, String type) {
        String value = null;
        AcntTechinfo techInfo = new AcntTechinfo();
        ParameterId parameterId = new ParameterId();
        parameterId.setKind(type);
        Parameter parameter = new Parameter();
        IAccountService acntLogic = (IAccountService) this.getBean("AccountLogic");
        Acnt acnt = acntLogic.loadByRid(rid);
        IParameterService parameterLogic = (IParameterService) this.getBean("ParameterLogic"); 
        List techinfoList = acntLogic.listByRidKindFromTechInfo(acnt.getRid(), type);
        for(int i=0;i<techinfoList.size();i++) {
            techInfo = (AcntTechinfo) techinfoList.get(i);
            parameterId.setCode(techInfo.getId().getCode());
            parameter = parameterLogic.loadByKey(parameterId);
            if(parameter!=null){
                value = value + "," + parameter.getName();
            }
        }
        if(value!=null){
            value = value.substring(5);
        }else {
            value = "";
        }
        return value;
    }
    /**
     * 获取技术资料并放入ViewBean
     * @param acnt
     * @param vb
     * @return
     */
    private VbDisplayInfo getTechInfo(Acnt acnt, VbDisplayInfo vb) {
        String infoValue = "";        
        Long rid = acnt.getRid();
        infoValue = getTechInfoVb(rid, Constant.PROJECT_TYPE);
        vb.setProjectType(infoValue);
        
       
        infoValue = getTechInfoVb(rid, Constant.PRODUCT_TYPE);
        vb.setProductType(infoValue);
        
      
        infoValue = getTechInfoVb(rid, Constant.PRODUCT_ATTRIBUTE);
        vb.setProductAttribute(infoValue);
        
       
        infoValue = getTechInfoVb(rid, Constant.WORK_ITEM);
        vb.setWorkItem(infoValue);;
        
       
        infoValue = getTechInfoVb(rid, Constant.TECHNICAL_DOMAIN);
        vb.setTechnicalDomain(infoValue);
        
       
        infoValue = getTechInfoVb(rid, Constant.ORIGINAL_LANGUAGE);
        vb.setOriginalLanguage(infoValue);
        
       
        infoValue = getTechInfoVb(rid, Constant.TRANSLATION_LANGUANGE);
        vb.setTranslationLanguage(infoValue);
        
       
        
        return vb;
    }
    /**
     * 获取技术资料详细资料
     * @param vb
     * @param rid
     * @param kind
     * @return
     */
    private VbDisplayInfo getTechInfoTextDetail(VbDisplayInfo vb,Long rid, String kind){

        IAccountService acntLogic = (IAccountService) this.getBean("AccountLogic");
        AcntTechinfo techInfo = new AcntTechinfo();
       if(kind.equals(Constant.DEVELOPENVIRONMENT)){
        techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, kind, Constant.JOBSYSTEM);
        vb.setDevJobSystem(techInfo.getDescription());
        
        techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, kind, Constant.DATABASE);
        vb.setDevDataBase(techInfo.getDescription());
        
        techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, kind, Constant.TOOL);
        vb.setDevTool(techInfo.getDescription());
        
        techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, kind, Constant.NETWORK);
        vb.setDevNetWork(techInfo.getDescription());
        
        techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, kind, Constant.PROGRAMLANGUAGE);
        vb.setDevProgramLanguage(techInfo.getDescription());
        
        techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, kind, Constant.OTHERS);
        vb.setDevOthers(techInfo.getDescription());
       } else if(kind.equals(Constant.TRNSLATEPUBLISHTYPE)) {
           techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, kind, Constant.JOBSYSTEM);
           vb.setTranJobSystem(techInfo.getDescription());
           
           techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, kind, Constant.DATABASE);
           vb.setTranDataBase(techInfo.getDescription());
           
           techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, kind, Constant.TOOL);
           vb.setTranTool(techInfo.getDescription());
           
           techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, kind, Constant.NETWORK);
           vb.setTranNetWork(techInfo.getDescription());
           
           techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, kind, Constant.PROGRAMLANGUAGE);
           vb.setTranProgramLanguage(techInfo.getDescription());
           
           techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, kind, Constant.OTHERS);
           vb.setTranOthers(techInfo.getDescription());
       }
       
        
        return vb;
    }
    /**
     * 获取客户资料
     * @param acnt
     * @param vb
     * @return
     */
    private VbDisplayInfo getCustomer(Acnt acnt, VbDisplayInfo vb){
        IAccountService acntLogic = (IAccountService)this.getBean("AccountLogic");
        AcntCustContactor acntCustContactor = acntLogic.loadByRidTypeFromAcntCustContactor(acnt.getRid(), IDtoAccount.CUSTOMER_CONTACTOR_CONTRACT);
        vb.setContract(acntCustContactor.getName());
        vb.setContractTel(acntCustContactor.getTelephone());
        vb.setContractEmail(acntCustContactor.getEmail());
        
        acntCustContactor = acntLogic.loadByRidTypeFromAcntCustContactor(acnt.getRid(), IDtoAccount.CUSTOMER_CONTACTOR_EXE);
        vb.setExecutive(acntCustContactor.getName());
        vb.setExecutiveTel(acntCustContactor.getTelephone());
        vb.setExecutiveEmail(acntCustContactor.getEmail());
        
        acntCustContactor = acntLogic.loadByRidTypeFromAcntCustContactor(acnt.getRid(), IDtoAccount.CUSTOMER_CONTACTOR_FINANCIAL);
        vb.setFinancial(acntCustContactor.getName());
        vb.setFinancialTel(acntCustContactor.getTelephone());
        vb.setFinancialEmail(acntCustContactor.getEmail());
        return vb;
        
    }
    /**
     * 获取人员资料
     * @param acnt
     * @param vb
     * @return
     */
    private VbDisplayInfo getPerson(Acnt acnt, VbDisplayInfo vb){
        IAccountService acntLogic = (IAccountService)this.getBean("AccountLogic");
        AcntPerson person = acntLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_PM);
        vb.setPMName(person.getName());
        
        person = acntLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_TC_SIGNER);
        vb.setTCSName(person.getName());
        
        person = acntLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_BD_MANAGER);
        vb.setBDMName(person.getName());
        
        person = acntLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_LEADER);
        vb.setLeaderName(person.getName());
        
        person = acntLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_SALES);
        vb.setSalesName(person.getName());
        
        person = acntLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_CUST_SERVICE_MANAGER);
        vb.setCustServiceManagerName(person.getName());
        
        person = acntLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_ENGAGE_MANAGER);
        vb.setEngageManagerName(person.getName());
        
        person = acntLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_APPLICANT);
        vb.setApplicantName(person.getName());
        
        
        return vb;
        
    }
   

}
