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
import server.essp.projectpre.db.*;
import server.essp.projectpre.service.account.IAccountService;
import server.essp.projectpre.service.accountapplication.IAccountApplicationService;
import server.essp.projectpre.service.constant.Constant;
import server.essp.projectpre.service.log.ILogService;
import server.essp.projectpre.service.parameter.IParameterService;
import server.essp.projectpre.service.sendMail.ISendMailService;
import server.essp.projectpre.service.site.ISiteService;
import server.essp.projectpre.service.syncException.ISyncExceptionService;
import server.essp.projectpre.ui.project.confirm.AfProjectConfirm;
import server.essp.security.service.role.IRoleService;
import server.essp.syncproject.ISyncToHrms;
import server.essp.syncproject.SyncService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
import c2s.essp.common.account.IDtoAccount;
import c2s.essp.common.user.DtoRole;
import c2s.essp.common.user.DtoUser;
import c2s.essp.common.user.DtoUserBase;

import com.wits.util.comDate;

/**
 * 复核结案申请的Action
 * @author Stephen
 *
 * 
 */
public class AcCheckConfirmProjectApp extends AbstractESSPAction {
    public static final String vmFile11 = "mail/template/proj/CheckConfirmMail.html";
    public static final String vmFile12 = "mail/template/proj/RejectConfirmMail.html";
    /**
     * 对结案申请进行审核操作
     *   1.获取相关人员
     *   2.获取技术资料
     *   3.数据同步（财务_结案）
     *   4.发送邮件给申请人
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
            String acntId = null;
             if(request.getParameter("acntId")!=null){
                 acntId = request.getParameter("acntId");
                 
             }
             IAccountService acntLogic = (IAccountService)this.getBean("AccountLogic");
             IAccountWService finLogic = (IAccountWService)this.getBean("FinAccountService");
             IAccountWService tsLogic = (IAccountWService)this.getBean("TSAccountService");
             ISyncToHrms syncToHrms = (ISyncToHrms)this.getBean("SyncToHrms");
             Acnt acnt = acntLogic.loadByAcntId(acntId, "1");
             AfProjectConfirm af =  (AfProjectConfirm) this.getForm();
             af.setAcntId(acntId);
             //获取相关人员
             af = getPerson(acnt.getRid(), af);
             
             //获取技术资料
             af = getTechInfo(acnt.getRid(), af);
             if(acnt!=null){
                 IParameterService parameterLogic = (IParameterService)this.getBean("ParameterLogic");
                 Parameter parameter = parameterLogic.loadByKindCode(server.essp.projectpre.service.constant.Constant.PROJEC_TYPE, acnt.getAcntAttribute());
                 if(parameter!=null){
                 af.setAcntAttribute(parameter.getName());
                 }
             af.setAcntName(acnt.getAcntName());
             af.setAcntFullName(acnt.getAcntFullName());
             af.setAcntBrief(acnt.getAcntBrief());
             String date = comDate.dateToString(acnt.getAcntPlannedStart(), "yyyy-MM-dd")+"~"+comDate.dateToString(acnt.getAcntPlannedFinish(), "yyyy-MM-dd");
             af.setAcntAnticpated(date);
             }
             IAccountApplicationService logic = (IAccountApplicationService) this.getBean("AccountApplicationLogic");
             
             AcntApp acntApp = logic.loadByTypeAcntId(Constant.PROJECTCONFIRMAPP, af.getAcntId());
             boolean isConfirm = false;
             if(status.equals(Constant.CONFIRMED)) {
                 isConfirm = true;
                 acntApp.setApplicationStatus(Constant.CONFIRMED);
                 logic.update(acntApp);
                 acnt.setAcntStatus(Constant.CLOSED);    
                 //向LOG表中插入一笔记录
                 ILogService logicLog=(ILogService)this.getBean("LogLogic");
                 PPLog log = new PPLog();
                 log.setAcntId(acntApp.getAcntId());
                 log.setApplicationType(Constant.PROJECTCONFIRMAPP);
                 log.setDataType(Constant.LOG_PROJECT);
                 log.setMailReceiver(acntApp.getApplicantName());
                 log.setOperation(acntApp.getApplicationStatus());
                 log.setOperationDate(new Date());
                 log.setOperator(userName);
                 logicLog.save(log);
                 //数据同步（财务_结案,TimeSheet）
                 Map map = new HashMap();
                 map.put("actuFrom", comDate.dateToString(acnt.getAcntActualStart(), "yyyy-MM-dd"));
                 map.put("actuTo", comDate.dateToString(acnt.getAcntActualFinish(),"yyyy-MM-dd"));
                 map.put("manMonth", acnt.getActualManmonth());
                 try{
           //          finLogic.closeAccount(acnt.getAcntId(), map);
                 }catch (BusinessException e){
                     String errMessge = e.getErrorCode();
                     if(errMessge.equals("Close Finance ERROR")||errMessge.
                              equals("Close Finance Rollback ERROR")){        
                     updateSyncException(acnt,"FinAccountWServiceImpl",errMessge,user.getUserLoginId());      
                     request.setAttribute("Message","Carry forward fail!");
                 }        
                 }
                 try{
                	 if(!"V".equalsIgnoreCase(acnt.getExecSite())) {
           //     		 tsLogic.closeAccount(acnt.getAcntId(), map);
                	 }
                 }catch(BusinessException e){
                     String errMessge = e.getErrorCode();
                     if(errMessge.equals("Synchronize Close TimeSheet Error")){
                     updateSyncException(acnt,"SyncAccountImp",errMessge,user.getUserLoginId());
                     request.setAttribute("Message","Carry forward fail!");
                     }
                 }
                 try{
                	 ISiteService siteLogic = (ISiteService)this.getBean("AreaCodeLogic");
                     Site site = siteLogic.loadByCode(acnt.getExecSite());
                     String siteName = "";
                     if(site != null) {
                     	siteName = site.getSiteName();
                     }
                	 if(siteName.equalsIgnoreCase("TP")) {
                		 AcntPerson person = acntLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_PM);
                		 syncToHrms.closeProject(acnt, siteName, person.getLoginId());
                	 }
                 } catch(BusinessException e) {
                	 updateSyncException(acnt,"SyncToHrms",e.getErrorMsg(),user.getUserLoginId());
                     request.setAttribute("Message","Carry forward fail!");
                 }
             } else {
                 acntApp.setApplicationStatus(Constant.REJECTED);
                 acntApp.setRemark(af.getComment());
                 logic.update(acntApp);
                 
                 //向LOG表中插入一笔记录
                 ILogService logicLog=(ILogService)this.getBean("LogLogic");
                 PPLog log = new PPLog();
                 log.setAcntId(acntApp.getAcntId());
                 log.setApplicationType(Constant.PROJECTCONFIRMAPP);
                 log.setDataType(Constant.LOG_PROJECT);
                 log.setMailReceiver(acntApp.getApplicantName());
                 log.setOperation(acntApp.getApplicationStatus());
                 log.setOperationDate(new Date());
                 log.setOperator(userName);
                 logicLog.save(log);
             } 
             ISendMailService sendLogic = (ISendMailService)this.getBean("SendMailLogic");
           if(isConfirm){
        	 //如果通过申请发送邮件给申请人以及PMO人员
             AcntPerson person = acntLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_APPLICANT);
             Map mailMap = new HashMap();
             this.prepareMailMap(mailMap, person.getLoginId(), person.getDomain());
             //只给HQ的PMO发送邮件
             String[] roleIds = {DtoRole.ROLE_HAP};
             IRoleService roleLogic =(IRoleService)this.getBean("RoleService");
             List projectOfficeList = roleLogic.listLoginIdUnderRole(roleIds);
             for(int i=0;i<projectOfficeList.size();i++){
                 DtoUserBase dtoUser = (DtoUserBase)projectOfficeList.get(i);
                 this.prepareMailMap(mailMap,dtoUser.getUserLoginId(), dtoUser.getDomain());
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
                 sendLogic.sendMail(loginId,domain, vmFile11, "Project Confirm Check("+af.getAcntId()+")", af);
             }
           } else {
        	   //如果拒绝申请给申请人和执行者发送邮件
        	   AcntPerson person = acntLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_APPLICANT);
        	   sendLogic.sendMail(person.getLoginId(), person.getDomain(), vmFile12, "Project Confirm Rejected", af);
        	   sendLogic.sendMail(user.getUserLoginId(), user.getDomain(), vmFile12, "Project Confirm Rejected", af);
           }
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
        syncExcption.setType("Close");
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
     * 获取相关人员的信息
     * @param Rid 当前戴结案专案的Rid
     * @param af 当前的ActionForm
     * @return 含有人员信息的ActionForm
     */
    private AfProjectConfirm getPerson(Long Rid, AfProjectConfirm af) {
        IAccountService acntLogic = (IAccountService) this.getBean("AccountLogic");
        
        AcntPerson acntPerson = acntLogic.loadByRidFromPerson(Rid, IDtoAccount.USER_TYPE_APPLICANT);
        if(acntPerson!=null) {
        af.setApplicant(acntPerson.getName());
        }
        
        acntPerson = acntLogic.loadByRidFromPerson(Rid, IDtoAccount.USER_TYPE_LEADER);
        if(acntPerson!=null) {
        af.setLeader(acntPerson.getName());
        }
        
        acntPerson = acntLogic.loadByRidFromPerson(Rid, IDtoAccount.USER_TYPE_PM);
        if(acntPerson!=null) {
        af.setPMName(acntPerson.getName());
        }
        
        acntPerson = acntLogic.loadByRidFromPerson(Rid, IDtoAccount.USER_TYPE_TC_SIGNER);
        if(acntPerson!=null) {
        af.setTCSName(acntPerson.getName());
        }
        
        acntPerson = acntLogic.loadByRidFromPerson(Rid, IDtoAccount.USER_TYPE_BD_MANAGER);
        if(acntPerson!=null) {
        af.setBDName(acntPerson.getName());
        }
        
        return af;
        
    }
    /**
     * 获取相关的技术资料
     * @param Rid 当前待结案专案的Rid
     * @param af 当前的ActionForm
     * @return 含有技术资料的ActionForm
     */
    private AfProjectConfirm getTechInfo(Long Rid, AfProjectConfirm af) {
        
        IAccountService acntLogic = (IAccountService) this.getBean("AccountLogic");
        
        List productList = acntLogic.listByRidKindFromTechInfo(Rid, server.essp.projectpre.service.constant.Constant.PRODUCT_TYPE);
        List productAttributeList = acntLogic.listByRidKindFromTechInfo(Rid, server.essp.projectpre.service.constant.Constant.PRODUCT_ATTRIBUTE);
        List workItemList = acntLogic.listByRidKindFromTechInfo(Rid, server.essp.projectpre.service.constant.Constant.WORK_ITEM);
        
        String productStr = null;
        String productAttributeStr = null;
        String workItemStr = null;
        
        ParameterId parameterId = new ParameterId();
        Parameter parameter = new Parameter();
        IParameterService parameterLogic = (IParameterService) this.getBean("ParameterLogic");
        
        for(int i=0;i<productList.size();i++) {
            AcntTechinfo techInfo = (AcntTechinfo) productList.get(i); 
            parameterId.setCode(techInfo.getId().getCode());
            parameterId.setKind(techInfo.getId().getKind());
            parameter = parameterLogic.loadByKey(parameterId);
            if(parameter!=null){
             productStr = productStr +","+ parameter.getName();
            }
        }
        if(productStr!=null) {
        af.setProductType(productStr.substring(5));
        }
        
        for(int i=0;i<productAttributeList.size();i++) {
            AcntTechinfo techInfo = (AcntTechinfo) productAttributeList.get(i);
            parameterId.setCode(techInfo.getId().getCode());
            parameterId.setKind(techInfo.getId().getKind());
            parameter = parameterLogic.loadByKey(parameterId);
            if(parameter!=null){
                productAttributeStr = productAttributeStr +","+ parameter.getName();
            }
        }
        if(productAttributeStr!=null) {
        af.setProductAttribute(productAttributeStr.substring(5));
        }
        
        for(int i=0;i<workItemList.size();i++) {
            AcntTechinfo techInfo = (AcntTechinfo) workItemList.get(i);
            parameterId.setCode(techInfo.getId().getCode());
            parameterId.setKind(techInfo.getId().getKind());
            parameter = parameterLogic.loadByKey(parameterId);
            if(parameter!=null){
              workItemStr = workItemStr +","+ parameter.getName();
            }
        }
        if(workItemStr!=null) {
        af.setWorkItem(workItemStr.substring(5));
        }
        
        return af;
    }

}
