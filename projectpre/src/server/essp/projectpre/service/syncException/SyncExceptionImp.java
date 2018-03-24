/*
 * Created on 2007-10-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.projectpre.service.syncException;

import itf.webservices.IAccountWService;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import server.essp.projectpre.db.*;
import server.essp.projectpre.service.account.IAccountService;
import server.essp.projectpre.service.constant.Constant;
import server.essp.projectpre.service.parameter.IParameterService;
import server.essp.projectpre.service.site.ISiteService;
import server.essp.projectpre.ui.sync.VbSyncExceptionList;
import server.essp.syncproject.ISyncToHrms;
import server.framework.common.BusinessException;
import server.framework.logic.AbstractBusinessLogic;
import c2s.essp.common.account.IDtoAccount;

import com.wits.util.comDate;

public class SyncExceptionImp extends AbstractBusinessLogic
implements ISyncExceptionService{
    private IAccountService accountLogic;
    private IAccountWService finAccountService;
    private IAccountWService tsAccountService; 
    private IParameterService parameterLogic;
    private ISyncToHrms syncToHrms;
    private ISiteService siteService;
    public void setSiteService(ISiteService siteService) {
		this.siteService = siteService;
	}

	public void setSyncToHrms(ISyncToHrms syncToHrms) {
		this.syncToHrms = syncToHrms;
	}

	/**
     * 找到状态为激活的同步异常信息
     */
    private List loadByStatus(String status) throws BusinessException {
        List syncList = new ArrayList();
        try{
            Session session = this.getDbAccessor().getSession();
            Query query = session
             .createQuery("from PpSyncException as t"+
                          " where t.status=:status"+
                          " order by t.acntId")
            .setString("status", status);
            syncList = query.list();
            
        } catch ( Exception e) {
            throw new BusinessException("error.system.db", e);
        }
        return syncList;
    }
    
    /**
     * 根据状态列出状态为激活状态的记录
     */
    public List listSyncException(String status) throws BusinessException {
        List syncList = loadByStatus(status);
        List viewBean = new ArrayList();
        String IDFORMATER = "00000000";
        String newRid = null;
        DecimalFormat df = new DecimalFormat(IDFORMATER);
        if(syncList != null){
        for(int i=0;i<syncList.size();i++){         
            PpSyncException sync = (PpSyncException) syncList.get(i);        
            VbSyncExceptionList vbSync = new VbSyncExceptionList();     
            newRid = df.format(sync.getRid(), new StringBuffer(),
                    new FieldPosition(0)).toString();
            vbSync.setRid(String.valueOf(sync.getRid()));
            vbSync.setAcntRid(newRid);
            vbSync.setAcntId(sync.getAcntId());
            vbSync.setAcntName(sync.getAcntName());
            vbSync.setType(sync.getType());
            vbSync.setModel(sync.getModel());
            viewBean.add(vbSync);           
          }
        }
        return viewBean;
    }

    /**
     * 保存
     */
    public void save(PpSyncException syncExcp) throws BusinessException {
        PpSyncException sync = loadByRid(syncExcp.getRid());
        if(sync.getAcntId()== null){
            this.getDbAccessor().save(syncExcp);
        }else{
            this.getDbAccessor().update(syncExcp);
        }
    }

    /**
     * 更新状态
     */
    public void updateStaus(Long rid) throws BusinessException {
        try {
            PpSyncException syncExcp;
            syncExcp = loadByRid(rid);
            if(syncExcp.getAcntId() != null){
              syncExcp.setStatus(Constant.INACTIVE);
              this.getDbAccessor().update(syncExcp);
            }
        } catch (Exception e) {
            throw new BusinessException("error.system.db", e);
        }
    }
    
    /**
     * 新增或更新TIMESHEET或FINANCE
     */
    public void updateTimeSheetOrFinance(Long rid) throws BusinessException {
        try {
            PpSyncException sync = loadByRid(rid);
            Acnt acnt =accountLogic.loadByRid(sync.getAcntRid());
            Map map = new HashMap();
            map.put("projId", acnt.getAcntId());
            map.put("projName", acnt.getAcntFullName());
            map.put("nickName", acnt.getAcntName());
            map.put("manager", acnt.getCostAttachBd());
            AcntPerson person = accountLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_LEADER);
            if(person != null){
            map.put("leader", person.getLoginId());
            }
            map.put("costDept", acnt.getExecUnitId());
            person = accountLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_TC_SIGNER);
            if(person != null){
            map.put("tcSigner", person.getLoginId());
            }
            map.put("planFrom", comDate.dateToString(acnt.getAcntPlannedStart(), "yyyy-MM-dd"));
            map.put("planTo", comDate.dateToString(acnt.getAcntPlannedFinish(), "yyyy-MM-dd"));
            map.put("custShort",acnt.getCustomerId());
            map.put("estManmonth", acnt.getEstManmonth());
            map.put("achieveBelong", acnt.getAchieveBelong());
            person = accountLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_SALES);     
            if(person != null){
            map.put("sales", person.getLoginId());
            }
            map.put("actuFrom", comDate.dateToString(acnt.getAcntActualStart(),"yyyy-MM-dd"));
            map.put("actuTo", comDate.dateToString(acnt.getAcntActualFinish(),"yyyy-MM-dd"));
            map.put("manMonth", acnt.getActualManmonth());
            map.put("productName", acnt.getProductName());
            map.put("projectDesc", acnt.getAcntBrief());
            person = accountLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_APPLICANT);  
            if(person != null){
            map.put("applicant", person.getLoginId());
            }
            person = accountLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_BD_MANAGER);
            if(person != null){
            map.put("divisionManager", person.getLoginId());
            }
            person = accountLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_PM);
            if(person != null){
            map.put("projectManager", person.getName());
            map.put("PM", person.getLoginId());
            }
            map.put("projectExecUnit", acnt.getExecUnitId());
            person = accountLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_CUST_SERVICE_MANAGER);
            if(person != null){
            map.put("custServiceManager", person.getLoginId());
            }
            person = accountLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_ENGAGE_MANAGER);
            if(person != null){
            map.put("engageManager", person.getLoginId());
            }
            map.put("bizSource", acnt.getBizSource());
            map.put("masterProjects", acnt.getContractAcntId());
            map.put("projectProperty", acnt.getAcntAttribute());
            map.put("estimatedWords", acnt.getEstSize());
            map = setTechinfo(map, acnt.getRid());
            map = getTechInfoTextList(map, acnt.getRid());
            map = getTechInfoTextDetail(map,acnt.getRid(),server.essp.projectpre.service.constant.Constant.DEVELOPENVIRONMENT);
            map = getTechInfoTextDetail(map,acnt.getRid(),server.essp.projectpre.service.constant.Constant.TRNSLATEPUBLISHTYPE);
            map = getCustContactor(map, acnt.getRid());
            
            if(sync.getModel().equals("Finance") && sync.getType().equals("Apply")){
                finAccountService.addAccount(map);
            }
            if(sync.getModel().equals("Finance") && sync.getType().equals("Update")){
                finAccountService.updateAccount(map);
            }
            if(sync.getModel().equals("Finance") && sync.getType().equals("Close")){
      //          finAccountService.closeAccount(acnt.getAcntId(), map);
            }
            if(sync.getModel().equals("Finance") && sync.getType().equals("Reopen")){
       //         finAccountService.reopenOrCloseAccount(acnt.getAcntId(), "N");
            }
            if(sync.getModel().equals("Finance") && sync.getType().equals("DClose")){
       //         finAccountService.reopenOrCloseAccount(acnt.getAcntId(), "Y");
            }
            if(sync.getModel().equals("TimeSheet") && sync.getType().equals("Apply")){
                tsAccountService.addAccount(map);
            }
            if(sync.getModel().equals("TimeSheet") && sync.getType().equals("Update")){
                tsAccountService.updateAccount(map);
            }
            if(sync.getModel().equals("TimeSheet") && sync.getType().equals("Close")){
        //        tsAccountService.closeAccount(acnt.getAcntId(), map);
            }
            if(sync.getModel().equals("TimeSheet") && sync.getType().equals("Reopen")){
    //            tsAccountService.reopenOrCloseAccount(acnt.getAcntId(), "N");
            }
            if(sync.getModel().equals("TimeSheet") && sync.getType().equals("DClose")){
       //         tsAccountService.reopenOrCloseAccount(acnt.getAcntId(), "Y");
            }
            if(sync.getModel().equals("Hrms") && sync.getType().equals("Apply")) {
            	Site site = siteService.loadByCode(acnt.getExecSite());
                String siteName = "";
                if(site != null) {
                	siteName = site.getSiteName();
                }
            	syncToHrms.addProjcet(acnt, siteName, (String)map.get("PM"));
            }
            if(sync.getModel().equals("Hrms") && sync.getType().equals("Update")) {
            	Site site = siteService.loadByCode(acnt.getExecSite());
                String siteName = "";
                if(site != null) {
                	siteName = site.getSiteName();
                }
                syncToHrms.updateProject(acnt, siteName, (String)map.get("PM"));
            }
            if(sync.getModel().equals("Hrms") && sync.getType().equals("Close")) {
            	Site site = siteService.loadByCode(acnt.getExecSite());
                String siteName = "";
                if(site != null) {
                	siteName = site.getSiteName();
                }
                syncToHrms.closeProject(acnt, siteName, (String)map.get("PM"));
            }
            updateStaus(rid);
        } catch ( Exception e) {
            throw new BusinessException("error.system.db", e);
        }
        
    }
    
    /**
     * 将查询到的客户资料放入MAP中,作为传到财务系统的数据
     * @param map
     * @param rid
     * @return
     */
    private Map getCustContactor(Map map,Long rid){
        AcntCustContactor acntCustContactor = new AcntCustContactor();
         
        acntCustContactor = accountLogic.loadByRidTypeFromAcntCustContactor(rid, IDtoAccount.CUSTOMER_CONTACTOR_CONTRACT);
        if(acntCustContactor != null){
        map.put("ccontact", acntCustContactor.getName());
        map.put("ccontTel", acntCustContactor.getTelephone());
        map.put("ccontEmail", acntCustContactor.getEmail());
        }   
        
        acntCustContactor = accountLogic.loadByRidTypeFromAcntCustContactor(rid, IDtoAccount.CUSTOMER_CONTACTOR_EXE);
        if(acntCustContactor != null){
        map.put("pcontact", acntCustContactor.getName());
        map.put("pcontTel", acntCustContactor.getTelephone());
        map.put("pcontEmail", acntCustContactor.getEmail());
        }
 
        acntCustContactor = accountLogic.loadByRidTypeFromAcntCustContactor(rid, IDtoAccount.CUSTOMER_CONTACTOR_FINANCIAL);
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
          AcntTechinfo techInfo = new AcntTechinfo();
         if(kind.equals(server.essp.projectpre.service.constant.Constant.DEVELOPENVIRONMENT)){
          techInfo = accountLogic.loadByRidKindCodeFromTechInfo(rid, kind, server.essp.projectpre.service.constant.Constant.JOBSYSTEM);
          if(techInfo != null){
          map.put("devEnvOs", techInfo.getDescription());
          }
          
          techInfo = accountLogic.loadByRidKindCodeFromTechInfo(rid, kind, server.essp.projectpre.service.constant.Constant.DATABASE);
          if(techInfo != null){
          map.put("devEnvDb", techInfo.getDescription());
          }
          
          techInfo = accountLogic.loadByRidKindCodeFromTechInfo(rid, kind, server.essp.projectpre.service.constant.Constant.TOOL);
          if(techInfo != null){
          map.put("devEnvTool", techInfo.getDescription());
          }
          
          techInfo = accountLogic.loadByRidKindCodeFromTechInfo(rid, kind, server.essp.projectpre.service.constant.Constant.NETWORK);
          if(techInfo != null){
          map.put("devEnvNetwork", techInfo.getDescription());
          }
          
          techInfo = accountLogic.loadByRidKindCodeFromTechInfo(rid, kind, server.essp.projectpre.service.constant.Constant.PROGRAMLANGUAGE);
          if(techInfo != null){
          map.put("devEnvLang", techInfo.getDescription());
          }
          techInfo = accountLogic.loadByRidKindCodeFromTechInfo(rid, kind, server.essp.projectpre.service.constant.Constant.OTHERS);
          if(techInfo != null){
          map.put("devEnvOther", techInfo.getDescription());
          }
         } else if(kind.equals(server.essp.projectpre.service.constant.Constant.TRNSLATEPUBLISHTYPE)) {
             techInfo = accountLogic.loadByRidKindCodeFromTechInfo(rid, kind, server.essp.projectpre.service.constant.Constant.JOBSYSTEM);
             if(techInfo != null){
             map.put("tranEnvOs", techInfo.getDescription());
             }
             
             techInfo = accountLogic.loadByRidKindCodeFromTechInfo(rid, kind, server.essp.projectpre.service.constant.Constant.DATABASE);
             if(techInfo != null){
             map.put("tranEnvDb", techInfo.getDescription());
             }
             
             techInfo = accountLogic.loadByRidKindCodeFromTechInfo(rid, kind, server.essp.projectpre.service.constant.Constant.TOOL);
             if(techInfo != null){
             map.put("tranEnvTool", techInfo.getDescription());
             }
             
             techInfo = accountLogic.loadByRidKindCodeFromTechInfo(rid, kind, server.essp.projectpre.service.constant.Constant.NETWORK);
             if(techInfo != null){
             map.put("tranEnvNetwork", techInfo.getDescription());
             }
             
             techInfo = accountLogic.loadByRidKindCodeFromTechInfo(rid, kind, server.essp.projectpre.service.constant.Constant.PROGRAMLANGUAGE);
             if(techInfo != null){
             map.put("tranEnvLang", techInfo.getDescription());
             }
             
             techInfo = accountLogic.loadByRidKindCodeFromTechInfo(rid, kind, server.essp.projectpre.service.constant.Constant.OTHERS);
             if(techInfo != null){
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
        techInfo = accountLogic.loadByRidKindCodeFromTechInfo(rid, server.essp.projectpre.service.constant.Constant.HARDREQ, server.essp.projectpre.service.constant.Constant.HARDREQ);
        if(techInfo != null){
        map.put("hardWareReq", techInfo.getDescription());    
        }
        
        techInfo = accountLogic.loadByRidKindCodeFromTechInfo(rid, server.essp.projectpre.service.constant.Constant.SOFTREQ, server.essp.projectpre.service.constant.Constant.SOFTREQ);
        if(techInfo != null){
        map.put("softWareReq", techInfo.getDescription());              
        }
        return map;
    }
    
    /**
     * 根据ACCOUNT_RID得到技术资料
     * @param map
     * @param rid
     * @return
     */
    private Map setTechinfo(Map map, Long rid){
        String str = "";
        str = getTechInfoVb(rid, server.essp.projectpre.service.constant.Constant.PROJECT_TYPE);
        if(str != null){
        map.put("projectType",str);
        map.put("projTypeNo", str);
        }
        str = getTechInfoName(rid);
        if(str != null){
        map.put("AccountTypeName", str);
        }
        
        str = getTechInfoVb(rid, server.essp.projectpre.service.constant.Constant.PRODUCT_TYPE);
        if(str != null){
        map.put("productType", str);
        }
       
        str = getTechInfoVb(rid, server.essp.projectpre.service.constant.Constant.PRODUCT_ATTRIBUTE);
        if(str != null){
        map.put("productProperty", str);
        }
       
        str = getTechInfoVb(rid, server.essp.projectpre.service.constant.Constant.WORK_ITEM);
        if(str != null){
        map.put("workItem", str);
        }
      
        str = getTechInfoVb(rid, server.essp.projectpre.service.constant.Constant.TECHNICAL_DOMAIN);
        if(str != null){
        map.put("skillDomain", str);
        }
      
        str = getTechInfoVb(rid, server.essp.projectpre.service.constant.Constant.ORIGINAL_LANGUAGE);
        if(str != null){
        map.put("originalLan", str);
        }
        
        str = getTechInfoVb(rid, server.essp.projectpre.service.constant.Constant.TRANSLATION_LANGUANGE);
        if(str != null){
        map.put("transLan", str);
        }
        return map;
    }
    
    /**
     * 根据专案的RID查出相关技术资料的代码
     * @param rid
     * @param type
     * @return String
     */
    private String getTechInfoVb(Long rid, String type) {
        AcntTechinfo techInfo = new AcntTechinfo();
        String value = null;
        List techinfoList = accountLogic.listByRidKindFromTechInfo(rid, type);
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
     * 根据专案的RID获取专案类型的名称
     * @param rid
     * @return String
     */
    private String getTechInfoName(Long rid){
        String name = "";
        List techinfoList = accountLogic.listByRidKindFromTechInfo(rid, Constant.PROJECT_TYPE);
        if(techinfoList!=null&&techinfoList.size()>0){
            AcntTechinfo techInfo = (AcntTechinfo) techinfoList.get(0);
            Parameter para = parameterLogic.loadByKindCode(
                    Constant.PROJECT_TYPE, techInfo.getId().getCode());
            name = para.getName();
        }
        return name;
    }
    
    /**
     * 根据RID得到异常同步的记录
     * @param rid
     * @return
     */
    private PpSyncException loadByRid(Long rid) {
        Session session;
        List qList = null;
        try {
           session = this.getDbAccessor().getSession();
           Query query = session.createQuery("from PpSyncException as a where a.rid=:rid order by a.rid")
                        .setLong("rid",rid);
           qList = query.list();
        } catch (Exception e) {
            throw new BusinessException("error.system.db", e);
        }
        
        PpSyncException sync = new PpSyncException();
        if(qList != null && qList.size() >0){
            sync = (PpSyncException)qList.get(0);
        }
        return sync;
    }

    public void setAccountLogic(IAccountService accountLogic) {
        this.accountLogic = accountLogic;
    }


    public void setFinAccountService(IAccountWService finAccountService) {
        this.finAccountService = finAccountService;
    }


    public void setParameterLogic(IParameterService parameterLogic) {
        this.parameterLogic = parameterLogic;
    }

    public void setTsAccountService(IAccountWService tsAccountService) {
        this.tsAccountService = tsAccountService;
    }


   

   

   
}
