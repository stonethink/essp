package server.essp.projectpre.ui.project.check;

import itf.webservices.IAccountWService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Acnt;
import server.essp.projectpre.db.AcntApp;
import server.essp.projectpre.db.AcntCustContactor;
import server.essp.projectpre.db.AcntCustContactorApp;
import server.essp.projectpre.db.AcntPerson;
import server.essp.projectpre.db.AcntPersonApp;
import server.essp.projectpre.db.AcntTechinfo;
import server.essp.projectpre.db.AcntTechinfoApp;
import server.essp.projectpre.db.Bd;
import server.essp.projectpre.db.PPLog;
import server.essp.projectpre.db.Parameter;
import server.essp.projectpre.db.ParameterId;
import server.essp.projectpre.db.PpSyncException;
import server.essp.projectpre.db.Site;
import server.essp.projectpre.service.account.IAccountService;
import server.essp.projectpre.service.accountapplication.IAccountApplicationService;
import server.essp.projectpre.service.bd.IBdService;
import server.essp.projectpre.service.constant.Constant;
import server.essp.projectpre.service.log.ILogService;
import server.essp.projectpre.service.parameter.IParameterService;
import server.essp.projectpre.service.sendMail.ISendMailService;
import server.essp.projectpre.service.site.ISiteService;
import server.essp.projectpre.service.syncException.ISyncExceptionService;
import server.essp.projectpre.ui.project.apply.VbChangeMail;
import server.essp.security.service.role.IRoleService;
import server.essp.syncproject.ISyncToHrms;
import server.essp.syncproject.SyncService;
import server.framework.common.BusinessException;
import c2s.dto.DtoUtil;
import c2s.dto.TransactionData;
import c2s.essp.common.account.IDtoAccount;
import c2s.essp.common.user.DtoRole;
import c2s.essp.common.user.DtoUser;
import c2s.essp.common.user.DtoUserBase;

import com.wits.util.comDate;
/**
 * 审核专案变更申请
 * @author wenhaizheng
 *
 */
public class AcCheckChangeProjectApp extends AbstractESSPAction {
    public static final String vmFile11 = "mail/template/proj/CheckChangeMail.html";
    public static final String vmFile12 = "mail/template/proj/RejectChangeMail.html";
    
    /**
     * 对专案变更申请进行审核操作
     *    1.准备变更前数据
     *    2.准备变更后的数据
     *    3.更新技术资料
     *    4.更新客户资料
     *    5.更新人员资料
     *    6.数据同步（财务_更新)
     *    7.发送邮件给申请人
     * @param request
     * @param response
     * @param data
     * @throws BusinessException
     */
    public void executeAct(HttpServletRequest request, HttpServletResponse response, 
            TransactionData data) throws BusinessException {
    	 DtoUser user = (DtoUser)request.getSession().getAttribute(DtoUser.SESSION_USER);
         String userName = user.getUserName();
        String status = request.getParameter("status");
        AfProjectCheck af = (AfProjectCheck) this.getForm();
        Long acntAppRid = Long.valueOf(af.getRid());
        IAccountApplicationService logic = (IAccountApplicationService) this.getBean("AccountApplicationLogic");
        IAccountWService finLogic = (IAccountWService)this.getBean("FinAccountService");
        IAccountWService tsLogic = (IAccountWService)this.getBean("TSAccountService");
        ISyncToHrms syncToHrms = (ISyncToHrms)this.getBean("SyncToHrms");
        AcntApp acntApp = logic.loadByRid(acntAppRid);
        VbChangeMail vb = new VbChangeMail();
        vb.setApplicationDate(comDate.dateToString(acntApp.getApplicationDate(), "yyyy-MM-dd"));
        vb.setApplicantName(acntApp.getApplicantName());
        String customerId = "";
        String custShort = "";
        if(af.getCustomerId().indexOf("---")>-1){
            customerId = af.getCustomerId().substring(0,af.getCustomerId().indexOf("---"));
            custShort = af.getCustomerId().substring(af.getCustomerId().indexOf("-")+3); 
        } else {
            customerId = af.getCustomerId();
            custShort = af.getCustomerId();
        }
        //准备变更前数据
        vb = getInfoBefore(acntApp.getAcntId(), vb);
        
        acntApp.setCustomerId(customerId);
        //准备变更后的数据
        vb = getInfoAfter(acntApp, vb);
       
     if(status.equals(Constant.CONFIRMED)){
        IAccountService acntLogic = (IAccountService) this.getBean("AccountLogic");
        Acnt acnt = acntLogic.loadByAcntId(acntApp.getAcntId(), "1");
        Long acntRid = acnt.getRid();
        String relPrjType = acnt.getRelPrjType();
        String contractAcntId = acnt.getContractAcntId();
        String relParentId = acnt.getRelParentId();
        String acntFullNameBefore = acnt.getAcntFullName();
        AcntPerson personPM = acntLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_PM);
        String pmIdBefore = personPM.getLoginId();
        Date plannedStartBefore = acnt.getAcntPlannedStart();
        Date actualStartBefore = acnt.getAcntActualStart();
        Date applicationDate = acnt.getApplicationDate();
        try {
            DtoUtil.copyProperties(acnt, acntApp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        acnt.setCustomerId(customerId);
        acnt.setRid(acntRid);
        acnt.setRelPrjType(relPrjType);
        acnt.setContractAcntId(contractAcntId);
        acnt.setRelParentId(relParentId);
        acnt.setApplicationDate(applicationDate);
        acntLogic.update(acnt);
        
        
        //更新技术资料
        updateTechInfo(acntRid, acntAppRid);
        //更新客户资料
        updateCustomerInfo(acntRid, acntAppRid);
        //更新人员资料
        updatePerson(acntRid, acntAppRid);
           
        //向LOG表中插入一笔记录
        ILogService logicLog=(ILogService)this.getBean("LogLogic");
        PPLog log = new PPLog();
        log.setAcntId(acntApp.getAcntId());
        log.setApplicationType(Constant.PROJECTCHANGEAPP);
        log.setDataType(Constant.LOG_PROJECT);
        log.setMailReceiver(acntApp.getApplicantName());
        log.setOperation(status);
        log.setOperationDate(new Date());
        log.setOperator(userName);
        logicLog.save(log);
        //数据同步（财务_更新,TimeSheet）
        SyncService syncLogic = (SyncService) this.getBean("SyncService");
        Map map = new HashMap();
        map.put("projId", acnt.getAcntId());
        map.put("projName", acnt.getAcntFullName());
        map.put("nickName", acnt.getAcntName());
        map.put("manager", acnt.getCostAttachBd());
        AcntPerson person = acntLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_LEADER);
        map.put("leader", person.getLoginId());
        map.put("costDept", acnt.getExecUnitId());
        person = acntLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_TC_SIGNER);
        map.put("tcSigner", person.getLoginId());
        map.put("planFrom", comDate.dateToString(acnt.getAcntPlannedStart(), "yyyy-MM-dd"));
        map.put("planTo", comDate.dateToString(acnt.getAcntPlannedFinish(), "yyyy-MM-dd"));
        map.put("custShort", custShort);
        map.put("estManmonth", acnt.getEstManmonth());
        map.put("achieveBelong", acnt.getAchieveBelong());
        person = acntLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_SALES);     
        map.put("sales", person.getLoginId());
        map.put("actuFrom", comDate.dateToString(acnt.getAcntActualStart(),"yyyy-MM-dd"));
        map.put("actuTo", comDate.dateToString(acnt.getAcntActualFinish(),"yyyy-MM-dd"));
        map.put("manMonth", acnt.getActualManmonth());
        map.put("productName", acnt.getProductName());
        map.put("projectDesc", acnt.getAcntBrief());
        person = acntLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_APPLICANT);  
        map.put("applicant", person.getLoginId());
        person = acntLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_BD_MANAGER);
        map.put("divisionManager", person.getLoginId());
        person = acntLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_PM);
        //根据新需求专案经理字段放入员工的名字 
        //将PM的ID放入另一KEY中 add by wenhaizheng 20080305
        //结转到财务的PM还是存放PM的ID mark by wenhaizheng 20080509
        map.put("projectManager", person.getLoginId());
        map.put("PM", person.getLoginId());
        map.put("projectExecUnit", acnt.getExecUnitId());
        person = acntLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_CUST_SERVICE_MANAGER);
        map.put("custServiceManager", person.getLoginId());
        person = acntLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_ENGAGE_MANAGER);
        map.put("engageManager", person.getLoginId());
        map.put("bizSource", acnt.getBizSource());
        map.put("masterProjects", acnt.getContractAcntId());
        map.put("projectProperty", acnt.getAcntAttribute());
        map.put("estimatedWords", acnt.getEstSize());
        map.put("UsePrimavera", acnt.getPrimaveraAdapted());
        map.put("Billable", acnt.getBillingBasis());
        map.put("AchieveBelong", acnt.getAchieveBelong());
        map.put("bl", acnt.getBl());
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
        	  if(!"V".equalsIgnoreCase(acnt.getExecSite())){
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
          if(siteName.equalsIgnoreCase("TP") 
        	&& isChanged(acnt, acntFullNameBefore, pmIdBefore, (String)map.get("PM"), 
        			plannedStartBefore, actualStartBefore)) {
        	  try{
        		  syncToHrms.updateProject(acnt, siteName, (String)map.get("PM"));
        	  } catch (BusinessException e) {
                  updateSyncException(acnt,"SyncToHrms",e.getErrorMsg(),user.getUserLoginId());
                  request.setAttribute("Message","Carry forward fail!"); 
        	  }
          }
     } else {
            
            acntApp.setRemark(af.getComment());
            //向LOG表中插入一笔记录
            ILogService logicLog=(ILogService)this.getBean("LogLogic");
            PPLog log = new PPLog();
            log.setAcntId(acntApp.getAcntId());
            log.setApplicationType(Constant.PROJECTCHANGEAPP);
            log.setDataType(Constant.LOG_PROJECT);
            log.setMailReceiver(acntApp.getApplicantName());
            log.setOperation(status);
            log.setOperationDate(new Date());
            log.setOperator(userName);
            logicLog.save(log);
        }
            acntApp.setApplicationStatus(status);
            logic.update(acntApp);
            //发送邮件给申请人
            if(status.equals("Confirmed")){  
                Map mailMap = new HashMap();
                this.prepareMailMap(mailMap, acntApp.getApplicantId(), acntApp.getDomain());
                ISendMailService sendLogic = (ISendMailService)this.getBean("SendMailLogic");
//                sendLogic.sendMail(acntApp.getApplicantId(),acntApp.getDomain(), vmFile11, "Project Change Check", vb);
                //只给HQ的PMO发送邮件
                String[] roleIds = {DtoRole.ROLE_HAP};
                IRoleService roleLogic =(IRoleService)this.getBean("RoleService");
                List projectOfficeList = roleLogic.listLoginIdUnderRole(roleIds);
                for(int i=0;i<projectOfficeList.size();i++){
                    DtoUserBase dtoUser = (DtoUserBase)projectOfficeList.get(i);
                    this.prepareMailMap(mailMap,dtoUser.getUserLoginId(), dtoUser.getDomain());
//                    sendLogic.sendMail(dtoUser.getUserLoginId(),dtoUser.getDomain(), vmFile11, "Project Change Check", vb);
                }
                List mailList = new ArrayList(mailMap.values());
                int size = mailList.size();
                String temp = "";
                String loginId = "";
                String domain = "";
                for(int i = 0;i<size;i++){
                    temp = (String)mailList.get(i);
                    loginId = temp.substring(0,temp.indexOf(":"));
                    domain= temp.substring(temp.indexOf(":")+1);
                    sendLogic.sendMail(loginId,domain, vmFile11, "Project Change Check("+vb.getAcntId()+")", vb);
                }
            } else {
                ISendMailService sendLogic = (ISendMailService)this.getBean("SendMailLogic");
                vb.setReMark(af.getComment());
                sendLogic.sendMail(acntApp.getApplicantId(),acntApp.getDomain(), vmFile12, "Project Change Rejected", vb);
                //给执行者发送邮件
                sendLogic.sendMail(user.getUserLoginId(),user.getDomain(), vmFile12, "Project Change Rejected", vb);
            }
            
    }
    /**
     * 判断专案是否有需要更新到104系统
     * @param acnt
     * @param acntFullNameBefore
     * @param pmIdBefore
     * @param pmId
     * @param plannedStartBefore
     * @param actualStartBefore
     * @return
     */
    private boolean isChanged(Acnt acnt, String acntFullNameBefore, String pmIdBefore, 
    		String pmId, Date plannedStartBefore, Date actualStartBefore) {
    	boolean isChanged = false;
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
    	return isChanged;
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
        }else if("SyncAccountImp".equals(impClassName)){
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
     * 将要邮件接收人员放入不重复的Map中
     * @param mailMap
     * @param loginId
     * @param domain
     */
    private void prepareMailMap(Map mailMap, String loginId, String domain){
        if(mailMap.containsKey(loginId)){
            return;
        }
        mailMap.put(loginId,loginId+":"+domain);
    }
    /**
     * 生成变更后专案申请的相关资料邮件内容
     * @param acntApp
     * @param vb
     * @return
     */
    private VbChangeMail getInfoAfter(AcntApp acntApp, VbChangeMail vb){
    	IAccountService acntLogic = (IAccountService)this.getBean("AccountLogic");
        vb.setAcntFullName(acntApp.getAcntFullName());
        IParameterService parameterLogic = (IParameterService)this.getBean("ParameterLogic");
        Parameter parameter = parameterLogic.loadByKindCode(Constant.PROJEC_TYPE, acntApp.getAcntAttribute());
        if(parameter!=null){
        vb.setAcntAttribute(parameter.getName());
        }
     
        vb.setAcntBrief(acntApp.getAcntBrief());
        ISiteService siteLogic = (ISiteService)this.getBean("AreaCodeLogic");
        Site site = siteLogic.loadByCode(acntApp.getExecSite());
        if(site!=null){
        vb.setExecSite(site.getSiteName());
        }
        IBdService bdLogic = (IBdService)this.getBean("BdCodeLogic");
        Bd bd = bdLogic.loadByBdCode(acntApp.getCostAttachBd());
        if(bd!=null){
        vb.setCostAttachBd(bd.getBdName());
        }
        Acnt execUnit = acntLogic.loadByAcntId(acntApp.getExecUnitId(), "0");
        if(execUnit != null) {
        	vb.setExecUnit(execUnit.getAcntId()+"--"+execUnit.getAcntName());
        }
        bd = bdLogic.loadByBdCode(acntApp.getBizSource());
        if(bd!=null){
        vb.setBizSource(bd.getBdName());
        }
        bd = bdLogic.loadByBdCode(acntApp.getAchieveBelong());
        if(bd!=null){
        vb.setAchieveBelong(bd.getBdName());
        }

        vb.setProductName(acntApp.getProductName());
        vb.setAcntPlannedStart(comDate.dateToString(acntApp.getAcntPlannedStart(), "yyyy-MM-dd"));
        vb.setAcntPlannedFinish(comDate.dateToString(acntApp.getAcntPlannedFinish(), "yyyy-MM-dd"));
        vb.setAcntActualStart(comDate.dateToString(acntApp.getAcntActualStart(), "yyyy-MM-dd"));
        vb.setAcntActualFinish(comDate.dateToString(acntApp.getAcntActualFinish(), "yyyy-MM-dd"));
        vb.setEstManmonth(String.valueOf(acntApp.getEstManmonth()));
        vb.setActualManmonth(String.valueOf(acntApp.getActualManmonth()));
        vb.setEstSize(String.valueOf(acntApp.getEstSize()));
        vb.setOtherDesc(acntApp.getOtherDesc());
        if("1".equals(acntApp.getPrimaveraAdapted())) {
        	vb.setPrimaveraAdapted("Y");
        } else {
        	vb.setPrimaveraAdapted("N");
        }
        if("1".equals(acntApp.getBillingBasis())) {
        	vb.setBillingBasis("Y");
        } else {
        	vb.setBillingBasis("N");
        }
        vb.setBizProperty(acntApp.getBizProperty());
        vb.setBillType(acntApp.getBillType());
        vb.setBl(acntApp.getBl());
        
        //人员信息
        vb = personMail(acntApp.getRid(), vb, "acntApp");
        
        //技术信息
        vb = techMail(acntApp.getRid(), vb, "acntApp");
        
        //客户信息
        vb = customerMail(acntApp.getRid(), vb, "acntApp");
        vb.setCustomerId(acntApp.getCustomerId());
     
        return vb;
    }
    
    /**
     * 生成变更前专案申请的相关资料邮件内容
     * @param acntId
     * @param vb
     * @return
     */
    private VbChangeMail getInfoBefore(String acntId, VbChangeMail vb){
        IAccountService acntLogic = (IAccountService)this.getBean("AccountLogic");
        Acnt acnt = acntLogic.loadByAcntId(acntId, "1");
        vb.setAcntId(acnt.getAcntId());
        vb.setAcntFullNameBefore(acnt.getAcntFullName());
        IParameterService parameterLogic = (IParameterService)this.getBean("ParameterLogic");
        Parameter parameter = parameterLogic.loadByKindCode(Constant.PROJEC_TYPE, acnt.getAcntAttribute());
        if(parameter!=null){
        vb.setAcntAttributeBefore(parameter.getName());
        }
        
        vb.setAcntBriefBefore(acnt.getAcntBrief());
        ISiteService siteLogic = (ISiteService)this.getBean("AreaCodeLogic");
        Site site = siteLogic.loadByCode(acnt.getExecSite());
        if(site!=null){
        vb.setExecSiteBefore(site.getSiteName());
        }
        IBdService bdLogic = (IBdService)this.getBean("BdCodeLogic");
        Bd bd = bdLogic.loadByBdCode(acnt.getCostAttachBd());
        if(bd!=null){
        vb.setCostAttachBdBefore(bd.getBdName());
        }
        Acnt execUnit = acntLogic.loadByAcntId(acnt.getExecUnitId(), "0");
        if(execUnit != null) {
        	vb.setExecUnitBefore(execUnit.getAcntId()+"--"+execUnit.getAcntName());
        }
        bd = bdLogic.loadByBdCode(acnt.getBizSource());
        if(bd!=null){
        vb.setBizSourceBefore(bd.getBdName());
        }
        bd = bdLogic.loadByBdCode(acnt.getAchieveBelong());
        if(bd!=null){
        vb.setAchieveBelongBefore(bd.getBdName());
        }
       
        vb.setProductNameBefore(acnt.getProductName());
        vb.setAcntPlannedStartBefore(comDate.dateToString(acnt.getAcntPlannedStart(), "yyyy-MM-dd"));
        vb.setAcntPlannedFinishBefore(comDate.dateToString(acnt.getAcntPlannedFinish(), "yyyy-MM-dd"));
        vb.setAcntActualStartBefore(comDate.dateToString(acnt.getAcntActualStart(), "yyyy-MM-dd"));
        vb.setAcntActualFinishBefore(comDate.dateToString(acnt.getAcntActualFinish(), "yyyy-MM-dd"));
        vb.setEstManmonthBefore(acnt.getEstManmonth());
        vb.setActualManmonthBefore(acnt.getActualManmonth());
        vb.setEstSizeBefore(acnt.getEstSize());
        vb.setOtherDescBefore(acnt.getOtherDesc());
        if("1".equals(acnt.getPrimaveraAdapted())) {
        	vb.setPrimaveraAdaptedBefore("Y");
        } else {
        	vb.setPrimaveraAdaptedBefore("N");
        }
        if("1".equals(acnt.getBillingBasis())) {
        	vb.setBillingBasisBefore("Y");
        } else {
        	vb.setBillingBasisBefore("N");
        }
        vb.setBizPropertyBefore(acnt.getBizProperty());
        vb.setBillTypeBefore(acnt.getBillType());
        vb.setBlBefore(acnt.getBl());
        
        //人员信息
        vb = personMail(acnt.getRid(), vb, "acnt");
        
        //技术信息
        vb = techMail(acnt.getRid(), vb, "acnt");
        
        //客户信息
        vb = customerMail(acnt.getRid(), vb, "acnt");
        vb.setCustomerIdBefore(acnt.getCustomerId());
       
        
        
        return vb;
    }
    
    /**
     * 生成变更前后客户资料的邮件内容
     * @param rid
     * @param vb
     * @param type
     * @return
     */
    private VbChangeMail customerMail(Long rid, VbChangeMail vb, String type){
      if(type.equals("acnt")){
        IAccountService acntLogic = (IAccountService)this.getBean("AccountLogic");
        AcntCustContactor acntCustContactor = acntLogic.loadByRidTypeFromAcntCustContactor(rid, IDtoAccount.CUSTOMER_CONTACTOR_CONTRACT);
        vb.setContractBefore(acntCustContactor.getName());
        vb.setContractTelBefore(acntCustContactor.getTelephone());
        vb.setContractEmailBefore(acntCustContactor.getEmail());
        
        acntCustContactor = acntLogic.loadByRidTypeFromAcntCustContactor(rid, IDtoAccount.CUSTOMER_CONTACTOR_EXE);
        vb.setExecutiveBefore(acntCustContactor.getName());
        vb.setExecutiveTelBefore(acntCustContactor.getTelephone());
        vb.setExecutiveEmailBefore(acntCustContactor.getEmail());
        
        acntCustContactor = acntLogic.loadByRidTypeFromAcntCustContactor(rid, IDtoAccount.CUSTOMER_CONTACTOR_FINANCIAL);
        vb.setFinancialBefore(acntCustContactor.getName());
        vb.setFinancialTelBefore(acntCustContactor.getTelephone());
        vb.setFinancialEmailBefore(acntCustContactor.getEmail());
      } else if(type.equals("acntApp")) {
          IAccountApplicationService logic = (IAccountApplicationService)this.getBean("AccountApplicationLogic");
          AcntCustContactorApp acntCustContactorApp = logic.loadByRidTypeFromAcntCustContactorApp(rid, IDtoAccount.CUSTOMER_CONTACTOR_CONTRACT);
          vb.setContract(acntCustContactorApp.getName());
          vb.setContractTel(acntCustContactorApp.getTelephone());
          vb.setContractEmail(acntCustContactorApp.getEmail());
          
          acntCustContactorApp = logic.loadByRidTypeFromAcntCustContactorApp(rid, IDtoAccount.CUSTOMER_CONTACTOR_EXE);
          vb.setExecutive(acntCustContactorApp.getName());
          vb.setExecutiveTel(acntCustContactorApp.getTelephone());
          vb.setExecutiveEmail(acntCustContactorApp.getEmail());
          
          acntCustContactorApp = logic.loadByRidTypeFromAcntCustContactorApp(rid, IDtoAccount.CUSTOMER_CONTACTOR_FINANCIAL);
          vb.setFinancial(acntCustContactorApp.getName());
          vb.setFinancialTel(acntCustContactorApp.getTelephone());
          vb.setFinancialEmail(acntCustContactorApp.getEmail());
          
      }
        
        return vb;
    }
    
    /**
     * 生成变更前后技术资料的邮件内容
     * @param rid
     * @param vb
     * @param type
     * @return
     */
    private VbChangeMail techMail(Long rid, VbChangeMail vb, String type){

        String projectType = getTechInfo(rid, Constant.PROJECT_TYPE, type);
        String productType = getTechInfo(rid, Constant.PRODUCT_TYPE, type);
        String productAttribute = getTechInfo(rid, Constant.PRODUCT_ATTRIBUTE, type);
        String workItem = getTechInfo(rid, Constant.WORK_ITEM, type);
        String technicalDomain = getTechInfo(rid, Constant.TECHNICAL_DOMAIN, type);
        String originalLanguage = getTechInfo(rid, Constant.ORIGINAL_LANGUAGE, type);
        String translationLanguage = getTechInfo(rid, Constant.TRANSLATION_LANGUANGE, type);
        
      if(type.equals("acnt")){
           vb.setProjectTypeBefore(projectType);
           vb.setProductTypeBefore(productType);
           vb.setProductAttributeBefore(productAttribute); 
           vb.setWorkItemBefore(workItem);
           vb.setTechnicalDomainBefore(technicalDomain);
           vb.setOriginalLanguageBefore(originalLanguage);
           vb.setTranslationLanguageBefore(translationLanguage);
      } else if(type.equals("acntApp")) {
          vb.setProjectType(projectType);
          vb.setProductType(productType);
          vb.setProductAttribute(productAttribute); 
          vb.setWorkItem(workItem);
          vb.setTechnicalDomain(technicalDomain);
          vb.setOriginalLanguage(originalLanguage);
          vb.setTranslationLanguage(translationLanguage);
          
      }
      
      
        vb = getTechInfoText(rid, vb, type);
       
        
        
        return vb;
    }
    /**
     * 生成变更前后的技术资料文本信息的邮件内容
     * @param rid
     * @param vb
     * @param type
     * @return
     */
    private VbChangeMail getTechInfoText(Long rid, VbChangeMail vb, String type){
      if(type.equals("acnt")){
        IAccountService acntLogic = (IAccountService)this.getBean("AccountLogic");
        AcntTechinfo techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, Constant.DEVELOPENVIRONMENT, Constant.JOBSYSTEM);
        vb.setDevelopJobSystemBefore(techInfo.getDescription());
        
        techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, Constant.DEVELOPENVIRONMENT, Constant.DATABASE);
        vb.setDevelopDataBaseBefore(techInfo.getDescription());
        
        techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, Constant.DEVELOPENVIRONMENT, Constant.TOOL);
        vb.setDevelopToolBefore(techInfo.getDescription());
        
        techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, Constant.DEVELOPENVIRONMENT, Constant.NETWORK);
        vb.setDevelopNetWorkBefore(techInfo.getDescription());
        
        techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, Constant.DEVELOPENVIRONMENT, Constant.PROGRAMLANGUAGE);
        vb.setDevelopProgramLanguageBefore(techInfo.getDescription());
        
        techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, Constant.DEVELOPENVIRONMENT, Constant.OTHERS);
        vb.setDevelopOthersBefore(techInfo.getDescription());
        
        techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, Constant.TRNSLATEPUBLISHTYPE, Constant.JOBSYSTEM);
        vb.setTypeJobSystemBefore(techInfo.getDescription());
        
        techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, Constant.TRNSLATEPUBLISHTYPE, Constant.DATABASE);
        vb.setTypeDataBaseBefore(techInfo.getDescription());
        
        techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, Constant.TRNSLATEPUBLISHTYPE, Constant.TOOL);
        vb.setTypeToolBefore(techInfo.getDescription());
        
        techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, Constant.TRNSLATEPUBLISHTYPE, Constant.NETWORK);
        vb.setTypeNetWorkBefore(techInfo.getDescription());
        
        techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, Constant.TRNSLATEPUBLISHTYPE, Constant.PROGRAMLANGUAGE);
        vb.setTypeProgramLanguageBefore(techInfo.getDescription());
        
        techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, Constant.TRNSLATEPUBLISHTYPE, Constant.OTHERS);
        vb.setTypeOthersBefore(techInfo.getDescription());
        
        techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, Constant.HARDREQ, Constant.HARDREQ);
        vb.setHardReqBefore(techInfo.getDescription());
        
        techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, Constant.SOFTREQ, Constant.SOFTREQ);
        vb.setSoftReqBefore(techInfo.getDescription());
      } else if(type.equals("acntApp")) {
          IAccountApplicationService logic = (IAccountApplicationService)this.getBean("AccountApplicationLogic");
          AcntTechinfoApp techInfoApp = logic.loadByRidKindCodeFromTechInfoApp(rid, Constant.DEVELOPENVIRONMENT, Constant.JOBSYSTEM);
          vb.setDevelopJobSystem(techInfoApp.getDescription());
          
          techInfoApp = logic.loadByRidKindCodeFromTechInfoApp(rid, Constant.DEVELOPENVIRONMENT, Constant.DATABASE);
          vb.setDevelopDataBase(techInfoApp.getDescription());
          
          techInfoApp = logic.loadByRidKindCodeFromTechInfoApp(rid, Constant.DEVELOPENVIRONMENT, Constant.TOOL);
          vb.setDevelopTool(techInfoApp.getDescription());
          
          techInfoApp = logic.loadByRidKindCodeFromTechInfoApp(rid, Constant.DEVELOPENVIRONMENT, Constant.NETWORK);
          vb.setDevelopNetWork(techInfoApp.getDescription());
          
          techInfoApp = logic.loadByRidKindCodeFromTechInfoApp(rid, Constant.DEVELOPENVIRONMENT, Constant.PROGRAMLANGUAGE);
          vb.setDevelopProgramLanguage(techInfoApp.getDescription());
          
          techInfoApp = logic.loadByRidKindCodeFromTechInfoApp(rid, Constant.DEVELOPENVIRONMENT, Constant.OTHERS);
          vb.setDevelopOthers(techInfoApp.getDescription());
          
          techInfoApp = logic.loadByRidKindCodeFromTechInfoApp(rid, Constant.TRNSLATEPUBLISHTYPE, Constant.JOBSYSTEM);
          vb.setTypeJobSystem(techInfoApp.getDescription());
          
          techInfoApp = logic.loadByRidKindCodeFromTechInfoApp(rid, Constant.TRNSLATEPUBLISHTYPE, Constant.DATABASE);
          vb.setTypeDataBase(techInfoApp.getDescription());
          
          techInfoApp = logic.loadByRidKindCodeFromTechInfoApp(rid, Constant.TRNSLATEPUBLISHTYPE, Constant.TOOL);
          vb.setTypeTool(techInfoApp.getDescription());
          
          techInfoApp = logic.loadByRidKindCodeFromTechInfoApp(rid, Constant.TRNSLATEPUBLISHTYPE, Constant.NETWORK);
          vb.setTypeNetWork(techInfoApp.getDescription());
          
          techInfoApp = logic.loadByRidKindCodeFromTechInfoApp(rid, Constant.TRNSLATEPUBLISHTYPE, Constant.PROGRAMLANGUAGE);
          vb.setTypeProgramLanguage(techInfoApp.getDescription());
          
          techInfoApp = logic.loadByRidKindCodeFromTechInfoApp(rid, Constant.TRNSLATEPUBLISHTYPE, Constant.OTHERS);
          vb.setTypeOthers(techInfoApp.getDescription());
          
          techInfoApp = logic.loadByRidKindCodeFromTechInfoApp(rid, Constant.HARDREQ, Constant.HARDREQ);
          vb.setHardReq(techInfoApp.getDescription());
          
          techInfoApp = logic.loadByRidKindCodeFromTechInfoApp(rid, Constant.SOFTREQ, Constant.SOFTREQ);
          vb.setSoftReq(techInfoApp.getDescription());
          
      }
        
        
        
        return vb;
    }
    
    /**
     * 生成变更前后技术资料邮件内容的具体实现
     * @param rid
     * @param kind
     * @param type
     * @return
     */
    private String getTechInfo(Long rid, String kind, String type){
      String valueCom = null;
      if(type.equals("acnt")){
       IAccountService acntLogic = (IAccountService)this.getBean("AccountLogic");
       List techInfoList = acntLogic.listByRidKindFromTechInfo(rid, kind);
       AcntTechinfo techInfo = new AcntTechinfo();
       ParameterId parameterId = new ParameterId();
       parameterId.setKind(kind);
       Parameter parameter = new Parameter();
       
       IParameterService parameterLogic = (IParameterService) this.getBean("ParameterLogic");
       for(int i=0;i<techInfoList.size();i++) {
           techInfo = (AcntTechinfo) techInfoList.get(i);
           parameterId.setCode(techInfo.getId().getCode());
           parameter = parameterLogic.loadByKey(parameterId);
           if(parameter!=null){
               valueCom = valueCom + "," + parameter.getName();
           }
       }
       if(valueCom!=null){
           valueCom = valueCom.substring(5);
       }else{
           valueCom = "";
       }
      } else if(type.equals("acntApp")) {
          IAccountApplicationService logic = (IAccountApplicationService)this.getBean("AccountApplicationLogic");
          List techInfoAppList = logic.listByRidKindFromTechInfoApp(rid, kind);
          AcntTechinfoApp techInfoApp = new AcntTechinfoApp();
          ParameterId parameterId = new ParameterId();
          parameterId.setKind(kind);
          Parameter parameter = new Parameter();
          
          IParameterService parameterLogic = (IParameterService) this.getBean("ParameterLogic");
          for(int i=0;i<techInfoAppList.size();i++) {
              techInfoApp = (AcntTechinfoApp) techInfoAppList.get(i);
              parameterId.setCode(techInfoApp.getId().getCode());
              parameter = parameterLogic.loadByKey(parameterId);
              if(parameter!=null){
                  valueCom = valueCom + "," + parameter.getName();
              }
          }
          if(valueCom!=null){
              valueCom = valueCom.substring(5);
          }else{
              valueCom = "";
          }
          
      }
        
        return valueCom;
    }
    
    /**
     * 生成变更前后人员资料的邮件内容
     * @param rid
     * @param vb
     * @param type
     * @return
     */
    private VbChangeMail personMail(Long rid, VbChangeMail vb, String type){
      if(type.equals("acnt")){
        IAccountService acntLogic = (IAccountService)this.getBean("AccountLogic");
        AcntPerson person = acntLogic.loadByRidFromPerson(rid, IDtoAccount.USER_TYPE_PM);
        vb.setPMNameBefore(person.getName());
        
        person = acntLogic.loadByRidFromPerson(rid, IDtoAccount.USER_TYPE_TC_SIGNER);
        vb.setTCSNameBefore(person.getName());
        
        person = acntLogic.loadByRidFromPerson(rid, IDtoAccount.USER_TYPE_BD_MANAGER);
        vb.setBDMNameBefore(person.getName());
        
        person = acntLogic.loadByRidFromPerson(rid, IDtoAccount.USER_TYPE_LEADER);
        vb.setLeaderNameBefore(person.getName());
        
        person = acntLogic.loadByRidFromPerson(rid, IDtoAccount.USER_TYPE_SALES);
        vb.setSalesNameBefore(person.getName());
        
        person = acntLogic.loadByRidFromPerson(rid, IDtoAccount.USER_TYPE_CUST_SERVICE_MANAGER);
        vb.setCustServiceManagerNameBefore(person.getName());
        
        person = acntLogic.loadByRidFromPerson(rid, IDtoAccount.USER_TYPE_ENGAGE_MANAGER);
        vb.setEngageManagerNameBefore(person.getName());
        
      } else if(type.equals("acntApp")) {
          IAccountApplicationService logic = (IAccountApplicationService)this.getBean("AccountApplicationLogic");
          AcntPersonApp personApp = logic.loadByRidFromPersonApp(rid, IDtoAccount.USER_TYPE_PM);
          vb.setPMName(personApp.getName());
          
          personApp = logic.loadByRidFromPersonApp(rid, IDtoAccount.USER_TYPE_TC_SIGNER);
          vb.setTCSName(personApp.getName());
          
          personApp = logic.loadByRidFromPersonApp(rid, IDtoAccount.USER_TYPE_BD_MANAGER);
          vb.setBDMName(personApp.getName());
          
          personApp = logic.loadByRidFromPersonApp(rid, IDtoAccount.USER_TYPE_LEADER);
          vb.setLeaderName(personApp.getName());
          
          personApp = logic.loadByRidFromPersonApp(rid, IDtoAccount.USER_TYPE_SALES);
          vb.setSalesName(personApp.getName());
          
          personApp = logic.loadByRidFromPersonApp(rid, IDtoAccount.USER_TYPE_CUST_SERVICE_MANAGER);
          vb.setCustServiceManagerName(personApp.getName());
          
          personApp = logic.loadByRidFromPersonApp(rid, IDtoAccount.USER_TYPE_ENGAGE_MANAGER);
          vb.setEngageManagerName(personApp.getName());
          
      }
        
        
        return vb;
    }
    
    /**
     * 更新技术资料
     * @param acntRid
     * @param acntAppRid
     */
    private void updateTechInfo(Long acntRid, Long acntAppRid){
//      变更技术资料
        IAccountService acntLogic = (IAccountService) this.getBean("AccountLogic");
//        acntLogic.deleteAllAcntTechinfo(acntRid);
        IAccountApplicationService logic = (IAccountApplicationService) this.getBean("AccountApplicationLogic");
        List techInfoAppList = logic.listByRidFromTechInfoApp(acntAppRid);
        List techInfoList = acntLogic.listByRidFromTechInfo(acntRid);
        for(int i=0;i<techInfoList.size();i++){
            AcntTechinfo techinfoBefore = (AcntTechinfo)techInfoList.get(i);
            AcntTechinfoApp techinfoAppAfter = logic.loadByRidKindCodeFromTechInfoApp(acntAppRid, 
                                               techinfoBefore.getId().getKind(),
                                               techinfoBefore.getId().getCode());
            if(techinfoAppAfter!=null){
//                techinfoBefore.getId().setAcntRid(acntRid);
//                techinfoBefore.getId().setCode(techinfoAppAfter.getId().getCode());
//                techinfoBefore.getId().setKind(techinfoAppAfter.getId().getKind());
                techinfoBefore.setDescription(techinfoAppAfter.getDescription());
                acntLogic.updateAcntTechinfo(techinfoBefore);
                techInfoAppList.remove(techinfoAppAfter);
                continue;
            } else if(techinfoAppAfter==null){
                acntLogic.deleteAcntTechinfo(techinfoBefore);
                continue;
            }  
        }
        for(int j=0;j<techInfoAppList.size();j++){
            AcntTechinfoApp techinfoApp = (AcntTechinfoApp)techInfoAppList.get(j);
            AcntTechinfo techinfoNest = new AcntTechinfo();
            techinfoNest.getId().setAcntRid(acntRid);
            techinfoNest.getId().setCode(techinfoApp.getId().getCode());
            techinfoNest.getId().setKind(techinfoApp.getId().getKind());
            techinfoNest.setDescription(techinfoApp.getDescription());
            acntLogic.saveAcntTechinfo(techinfoNest);
        }
    }
    /**
     * 更新客户资料
     * @param acntRid
     * @param acntAppRid
     */
    private void updateCustomerInfo(Long acntRid, Long acntAppRid) {
        //变更客户资料
        IAccountService acntLogic = (IAccountService) this.getBean("AccountLogic");
        //acntLogic.deleteAllAcntCustContactor(acntRid);
        IAccountApplicationService logic = (IAccountApplicationService) this.getBean("AccountApplicationLogic");
        List customerInfoAppList = logic.listByRidFromCustomerInfoApp(acntAppRid);
        for(int i=0;i<customerInfoAppList.size();i++){
            AcntCustContactorApp custContactorApp = (AcntCustContactorApp)customerInfoAppList.get(i);
            AcntCustContactor custContactor = acntLogic.loadByRidTypeFromAcntCustContactor(acntRid, custContactorApp.getContactorType());  
            custContactor.setContactorType(custContactorApp.getContactorType());
            custContactor.setName(custContactorApp.getName());
            custContactor.setTelephone(custContactorApp.getTelephone());
            custContactor.setEmail(custContactorApp.getEmail());
            custContactor.setAcntRid(acntRid);
            acntLogic.updateAcntCustContactor(custContactor);
            
        }
    }
    
    /**
     * 更新人员资料
     * @param acntRid
     * @param acntAppRid
     */
    private void updatePerson(Long acntRid, Long acntAppRid) {
        //变更人员资料
        IAccountService acntLogic = (IAccountService) this.getBean("AccountLogic");
        //AcntPerson personNest = acntLogic.loadByRidFromPerson(acntRid, IDtoAccount.USER_TYPE_APPLICANT);
        //acntLogic.deleteAllAcntPerson(acntRid);
        IAccountApplicationService logic = (IAccountApplicationService) this.getBean("AccountApplicationLogic");
        List personAppList = logic.listByRidFromPersonApp(acntAppRid);
        for(int i=0;i<personAppList.size();i++) {
            AcntPersonApp personApp = (AcntPersonApp)personAppList.get(i);
            AcntPerson person = acntLogic.loadByRidFromPerson(acntRid, personApp.getPersonType());
            person.setPersonType(personApp.getPersonType());
            person.setLoginId(personApp.getLoginId());
            person.setName(personApp.getName());
            person.setDomain(personApp.getDomain());
            person.setAcntRid(acntRid);
            acntLogic.updateAcntPerson(person);   
        }
        //acntLogic.saveAcntPerson(personNest);
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
        map.put("projectType",str);
        map.put("projTypeNo", str);
       
        str = getTechInfoName(rid);
        map.put("AccountTypeName", str);
        
        str = getTechInfoVb(rid, server.essp.projectpre.service.constant.Constant.PRODUCT_TYPE);
        map.put("productType", str);
        
       
        str = getTechInfoVb(rid, server.essp.projectpre.service.constant.Constant.PRODUCT_ATTRIBUTE);
        map.put("productProperty", str);
        
       
        str = getTechInfoVb(rid, server.essp.projectpre.service.constant.Constant.WORK_ITEM);
        map.put("workItem", str);
        
      
        str = getTechInfoVb(rid, server.essp.projectpre.service.constant.Constant.TECHNICAL_DOMAIN);
        map.put("skillDomain", str);
        
      
        str = getTechInfoVb(rid, server.essp.projectpre.service.constant.Constant.ORIGINAL_LANGUAGE);
        map.put("originalLan", str);
      
        str = getTechInfoVb(rid, server.essp.projectpre.service.constant.Constant.TRANSLATION_LANGUANGE);
        map.put("transLan", str);
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
        List techinfoList = acntLogic.listByRidKindFromTechInfo(rid, type);
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
     * 将查询到的技术资料文本放入MAP中,作为传到财务系统的数据
     * @param map
     * @param rid
     * @return
     */
    private Map getTechInfoTextList(Map map,Long rid) {
      
      
        AcntTechinfo techInfo = new AcntTechinfo();
        IAccountService acntLogic = (IAccountService) this.getBean("AccountLogic");
      
        techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, server.essp.projectpre.service.constant.Constant.HARDREQ, server.essp.projectpre.service.constant.Constant.HARDREQ);
        map.put("hardWareReq", techInfo.getDescription());
       
      
      
        techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, server.essp.projectpre.service.constant.Constant.SOFTREQ, server.essp.projectpre.service.constant.Constant.SOFTREQ);
        map.put("softWareReq", techInfo.getDescription());
       
       
       
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
      map.put("devEnvOs", techInfo.getDescription());
      
      techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, kind, server.essp.projectpre.service.constant.Constant.DATABASE);
      map.put("devEnvDb", techInfo.getDescription());
      
      techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, kind, server.essp.projectpre.service.constant.Constant.TOOL);
      map.put("devEnvTool", techInfo.getDescription());
      
      techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, kind, server.essp.projectpre.service.constant.Constant.NETWORK);
      map.put("devEnvNetwork", techInfo.getDescription());
      
      techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, kind, server.essp.projectpre.service.constant.Constant.PROGRAMLANGUAGE);
      map.put("devEnvLang", techInfo.getDescription());
      
      techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, kind, server.essp.projectpre.service.constant.Constant.OTHERS);
      map.put("devEnvOther", techInfo.getDescription());
     } else if(kind.equals(server.essp.projectpre.service.constant.Constant.TRNSLATEPUBLISHTYPE)) {
         techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, kind, server.essp.projectpre.service.constant.Constant.JOBSYSTEM);
         map.put("tranEnvOs", techInfo.getDescription());
         
         techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, kind, server.essp.projectpre.service.constant.Constant.DATABASE);
         map.put("tranEnvDb", techInfo.getDescription());
         
         techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, kind, server.essp.projectpre.service.constant.Constant.TOOL);
         map.put("tranEnvTool", techInfo.getDescription());
         
         techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, kind, server.essp.projectpre.service.constant.Constant.NETWORK);
         map.put("tranEnvNetwork", techInfo.getDescription());
         
         techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, kind, server.essp.projectpre.service.constant.Constant.PROGRAMLANGUAGE);
         map.put("tranEnvLang", techInfo.getDescription());
         
         techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, kind, server.essp.projectpre.service.constant.Constant.OTHERS);
         map.put("tranEnvOther", techInfo.getDescription());
     }
     
      
      return map;
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
        map.put("ccontact", acntCustContactor.getName());
        map.put("ccontTel", acntCustContactor.getTelephone());
        map.put("ccontEmail", acntCustContactor.getEmail());
      
       
      
        acntCustContactor = acntLogic.loadByRidTypeFromAcntCustContactor(rid, IDtoAccount.CUSTOMER_CONTACTOR_EXE);
        map.put("pcontact", acntCustContactor.getName());
        map.put("pcontTel", acntCustContactor.getTelephone());
        map.put("pcontEmail", acntCustContactor.getEmail());
 
        acntCustContactor = acntLogic.loadByRidTypeFromAcntCustContactor(rid, IDtoAccount.CUSTOMER_CONTACTOR_FINANCIAL);
        map.put("icontact", acntCustContactor.getName());
        map.put("icontTel", acntCustContactor.getTelephone());
        map.put("icontEmail", acntCustContactor.getEmail());
       
      
        
          return map;
    }

}
