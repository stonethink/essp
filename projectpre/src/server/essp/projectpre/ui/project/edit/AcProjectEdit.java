/*
 * Created on 2007-4-23
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.projectpre.ui.project.edit;

import itf.webservices.IAccountWService;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.*;
import server.essp.projectpre.service.account.IAccountService;
import server.essp.projectpre.service.constant.Constant;
import server.essp.projectpre.service.customer.ICustomerService;
import server.essp.projectpre.service.log.ILogService;
import server.essp.projectpre.service.parameter.IParameterService;
import server.essp.projectpre.service.site.ISiteService;
import server.essp.projectpre.service.syncException.ISyncExceptionService;
import server.essp.syncproject.ISyncToHrms;
import server.framework.common.BusinessException;
import c2s.dto.DtoUtil;
import c2s.dto.TransactionData;
import c2s.essp.common.account.IDtoAccount;
import c2s.essp.common.user.DtoUser;
import com.wits.util.comDate;

/**
 * 专案资料修改作业
 * @author ZWH
 */
public class AcProjectEdit extends AbstractESSPAction {
    /**
     * 专案资料修改操作
     *    1.更新专案相关资料
     *    2.更新人员相关资料
     *    3.将修改的数据同步到财务数据库中
     * @param request
     * @param response
     * @param data
     * @throws BusinessException
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {
    	DtoUser user = (DtoUser)request.getSession().getAttribute(DtoUser.SESSION_USER);
        String userName = user.getUserName();
        AfProjectEdit af = (AfProjectEdit)this.getForm();
        IAccountService acntLogic = (IAccountService)this.getBean("AccountLogic");
        Acnt acnt = acntLogic.loadByAcntId(af.getAcntId(),"1");
        String execSite = acnt .getExecSite(); 
        String acntNameBefore = acnt.getAcntName();
        String acntFullNameBefore = acnt.getAcntFullName();
        AcntPerson personPM = acntLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_PM);
        String pmIdBefore = personPM.getLoginId();
        Date plannedStartBefore = acnt.getAcntPlannedStart();
        Date actualStartBefore = acnt.getAcntActualStart();
        try {
            DtoUtil.copyProperties(acnt, af);
        } catch (Exception e) {
            e.printStackTrace();
        }     
        acnt.setExecSite(execSite);
        acnt.setAcntPlannedStart(comDate.toDate(af.getAcntPlannedStart()));    
        acnt.setAcntPlannedFinish(comDate.toDate(af.getAcntPlannedFinish()));    
        acnt.setAcntActualStart(comDate.toDate(af.getAcntActualStart()));     
        acnt.setAcntActualFinish(comDate.toDate(af.getAcntActualFinish()));
        if(af.getExecUnitName()==null||"".equals(af.getExecUnitName()) || af.getExecUnitName().equals("-1")) {
            acnt.setExecUnitId("");
        } else{
            acnt.setExecUnitId(af.getExecUnitName().substring(0, af.getExecUnitName().indexOf("---")));
            
        }
        updatePerson(acnt, acntLogic, af);
        acntLogic.update(acnt);
        this.getRequest().setAttribute("rid", acnt.getRid());
        this.getRequest().setAttribute("opType", "save");
        syncoData(acnt, acntLogic, request, acntNameBefore, acntFullNameBefore,
        		pmIdBefore, plannedStartBefore, actualStartBefore);
        ILogService logicLog=(ILogService)this.getBean("LogLogic");
        PPLog log = new PPLog();
        log.setAcntId(acnt.getAcntId());
        log.setApplicationType(Constant.PROJECTEDIT);
        log.setDataType(Constant.LOG_PROJECT);
        log.setMailReceiver("");
        log.setOperation("Edit");
        log.setOperationDate(new Date());
        log.setOperator(userName);
        logicLog.save(log);
    }
    /**
     * 同步数据
     * @param acnt
     * @param acntLogic
     */
    private void syncoData(Acnt acnt, IAccountService acntLogic,HttpServletRequest request,
    		String acntNameBefore, String acntFullNameBefore, String pmIdBefore,
    		Date plannedStartBefore, Date actualStartBefore){
//      数据同步（财务_新增, TimeSheet）
        IAccountWService finLogic = (IAccountWService)this.getBean("FinAccountService");
        IAccountWService tsLogic = (IAccountWService)this.getBean("TSAccountService");
        ISyncToHrms syncToHrms = (ISyncToHrms)this.getBean("SyncToHrms");
        DtoUser user = (DtoUser)request.getSession().getAttribute(DtoUser.SESSION_USER);
        Map map = new HashMap();
        map.put("projId", acnt.getAcntId());
        map.put("projName", acnt.getAcntFullName());
        map.put("nickName", acnt.getAcntName());
        map.put("manager", acnt.getCostAttachBd());
        AcntPerson person = acntLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_LEADER);
        if(person!=null){
        map.put("leader", person.getLoginId());
        }
        map.put("costDept", acnt.getExecUnitId());
        person = acntLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_TC_SIGNER);
        if(person!=null){
        map.put("tcSigner", person.getLoginId());
        }
        map.put("planFrom", comDate.dateToString(acnt.getAcntPlannedStart(), "yyyy-MM-dd"));
        map.put("planTo", comDate.dateToString(acnt.getAcntPlannedFinish(), "yyyy-MM-dd"));
        ICustomerService custLogic = (ICustomerService)this.getBean("CustomerLogic");
        Customer customer = custLogic.loadByCustomerId(acnt.getCustomerId());
        if(customer!=null){
        map.put("custShort", customer.getShort_());
        }
        map.put("estManmonth", acnt.getEstManmonth());
        map.put("achieveBelong", acnt.getAchieveBelong());
        
        person = acntLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_SALES);     
        if(person!=null){
        map.put("sales", person.getLoginId());
        }
        map.put("actuFrom", comDate.dateToString(acnt.getAcntActualStart(),"yyyy-MM-dd"));
        map.put("actuTo", comDate.dateToString(acnt.getAcntActualFinish(),"yyyy-MM-dd"));
        map.put("manMonth", acnt.getActualManmonth());
        map.put("productName", acnt.getProductName());
        map.put("projectDesc", acnt.getAcntBrief());
        person = acntLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_APPLICANT);  
        if(person!=null){
        map.put("applicant", person.getLoginId());
        }
        person = acntLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_BD_MANAGER);
        if(person!=null){
        map.put("divisionManager", person.getLoginId());
        }
        person = acntLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_PM);
        if(person!=null){
        //根据新需求专案经理字段放入员工的名字 
        //将PM的ID放入另一KEY中 add by wenhaizheng 20080305
        //结转到财务的PM还是存放PM的ID mark by wenhaizheng 20080509
        map.put("projectManager", person.getLoginId());
        map.put("PM", person.getLoginId());
        }
        map.put("projectExecUnit", acnt.getExecUnitId());
        person = acntLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_CUST_SERVICE_MANAGER);
        if(person!=null){
        map.put("custServiceManager", person.getLoginId());
        }
        person = acntLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_ENGAGE_MANAGER);
        if(person!=null){
        map.put("engageManager", person.getLoginId());
        }
        map.put("bizSource", acnt.getBizSource());
        map.put("masterProjects", acnt.getContractAcntId());
        map.put("projectProperty", acnt.getAcntAttribute());
        map.put("estimatedWords", acnt.getEstSize());
        map.put("UsePrimavera", acnt.getPrimaveraAdapted());
        map.put("Billable", acnt.getBillingBasis());
        map.put("AchieveBelong", acnt.getAchieveBelong());
        map = setTechinfo(map, acnt.getRid());
        map = getTechInfoTextList(map, acnt.getRid());
        map = getTechInfoTextDetail(map,acnt.getRid(),server.essp.projectpre.service.constant.Constant.DEVELOPENVIRONMENT);
        map = getTechInfoTextDetail(map,acnt.getRid(),server.essp.projectpre.service.constant.Constant.TRNSLATEPUBLISHTYPE);
        map = getCustContactor(map, acnt.getRid());
        try{
            finLogic.updateAccount(map);
          }catch (BusinessException e){
              //如果同步失败则向异常同步信息记录表中插入数据
             String errMessge = e.getErrorCode();           
             if(errMessge.equals("UPDATE Finance ERROR")||errMessge.
                         equals("UPDATE Finance Rollback ERROR")){        
               updateSyncException(acnt,"FinAccountWServiceImpl",errMessge,user.getUserLoginId());
               request.setAttribute("Message","Carry forward fail!");
             }  
          }
          try{
        	  if(!"V".equalsIgnoreCase(acnt.getExecSite())) {
        		  tsLogic.updateAccount(map);
        	  }
          }catch(BusinessException e){
              String errMessge = e.getErrorCode();    
              updateSyncException(acnt,"SyncAccountImp",errMessge,user.getUserLoginId());
              request.setAttribute("Message","Carry forward fail!");  
          }
          ISiteService siteLogic = (ISiteService)this.getBean("AreaCodeLogic");
          Site site = siteLogic.loadByCode(acnt.getExecSite());
          String siteName = "";
          if(site != null) {
          	siteName = site.getSiteName();
          }
          if(siteName.equalsIgnoreCase("TP") && 
        		  isChanged(acnt, acntNameBefore, acntFullNameBefore, pmIdBefore,
        				  (String)map.get("PM"), plannedStartBefore, actualStartBefore)) {
        	  try{
        		  syncToHrms.updateProject(acnt, siteName, (String)map.get("PM"));
        	  } catch (BusinessException e) {
                  updateSyncException(acnt,"SyncToHrms",e.getErrorMsg(),user.getUserLoginId());
                  request.setAttribute("Message","Carry forward fail!"); 
        	  }
          }
    }
    /**
     * 判断哪些需要更新
     * @param acnt
     * @param acntNameBefore
     * @param acntFullNameBefore
     * @param pmIdBefore
     * @param pmId
     * @param plannedStartBefore
     * @param actualStartBefore
     * @return
     */
    private boolean isChanged(Acnt acnt,String acntNameBefore, String acntFullNameBefore, String pmIdBefore, 
    		String pmId, Date plannedStartBefore, Date actualStartBefore) {
    	if(!acntNameBefore.equals(acnt.getAcntName())) {
    		return true;
    	}
    	if(!acntFullNameBefore.equals(acnt.getAcntFullName())){
    		return true;
    	} 
    	if(!pmIdBefore.equals(pmId)) {
    		return true;
    	}
    	if(acnt.getAcntActualStart() == null && 
    			plannedStartBefore.compareTo(acnt.getAcntPlannedStart()) != 0) {
    		return true;
    	}
    	if(actualStartBefore == null && acnt.getAcntActualStart() != null) {
    		return true;
    	}
    	if(actualStartBefore != null && acnt.getAcntActualStart() == null) {
    		return true;
    	}
    	if(actualStartBefore != null && acnt.getAcntActualStart() != null
    	  && actualStartBefore.compareTo(acnt.getAcntActualStart()) != 0) {
    		return true;
    	}
    	return false;
    }
    /**
     * 截转失败时向同步异常信息表中插入一笔数据
     * @param acnt
     * @param impClassName
     * @param errorMessge
     * @param loginId
     */
    private void updateSyncException(Acnt acnt,String impClassName,
            String errorMessge,String loginId){
        ISyncExceptionService syncLogic = (ISyncExceptionService) this.getBean("SyncExceptionLogic");
        PpSyncException syncExcption = new PpSyncException();
        syncExcption.setAcntRid(acnt.getRid());
        syncExcption.setAcntId(acnt.getAcntId());
        syncExcption.setAcntName(acnt.getAcntName());
        syncExcption.setImpClassName(impClassName);
        if(impClassName.equals("FinAccountWServiceImpl")){
            syncExcption.setModel("Finance");
        } else if("SyncAccountImp".equals(impClassName)){
            syncExcption.setModel("TimeSheet");
        } else if("SyncToHrms".equals(impClassName)) {
        	syncExcption.setModel("Hrms");
        }
        syncExcption.setStatus(Constant.ACTIVE);
        syncExcption.setErrorMessage(errorMessge);
        syncExcption.setType("Update");
        syncExcption.setRcu(loginId);
        syncExcption.setRct(new Date());
        syncLogic.save(syncExcption);
    }
    
    /**
     * 将查询到的客户资料放入MAP中,作为传到财务系统的数据
     * @param map
     * @param rid
     * @return
     */
    private Map getCustContactor(Map map,Long rid){
        AcntCustContactor acntCustContactor = new AcntCustContactor();
        IAccountService acntLogic = (IAccountService) this.getBean("AccountLogic");
        acntCustContactor = acntLogic.loadByRidTypeFromAcntCustContactor(rid, IDtoAccount.CUSTOMER_CONTACTOR_CONTRACT);
        if(acntCustContactor != null){
        map.put("ccontact", acntCustContactor.getName());
        map.put("ccontTel", acntCustContactor.getTelephone());
        map.put("ccontEmail", acntCustContactor.getEmail());
        }
      
        acntCustContactor = acntLogic.loadByRidTypeFromAcntCustContactor(rid, IDtoAccount.CUSTOMER_CONTACTOR_EXE);
        if(acntCustContactor != null){
        map.put("pcontact", acntCustContactor.getName());
        map.put("pcontTel", acntCustContactor.getTelephone());
        map.put("pcontEmail", acntCustContactor.getEmail());
        }
 
        acntCustContactor = acntLogic.loadByRidTypeFromAcntCustContactor(rid, IDtoAccount.CUSTOMER_CONTACTOR_FINANCIAL);
        if(acntCustContactor != null){
        map.put("icontact", acntCustContactor.getName());
        map.put("icontTel", acntCustContactor.getTelephone());
        map.put("icontEmail", acntCustContactor.getEmail());
        }
        
        return map;
    }
    /**
     * 将查询到的技术资料文本详细资料放入MAP中,作为传到财务系统的数据
     * @param map
     * @param rid
     * @param kind
     * @return
     */
    private Map getTechInfoTextDetail(Map map,Long rid, String kind){
      IAccountService acntLogic = (IAccountService) this.getBean("AccountLogic");
      AcntTechinfo techInfo = new AcntTechinfo();
     if(kind.equals(server.essp.projectpre.service.constant.Constant.DEVELOPENVIRONMENT)){
      techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, kind, server.essp.projectpre.service.constant.Constant.JOBSYSTEM);
      if(techInfo!=null){
      map.put("devEnvOs", techInfo.getDescription());
      }
      
      techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, kind, server.essp.projectpre.service.constant.Constant.DATABASE);
      if(techInfo!=null){
      map.put("devEnvDb", techInfo.getDescription());
      }
      
      techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, kind, server.essp.projectpre.service.constant.Constant.TOOL);
      if(techInfo!=null){
      map.put("devEnvTool", techInfo.getDescription());
      }
      
      techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, kind, server.essp.projectpre.service.constant.Constant.NETWORK);
      if(techInfo!=null){
      map.put("devEnvNetwork", techInfo.getDescription());
      }
      
      techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, kind, server.essp.projectpre.service.constant.Constant.PROGRAMLANGUAGE);
      if(techInfo!=null){
      map.put("devEnvLang", techInfo.getDescription());
      }
      
      techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, kind, server.essp.projectpre.service.constant.Constant.OTHERS);
      if(techInfo!=null){
      map.put("devEnvOther", techInfo.getDescription());
      }
     } else if(kind.equals(server.essp.projectpre.service.constant.Constant.TRNSLATEPUBLISHTYPE)) {
         techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, kind, server.essp.projectpre.service.constant.Constant.JOBSYSTEM);
         if(techInfo!=null){
         map.put("tranEnvOs", techInfo.getDescription());
         }
         
         techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, kind, server.essp.projectpre.service.constant.Constant.DATABASE);
         if(techInfo!=null){
         map.put("tranEnvDb", techInfo.getDescription());
         }
         
         techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, kind, server.essp.projectpre.service.constant.Constant.TOOL);
         if(techInfo!=null){
         map.put("tranEnvTool", techInfo.getDescription());
         }
         
         techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, kind, server.essp.projectpre.service.constant.Constant.NETWORK);
         if(techInfo!=null){
         map.put("tranEnvNetwork", techInfo.getDescription());
         }
         
         techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, kind, server.essp.projectpre.service.constant.Constant.PROGRAMLANGUAGE);
         if(techInfo!=null){
         map.put("tranEnvLang", techInfo.getDescription());
         }
         
         techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, kind, server.essp.projectpre.service.constant.Constant.OTHERS);
         if(techInfo!=null){
         map.put("tranEnvOther", techInfo.getDescription());
         }
     }
         return map;
  }
    /**
     * 将查询到的技术资料文本放入MAP中,作为传到财务系统的数据
     * @param map
     * @param rid
     * @return
     */
    private Map getTechInfoTextList(Map map,Long rid) {
        AcntTechinfo techInfo = new AcntTechinfo();
        IAccountService acntLogic = (IAccountService) this.getBean("AccountLogic");
      
        techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, server.essp.projectpre.service.constant.Constant.HARDREQ, server.essp.projectpre.service.constant.Constant.HARDREQ);
        if(techInfo!=null){
        map.put("hardWareReq", techInfo.getDescription());
        }
      
        techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, server.essp.projectpre.service.constant.Constant.SOFTREQ, server.essp.projectpre.service.constant.Constant.SOFTREQ);
        if(techInfo!=null){
        map.put("softWareReq", techInfo.getDescription());
        }
        
        return map;
    }
    /**
     * 根据专案的RID查出相关技术资料的代码
     * @param rid
     * @param type
     * @return
     */
    private String getTechInfoVb(Long rid, String type) {
        AcntTechinfo techInfo = new AcntTechinfo();
        String value = null;
        IAccountService acntLogic = (IAccountService) this.getBean("AccountLogic");
        Acnt acnt = acntLogic.loadByRid(rid);
        List techinfoList = acntLogic.listByRidKindFromTechInfo(acnt.getRid(), type);
        for(int i=0;i<techinfoList.size();i++) {
            techInfo = (AcntTechinfo) techinfoList.get(i);
            value = value + "," + techInfo.getId().getCode();
        }
        if(value!=null){
            value = value.substring(5);
        }else {
            value = "";
        }
        return value;
    }
    /**
     * 将查询到的技术资料代码按类型放入MAP中,作为传到财务系统的数据
     * @param map
     * @param rid
     * @return
     */
    private Map setTechinfo(Map map, Long rid){
        String str = "";
        str = getTechInfoVb(rid, server.essp.projectpre.service.constant.Constant.PROJECT_TYPE);
        if(str!=null){
        map.put("projectType",str);
        map.put("projTypeNo", str);
        }
        
        str = getTechInfoName(rid);
        if(str!=null){
        map.put("AccountTypeName", str);
        }
        
        str = getTechInfoVb(rid, server.essp.projectpre.service.constant.Constant.PRODUCT_TYPE);
        if(str!=null){
        map.put("productType", str);
        }
       
        str = getTechInfoVb(rid, server.essp.projectpre.service.constant.Constant.PRODUCT_ATTRIBUTE);
        if(str!=null){
        map.put("productProperty", str);
        }
        
       
        str = getTechInfoVb(rid, server.essp.projectpre.service.constant.Constant.WORK_ITEM);
        if(str!=null){
        map.put("workItem", str);
        }
        
      
        str = getTechInfoVb(rid, server.essp.projectpre.service.constant.Constant.TECHNICAL_DOMAIN);
        if(str!=null){
        map.put("skillDomain", str);
        }
        
      
        str = getTechInfoVb(rid, server.essp.projectpre.service.constant.Constant.ORIGINAL_LANGUAGE);
        if(str!=null){
        map.put("originalLan", str);
        }
      
        str = getTechInfoVb(rid, server.essp.projectpre.service.constant.Constant.TRANSLATION_LANGUANGE);
        if(str!=null){
        map.put("transLan", str);
        }
        return map;
    }
    /**
     * 根据专案的RID获取专案类型的名称
     * @param rid
     * @return
     */
    private String getTechInfoName(Long rid){
    	String name = "";
    	IAccountService acntLogic = (IAccountService) this.getBean("AccountLogic");
    	IParameterService paraLogic = (IParameterService)this.getBean("ParameterLogic");
        List techinfoList = acntLogic.listByRidKindFromTechInfo(rid, Constant.PROJECT_TYPE);
        if(techinfoList!=null&&techinfoList.size()>0){
        	AcntTechinfo techInfo = (AcntTechinfo) techinfoList.get(0);
        	Parameter para = paraLogic.loadByKindCode(
        			Constant.PROJECT_TYPE, techInfo.getId().getCode());
            name = para.getName();
        }
    	return name;
    }
    /**
     * 更新人员相关信息
     * @param acnt
     * @param acntLogic
     * @param af
     */
    private void updatePerson(Acnt acnt, IAccountService acntLogic, AfProjectEdit af){
        AcntPerson person = null;
        person = acntLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_PM);
        person.setName(af.getPMName());
        person.setLoginId(af.getPMId());
        person.setDomain(af.getPMDomain());
        acntLogic.updateAcntPerson(person);
        
        person = acntLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_APPLICANT);
        person.setName(af.getApplicantName());
        person.setLoginId(af.getAPId());
        person.setDomain(af.getAPDomain());
        acntLogic.updateAcntPerson(person);
        
        person = acntLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_TC_SIGNER);
        person.setName(af.getTCSName());
        person.setLoginId(af.getTCSId());
        person.setDomain(af.getTCSDomain());
        acntLogic.updateAcntPerson(person);
        
        person = acntLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_BD_MANAGER);
        person.setName(af.getBDMName());
        person.setLoginId(af.getBDId());
        person.setDomain(af.getBDDomain());
        acntLogic.updateAcntPerson(person);
        
        person = acntLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_LEADER);
        person.setName(af.getLeaderName());
        person.setLoginId(af.getLeaderId());
        person.setDomain(af.getLeaderDomain());
        acntLogic.updateAcntPerson(person);
        
        person = acntLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_SALES);
        person.setName(af.getSalesName());
        person.setLoginId(af.getSalesId());
        person.setDomain(af.getSalesDomain());
        acntLogic.updateAcntPerson(person);
    }

}
